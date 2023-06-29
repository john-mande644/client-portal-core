package com.owd.web.api;





import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.hibernate.HibernateSession;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;


public class InvoiceDataResponse extends APIResponse

{
private final static Logger log =  LogManager.getLogger();



//XML root name

	public static final String kRootTag 	= "OWD_INVOICE_DATA_RESPONSE";


    protected static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    protected static DateFormat dfx = new SimpleDateFormat("yyyyMMdd");


    List<String> types=new ArrayList<String>();
    Date starter = null;
    Date ender = null;
    String grouper=null;
    int id= -1;
    int client_id=0;


	public InvoiceDataResponse(double api_v)

	{

		super(api_v);

	}

	public void setParams(List<String> billTypes, Date startDate, Date endDate, String groupPrefix, int id_after, int client_idx)

	{
        types=billTypes;
        starter=startDate;
        ender=endDate;
        grouper = groupPrefix;
        id=id_after;
        client_id=client_idx;

	}

	

	public String getXML()

	{
        StringBuffer sb = new StringBuffer();

        try{

		

		sb.append("<"+kRootTag+">");

		 if(types.contains(InvoiceDataRequest.kEnumTypePickPack))
         {
             PreparedStatement ps = HibernateSession.getPreparedStatement("select p.*,o.order_num,o.order_refnum from sp_bill_pickpack p join owd_order o (NOLOCK) on order_id=oid and p.[client id]=? where p.shipped_on>=? and p.shipped_on<=? and id>?  "+(grouper.length()>0?" and p.group_name like ?":"")+" order by p.group_name, p.shipped_on,oid ");

             ps.setInt(1,client_id);
             ps.setString(2,df.format(starter));
             ps.setString(3,df.format(ender));
             ps.setInt(4,id);
             if(grouper.length()>0)
             {
                ps.setString(5,grouper+"%");
             }
             String currentGroup=null;

             ResultSet rs = ps.executeQuery();

             sb.append("<PICKPACKS>");

             while(rs.next())
             {

               sb.append("<PICKPACK "
                       +API.buildAttribute("id",rs.getString("id"))+" "
                       +API.buildAttribute("group",rs.getString("group_name"))+" "
                       +API.buildAttribute("date",dfx.format(rs.getDate("shipped_on")))+" "
                       +API.buildAttribute("clientOrderRef",rs.getString("order_refnum"))+" "
                       +API.buildAttribute("owdOrderRef",rs.getString("order_num"))+" "
                       +API.buildAttribute("orderType",rs.getString("Order Type"))+" "
                       +API.buildAttribute("orderFee",rs.getString("Order Fees"))+" "
                       +API.buildAttribute("packs",rs.getString("packages"))+" "
                       +API.buildAttribute("packagingFee",""+rs.getFloat("packagecost"))+" "
                       +API.buildAttribute("picks",rs.getString("Additional Picks"))+" "
                       +API.buildAttribute("picksFee",rs.getString("Additional Pick Fees"))+" "
                       +API.buildAttribute("inserts",rs.getString("Inserts"))+" "
                       +API.buildAttribute("insertsFee",rs.getString("Insert Fees"))+" "
                       +API.buildAttribute("intlFee",rs.getString("International Fees"))+" "
                       +(api_vers>=2.0?API.buildAttribute("facility_code",rs.getString("facility_code")):"")+" "
                       +(api_vers>=2.1?API.buildAttribute("change_order_fee",""+rs.getString("Order Change Fees")):"")+" "

                       +(api_vers>=2.1?API.buildAttribute("lot_items",""+rs.getString("Lot Items")):"0")+" "
                       +(api_vers>=2.7?API.buildAttribute("bulky_pick_units",""+rs.getString("bulky_pick_units")):"")+" "
                       +(api_vers>=2.7?API.buildAttribute("bulky_pick_fees",""+rs.getString("bulky_pick_fees")):"")+" "
                       +(api_vers>=2.7?API.buildAttribute("bulky_pack_units",""+rs.getString("bulky_pack_units")):"")+" "
                       +(api_vers>=2.7?API.buildAttribute("bulky_pack_fees",""+rs.getString("bulky_pack_fees")):"")+" "
                       +API.buildAttribute("colorFee",rs.getString("colorprint"))+" />");

             }
             sb.append("</PICKPACKS>");

             rs.close();
             ps.close();
         }

        if(types.contains(InvoiceDataRequest.kEnumTypeStorage))
        {
            PreparedStatement ps = HibernateSession.getPreparedStatement("select * from sp_bill_storage p  where p.[Client ID]=? and recorded_date>=? and recorded_date<=? and id>? "+(grouper.length()>0?" and  group_name like ?":"")+"order by group_name, inventory_num ");

            ps.setInt(1,client_id);
            ps.setString(2,df.format(starter));
            ps.setString(3,df.format(ender));
            ps.setInt(4,id);

            if(grouper.length()>0)
            {
                ps.setString(5,grouper+"%");
            }

            ResultSet rs = ps.executeQuery();

            String currentGroup=null;

            sb.append("<STORAGE>");

            while(rs.next())
            {

                sb.append("<SPACE "
                        +API.buildAttribute("id",rs.getString("id"))+" "
                        +API.buildAttribute("recorded_date",dfx.format(rs.getDate("recorded_date")))+" "
                        +API.buildAttribute("group",rs.getString("group_name"))+" "
                        +API.buildAttribute("sku",rs.getString("inventory_num"))+" "
                        +API.buildAttribute("title",rs.getString("description"))+" "
                        +API.buildAttribute("cubicFeet",""+rs.getFloat("Cubic Feet Used"))+" "
                        +(api_vers>=2.0?API.buildAttribute("facility_code",rs.getString("default_facility_code")):"")+" "
                        +API.buildAttribute("cubicFee",""+rs.getFloat("Cost"))+" />");

            }
            sb.append("</STORAGE>");

            rs.close();
            ps.close();
        }

        if(types.contains(InvoiceDataRequest.kEnumTypeReceives))
        {
            PreparedStatement ps = HibernateSession.getPreparedStatement("select * from sp_bill_receiving p where p.[client id]=? and received>=? and received<=? and id>?"+(grouper.length()>0?" and group_name like ?":"")+" order by group_name, received ");
            String currentGroup=null;

            ps.setInt(1,client_id);
            ps.setString(2,df.format(starter));
            ps.setString(3,df.format(ender));
            ps.setInt(4,id);

            if(grouper.length()>0)
            {
                ps.setString(5,grouper+"%");
            }

            ResultSet rs = ps.executeQuery();


            sb.append("<RECEIVING>");

            while(rs.next())
            {

                sb.append("<RECEIVE "
                        +API.buildAttribute("id",rs.getString("id"))+" "
                        +API.buildAttribute("group",rs.getString("group_name"))+" "
                        +API.buildAttribute("date",dfx.format(rs.getDate("received")))+" "
                        +API.buildAttribute("asnId",rs.getString("asnid"))+" "
                        +API.buildAttribute("rcvId",rs.getString("rcvid"))+" "
                        +API.buildAttribute("asnPo",rs.getString("client_po"))+" "
                        +API.buildAttribute("asnRef",rs.getString("client_ref"))+" "
                        +API.buildAttribute("billedHours",""+rs.getFloat("billedhours"))+" "
                        +(api_vers>=2.0?API.buildAttribute("facility_code",""+rs.getString("location")):"")+" "
                        +(api_vers>=2.1?API.buildAttribute("blind_asn_fee",""+rs.getString("blindasnfee")):"")+" "
                        +(api_vers>=2.7?API.buildAttribute("receive_units",""+ (rs.getString("receive_units")== null ?"0":rs.getString("receive_units"))):"")+" "
                        +(api_vers>=2.7?API.buildAttribute("unitsFee",""+ (rs.getString("billedUnits")==null?"0.0":rs.getString("billedUnits"))):"")+" "
                        +(api_vers>=2.7?API.buildAttribute("newSkus",""+ (rs.getString("newSkus")==null?"0":rs.getString("newSkus"))):"")+" "
                        +(api_vers>=2.7?API.buildAttribute("newSkuFees",""+ (rs.getString("newSkuFees")==null?"0.0":rs.getString("newSkuFees"))):"")+" "
                        +API.buildAttribute("receiveFee",""+rs.getFloat("billedamount"))+" />");

            }
            sb.append("</RECEIVING>");

            rs.close();
            ps.close();

        }

        if(types.contains(InvoiceDataRequest.kEnumTypeReturns))
        {
            PreparedStatement ps = HibernateSession.getPreparedStatement("select * from sp_bill_returns p where p.[client id]=? and returned>=? and returned<=? and id>? "+(grouper.length()>0?" and groupname like ?":"")+" order by groupname, returned ");
            String currentGroup=null;

            ps.setInt(1,client_id);
            ps.setString(2,df.format(starter));
            ps.setString(3,df.format(ender));
            ps.setInt(4,id);

            if(grouper.length()>0)
            {
                ps.setString(5,grouper+"%");
            }

            ResultSet rs = ps.executeQuery();

            sb.append("<RETURNS>");

            while(rs.next())
            {

                sb.append("<RETURN "
                        +API.buildAttribute("id",rs.getString("id"))+" "
                        +API.buildAttribute("group",rs.getString("groupname"))+" "
                        +API.buildAttribute("date",dfx.format(rs.getDate("returned")))+" "
                        +API.buildAttribute("clientOrderRef",rs.getString("order_refnum"))+" "
                        +API.buildAttribute("owdOrderRef",rs.getString("order_num"))+" "
                        +(api_vers>=2.0?API.buildAttribute("facility_code",""+rs.getString("location")):"")+" "
                        +(api_vers>=2.7?API.buildAttribute("return_units",""+ (rs.getString("return_units")==null?"0":rs.getString("return_units"))):"")+" "

                        +API.buildAttribute("returnFee",rs.getString("return_fee"))+" />");

            }
            sb.append("</RETURNS>");

            rs.close();
            ps.close();

        }

        if(types.contains(InvoiceDataRequest.kEnumTypeShipping))
        {
            PreparedStatement ps = HibernateSession.getPreparedStatement("select * from sp_bill_shipping p where p.[client id]=? and recorded_date>=? and recorded_date<=? and id>? "+(grouper.length()>0?" and group_name like ?":"")+" order by group_name, recorded_date ");
            String currentGroup=null;

            ps.setInt(1,client_id);
            ps.setString(2,df.format(starter));
            ps.setString(3,df.format(ender));
            ps.setInt(4,id);

            if(grouper.length()>0)
            {
                ps.setString(5,grouper+"%");
            }

            ResultSet rs = ps.executeQuery();

            sb.append("<SHIPPING>");

            while(rs.next())
            {

                if(client_id == 489 && OWDUtilities.roundFloat(rs.getFloat("amount")) == 0.08f){
                    sb.append("<SHIPEVENT "
                            +API.buildAttribute("id",rs.getString("id"))+" "
                            +API.buildAttribute("group",rs.getString("group_name"))+" "
                            +API.buildAttribute("date",dfx.format(rs.getDate("recorded_date")))+" "
                            +API.buildAttribute("clientOrderRef",rs.getString("order_refnum"))+" "
                            +API.buildAttribute("owdOrderRef",rs.getString("order_num"))+" "
                            +API.buildAttribute("amount",""+ OWDUtilities.roundFloat(rs.getFloat("amount")))+" "
                            +API.buildAttribute("type","Carrier Management")+ " "
                            +API.buildAttribute("trackingCode",rs.getString("tracking"))+" />");
                }else{
                    sb.append("<SHIPEVENT "
                            +API.buildAttribute("id",rs.getString("id"))+" "
                            +API.buildAttribute("group",rs.getString("group_name"))+" "
                            +API.buildAttribute("date",dfx.format(rs.getDate("recorded_date")))+" "
                            +API.buildAttribute("clientOrderRef",rs.getString("order_refnum"))+" "
                            +API.buildAttribute("owdOrderRef",rs.getString("order_num"))+" "
                            +API.buildAttribute("amount",""+ OWDUtilities.roundFloat(rs.getFloat("amount")))+" "
                            +API.buildAttribute("type",rs.getString("activity"))+" "
                            +API.buildAttribute("trackingCode",rs.getString("tracking"))+" />");
                }


            }
            sb.append("</SHIPPING>");

            rs.close();
            ps.close();

        }

            log.debug(sb);
            sb.append("</"+kRootTag+">");


        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
		return sb.toString();


	}





}
