/**
 * 
 */
package com.samsoft.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

/**
 * @author Kumar Sambhav Jain
 *
 */
public class SalaryStepChunkListener implements ChunkListener {

	private static final Logger LOG = LoggerFactory.getLogger(SalaryStepChunkListener.class);

	@Override
	public void beforeChunk(ChunkContext context) {
		LOG.debug("beforeChunk " + context);

	}

	@Override
	public void afterChunk(ChunkContext context) {
		LOG.debug("afterChunk " + context);

	}

	@Override
	public void afterChunkError(ChunkContext context) {
		LOG.error("afterChunkError " + context);

	}

}
