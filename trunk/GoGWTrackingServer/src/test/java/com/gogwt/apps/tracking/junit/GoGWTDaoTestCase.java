package com.gogwt.apps.tracking.junit;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;

import com.gogwt.apps.tracking.data.CustomerProfile;
import com.gogwt.apps.tracking.data.Stock;
import com.gogwt.apps.tracking.data.StockDailyRecord;
import com.gogwt.apps.tracking.data.TrackingMobileData;
import com.gogwt.apps.tracking.data.TrackingSmsData;
import com.gogwt.apps.tracking.exceptions.InvalidUserException;
import com.gogwt.apps.tracking.services.dataaccess.CustomerDAO;
import com.gogwt.apps.tracking.services.dataaccess.hibernate.HibernateCustomerDAO;
import com.gogwt.apps.tracking.services.dataaccess.hibernate.HibernateUtil;

public class GoGWTDaoTestCase extends TestCase {

	private static Logger logger = Logger.getLogger(GoGWTDaoTestCase.class);

	public GoGWTDaoTestCase(String name) {
		super(name);
		// System.out.println(" LookupTestCase ");
	}

	protected void setUp() throws Exception {
		super.setUp();
		logger.info("setUp ");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		logger.info("tearDown ");
	}

	public List<Stock> sstAllStock() throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			 

			Query query = session
					.createQuery("from Stock");
			List<Stock> stockList = query.list();
			
			
			
			session.getTransaction().commit();
			System.out.println("Done");
			
			return stockList;
		}
		catch (ConstraintViolationException e) {
			String state = e.getSQLState();
			String conName = e.getConstraintName();
			
			e.printStackTrace();
			//System.out.println(" === state="+state + ", conName="+conName);
			
			
		}

		catch (Throwable e) {
			e.printStackTrace();
			// throw new AppRemoteException("error for gettting customer", e);
			throw new InvalidUserException();
		} finally {
			if (session != null) {
				// session.close();
			}
		}
		
		return null;
	}
	
	public void test_updateCustomer() throws Exception {
		System.out.println(" test_updateCustomer ");
		try {
		   CustomerDAO dao = new HibernateCustomerDAO();
		   CustomerProfile profile = dao.retrieveCustomerProfileByUsernameAndGroupId("gogwtuser", "gogwtid");
		   System.out.println(" profile firstname= " + profile.getFirstName());
		
		   profile.setFirstName("updFirst");
		   profile.setLastName("updLast");
		
		   CustomerProfile  updateProfile = dao.updateCustomer(profile);
		
		   System.out.println(" updateProfile firstname= " + updateProfile.getFirstName());
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public void test_insertCustomer() throws Exception {
		System.out.println(" == Start test_insertCustomer");
		try {
			CustomerProfile profile = new CustomerProfile();
			profile.setGroupId("gid2");
			profile.setUserName("myusername2");
			profile.setGroupName("Group ID2");
			profile.setFirstName("myfirst2");
			profile.setLastName("mylastname2");
			profile.setEmail("mdl@gmail.com");
			
			profile.setPassword("pass");
			profile.setActive(false);
			profile.setPhoneNumber("4556666666");
			profile.setCreateDate(new Date());
			
			 CustomerDAO dao = new HibernateCustomerDAO();
			 dao.enrollCustomer(profile);
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
		
		System.out.println(" == End test_insertCustomer");
	}
	
	public void test_retrieveCustomerProfileByUsernameAndGroupId() throws Exception {
		System.out.println(" == Start test_retrieveCustomerProfileByUsernameAndGroupId");
		try {
			CustomerDAO dao = new HibernateCustomerDAO();
			CustomerProfile profile = dao.retrieveCustomerProfileByUsernameAndGroupId(
					"myusername2", "gid2");
			System.out.println(" " + profile.getLastName());
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
		
		System.out.println(" == End test_retrieveCustomerProfileByUsernameAndGroupId");
	}
	 
	
	public void test_insertSms() throws Exception {
		System.out.println(" test_insertSms ");
		CustomerDAO dao = new HibernateCustomerDAO();
		
		List<TrackingSmsData> smsDataList = new ArrayList<TrackingSmsData>();
		
		TrackingSmsData smsData = new TrackingSmsData();
		smsData.setDisplayName("dec29");
		smsData.setGroupId("gogwteam");
		smsData.setAddress("12323333");
		smsData.setRead(0);
		smsData.setType(1);
		smsData.setBody("hi there");
		smsData.setCreateDate(new Date());
		smsData.setStartTime(System.currentTimeMillis());
	 	
		smsDataList.add(smsData);
		
		try {
		   dao.saveSmsData(smsDataList);
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
		
	}
	
	public void testRetriveTracking() throws Exception {
		System.out.println(" testRetriveTracking ");
		CustomerDAO dao = new HibernateCustomerDAO();
		String groupId = "gogwteam";
		String displayName = "dec15";
		Long startTimeLong = Long.parseLong("1324317801280");
		
		
		List<TrackingMobileData> retList = dao.getHistorialTrackDetail(groupId, displayName, startTimeLong);
		
		System.out.println(" \n\n ==== History Tracking Data from DB ==== ");
		int i=0;
		for (TrackingMobileData track : retList) {
			System.out.println("i= " + i++ + "," + track.toString());
		}
		return;
	}
	
	public void testGetallStocks() throws Exception {
		List<Stock> stocks = sstAllStock();
		
		for (Stock stock : stocks) {
			System.out.println(stock.toString());
		}
		return;
		
	}
	
	public void XXXtestSelectStock() throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			Stock stock = (Stock) session.get(Stock.class, 34);
			
			StockDailyRecord stockDailyRecords = new StockDailyRecord();
			stockDailyRecords.setPriceOpen(new Float("2.2"));
			stockDailyRecords.setPriceClose(new Float("3.1"));
			stockDailyRecords.setPriceChange(new Float("120.0"));
			stockDailyRecords.setVolume(3000000L);
			stockDailyRecords.setDate(new java.util.Date());

			stockDailyRecords.setStock(stock);
			stock.getStockDailyRecords().add(stockDailyRecords);

			session.save(stockDailyRecords);
			session.getTransaction().commit();
			System.out.println("Done");
		}
		catch (ConstraintViolationException e) {
			String state = e.getSQLState();
			String conName = e.getConstraintName();
			
			e.printStackTrace();
			//System.out.println(" === state="+state + ", conName="+conName);
			
			
		}

		catch (Throwable e) {
			e.printStackTrace();
			// throw new AppRemoteException("error for gettting customer", e);
			throw new InvalidUserException();
		} finally {
			if (session != null) {
				// session.close();
			}
		}
	}
	public void XXXtestInserStock() throws Exception {
		System.out.println("Hibernate one to many (XML Mapping)");

		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();

			session.beginTransaction();

			Stock stock = new Stock();
			stock.setStockCode("70523");
			stock.setStockName("SON");
			session.save(stock);

			StockDailyRecord stockDailyRecords = new StockDailyRecord();
			stockDailyRecords.setPriceOpen(new Float("23.2"));
			stockDailyRecords.setPriceClose(new Float("3.1"));
			stockDailyRecords.setPriceChange(new Float("120.0"));
			stockDailyRecords.setVolume(3000000L);
			stockDailyRecords.setDate(new java.util.Date());

			stockDailyRecords.setStock(stock);
			stock.getStockDailyRecords().add(stockDailyRecords);

			session.save(stockDailyRecords);

			session.getTransaction().commit();
			System.out.println("Done");
		}
		catch (ConstraintViolationException e) {
			String state = e.getSQLState();
			String conName = e.getConstraintName();
			
			System.out.println(" === state="+state + ", conName="+conName);
			
		}

		catch (Throwable e) {
			e.printStackTrace();
			// throw new AppRemoteException("error for gettting customer", e);
			throw new InvalidUserException();
		} finally {
			if (session != null) {
				// session.close();
			}
		}

		// HibernateCustomerDAO customerDAO = new HibernateCustomerDAO();
		// customerDAO.testInserStock();
	}

	@Test
	public void XXXtestRetriveLocation() throws Exception {
		CustomerDAO customerDAO = new HibernateCustomerDAO();

		System.out.println(" === retriveLocation");
		CustomerProfile customerProfile = new CustomerProfile();
		customerProfile.setGroupId("g1");

		Calendar endCal, startCal;

		List<TrackingMobileData> retrieveLocations = customerDAO
				.retrieveLocations(customerProfile, null, null);

		if (retrieveLocations != null && !retrieveLocations.isEmpty()) {
			for (TrackingMobileData location : retrieveLocations) {
				logger.info(location);
			}
		} else {
			logger.info(" no result");
		}
	}

	public void XXXtestRetriveLocationWithDateRange() throws Exception {
		CustomerDAO customerDAO = new HibernateCustomerDAO();

		System.out.println(" === retriveLocation");
		CustomerProfile customerProfile = new CustomerProfile();
		customerProfile.setGroupId("g1");

		Calendar endCal, startCal = null;
		endCal = Calendar.getInstance();
		String daysParam = "5";
		try {
			int days = Integer.parseInt(daysParam);
			startCal = Calendar.getInstance();
			startCal.add(Calendar.DATE, (-1) * days);
		} catch (NumberFormatException e) {
			// do nothing
		}

		List<TrackingMobileData> retrieveLocations = customerDAO
				.retrieveLocations(customerProfile, endCal, startCal);

		if (retrieveLocations != null && !retrieveLocations.isEmpty()) {
			for (TrackingMobileData location : retrieveLocations) {
				logger.info(location);
			}
		} else {
			logger.info(" no result");
		}
	}

	public void testCreateSaveLocation() throws Exception {
		System.out.println(" === CreateSaveLocation");

		List<TrackingMobileData> retrieveLocations = new ArrayList<TrackingMobileData>();

		TrackingMobileData trackingMobileData = null;
		/*
		 * TrackingMobileData trackingMobileData = new TrackingMobileData();
		 * trackingMobileData.setGroupId("g1");
		 * trackingMobileData.setPhoneNumber("123333");
		 * trackingMobileData.setDisplayName("g1 dis");
		 * trackingMobileData.setLatitude(33299998);
		 * trackingMobileData.setLongitude(-83700000);
		 * trackingMobileData.setAltitude(0.000);
		 * trackingMobileData.setProvider("gps");
		 * trackingMobileData.setAccuracy(-1.000);
		 * trackingMobileData.setBearing(-1.000000 );
		 * trackingMobileData.setDistance(102555.87);
		 * trackingMobileData.setSpeed(0.000000);
		 * trackingMobileData.setTime(1314576003);
		 * trackingMobileData.setTotalDistance(196591.20);
		 * 
		 * retrieveLocations.add(trackingMobileData);
		 * 
		 * trackingMobileData = new TrackingMobileData();
		 * trackingMobileData.setGroupId("g1");
		 * trackingMobileData.setPhoneNumber("123333");
		 * trackingMobileData.setDisplayName("g1 dis");
		 * trackingMobileData.setLatitude(33599998);
		 * trackingMobileData.setLongitude(-83500000);
		 * 
		 * trackingMobileData.setAltitude(0.000);
		 * trackingMobileData.setProvider("gps");
		 * trackingMobileData.setAccuracy(-1.000);
		 * trackingMobileData.setBearing(-1.000000 );
		 * trackingMobileData.setDistance(102555.87);
		 * trackingMobileData.setSpeed(0.000000);
		 * trackingMobileData.setTime(1314576003);
		 * trackingMobileData.setTotalDistance(196591.20);
		 * 
		 * retrieveLocations.add(trackingMobileData);
		 * 
		 * trackingMobileData = new TrackingMobileData();
		 * trackingMobileData.setGroupId("g1");
		 * trackingMobileData.setPhoneNumber("123333");
		 * trackingMobileData.setDisplayName("g1 dis");
		 * trackingMobileData.setLatitude(33299998);
		 * trackingMobileData.setLongitude(-83800000);
		 * trackingMobileData.setAltitude(0.000);
		 * trackingMobileData.setProvider("gps");
		 * trackingMobileData.setAccuracy(-1.000);
		 * trackingMobileData.setBearing(-1.000000 );
		 * trackingMobileData.setDistance(102555.87);
		 * trackingMobileData.setSpeed(0.000000);
		 * trackingMobileData.setTime(1314576003);
		 * trackingMobileData.setTotalDistance(196591.20);
		 * 
		 * retrieveLocations.add(trackingMobileData);
		 */

		// second line
		trackingMobileData = new TrackingMobileData();
		trackingMobileData.setGroupId("g1");
		trackingMobileData.setPhoneNumber("223333");
		trackingMobileData.setDisplayName("g2 dis");
		trackingMobileData.setLatitude(35999998);
		trackingMobileData.setLongitude(-85500000);
		trackingMobileData.setAltitude(0.000);
		trackingMobileData.setProvider("gps");
		trackingMobileData.setAccuracy(-1.000);
		trackingMobileData.setBearing(-1.000000);
		trackingMobileData.setDistance(102555.87);
		trackingMobileData.setSpeed(0.000000);
		trackingMobileData.setTime(1314576003);
		trackingMobileData.setTotalDistance(196591.20);

		retrieveLocations.add(trackingMobileData);

		trackingMobileData = new TrackingMobileData();
		trackingMobileData.setGroupId("g1");
		trackingMobileData.setPhoneNumber("223333");
		trackingMobileData.setDisplayName("g2 dis");
		trackingMobileData.setLatitude(35199998);
		trackingMobileData.setLongitude(-85300000);
		trackingMobileData.setAltitude(0.000);
		trackingMobileData.setProvider("gps");
		trackingMobileData.setAccuracy(-1.000);
		trackingMobileData.setBearing(-1.000000);
		trackingMobileData.setDistance(102555.87);
		trackingMobileData.setSpeed(0.000000);
		trackingMobileData.setTime(1314576003);
		trackingMobileData.setTotalDistance(196591.20);

		retrieveLocations.add(trackingMobileData);

		trackingMobileData = new TrackingMobileData();
		trackingMobileData.setGroupId("g1");
		trackingMobileData.setPhoneNumber("223333");
		trackingMobileData.setDisplayName("g2 dis");
		trackingMobileData.setLatitude(35299998);
		trackingMobileData.setLongitude(-85200000);
		trackingMobileData.setAltitude(0.000);
		trackingMobileData.setProvider("gps");
		trackingMobileData.setAccuracy(-1.000);
		trackingMobileData.setBearing(-1.000000);
		trackingMobileData.setDistance(102555.87);
		trackingMobileData.setSpeed(0.000000);
		trackingMobileData.setTime(1314576003);
		trackingMobileData.setTotalDistance(196591.20);

		retrieveLocations.add(trackingMobileData);

		CustomerDAO customerDAO = new HibernateCustomerDAO();
		customerDAO.saveTrackingData(retrieveLocations);

		return;
	}

}
