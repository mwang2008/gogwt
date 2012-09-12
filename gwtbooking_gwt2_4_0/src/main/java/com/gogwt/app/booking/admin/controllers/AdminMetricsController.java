package com.gogwt.app.booking.admin.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.gogwt.app.booking.controllers.BaseAbstractController;
import com.gogwt.app.booking.performance.metrics.PerformanceMetricsCollector;
import com.gogwt.app.booking.performance.metrics.PerformanceMetricsKeyBean;
import com.gogwt.app.booking.performance.metrics.PerformanceMetricsValueBean;
import com.gogwt.app.booking.utils.BeanLookupService;

public class AdminMetricsController extends BaseAbstractController  {
	private static Logger logger = Logger.getLogger(AdminMetricsController.class);
	private static String PERFORMANCE_ASPECTJ = "name=aop/injection/rpc/PerformanceMetricCollectorAspect";
	
	@Override
	protected ModelAndView handleRequestInternal(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	    
		logger.debug("handleRequestInternal");
		
		PerformanceMetricsCollector aspect = getRPCPerformanceMetricCollectorAspect();
		Map<PerformanceMetricsKeyBean, PerformanceMetricsValueBean> metricsInfoMap = 
			new HashMap<PerformanceMetricsKeyBean, PerformanceMetricsValueBean>(aspect.getMetricsMap());

		request.setAttribute("performanceMetricsInfoMap", metricsInfoMap);

		// TODO: valid hotel mnemonic
		return new ModelAndView("/admin/admin_metrics");
	}

	private PerformanceMetricsCollector getRPCPerformanceMetricCollectorAspect() {
		return (PerformanceMetricsCollector) BeanLookupService.getBean(PERFORMANCE_ASPECTJ);
	}
}
