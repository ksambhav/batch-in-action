# batch-in-action

####Spring Batch sample application.

#####Blog referred:- https://blog.codecentric.de/en/2012/03/transactions-in-spring-batch-part-1-the-basics/


#####The batch is separated in chunks, and each chunk is running in its own transaction.
#####If there’s a RuntimeException being thrown in one of the participating components, the transaction for the chunk is rolled back and the batch fails. Every already committed chunk of course stays in the processed state.

#####. Spring Batch’s JdbcCursorItemReader uses a separate connection for opening the cursor, thereby bypassing the transaction managed by the transaction manager.

#####A batch job instance is identified by the JobParameters, so a batch job started with certain parameters that have been used in a prior job execution automatically triggers a restart, when the first execution has been failed. If not, the second job execution would be rejected.


*We have several listener types in Spring Batch, the important ones are the following:-
 *The JobExecutionListener has two methods, beforeJob and afterJob. Both of them are, of course, executed outside of the chunk’s transaction.
 *The StepExecutionListener has two methods, beforeStep and afterStep. Both of them are, of course, executed outside of the chunk’s transaction.
 *The ChunkListener has two methods, beforeChunk and afterChunk. The first one is executed inside the chunk’s transaction, the second one outside of the   chunk’s transaction.
 *The ItemReadListener has three methods, beforeRead, afterRead and onReadError. All of them are executed inside the chunk’s transaction.
 *The ItemProcessListener has three methods, beforeProcess, afterProcess and onProcessError. All of them are executed inside the chunk’s transaction.
 *The ItemWriteListener has three methods, beforeWrite, afterWrite and onWriteError. All of them are executed inside the chunk’s transaction.
 *The SkipListener has three methods, onSkipInRead, onSkipInProcess and onSkipInWrite. All of them are executed inside the chunk’s transaction. We’ll talk about this listener in the blog post about skip functionality.