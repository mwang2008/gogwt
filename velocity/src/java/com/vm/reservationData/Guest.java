package com.vm.reservationData;

public class Guest
{
  private String mFirstName;
  private String mLastName;
  private String mEmail;

  /**
   * @return Returns the value of {@link #mEmail}.
   */
  public String getEmail()
  {
    return mEmail;
  }

  /**
   * @param email Set the value of {@link #mEmail}.
   */
  public void setEmail( String email )
  {
    mEmail = email;
  }

  /**
   * @return Returns the value of {@link #mFirstName}.
   */
  public String getFirstName()
  {
    return mFirstName;
  }

  /**
   * @param firstName Set the value of {@link #mFirstName}.
   */
  public void setFirstName( String firstName )
  {
    mFirstName = firstName;
  }

  /**
   * @return Returns the value of {@link #mLastName}.
   */
  public String getLastName()
  {
    return mLastName;
  }

  /**
   * @param lastName Set the value of {@link #mLastName}.
   */
  public void setLastName( String lastName )
  {
    mLastName = lastName;
  }
  
 
  public String toString()
  {
      StringBuffer buf = new StringBuffer( 64 );
      buf.append( getClass().getName() );

      buf.append( "(" );

      buf.append( "mFirstName: " + mFirstName + "  " );
      buf.append( "mLastName: " + mLastName + "  " );
      buf.append( "mEmail: " + mEmail + "  " );
      buf.append( " )");
      
      return buf.toString();

  }
}
