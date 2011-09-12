package com.gogwt.apps.tracking.junit;

import java.util.ArrayList;
import java.util.Calendar;
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

	public void XXXXtestCreateSaveLocation() throws Exception {
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