package com.gogwt.app.booking.performance.metrics;

import java.util.Map;
/**
 * <code><B>PerformanceMetricsCollector</code></b>
 * <p>
 * Marker interface used in order to not have compile errors related to aspectj (*.aj)
 * references for performance metrics
 * </p>
 * @author michael.wang
 */
public interface PerformanceMetricsCollector {
	Map <PerformanceMetricsKeyBean, PerformanceMetricsValueBean> getMetricsMap();
}
