package com.owd.web.api;



import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.*;
import com.owd.core.business.order.*;
import com.owd.core.managers.*;
import com.owd.core.OWDUtilities;
import org.w3c.dom.Element;





public class ReportRequest implements APIRequestHandler

{
private final static Logger log =  LogManager.getLogger();

	//root node name

	public static final String kRootTag = "OWD_REPORT_REQUEST";



	//root node attributes

	public static final String kStartDate = "start_date";

	public static final String kEndDate = "end_date";

	public static final String kFormat = "format";

	public static final String kReportType = "report_type";

	

	public static final String kXMLFormat = "XML";

	public static final String kCSVFormat = "CSV";



	public static final String kReturnReportType = "RETURNS";
	public static final String kInventoryActivityReportType = "INVENTORY_ACTIVITIES";


    @Trace
	public APIResponse handle(Client client, Element root,boolean testing, double api_version) throws Exception

	{

		//////log.debug("in ReportRequest Handler");



		String startDate = root.getAttribute(kStartDate).trim();

		String endDate = root.getAttribute(kEndDate).trim();

		String format = root.getAttribute(kFormat).trim();

		String reportType = root.getAttribute(kReportType).trim();

		

		if(format.equals(kXMLFormat) || format.equals(kCSVFormat))

		{

			if(reportType.equals(kReturnReportType) || reportType.equals(kInventoryActivityReportType))

			{

				startDate = OWDUtilities.getSQLDateFromYYYYMMDD(startDate);

				if(startDate.equals("NULL"))

					throw new APIContentException("start_date value is not a valid date.");

					

				endDate = OWDUtilities.getSQLDateFromYYYYMMDD(endDate);

				if(endDate.equals("NULL"))

					throw new APIContentException("start_date value is not a valid date.");

					

				startDate = startDate.replace('\'',' ').trim()+" 00:00:00";

				endDate = endDate.replace('\'',' ').trim()+" 23:59:59";

				

			}else

			{

				throw new APIContentException("report_type value \""+reportType+"\" not recognized.");

			}

		}else

		{

			throw new APIContentException("format value \""+format+"\" not recognized.");

		}

		

		ReportResponse response = ReportResponseFactory.getReportResponse(api_version,reportType,startDate,endDate,format,client.client_id);

		

		return response;

	}



}
