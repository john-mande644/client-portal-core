package com.owd.web.api

import com.owd.core.OWDUtilities
import com.owd.core.TagUtilities
import com.owd.core.business.order.Order
import com.owd.core.managers.InventoryManager
import com.owd.hibernate.generated.EdiDocs
import com.owd.hibernate.generated.EdiSpsConfigdata
import com.owd.hibernate.HibernateSession
import org.hibernate.Query

/**
 * Created by stewartbuskirk1 on 6/11/15.
 */
class EDIUtilities {

    public static EdiDocs getEdiDocForReference(String ediReference) throws Exception {
        EdiDocs doc = null;

        if (ediReference == null) {
            throw new Exception("EDI reference available but null value not allowed");
        }

        if (ediReference.startsWith("OWD.EDI.SPS.850.")) {
            String idStr = ediReference.replaceAll("OWD\\.EDI\\.SPS\\.850\\.", "")
            println "Found edi doc ID " + idStr
            int docId = 0
            try {
                docId = Integer.parseInt(idStr)

                doc = HibernateSession.currentSession().load(EdiDocs.class, docId)
                if (doc.getId() < 1) {
                    throw new Exception("Bad EDI doc ID " + docId)
                }

            } catch (Exception ex) {
                throw new Exception("Unable to locate EDI document for reference " + ediReference)
            }
        } else {
            throw new Exception("EDI reference " + ediReference + " uses an invalid format");
        }

        return doc
    }

    public static EdiSpsConfigdata getConfigForEdiDoc(EdiDocs podata) throws Exception {
        EdiSpsConfigdata doc = null;

        if (podata == null) {
            throw new Exception("EDI doc record available but null value not allowed");
        }

        XmlParser parser = new XmlParser()
        parser.setTrimWhitespace(true)
        Node po = parser.parseText(podata.getDocData())


        String partner = po.Header.OrderHeader.TradingPartnerId.text()
        String vendor = po.Header.OrderHeader.Vendor.text()

        println "SPSPartner:" + partner
        println "SPSVendor:" + vendor

        Query q = HibernateSession.currentSession().createQuery("from EdiSpsConfigdata where Spsaccount=? and Vendorid=?")
        q.setParameter(0, partner)
        q.setParameter(1, vendor)
        List<EdiSpsConfigdata> configs = (List<EdiSpsConfigdata>) q.list()
        if (configs.size() != 1) {
            throw new Exception("SPS EDI doc validation error - wrong number of configs found for " + partner + "/" + vendor)
        }

        doc = configs.get(0)

        if (doc.getId() < 1) {
            throw new Exception("SPS EDI doc validation error - unable to load config for " + partner + "/" + vendor)
        }


        return doc
    }

    public static void applyEdiShippingMethod(Order order) throws Exception {
        String vendor = order.getTagMap().get(TagUtilities.kVendorComplianceReference)
        String ediDocRaw = order.getTagValue(TagUtilities.kEDIPurchaseOrder)

        XmlParser parser = new XmlParser()
        parser.setTrimWhitespace(true)
        Node po = parser.parseText(ediDocRaw)

        switch(order.clientID) {
            case "489": //Symphony

                switch (vendor) {
                    case "Wayfair":

                        //fake item for billing purposes to collect the pick fees
                        InventoryManager.postInventoryAdjustmentAsAdjustmentToCurrentValue(324007, 489, 2,
                                OWDUtilities.getSQLDateNoTimeForToday(),
                                "WAYFAIR-" + OWDUtilities.getSQLDateNoTimeForToday() + "-" + order.getClientOrderReference(),
                                order.facilityCode, HibernateSession.currentSession());
                        order.template = "wayfair"

                        order.addInsertItemIfAvailable("Wayfair", 2);
                        String shipCode = po.Header.CarrierInformation.CarrierAlphaCode.text()
                        if ("FDEG".equals(shipCode)) {
                            order.getShippingInfo().setShipOptions("FedEx Ground", "Third Party Billing", "346593300");

                            order.shippercontact = "Wayfair DC";
                            order.shippercompany = "Wayfair";
                            order.shipperaddress_one = "4 Copley Place";
                            order.shipperaddress_two = "Floor 7";
                            order.shippercity = "Boston";
                            order.shipperstate = "MA";
                            order.shipperzip = "02116";
                            order.shipperphone = "7042356831";  //Chilitech number
                        } else {
                            throw new Exception("Unrecognized ship code " + shipCode + " for Wayfair!")
                        }


                        break;
                    case "Brookstone":
                        order.getShippingInfo().setShipOptions("UPS Ground", "Third Party Billing", "A6012A");

                        order.shippercontact = "Brookstone DC";
                        order.shippercompany = "Brookstone DC";
                        order.shipperaddress_one = "1611 Bassford Drive";
                        order.shipperaddress_two = "";
                        order.shippercity = "Mexico";
                        order.shipperstate = "MO";
                        order.shipperzip = "65265";
                        order.shipperphone = "7042356831";  //Chilitech number

                        order.template = "brookstone"
                        break;

                    case "Amazon":
                        order.getShippingInfo().setShipOptions("FedEx Ground", "Prepaid", null);

                        break;

                    case "ToysRUs":
                        order.getShippingInfo().setShipOptions("FedEx Ground", "Third Party Billing", "622483483");
                        order.template = "toysrus"
                        break;


                    default:
                        throw new IllegalArgumentException("Unrecognized EDI partner: " + vendor);
                }
                break;
            default:
                throw new IllegalArgumentException("Unrecognized EDI Client ID: " + order.clientID);
        }
    }

}
