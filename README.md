# batch-in-action

##Spring Batch sample application.

##Handy blog:- https://blog.codecentric.de/en/2012/03/transactions-in-spring-batch-part-1-the-basics/


###The batch is separated in chunks, and each chunk is running in its own transaction.
### If there’s a RuntimeException being thrown in one of the participating components, the transaction for the chunk is rolled back and the batch fails. Every already committed chunk of course stays in the processed state.