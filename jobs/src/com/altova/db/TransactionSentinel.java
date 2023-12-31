/**
 * TransactionSentinel.java
 *
 * This file was generated by MapForce 2015r3.
 *
 * YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
 * OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.
 *
 * Refer to the MapForce Documentation for further details.
 * http://www.altova.com/mapforce
 */
 
package com.altova.db;

public class TransactionSentinel 
{
	private TransactionHelper tr;
	private String name;
		
	public TransactionSentinel (TransactionHelper t, String name) throws java.sql.SQLException
	{
		tr = t;
		this.name = name;
		tr.beginTrans(name);
	}
	
	public void commit() throws java.sql.SQLException
	{
		tr.commitTrans();
		tr = null;
	}
	
	public void rollback() throws java.sql.SQLException
	{
		if (tr != null)
			tr.rollbackTrans(name);
	}	
}
