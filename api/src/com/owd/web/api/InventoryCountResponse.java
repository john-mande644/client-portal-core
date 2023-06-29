package com.owd.web.api;





import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibernateSession;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;



public class InventoryCountResponse extends APIResponse

{
private final static Logger log =  LogManager.getLogger();



//XML root name

	public static final String kRootTag 	= "OWD_INVENTORY_COUNT_RESPONSE";



	

//XML line item element name

	public static final String kCountTag 	= "COUNT";
    public static final String kFacilityCountTag = "FACILITY_COUNT";

//XML line item attribute names

	public static final String kAttSKU 	= "part_reference";

	public static final String kAttCount 	= "quantity_on_hand";


	public static final String kAttDescription 	= "description";
	
	public static final String kAttIsActive 	= "is_active";
	
	public static final String kAttBackordered 	= "backorder_count";
    public static final String kAttHeld 	= "on_hold_count";

    public static final String kAttPendingOrders  	= "backorder_order_count";
    public static final String kAttExpected   	= "expected_count";
    public static final String kAttPendingPOs   	= "expected_asn_count";
    public static final String kAttNextArrival   	= "expected_asn_next_date";
    public static final String kAttFacilityCode     = "facility_code";
    public static final String kAttTotalQty   = "total_quantity";


	
	

	public InventoryCountResponse(double api_v)

	{

		super(api_v);

	}


    private String responseXml;

    public void setXml(String newXml)
    {
        responseXml = newXml;
    }

    public String getXML()
    {
        return responseXml;
    }


}
