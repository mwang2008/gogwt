package com.gogwt.app.booking.pipeline;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.gogwt.app.booking.utils.BeanLookupService;

public class PipelineThreadExecutor {
	private static Logger logger = Logger.getLogger(PipelineThreadExecutor.class);

	ThreadPoolExecutor threadPoolExecutor;

	private static final int POOL_SIZE = 5;
	private static final int MAX_POOL_SIZE = 5;
	private static final int KEEP_ALIVE_TIME = 500;
	private static final int QUEUE_SIZE = 50;
	private static final int TIMEOUT = 10;

	public PipelineThreadExecutor() {
		threadPoolExecutor = new ThreadPoolExecutor(POOL_SIZE, MAX_POOL_SIZE,
				KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(QUEUE_SIZE));
	}


	public void execute(final Map<String, Object> params) {
		
		threadPoolExecutor.execute(new Runnable() {
			public void run() {
				logger.debug("PipelineThreadExecutor.execute");
				String path = (String)params.get("pipelinePath");
				logger.debug("path="+path);
				
				ExecutePipeline executePipeline = (ExecutePipeline)BeanLookupService.getBean(path);
				executePipeline.executePipelineProcessors(params);
			}

		});
		
		//shutdowm
		try {
		   threadPoolExecutor.shutdown();
		   threadPoolExecutor.awaitTermination(TIMEOUT, TimeUnit.SECONDS);
		}
		catch (Throwable e) {
			logger.fatal("Timeout exceeded getting content.", e);
		}
	}

}
