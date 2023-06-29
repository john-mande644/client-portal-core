package com.owd.jobs.jobobjects.batchimporters;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.CountryNames;
import com.owd.core.DelimitedReader;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderStatus;
import com.owd.core.business.order.OrderUtilities;
import com.owd.jobs.jobobjects.utilities.RateShopper;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Jun 18, 2007
 * Time: 11:01:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class NugenicsInfusionUploadData extends OWDUploadOrdersData_1{
private final static Logger log =  LogManager.getLogger();

    public static int Sku	= 3;
    public static int ProductName=2;

   public static int Shipping = 4    ;
   public static int Id	=0  ;


  public static int Description	=20     ;
   public static int Qty=	21   ;

   public static int FirstName	=24     ;
   public static int LastName	=25 ;
   public static int Company	=26	;
   public static int Phone1	=28  ;
    
   public static int Email	=36  ;
   public static int StreetAddress1	=37    ;
   public static int StreetAddress2	=38   ;
   public static int City=	39 ;
   public static int State=	40   ;
   public static int PostalCode	=41   ;
   public static int Country	=43  ;


   public static int ShipFirstName	=5     ;
   public static int ShipLastName	=7 ;
   public static int ShipStreet1 = 10	 ;
   public static int ShipStreet2	= 11 ;
   public static int ShipCity	= 12 ;
   public static int ShipState	 = 13;
   public static int ShipPostalCode = 14;
    public static int ShipCountry = 15;

    /*
    IS file column designations

    0	OrderId
1	Type
2	ProductName
3	Sku
4	Shipping
5	ShipFirstName
6	ShipMiddleName
7	ShipLastName
8	ShipCompany
9	ShipPhone
10	ShipStreet1
11	ShipStreet2
12	ShipCity
13	ShipState
14	ShipZip
15	ShipCountry
16	FulfillmentItemId
17	SourceType
18	SourceId
19	ProductId
20	Description
21	Qty
22	Options
23	Id
24	FirstName
25	LastName
26	Company
27	Phone1Type
28	Phone1
29	Phone1Ext
30	Phone2Type
31	Phone2
32	Phone2Ext
33	Phone3Type
34	Phone3
35	Phone3Ext
36	Email
37	StreetAddress1
38	StreetAddress2
39	City
40	State
41	PostalCode
42	ZipFour1
43	Country
44	Website
45	Title
46	MiddleName
47	Suffix
48	Nickname
49	JobTitle
50	Address1Type
51	Address2Street1
52	Address2Street2
53	City2
54	State2
55	PostalCode2
56	ZipFour2
57	Country2
58	Address2Type
59	EmailAddress2
60	SpouseName
61	EmailAddress3
62	Address3Type
63	Address3Street1
64	Address3Street2
65	City3
66	State3
67	PostalCode3
68	ZipFour3
69	Country3
70	AssistantName
71	AssistantPhone
72	Phone4Type
73	Phone4
74	Phone4Ext
75	Phone5Type
76	Phone5
77	Phone5Ext
78	Fax1Type
79	Fax1
80	Fax2
81	Fax2Type
82	Leadsource
83	OrderTotal
84	PromoCode
85	CardType
86	ItemDescription

     */

       public Order loadOrder(Order order, int orderIndex) throws Exception {
        //log.debug("in sch loadOrder");
        if (orderIndex < 0 || orderIndex >= getOrderPositionList().size())
            throw new Exception("Could not load order index " + orderIndex + "; did not match order list size of " + getOrderPositionList().size());

        OrderData data = (OrderData) getOrderPositionList().get(orderIndex);
        DelimitedReader rdr = getDataReader();

           long itemIDTotal = 0;

        for (int row = data.rowIndexStart; row < rdr.getRowCount(); row++) {
            //log.debug("Sch loading row " + row);
            if (row == data.rowIndexStart) {
                //first row
                order.order_refnum = ""+rdr.getStrValue(Id, row, "");
                if (order.order_refnum.length() < 1) {
                    throw new Exception("Client order reference was invalid or not found.");
                }

                //response.add(order.order_refnum);
                //log.debug("Sch loading ref " + order.order_refnum);

                order.getBillingContact().setName( rdr.getStrValue(FirstName, row, "")+" "+rdr.getStrValue(LastName, row, ""));
                order.getBillingContact().email = rdr.getStrValue(Email, row, "");
                order.getBillingContact().phone = rdr.getStrValue(Phone1, row, "");

                order.getBillingAddress().company_name = rdr.getStrValue(Company, row, ".");
                if (order.getBillingAddress().company_name.trim().length() < 1) order.getBillingAddress().company_name = ".";
                order.getBillingAddress().address_one = rdr.getStrValue(StreetAddress1, row, "");
                order.getBillingAddress().address_two = rdr.getStrValue(StreetAddress2, row, "");
                order.getBillingAddress().city = rdr.getStrValue(City, row, "");
                order.getBillingAddress().state = rdr.getStrValue(State, row, "");
                order.getBillingAddress().zip = ("".equals(rdr.getStrValue(PostalCode, row, "").trim())?rdr.getStrValue(42, row, ""):rdr.getStrValue(PostalCode, row, ""));
                order.getBillingAddress().country = rdr.getStrValue(Country, row, "");


                order.getShippingContact().setName(order.getBillingContact().getName());
                order.getShippingContact().email = order.getBillingContact().getEmail();
                order.getShippingContact().phone = order.getBillingContact().getPhone();

                order.getShippingAddress().company_name = order.getBillingAddress().getCompany_name();
                if (order.getShippingAddress().company_name.trim().length() < 1) order.getShippingAddress().company_name = ".";

                if("".equals(rdr.getStrValue(ShipStreet1, row, "").replaceAll("null","")))
                {
                         order.getShippingAddress().address_one = rdr.getStrValue(StreetAddress1, row, "");
                order.getShippingAddress().address_two = rdr.getStrValue(StreetAddress2, row, "");
                order.getShippingAddress().city = rdr.getStrValue(City, row, "");
                order.getShippingAddress().state = rdr.getStrValue(State, row, "");
                order.getShippingAddress().zip = ("".equals(rdr.getStrValue(PostalCode, row, "").trim())?rdr.getStrValue(42, row, ""):rdr.getStrValue(PostalCode, row, ""));

                    order.getBillingAddress().country = rdr.getStrValue(Country, row, "");

                }   else
                {
                order.getShippingAddress().address_one = rdr.getStrValue(ShipStreet1, row, "");
                order.getShippingAddress().address_two = rdr.getStrValue(ShipStreet2, row, "");
                order.getShippingAddress().city = rdr.getStrValue(ShipCity, row, "");
                order.getShippingAddress().state = rdr.getStrValue(ShipState, row, "");
                order.getShippingAddress().zip = rdr.getStrValue(ShipPostalCode, row, "");
                    if("".equals(order.getShippingAddress().zip.trim()))
                    {
                        order.getShippingAddress().zip = ("".equals(rdr.getStrValue(PostalCode, row, "").trim())?rdr.getStrValue(42, row, ""):rdr.getStrValue(PostalCode, row, ""));
                    }
                order.getShippingAddress().country = rdr.getStrValue(ShipCountry, row, "")
                ;
                }

                log.debug(order.getShippingAddress().country);
                correctCountryName(order,"","USA");
                correctCountryName(order,"TRINIDAD AND TOBAGO","TRINIDAD TOBAGO");
                correctCountryName(order,"RUSSIAN FEDERATION","RUSSIA");
                correctCountryName(order,"MACAO","MACAU");
                correctCountryName(order,"ANTIGUA AND BARBUDA","ANTIGUA");
                correctCountryName(order,"Virgin Islands, British","BRITISH VIRGIN ISLANDS");
                correctCountryName(order,"VIET NAM","VIETNAM");
                correctCountryName(order,"DEMOCRATIC REPUBLIC OF CONGO","CONGO");
                correctCountryName(order,"","UNITED STATES");
                correctCountryName(order,"EMPTY","UNITED STATES");
                correctCountryName(order,"ST. MARTIN","GUADELOUPE");
                correctCountryName(order,"WESTERN SAHARA","MOROCCO");


                correctCountryName(order,"TURKS AND CAICOS ISLANDS","TURKS CAICOS ISLANDS");
                correctCountryName(order,"HOLY SEE (VATICAN CITY STATE)","VATICAN CITY STATE");
                correctCountryName(order,"ST. BARTH","GUADELOUPE");
                correctCountryName(order,"ST. VINCENT AND THE GRENEDINES","ST. VINCENT & GRENADINES");
                correctCountryName(order,"ST. VINCENT AND THE GRENADINES","ST. VINCENT & GRENADINES");
                correctCountryName(order,"REPUBLIC OF MACEDONIA","MACEDONIA");
                        correctCountryName(order,"ISLE OF MAN","UNITED KINGDOM");

                if (!CountryNames.exists(order.getBillingAddress().country.toUpperCase())) {
                    order.getBillingAddress().country="USA";
                }
                if (!CountryNames.exists(order.getShippingAddress().country.toUpperCase())) {
                    throw new Exception("Shipping Country name not recognized: " + order.getShippingAddress().country);
                }
                order.getShippingAddress().country = CountryNames.getCountryName(order.getShippingAddress().country.toUpperCase());
                order.getBillingAddress().country = CountryNames.getCountryName(order.getBillingAddress().country.toUpperCase());

                order.total_shipping_cost = 0.00f;
                order.total_tax_cost = 0.00f;
                order.discount = 0.00f;

                order.total_shipping_cost = 0.00f;
               order.po_num= rdr.getStrValue(82, row, "");



                order.order_type = "InfusionSoft Download";
                order.ship_operator = "InfusionSoft";
                    //log.debug("Checking is_paid translation for value:"+rdr.getStrValue(kOrder_Paid, row, "nuthin"));
                //log.debug("...translates to int value:"+rdr.getIntValue(kOrder_Paid, row, -1));
                order.is_paid = 1;
                order.is_gift = "1";
                order.gift_message = "Thank you for your order!";



            }
            if((""+rdr.getStrValue(Id, row, "")).equals(order.order_refnum))
            {
                itemIDTotal += rdr.getIntValue(16,row,0);
            //product data collected for every row
                String sku = rdr.getStrValue(Sku,row,"").trim();
                if(sku.equals("MD303-4M")){sku = "MD301-4M";}
                if(sku.equals("FLEX-6M")){sku = "OF-6B-UP";}


                if( (rdr.getStrValue(Description, row, "").contains("Shipping") && rdr.getStrValue(Description, row, "").indexOf("$6.95")>=0) ||
                        ((sku.equals("1317154034")) || (sku.length()==0 && rdr.getStrValue(ProductName, row, "").contains("Shipping"))))
                {
                    
                }   else
                {
                log.debug("NUGENICS-IS adding SKU <"+sku+">");

                    sku = Normalizer.normalize(sku, Normalizer.Form.NFD);
                    sku = sku.replaceAll("[^\\x20-\\x7F]", "");

                    if(!(sku.equals("ME-1234"))){
            addLineItem(order,sku.trim(),
                    rdr.getStrValue(Qty, row, ""),
                    "0.00",
                    "0.00",
                    rdr.getStrValue(ProductName, row, ""),
                    "", "");
                    }
                }
            }
        }


               List<String> alternateShipMethodList = new ArrayList<String>();
           if(order.getShippingContact().getName().equalsIgnoreCase("Soluntra King"))
           {
               alternateShipMethodList.add("TANDATA_USPS.USPS.PRIORITY");
               alternateShipMethodList.add("OWD_USPS_I_PRIORITY");

           }else{
               alternateShipMethodList.add("TANDATA_USPS.USPS.PRIORITY");
               alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.IECO");
            //   alternateShipMethodList.add("CONNECTSHIP_UPS.UPS.STD");
            //   alternateShipMethodList.add("CONNECTSHIP_UPS.UPS.GND");
           if(order.getShippingAddress().isUSLower48())
           {
           alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.GND");
           }
           alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.FHD");

           }


           order.getShippingInfo().setShipOptions( RateShopper.rateShop(order, alternateShipMethodList), "Prepaid", "");

      //  order.order_refnum = order.order_refnum+"-"+itemIDTotal+"9";
        return order;
    }



    protected void correctCountryName(Order order, String oldCountry, String newCountry)
    {
        if(order.getShippingAddress().country.toUpperCase().contains(oldCountry) && oldCountry.length()>0)
        {
            order.getShippingAddress().country=newCountry;
        } else if(order.getShippingAddress().country.toUpperCase().equals(oldCountry))
        {
            order.getShippingAddress().country=newCountry;

        }

        if(order.getBillingAddress().country.toUpperCase().contains(oldCountry) && oldCountry.length()>0)
        {
            order.getBillingAddress().country=newCountry;
        } else if(order.getBillingAddress().country.toUpperCase().equals(oldCountry))
        {
            order.getBillingAddress().country=newCountry;

        }
    }
     protected void processReader() {

        String orderRef = "thisisaninvalidorderreference";
        if (getDataReader() == null) return;

         List<String> orderrefs = new ArrayList<String>();


        for (int row = 0; row < getDataReader().getRowCount(); row++) {
            log.debug("DH handling row " + row);
            log.debug("Working on ref "+getDataReader().getStrValue(Id, row, "XXX"));
            log.debug(orderrefs);
            if (!(getDataReader().getStrValue(Id, row, "XXX").trim().equals("XXX"))) {
                if (orderRef.equals(getDataReader().getStrValue(Id, row, "XXX").trim())) {
                    //got the same order
                    log.debug("DH found extra row");
                    OrderData data = (OrderData) getOrderPositionList().get(getOrderPositionList().size() - 1);
                    data.rowsForOrder++;
                    //log.debug("DH adding item to order " + data.orderRef);
                } else {
                    //new order
                    log.debug("DH found new row");
                    if(!(orderrefs.contains(getDataReader().getStrValue(Id, row, "XXX").trim())))
                    {
                    OrderData data = new OrderData();
                    orderRef = getDataReader().getStrValue(Id, row, "XXX").trim();
                    data.orderRef = orderRef;
                    orderrefs.add(orderRef);
                    data.rowIndexStart = row;
                    data.rowsForOrder = 1;
                    getOrderPositionList().add(data);
                    }
                    log.debug("DH added entry for " + orderRef + ", row " + row);
                }
            }
        }
    }



}
