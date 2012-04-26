package com.gogwt.apps.tracking.services.dataaccess.hibernate;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
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
import com.gogwt.apps.tracking.data.TrackingSmsData;
import com.gogwt.apps.tracking.exceptions.AppRemoteException;
import com.gogwt.apps.tracking.exceptions.CouldNotFindException;
import com.gogwt.apps.tracking.exceptions.DisplayNameAlreadyLoginException;
import com.gogwt.apps.tracking.exceptions.DuplicatedUserNameException;
import com.gogwt.apps.tracking.exceptions.InvalidUserException;
import com.gogwt.apps.tracking.formbean.C2DMRegisterBean;
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
			int index = 0;
			for (TrackingMobileData trackData : trackingMobileDataList) {
				try {
	
					session.save(trackData);
				    if (index%20 == 0) {  //20, same as the JDBC batch size
				    	session.flush();  //flush a batch of inserts and release memory:
				        session.clear();
					}
					totalSaved++;
					index++;
				} catch (HibernateException e) {
					logger.error("Error when insert tracking data "	+ trackData.toString(), e);
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
	
	@Override
	public int saveSmsData(List<TrackingSmsData> smsDataList) throws InvalidUserException, AppRemoteException {
		logger.debug("start saveTrackingData");
		Session session = null;
		Transaction tx = null;
		try {
			if (session == null || !session.isOpen()) {
				session = HibernateUtil.getSessionFactory().openSession();
			}

			tx = session.beginTransaction();
			int totalSaved = 0;
			for (TrackingSmsData trackData : smsDataList) {
				try {
					session.save(trackData);
					totalSaved++;
				} catch (HibernateException e) {
					e.printStackTrace();
					tx.rollback();
					logger.error(
							"Error when insert sms data " + trackData.toString(), e);
				}
			}
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
		}	}

	/**
	 * 
	 * select min(time), max(time), display_name, start_time from
	 * tracking_mobile where groupid='g5' group by display_name, start_time
	 * order by display_name, start_time ;
	 * 
	 * @param customerProfile
	 * @return
	 */
	public List<TrackingMobileData> retrieveMinLocationsSnapShot(
			CustomerProfile customerProfile) throws InvalidUserException, AppRemoteException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
	 		/*
			 select * from (select display_name, start_time, min(time) as mintime from tracking_mobile where groupId='gogwteam' group by display_name, start_time) as x inner join tracking_mobile as f on f.display_name = x.display_name and f.start_time = x.start_time and f.time = x.mintime;
			 select * from (select display_name, start_time, max(time) as maxtime from tracking_mobile where groupId='gogwteam' group by display_name, start_time ) as x inner join tracking_mobile as f on f.display_name = x.display_name and f.start_time = x.start_time and f.time = x.maxtime;
			*/
			
			String SQL_QUERY_MIN = "select * from (" +
			                        "select display_name, start_time, min(time) as mintime " +
			                        "from tracking_mobile where groupId=:groupId group by display_name, start_time"+ 
			                        ") as x inner join tracking_mobile as f on f.display_name = x.display_name and f.start_time = x.start_time and f.time = x.mintime"; 
		 
			SQLQuery queryMin = session.createSQLQuery(SQL_QUERY_MIN);		
			queryMin.addEntity(TrackingMobileData.class);
			queryMin.setString("groupId", customerProfile.getGroupId());
			List<TrackingMobileData> resultMin = queryMin.list();
	 
			tx.commit();
			
			return resultMin;
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
	 
	public List<TrackingMobileData> retrieveMaxLocationsSnapShot(
			CustomerProfile customerProfile) throws InvalidUserException, AppRemoteException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
	 		/*
			 select * from (select display_name, start_time, min(time) as mintime from tracking_mobile where groupId='gogwteam' group by display_name, start_time) as x inner join tracking_mobile as f on f.display_name = x.display_name and f.start_time = x.start_time and f.time = x.mintime;
			 select * from (select display_name, start_time, max(time) as maxtime from tracking_mobile where groupId='gogwteam' group by display_name, start_time ) as x inner join tracking_mobile as f on f.display_name = x.display_name and f.start_time = x.start_time and f.time = x.maxtime;
			*/
		 	String SQL_QUERY_MAX = "select * from (" +
                                    "select display_name, start_time, max(time) as maxtime " +
                                    "from tracking_mobile where groupId=:groupId group by display_name, start_time"+ 
                                    ") as x inner join tracking_mobile as f on f.display_name = x.display_name and f.start_time = x.start_time and f.time = x.maxtime"; 

	  	
			SQLQuery queryMax = session.createSQLQuery(SQL_QUERY_MAX);	
			queryMax.addEntity(TrackingMobileData.class);
			queryMax.setString("groupId", customerProfile.getGroupId());
			List<TrackingMobileData> resultMax = queryMax.list();
			
		 
			tx.commit();
			
			return resultMax;
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
	 * @deprecated use getTrack instead
	 */
	public List<TrackingMobileData> getHistorialTrackDetail(String groupId, String displayName, long startTimeLong) throws InvalidUserException, AppRemoteException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			
			Query query = null;
			
			query = session.createQuery("from TrackingMobileData where groupId=:groupId and displayName=:displayName and startTime=:startTime  order by displayName, time");			
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
	 
	@Override
	public List<TrackingSmsData> findAllTrackingSmsData(String groupId, String displayName, long startTimeLong) throws InvalidUserException, AppRemoteException {

		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			
			Query query = null;
			
			query = session.createQuery("from TrackingSmsData where groupId=:groupId and displayName=:displayName and startTime=:startTime  order by displayName, date");			
			query.setParameter("groupId", groupId);
			query.setParameter("displayName", displayName);
			query.setParameter("startTime", startTimeLong);
			
			List<TrackingSmsData> result = query.list();
			
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
	
	/**
	 * 
	 */
	public List<TrackingMobileData> getTrack(String userName, String groupId, String displayName, long startTimeLong) throws InvalidUserException, AppRemoteException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			
		 
			String SQL_QUERY = "select * from tracking_mobile where groupId=:groupId and display_name=:displayName and start_time=:startTime order by display_name, time";
 
            SQLQuery query = session.createSQLQuery(SQL_QUERY);	
            query.addEntity(TrackingMobileData.class);
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
			throw new AppRemoteException("error for getTrack", e);
		} finally {
			if (session != null && session.isOpen()) {
				//session.close();  //no need to close it, as getCurrentSession(), commit and rollBack close connection
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
			//System.out.println(" idStr=" + idStr);

			return idStr;

		} catch (ConstraintViolationException e) {
			String sqlState = e.getSQLState();
			String constrainName = e.getConstraintName();
			e.printStackTrace();
			
			SQLException sqle = e.getSQLException();
			
			if (StringUtils.equals(SQL_DUPLICATE_RECORD, sqlState)) {
				throw new DuplicatedUserNameException("Username of "
						+ request.getUserName()
						+ " has been used, please choose another one");
			}

			throw new AppRemoteException("Errore: " + sqle.getMessage());					

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

	public CustomerProfile retrieveCustomerProfileByUsernameAndGroupId(final String userName, final String groupId) throws InvalidUserException, AppRemoteException {
		logger.debug("start retrieveCustomerProfileByUsername");

		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();

			session.beginTransaction();

			Query query = session
					.createQuery("from CustomerProfile where userName=:userName and groupId=:groupId");
			query.setParameter("userName", userName);
			query.setParameter("groupId", groupId);

			List<CustomerProfile> result = query.list();

			session.getTransaction().commit();

			if (result == null || result.isEmpty()) {
				throw new InvalidUserException(
						"invalid Username and/or GroupId: "
								+ userName);
			}

			return result.get(0);
		}

		catch (GenericJDBCException e) {
			e.printStackTrace();
			// throw new AppRemoteException("error for gettting customer", e);
			throw new InvalidUserException("Wrong Username and/or GroupId "
					+ userName);
		} finally {
			if (session != null) {
				// session.close();
			}
		}
	}
	
	@Override
	public CustomerProfile retrieveCustomerProfileByUsernameAndGroupId(
			LoginFormBean loginForm) throws InvalidUserException,
			AppRemoteException {
		logger.debug("start retrieveCustomerProfileByUsername");

		return retrieveCustomerProfileByUsernameAndGroupId(loginForm.getUserName(), loginForm.getGroupId());
		 
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
	public CustomerProfile updateCustomer(CustomerProfile profile)
			throws AppRemoteException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();

			tx = session.beginTransaction();
			CustomerProfile updateProfile = (CustomerProfile)session.merge(profile);
			tx.commit();
			
			return updateProfile;
		}

		catch (GenericJDBCException e) {
			e.printStackTrace();
			// throw new AppRemoteException("error for gettting customer", e);
			throw new AppRemoteException("wrong update GroupId=: " + profile.getGroupId() + ", username="+profile.getUserName());
		} finally {
			 
			if (session != null) {
				// session.close();
			}
		}	 
	}
	
	public void deleteAccountByGroupIdNuserName(final String groupId, final String userName) throws AppRemoteException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();

			tx = session.beginTransaction();
			Query query = session.createQuery("delete from CustomerProfile where groupId=:groupId and userName=:userName");
			query.setParameter("groupId", groupId);
			query.setParameter("userName", userName);
			query.executeUpdate(); 
			
			tx.commit();
		}

		catch (GenericJDBCException e) {
			e.printStackTrace();			
			throw new AppRemoteException("wrong delete GroupId=: " + groupId + ", username="+userName);
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

	@Override
	public void registerC2DM(C2DMRegisterBean registerBean)
			throws AppRemoteException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();

			tx = session.beginTransaction();
			//session.merge(registerBean);
			session.saveOrUpdate(registerBean);
			tx.commit();
		}

		catch (GenericJDBCException e) {
			e.printStackTrace();			
			throw new AppRemoteException("wrong save c2dm register=: " + registerBean.getPhone() + ", id="+registerBean.getRegistrationid());
		} 
		catch (Exception e) {
			e.printStackTrace();			
			throw new AppRemoteException("wrong save c2dm register=: " + registerBean.getPhone() + ", id="+registerBean.getRegistrationid());
		} 
		finally {
			 
			if (session != null) {
				// session.close();
			}
		}	  
		
	}


	
	public void unregisterC2DM(final C2DMRegisterBean registerBean) throws AppRemoteException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();

			tx = session.beginTransaction();
			Query query = session.createQuery("delete from C2DMRegisterBean where phone=:phone");
			query.setParameter("phone", registerBean.getPhone());
			 
			query.executeUpdate(); 
			
			tx.commit();
		}

		catch (Exception e) {
			e.printStackTrace();			
			throw new AppRemoteException("wrong delete phone=: " + registerBean.getPhone());
		} finally {
			 
			if (session != null) {
				// session.close();
			}
		}	 
	}

	public C2DMRegisterBean getC2DMRegisterByPhonenumber(final String phone) throws CouldNotFindException, AppRemoteException {
		logger.debug("start retrieveCustomerProfileByUsername");

		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();

			session.beginTransaction();

			Query query = session
					.createQuery("from C2DMRegisterBean where phone=:phone");
			query.setParameter("phone", phone);

			List<C2DMRegisterBean> result = query.list();

			session.getTransaction().commit();

			if (result == null || result.isEmpty()) {
				throw new CouldNotFindException("invalid phone: " + phone);
			}

			return result.get(0);
		}

		catch (GenericJDBCException e) {
			e.printStackTrace();
			// throw new AppRemoteException("error for gettting customer", e);
			throw new CouldNotFindException("wrong phone: " + phone);
		} finally {
			
			if (session != null) {
				// session.close();
			}
		}
	}
	
	@Override
	public List<C2DMRegisterBean> getRegisterListByGroupId(String groupId)
			throws AppRemoteException {
		logger.debug("start getRegisterListByGroupId");

		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();

			session.beginTransaction();

			Query query = session
					.createQuery("from C2DMRegisterBean where groupId=:groupId");
			query.setParameter("groupId", groupId);

			List<C2DMRegisterBean> result = query.list();

			session.getTransaction().commit();

			if (result == null || result.isEmpty()) {
				throw new CouldNotFindException("invalid groupId: " + groupId);
			}

			return result;
		}

		catch (Exception e) {
			e.printStackTrace();
			// throw new AppRemoteException("error for gettting customer", e);
			throw new CouldNotFindException("wrong groupId: " + groupId);
		} finally {
			
			if (session != null) {
				// session.close();
			}
		}
	}
	
	
}
