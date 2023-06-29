package com.owd.jobs.clients

import com.owd.LogableException
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.CountryNames
import com.owd.core.Mailer
import com.owd.core.OWDUtilities
import com.owd.core.business.order.Inventory
import com.owd.core.business.order.LineItem
import com.owd.core.business.order.NewOrder
import com.owd.core.business.order.OrderUtilities
import com.owd.core.xml.OrderXMLDoc
import com.owd.jobs.OWDStatefulJob
import com.owd.jobs.SKUNotFoundException
import com.owd.jobs.jobobjects.corecommerce.CoreCommerceOrderService

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jun 8, 2010
 * Time: 10:28:21 PM
 * To change this template use File | Settings | File Templates.
 */
class AlgaeCoreCommerceImporter extends OWDStatefulJob {
    private final static Logger log =  LogManager.getLogger();

    def int clientID = 266

    static void main(String[] args) {

    //    println new Date().minus(10).time * 0.001
    //    println new Date().plus(2).time * 0.001
       run();
/*

    Node orderList2 = CoreCommerceOrderService.sendOrderShipmentNotification("https://store.brazillivecoral.com/admin/_callback.php", "oneworld", "1979159a", "brazilliveco154", "1979159a", 2,0,1);

      int orderCount = Integer.parseInt(orderList2.List.'@possibleLength'.get(0));

      orderList2 = CoreCommerceOrderService.sendOrderShipmentNotification("https://store.brazillivecoral.com/admin/_callback.php", "oneworld", "1979159a", "brazilliveco154", "1979159a", 2,orderCount-99,100);

      log.debug("Order count is: "+Integer.parseInt(orderList2.List.'@possibleLength'.get(0)));

    processOrderList(orderList2,"BL")*/

    }

    static void mainx(args) {
        def orderList3 = CoreCommerceOrderService.getOrderList("https://store.algaecal.com/admin/_callback.php", "oneworld", "1979159a", "algaecalstor702", "1979159a", 10, 0, 1);

        int orderCount = Integer.parseInt(orderList3.List.'@possibleLength'.get(0));
        while (orderCount > 0) {
            log.debug("ordercount=" + orderCount);

            if (orderCount >= 99) {
                orderCount -= 99;
            } else {
                orderCount = 0;
            }
            orderList3 = CoreCommerceOrderService.getOrderList("https://store.algaecal.com/admin/_callback.php", "oneworld", "1979159a", "algaecalstor702", "1979159a", 10, orderCount, 100);


            processOrderList(orderList3, "AC")
        }
/*
    Node orderList2 = CoreCommerceOrderService.sendOrderShipmentNotification("https://store.brazillivecoral.com/admin/_callback.php", "oneworld", "1979159a", "brazilliveco154", "1979159a", 2,0,1);

      int orderCount = Integer.parseInt(orderList2.List.'@possibleLength'.get(0));

      orderList2 = CoreCommerceOrderService.sendOrderShipmentNotification("https://store.brazillivecoral.com/admin/_callback.php", "oneworld", "1979159a", "brazilliveco154", "1979159a", 2,orderCount-99,100);

      log.debug("Order count is: "+Integer.parseInt(orderList2.List.'@possibleLength'.get(0)));

    processOrderList(orderList2,"BL")*/

    }

    public void internalExecute() {

        try {
            // mrhoades: change per case 1039640
            // def orderList = CoreCommerceOrderService.getOrderList("https://store.brazillivecoral.com/admin/_callback.php", "oneworld", "1979159a", "algaecalinc893", "1979159a", 10, 0, 1);
            def orderList = CoreCommerceOrderService.getOrderList("https://store.brazillivecoral.com/admin/_callback.php", "oneworld", "1979159a", "brazilliveco154", "1979159a", 10, 0, 1);

            println orderList
            int orderCount = Integer.parseInt(orderList.List.'@possibleLength'.get(0));
            while (orderCount > 0) {
                log.debug("ordercount=" + orderCount);

                if (orderCount >= 99) {
                    orderCount -= 99;
                } else {
                    orderCount = 0;
                }
                // mrhoades: change per case 1039640
                // orderList = CoreCommerceOrderService.getOrderList("https://store.brazillivecoral.com/admin/_callback.php", "oneworld", "1979159a", "algaecalinc893", "1979159a", 10, orderCount, 100);
                orderList = CoreCommerceOrderService.getOrderList("https://store.brazillivecoral.com/admin/_callback.php", "oneworld", "1979159a", "brazilliveco154", "1979159a", 10, orderCount, 100);
                processOrderList(orderList, "BL")

            }



            /*def orderList2 = CoreCommerceOrderService.getOrderList("https://store.brazillivecoral.com/admin/_callback.php", "oneworld", "1979159a", "brazilliveco154", "1979159a", 10, 0, 1);

            orderCount = Integer.parseInt(orderList2.List.'@possibleLength'.get(0));
            while (orderCount > 0) {
                log.debug("ordercount=" + orderCount);

                if (orderCount >= 99) {
                    orderCount -= 99;
                } else {
                    orderCount = 0;
                }
                orderList2 = CoreCommerceOrderService.getOrderList("https://store.brazillivecoral.com/admin/_callback.php", "oneworld", "1979159a", "brazilliveco154", "1979159a", 10, orderCount, 100);


                processOrderList(orderList2, "BL")

            }*/

           /* def orderList3 = CoreCommerceOrderService.getOrderList("https://www20.corecommerce.com/~algaecalstor702/admin/_callback.php", "oneworld", "1979159a", "algaecalstor702", "1979159a", 10, 0, 1);

            orderCount = Integer.parseInt(orderList3.List.'@possibleLength'.get(0));
            while (orderCount > 0) {
                log.debug("ordercount=" + orderCount);

                if (orderCount >= 99) {
                    orderCount -= 99;
                } else {
                    orderCount = 0;
                }
                orderList3 = CoreCommerceOrderService.getOrderList("https://www20.corecommerce.com/~algaecalstor702/admin/_callback.php", "oneworld", "1979159a", "algaecalstor702", "1979159a", 10, orderCount, 100);


                processOrderList(orderList3, "AC")

            }*/

        } catch (Exception ex) {
        if(!ex.getMessage().contains("FileNotFound")){
            new LogableException(ex,ex.message,"Import Error","266","CoreCommerce",LogableException.errorTypes.ORDER_IMPORT);
        }
            //
ex.printStackTrace();

        }
    }

    private static void processOrderList(Node orderList, String orderSuffix) {
        println orderList.Action.text()
        // println orderList.List.Order;

        println orderList.List.Order.size()

        if (orderList.Status.text() == "SUCCESS_CODE") {
            def orders = orderList.List.Order;
            //   println orders.getClass()

            //def stringWriter = new StringWriter()
            //def printWriter = new PrintWriter(stringWriter)
            //new XmlNodePrinter(printWriter).print(orderList)
            //  println stringWriter.toString()

            orders.each { gorder ->
                // println gorder.getClass()
                try {


                    println "checking order..."
                    NewOrder owdOrder = new NewOrder("266")
                    def refnum = gorder.Number.text() + "-" + orderSuffix
                    println "Exists:" + owdOrder.orderRefnumExists(refnum + "")
                    println "ref:" + refnum

                    if (!owdOrder.orderRefnumExists(refnum)) {
                        if (gorder.Status.text() == "ORDER_STATUS_APPROVED") {
                            try {
                                owdOrder.order_type = "CoreCommerce Import";//source of the order, who created the order
                                owdOrder.ship_call_date = OWDUtilities.getSQLDateForToday();
//date and time of importing orders
                                owdOrder.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                                //no payment processing
                                owdOrder.backorder_rule = OrderXMLDoc.kBackOrderAll;//default(safe): kBackOrderAll;
                                owdOrder.order_refnum = refnum

                                println refnum

                                owdOrder.getBillingAddress().company_name = gorder.BillingAddress.Company.text()
                                owdOrder.getBillingAddress().address_one = gorder.BillingAddress.Address1.text()
                                owdOrder.getBillingAddress().address_two = gorder.BillingAddress.Address2.text()
                                owdOrder.getBillingAddress().city = gorder.BillingAddress.City.text()
                                owdOrder.getBillingAddress().state = gorder.BillingAddress.State.text()
                                owdOrder.getBillingAddress().zip = gorder.BillingAddress.Zip.text()
                                owdOrder.getBillingAddress().country = CountryNames.getCountryName(gorder.BillingAddress.Country.text())

                                owdOrder.getBillingContact().name = gorder.BillingAddress.FirstName.text() + " " + gorder.BillingAddress.LastName.text()
                                owdOrder.getBillingContact().email = gorder.BillingAddress.Email.text()
                                owdOrder.getBillingContact().phone = gorder.Phone.text()

                                owdOrder.getShippingAddress().company_name = gorder.ShippingAddress.Company.text()
                                owdOrder.getShippingAddress().address_one = gorder.ShippingAddress.Address1.text()
                                owdOrder.getShippingAddress().address_two = gorder.ShippingAddress.Address2.text()
                                owdOrder.getShippingAddress().city = gorder.ShippingAddress.City.text()
                                owdOrder.getShippingAddress().state = gorder.ShippingAddress.State.text()
                                owdOrder.getShippingAddress().zip = gorder.ShippingAddress.Zip.text()
                                owdOrder.getShippingAddress().country = CountryNames.getCountryName(gorder.ShippingAddress.Country.text())

                                owdOrder.getShippingContact().name = gorder.ShippingAddress.FirstName.text() + " " + gorder.ShippingAddress.LastName.text()
                                owdOrder.getShippingContact().email = gorder.ShippingAddress.Email.text()

                                def carrsvc = gorder.ShippingMethod.text()
                                println carrsvc





                                gorder.Items.Item.each { itemx ->
                                    boolean isWholesale = false;
                                    boolean addHalfCaseCC90 = false;
                                    String sku = itemx.Sku.text();
                                    if (sku.equals("AlgaeCal D3-60")) {
                                        sku = "AlgaeCal+D3-60"
                                    }

                                    if (sku.endsWith("-WS"))//"CC90-WS".equals(sku))
                                    {
                                        sku = sku.substring(0, sku.indexOf("-WS"));
                                        isWholesale = true;
                                    }

                                    if (sku.indexOf("-RC") >= 0) {
                                        sku = sku.substring(0, sku.indexOf("-RC"));
                                    }
                                    if (sku.equals("Brazil 90C-18")) {
                                        sku = "Brazil 90C-12";
                                    }
                                    if (sku.equals("CPOW110") || sku.equalsIgnoreCase("CC147g")) {
                                        owdOrder.addLineItem("SCOOP", itemx.Quantity.text(), "0.00", "0.00", "Scoop", "", "");
                                    }

                                    if (addHalfCaseCC90) {
                                        owdOrder.addLineItem("Brazil 90C-6", itemx.Quantity.text(), "0.00", "0.00", "Special Offer", "", "");
                                    }
                                    println "cost:" + itemx.Price.Amount.text()

                                    owdOrder.addLineItem(sku, itemx.Quantity.text(), itemx.Price.Amount.text(), "0.00", itemx.Name.text(), "", "")
                                }
                                println "d:" + gorder.Discount.Amount.text()
                                println "sh:" + gorder.ShippingAndHandling.Amount.text()
                                owdOrder.total_shipping_cost = Double.parseDouble(gorder.ShippingAndHandling.Amount.text())
                                owdOrder.total_tax_cost = 0.00
                                owdOrder.discount = Double.parseDouble(gorder.Discount.Amount.text())
                                owdOrder.recalculateBalance()

                                if (owdOrder.containsSKU("AlgaeCal Plus") || owdOrder.containsSKU("8-Pack AlgaeCal Plus") || owdOrder.containsSKU("Case of AlgaeCal 24 Btls")) {
                                    if (!owdOrder.containsSKU("Brochures-AlgaeCal")) {
                                        owdOrder.addInsertItemIfAvailable("Brochures-AlgaeCal", 1);
                                    }
                                }
                                if (owdOrder.containsSKU("The Kitchen Sink")) {
                                    owdOrder.addInsertItemIfAvailable("Brochure - TKS", 1);
                                }
                                if (owdOrder.containsSKU("CC147g") || owdOrder.containsSKU("CC120")) {
                                    owdOrder.addInsertItemIfAvailable("Brochure-TB", 1);
                                }
                                if (owdOrder.containsSKU("Strontium") || owdOrder.containsSKU("Case of Strontium 24 Btls")) {
                                    if (!owdOrder.containsSKU("Brochures-AlgaeCal")) {
                                        owdOrder.addInsertItemIfAvailable("Brochures-AlgaeCal", 1);
                                    }
                                }


                                owdOrder.getShippingInfo().setShipOptions("Priority Mail", "Prepaid", "");

                                if (owdOrder.getShippingAddress().country.equalsIgnoreCase("United States"))
                                    owdOrder.getShippingAddress().country = "USA";
                                if (owdOrder.getBillingAddress().country.equalsIgnoreCase("United States"))
                                    owdOrder.getBillingAddress().country = "USA";

                                if (owdOrder.getShippingAddress().country.equalsIgnoreCase("CHINA")) {
                                    owdOrder.getShippingAddress().country = "CHINA PEOPLES REPUBLIC OF";
                                }


                                float pkgWgt = 0;
                                for (int j = 0; j < owdOrder.skuList.size(); j++) {
                                    LineItem iteml = (LineItem) owdOrder.skuList.elementAt(j);
                                    pkgWgt += (iteml.quantity_request) * Inventory.getKittedWeight(iteml.inventory_fkey);

                                }

                                //////log.debug("got pkg wgt "+pkgWgt);
                                if (pkgWgt < ((float) 0.8)) {
                                    owdOrder.getShippingInfo().setShipOptions("First Class", "Prepaid", "");
                                }

                                if (pkgWgt >= ((float) 2.0)) {
                                    owdOrder.getShippingInfo().setShipOptions("FedEx Ground", "Prepaid", "");
                                }


                                if (owdOrder.getShippingAddress().country.equalsIgnoreCase("BRUNEI DARUSSALAM")) {
                                    owdOrder.getShippingAddress().country = "BRUNEI";
                                }

                                //String cn = CountryNames.getCountryName(anOrder.getShippingAddress().country);
                                if (!(owdOrder.getShippingAddress().isUS())) {
                                    owdOrder.getShippingInfo().setShipOptions("USPS Priority Mail Express International", "Prepaid", "");
                                    owdOrder.is_future_ship = 1;
                                }



                                if (owdOrder.total_order_cost > 500.00f) {
                                    owdOrder.is_future_ship = 1;
                                }

                                String state = owdOrder.getShippingInfo().shipAddress.state;
                                if (state.equalsIgnoreCase("HI") ||
                                        state.equalsIgnoreCase("AK") ||
                                        state.equalsIgnoreCase("Alaska") ||
                                        state.equalsIgnoreCase("Hawaii")) {
                                    owdOrder.is_future_ship = 1;
                               //     Mailer.sendMail("HL Distribution Order Imported On Hold: " + owdOrder.order_refnum, "Order Reference: " + owdOrder.order_refnum + "\r\nPlaced on hold due to HI or AK shipping destination", "jeanne.roberts@shaw.ca", "do-not-reply@owd.com");


                                }

                                if (owdOrder.getShippingAddress().isPOAPO() && (owdOrder.getShippingAddress().isInternational() || owdOrder.getShippingInfo().carr_service.toUpperCase().indexOf("FEDEX") >= 0
                                        || owdOrder.getShippingInfo().carr_service.toUpperCase().indexOf("UPS") >= 0)) {
                                    owdOrder.is_future_ship = 1;
                               //     Mailer.sendMail("HL Distribution Order Imported On Hold: " + owdOrder.order_refnum, "Order Reference: " + owdOrder.order_refnum + "\r\nPlaced on hold due to possible PO or APO address for Intl/UPS/FedEx order", "hlorders@shaw.ca", "do-not-reply@owd.com");
                               //     Mailer.sendMail("HL Distribution Order Imported On Hold: " + owdOrder.order_refnum, "Order Reference: " + owdOrder.order_refnum + "\r\nPlaced on hold due to possible PO or APO address for Intl/UPS/FedEx order", "jeanne.roberts@shaw.ca", "do-not-reply@owd.com");


                                }

                                owdOrder.postDateHoursDelay = 24;
                                if (orderSuffix.equals("CC")) {
                                    owdOrder.prt_invoice_reqd = 1;
                                    owdOrder.prt_pack_reqd = 0;
                                    owdOrder.prt_pick_reqd = 0;
                                } else if (orderSuffix.equals("BL")) {
                                    owdOrder.prt_invoice_reqd = 0;
                                    owdOrder.prt_pack_reqd = 0;
                                    owdOrder.prt_pick_reqd = 1;


                                }
                                //process order
                                String reference = owdOrder.saveNewOrder(OrderUtilities.kHoldForPayment, false);
                                log.debug("saved " + reference);

                            } catch (SKUNotFoundException exsku) {
                                exsku.printStackTrace();


                                String[] skuemails = new String[1];
                                skuemails[0] = ("jeanne.roberts@shaw.ca");

                                Mailer.sendMail("Algaecal import error", exsku.getMessage(), skuemails, "do-not-reply@owd.com");
                            }
                        } else {
                            println gorder.Status.text()
                        }
                    } else {
                        println "Skipping ref ${gorder.Number.text()} as duplicate..."
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            //error response
        }
    }

}
/*    
  <Response>
  <Action>
    ACTION_TYPE_ORDER_LIST
  </Action>
  <Status>
    SUCCESS_CODE
  </Status>
  <Code/>
  <Messages/>
  <List length="1" possibleLength="1">
    <Order id="1">
      <Number>
        1274891691-483
      </Number>
      <BillingAddress>
        <FirstName>
          Jeanne
        </FirstName>
        <LastName>
          Roberts
        </LastName>
        <Address1>
          2805 1323 Homer St
        </Address1>
        <Address2/>
        <City>
          Vancouver
        </City>
        <State>
          BC
        </State>
        <Zip>
          v6b5t1
        </Zip>
        <Email>
          jamie.f@algaecal.com
        </Email>
        <Company>
          AlgaeCal
        </Company>
        <County/>
        <Country>
          CA
        </Country>
      </BillingAddress>
      <ShippingAddress>
        <FirstName>
          Jeanne
        </FirstName>
        <LastName>
          Roberts
        </LastName>
        <Address1>
          2805 1323 Homer St
        </Address1>
        <Address2/>
        <City>
          Vancouver
        </City>
        <State>
          BC
        </State>
        <Zip>
          v6b5t1
        </Zip>
        <Email>
          jamie.f@algaecal.com
        </Email>
        <Company>
          AlgaeCal
        </Company>
        <County/>
        <Country>
          CA
        </Country>
      </ShippingAddress>
      <Phone>
        3109614151
      </Phone>
      <Fax/>
      <IpAddress>
        24.83.164.4
      </IpAddress>
      <CustomFields length="0"/>
      <TransactionId>
        2655-0_31
      </TransactionId>
      <ShippingService/>
      <ShippingMethod>
        Canada
      </ShippingMethod>
      <TotalWeight>
        <Amount>
          0.39
        </Amount>
        <Units>
          LBS
        </Units>
      </TotalWeight>
      <ShippingWeight>
        <Amount>
          0.3900
        </Amount>
        <Units>
          LBS
        </Units>
      </ShippingWeight>
      <Payment>
        <PaymentMethod>
          PAYMENT_CREDIT_CARD_TYPE
        </PaymentMethod>
        <Number/>
        <Expiration>
          <UnixTimeStamp>
            1359698400
          </UnixTimeStamp>
          <Day>
            01
          </Day>
          <Month>
            02
          </Month>
          <Year>
            2013
          </Year>
          <Hour>
            00
          </Hour>
          <Minute>
            00
          </Minute>
          <Second>
            00
          </Second>
        </Expiration>
        <Type>
          Visa
        </Type>
        <Code/>
        <Name>
          Jeanne Roberts
        </Name>
        <StartDate/>
        <IssueNumber/>
      </Payment>
      <Items length="1">
        <Item>
          <Quantity>
            1
          </Quantity>
          <Sku>
            CC120
          </Sku>
          <Vendor id="0"/>
          <ProductId>
            20
          </ProductId>
          <Name>
            TrueBlue Coral Calcium Capsules
          </Name>
          <Options length="0"/>
          <Status>
            ORDER_ITEM_STATUS_REGULAR
          </Status>
          <ShippedAmount>
            0
          </ShippedAmount>
          <Price>
            <Amount>
              27.95
            </Amount>
          </Price>
          <ExtraPrice>
            <Amount/>
          </ExtraPrice>
          <Weight>
            <Amount>
              0.39
            </Amount>
            <Units>
              LBS
            </Units>
          </Weight>
          <Taxable>
            FALSE_VALUE
          </Taxable>
          <ProductCost>
            <Amount>
              0.00
            </Amount>
          </ProductCost>
          <OptionCost>
            <Amount>
              0.00
            </Amount>
          </OptionCost>
          <AddedToCart>
            <UnixTimeStamp>
              1274891347
            </UnixTimeStamp>
            <Day>
              26
            </Day>
            <Month>
              05
            </Month>
            <Year>
              2010
            </Year>
            <Hour>
              11
            </Hour>
            <Minute>
              29
            </Minute>
            <Second>
              07
            </Second>
          </AddedToCart>
          <ShipExempt>
            FALSE_VALUE
          </ShipExempt>
          <SubscriptionEligible>
            TRUE_VALUE
          </SubscriptionEligible>
        </Item>
      </Items>
      <SubTotal>
        <Amount>
          27.95
        </Amount>
      </SubTotal>
      <TaxRates length="0"/>
      <Discount>
        <Amount>
          0.00
        </Amount>
      </Discount>
      <ShippingAndHandling>
        <Amount>
          9
        </Amount>
      </ShippingAndHandling>
      <GrandTotal>
        <Amount>
          36.95
        </Amount>
      </GrandTotal>
      <Status>
        ORDER_STATUS_APPROVED
      </Status>
      <ShippingDate>
        <UnixTimeStamp>
          1274850000
        </UnixTimeStamp>
        <Day>
          26
        </Day>
        <Month>
          05
        </Month>
        <Year>
          2010
        </Year>
        <Hour>
          00
        </Hour>
        <Minute>
          00
        </Minute>
        <Second>
          00
        </Second>
      </ShippingDate>
      <TrackingElements length="0"/>
      <Memo/>
      <Note/>
      <NoteToCustomer/>
      <UpsWorldShipExport>
        FALSE_VALUE
      </UpsWorldShipExport>
      <Affiliate id="0"/>
      <CommissionEarned>
        <Amount>
          0.00
        </Amount>
      </CommissionEarned>
      <CouponCode/>
      <GiftCertificateCode/>
      <ShippingInstructions/>
      <HowHeard/>
      <GiftCertificateAmountPaid>
        <Amount>
          0
        </Amount>
      </GiftCertificateAmountPaid>
      <ShippingTaxable>
        FALSE_VALUE
      </ShippingTaxable>
      <OrderDate>
        <UnixTimeStamp>
          1274862893
        </UnixTimeStamp>
        <Day>
          26
        </Day>
        <Month>
          05
        </Month>
        <Year>
          2010
        </Year>
        <Hour>
          03
        </Hour>
        <Minute>
          34
        </Minute>
        <Second>
          53
        </Second>
      </OrderDate>
      <VatInclusive>
        FALSE_VALUE
      </VatInclusive>
      <RemoteHost>
        S0106001d725790c4.vc.shawcable.net
      </RemoteHost>
      <Donation>
        <Amount>
          0.00
        </Amount>
      </Donation>
      <GoogleFulfillmentStatus/>
      <GoogleFinancialStatus/>
      <ChargeAmount>
        <Amount>
          0.00
        </Amount>
      </ChargeAmount>
      <RefundAmount>
        <Amount>
          0.00
        </Amount>
      </RefundAmount>
      <RewardPointsEarned>
        0
      </RewardPointsEarned>
      <HasBeenReturned>
        FALSE_VALUE
      </HasBeenReturned>
      <AdminFile/>
      <CreditAmount>
        <Amount>
          0.00
        </Amount>
      </CreditAmount>
      <RechargeRate>
        0
      </RechargeRate>
      <RechargeType/>
      <Parent/>
      <PaymentDate>
        <UnixTimeStamp/>
        <Day>
          31
        </Day>
        <Month>
          12
        </Month>
        <Year>
          1969
        </Year>
        <Hour>
          18
        </Hour>
        <Minute>
          00
        </Minute>
        <Second>
          00
        </Second>
      </PaymentDate>
      <PaymentText/>
      <StoneEdgeExported>
        FALSE_VALUE
      </StoneEdgeExported>
      <PaymentGatewayStatus/>
      <PaymentGatewayType>
        PAYMENT_GATEWAY_TYPE_MONERIS_ESELECT
      </PaymentGatewayType>
      <PointOfSale>
        FALSE_VALUE
      </PointOfSale>
      <Tendered>
        <Amount>
          36.95
        </Amount>
      </Tendered>
      <Change>
        <Amount>
          0.00
        </Amount>
      </Change>
      <PointOfSaleExported>
        FALSE_VALUE
      </PointOfSaleExported>
      <OrderCost>
        <Amount>
          0.00
        </Amount>
      </OrderCost>
      <Captured>
        <Amount>
          0.00
        </Amount>
      </Captured>
      <GatewayTransactionId2/>
      <Reauthorized>
        FALSE_VALUE
      </Reauthorized>
      <FreightShipping>
        <Amount>
          0.00
        </Amount>
      </FreightShipping>
      <TaxCalculatedAfterDiscounts>
        FALSE_VALUE
      </TaxCalculatedAfterDiscounts>
      <QuickbooksId/>
      <QuickbooksSessionId/>
      <AdminAttention>
        FALSE_VALUE
      </AdminAttention>
      <PricingGroupLocation id="">
        <Name/>
      </PricingGroupLocation>
      <PricingGroupShipDate>
        <UnixTimeStamp/>
        <Day>
          31
        </Day>
        <Month>
          12
        </Month>
        <Year>
          1969
        </Year>
        <Hour>
          18
        </Hour>
        <Minute>
          00
        </Minute>
        <Second>
          00
        </Second>
      </PricingGroupShipDate>
    </Order>
  </List>
</Response>
*/
