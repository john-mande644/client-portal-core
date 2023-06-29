package com.owd.web.api;





import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.managers.*;
import com.owd.core.OWDUtilities;
import com.owd.hibernate.HibernateSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;





public  class ReturnsReportResponse extends ReportResponse

{
private final static Logger log =  LogManager.getLogger();





//XML RETURN name

	public static final String kReturnTag 	= "RETURN";

//XML RETURN attributes

	public static final String kAttROWDOrderID = "owd_order_id";

	public static final String kAttRClientOrderID = "client_order_id";

	public static final String kAttRBillName = "bill_last_name";

	public static final String kAttRBillZip = "bill_zip";

	public static final String kAttRReceiveDate = "receive_date";

	public static final String kAttRNotes = "notes";



//XML CSVDATA name

	public static final String kReturnItemTag 	= "RETURNED_ITEM";

//XML CSVDATA attributes

	public static final String kAttRItemSKU = "sku";

	public static final String kAttRItemDesc = "description";

	public static final String kAttRItemReason = "return_reason";

	public static final String kAttRItemCount = "received";

	public static final String kAttRItemOK = "accepted";

	public static final String kAttRItemBad = "damaged";

	public static final String kAttRItemOpen = "opened";





	public static final String hdr = kAttROWDOrderID+","+kAttRClientOrderID+","+kAttRBillName+","

								+kAttRBillZip+","+kAttRReceiveDate+","+kAttRNotes+","+kAttRItemSKU+","+kAttRItemDesc

								+","+kAttRItemReason+","+kAttRItemCount+","+kAttRItemOK+","+kAttRItemBad+","+kAttRItemOpen;

								



	public ReturnsReportResponse(double api_v,String startDate, String endDate,String format,String client_fkey) throws Exception {

		super(api_v,startDate,endDate,format,client_fkey);

		

		data = getReportData();

	}



	protected String data;

	

	public String getXML(){

		StringBuffer sb = new StringBuffer();

		

		sb.append("<"+kRootTag+" ");

		sb.append(API.buildAttribute(kAttFormat,reportFormat)+" ");

		sb.append(API.buildAttribute(kAttReportType,ReportRequest.kReturnReportType)+" >");

		if(reportFormat.equals(ReportRequest.kXMLFormat))

		{

			sb.append(data);

		}else if(reportFormat.equals(ReportRequest.kCSVFormat))

		{

			sb.append("<"+kCSVDataTag+" ");

			sb.append(API.buildAttribute(kAttHeader,hdr)+" >");

			sb.append("<![CDATA["+data+"]]>");

			sb.append("</"+kCSVDataTag+">");

		}

		sb.append("</"+kRootTag+">");

				

		return sb.toString();

	}

	

	protected String getReportData() throws Exception

	{

	

		boolean isXML = (reportFormat.equals(ReportRequest.kXMLFormat));

		StringBuffer sb = new StringBuffer();

		String CSVPrefix = "";


		ResultSet rs = null;

		String orderReference;

		

		try

		{


			

			String esql = "select ref_num as 'owd_order_id', ISNULL(MAX(order_refnum),'') as 'client_order_id', ISNULL(MAX(bill_last_name),'') as 'bill_last_name',ISNULL(MAX(bill_zip),'') as 'bill_zip',\n" +
                    "MAX(r.created_date) as 'receive_date',ISNULL(MAX(r.notes),'') as 'notes',\n" +
                    "inventory_num as 'sku', ISNULL(MAX(description),'') as 'description',SUM(case when item_status='Returned' then quantity else 0 end) as 'received',ISNULL(MAX(return_reason),'Unknown') as 'return_reason',\n" +
                    "SUM(case when item_status='Damaged' then quantity else 0 end) as 'damaged',SUM(case when item_status='Opened' then quantity else 0 end) as 'opened'\n" +
                    "from owd_receive r (NOLOCK) join owd_receive_item (NOLOCK) \n" +
                    "\n" +
                    "\t\t\t\t\t\ton receive_id=receive_fkey and item_status in ('Returned','Damaged','Opened')\n" +
                    "\t\t\t\t\t\tjoin owd_order (NOLOCK) on ref_num = order_num\n" +
                    "\n" +
                    "\t\t\t\t\t\twhere r.is_void = 0 and r.created_date >=? and ref_num IS NOT NULL \n" +
                    "\n" +
                    "\t\t\t\t\t\tand r.created_date <= ? and r.client_fkey=? group by ref_num, receive_id,inventory_num order by ref_num,inventory_num";


             PreparedStatement ps = HibernateSession.getPreparedStatement(esql);
             ps.setString(1,periodStart);
            ps.setString(2,periodEnd);
            ps.setInt(3,Integer.parseInt(client_key));
			//////log.debug(esql);

			rs = ps.executeQuery();

			
            String currOrder="";
			while(rs.next())

			{

				

                if(currOrder.equals(rs.getString(1)) )
                {
                    if(isXML)

                    {

                        sb.append("<"+kReturnItemTag+" ");

                        sb.append(API.buildAttribute(kAttRItemSKU,rs.getString(7))+" ");

                        sb.append(API.buildAttribute(kAttRItemDesc,rs.getString(8))+" ");

                        sb.append(API.buildAttribute(kAttRItemReason,rs.getString(10))+" ");

                        sb.append(API.buildAttribute(kAttRItemCount,""+rs.getString(9))+" ");

                        sb.append(API.buildAttribute(kAttRItemOK,""+(rs.getInt(9)+rs.getInt(12)+rs.getInt(11)))+" ");

                        sb.append(API.buildAttribute(kAttRItemBad,""+(-1*rs.getInt(11)))+" ");

                        sb.append(API.buildAttribute(kAttRItemOpen,""+(-1*rs.getInt(12)))+" />");

                    }else

                    {

                        sb.append(CSVPrefix+"\""+rs.getString(7)+"\",\""+rs.getString(8)+"\",\""+rs.getString(10)+"\","+rs.getInt(9)+","+(rs.getInt(9)+rs.getInt(11)+rs.getInt(12))

                                +","+(-1*rs.getInt(11))+","+(-1*rs.getInt(12))+"\n");

                    }
                }   else
                {
                    if(!(currOrder.equals("")) && isXML)
                    {
                        sb.append("</"+kReturnTag+">");

                    }
                    currOrder=rs.getString(1);
				if(isXML)

				{

					sb.append("<"+kReturnTag+" ");

					sb.append(API.buildAttribute(kAttROWDOrderID,rs.getString(1))+" ");

					sb.append(API.buildAttribute(kAttRClientOrderID,rs.getString(2))+" ");

					sb.append(API.buildAttribute(kAttRBillName,rs.getString(3))+" ");

					sb.append(API.buildAttribute(kAttRBillZip,rs.getString(4))+" ");

					sb.append(API.buildAttribute(kAttRReceiveDate,OWDUtilities.getYYYYMMDDFromSQLDate(rs.getString(5)))+" ");

					sb.append(API.buildAttribute(kAttRNotes,rs.getString(6))+" >");

				}else{

					CSVPrefix = rs.getString(1)+",\""+rs.getString(2)+"\",\""+rs.getString(3)+"\",\""+rs.getString(4)+"\","

								+OWDUtilities.getYYYYMMDDFromSQLDate(rs.getString(5))+",\""+rs.getString(6)+"\",";

				}



						if(isXML)

						{

							sb.append("<"+kReturnItemTag+" ");

							sb.append(API.buildAttribute(kAttRItemSKU,rs.getString(7))+" ");

							sb.append(API.buildAttribute(kAttRItemDesc,rs.getString(8))+" ");

							sb.append(API.buildAttribute(kAttRItemReason,rs.getString(10))+" ");

							sb.append(API.buildAttribute(kAttRItemCount,""+rs.getString(9))+" ");

							sb.append(API.buildAttribute(kAttRItemOK,""+(rs.getInt(9)+rs.getInt(12)+rs.getInt(11)))+" ");

							sb.append(API.buildAttribute(kAttRItemBad,""+(-1*rs.getInt(11)))+" ");

							sb.append(API.buildAttribute(kAttRItemOpen,""+(-1*rs.getInt(12)))+" />");

						}else

						{

							sb.append(CSVPrefix+"\""+rs.getString(7)+"\",\""+rs.getString(8)+"\",\""+rs.getString(10)+"\","+rs.getInt(9)+","+(rs.getInt(9)+rs.getInt(11)+rs.getInt(12))

										+","+(-1*rs.getInt(11))+","+(-1*rs.getInt(12))+"\n");

						}

					}
            }

				


				

				if(isXML)

				{

					sb.append("</"+kReturnTag+">");

				}




			

			

		}catch(Throwable th)

		{

			Exception ex;

			

			if ( th instanceof Exception )

			{

				ex = (Exception)th;

			}else{

				ex = new Exception(th.toString());

			}

			//////log.debug("in ReturnsReportRequest getReportData ex");

			//ex.printStackTrace();

			

			throw ex;

		}

		finally

		{

			try{rs.close();}catch(Exception ex){}

		}

		



		return sb.toString();



	}



}
