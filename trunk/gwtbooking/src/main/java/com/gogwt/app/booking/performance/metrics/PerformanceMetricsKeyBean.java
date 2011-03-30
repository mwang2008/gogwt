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
 * 
 */

public class PerformanceMetricsKeyBean implements Serializable {
	private String className;
	private String methodName;

	public PerformanceMetricsKeyBean(String className, String methodName) {
		super();
		this.className = className;
		this.methodName = methodName;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	//@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((className == null) ? 0 : className.hashCode());
		result = prime * result
				+ ((methodName == null) ? 0 : methodName.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	//@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PerformanceMetricsKeyBean other = (PerformanceMetricsKeyBean) obj;

		if (className == null) {
			if (other.className != null) {
				return false;
			}
		} else if (!className.equals(other.className)) {
			return false;
		}
		if (methodName == null) {
			if (other.methodName != null) {
				return false;
			}
		} else if (!methodName.equals(other.methodName)) {
			return false;
		}
		return true;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

}

