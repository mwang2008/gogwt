package com.gogwt.app.booking.pipeline;

import java.util.Map;

public interface PipelineProcessor {
	 /**
	   * <p>
	   * processor execute
	   * </p>
	   */
	  void execute(final Map <String, Object> params);
}
