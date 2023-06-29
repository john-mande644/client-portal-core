package com.owd.dc.manifest.api.internal;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 7, 2008
 * Time: 12:14:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class CarrierInfo {

          public String serviceName;
          public String serviceCode;
          public String carrierName;
          public String carrierCode;
          public String labelDocName;
          public String labelDocCode;
          public String IntlDoc1Name;
          public String IntlDoc1Code;
          public String IntlDoc2Name;
          public String IntlDoc2Code;
          public String IntlDoc3Name;
          public String IntlDoc3Code;
          public int labelCount;
          public int IntlDoc1Count;
          public int IntlDoc2Count;
          public int IntlDoc3Count;
          public int isInternational;
          public int requestTracking;
          public int rateOnly;
          public String oldCarrierCode;
          public int trackingType;


          private CarrierInfo()
          {
          }

          public static CarrierInfo createCarrier(String   aserviceName,
                                String aserviceCode,
                                String acarrierName,
                                String acarrierCode,
                                String alabelDocName,
                                String alabelDocCode,
                                String aIntlDoc1Name,
                                String aIntlDoc1Code,
                                String aIntlDoc2Name,
                                String aIntlDoc2Code,
                                String aIntlDoc3Name,
                                String aIntlDoc3Code,
                                String alabelCount,
                                String aIntlDoc1Count,
                                String aIntlDoc2Count,
                                String aIntlDoc3Count,
                                String aisInternational,
                                String arequestTracking,
                                String arateOnly,
                                String aoldCarrierCode,
                                String atrackingType)
         {
                                 CarrierInfo carrier = new CarrierInfo();

                                carrier.serviceName = aserviceName;
                                carrier.serviceCode = aserviceCode;
                                carrier.carrierName = acarrierName;
                                carrier.carrierCode = acarrierCode;
                                carrier.labelDocName = alabelDocName;
                                carrier.labelDocCode = alabelDocCode;
                                carrier.IntlDoc1Name = aIntlDoc1Name;
                                carrier.IntlDoc1Code = aIntlDoc1Code;
                                carrier.IntlDoc2Name = aIntlDoc2Name;
                                carrier.IntlDoc2Code = aIntlDoc2Code;
                                carrier.IntlDoc3Name = aIntlDoc3Name;
                                carrier.IntlDoc3Code = aIntlDoc3Code;
                                carrier.labelCount = new Integer(alabelCount).intValue();
                                carrier.IntlDoc1Count = new Integer(aIntlDoc1Count).intValue();
                ////System.out.println(carrier.serviceName+":"+carrier.IntlDoc1Count);

                                carrier.IntlDoc2Count = new Integer(aIntlDoc2Count).intValue();
                                carrier.IntlDoc3Count = new Integer(aIntlDoc3Count).intValue();
                                carrier.isInternational = new Integer(aisInternational).intValue();
                                carrier.requestTracking = new Integer(arequestTracking).intValue();
                                carrier.rateOnly = new Integer(arateOnly).intValue();
                                carrier.oldCarrierCode = aoldCarrierCode;
                                carrier.trackingType = new Integer(atrackingType).intValue();

                                return carrier;


         }



}
