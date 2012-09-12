package com.gogwt.app.booking.performance.metrics;

import java.io.Serializable;

/**
 * <code><B>PerformanceMetricsValueBean</code></b>
 * <p>
 * Stores metrics related to a single piece of web functionality provided in the
 * application and intercepted by aspectj
 * </p>
 * 
 * @author michael.wang
 */
public class PerformanceMetricsValueBean implements Serializable {
	public static final long ONE_MILLION = 1000000L;
	private int callCount;
	private int exceptionCount;
	private long callLatency;
	private long maxCallLatency;
	private long minCallLatency = ONE_MILLION;

	public final void incrementCallCount() {
		this.callCount++;
	}

	public final void incrementExceptionCount() {
		this.exceptionCount++;
	}

	public final void setCallLatency(long start, long end) {
		this.callLatency += (end - start);
	}

	/**
	 * <p>
	 * See {@link #setcallCount(int)}
	 * </p>
	 * 
	 * @return Returns the callCount.
	 */
	public int getCallCount() {
		return callCount;
	}

	/**
	 * <p>
	 * Set the value of <code>callCount</code>.
	 * </p>
	 * 
	 * @param callCount
	 *            The callCount to set.
	 */
	public void setCallCount(int callCount) {
		this.callCount = callCount;
	}

	/**
	 * <p>
	 * See {@link #setexceptionCount(int)}
	 * </p>
	 * 
	 * @return Returns the exceptionCount.
	 */
	public int getExceptionCount() {
		return exceptionCount;
	}

	/**
	 * <p>
	 * Set the value of <code>exceptionCount</code>.
	 * </p>
	 * 
	 * @param exceptionCount
	 *            The exceptionCount to set.
	 */
	public void setExceptionCount(int exceptionCount) {
		this.exceptionCount = exceptionCount;
	}

	/**
	 * <p>
	 * See {@link #setcallLatency(long)}
	 * </p>
	 * 
	 * @return Returns the callLatency.
	 */
	public long getCallLatency() {
		return callLatency;
	}

	/**
	 * <p>
	 * Set the value of <code>callLatency</code>.
	 * </p>
	 * 
	 * @param callLatency
	 *            The callLatency to set.
	 */
	public void setCallLatency(long callLatency) {
		this.callLatency = callLatency;
	}

	/**
	 * @return the maxCallLatency
	 */
	public long getMaxCallLatency() {
		return maxCallLatency;
	}

	/**
	 * @param maxCallLatency
	 *            the maxCallLatency to set
	 */
	public void setMaxCallLatency(long maxCallLatency) {
		this.maxCallLatency = maxCallLatency;
	}

	/**
	 * @return the minCallLatency
	 */
	public long getMinCallLatency() {
		return minCallLatency;
	}

	/**
	 * @param minCallLatency
	 *            the minCallLatency to set
	 */
	public void setMinCallLatency(long minCallLatency) {
		this.minCallLatency = minCallLatency;
	}
}
