package com.owd.web.api;









public class ReportRequestFactory

{

	public static ReportResponse getReportResponse(double api_v,String reportType, String startDate, String endDate,String format)

	{

	
            ReportResponse rrr = null;
		

		if(reportType.equals(ReportRequest.kReturnReportType))

		{
                        try
                        {
                             rrr = new ReturnsReportResponse(api_v,startDate,endDate,format, "");
                            
                        }
                        catch(Exception e)
                        {
                            //e.printStackTrace();
                        }

			

		} else  if(reportType.equals(ReportRequest.kReturnReportType))

		{
                        try
                        {
                             rrr = new InvActivitiesReportResp(api_v,startDate,endDate,format, "");

                        }
                        catch(Exception e)
                        {
                            //e.printStackTrace();
                        }



		}

            return rrr ;

	}

}
