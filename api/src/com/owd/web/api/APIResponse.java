package com.owd.web.api;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.commons.lang.StringEscapeUtils;


public class APIResponse

{
private final static Logger log =  LogManager.getLogger();



//XML root name

	public static final String kTagAPIResponse 	= "OWD_API_RESPONSE";

	

//XML attribute names

	public static final String kAttResults 	= "results";

	public static final String kAttErrorType 		= "error_type";

	public static final String kAttErrorResponse 		= "error_response";



	public static final String kErrFormat = "FORMAT";

	public static final String kErrRule = "RULE";

	public static final String kErrProcess = "PROCESS";

	public static final String kErrContent = "CONTENT";

	

	public static final String kStatusCodeSuccess = "SUCCESS";

	public static final String kStatusCodeError = "ERROR";



	protected double api_vers = 1.0;

	

	public String results = kStatusCodeSuccess;

	protected String error_type = "";

	protected String error_response = "Unspecified error";

	

	private APIRequest request = null;

	

	public APIResponse(double api_v)

	{

		super();

		api_vers = api_v;

	}

	

	public void setError(String errorType, String errorResponse)

	{

		results = kStatusCodeError;

		error_type = errorType;

		if(errorResponse != null)

			error_response = errorResponse;

	}	

	

	

	public String getStartTag()

	{


		StringBuffer sb = new StringBuffer();

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><"+kTagAPIResponse);

		sb.append(" "+kAttResults+"=\""+results+"\"");

		if(results.equals("ERROR"))

		{

			sb.append(" "+kAttErrorType+"=\""+error_type+"\"");

			sb.append(" "+kAttErrorResponse+"=\""+ StringEscapeUtils.escapeXml(error_response)+"\"");

		}

		sb.append(">");

		
         try
        {
		return new String(sb.toString().getBytes("UTF-8"),"UTF-8");
        }catch(Exception ex)
         {
             ex.printStackTrace();
         }
        return "";

	}



	public String getNode()

	{

		if(results.equals(kStatusCodeError))

		{

			return getStartTag()+getEndTag();

		}else

		{

			return getStartTag()+getXML()+getEndTag();

		}

		

	}

	

		

	public String getXML()

	{

		return "";

	}

	

	public String getEndTag()

	{

		return "</"+kTagAPIResponse+">";

	}



}
