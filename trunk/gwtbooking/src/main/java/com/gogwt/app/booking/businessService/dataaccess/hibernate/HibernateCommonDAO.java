package com.gogwt.app.booking.businessService.dataaccess.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import com.gogwt.app.booking.businessService.dataaccess.CommonDAO;
import com.gogwt.app.booking.dto.dataObjects.UserContextBean;
import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.common.KeywordBean;
import com.gogwt.app.booking.dto.dataObjects.common.StateBean;

public class HibernateCommonDAO implements CommonDAO {

	private static Logger logger = Logger.getLogger(HibernateCommonDAO.class);
    private static final String QUERY_SELECT_STATE = "selectState";
	
	/**
	 * Get state from database
	 */
	public List<StateBean> getStateList(UserContextBean userContext) {
		logger.debug("getStateList");
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		//Query query = session.getNamedQuery(QUERY_SELECT_STATE);
		
		Query query = session.createQuery("from StateBean where languageId=:languageId ORDER BY state_name ASC");
		query.setParameter("languageId", userContext.getLanguageId());
		List<StateBean> result = query.list();
		
	 	session.getTransaction().commit();
	 	
		return result;
	}

	/**
	 * Retreive hotel detail
	 * Note: currently, return english version for all languages.
	 */
	public HotelBean getHotelDetail(int propertyId, UserContextBean userContext) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
			 
		HotelBean hotelBean = (HotelBean)session.get(HotelBean.class, propertyId);
	  	session.getTransaction().commit();
	
		return hotelBean; 
	}

	public List<KeywordBean> getKeywordList(String keyword, int numberResult) {
		logger.debug("getKeyword");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
        
		Query query = session.createQuery("from KeywordBean where searchkey like :searchkey ORDER BY keyword ASC");
		query.setParameter("searchkey", keyword.toUpperCase() + "%" );		
		query.setFirstResult(0);
		query.setMaxResults(10);

		List<KeywordBean> result = query.list();
		
		session.getTransaction().commit();
		
		return result ;
	}

}
