/**
 * ResultSetWrapper.java
 *
 * This file was generated by MapForce 2010r3.
 *
 * YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
 * OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.
 *
 * Refer to the MapForce Documentation for further details.
 * http://www.altova.com/mapforce
 */


package com.altova.db;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.sql.ResultSet;

public class ResultSetWrapper 
{
private final static Logger log =  LogManager.getLogger();
	private Catalog catalog;
	public ResultSet resultset;
	public Statement statement;
	
	public ResultSetWrapper(Catalog catalog, ResultSet resultset, Statement statement)
	{
		this.catalog = catalog;
		this.resultset = resultset;
		this.statement = statement;
		catalog.allocateConnection();
	}
	
	public void close() throws Exception
	{
		if( resultset != null )
		{
			resultset.close();
			resultset = null;
		}
		if( statement != null )
		{
			statement.destroy();
			// statement = null;
			catalog.freeConnection();
		}
	}
	
	public ResultSet getResultSet() { return resultset; }
}
