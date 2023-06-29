package com.owd.jobs.jobobjects.batchimporters;

import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderAuto;
import com.owd.hibernate.generated.OwdOrderAutoItem;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.CountryNames;
import com.owd.core.business.autoship.AutoShipFactory;
import com.owd.core.business.order.Order;
import com.owd.hibernate.*;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Jan 12, 2006
 * Time: 8:48:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class LifeSpanImportData {
private final static Logger log =  LogManager.getLogger();
    private String rtHeader = "H";
    private String rtAddress = "A";
    private String rtOrder = "O";
    private String rtItem = "I";
    private String rtPayment = "P";
    private String rtTrailer = "T";
    private String recordType;
    private String HcallId;
    private Integer Hcampaignid;
    private String HcallTime;
    private String HphoneNumber;
    private Integer ACallId;
    private String AfirstName;
    private String AlastName;
    private String Aaddress;
    private String Acity;
    private String Astate;
    private String Azip;
    private String Acountry;
    private String Atype;
    private String Aemail;

    private Integer OCallId;
    private float Osubtotal;
    private float Otax;
    private float Oshipping;
    private float Ototal;
    private String OshippingCode;

    private Integer ICallId;
    private String Isku;
    private String Idescription;
    private String IunitPrice;
    private float IunitShipping;
    private String Iquanity;

    private Integer PCallId;
    private String Ppayment;
    private String ProutingCCV;
    private String PaccountNumber;
    private String PcheckNumber;
    private float PchargeAmount;
    private String PprocessingStatus;
    private Integer PrefNumber;
    private int PccMonth;
    private int PccYear;
    public boolean autoship = false;

    public String getHCallId(String line) {
        HcallId = line.substring(1, 11).trim();
        return HcallId;
    }

    public Integer getHCampaignid(String line) {
        Hcampaignid = Integer.valueOf(line.substring(11, 21).trim());
        return Hcampaignid;
    }

    public String getHCallTime(String Line) {
        HcallTime = Line.substring(41, 60).trim();
        return HcallTime;
    }

    public String getRecordType(String line) {
        recordType = line.substring(0, 1);
        return recordType;
    }

    public String getHphoneNumber(String line) {
        HphoneNumber = line.substring(31, 41).trim();
        return HphoneNumber;
    }

    public Integer getACallId(String line) {
        ACallId = Integer.valueOf(line.substring(1, 11).trim());
        return ACallId;
    }

    public String getAfirstName(String line) {
        AfirstName = line.substring(11, 41).trim();
        return AfirstName;
    }

    public String getAlastName(String line) {
        AlastName = line.substring(41, 71).trim();
        return AlastName;
    }

    public String getAaddress(String line) {
        Aaddress = line.substring(71, 131).trim();
        return Aaddress;
    }

    public String getAcity(String line) {
        Acity = line.substring(131, 161).trim();
        return Acity;
    }

    public String getAstate(String line) {
        Astate = line.substring(161, 163).trim();
        return Astate;
    }

    public String getAzip(String line) {
        Azip = line.substring(163, 169).trim();
        return Azip;
    }

    public String getAcountry(String line) {
        Acountry = line.substring(169, 171).trim();
        return Acountry;
    }

    public String getAtype(String line) {
        Atype = line.substring(171, 175).trim();
        return Atype;
    }

    public String getAemail(String line) {
        Aemail = line.substring(175, 225);
        return Aemail;
    }

    public Integer getOCallId(String line) {
        OCallId = Integer.valueOf(line.substring(1, 11).trim());
        return OCallId;
    }

    public float getOsubtotal(String line) {
        Osubtotal = Float.valueOf(line.substring(11, 19).trim()).floatValue();
        return Osubtotal;
    }

    public float getOtax(String line) {
        Otax = Float.valueOf(line.substring(19, 27).trim()).floatValue();
        return Otax;
    }

    public float getOshipping(String line) {
        Oshipping = Float.valueOf(line.substring(27, 35).trim()).floatValue();
        return Oshipping;
    }

    public float getOtotal(String line) {
        Ototal = Float.valueOf(line.substring(35, 43).trim()).floatValue();
        return Ototal;
    }

    public String getOshippingCode(String line) {
        OshippingCode = line.substring(43, 45).trim();
        return OshippingCode;
    }

    public Integer getICallId(String line) {
        ICallId = Integer.valueOf(line.substring(1, 11).trim());
        return ICallId;
    }

    public String getIsku(String line) {
        Isku = line.substring(11, 41).trim();
        return Isku;
    }

    public String getIdescription(String line) {
        Idescription = line.substring(41, 116).trim();
        return Idescription;
    }

    public String getIunitPrice(String line) {
        IunitPrice = line.substring(116, 124).trim();
        return IunitPrice;
    }

    public float getIunitShipping(String line) {
        IunitShipping = Float.valueOf(line.substring(124, 132).trim()).floatValue();
        return IunitShipping;
    }

    public String getIquanity(String line) {
        Iquanity = line.substring(132, 142).trim();
        return Iquanity;
    }

    public Integer getPCallId(String line) {
        PCallId = Integer.valueOf(line.substring(1, 11).trim());
        return PCallId;
    }

    public String getPpayment(String line) {
        Ppayment = line.substring(11, 17).trim();
        return Ppayment;
    }

    public String getProutingCCV(String line) {
        ProutingCCV = line.substring(17, 29).trim();
        return ProutingCCV;
    }

    public String getPaccountNumber(String line) {
        PaccountNumber = line.substring(29, 45).trim();
        return PaccountNumber;
    }

    public String getPcheckNumber(String line) {
        PcheckNumber = line.substring(45, 51).trim();
        return PcheckNumber;
    }

    public float getPchargeAmount(String line) {
        PchargeAmount = Float.valueOf(line.substring(51, 59).trim()).floatValue();
        return PchargeAmount;
    }

    public String getPprocessingStatus(String line) {
        PprocessingStatus = line.substring(59, 71).trim();
        return PprocessingStatus;
    }

    public Integer getPrefNumber(String line) {
        PrefNumber = Integer.valueOf(line.substring(71, 81).trim());
        return PrefNumber;
    }

    public int getPccMonth(String line) {
        PccMonth = Integer.parseInt(line.substring(45, 47).trim());
        return PccMonth;
    }

    public int getPccYear(String line) {
        PccYear = Integer.parseInt(line.substring(47, 51).trim());
        return PccYear;
    }


    public Order loadOrder(List orderInfo, Order order, int orderIndex) throws Exception {
        String header = null;
        String orderRecord = null;
        List addresss = new ArrayList();
        List items = new ArrayList();
        String payment = null;
        //log.debug("in LifeSpan loadOrder");
        for (int i = 0; i < orderInfo.size(); i++) {
            String currentRecord = orderInfo.get(i).toString();
            String rt = getRecordType(currentRecord);
            if (rt.equals(rtHeader)) {
                header = currentRecord;
            }
            if (rt.equals(rtAddress)) {
                addresss.add(currentRecord);
            }
            if (rt.equals(rtOrder)) {
                orderRecord = currentRecord;
            }
            if (rt.equals(rtItem)) {
                items.add(currentRecord);
            }
            if (rt.equals(rtPayment)) {
                payment = currentRecord;
            }
            if (rt.equals(rtTrailer)) {
                if (header.equals(null) || orderRecord.equals(null) || payment.equals(null) || addresss.size() < 1 || items.size() < 1) {
                    throw new Exception("All info not present");
                }
            }


        }






        //  if (orderIndex < 0 || orderIndex >= getOrderPositionList().size())
        //      throw new Exception("Could not load order index " + orderIndex + "; did not match order list size of " + getOrderPositionList().size());



        //  for (int row = data.rowIndexStart; row < (data.rowIndexStart + data.rowsForOrder); row++) {
        //log.debug("LifeSpan process order ");
        //    if (row == data.rowIndexStart) {
        //first row
        order.order_refnum = getHCallId(header);
        if (order.order_refnum.length() < 1) {
            throw new Exception("Client order reference was invalid or not found.");
        }
        //response.add(order.order_refnum);
        //log.debug("DH loading ref " + order.order_refnum);
        //Proccess Address records
        for (int i = 0; i < addresss.size(); i++) {
            String r = (String) addresss.get(i);
            if (getAtype(r).equals("BILL")) {
                order.getBillingContact().setName(getAfirstName(r) + " " + getAlastName(r));
                order.getBillingContact().email = getAemail(r);
                order.getBillingContact().phone = getHphoneNumber(header);

                order.getBillingAddress().company_name = ".";
                if (order.getBillingAddress().company_name.trim().length() < 1) order.getBillingAddress().company_name = ".";
                order.getBillingAddress().address_one = getAaddress(r);
                order.getBillingAddress().address_two = "";
                order.getBillingAddress().city = getAcity(r);
                order.getBillingAddress().state = getAstate(r);
                order.getBillingAddress().zip = getAzip(r);
                order.getBillingAddress().country = getAcountry(r);
                if (addresss.size() == 1) {
                    order.getShippingContact().setName(getAfirstName(r) + " " + getAlastName(r));
                    order.getShippingContact().email = getAemail(r);
                    order.getShippingContact().phone = getHphoneNumber(header);

                    order.getShippingAddress().company_name = ".";
                    if (order.getShippingAddress().company_name.trim().length() < 1) order.getShippingAddress().company_name = ".";

                    order.getShippingAddress().address_one = getAaddress(r);
                    order.getShippingAddress().address_two = "";
                    order.getShippingAddress().city = getAcity(r);
                    order.getShippingAddress().state = getAstate(r);
                    order.getShippingAddress().zip = getAzip(r);
                    order.getShippingAddress().country = getAcountry(r);

                }
            }
            if (getAtype(r).equals("SHIP")) {
                order.getShippingContact().setName(getAfirstName(r) + " " + getAlastName(r));
                order.getShippingContact().email = getAemail(r);
                order.getShippingContact().phone = getHphoneNumber(header);

                order.getShippingAddress().company_name = ".";
                if (order.getShippingAddress().company_name.trim().length() < 1) order.getShippingAddress().company_name = ".";

                order.getShippingAddress().address_one = getAaddress(r);
                order.getShippingAddress().address_two = "";
                order.getShippingAddress().city = getAcity(r);
                order.getShippingAddress().state = getAstate(r);
                order.getShippingAddress().zip = getAzip(r);
                order.getShippingAddress().country = getAcountry(r);
            }
            if (!CountryNames.exists(order.getBillingAddress().country)) {
                throw new Exception("Customer Country name not recognized: " + order.getBillingAddress().country);
            }
            if (!CountryNames.exists(order.getShippingAddress().country)) {
                throw new Exception("Shipping Country name not recognized: " + order.getShippingAddress().country);
            }
            order.getShippingAddress().country = CountryNames.getCountryName(order.getShippingAddress().country);
            order.getBillingAddress().country = CountryNames.getCountryName(order.getBillingAddress().country);
        }
        order.total_shipping_cost = getOshipping(orderRecord);
        //log.debug(order.total_shipping_cost);
        order.total_tax_cost = getOtax(orderRecord);
        //  order.total_order_cost = getOtotal(orderRecord);
        //order.discount = -1 * Math.abs(rdr.getFloatValue(kOrder_Discount, row, 0.00f));
        // order.getShippingInfo().comments = rdr.getStrValue(OrderComments, row, "");


        //  order.getShippingInfo().ss_saturday = rdr.getStrValue(kShip_Handling_Fee, row, "0").equals("1") ? "1" : "0";
        //  float cod_charge = OWDUtilities.roundFloat(rdr.getFloatValue(kCOD_Shipment, row, 0.00f));
        //  if (cod_charge > 0.00f) {
        //      order.getShippingInfo().cod_charge = "" + cod_charge;
        //      order.getShippingInfo().ss_cod = "1";
        //  }

        //  order.total_shipping_cost = rdr.getFloatValue(kShip_Handling_Fee, row, 0.00f);
        //Loop through items
        boolean rush = false;
        autoship = false;
        int upgrade = 0;
        for (int i = 0; i < items.size(); i++) {
            String item = (String) items.get(i);
            if (!getIsku(item).equals("RUSH")) {
                if (getIsku(item).equals("BOTTLE")) {
                    autoship = true;
                }
                addLineItem(order, skuLookup(getIsku(item)),
                        getIquanity(item),
                        getIunitPrice(item),
                        "0.00",
                        getIdescription(item),
                        "", "");
                upgrade++;
            } else {
                rush = true;
            }


        }
        // add GF to orders
        //tod

       // if (OrderUtilities.getAvailableInventory(Inventory.dbloadByPart("PF", "320").inventory_id + "") > 1) {
            //log.debug("in pf");
     //       addLineItem(order, "PF", "1", "0.00", "0.00", "", "", "");
     //   }


        if (rush) {
            order.getShippingInfo().setShipOptions("USPS Priority Mail", "Prepaid", "");
            order.getShippingInfo().whse_notes = "Priority Rush Shipping";
            // //log.debug("Shipping cost " + order.total_shipping_cost);
            // order.total_shipping_cost = order.total_shipping_cost + 6.95f;
            order.recalculateBalance();
            //  //log.debug("Shipping cost new " + order.total_shipping_cost);
            //  //log.debug("total " + order.total_order_cost);
            //order.total_order_cost = order.total_order_cost - 6.95f;
            // //log.debug("total new" + order.total_order_cost);
        } else {
            if (upgrade > 1) {
                order.getShippingInfo().setShipOptions("USPS Priority Mail", "Prepaid", "");
            } else {
                order.getShippingInfo().setShipOptions("USPS First-Class Mail", "Prepaid", "");
            }

        }

        //bill to client account



        order.order_type = "Gelenk3IVR";
        //   order.ship_operator = rdr.getStrValue(kOrder_Taker, row, "");
        ////log.debug("Checking is_paid translation for value:" + rdr.getStrValue(kOrder_Paid, row, "nuthin"));
        // //log.debug("...translates to int value:" + rdr.getIntValue(kOrder_Paid, row, -1));

        // order.is_gift = "" + (rdr.getIntValue(kIs_Gift, row, 0) > 0 ? 1 : 0);
        // order.gift_message = rdr.getStrValue(kGift_Message, row, "");
        if (getPpayment(payment).equals("CARD")) {
            order.is_paid = 0;
            order.cc_num = getPaccountNumber(payment);
            order.cc_exp_mon = getPccMonth(payment);
            order.cc_exp_year = getPccYear(payment);
        } else {
            order.is_paid = 1;

        }
        // order.payment_status = "";

        // order.po_num = rdr.getStrValue(kPONumber, row, "");


        //   }
        //product data collected for every row

        //}

        if (autoship == true) {
            //log.debug("autoship");
            // autoShip(order);
        }
        return order;

    }

    public void addLineItem(Order order, String sku, String qty, String unitPrice, String linePrice, String desc, String color, String size) throws Exception {
        //log.debug("adding li:" + sku + "," + qty + "," + unitPrice + "," +
          //      linePrice + "," +
         //       desc + "," +
         //       color + "," + size);

        if (new Float(unitPrice).floatValue() < 0.00f) {
            order.discount = order.discount + new Float(unitPrice).floatValue();
        } else {
            order.addLineItem(sku,
                    qty,
                    unitPrice,
                    linePrice,
                    desc,
                    color, size);
        }
    }

    public String skuLookup(String s) {
        String sku = null;
        if (s.equals("BOTTLE")) {
            sku = "G4";
        }
        if (s.equals("OMEGA3")) {
            sku = "O3";
        }
        if (s.equals("CREAM")) {
            sku = "TC";
        }
        if (s.equals("PEDOMETER")) {
            sku = "PDM";
        }
        if (sku.equals(null)) {
            sku = s;
        }
        return sku;

    }

    public void autoShip(Order o) throws Exception {
        Session sess = HibernateSession.currentSession();
        OwdOrderAuto auto = AutoShipFactory.getNewAutoShip(320);
        auto.setBillName(o.getBillingContact().getName());
        auto.setBillPhone(o.getBillingContact().phone);
        auto.setBillEmail(o.getBillingContact().email);
        auto.setBillAddressOne(o.getBillingAddress().address_one);
        auto.setBillAddressTwo(o.getBillingAddress().address_two);
        auto.setBillCity(o.getBillingAddress().city);
        auto.setBillState(o.getBillingAddress().state);
        auto.setBillZip(o.getBillingAddress().zip);
        auto.setBillCountry(o.getBillingAddress().country);

        auto.setShipName(o.getShippingContact().getName());
        auto.setShipPhone(o.getShippingContact().phone);
        auto.setShipEmail(o.getShippingContact().email);
        auto.setShipAddressOne(o.getShippingAddress().address_one);
        auto.setShipAddressTwo(o.getShippingAddress().address_two);
        auto.setShipCity(o.getShippingAddress().city);
        auto.setShipState(o.getShippingAddress().state);
        auto.setShipZip(o.getShippingAddress().zip);
        auto.setShipCountry(o.getShippingAddress().country);

        // auto.setShipMethod(o.getShippingInfo().carr_service);
        auto.setShipInterval(new Integer(1));
        auto.setRequirePayment(new Integer(1));
        auto.setShipCost(new BigDecimal(6.95));

        auto.setCreatedBy("IVR Import");
        auto.setCreated(Calendar.getInstance().getTime());
        auto.setStatus(new Integer(0));
        auto.setClientFkey(Integer.decode(o.clientID));
        auto.setCalendarUnit("month");
        OwdOrder source = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,new Integer(o.getID()));
        if(source!=null)
        {
          auto.getSourceOrders().add(source);
        }
        auto.setOrderSource(o.order_type);
        Calendar expected = Calendar.getInstance();
        //todo chekc on dates

        // expected.setTime(os.post_date);
        expected.roll(Calendar.MONTH, 1);
        auto.setNextShipmentDate(expected.getTime());
        auto.setOrderSource(o.order_type);
        auto.setCcNum(o.cc_num);
        auto.setCcExpMon(new Integer(o.cc_exp_mon));
        auto.setCcExpYear(new Integer(o.cc_exp_year));
        sess.saveOrUpdate(auto);

        OwdOrderAutoItem autoitem = new OwdOrderAutoItem();
        autoitem.setSku("G90");
        autoitem.setUnitPrice(new BigDecimal(28.95));
        autoitem.setQuantity((int) o.getQuantityForSKU("G4"));
        if (autoitem.getQuantity() > 1) {
            auto.setShipMethod("USPS Priority Mail");
        } else {
            auto.setShipMethod("USPS First-Class Mail");
        }

        if (auto.getShipState().equals("AZ")) {
            auto.setSalesTax(new BigDecimal(0.08d * (autoitem.getUnitPrice().doubleValue() * autoitem.getQuantity())));
        } else {
            auto.setSalesTax(new BigDecimal(0.00));
        }


        autoitem.setOwdOrderAuto(auto);
        auto.getOwdOrderAutoItems().add(autoitem);
        sess.save(autoitem);
     //   OwdOrderAutoItem autoitem2 = new OwdOrderAutoItem();
    //    autoitem2.setSku("PF");
     //   autoitem2.setUnitPrice(new BigDecimal(0.00));
     //   autoitem2.setQuantity(1);
     //   autoitem2.setOwdOrderAuto(auto);
     //   auto.getOwdOrderAutoItems().add(autoitem2);
     //   sess.save(autoitem2);


        sess.saveOrUpdate(auto);
        HibUtils.commit(sess);;
        //  saveOrUpdateAutoShip(auto, "New subscription  created from import file " + "\r\n");
        //  //log.debug("Created new subscription!   Status is " + AutoShipManager.getAutoShipStatus("" + auto.getStatus()));")


    }


}
