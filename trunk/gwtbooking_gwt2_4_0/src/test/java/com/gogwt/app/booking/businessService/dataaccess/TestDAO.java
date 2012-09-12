package com.gogwt.app.booking.businessService.dataaccess;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestDAO extends TestCase {
	
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TestDAO( String testName )
    {
        super( testName );
    }
    
	protected void setUp() throws Exception {
		super.setUp();
		System.out.println(" TestDAO setUp ");
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
		System.out.println(" TestDAO tearDown ");
	}
	
	 /**
     * Rigourous Test :-)
     */
    public void testDAO()
    {
        assertTrue( true );
        System.out.println(" TestDAO test DAO");
    }
    
    
    public void MytestDAO()
    {
        assertTrue( true );
        System.out.println("TestDAO MytestDAO");
    }


    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TestDAO.class );
    }

   
}
