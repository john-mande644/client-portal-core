<html>
<%@ page import="
com.owd.core.Mailer,
com.owd.core.OWDUtilities,
com.owd.core.business.Address,
com.owd.core.business.Contact"
%>
<%@ page import="com.owd.core.business.order.*"%><%@ page import="com.owd.core.dbUtilities"%>
<%@ page import="com.owd.core.xml.OrderXMLDoc"%>
<%@ page import="java.text.DecimalFormat"%><%@ page import="java.text.NumberFormat"%>
<%@ page import="java.util.*" %>
<%@ page import="com.owd.core.managers.FacilitiesManager" %>
<%
    Order currorder = null;
    String errorMessage = "";
    String trackEvent = null;
    List country = Arrays.asList(new String[]{"AUSTRALIA",
            "AUSTRIA", "BELGIUM", "CANADA", "CHINA", "DENMARK", "ENGLAND", "FINLAND", "FRANCE", "GERMANY",
            "GUAM", "IRELAND", "ITALY", "JAPAN", "NETHERLANDS", "NEW ZEALAND", "NORWAY", "POLAND",
            "PORTUGAL", "PUERTO RICO", "SOUTH AFRICA", "SPAIN", "SWEDEN", "SWITZERLAND", "UNITED KINGDOM"});

    try {
        currorder = (Order) request.getSession(true).getAttribute("currorder");
    } catch (Exception ex) {
        currorder = new Order("117");
        currorder.getShippingInfo().carr_service = "First Class";
        request.getSession(true).setAttribute("currorder", currorder);
    }


    if (null == currorder || ("clear".equals((String) request.getParameter("editaction")))) {
        currorder = new Order("117");
        currorder.getShippingInfo().carr_service = "First Class";
        request.getSession(true).setAttribute("currorder", currorder);
    } else {

    }
    currorder.shipping_taxable = true;
    Address billingAddress = currorder.getBillingAddress();
    Address shippingAddress = currorder.getShippingAddress();
    Contact billContact = currorder.getBillingContact();
    Contact shipContact = currorder.getShippingContact();

    DecimalFormat dform = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
    Vector skus = currorder.skuList;
    //
    //
    currorder.total_product_cost = (float) 0.000;
    currorder.total_tax_cost = (float) 0.000;
    int units = 0;


    for (int i = 0; i < skus.size(); i++) {
        LineItem item = (LineItem) skus.elementAt(i);
        if ((item.quantity_backordered + item.quantity_request) > 0) {
            currorder.total_product_cost += item.total_price;

            int qty = (int) item.quantity_request + (int) item.quantity_backordered;

            if (!(item.client_part_no.startsWith("GW-"))) {
                int packIndex = item.description.indexOf("-Pack");

                if ((packIndex > 0)) {
                    String lastToken = "1";
                    StringTokenizer st = new StringTokenizer(item.description.substring(0, packIndex), " ");
                    while (st.hasMoreTokens())
                        lastToken = st.nextToken();
                    try {
                        qty = qty * (new Integer(lastToken).intValue());
                    } catch (Exception ex) {

                    }
                }
                units += qty;
            }
        }
    }


    String carrier = currorder.getCarrier();

    if ((carrier.indexOf("Priority Mail") >= 0) && units > 1) {
        carrier = "First Class";
    }

    if (!(ts(currorder.getShippingAddress().country).equals("US") || ts(currorder.getShippingAddress().country).equals("USA"))) {
        carrier = "Int\'l Letter-Post/Sm Packet Air";
    }

    float priority = (float) (4.95 + ((float) 1.50 * (units - 1)));
    float twoday = (float) 9.95 + ((float) 1.50 * (units - 1));
    float oneday = (float) (15.95 + ((float) 1.50 * (units - 1)));
    float canada = (float) 8.95 + ((float) 1.50 * (units - 1));
    float intl = (float) 12.95 + ((float) 4.95 * (units - 1));

    if (currorder.containsSKU("VV:GT 08-03521")) {
        //start new info III
        long skuUnits = currorder.getQuantityForSKU("VV:GT 08-03521");
        priority += skuUnits * (float) 11.18;
        twoday += skuUnits * (float) 51.07;
        oneday += skuUnits * (float) 70.32;
        intl += skuUnits * (float) 69.03;
        canada += skuUnits * (float) 24.43;

    }
    if (currorder.containsSKU("VV:GT 08-83347")) {
        long skuUnits = currorder.getQuantityForSKU("VV:GT 08-83347");
        priority += skuUnits * (float) 11.18;
        twoday += skuUnits * (float) 51.07;
        oneday += skuUnits * (float) 70.32;
        intl += skuUnits * (float) 69.03;
        canada += skuUnits * (float) 24.43;

    }
    if (currorder.containsSKU("VV:GT 08-83357")) {
        long skuUnits = currorder.getQuantityForSKU("VV:GT 08-83357");
        priority += skuUnits * (float) 11.18;
        twoday += skuUnits * (float) 51.07;
        oneday += skuUnits * (float) 70.32;
        intl += skuUnits * (float) 94.63;
        canada += skuUnits * (float) 24.43;

    }
    if (currorder.containsSKU("VV:GT 08-03535")) {
        long skuUnits = currorder.getQuantityForSKU("VV:GT 08-03535");
        priority += skuUnits * (float) 11.18;
        twoday += skuUnits * (float) 51.07;
        oneday += skuUnits * (float) 70.32;
        intl += skuUnits * (float) 94.63;
        canada += skuUnits * (float) 24.43;

    }

    //end new info III


    if (currorder.containsSKU("VV:GT 08-83062")) {
        //7.5	31.5	41.5	31.25	16.5
        long skuUnits = currorder.getQuantityForSKU("VV:GT 08-83062");
        priority += skuUnits * (float) 7.50;
        twoday += skuUnits * (float) 31.50;
        oneday += skuUnits * (float) 41.5;
        intl += skuUnits * (float) 31.25;
        canada += skuUnits * (float) 16.5;

    }
    if (currorder.containsSKU("VV:GT 08-83061")) {
        //6	30	40	48.3	15
        long skuUnits = currorder.getQuantityForSKU("VV:GT 08-83061");
        priority += skuUnits * (float) 6;
        twoday += skuUnits * (float) 30;
        oneday += skuUnits * (float) 40;
        intl += skuUnits * (float) 48.3;
        canada += skuUnits * (float) 15;
    }
    if (currorder.containsSKU("VV:GT 08-02780")) {
        //7.5	31.5	41.5	31.25	16.5
        long skuUnits = currorder.getQuantityForSKU("VV:GT 08-02780");
        priority += skuUnits * (float) 7.50;
        twoday += skuUnits * (float) 31.50;
        oneday += skuUnits * (float) 41.5;
        intl += skuUnits * (float) 31.25;
        canada += skuUnits * (float) 16.5;
    }
    if (currorder.containsSKU("VV:GT 08-02779")) {
        //6	30	40	48.3	15
        long skuUnits = currorder.getQuantityForSKU("VV:GT 08-02779");
        priority += skuUnits * (float) 6;
        twoday += skuUnits * (float) 30;
        oneday += skuUnits * (float) 40;
        intl += skuUnits * (float) 48.3;
        canada += skuUnits * (float) 15;
    }
    if (currorder.containsSKU("VV:GT 08-01707")) {
        //6	30	40	48.3	15
        long skuUnits = currorder.getQuantityForSKU("VV:GT 08-01707");
        priority += skuUnits * (float) 6;
        twoday += skuUnits * (float) 30;
        oneday += skuUnits * (float) 40;
        intl += skuUnits * (float) 48.3;
        canada += skuUnits * (float) 15;
    }
    if (currorder.containsSKU("VV:GT 08-01707-FAN")) {
        //5	20	27	35	10
        long skuUnits = currorder.getQuantityForSKU("VV:GT 08-01707-FAN");
        priority += skuUnits * (float) 5;
        twoday += skuUnits * (float) 20;
        oneday += skuUnits * (float) 27;
        intl += skuUnits * (float) 35;
        canada += skuUnits * (float) 10;
    }

    if (currorder.containsSKU("VV:GT 05-50835")) {
        //5	20	27	35	10
        long skuUnits = currorder.getQuantityForSKU("VV:GT 05-50835");
        priority += skuUnits * (float) 16;
        twoday += skuUnits * (float) 48;
        oneday += skuUnits * (float) 53;
        intl += skuUnits * (float) 82;
        canada += skuUnits * (float) 26;
    }

    if (currorder.containsSKU("VV:GT 05-50836")) {
        //5	20	27	35	10
        long skuUnits = currorder.getQuantityForSKU("VV:GT 05-50836");
        priority += skuUnits * (float) 16;
        twoday += skuUnits * (float) 48;
        oneday += skuUnits * (float) 53;
        intl += skuUnits * (float) 82;
        canada += skuUnits * (float) 26;
    }

    //6  27  37  35  13
    if (currorder.containsSKU("VV:GT 05-51761")) {
        long skuUnits = currorder.getQuantityForSKU("VV:GT 05-51761");
        priority += skuUnits * (float) 6;
        twoday += skuUnits * (float) 27;
        oneday += skuUnits * (float) 37;
        intl += skuUnits * (float) 35;
        canada += skuUnits * (float) 13;
    }


    if (carrier.indexOf("First Class") >= 0) {
        currorder.total_shipping_cost = priority;
        if (units < 2) {
            currorder.getShippingInfo().setShipOptions("First Class", "Prepaid", "");
        } else {
            currorder.getShippingInfo().setShipOptions("Priority Mail", "Prepaid", "");

        }

    } else if (carrier.indexOf("Priority Mail") >= 0) {
        currorder.total_shipping_cost = priority;
        if (units < 2) {
            currorder.total_shipping_cost += 1.50;
        }
        currorder.getShippingInfo().setShipOptions("Priority Mail", "Prepaid", "");

    } else if (carrier.indexOf("UPS Next Day Air Saver") >= 0) {
        currorder.total_shipping_cost = oneday;
        currorder.getShippingInfo().setShipOptions("UPS Next Day Air Saver", "Prepaid", "");
    } else if (carrier.indexOf("UPS 2nd Day Air") >= 0) {
        currorder.total_shipping_cost = twoday;
        currorder.getShippingInfo().setShipOptions("UPS 2nd Day Air", "Prepaid", "");

    } else if (carrier.indexOf("m Packet Air") > 0) {
        if (ts(currorder.getShippingAddress().country).equals("CANADA"))
            currorder.total_shipping_cost = canada;
        else
            currorder.total_shipping_cost = intl;

        currorder.getShippingInfo().setShipOptions("Int\'l Letter-Post/Sm Packet Air", "Prepaid", "");
    } else {
        currorder.total_shipping_cost = priority;
    }

    if (units < 1) currorder.total_shipping_cost = (float) 0;

    if ("coupon".equals((String) request.getParameter("editaction"))) {

        String coup = (String) request.getParameter("coupon");
        if (coup == null) coup = "";
        currorder.coupon_code = coup.trim();
    }
    String coupMessage = "";
    String disctype = "";
    currorder.discount = 0;

    currorder.tax_pct = (float) 0.000000;
    currorder.total_tax_cost = (float) 0.000000;

    if ("TX".equalsIgnoreCase(currorder.getShippingAddress().state) || "Texas".equalsIgnoreCase(currorder.getShippingAddress().state)) {
        currorder.tax_pct = (float) 0.067500;
        currorder.total_tax_cost = (float) 0.067500 * (currorder.total_shipping_cost + currorder.total_product_cost);
    }
    if ("SD".equalsIgnoreCase(currorder.getShippingAddress().state) || "South Dakota".equalsIgnoreCase(currorder.getShippingAddress().state)) {
        currorder.tax_pct = (float) 0.060000;
        currorder.total_tax_cost = (float) 0.060000 * (currorder.total_shipping_cost + currorder.total_product_cost);
    }
    if ("SC".equalsIgnoreCase(currorder.getShippingAddress().state) || "South Carolina".equalsIgnoreCase(currorder.getShippingAddress().state)) {
        currorder.tax_pct = (float) 0.060000;
        currorder.total_tax_cost = (float) 0.060000 * (currorder.total_shipping_cost + currorder.total_product_cost);
    }


    if (currorder.coupon_code != null) {
        if (currorder.coupon_code.trim().length() > 0) {

            Coupon coupon = null;
            try {
                if (currorder.getShippingAddress().country.equals("USA")) {
                    currorder.discount = Coupon.getOrderDiscount(currorder.total_product_cost, priority, currorder.coupon_code, currorder.clientID, currorder.skuList, currorder.getShippingAddress());
                } else if (currorder.getShippingAddress().country.equals("CANADA")) {
                    if (currorder.coupon_code.trim().equalsIgnoreCase("ACTIVEHOLIDAYS")) {
                        currorder.discount = Coupon.getOrderDiscount(currorder.total_product_cost, priority, currorder.coupon_code, currorder.clientID, currorder.skuList, currorder.getShippingAddress());
                    } else {
                        currorder.discount = Coupon.getOrderDiscount(currorder.total_product_cost, canada, currorder.coupon_code, currorder.clientID, currorder.skuList, currorder.getShippingAddress());
                    }
                } else {
                    if (currorder.coupon_code.trim().equalsIgnoreCase("ACTIVEHOLIDAYS")) {
                        currorder.discount = Coupon.getOrderDiscount(currorder.total_product_cost, priority, currorder.coupon_code, currorder.clientID, currorder.skuList, currorder.getShippingAddress());
                    } else {
                        currorder.discount = Coupon.getOrderDiscount(currorder.total_product_cost, intl, currorder.coupon_code, currorder.clientID, currorder.skuList, currorder.getShippingAddress());
                    }
                }


                currorder.recalculateBalance();
                if (currorder.discount > 0) {
                    switch (coupon.coupon_type) {
                        case Coupon.kCouponTypeAmount:

                            disctype = "(credit&nbsp;for&nbsp;order) ";

                            break;
                        case Coupon.kCouponTypePercent:

                            disctype = "(" + (coupon.disc_pct * 100) + "%&nbsp;discount&nbsp;on&nbsp;product&nbsp;subtotal) ";

                            break;
                        case Coupon.kCouponTypeShipAmt:

                            disctype = "(credit&nbsp;toward&nbsp;shipping) ";

                            break;
                        case Coupon.kCouponTypeShipPct:

                            disctype = "(" + (coupon.ship_pct * 100) + "%&nbsp;discount&nbsp;on&nbsp;standard&nbsp;shipping) ";

                            break;
                    }
                }

            } catch (Exception excp) {
                coupMessage = excp.getMessage();
            }
        } else {
            currorder.discount = 0;
        }
    }


    currorder.recalculateBalance();

    if ("submit".equals((String) request.getParameter("editaction"))) {
        if (units < 1) {
            if (units < 1) {
                errorMessage = "No items in order : order not saved<BR>";
            } else {
                errorMessage = "Shipping choice not made - edit customer info to set shipping. : order not saved<BR>";
            }
        } else {


            currorder.backorder_rule = (String) request.getParameter("backorder_rule");
            if (null == currorder.backorder_rule || "null".equals(currorder.backorder_rule))
                currorder.backorder_rule = OrderXMLDoc.kPartialShip;


            currorder.payment_status = OrderXMLDoc.kPaymentStatusWaitingForPayment;
            currorder.ship_call_date = OWDUtilities.getSQLDateForToday();
            currorder.ship_operator = "Call Center";
            currorder.order_type = "OWD Call Center";


            String orderNum = "";
            boolean isGroundOverride = false;

            for (int i = 0; i < currorder.skuList.size(); i++) {
                LineItem item = (LineItem) currorder.skuList.elementAt(i);
            }


            if (currorder.containsSKU("VV:GT 08-83062")) {
                isGroundOverride = true;

            }
            if (currorder.containsSKU("VV:GT 08-83061")) {
                isGroundOverride = true;
            }
            if (currorder.containsSKU("VV:GT 08-02780")) {
                isGroundOverride = true;
            }
            if (currorder.containsSKU("VV:GT 08-02779")) {
                isGroundOverride = true;
            }
            if (currorder.containsSKU("VV:GT 08-01707")) {
                isGroundOverride = true;
            }
            if (currorder.containsSKU("VV:GT 08-01707-FAN")) {
                isGroundOverride = true;
            }

            if (currorder.containsSKU("VV:GT 08-03521")) {
                isGroundOverride = true;
            }
            if (currorder.containsSKU("VV:GT 08-83347")) {
                isGroundOverride = true;
            }
            if (currorder.containsSKU("VV:GT 08-83357")) {
                isGroundOverride = true;
            }
            if (currorder.containsSKU("VV:GT 08-03535")) {
                isGroundOverride = true;
            }

            if (currorder.containsSKU("VV:GT 05-50835")) {
                isGroundOverride = true;
            }
            if (currorder.containsSKU("VV:GT 05-50836")) {
                isGroundOverride = true;
            }
            if (currorder.containsSKU("VV:GT 05-51761")) {
                isGroundOverride = true;
            }


            if (isGroundOverride &&
                    (currorder.getShippingInfo().carr_service.toUpperCase().indexOf("PRIORITY") >= 0 ||
                            currorder.getShippingInfo().carr_service.toUpperCase().indexOf("FIRST CLASS") >= 0)
                    && currorder.getShippingAddress().country.equalsIgnoreCase("USA")) {
                currorder.getShippingInfo().setShipOptions("UPS Ground", "Prepaid", "");
            }

            if (isGroundOverride && currorder.getShippingAddress().country.equalsIgnoreCase("CANADA")) {
                currorder.getShippingInfo().setShipOptions("UPS Standard Canada", "Prepaid", "");
            }

            currorder.recalculateBalance();

            if (currorder.total_order_cost > 100 &&
                    (currorder.getShippingInfo().carr_service.toUpperCase().indexOf("PRIORITY") >= 0 ||
                            currorder.getShippingInfo().carr_service.toUpperCase().indexOf("FIRST CLASS") >= 0 ||
                            currorder.getShippingInfo().carr_service.toUpperCase().indexOf("UPS GROUND") >= 0 ||
                            currorder.getShippingInfo().carr_service.toUpperCase().indexOf("SM PACKET AIR") >= 0)
                    && (currorder.getShippingAddress().country.equalsIgnoreCase("CANADA") ||
                    currorder.getShippingAddress().country.equalsIgnoreCase("USA"))) {
                currorder.getShippingInfo().setShipOptions("UPS Ground", "Prepaid", "");
                if (currorder.getShippingAddress().country.equalsIgnoreCase("CANADA")) {
                    currorder.getShippingInfo().setShipOptions("UPS Standard Canada", "Prepaid", "");
                }
            }


            if (currorder.total_order_cost > 200) {
                currorder.is_future_ship = 1;
                currorder.forcePayment = true;
                currorder.backorder_rule = OrderXMLDoc.kBackOrderAll;
                currorder.hold_reason = currorder.hold_reason + ": " + "Order total over 200.00 - held for fraud review";
            }

            if (!(currorder.getShippingAddress().country.equals("USA")) && !(currorder.getShippingAddress().country.equals("CANADA"))) {
                boolean intlHoldClear = false;

                if (country.contains(currorder.getShippingAddress().country) &&
                        currorder.getShippingAddress().address_one.equals(currorder.getBillingAddress().address_one) &&
                        currorder.getShippingAddress().address_two.equals(currorder.getBillingAddress().address_two) &&
                        currorder.getShippingAddress().city.equals(currorder.getBillingAddress().city) &&
                        currorder.getShippingAddress().zip.equals(currorder.getBillingAddress().zip) &&
                        currorder.getShippingAddress().country.equals(currorder.getBillingAddress().country))
                //todo check needs to be for matching addresses in addition to shipping country in list
                {
                    //order less than 100
                    if (currorder.total_order_cost < 100.00) {
                        intlHoldClear = true;
                    }


                }

                if (!intlHoldClear) {
                    //international
                    currorder.is_future_ship = 1;
                    currorder.forcePayment = true;
                    currorder.hold_reason = currorder.hold_reason + ": International order - held for fraud review";
                    currorder.backorder_rule = OrderXMLDoc.kBackOrderAll;
                }
            } else {
                //US or canada
                currorder.forcePayment = true;
                currorder.backorder_rule = OrderXMLDoc.kBackOrderAll;


            }


            if (currorder.bill_cc_type.equals("CK")) {
                currorder.saveNewOrder(com.owd.core.business.order.OrderUtilities.kHoldForPayment, false);
            } else {
                currorder.saveNewOrder(OrderUtilities.kRequirePayment, false);
            }

            orderNum = currorder.order_refnum;
            if (orderNum == null) {
                //order not saved
                errorMessage = "Order not completed: " + currorder.last_error;

            } else {
                //order OK
                errorMessage = "Order Complete! Reference is: " + orderNum;
                trackEvent = "SalePhone";
                if (currorder.bill_cc_type.equals("CK")) {
                    trackEvent = "SalePhoneCheck";
                }

                if (currorder.is_future_ship == 1) {
                    errorMessage = errorMessage + "<BR>Order placed on hold: " + currorder.last_error + " " + currorder.hold_reason;
                    StringBuffer sb = new StringBuffer();
                    StringBuffer sub = new StringBuffer();

                    sub.append("AV Call Center Order (" + (currorder.order_refnum == null ? currorder.old_refnum : currorder.order_refnum) + " On Hold" + (currorder.hold_reason.indexOf("fraud review") >= 0 ? " for Review" : "") + ")");
                    sb.append("This order was placed but put on hold. The most likely reason is:\r\n\r\n");
                    sb.append(currorder.last_payment_error + " " + currorder.last_error + ": " + currorder.hold_reason + "");


                    sb.append("\r\n\r\nOrder Report - Ref# " + currorder.order_refnum + " for Call Center");
                    if (currorder.backorder_order_num != null) {
                        sb.append("\r\nPartial backorder created as reference: " + currorder.backorder_order_num);
                    }
                    sb.append("\r\nCustomer: " + currorder.getBillingContact().name);
                    sb.append("\r\nCustomer Email: " + currorder.getBillingContact().email);
                    sb.append("\r\nCustomer Phone: " + currorder.getBillingContact().phone);
                    sb.append("\r\nCustomer City:State:Zip: " + currorder.getBillingAddress().city + ":" + currorder.getBillingAddress().state + ":" + currorder.getBillingAddress().zip);
                    sb.append("\r\nCustomer Country: " + currorder.getBillingAddress().country);
                    sb.append("\r\nDelivery Name: " + currorder.getShippingContact().name);
                    sb.append("\r\nDelivery City:State:Zip: " + currorder.getShippingAddress().city + ":" + currorder.getShippingAddress().state + ":" + currorder.getShippingAddress().zip);
                    sb.append("\r\nDelivery Country: " + currorder.getShippingAddress().country);
                    sb.append("\r\nShip Method: " + currorder.getShippingInfo().carr_service);
                    sb.append("\r\nTotal: " + currorder.total_order_cost);
                    sb.append("\r\nBalance Owed: " + currorder.order_balance);
                    if (currorder.coupon_code.length() > 0) {
                        sb.append("\r\nCoupon Code Used: " + currorder.coupon_code);
                    }
                    if (currorder.po_num.length() > 0) {
                        sb.append("\r\nCommission Goes To: " + currorder.po_num);
                    }
                    sb.append("\r\nItems: \r\n");
                    for (int i = 0; i < currorder.skuList.size(); i++) {
                        LineItem item = (LineItem) currorder.skuList.elementAt(i);
                        sb.append("\r\nReq:" + (item.quantity_request + item.quantity_backordered) + " BO:" + item.quantity_backordered + " Desc:" + item.description + " SKU:" + item.client_part_no + " @ " + item.sku_price);
                    }

                    sb.append("\r\nOut of Stock Items: \r\n");
                    for (int i = 0; i < currorder.skuList.size(); i++) {
                        LineItem item = (LineItem) currorder.skuList.elementAt(i);
                        int available = OrderUtilities.getAvailableInventory(item.inventory_fkey, FacilitiesManager.getFacilityForCode(currorder.getFacilityCode()).getId());
                        if (available < 0) available = 0;
                        if ((item.quantity_request + item.quantity_backordered) > available) {
                            sb.append("\r\nRequested:" + (item.quantity_request + item.quantity_backordered) + " Available:" + available + " Shortfall:" + ((item.quantity_request + item.quantity_backordered) - available) + " SKU:" + item.client_part_no);
                        }

                    }

                    //sb.append("\r\n\rMessage: "+cart.getOrder().getShippingInfo().comments);


                    Vector emails = new Vector();


                    emails.addElement("avorders@activevideos.com");


                    Mailer.postMailMessage(sub.toString(), sb.toString(), emails, "do-not-reply@owd.com");

                    StringBuffer mailSummary = new StringBuffer();
                    mailSummary.append("Mail To: ");
                    for (int mx = 0; mx < emails.size(); mx++) {
                        mailSummary.append(((String) emails.elementAt(mx)) + "\n");
                    }
                    mailSummary.append("Subject: " + sub.toString() + "\n");
                    mailSummary.append(sb.toString());

                    Event.addOrderEvent(new Integer(currorder.orderID).intValue(), Event.kEventTypeEmailSent, mailSummary.toString(), null);
                }

            }


        }


    }
%>
<head>
<title>Call Center Order Creation</title>
</head>
<body bgcolor=#FFFFFF>
<% if (trackEvent != null)
{
%><!--BEGIN ROI TRACKING Code-->
<script language="javascript" src="https://track.roiservice.com/track/track.aspx?ROIID=937433107743318"></script>
<script language="javascript">
<!--
	if (typeof(ROIID) + '' != 'undefined') {
		TrackEvent('<%= trackEvent %>', '<%= currorder.total_order_cost%>','<%= currorder.order_refnum%>');
	}
//-->
</script>
<!--END ROI TRACKING Code-->
<%
}
%>
<CENTER><font face="Geneva, Verdana, Helvetica" size=-1><B>Call Center Order Creation</B></font></CENTER>
<HR>

<TABLE border=0 cellpadding=0 cellspacing=2 width=100%>
<TR>
<TD ALIGN=CENTER valign=top width=50% bgcolor="#000000"><font face="Geneva, Verdana, Helvetica"  color="#FFFFFF" size=-1><B>Bill To:</B></TD>
<TD ALIGN=CENTER valign=top width=50% bgcolor="#000000"><font face="Geneva, Verdana, Helvetica"  color="#FFFFFF" size=-1><B>Ship To:</B></TD>
</TR><TR>
<TD ALIGN=LEFT valign=top><font face="Geneva, Verdana, Helvetica" size=-1>&nbsp;<BR><%= billContact.name %><BR><%= billingAddress.company_name %><BR><%= billingAddress.address_one %><BR><%= billingAddress.address_two %><BR><%= billingAddress.city %>,<%= billingAddress.state %> <%= billingAddress.zip %><BR><%= billingAddress.country %><P><%= billContact.phone %><BR><%= billContact.email %><P><B>Payment Info:</B><BR><%= currorder.bill_cc_type %> - <%= currorder.cc_num %> - Exp <%= currorder.cc_exp_mon %>/<%= currorder.cc_exp_year %></TD>

<TD ALIGN=LEFT valign=top><font face="Geneva, Verdana, Helvetica" size=-1>&nbsp;<BR><%= shipContact.name %><BR><%= shippingAddress.company_name %><BR><%= shippingAddress.address_one %><BR><%= shippingAddress.address_two %><BR><%= shippingAddress.city %>,<%= shippingAddress.state %> <%= shippingAddress.zip %><BR><%= shippingAddress.country %><P><%= shipContact.phone %><BR><%= shipContact.email %><P><B>Shipping:</B>&nbsp;<%= carrier %></TD>
</TR>
<% if (!currorder.completed)
{
%>
<TR><TD COLSPAN=2 ALIGN=RIGHT><FORM METHOD=POST ACTION=editcustomer.jsp><HR><INPUT TYPE=SUBMIT NAME=SUBMIT VALUE="Edit Customer Info"></FORM></TD></TR>
<% } %>
</TABLE>
<TABLE border=0 cellpadding=0 cellspacing=0 width=100%>
<TR><TD COLSPAN=5><HR></TD></TR>
<TR><TD ALIGN=LEFT>
<% if (!currorder.completed)
{
%>
<font face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;Units&nbsp;</B></TD><TD ALIGN=LEFT>
<% } else{%>
<font face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;Requested-Backordered&nbsp;</B></TD><TD ALIGN=LEFT>
<% }%>
<font face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;Reference&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</B></TD><TD ALIGN=LEFT WIDTH=100%>
<font face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;(Available)&nbsp;Description&nbsp;</B></TD><TD ALIGN=LEFT>
<font face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;Each&nbsp;</B></TD><TD ALIGN=LEFT>
<font face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;Total&nbsp;</B></TD></TR>

<TR><TD COLSPAN=5><HR></TD></TR>
<%
	//Vector skus = currorder.skuList;
	//
	int backorderCount = 0;
	
	for(int i=0;i<skus.size();i++)
	{
		long boAmount = 0;
		LineItem item = (LineItem) skus.elementAt(i);
		boolean isBack = false;
		
			if(currorder.completed)
			{
				boAmount = item.quantity_backordered;
			}else{
				boAmount = item.quantity_request-OrderUtilities.getAvailableInventory(item.inventory_fkey,FacilitiesManager.getFacilityForCode(currorder.getFacilityCode()).getId());
			}
			if(boAmount > 0)
			{
				backorderCount++;isBack=true;
			}
		
%>
<TR><TD ALIGN=LEFT>
<font face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;<%= item.quantity_request+item.quantity_backordered %>
<%
if(currorder.completed)
{
%>
- <%= item.quantity_backordered %>
<% } %>
</B></TD><TD ALIGN=LEFT>
<font face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;<%= item.client_part_no %></B></TD><TD ALIGN=LEFT WIDTH=100%>
<font face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;(<%= (isBack?"BACKORDER":"YES") %><%= (isBack?(" "+boAmount):"") %>)&nbsp;<%= item.description %></B></TD><TD ALIGN=LEFT>
<font face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;<%= dform.format(item.sku_price) %></B></TD><TD ALIGN=LEFT>
<font face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;<%= dform.format(item.total_price) %></B></TD></TR>
<% } %>
<% if (!currorder.completed)
{
%>
<TR><TD COLSPAN=5 ALIGN=RIGHT><FORM METHOD=POST ACTION=edititems.jsp><INPUT TYPE=SUBMIT NAME=SUBMIT VALUE="Edit Items"></FORM></TD></TR>
<% } %>
<TR><TD COLSPAN=5><HR></TD></TR>

<TR><TD COLSPAN=2>&nbsp;</TD><TD ALIGN=RIGHT>
<font face="Geneva, Verdana, Helvetica" size=-2><B>Product&nbsp;Total:&nbsp;</B></TD><TD><font face="Geneva, Verdana, Helvetica" size=-2>
 <%= dform.format(currorder.total_product_cost) %></TD></TR>
 

<FORM METHOD=POST ACTION=orderentry.jsp?editaction=coupon>
<TR><TD COLSPAN=2 VALIGN=CENTER><font face="Geneva, Verdana, Helvetica" size=-1>Coupon:&nbsp;<INPUT TYPE=TEXT SIZE=50 NAME="coupon" VALUE="<%=currorder.coupon_code%>" ><INPUT TYPE=SUBMIT NAME=APPLY VALUE="APPLY"></TD><TD ALIGN=RIGHT><font face="Geneva, Verdana, Helvetica" size=-2><B>Discount<%= disctype %>:&nbsp;</B></TD><TD><font face="Geneva, Verdana, Helvetica" size=-2>
 <%= dform.format(currorder.discount) %></TD></TR>
<TR><TD COLSPAN=2>&nbsp;<%=coupMessage%></TD><TD ALIGN=RIGHT><font face="Geneva, Verdana, Helvetica" size=-2><B>Tax:&nbsp;</B></TD><TD><font face="Geneva, Verdana, Helvetica" size=-2>
<%= dform.format(currorder.total_tax_cost) %></TD></TR>
<TR><TD COLSPAN=2>&nbsp;</TD><TD ALIGN=RIGHT><font face="Geneva, Verdana, Helvetica" size=-2><B>Shipping:&nbsp;</B></TD><TD><font face="Geneva, Verdana, Helvetica" size=-2>
<%= dform.format(currorder.total_shipping_cost) %></TD></TR>
<TR><TD COLSPAN=2>&nbsp;</TD><TD ALIGN=RIGHT><font face="Geneva, Verdana, Helvetica" size=-2><B>Grand&nbsp;Total:&nbsp;</B></TD><TD><font face="Geneva, Verdana, Helvetica" size=-2>
<%= dform.format(currorder.total_order_cost) %></TD></TR>
</TD></TR>
</FORM>
<TR><TD COLSPAN=5 ALIGN=RIGHT>&nbsp;<BR><font face="Geneva, Verdana, Helvetica" size=-1 COLOR=red>
<%= errorMessage %>
<% if(backorderCount >0 && currorder.completed)
	{
%>
<BR>Backorder Created
<%
	}
%>
<% if (!currorder.completed)
{
%>
<FORM METHOD=POST ACTION=orderentry.jsp?editaction=submit><B>PLEASE REPEAT ORDER INFORMATION TO CUSTOMER BEFORE SUBMITTING! </B></font>
<%
	if(backorderCount >0)
	{
		if (null==currorder.backorder_rule) currorder.backorder_rule=OrderXMLDoc.kPartialShip;
%>	
<P><B>Backorder Type:&nbsp;</B><SELECT NAME="backorder_rule">
<OPTION VALUE="<%= OrderXMLDoc.kPartialShip %>" <%= (currorder.backorder_rule.equals(OrderXMLDoc.kPartialShip)?"SELECTED":"") %>>Ship In Stock items Now
<OPTION VALUE="<%= OrderXMLDoc.kBackOrderAll %>" <%= (currorder.backorder_rule.equals(OrderXMLDoc.kBackOrderAll)?"SELECTED":"") %>>Backorder Entire Order
</SELECT><P>
<%	
	} 

%>
<BR><INPUT TYPE=SUBMIT NAME=SUBMIT VALUE="SUBMIT ORDER"></FORM>

<%
}
%><P><FORM METHOD=POST ACTION=orderentry.jsp?editaction=clear><INPUT TYPE=SUBMIT NAME=SUBMIT VALUE="Start New Order / Reset Order"></FORM></TD></TR>
</TABLE>

</FONT>

<HR>
 

</body>
</html>
<%!

public  String ts(String str)
 {
 
 	if (str == null) return "";
 	
 	return str.trim();
 }
 
				


%>


