package com.owd.extranet.servlet;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;




public class OrderFinder

{
private final static Logger log =  LogManager.getLogger();
		String[] tableColumnNames = {"","Customer", "Reference","Status","At&nbsp;Warehouse","Shipped","Total","Notes","Comments"};

		String[] tableColumnDefs = {
				"order_id",
				"CASE WHEN (ISNULL(bill_company_name,\".\")=\".\" OR ISNULL(bill_company_name,\"\")=\"\") THEN (bill_first_name+\" \"+bill_last_name) ELSE bill_company_name END",
				"order_refnum",
				"CASE WHEN is_backorder = 1 and o.is_void=0 THEN (\"BO&nbsp;(Level&nbsp;\"+CONVERT(varchar,backorder_level+1)+\")\") ELSE order_status END",
				"ISNULL(CONVERT(varchar,post_date,107),\"<B>Held</B>\")",
				"ISNULL(CONVERT(varchar,o.shipped_on,107),\"<B>Not Shipped</B>\")",
				"order_total",
				"whse_notes",
				"ISNULL(e.message,'')"};

		String tableFromStmt = "FROM owd_order o (NOLOCK) JOIN owd_order_ship_info os ON o.order_id = os.order_fkey OUTER APPLY (SELECT TOP 1 message FROM order_events oe WITH (NOLOCK) WHERE oe.order_fkey = o.order_id AND oe.event_type = 1 ORDER BY event_stamp DESC) e";

		String[] tableLinkStarts = {ExtranetServlet.kParamAdminAction+"="+ExtranetServlet.kParamChangeMgrAction +"&"+ ExtranetServlet.kParamChangeMgrTo+"="+ ExtranetManager.kOrdersMgrName+"&"+OrdersManager.kParamOrderMgrAction+"="+OrdersManager.kParamOrderEditAction+"&"+OrdersManager.kparamOrderID+"=","","","","","","","",""};

		String[] tableLinkEnds = {"Edit</A>","","","","","","","",""};

		int[] tableLinkMids = {1,0,0,0,0,0,0,0,0};
		
	int[] tableRenders = {WebTable.kRenderString,WebTable.kRenderString,WebTable.kRenderString,WebTable.kRenderString,WebTable.kRenderString,WebTable.kRenderString,WebTable.kRenderMoney,WebTable.kRenderString,WebTable.kRenderString};

	

	public OrderFinder()

	{

	

	}

	

	public static final String kBlank = "";

	

	private String ref = null;

	private String track = null;

	private String OWDRef = null;

	private String customerName = null;

	private String coupon = null;

	private String backorderSearch = null;

	private String onHoldSearch = null;

	private String shippedSearch = null;

	private String receivedOnYear = null;

	private String receivedOnMonth = null;

	private String receivedOnDay = null;

	private String shippedOnYear = null;

	private String shippedOnMonth = null;

	private String shippedOnDay = null;

	private String receivedToYear = null;

	private String receivedToMonth = null;

	private String receivedToDay = null;

	private String shippedToYear = null;

	private String shippedToMonth = null;

	private String shippedToDay = null;

	private String nameType = null;
	
	private String POValue = null;
	private String Group = null;
    private String locationCode = null;

	

	private String sku = null;

	private int skuType = 1;
	
	
	private int POValueType = 1;
	private int GroupType = 1;
		
		private boolean voidsIncluded = false;

	private int refType = 1;

	private int trackType = 1;

	private int OWDRefType = 1;

	private int customerType = 2;

	private int onHoldType = 1;

	private int shippedType = 1;

	private int backorderType = 1;

	private int couponType = 1;


    private int locationType = 0;

	

	private static final int kIsType = 1;

	private static final int kInType = 2;

	private static final int kGreaterThanType = 3;

	private static final int kLessThanType = 4;

	

	

	public static final String kSubmitType = "searchorders";

	public static final String kRefSearchFlag = "doref";

	public static final String kRefSearchValue = "refvalue";

	public static final String kRefSearchType = "reftype";


	public static final String kPOValueSearchFlag = "doPOValue";

	public static final String kPOValueSearchValue = "POValuevalue";

	public static final String kPOValueSearchType = "POValuetype";

	public static final String kGroupSearchFlag = "doGroup";

	public static final String kGroupSearchValue = "Groupvalue";

	public static final String kGroupSearchType = "Grouptype";
	

	public static final String kOWDRefSearchFlag = "doOWDref";

	public static final String kOWDRefSearchValue = "OWDrefvalue";

	public static final String kOWDRefSearchType = "OWDreftype";

	

	public static final String kTrackSearchFlag = "dotrack";

	public static final String kTrackSearchValue = "trackvalue";

	public static final String kTrackSearchType = "tracktype";



	public static final String kCustomerSearchFlag = "doname";

	public static final String kCustomerSearchValue = "namevalue";

	public static final String kCustomerSearchType = "nametype";

	

	public static final String kNameSearchCriteria = "namecrit";

	public static final String kNameSearchBillLast = "critblast";

    public static final String kNameSearchBillName = "critbname";

    public static final String kNameSearchBillFirst = "critbfirst";

	public static final String kNameSearchCompany = "critcorp";

	public static final String kNameSearchEmail = "critemail";

	public static final String kNameSearchState = "critstate";


	public static final String kSKUSearchFlag = "dosku";

	public static final String kSKUSearchValue = "skuvalue";

	public static final String kSKUSearchType = "skutype";	

	
		public static final String kVoidSearchFlag = "dovoid";
		

	public static final String kHoldSearchFlag = "dohold";

	public static final String kHoldSearchValue = "holdvalue";

	public static final String kHoldSearchType = "holdtype";



	public static final String kBackorderSearchFlag = "doback";

	public static final String kBackorderSearchValue = "backvalue";

	public static final String kBackorderSearchType = "backtype";



	public static final String kShippedSearchFlag = "doshipped";

	public static final String kShippedSearchValue = "shippedvalue";

	public static final String kShippedSearchType = "shippedtype";



	public static final String kCouponSearchFlag = "docoupon";

	public static final String kCouponSearchValue = "couponvalue";

	public static final String kCouponSearchType = "coupontype";

		

	public static final String kShippedOnSearchFlag = "doshippedon";

	public static final String kShippedOnYearValue = "shipyear";

	public static final String kShippedOnMonthValue = "shipmonth";	

	public static final String kShippedOnDayValue = "shipday";	



	public static final String kShippedToYearValue = "ship2year";

	public static final String kShippedToMonthValue = "ship2month";	

	public static final String kShippedToDayValue = "ship2day";	

	

	public static final String kReceivedOnSearchFlag = "dorcvedon";

	public static final String kReceivedOnYearValue = "rcvyear";

	public static final String kReceivedOnMonthValue = "rcvmonth";	

	public static final String kReceivedOnDayValue = "rcvday";	



	public static final String kReceivedToYearValue = "rcv2year";

	public static final String kReceivedToMonthValue = "rcv2month";	

	public static final String kReceivedToDayValue = "rcv2day";



    public static final String kLocationSearchFlag = "dolocation";

    public static final String kLocationSearchValue = "locationvalue";



    public int getRefType()

	{

		return refType;

	}



	public int getTrackType()

	{

		return trackType;

	}

    public String getLocation()
    {
        if(locationCode == null)
        {
            locationCode = "DC1";
        }
        return locationCode;
    }

		

	public static OrderFinder parseRequest(HttpServletRequest req, HttpServletResponse resp)

	{

		OrderFinder finder = new OrderFinder();

		
	if(ExtranetServlet.getIntegerParam(req,kVoidSearchFlag,0)==1)

		{

		//voidSearch

			finder.setVoidsIncluded("1",1);

		

		}
		
		if(ExtranetServlet.getIntegerParam(req,kRefSearchFlag,0)==1)

		{

		//refSearch

			finder.setRef(ExtranetServlet.getStringParam(req,kRefSearchValue,""),ExtranetServlet.getIntegerParam(req,kRefSearchType,1));

		

		}



		if(ExtranetServlet.getIntegerParam(req,kTrackSearchFlag,0)==1)

		{

		//refSearch

			finder.setTrack(ExtranetServlet.getStringParam(req,kTrackSearchValue,""),ExtranetServlet.getIntegerParam(req,kTrackSearchType,1));

		

		}

        if(ExtranetServlet.getIntegerParam(req,kLocationSearchFlag,0)==1)

        {

            //refSearch

            finder.setLocation(ExtranetServlet.getStringParam(req,kLocationSearchValue,"DC1"),ExtranetServlet.getIntegerParam(req,kLocationSearchFlag,0));



        }



		if(ExtranetServlet.getIntegerParam(req,kOWDRefSearchFlag,0)==1)

		{

		//OWDRefSearch

			finder.setOWDRef(ExtranetServlet.getStringParam(req,kOWDRefSearchValue,""),ExtranetServlet.getIntegerParam(req,kOWDRefSearchType,1));

		

		}



		if(ExtranetServlet.getIntegerParam(req,kCustomerSearchFlag,0)==1)

		{

		//customerSearch

			finder.setCustomerName(ExtranetServlet.getStringParam(req,kCustomerSearchValue,""),ExtranetServlet.getIntegerParam(req,kCustomerSearchType,1));

			finder.setNameSearchCriteria(ExtranetServlet.getStringParam(req,kNameSearchCriteria,kNameSearchBillLast));

		}				



		if(ExtranetServlet.getIntegerParam(req,kHoldSearchFlag,0)==1)

		{

		//customerSearch

			finder.setOnHold(ExtranetServlet.getStringParam(req,kHoldSearchValue,""),ExtranetServlet.getIntegerParam(req,kHoldSearchType,1));

		

		}	



		if(ExtranetServlet.getIntegerParam(req,kCouponSearchFlag,0)==1)

		{

		//customerSearch

			finder.setCoupon(ExtranetServlet.getStringParam(req,kCouponSearchValue,""),ExtranetServlet.getIntegerParam(req,kCouponSearchType,1));

		

		}

		

		if(ExtranetServlet.getIntegerParam(req,kBackorderSearchFlag,0)==1)

		{

		//customerSearch

			finder.setBackordered(ExtranetServlet.getStringParam(req,kBackorderSearchValue,""),ExtranetServlet.getIntegerParam(req,kBackorderSearchType,1));

		

		}	



		if(ExtranetServlet.getIntegerParam(req,kSKUSearchFlag,0)==1)

		{

		//skuSearch

			finder.setSKU(ExtranetServlet.getStringParam(req,kSKUSearchValue,""),ExtranetServlet.getIntegerParam(req,kSKUSearchType,1));

		

		}	
		
		if(ExtranetServlet.getIntegerParam(req,kPOValueSearchFlag,0)==1)

		{

		//skuSearch

			finder.setPOValue(ExtranetServlet.getStringParam(req,kPOValueSearchValue,""),ExtranetServlet.getIntegerParam(req,kPOValueSearchType,1));

		

		}

		if(ExtranetServlet.getIntegerParam(req,kGroupSearchFlag,0)==1)

		{
			finder.setGroup(ExtranetServlet.getStringParam(req,kGroupSearchValue,""),ExtranetServlet.getIntegerParam(req,kGroupSearchType,1));

		}

				

		if(ExtranetServlet.getIntegerParam(req,kShippedSearchFlag,0)==1)

		{

		//customerSearch

			finder.setShipped(ExtranetServlet.getStringParam(req,kShippedSearchValue,""),ExtranetServlet.getIntegerParam(req,kShippedSearchType,1));

		

		}	



		if(ExtranetServlet.getIntegerParam(req,kShippedOnSearchFlag,0)==1)

		{

		//customerSearch

			finder.setShippedOn(	ExtranetServlet.getStringParam(req,kShippedOnYearValue,"2001"),

								ExtranetServlet.getStringParam(req,kShippedOnMonthValue,"1"),

								ExtranetServlet.getStringParam(req,kShippedOnDayValue,"1"),

								ExtranetServlet.getStringParam(req,kShippedToYearValue,"2001"),

								ExtranetServlet.getStringParam(req,kShippedToMonthValue,"1"),

								ExtranetServlet.getStringParam(req,kShippedToDayValue,"1"));

		

		}	

		

		if(ExtranetServlet.getIntegerParam(req,kReceivedOnSearchFlag,0)==1)

		{

		//customerSearch

				finder.setReceivedOn(	ExtranetServlet.getStringParam(req,kReceivedOnYearValue,"2001"),

									ExtranetServlet.getStringParam(req,kReceivedOnMonthValue,"1"),

									ExtranetServlet.getStringParam(req,kReceivedOnDayValue,"1"),

									ExtranetServlet.getStringParam(req,kReceivedToYearValue,"2001"),

									ExtranetServlet.getStringParam(req,kReceivedToMonthValue,"1"),

									ExtranetServlet.getStringParam(req,kReceivedToDayValue,"1"));

		

		}	

		

		if("Show All Orders".equals(ExtranetServlet.getStringParam(req,kSubmitType,""))||"Go to Page No.".equals(ExtranetServlet.getStringParam(req,kSubmitType,"")))

		{

		//customerSearch

			finder.setShowAll(true);

		}		

								

		return finder;

	}

	

	public void setShowAll(boolean all)

	{

		showAll = all;

	}

	
	public void setVoidsIncluded(String value, int findType)

	{

		if(value==null) return;

		

		voidsIncluded = ("1".equals(value));

		

	}


    public void setLocation(String value, int findType)

    {

        if(value==null) return;



        locationCode = value;
        locationType =findType;



    }

	public void setRef(String value, int findType)

	{

		if(value==null) return;

		

		ref = value;

		refType =findType;

	}



	public void setCoupon(String value, int findType)

	{

		if(value==null) return;

		

		coupon = value;

		couponType =findType;

	}

	

	public void setTrack(String value, int findType)

	{

		if(value==null) return;

		

		track = value;

		trackType =findType;

	}

	public void setGroup(String value, int findType){
		if(value==null)return;
		Group = value;
		GroupType = findType;
	}
	public void setPOValue(String value, int findType)

	{

		if(value==null) return;

		

		POValue = value;

		POValueType =findType;

	}
	
	public void setOWDRef(String value, int findType)

	{

		if(value==null) return;

		

		OWDRef = value;

		OWDRefType =findType;

	}



	

	public void setNameSearchCriteria(String value)

	{

		if(value==null) return;

		

		nameType = value;

	}

		

	public String getNameSearchCriteria()

	{

		if(isCustomerSearch())

			return nameType;

		else

			return kBlank;

	}
	public String getGroup()
	{
		if(isGroupSearch()){
			return Group;
		}else{
			return kBlank;
		}
	}
	public String getPOValue()

	{

		if(isPOValueSearch())

			return POValue;

		else

			return kBlank;

	}			

	public String getOWDRef()

	{

		if(isOWDRefSearch())

			return OWDRef;

		else

			return kBlank;

	}

	

	public int getOWDRefType()

	{

		return OWDRefType;

	}	



	public int getCouponType()

	{

		return couponType;

	}	



			

	public boolean isOWDRefSearch()

	{

		if(OWDRef == null)

			return false;

		else

			return true;

	}

	

	public String getRef()

	{

		if(isRefSearch())

			return ref;

		else

			return kBlank;

	}



	public String getCoupon()

	{

		if(isCouponSearch())

			return coupon;

		else

			return kBlank;

	}

	

	public String getTrack()

	{

		if(isTrackSearch())

			return track;

		else

			return kBlank;

	}



	public boolean isRefSearch()

	{

		if(ref == null)

			return false;

		else

			return true;

	}



	public boolean isCouponSearch()

	{

		if(coupon == null)

			return false;

		else

			return true;

	}


    public boolean isLocationSearch()

    {

        return (locationType == 1) ;
    }
	public boolean isTrackSearch()

	{

		if(track == null)

			return false;

		else

			return true;

	}



	public void setSKU(String value, int findType)

	{

		if(value==null) return;

		

		sku = value;

		skuType =findType;

	}

		

	public String getSKU()

	{

		if(isSKUSearch())

			return sku;

		else

			return kBlank;

	}

	

	public int getSKUType()

	{

		return skuType;

	}	

		

	public boolean isSKUSearch()

	{

		if(sku == null)

			return false;

		else

			return true;

	}

	

		

	public void setCustomerName(String value, int findType)

	{

		if(value==null) return;

		

		customerName = value;

		customerType =findType;

	}

		

	public String getCustomerName()

	{

		if(isCustomerSearch())

			return customerName;

		else

			return kBlank;

	}

	

	public int getCustomerType()

	{

		return customerType;

	}	

		

	public boolean isCustomerSearch()

	{

		if(customerName == null)

			return false;

		else

			return true;

	}
	
	public boolean isVoidsIncluded()

	{

		return voidsIncluded;
	}



	public void setShippedOn(String year, String month, String day,String year2, String month2, String day2)

	{

		if(year==null) return;

		

		shippedOnYear = year;

		shippedOnMonth =month;

		shippedOnDay =day;

		shippedToYear = year2;

		shippedToMonth =month2;

		shippedToDay =day2;

	}

		

	public String getShippedOnYear()

	{

		if(isShippedOnSearch())

			return shippedOnYear;

		else

			return ""+Calendar.getInstance().get(Calendar.YEAR);

	}



	public String getShippedOnMonth()

	{

		if(isShippedOnSearch())

			return shippedOnMonth;

		else

			return ""+(1+Calendar.getInstance().get(Calendar.MONTH));

	}



	public String getShippedOnDay()

	{

		if(isShippedOnSearch())

			return shippedOnDay;

		else

			return ""+Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

	}	



		

	public String getShippedToYear()

	{

		if(isShippedOnSearch())

			return shippedToYear;

		else

			return ""+Calendar.getInstance().get(Calendar.YEAR);

	}



	public String getShippedToMonth()

	{

		if(isShippedOnSearch())

			return shippedToMonth;

		else

			return ""+(1+Calendar.getInstance().get(Calendar.MONTH));

	}



	public String getShippedToDay()

	{

		if(isShippedOnSearch())

			return shippedToDay;

		else

			return ""+Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

	}	

		

				

	public boolean isShippedOnSearch()

	{

		if(shippedOnYear == null)

			return false;

		else

			return true;

	}



	public void setReceivedOn(String year, String month, String day,String year2, String month2, String day2)

	{

		if(year==null) return;

		

		receivedOnYear = year;

		receivedOnMonth =month;

		receivedOnDay =day;

		receivedToYear = year2;

		receivedToMonth =month2;

		receivedToDay =day2;

	}

		

	public String getReceivedOnYear()

	{

		if(isReceivedOnSearch())

			return receivedOnYear;

		else

			return ""+Calendar.getInstance().get(Calendar.YEAR);

	}



	public String getReceivedOnMonth()

	{

		if(isReceivedOnSearch())

			return receivedOnMonth;

		else

			return ""+(1+Calendar.getInstance().get(Calendar.MONTH));

	}



	public String getReceivedOnDay()

	{

		if(isReceivedOnSearch())

			return receivedOnDay;

		else

			return ""+Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

	}	



	public String getReceivedToYear()

	{

		if(isReceivedOnSearch())

			return receivedToYear;

		else

			return ""+Calendar.getInstance().get(Calendar.YEAR);

	}



	public String getReceivedToMonth()

	{

		if(isReceivedOnSearch())

			return receivedToMonth;

		else

			return ""+(1+Calendar.getInstance().get(Calendar.MONTH));

	}



	public String getReceivedToDay()

	{

		if(isReceivedOnSearch())

			return receivedToDay;

		else

			return ""+Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

	}	

			

	public boolean isReceivedOnSearch()

	{

		if(receivedOnYear == null)

			return false;

		else

			return true;

	}



	public void setOnHold(String value, int findType)

	{

		if(value==null) return;

		

		onHoldSearch = value;

		onHoldType =findType;

	}

		

	public String getOnHold()

	{

		if(isHoldSearch())

			return onHoldSearch;

		else

			return kBlank;

	}

	

	public int getHoldType()

	{

		return onHoldType;

	}	

	public boolean isGroupSearch()
	{
		if(Group == null){
			return false;

		}else{
			return true;
		}
	}
	public boolean isPOValueSearch()

	{

		if(POValue == null)

			return false;

		else

			return true;

	}
	public boolean isHoldSearch()

	{

		if(onHoldSearch == null)

			return false;

		else

			return true;

	}





	public void setShipped(String value, int findType)

	{

		if(value==null) return;

		

		shippedSearch = value;

		shippedType =findType;

	}

		

	public String getShipped()

	{

		if(isShippedSearch())

			return shippedSearch;

		else

			return kBlank;

	}

	public int getGroupType(){
		return GroupType;
	}
	public int getPOValueType()

	{

		return POValueType;

	}	

	public int getShippedType()

	{

		return shippedType;

	}	

		

	public boolean isShippedSearch()

	{

		if(shippedSearch == null)

			return false;

		else

			return true;

	}	

	



	public void setBackordered(String value, int findType)

	{

		if(value==null) return;

		

		backorderSearch = value;

		backorderType =findType;

	}

		

	public String getBackordered()

	{

		if(isBackorderSearch())

			return backorderSearch;

		else

			return kBlank;

	}

	

	public int getBackorderType()

	{

		return backorderType;

	}	

		

	public boolean isBackorderSearch()

	{

		if(backorderSearch == null)

			return false;

		else

			return true;

	}	

				

	private boolean showAll = false;

		

	public String showResults(HttpServletRequest req,HttpServletResponse resp) throws IOException

	{

		

		

		WebTable table = new WebTable();

		boolean gotSearch = false;

		table.setSQLDefs(tableColumnNames,tableColumnDefs,tableLinkStarts,tableLinkMids,tableLinkEnds,tableRenders,tableFromStmt);

					



		

		if(showAll)

		{

			gotSearch = true;

		}else

		{

			if(isRefSearch())

			{

				gotSearch = true;

				switch(refType)

				{

					case kIsType:

						table.addCriterium("o.order_refnum = \""+ref+"\"");

						break;

					case kInType:

						table.addCriterium("o.order_refnum like \"%"+ref+"%\"");

						break;

				

				default:

					table.addCriterium("o.order_refnum = \""+ref+"\"");

					refType = kIsType;

				}

			}

	

			if(isOWDRefSearch())

			{

				gotSearch = true;

				switch(OWDRefType)

				{

					case kIsType:

						table.addCriterium("o.order_num = \""+OWDRef+"\"");

						break;

					case kInType:

						table.addCriterium("o.order_num like \"%"+OWDRef+"%\"");

						break;

				

				default:

					table.addCriterium("o.order_num = \""+OWDRef+"\"");

					refType = kIsType;

				}

			}

			

			if(isTrackSearch())

			{

				gotSearch = true;

				switch(trackType)

				{

					case kIsType:

						table.addCriterium("order_id in (select order_fkey from owd_order_track where is_void=0 and tracking_no = \""+track+"\")");

						break;

				case kInType:

						table.addCriterium("order_id in (select order_fkey from owd_order_track where is_void=0 and tracking_no like \"%"+track+"%\")");

						break;

				default:

					table.addCriterium("order_id in (select order_fkey from owd_order_track where is_void=0 and tracking_no = \""+track+"\")");

					trackType = kIsType;

				}

			}
			
			if(isPOValueSearch())

			{

				gotSearch = true;

				switch(POValueType)

				{

					case kIsType:

						table.addCriterium("po_num = \'"+POValue+"\'");

						break;

				case kInType:

						table.addCriterium("po_num like \'%"+POValue+"%\'");

						break;

				default:

						table.addCriterium("po_num = \'"+POValue+"\'");

					POValueType = kIsType;

				}

			}
			if(isGroupSearch())

			{

				gotSearch = true;

				switch(GroupType)

				{

					case kIsType:

						table.addCriterium("group_name = \'"+Group+"\'");

						break;

					case kInType:

						table.addCriterium("group_name like \'%"+Group+"%\'");

						break;

					default:

						table.addCriterium("group_name = \'"+Group+"\'");

						GroupType = kIsType;

				}

			}


			if(isSKUSearch())

			{

				gotSearch = true;

				switch(skuType)

				{

					case kIsType:

						table.addCriterium("order_id in (select order_fkey from owd_line_item where  inventory_num = \""+sku+"\")");

						break;

					case kInType:

						table.addCriterium("order_id in (select order_fkey from owd_line_item where  inventory_num like \"%"+sku+"%\")");

						break;

				

				default:

					table.addCriterium("order_id in (select order_fkey from owd_line_item where  inventory_num = \""+sku+"\")");

					trackType = kIsType;

				}

			}

			

			if(isCustomerSearch())

			{

				gotSearch = true;

				String nameKey = "o.bill_last_name";

				

				if (getNameSearchCriteria().equals(kNameSearchBillLast))

					nameKey = "o.bill_last_name";

				else if (getNameSearchCriteria().equals(kNameSearchBillFirst))

					nameKey = "o.bill_first_name";

				else if (getNameSearchCriteria().equals(kNameSearchCompany))

					nameKey = "o.bill_company_name";

				else if (getNameSearchCriteria().equals(kNameSearchEmail))

					nameKey = "o.bill_email_address";
					
				else if (getNameSearchCriteria().equals(kNameSearchState))

					nameKey = "o.bill_state";
				else if (getNameSearchCriteria().equals(kNameSearchBillName))
                    nameKey = "o.bill_first_name ++ ' ' ++ bill_last_name";


                switch(customerType)

				{

					case kIsType:

						table.addCriterium(nameKey+" = \""+customerName+"\"");

						break;

					case kInType:

						table.addCriterium(nameKey+" like \"%"+customerName+"%\"");

						break;

				

				default:

					table.addCriterium(nameKey+" = \""+customerName+"\"");

					customerType = kIsType;

				}

			}



			if(isCouponSearch())

			{

				gotSearch = true;

				String couponKey = "o.coupon";



				switch(couponType)

				{

					case kIsType:

						table.addCriterium(couponKey+" = \""+coupon+"\"");

						break;

					case kInType:

						table.addCriterium(couponKey+" like \"%"+coupon+"%\"");

						break;

					case kGreaterThanType: //begins with

						table.addCriterium(couponKey+" like \""+coupon+"%\"");

						break;

					case kLessThanType: //ends with

						table.addCriterium(couponKey+" like \""+coupon+"%\"");

						break;				

				default:

					table.addCriterium(couponKey+" = \""+coupon+"\"");

					couponType = kIsType;

				}

			}

								

			if(isShippedOnSearch())

			{

				gotSearch = true;

					table.addCriterium("o.shipped_on >= \""+shippedOnYear+"-"+shippedOnMonth+"-"+shippedOnDay+" 00:00:00\" and o.shipped_on <= \""+shippedToYear+"-"+shippedToMonth+"-"+shippedToDay+" 23:59:50\"");

			}

	

			if(isReceivedOnSearch())

			{

				gotSearch = true;

					table.addCriterium("o.actual_order_date >= \""+receivedOnYear+"-"+receivedOnMonth+"-"+receivedOnDay+" 00:00:00\" and o.actual_order_date <=  \""+receivedToYear+"-"+receivedToMonth+"-"+receivedToDay+" 23:59:59\"");

			}

	

			if(isHoldSearch())

			{

				gotSearch = true;

				if(onHoldType==kIsType)

					table.addCriterium("is_future_ship= 1");

				else if(onHoldType==kInType)

					table.addCriterium("is_future_ship= 0");

			}

	

			if(isBackorderSearch())

			{

				gotSearch = true;

				if(backorderType==kIsType)

					table.addCriterium("is_backorder= 1");

				else if(backorderType==kInType)

					table.addCriterium("is_backorder= 0");

			}

	

			if(isShippedSearch())

			{

				gotSearch = true;

				if(shippedType==kIsType)

					table.addCriterium("o.shipped_on IS NOT NULL");

				else if(shippedType==kInType)

					table.addCriterium("order_id not in (select order_fkey from owd_order_track)");

			}

            if(isLocationSearch())

            {

                gotSearch = true;


                    table.addCriterium("facility_code = \""+locationCode+"\"");

            }

			



		}

			if(gotSearch)

			{

				table.addCriterium("o.client_fkey="+ExtranetServlet.getSessionString(req,ExtranetServlet.kExtranetClientID));

if(!(isVoidsIncluded()))
{
				table.addCriterium("o.is_void=0");
}
			}else

			{

				table.addCriterium("o.client_fkey=0");

			}

					

		showAll = false;													

		table.setDescription("");

			//check for find criteria

		
		int gotono = ExtranetServlet.getIntegerParam(req,WebTable.kTableGotoPage,0);
		if(gotono!=0)
			table.setPageNum(gotono);
		else
			table.setPageNum(ExtranetServlet.getIntegerParam(req,WebTable.kTableShowPage,1));
			

		table.setOrderCol(ExtranetServlet.getIntegerParam(req,WebTable.kTableSortColumn,-1));

		

		return table.getTable(req,resp);

		

	}









}
