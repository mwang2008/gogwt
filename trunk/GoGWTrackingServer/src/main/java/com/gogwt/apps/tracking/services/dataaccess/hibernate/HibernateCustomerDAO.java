package com.gogwt.apps.tracking.services.dataaccess.hibernate;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.GenericJDBCException;

import com.gogwt.apps.tracking.data.CustomerProfile;
import com.gogwt.apps.tracking.data.TrackingMobileData;
import com.gogwt.apps.tracking.exceptions.AppRemoteException;
import com.gogwt.apps.tracking.exceptions.DisplayNameAlreadyLoginException;
import com.gogwt.apps.tracking.exceptions.DuplicatedUserNameException;
import com.gogwt.apps.tracking.exceptions.InvalidUserException;
import com.gogwt.apps.tracking.formbean.LoginFormBean;
import com.gogwt.apps.tracking.services.dataaccess.CustomerDAO;
import com.gogwt.apps.tracking.utils.StringUtils;

public class HibernateCustomerDAO implements CustomerDAO {
	private static Logger logger = Logger.getLogger(HibernateCustomerDAO.class);
	private static final String SQL_DUPLICATE_RECORD = "23000";

	public int saveTrackingData(
			final List<TrackingMobileData> trackingMobileDataList)
			throws InvalidUserException, AppRemoteException {
		logger.debug("start saveTrackingData");
		Session session = null;
		Transaction tx = null;
		try {
			if (session == null || !session.isOpen()) {
				session = HibernateUtil.getSessionFactory().openSession();
			}

			tx = session.beginTransaction();
			int totalSaved = 0;
			for (TrackingMobileData trackData : trackingMobileDataList) {
				try {
					session.save(trackData);
					totalSaved++;
				} catch (HibernateException e) {
					logger.error(
							"Error when insert tracking data "
									+ trackData.toString(), e);
				}
			}
			// session.getTransaction().commit();
			tx.commit();

			return totalSaved;

		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}

			e.printStackTrace();
			throw new AppRemoteException("error for creating customer", e);
		} finally {
			if (session != null) {
				// session.close();
			}
		}

	}

	/**
	 * 
	 * select min(time), max(time), display_name, start_time from
	 * tracking_mobile where groupid='g5' group by display_name, start_time
	 * order by display_name, start_time ;
	 * 
	 * @param customerProfile
	 * @return
	 */
	public List<TrackingMobileData> retrieveLocationsSnapShot(
			CustomerProfile customerProfile) throws InvalidUserException, AppRemoteException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			
			//String SQL_QUERY_MIN = "select min(time), displayName, startTime, latitude, longitude from TrackingMobileData where groupId=:groupId group by displayName, startTime order by displayName, startTime";
			//String SQL_QUERY_MAX = "select max(time), displayName, startTime, latitude, longitude from TrackingMobileData where groupId=:groupId group by displayName, startTime order by displayName, startTime";
			/*
			String SQL_QUERY_MIN = "select * "+
			                          " from ( " + 
			                          " select displayName, startTime, min(time) as mintime from TrackingMobileData group by displayName, startTime " + 
			                          " ) as x inner join TrackingMobileData as f on f.displayName = x.displayName and f.startTime = x.startTime and f.time = x.mintime where groupId=:groupId";
			                          
			                          
			*/
			/*
			 select * from (select display_name, start_time, max(time) as mintime from tracking_mobile where groupId='g5' group by display_name, start_time) as x inner join tracking_mobile as f on f.display_name = x.display_name and f.start_time = x.start_time and f.time = x.mintime; 
			 */
			String SQL_QUERY_MIN = "select * from (" +
			                        "select display_name, start_time, min(time) as mintime " +
			                        "from tracking_mobile where groupId=:groupId group by display_name, start_time"+ 
			                        ") as x inner join tracking_mobile as f on f.display_name = x.display_name and f.start_time = x.start_time and f.time = x.mintime"; 
			
			//String SQL_QUERY_MIN = "select min(time) as mintime , displayName, startTime from TrackingMobileData where groupId=:groupId group by displayName, startTime order by displayName, startTime";
			//String SQL_QUERY_MIN = "from (select min(time) as mintime , displayName, startTime from TrackingMobileData where groupId=:groupId group by displayName, startTime order by displayName, startTime) as x inner join TrackingMobileData as f on f.displayName = x.displayName and f.startTime = x.startTime and f.time = x.mintime ";
			
			//String SQL_QUERY_MIN = "select  min(time), displayName, startTime from TrackingMobileData where groupId=:groupId group by displayName, startTime"  ;
			
	/*		String SQL_QUERY_MAX = "select * "+
                                     " from ( " + 
                                     " select displayName, startTime, max(time) as maxtime from TrackingMobileData group by displayName, startTime " + 
                                     " ) as x inner join TrackingMobileData as f on f.displayName = x.displayName and f.startTime = x.startTime and f.time = x.maxtime where groupId=:groupId"; 
*/
			String SQL_QUERY_MAX = "select * from (" +
                    "select display_name, start_time, max(time) as maxtime " +
                    "from tracking_mobile where groupId=:groupId group by display_name, start_time"+ 
                    ") as x inner join tracking_mobile as f on f.display_name = x.display_name and f.start_time = x.start_time and f.time = x.maxtime"; 

			
			/*SQLQuery query = session.createSQLQuery(SQL_QUERY_MIN);
			query.addEntity(TrackingMobileData.class);
			query.setString("groupId", customerProfile.getGroupId());
			List result = query.list();*/
			
			SQLQuery queryMin = session.createSQLQuery(SQL_QUERY_MIN);		
			queryMin.addEntity(TrackingMobileData.class);
			//queryMin.setParameter("groupId", customerProfile.getGroupId());
			queryMin.setString("groupId", customerProfile.getGroupId());
			List<TrackingMobileData> resultMin = queryMin.list();
			
			SQLQuery queryMax = session.createSQLQuery(SQL_QUERY_MAX);	
			queryMax.addEntity(TrackingMobileData.class);
			queryMax.setString("groupId", customerProfile.getGroupId());
			List<TrackingMobileData> resultMax = queryMax.list();
			
			
			List<TrackingMobileData> retList = new ArrayList<TrackingMobileData>();
			//TrackingMobileData tmData = null;
			retList.addAll(resultMin);
			retList.addAll(resultMax);
			/*
			Collections.sort(retList, new Comparator<TrackingMobileData>() {
				@Override
				public int compare(TrackingMobileData o1, TrackingMobileData o2) {
					if (o1.getDisplayName().equals(o2.getDisplayName())) {
						return (int)(o1.getTime()-o2.getTime());
					}
					else {
						return o1.getDisplayName().compareTo(o2.getDisplayName());
					}
				}				 
			});
			*/
			tx.commit();
			
			return retList;
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}

			e.printStackTrace();
			throw new AppRemoteException("error for creating retrieveLocationsSnapShot", e);
		} finally {
			if (session != null) {
				// session.close();
			}
		}
	}

	 
	public List<TrackingMobileData> retrieveLocations(
			CustomerProfile customerProfile, Calendar endCal, Calendar startCal)
			throws InvalidUserException, AppRemoteException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();

			Query query = null;
			if (startCal == null) {
				query = session
						.createQuery("from TrackingMobileData where groupId=:groupId order by displayName, startTime");
				query.setParameter("groupId", customerProfile.getGroupId());
			} else {
				query = session
						.createQuery("from TrackingMobileData where groupId=:groupId and createDate>=:startDate and createDate <=:endDate order by displayName, startTime");
				query.setParameter("groupId", customerProfile.getGroupId());
				query.setParameter("startDate", new Date(startCal.getTime()
						.getTime()));
				query.setParameter("endDate", new Date(endCal.getTime()
						.getTime()));
			}

			List<TrackingMobileData> result = query.list();

			session.getTransaction().commit();

			return result;
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}

			e.printStackTrace();
			throw new AppRemoteException("error for creating customer", e);
		} finally {
			if (session != null) {
				// session.close();
			}
		}

	}

	@Override
	public String enrollCustomer(CustomerProfile request)
			throws DuplicatedUserNameException, AppRemoteException {
		logger.debug("start CustomerProfile");

		Session session = null;
		Transaction tx = null;
		try {
			if (session == null || !session.isOpen()) {
				session = HibernateUtil.getSessionFactory().openSession();
			}

			tx = session.beginTransaction();

			session.save(request);

			// session.getTransaction().commit();
			tx.commit();

			String idStr = request.getId();
			System.out.println(" idStr=" + idStr);

			return idStr;

		} catch (ConstraintViolationException e) {
			String sqlState = e.getSQLState();
			String constrainName = e.getConstraintName();

			if (StringUtils.equals(SQL_DUPLICATE_RECORD, sqlState)) {
				throw new DuplicatedUserNameException("Username of "
						+ request.getUserName()
						+ " has been used, please choose another one");
			}

			throw new AppRemoteException("unknown errore: "
					+ request.getUserName());

		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}

			e.printStackTrace();
			throw new AppRemoteException("error for creating customer", e);
		} finally {
			if (session != null) {
				// session.close();
			}
		}
	}

	@Override
	public CustomerProfile getCustomerById(final String id)
			throws AppRemoteException {

		logger.debug("start getCustomerById");
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();

			session.beginTransaction();

			// retrieve
			CustomerProfile customerProfile = (CustomerProfile) session.get(
					CustomerProfile.class, id);

			session.getTransaction().commit();
			return customerProfile;

		} catch (Exception e) {
			e.printStackTrace();
			throw new AppRemoteException("error for gettting customer", e);
		} finally {
			if (session != null) {
				// session.close();
			}
		}
	}

	@Override
	public CustomerProfile retrieveCustomerProfileByUsername(
			LoginFormBean loginForm) throws InvalidUserException,
			AppRemoteException {
		logger.debug("start retrieveCustomerProfileByUsername");

		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();

			session.beginTransaction();

			Query query = session
					.createQuery("from CustomerProfile where userName=:userName and groupId=:groupId");
			query.setParameter("userName", loginForm.getUserName());
			query.setParameter("groupId", loginForm.getGroupId());

			List<CustomerProfile> result = query.list();

			session.getTransaction().commit();

			if (result == null || result.isEmpty()) {
				throw new InvalidUserException(
						"invalid Username and/or GroupId: "
								+ loginForm.getUserName());
			}

			return result.get(0);
		}

		catch (GenericJDBCException e) {
			e.printStackTrace();
			// throw new AppRemoteException("error for gettting customer", e);
			throw new InvalidUserException("Wrong Username and/or GroupId "
					+ loginForm.getUserName());
		} finally {
			if (session != null) {
				// session.close();
			}
		}
	}

	@Override
	public CustomerProfile retrieveCustomerProfileByGroupId(String groupId)
			throws InvalidUserException, AppRemoteException {
		logger.debug("start retrieveCustomerProfileByUsername");

		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();

			session.beginTransaction();

			Query query = session
					.createQuery("from CustomerProfile where groupId=:groupId");
			query.setParameter("groupId", groupId);

			List<CustomerProfile> result = query.list();

			session.getTransaction().commit();

			if (result == null || result.isEmpty()) {
				throw new InvalidUserException("invalid user: " + groupId);
			}

			return result.get(0);
		}

		catch (GenericJDBCException e) {
			e.printStackTrace();
			// throw new AppRemoteException("error for gettting customer", e);
			throw new InvalidUserException("wrong username: " + groupId);
		} finally {
			if (session != null) {
				// session.close();
			}
		}
	}

	@Override
	public void saveRemoteLoginUser(CustomerProfile customerProfile)
			throws DisplayNameAlreadyLoginException, AppRemoteException {

	}

}
