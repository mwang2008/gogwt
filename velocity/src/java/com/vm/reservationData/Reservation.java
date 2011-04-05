package com.vm.reservationData;

import java.util.Date;

public class Reservation
{
  private String mConfirmationNumber;
  private Guest mGuest;
  private Car mCar;
  private double mCharge;
  private int mRentDays;
  private Date mStartDate;
  
  
  
  /**
   * @return Returns the value of {@link #mConfirmationNumber}.
   */
  public String getConfirmationNumber()
  {
    return mConfirmationNumber;
  }
  /**
   * @param confirmationNumber Set the value of {@link #mConfirmationNumber}.
   */
  public void setConfirmationNumber( String confirmationNumber )
  {
    mConfirmationNumber = confirmationNumber;
  }
  /**
   * @return Returns the value of {@link #mCar}.
   */
  public Car getCar()
  {
    return mCar;
  }
  /**
   * @param car Set the value of {@link #mCar}.
   */
  public void setCar( Car car )
  {
    mCar = car;
  }
  /**
   * @return Returns the value of {@link #mGuest}.
   */
  public Guest getGuest()
  {
    return mGuest;
  }
  /**
   * @param guest Set the value of {@link #mGuest}.
   */
  public void setGuest( Guest guest )
  {
    mGuest = guest;
  }
 
  /**
   * @return Returns the value of {@link #mCharge}.
   */
  public double getCharge()
  {
    return mCharge;
  }
  /**
   * @param charge Set the value of {@link #mCharge}.
   */
  public void setCharge( double charge )
  {
    mCharge = charge;
  }
  /**
   * @return Returns the value of {@link #mRentDays}.
   */
  public int getRentDays()
  {
    return mRentDays;
  }
  /**
   * @param rentDays Set the value of {@link #mRentDays}.
   */
  public void setRentDays( int rentDays )
  {
    mRentDays = rentDays;
  }
  
  
  /**
   * @return Returns the value of {@link #mStartDate}.
   */
  public Date getStartDate()
  {
    return mStartDate;
  }
  /**
   * @param startDate Set the value of {@link #mStartDate}.
   */
  public void setStartDate( Date startDate )
  {
    mStartDate = startDate;
  }
  public String toString()
  {
      StringBuffer buf = new StringBuffer( 64 );
      buf.append( getClass().getName() );

      buf.append( "(" );
      
      buf.append( "mConfirmationNumber: " + mConfirmationNumber + "  " );
      if (mCar != null) {
        buf.append( "mCar: " + mCar.toString() + "  " );
      } 
      if (mGuest != null) {
        buf.append( "mGuest: " + mGuest.toString() + "  " );
      }      
      buf.append( "mCharge: " + mCharge + "  " );
      buf.append( "mRentDays: " + mRentDays + "  " );
      buf.append( " )");
      
      return buf.toString();

  }
  
}
