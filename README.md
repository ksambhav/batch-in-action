# batch-in-action

#### Spring Batch Sample application.

###### Blog referred/copied-from :- https://blog.codecentric.de/en/2012/03/transactions-in-spring-batch-part-1-the-basics/


##### Batch Transaction Boundaries

1. The batch is separated in chunks, and each chunk is running in its own transaction.
2. If there’s a RuntimeException being thrown in one of the participating components, the transaction for the chunk is rolled back and the batch fails. Every already committed chunk of course stays in the processed state.
3. Spring Batch’s JdbcCursorItemReader uses a separate connection for opening the cursor, thereby bypassing the transaction managed by the transaction manager.
4. A batch job instance is identified by the JobParameters, so a batch job started with certain parameters that have been used in a prior job execution automatically triggers a restart, when the first execution has been failed. If not, the second job execution would be rejected.


##### We have several listener types in Spring Batch, the important ones are the following:-

1. The JobExecutionListener has two methods, beforeJob and afterJob. Both of them are, of course, executed outside of the chunk’s transaction.
2. The StepExecutionListener has two methods, beforeStep and afterStep. Both of them are, of course, executed outside of the chunk’s transaction.
3. The ChunkListener has two methods, beforeChunk and afterChunk. The first one is executed inside the chunk’s transaction, the second one outside of the   chunk’s transaction.
4. The ItemReadListener has three methods, beforeRead, afterRead and onReadError. All of them are executed inside the chunk’s transaction.
5. The ItemProcessListener has three methods, beforeProcess, afterProcess and onProcessError. All of them are executed inside the chunk’s transaction.
6. The ItemWriteListener has three methods, beforeWrite, afterWrite and onWriteError. All of them are executed inside the chunk’s transaction.
7. The SkipListener has three methods, onSkipInRead, onSkipInProcess and onSkipInWrite. All of them are executed inside the chunk’s transaction.