package com.gogwt.app.booking.businessService.dataaccess;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * mvn test
 * @author wangm
 *
 */
public class GoGWTTestSuite extends TestCase {
	  public static Test suite()
	    {
		  TestSuite suite = new TestSuite();
		  
		   
		  suite.addTest(new TestSuite( LookupTestCase.class ));
		  suite.addTest(new TestSuite( TestDAO.class ));
		  
		  //suite.addTest(new LookupTestCase("testLookupKeyword"));  //test single method


		  return suite;
	    }
}
