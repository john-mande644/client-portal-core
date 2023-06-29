package com.owd.core.payment;





public interface PaymentProcessor

{



	public void setLogin(String login, String password);

	public void process(FinancialTransaction transaction, boolean testingMode ) throws Exception;



}
