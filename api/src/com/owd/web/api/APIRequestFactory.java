package com.owd.web.api;




//import org.apache.crimson.tree.XmlDocument;





import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.w3c.dom.Document;

import javax.servlet.http.HttpServletRequest;
import java.util.Hashtable;


public class APIRequestFactory

{
private final static Logger log =  LogManager.getLogger();





	static public APIRequest getRequest(HttpServletRequest req)

	{

	

	

		return new APIRequest();

	}



	static public APIRequest getRequest(Document doc)

	{

	

	

		return new APIRequest();

	}



	static public APIRequest getRequest(Hashtable req)

	{

	

	

		return new APIRequest();

	}

	



}
