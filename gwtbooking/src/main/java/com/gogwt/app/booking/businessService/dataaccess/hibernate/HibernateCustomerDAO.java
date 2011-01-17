package com.gogwt.app.booking.businessService.dataaccess.hibernate;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.GenericJDBCException;

import com.gogwt.app.booking.businessService.dataaccess.CustomerDAO;
import com.gogwt.app.booking.dto.dataObjects.common.CustomerProfile;
import com.gogwt.app.booking.dto.dataObjects.request.LoginFormBean;
import com.gogwt.app.booking.exceptions.clientserver.AppRemoteException;
import com.gogwt.app.booking.exceptions.clientserver.DuplicatedUserNameException;
import com.gogwt.app.booking.exceptions.clientserver.InvalidUserException;
import com.gogwt.framework.arch.utils.StringUtils;

public class HibernateCustomerDAO implements CustomerDAO {
	private static Logger logger = Logger.getLogger(HibernateCustomerDAO.class);
	private static final String SQL_DUPLICATE_RECORD = "23000";

	public CustomerProfile retrieveCustomerProfileByUsername(LoginFormBean loginForm)
			throws InvalidUserException, AppRemoteException {
		logger.debug("start retrieveCustomerProfileByUsername");

		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();

	        session.beginTransaction();
	        
	    	Query query = session.createQuery("from CustomerProfile where userName=:userName");
			query.setParameter("userName", loginForm.getUserName());
			List<CustomerProfile> result = query.list();
 
			if (result == null || result.isEmpty()) {
			    throw new InvalidUserException("invalid user: " + loginForm.getUserName());
			}
			 
			session.getTransaction().commit();
			
			return result.get(0);
		}
		
		catch (GenericJDBCException e) {
			e.printStackTrace();
			//throw new AppRemoteException("error for gettting customer", e);
			throw new InvalidUserException("wrong username: " + loginForm.getUserName());
		}
		 
	}

	public String enrollCustomer(final CustomerProfile request)
			throws DuplicatedUserNameException, AppRemoteException {

		logger.debug("start CustomerProfile");
		Transaction tx = null;
		try {
			Session session = HibernateUtil.getSessionFactory()
					.getCurrentSession();

			tx = session.beginTransaction();
			
			UUID uuid = UUID.randomUUID();
			String id = uuid.toString();

			request.setId(id);
			// save
			session.save(request);

			//session.getTransaction().commit();
			tx.commit();
			
			String idStr = request.getId();
			System.out.println(" idStr="+ idStr);
			
			return id;

		} catch (ConstraintViolationException e) {
			String sqlState = e.getSQLState();
			if (StringUtils.equals(SQL_DUPLICATE_RECORD, sqlState)) {
				throw new DuplicatedUserNameException("duplicate username: "
						+ request.getUserName());
			}

			throw new AppRemoteException("unknown errore: "
					+ request.getUserName());

		} catch (Exception e) {
			if (tx != null) {
			  tx.rollback();
			}
			 

			e.printStackTrace();
			throw new AppRemoteException("error for creating customer", e);
		}
	}

	public CustomerProfile getCustomerById(final String id)
			throws AppRemoteException {

		logger.debug("start getCustomerById");
		try {
			Session session = HibernateUtil.getSessionFactory()
					.getCurrentSession();

			session.beginTransaction();
			 
 
			// retrieve
			CustomerProfile customerProfile = (CustomerProfile) session.get(
					CustomerProfile.class, id);
			 
			session.getTransaction().commit();
			return customerProfile;


		} catch (Exception e) {
			e.printStackTrace();
			throw new AppRemoteException("error for gettting customer", e);
		}
	}
}
