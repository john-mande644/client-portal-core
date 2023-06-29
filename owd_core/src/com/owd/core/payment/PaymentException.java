package com.owd.core.payment;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Sep 27, 2003
 * Time: 5:20:38 PM
 * To change this template use Options | File Templates.
 */
public class PaymentException  extends Exception
{
	public PaymentException()
	{
		super();
	}

	public PaymentException(String message)
	{
		super(message);
	}
	public PaymentException(String message, Throwable th)
	{
		super(message, th);

	}
}
