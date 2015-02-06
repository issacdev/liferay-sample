package com.dbs.db.transaction;
 
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

import com.dbs.db.dao.Transaction;
import com.dbs.db.dao.annotation.TransactionRequired;
 
public class TransactionMonitor implements MethodInterceptor {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
 
		TransactionRequired transactionRequired = methodInvocation.getMethod().getAnnotation(TransactionRequired.class);
		if (transactionRequired != null){
			Transaction.getInstance().begin();
			logger.debug("Transaction Begin");
		}
 
		try {
			logger.debug("Method "+methodInvocation.getMethod().getName()+" invoking");
			Object result = methodInvocation.proceed();
			logger.debug("Method "+methodInvocation.getMethod().getName()+" invoked");
			
			if (transactionRequired != null){
				Transaction.getInstance().commit();
				logger.debug("Transaction Committed");
			}
			return result;
 
		} catch (IllegalArgumentException e) {
			if (transactionRequired != null){
				Transaction.getInstance().rollback();
				logger.debug("Transaction Rollback");
			}
			throw e;
		}finally{
			Transaction.getInstance().close();
		}
	}
}