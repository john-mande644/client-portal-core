package com.owd.web.api;




import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Client;
import org.w3c.dom.Element;



public interface APIRequestHandler

{





	public APIResponse handle(Client client, Element root, boolean testing, double api_version)  throws Exception;



}
