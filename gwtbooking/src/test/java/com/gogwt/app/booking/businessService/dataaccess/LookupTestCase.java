package com.gogwt.app.booking.businessService.dataaccess;

import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import com.gogwt.app.booking.businessService.dataaccess.hibernate.HibernateCommonDAO;
import com.gogwt.app.booking.dto.dataObjects.common.KeywordBean;
import com.gogwt.app.booking.utils.ToStringUtils;
 

/**
 * mvn test -Dtest=LookupTestCase
 * @author wangm
 *
 */
public class LookupTestCase extends TestCase {

	private static Logger logger = Logger.getLogger(LookupTestCase.class);
	
	public LookupTestCase(String name) {
		super(name);
		//System.out.println(" LookupTestCase ");
	}

	protected void setUp() throws Exception {
		super.setUp();
		logger.info("setUp ");
	}

	public void testGetLookupKeyword() throws Exception {
		CommonDAO commonDAO = new HibernateCommonDAO();
		String [] keywrods = {"atl", "orl", "new"};
		for (int i=0; i<10; i++) {
			long start = System.currentTimeMillis();
			String keyword = keywrods[i%3];
			List<KeywordBean> keywordList = commonDAO.getKeywordList(keyword, 10);
			long end = System.currentTimeMillis();
			System.out.println(" runtime: i=" + i+ ", " + keyword + "," + (end-start));
		}
	}
	public void XXXXtestLookupKeyword() throws Exception {
		logger.info("testLookupKeyword");
		CommonDAO commonDAO = new HibernateCommonDAO();
		
		String keyword = "atl";
		List<KeywordBean> keywordList = commonDAO.getKeywordList(keyword, 10);
		
		for (KeywordBean theKeyword : keywordList) {
			System.out.println(ToStringUtils.toString(theKeyword));
			
		}
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
		logger.info("tearDown ");
	}

	   /**
     * @return the suite of tests being tested
     */
	/*
    public static Test suite()
    {
        return new TestSuite( LookupTestCase.class );
    }
    */

}
