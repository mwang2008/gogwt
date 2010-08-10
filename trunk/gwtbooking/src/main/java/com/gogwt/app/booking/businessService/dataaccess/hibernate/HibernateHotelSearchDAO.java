package com.gogwt.app.booking.businessService.dataaccess.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import com.gogwt.app.booking.businessService.dataaccess.HotelSearchDAO;
import com.gogwt.app.booking.businessService.geocode.EarthGeoUtils;
import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;
import com.gogwt.app.booking.dto.dataObjects.request.GeocodeBean;
import com.gogwt.app.booking.dto.dataObjects.request.ReservationBean;

public class HibernateHotelSearchDAO implements HotelSearchDAO {

	private static Logger logger = Logger
			.getLogger(HibernateHotelSearchDAO.class);

	/**
	 * Search property
	 */
 
	public List<HotelBean> searchHotel(final GeocodeBean hotelSearchRequestBean) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		double deltaLat = EarthGeoUtils.getDeltaLatitudeFromDistance(
				hotelSearchRequestBean.getLat(), hotelSearchRequestBean
						.getLng(), hotelSearchRequestBean.getRadius());

		double deltaLng = EarthGeoUtils.getDeltaLongitudeFromDistance(
				hotelSearchRequestBean.getLat(), hotelSearchRequestBean
						.getLng(), hotelSearchRequestBean.getRadius());

		double minLat = hotelSearchRequestBean.getLat() - deltaLat;
		double maxLat = hotelSearchRequestBean.getLat() + deltaLat;
		double minLng = hotelSearchRequestBean.getLng() - deltaLng;
		double maxLng = hotelSearchRequestBean.getLng() + deltaLng;

		if (logger.isDebugEnabled()) {
			logger.debug("minLat=" + minLat + ",maxLat=" + maxLat + ",minLng="
					+ minLng + ",maxLng=" + maxLng);
		}

		Query query = session
				.createQuery("from HotelBean where lat>:minLat and lat<:maxLat and lng>:minLng and lng<:maxLng");
		query.setParameter("minLat", minLat);
		query.setParameter("maxLat", maxLat);
		query.setParameter("minLng", minLng);
		query.setParameter("maxLng", maxLng);

		List<HotelBean> result = query.list();

		session.getTransaction().commit();

		if (logger.isDebugEnabled()) {
			if (result != null) {
				for (int i = 0; i < result.size(); i++) {
					logger.debug(" " + result.get(i).toString());
				}
			}
		}
		
		return result;

	}

	/**
	 * Save reservation
	 */
	public int confirmReservation(ReservationBean reservation) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		session.save(reservation);

		session.getTransaction().commit();
		
		return reservation.getId();
		
	}

}
