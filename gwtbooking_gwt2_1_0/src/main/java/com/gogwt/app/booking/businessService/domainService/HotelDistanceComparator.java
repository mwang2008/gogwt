package com.gogwt.app.booking.businessService.domainService;

import java.util.Comparator;

import com.gogwt.app.booking.dto.dataObjects.common.HotelBean;

public class HotelDistanceComparator  implements Comparator<HotelBean> {
    private static final int EAUAL = 0;
    private static final int GREATER = 1;
    private static final int LESSER = -1;

    public int compare(HotelBean obj1, HotelBean obj2) {

       // HotelBean obj1 = (HotelBean)ob1;
       // HotelBean obj2 = (HotelBean)ob2;

        if (obj1 == null && obj2 == null) {
           return EAUAL;
        }

        if (obj1 == null && obj2 != null) {
            return LESSER;
        }

        if (obj1 != null && obj2 == null) {
            return GREATER;
        }

        double dist1 =  obj1.getDistance();
        double dist2 = obj2.getDistance();


        if (dist1 > dist2) {
           return GREATER;
        }
        else if (dist1 == dist2) {
            return EAUAL;
        }
        else {
            return LESSER;
        }
    }
}

