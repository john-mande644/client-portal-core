package com.owd.web.api;









public  abstract class ReportResponse extends APIResponse

{



//XML root name

	public static final String kRootTag 	= "OWD_REPORT_RESPONSE";



//XML root attributes

	public static final String kAttReportType = "report_type";

	public static final String kAttFormat = "format";



//XML CSVDATA name

	public static final String kCSVDataTag 	= "CSVDATA";

//XML CSVDATA attributes

	public static final String kAttHeader = "header";



	

	public String periodStart;

	public String periodEnd;

	public String reportFormat;

	public String client_key;

	

	public ReportResponse(double api_v,String startDate, String endDate,String format, String client_fkey){

		super(api_v);

		periodStart = startDate;

		periodEnd = endDate;

		reportFormat = format;

		client_key = client_fkey;

	}



	public abstract String getXML();



}
