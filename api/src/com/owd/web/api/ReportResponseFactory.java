package com.owd.web.api;









public class ReportResponseFactory

{

	public static ReportResponse getReportResponse(double api_v,String reportType, String startDate, String endDate,String format,String client_fkey) throws Exception

	{

	

		

		if(reportType.equals(ReportRequest.kReturnReportType))

		{

		

			return new ReturnsReportResponse(api_v,startDate,endDate,format,client_fkey);

		}else if(reportType.equals(ReportRequest.kInventoryActivityReportType))

		{

		

			return new InvActivitiesReportResp(api_v,startDate,endDate,format,client_fkey);

		}else


		{

		

			throw new APIContentException("report_type attribute value \""+reportType+"\" not recognized.");

		}

	

	}

}
