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
	 			/*
			 select * from (select display_name, start_time, max(time) as mintime from tracking_mobile where groupId='g5' group by display_name, start_time) as x inner join tracking_mobile as f on f.display_name = x.display_name and f.start_time = x.start_time and f.time = x.mintime; 
			 */
			String SQL_QUERY_MIN = "select * from (" +
			                        "select display_name, start_time, min(time) as mintime " +
			                        "from tracking_mobile where groupId=:groupId group by display_name, start_time"+ 
			                        ") as x inner join tracking_mobile as f on f.display_name = x.display_name and f.start_time = x.start_time and f.time = x.mintime"; 
			String SQL_QUERY_MAX = "select * from (" +
                    "select display_name, start_time, max(time) as maxtime " +
                    "from tracking_mobile where groupId=:groupId group by display_name, start_time"+ 
                    ") as x inner join tracking_mobile as f on f.display_name = x.display_name and f.start_time = x.start_time and f.time = x.maxtime"; 

	 
			SQLQuery queryMin = session.createSQLQuery(SQL_QUERY_MIN);		
			queryMin.addEntity(TrackingMobileData.class);
			queryMin.setString("groupId", customerProfile.getGroupId());
			List<TrackingMobileData> resultMin = queryMin.list();
			
			SQLQuery queryMax = session.createSQLQuery(SQL_QUERY_MAX);	
			queryMax.addEntity(TrackingMobileData.class);
			queryMax.setString("groupId", customerProfile.getGroupId());
			List<TrackingMobileData> resultMax = queryMax.list();
			
			
			List<TrackingMobileData> retList = new ArrayList<TrackingMobileData>();
		 
			retList.addAll(resultMin);
			retList.addAll(resultMax);
 
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

	/**
	 * retrieve historial track detail.
	 */
	public List<TrackingMobileData> getHistorialTrackDetail(String groupId, String displayName, long startTimeLong) throws InvalidUserException, AppRemoteException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			
			Query query = null;
			
			query = session.createQuery("from TrackingMobileData where groupId=:groupId and displayName=:displayName and startTime=:startTime  order by displayName, startTime");
			query.setParameter("groupId", groupId);
			query.setParameter("displayName", displayName);
			query.setParameter("startTime", startTimeLong);
			
			List<TrackingMobileData> result = query.list();
			
            tx.commit();

			return result;
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
	 
	public int deleteTrack(String userName, String groupId, String displayName, long startTimeLong) throws InvalidUserException, AppRemoteException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
		    String hql = "delete from TrackingMobileData where groupId=:groupId and displayName=:displayName and startTime=:startTime";
		    Query query = session.createQuery(hql);
		    query.setParameter("groupId", groupId);
			query.setParameter("displayName", displayName);
			query.setParameter("startTime", startTimeLong);
			int row = query.executeUpdate();
			tx.commit();
			
			return row;
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}

			e.printStackTrace();
			throw new AppRemoteException("error for deleteTrack", e);
		} finally {
			if (session != null && session.isOpen()) {
				//session.close();  //no need to close it, as getCurrentSession(), commit and rollBack close connection
			}
		}	
		 
	}
	
	public List<TrackingMobileData> getTrack(String userName, String groupId, String displayName, long startTimeLong) throws InvalidUserException, AppRemoteException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
		    String hql = "from TrackingMobileData where groupId=:groupId and displayName=:displayName and startTime=:startTime";
		    Query query = session.createQuery(hql);
		    query.setParameter("groupId", groupId);
			query.setParameter("displayName", displayName);
			query.setParameter("startTime", startTimeLong);
			
			List<TrackingMobileData> result = query.list();
			
			tx.commit();
			
			return result;
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}

			e.printStackTrace();
			throw new AppRemoteException("error for deleteTrack", e);
		} finally {
			if (session != null && session.isOpen()) {
				//session.close();  //no need to close it, as getCurrentSession(), commit and rollBack close connection
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
			tx = session.beginTransaction();

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

			tx.commit();

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
