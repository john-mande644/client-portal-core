package com.owd.web.api;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Client;
import com.owd.hibernate.HibernateSession;
import org.hibernate.criterion.Restrictions;
import org.w3c.dom.Element;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class InvoiceDataRequest implements APIRequestHandler

{
private final static Logger log =  LogManager.getLogger();

    //root node name

    public static final String kRootTag = "OWD_INVOICE_DATA_REQUEST";

    //root node attributes

    public static final String kType = "type";
    public static final String kGroupPrefix = "group_prefix";
    public static final String kIDsAfter = "ids_after";

    public static final String kStartDate = "startDate";
    public static final String kEndDate = "endDate";

    protected static DateFormat df = new SimpleDateFormat("yyyyMMdd");


    public static List<String> invoiceTypes;

    public static final String kEnumTypeAll="ALL";
    public static final String kEnumTypePickPack="PICKPACK";
    public static final String kEnumTypeShipping="SHIP";
    public static final String kEnumTypeReturns="RETURN";
    public static final String kEnumTypeReceives="RECEIVE";
    public static final String kEnumTypeStorage="STORAGE";

    public static String kEnumTypesString = "";

    static {
        invoiceTypes = new ArrayList<String>();
        invoiceTypes.add(kEnumTypeAll);
        invoiceTypes.add(kEnumTypePickPack);
        invoiceTypes.add(kEnumTypeShipping);
        invoiceTypes.add(kEnumTypeReturns);
        invoiceTypes.add(kEnumTypeReceives);
        invoiceTypes.add(kEnumTypeStorage);

        int index = 1;
        for(String typeck:invoiceTypes)
        {
           if(index==1)
           {
               kEnumTypesString = kEnumTypesString+typeck;
           }     else
               if(index!= invoiceTypes.size())
               {
                   kEnumTypesString = kEnumTypesString+", "+typeck;

               }   else
               {
                   kEnumTypesString = kEnumTypesString+" or "+typeck;

               }
            index++;
        }

    }

    @Trace
    public APIResponse handle(Client client, Element root, boolean testing, double api_version) throws Exception

    {

       // if(client.client_id.equals("489"))
     //   {
        InvoiceDataResponse response = new InvoiceDataResponse(api_version);

        //////log.debug("in InventoryCountRequest Handler");

        String type = root.getAttribute(kType).trim();
        String groupPrefix = root.getAttribute(kGroupPrefix);
            if(groupPrefix==null)
            {
                groupPrefix="";

            }else
            {
                groupPrefix = groupPrefix.trim();
            }
        String startDate =  root.getAttribute(kStartDate).trim();
        String endDate =  root.getAttribute(kEndDate).trim();
        Date javaStartDate = null;
        try {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(df.parse(startDate));
            javaStartDate = new java.util.Date(gc.getTime().getTime());
        } catch (ParseException num) {
            throw new APIContentException("The attribute 'startDate' requires passing a value that can be converted to date. Value should be in YYYYMMDD format.");
        }
        Date javaEndDate = null;
        try {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(df.parse(endDate));
            javaEndDate = new java.util.Date(gc.getTime().getTime());
        } catch (ParseException num) {
            throw new APIContentException("The attribute 'endDate' requires passing a value that can be converted to date. Value should be in YYYYMMDD format.");
        }

        if(javaEndDate.getTime()<javaStartDate.getTime())
        {
            throw new APIContentException("The attribute 'endDate' must have a date value greater or equal to the 'startDate' attribute value. Values should be in YYYYMMDD format.");
        }


        int ids_after=-1;
            String idsAfterStr = root.getAttribute(kIDsAfter);
            if(idsAfterStr==null)
            {
                idsAfterStr="0";

            }else
            {
                idsAfterStr = idsAfterStr.trim();
                if(idsAfterStr.length()<1)
                {
                    idsAfterStr="0";
                }
            }
        try
        {
            ids_after = Integer.parseInt(idsAfterStr);

        }   catch(Exception ex){
            throw new APIContentException("ids_after value must represent a numeric integer >=0");

        }
            if(ids_after<0)
            {
                throw new APIContentException("ids_after value must represent a numeric integer >=0");
            }


        List<String> reqInvoiceTypes = new ArrayList<String>();

        type = type.toUpperCase();
        log.debug("type="+type);


        for(String typeck:invoiceTypes)
        {

            if (typeck.equalsIgnoreCase(type) || kEnumTypeAll.equalsIgnoreCase(type))
            {


                    log.debug("adding typeck:"+typeck);

                    reqInvoiceTypes.add(typeck);

            }
        }

        if(reqInvoiceTypes.size()<1)
        {

            throw new APIContentException("The attribute 'type' must have a value. The value must be one of "+kEnumTypesString+".");
        }

            if(ids_after>0 && type.equalsIgnoreCase(kEnumTypeAll))
            {
                throw new APIContentException("ids_after value must be omitted for an 'ALL' invoice data type. Choose a specific invoice type to use the ids_after attribute.");

            }
        response.setParams(reqInvoiceTypes, javaStartDate, javaEndDate,groupPrefix,ids_after,Integer.decode(client.client_id));

        return response;
       // }else
        //{
        //    throw new APIContentException("This API is only available on an approved basis. Please contact casetracker@owd.com for deails.");
      //  }

    }



}
