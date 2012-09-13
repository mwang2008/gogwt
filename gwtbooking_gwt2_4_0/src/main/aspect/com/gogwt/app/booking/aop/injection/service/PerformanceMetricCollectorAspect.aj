package com.gogwt.app.booking.aop.injection.servicebo;

import java.util.Map;
import com.gogwt.app.booking.performance.metrics.*;
import com.gogwt.app.booking.performance.metrics.PerformanceMetricsCollector;

public aspect PerformanceMetricCollectorAspect implements PerformanceMetricsCollector {
  
  /*  
  protected pointcut methodControllerCalls():
    execution(public * com.gogwt.app.booking.controllers.action..*Controller.* (..) );
  */
  
  protected pointcut methodRPCControllerCalls():
    execution(public * com.gogwt.app.booking.rpc.services.reservation..*RPCController.* (..) );
 
 
  protected pointcut methodDomainServiceCalls():
    execution(public * com.gogwt.app.booking.businessService.domainService..*Service.* (..) );
 
  /* 
  protected pointcut interceptedMethodCalls():
    methodControllerCalls() || methodDomainServiceCalls();
  */

  protected pointcut interceptedMethodCalls():
    methodRPCControllerCalls() || methodDomainServiceCalls();

    
  //<PerformanceMetricsKeyBean, PerformanceMetricsValueBean>
  private Map metricsMap
       = new java.util.concurrent.ConcurrentHashMap();
    
   
  Object around() throws RuntimeException : interceptedMethodCalls()  {
     //System.out.println("---%%%%--- interceptedMethodCalls");
     
     Object response = null;
     String methodName = thisJoinPointStaticPart.getSignature().getName();
     String className = thisJoinPointStaticPart.getSignature().getDeclaringType().getSimpleName();   //.getName();
      
     PerformanceMetricsKeyBean key = new PerformanceMetricsKeyBean(className, methodName);
     PerformanceMetricsValueBean value = ensureValue(key);
     
     try {
      // log call
      value.incrementCallCount();
      
      // execute target method
      long start = System.currentTimeMillis();
      response = proceed();
      long end = System.currentTimeMillis();
      
      //System.out.println(" ---%%%%--- timed used: +" + (end - start) + ", methodName="+methodName + ",className=" + className );
      // log latency
      value.setCallLatency(start, end);
      
      long diff = end - start;
      
      // set max latency
      if (diff > value.getMaxCallLatency()) {
        value.setMaxCallLatency(diff);
      }
           
      if (diff < value.getMinCallLatency()) {
        value.setMinCallLatency(diff);
      }
    } catch (RuntimeException r) {
      // log exception
      value.incrementExceptionCount();
      throw r;
    }
    return response;
  }
  
    /**
   * <p>
   * Make sure the map contains the key/value. If it doesnt, then add one
   * </p>
   * <PerformanceMetricsKeyBean, PerformanceMetricsValueBean>
   * @param key
   * @return
   */
  private PerformanceMetricsValueBean ensureValue(PerformanceMetricsKeyBean key) {
    Map map = getMetricsMap();
    PerformanceMetricsValueBean value = (PerformanceMetricsValueBean)map.get(key) ;
    if ( value == null) {
      value = new PerformanceMetricsValueBean();
      map.put(key, value);
    }
    return value;
  }
  
    /**
   * <p>
   * See {@link
   * #setmetricsMap(Map<PerformanceMetricsKeyBean,PerformanceMetricsValueBean>)}
   * </p>
   * @return Returns the metricsMap.
   */
  public Map getMetricsMap()
  {
    if (metricsMap == null) {
       metricsMap = new java.util.concurrent.ConcurrentHashMap();
    }
    return metricsMap;
  }

  public void setMetricsMap(Map metricsMap)
  {
    this.metricsMap = metricsMap;
  }
 
   
}