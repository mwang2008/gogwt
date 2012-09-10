package com.gogwt.app.booking.pipeline;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.gogwt.app.booking.pipeline.reservation.ReservationDemoProcessor;

 
public class ExecutePipeline {
	private static Logger logger = Logger.getLogger(ReservationDemoProcessor.class);

	private List<PipelineProcessor> mProcessors;

	/**
	 * <p> Start execute each processor </p>
	 */
	public void executePipelineProcessors(final Map<String, Object> params) {
		if (mProcessors != null && !mProcessors.isEmpty()) {
			for (PipelineProcessor processor : mProcessors) {
				try {
					logger.debug("start " + processor.getClass().getName());
					processor.execute(params);
				} catch (Throwable e) {					
					// catch throwable, do not let any exception interfere other pipeline process.
					logger.debug("**** having error when ExecutePipeline: " + processor.getClass().getName(), e);
				}
			}
		}
	}

	/**
	 * <p> See {@link #setmProcessors(List<ResProcessor>)} </p>
	 * 
	 * @return Returns the processors.
	 */
	public List<PipelineProcessor> getProcessors() {
		return mProcessors;
	}

	/**
	 * <p> Set the value of <code>processors</code>. </p>
	 * 
	 * @param processors
	 *            The processors to set.
	 */
	public void setProcessors(List<PipelineProcessor> processors) {
		mProcessors = processors;
	}
}
