package com.vm.reservationData;

public class Car
{
  public final static int FORD = 1;
  public final static int TOYOTA = 2;
  
  private int mId;
  
  /**
   * @return Returns the value of {@link #mId}.
   */
  public int getId()
  {
    return mId;
  }

  /**
   * @param id Set the value of {@link #mId}.
   */
  public void setId( int id )
  {
    mId = id;
  }

  public String toString()
  {
      StringBuffer buf = new StringBuffer( 64 );
      buf.append( getClass().getName() );

      buf.append( "(" );

      buf.append( "mId: " + mId + "  " );       
      buf.append( " )");
      
      return buf.toString();

  }
}
