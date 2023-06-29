package com.owd.web.api;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.managers.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public  class InvActivitiesReportResp extends ReportResponse
{
private final static Logger log =  LogManager.getLogger();


//XML RETURN name
	public static final String kInventoryActivityTag 	= "INVENTORY_ACTIVITY";
//XML RETURN attributes
	public static final String kAttActivitySKU = "sku";
	public static final String kAttActivityReceived = "received";
	public static final String kAttActivityAdjusted = "adjustments";
	public static final String kAttActivityReturned = "returns";
	public static final String kAttActivityShipped = "shipped";
	public static final String kAttActivityCurrent = "current";



	public static final String hdr = kAttActivitySKU+","+kAttActivityReceived+","+kAttActivityAdjusted+","
								+kAttActivityReturned+","+kAttActivityShipped+","+kAttActivityCurrent;
								

	public InvActivitiesReportResp(double api_v,String startDate, String endDate,String format,String client_fkey) throws Exception {
		super(api_v,startDate,endDate,format,client_fkey);
		
		data = getReportData();
	}

	protected String data;
	
	public String getXML(){
		StringBuffer sb = new StringBuffer();
		
		sb.append("<"+kRootTag+" ");
		sb.append(API.buildAttribute(kAttFormat,reportFormat)+" ");
		sb.append(API.buildAttribute(kAttReportType,kInventoryActivityTag)+" >");
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
		
		Connection cxn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String orderReference;
		
		try
		{
			cxn = ConnectionManager.getConnection();
			
			
			String esql = "select i.inventory_num, "
+"CASE WHEN (UPPER(ref_num) like \'ASN%\' AND item_status=\'New\') THEN ri.quantity ELSE 0 END as received, "
+"CASE WHEN (item_status = \'Returned\') THEN ri.quantity ELSE 0 END as returned, "
+"(select ISNULL(sum(quantity_actual),0) from owd_line_item l (NOLOCK) join owd_order_track t (NOLOCK) on l.order_fkey=t.order_fkey and t.is_void=0 and line_index=1  "
+"and t.ship_date >= \'"+periodStart+"\' and t.ship_date >= \'"+periodEnd+"\' "
+"where l.inventory_id = i.inventory_id ) as shipped, "
+"CASE WHEN ((UPPER(ref_num) not like \'ASN%\' OR (UPPER(ref_num)  like \'ASN%\' AND  item_status <> \'New\')) AND item_status <> \'Returned\') THEN ri.quantity ELSE 0 END as adjusted, "
+"ISNULL(case when i.is_auto_inventory=1 then 99999 else qty_on_hand end,0) as \'current\'  "
+"from owd_inventory i (NOLOCK) join owd_inventory_oh (NOLOCK) on inventory_id = inventory_fkey "
+"join owd_receive_item ri (NOLOCK) join owd_receive r (NOLOCK) on receive_id = receive_fkey and r.is_void=0 and r.created_date >= \'"+periodStart+"\' "
+"and  r.created_date <= \'"+periodEnd+"\' and is_unusable=0  "
+"on i.inventory_id = ri.inventory_id "
+"where i.client_fkey="+client_key;


			//////log.debug(esql);
			stmt = cxn.createStatement();
			
			stmt.executeQuery(esql);
			
			rs = stmt.getResultSet();
			
			while(rs.next())
			{
				if(isXML)
				{
					sb.append("<"+kInventoryActivityTag+" ");
					sb.append(API.buildAttribute(kAttActivitySKU,rs.getString(1))+" ");
					sb.append(API.buildAttribute(kAttActivityReceived,rs.getString(2))+" ");
					sb.append(API.buildAttribute(kAttActivityAdjusted,rs.getString(3))+" ");
					sb.append(API.buildAttribute(kAttActivityReturned,rs.getString(4))+" ");
					sb.append(API.buildAttribute(kAttActivityShipped,rs.getString(5))+" ");
					sb.append(API.buildAttribute(kAttActivityCurrent,rs.getString(6))+" />");
				}else{
					sb.append(rs.getString(1)+",\""+rs.getString(3)+"\",\""+rs.getString(4)+"\",\""+rs.getString(5)+"\",\""
								+rs.getString(6)+"\",\""+rs.getString(7)+"\"\n");
				}
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
			//ex.printStackTrace();
			
			throw ex;
		}
		finally
		{
			try{rs.close();}catch(Exception ex){}
			try{stmt.close();}catch(Exception ex){}
			try{cxn.close();}catch(Exception ex){}
		}
		return sb.toString();

	}

}
