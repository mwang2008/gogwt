package com.gogwt.app.booking.businessService.dataaccess;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestDAO extends TestCase {
	
	 /**
     * Rigourous Test :-)
     */
    public void testDAO()
    {
        assertTrue( true );
        System.out.println(" test DAO");
    }
    
	 /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TestDAO( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TestDAO.class );
    }

   
}
