package com.owd.web.api;









public class OrderCreateResponse extends APIResponse

{



//XML root name

	public static final String kTagOrderCreateResponse 	= "OWD_ORDER_CREATE_RESPONSE";

	

//XML attribute names

	public static final String kAttOWDOrderID 	= "";





	private String order_id ="";

	public OrderCreateResponse(double api_v)

	{

		super(api_v);

	}

	public void setOrderID(String id)

	{

		order_id = id;

	}

	

	public String getXML()

	{

		return super.getStartTag()+"<"+kTagOrderCreateResponse+" "

				+kAttOWDOrderID+"="+order_id+" />"+super.getEndTag();

	}



}
