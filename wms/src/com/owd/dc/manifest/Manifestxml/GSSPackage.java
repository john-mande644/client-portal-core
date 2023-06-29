package com.owd.dc.manifest.Manifestxml;

import com.thoughtworks.xstream.XStream;
import org.apache.axis.message.MessageElement;

import javax.xml.namespace.QName;
import java.math.BigDecimal;
import java.security.PrivateKey;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Apr 22, 2010
 * Time: 11:50:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class GSSPackage {
    private String xmlns="mailerdataformatf07.xsd";
       private String PackageID2;
        private String OrderFkey = "";
        private String OrderID = "" ; 
    private String ItemValueCurrencyType = "" ; 
    private String SenderName = "" ; 
    private String SenderFirstName = "" ;
    private String SenderLastName = "";
    private String SenderBusinessName = "";
    private String SenderAddress_Line_1 = "" ; 
    private String SenderAddress_Line_2 = "" ; 
    private String SenderAddress_Line_3 = "" ; 
    private String SenderCity = "" ; 
    private String SenderProvince = "" ; 
    private String SenderPostal_Code = "" ; 
    private String SenderCountry_Code = "" ; 
    private String SenderPhone = "" ; 
    private String SenderEmail = "" ; 
    private String RecipientName = "" ;
    private String RecipientFirstName = "";
    private String RecipientLastName = "";
    private String RecipientBusinessName = "";
    private String RecipientAddress_Line_1 = "" ; 
    private String RecipientAddress_Line_2 = "" ; 
    private String RecipientAddress_Line_3 = "" ; 
    private String RecipientCity = "" ; 
    private String RecipientProvince = "" ; 
    private String RecipientPostal_Code = "" ; 
    private String RecipientCountry_Code = "" ; 
    private String RecipientPhone = "" ; 
    private String RecipientEmail = "" ; 
    private String PackageType = "" ; 
    private String TaxpayerID = "" ; 
    private String ShippingandHandling = "" ; 
    private String ShippingCurrencyType = "" ; 
    private String PackageID = "" ; 
    private String PackageWeight = "" ; 
    private String WeightUnit = "" ; 
    private String PackageLength = "" ; 
    private String PackageWidth = "" ; 
    private String PackageHeight = "" ; 
    private String UnitOfMeasurement = "" ; 
    private String ServiceType = "" ; 
    private String RateType = "" ; 
    private String PackagePhysicalCount = "" ; 
    private String PostagePaid = "" ; 
    private String PostagePaidCurrencyType = "" ; 
    private String MailingAgentID = "" ; 
    private String ReturnAgentID = "" ; 
    private String ValueLoaded = "" ; 
    private String LicenseNumber = "" ; 
    private String CertificateNumber = "" ; 
    private String InvoiceNumber = "" ; 
    private String PFCorEEL = "" ; 
    private String ReturnServiceRequested = "" ; 
    private String Location;
    private String boxTypeFkey;
    private double ShipPriceToRecord;
    private BigDecimal ShipDiscountShare;
    private BigDecimal USPSAddToPercent;
    private String OWDInsuranceCost;
    private String InsuredAmount;

    public String getInsuredAmount() {
        return InsuredAmount;
    }

    public void setInsuredAmount(String insuredAmount) {
        InsuredAmount = insuredAmount;
    }

    public String getOWDInsuranceCost() {
        return OWDInsuranceCost;
    }

    public void setOWDInsuranceCost(String OWDInsuranceCost) {
        this.OWDInsuranceCost = OWDInsuranceCost;
    }

    public BigDecimal getUSPSAddToPercent() {
        return USPSAddToPercent;
    }

    public void setUSPSAddToPercent(BigDecimal USPSAddToPercent) {
        if(null==USPSAddToPercent){
            this.USPSAddToPercent = BigDecimal.ZERO;
        }else {
            this.USPSAddToPercent = USPSAddToPercent;
        }
    }

    public BigDecimal getShipDiscountShare() {
        return ShipDiscountShare;
    }

    public void setShipDiscountShare(BigDecimal shipDiscountShare) {
        if(null == shipDiscountShare){
            ShipDiscountShare = BigDecimal.ZERO;
        }else {
            ShipDiscountShare = shipDiscountShare;
        }
    }

    public double getShipPriceToRecord() {
        return ShipPriceToRecord;
    }

    public void setShipPriceToRecord(double shipPriceToRecord) {
        ShipPriceToRecord = shipPriceToRecord;
    }

    public String getBoxTypeFkey() {
        return boxTypeFkey;
    }

    public void setBoxTypeFkey(String boxTypeFkey) {
        this.boxTypeFkey = boxTypeFkey;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    private List<Item> Items;

    private String resultTrackingNum;
    private String resultPostagePaid;
    private int resultIndex;

    public int getResultIndex() {
        return resultIndex;
    }

    public void setResultIndex(int resultIndex) {
        this.resultIndex = resultIndex;
    }

    public String getResultTrackingNum() {
        return resultTrackingNum;
    }

    public void setResultTrackingNum(String resultTrackingNum) {
        this.resultTrackingNum = resultTrackingNum;
    }

    public String getResultPostagePaid() {
        return resultPostagePaid;
    }

    public void setResultPostagePaid(String resultPostagePaid) {
        this.resultPostagePaid = resultPostagePaid;
    }

    public String getOrderFkey() {
        return OrderFkey;
    }

    public void setOrderFkey(String orderFkey) {
        OrderFkey = orderFkey;
    }

    public MessageElement getXml() throws Exception{
         System.out.println("Inside of the package xml");
         System.out.println(SenderAddress_Line_3);
         MessageElement root = new MessageElement(new QName("Package"));
          root.addAttribute("","PackageID",PackageID);
           root.addAttribute("","xmlns","mailerdataformatf07.xsd");
     if(OrderID.length()>0){
        root.addChildElement("OrderID").addTextNode(OrderID);

     }


     if(ItemValueCurrencyType.length()>0){
     root.addChildElement("ItemValueCurrencyType").addTextNode(ItemValueCurrencyType);
     }

     if (SenderName.length()>0){
         root.addChildElement("SenderName").addTextNode(SenderName);
     }
        if(SenderFirstName.length()>0){
            root.addChildElement("SenderFirstName").addTextNode(SenderFirstName);
        }
        if(SenderLastName.length()>0){
            root.addChildElement("SenderLastName").addTextNode(SenderLastName);
        }
        if(SenderBusinessName.length()>0){
            root.addChildElement("SenderBusinessName").addTextNode(SenderBusinessName);
        }
    if (SenderAddress_Line_1.length()>0){
          MessageElement e = new MessageElement(new QName("SenderAddress_Line_1"));
        e.addTextNode(SenderAddress_Line_1);
        root.addChild(e);
        //root.addChildElement("SenderAddress_Line_1").addTextNode(SenderAddress_Line_1);
    }
    if (SenderAddress_Line_2.length()>0){
          MessageElement e = new MessageElement(new QName("SenderAddress_Line_2"));
        e.addTextNode(SenderAddress_Line_2);
        root.addChild(e);
        //root.addChildElement("SenderAddress_Line_2").addTextNode(SenderAddress_Line_2);
    }

     if (null!= SenderAddress_Line_3 && SenderAddress_Line_3.length()>0){
         MessageElement e = new MessageElement(new QName("SenderAddress_Line_3"));
         e.addTextNode(SenderAddress_Line_3);
         root.addChild(e);
        // root.addChildElement("SenderAddress_Line_3").addTextNode(SenderAddress_Line_3);
     }
     if (SenderCity.length()>0){

         root.addChildElement("SenderCity").addTextNode(SenderCity);
     }
     if (SenderProvince.length()>0){

         root.addChildElement("SenderProvince").addTextNode(SenderProvince);
     }
     if (SenderPostal_Code.length()>0){
         root.addChildElement("SenderPostal_Code").addTextNode(SenderPostal_Code);
     }
     if (SenderCountry_Code.length()>0){

         root.addChildElement("SenderCountry_Code").addTextNode(SenderCountry_Code);
     }
     if (SenderPhone.length()>0){

         root.addChildElement("SenderPhone").addTextNode(SenderPhone);
     }
     if (SenderEmail.length()>0){

         root.addChildElement("SenderEmail").addTextNode(SenderEmail);
     }
     if (RecipientName.length()>0){

         root.addChildElement("RecipientName").addTextNode(RecipientName);
     }
        if(RecipientFirstName.length()>0){
                   root.addChildElement("RecipientFirstName").addTextNode(RecipientFirstName);
               }
               if(RecipientLastName.length()>0){
                   root.addChildElement("RecipientLastName").addTextNode(RecipientLastName);
               }
               if(RecipientBusinessName.length()>0){
                   root.addChildElement("RecipientBusinessName").addTextNode(RecipientBusinessName);
               }
     if (RecipientAddress_Line_1.length()>0){
            MessageElement e = new MessageElement(new QName("RecipientAddress_Line_1"));
         e.addTextNode(RecipientAddress_Line_1);
         root.addChild(e);
         //root.addChildElement("RecipientAddress_Line_1").addTextNode(RecipientAddress_Line_1);
     }
     if (RecipientAddress_Line_2.length()>0){
             MessageElement e = new MessageElement(new QName("RecipientAddress_Line_2"));
         e.addTextNode(RecipientAddress_Line_2);
         root.addChild(e);
         //root.addChildElement("RecipientAddress_Line_2").addTextNode(RecipientAddress_Line_2);
     }
     if (null!= RecipientAddress_Line_3&& RecipientAddress_Line_3.length()>0){
            MessageElement e = new MessageElement(new QName("RecipientAddress_Line_3"));
         e.addTextNode(RecipientAddress_Line_3);
         root.addChild(e);
        // root.addChildElement("RecipientAddress_Line_3").addTextNode(RecipientAddress_Line_3);
     }
     if (RecipientCity.length()>0){

         root.addChildElement("RecipientCity").addTextNode(RecipientCity);
     }
     if (RecipientProvince.length()>0){

         root.addChildElement("RecipientProvince").addTextNode(RecipientProvince);
     }
     if (RecipientPostal_Code.length()>0){

         root.addChildElement("RecipientPostal_Code").addTextNode(RecipientPostal_Code);
     }  else{
          root.addChildElement("RecipientPostal_Code").addTextNode(" ");
     }


     if (RecipientCountry_Code.length()>0){

         root.addChildElement("RecipientCountry_Code").addTextNode(RecipientCountry_Code);
     }
     if (RecipientPhone.length()>0){

         root.addChildElement("RecipientPhone").addTextNode(RecipientPhone);
     }
     if (RecipientEmail.length()>0){

         root.addChildElement("RecipientEmail").addTextNode(RecipientEmail);
     }
     if (PackageType.length()>0){

         root.addChildElement("PackageType").addTextNode(PackageType);
     }
     if (TaxpayerID.length()>0){

         root.addChildElement("TaxpayerID").addTextNode(TaxpayerID);
     }
     if (ShippingandHandling.length()>0){

         root.addChildElement("ShippingandHandling").addTextNode(ShippingandHandling);
     }
     if (ShippingCurrencyType.length()>0){

         root.addChildElement("ShippingCurrencyType").addTextNode(ShippingCurrencyType);
     }
     if (PackageID.length()>0){

         root.addChildElement("PackageID").addTextNode(PackageID);
     }
     if (PackageWeight.length()>0){

         root.addChildElement("PackageWeight").addTextNode(PackageWeight);
     }
     if (WeightUnit.length()>0){

         root.addChildElement("WeightUnit").addTextNode(WeightUnit);
     }
     if (PackageLength.length()>0){

         root.addChildElement("PackageLength").addTextNode(PackageLength);
     }
     if (PackageWidth.length()>0){

         root.addChildElement("PackageWidth").addTextNode(PackageWidth);
     }
     if (PackageHeight.length()>0){

         root.addChildElement("PackageHeight").addTextNode(PackageHeight);
     }
     if (UnitOfMeasurement.length()>0){

         root.addChildElement("UnitOfMeasurement").addTextNode(UnitOfMeasurement);
     }
     if (ServiceType.length()>0){

         root.addChildElement("ServiceType").addTextNode(ServiceType);
     }
     if (RateType.length()>0){

         root.addChildElement("RateType").addTextNode(RateType);
     }
     if (PackagePhysicalCount.length()>0){

         root.addChildElement("PackagePhysicalCount").addTextNode(PackagePhysicalCount);
     }
     if (PostagePaid.length()>0){

         root.addChildElement("PostagePaid").addTextNode(PostagePaid);
     }
     if (PostagePaidCurrencyType.length()>0){

         root.addChildElement("PostagePaidCurrencyType").addTextNode(PostagePaidCurrencyType);
     }
     if (MailingAgentID.length()>0){

         root.addChildElement("MailingAgentID").addTextNode(MailingAgentID);
     }
     if (ReturnAgentID.length()>0){

         root.addChildElement("ReturnAgentID").addTextNode(ReturnAgentID);
     }
     if (ValueLoaded.length()>0){

         root.addChildElement("ValueLoaded").addTextNode(ValueLoaded);
     }
     if (LicenseNumber.length()>0){

         root.addChildElement("LicenseNumber").addTextNode(LicenseNumber);
     }
     if (CertificateNumber.length()>0){

         root.addChildElement("CertificateNumber").addTextNode(CertificateNumber);
     }
     if (InvoiceNumber.length()>0){

         root.addChildElement("InvoiceNumber").addTextNode(InvoiceNumber);
     }
     if (PFCorEEL.length()>0){

         root.addChildElement("PFCorEEL").addTextNode(PFCorEEL);
     }
     if (ReturnServiceRequested.length()>0){

         root.addChildElement("ReturnServiceRequested").addTextNode(ReturnServiceRequested);
     }
         System.out.println("Going to do items");
         for(Item i:Items){
             System.out.println("looking xml items");
             root.addChild(i.getXml());
         }
             return root;
     }
    /* public static XStream getManifestXstream(){
       XStream x = new XStream();
         x.alias("Item",Item.class);
        x.alias("Manifest",Manifest.class);
         x.useAttributeFor(Manifest.class,"xmlns");
        x.useAttributeFor(DispatchXml.class,"xmlns");
         x.useAttributeFor(Item.class,"xmlns");

        x.aliasField("PackageID",GSSPackage.class,"PackageID2");
        x.useAttributeFor(GSSPackage.class,"PackageID2");
         x.useAttributeFor(GSSPackage.class,"xmlns");
        x.addImplicitCollection(GSSPackage.class,"Items");
       
        return x;

    }*/

    





    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getItemValueCurrencyType() {
        return ItemValueCurrencyType;
    }

    public void setItemValueCurrencyType(String itemValueCurrencyType) {
        ItemValueCurrencyType = itemValueCurrencyType;
    }

    public String getSenderName() {
        return SenderName;
    }

    public void setSenderName(String senderName) {
        SenderName = senderName;
    }

    public String getSenderAddress_Line_1() {
        return SenderAddress_Line_1;
    }

    public void setSenderAddress_Line_1(String senderAddress_Line_1) {
        SenderAddress_Line_1 = senderAddress_Line_1;
    }

    public String getSenderAddress_Line_2() {
        return SenderAddress_Line_2;
    }

    public void setSenderAddress_Line_2(String senderAddress_Line_2) {
        SenderAddress_Line_2 = senderAddress_Line_2;
    }

    public String getSenderAddress_Line_3() {
        return SenderAddress_Line_3;
    }

    public void setSenderAddress_Line_3(String senderAddress_Line_3) {
        SenderAddress_Line_3 = senderAddress_Line_3;
    }

    public String getSenderCity() {
        return SenderCity;
    }

    public void setSenderCity(String senderCity) {
        SenderCity = senderCity;
    }

    public String getSenderProvince() {
        return SenderProvince;
    }

    public void setSenderProvince(String senderProvince) {
        SenderProvince = senderProvince;
    }

    public String getSenderPostal_Code() {
        return SenderPostal_Code;
    }

    public void setSenderPostal_Code(String senderPostal_Code) {
        SenderPostal_Code = senderPostal_Code;
    }

    public String getSenderCountry_Code() {
        return SenderCountry_Code;
    }

    public void setSenderCountry_Code(String senderCountry_Code) {
        SenderCountry_Code = senderCountry_Code;
    }

    public String getSenderPhone() {
        return SenderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        SenderPhone = senderPhone;
    }

    public String getSenderEmail() {
        return SenderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        SenderEmail = senderEmail;
    }

    public String getRecipientName() {
        return RecipientName;
    }

    public void setRecipientName(String recipientName) {
        if (recipientName.length()>35){
         RecipientName = recipientName.substring(0,35);
        } else{
        RecipientName = recipientName;
        }
    }

    public String getRecipientAddress_Line_1() {
        return RecipientAddress_Line_1;
    }

    public void setRecipientAddress_Line_1(String recipientAddress_Line_1) {
        if (recipientAddress_Line_1.length()>35){
           RecipientAddress_Line_1 = recipientAddress_Line_1.substring(0,35);
           RecipientAddress_Line_2 = recipientAddress_Line_1.substring(35,recipientAddress_Line_1.length());
        }  else{
        RecipientAddress_Line_1 = recipientAddress_Line_1;
        }
    }

    public String getRecipientAddress_Line_2() {
        return RecipientAddress_Line_2;
    }

    public void setRecipientAddress_Line_2(String recipientAddress_Line_2) {
        if (RecipientAddress_Line_2.length()>0){
         RecipientAddress_Line_2 = RecipientAddress_Line_2 + recipientAddress_Line_2;
        }else{
        RecipientAddress_Line_2 = recipientAddress_Line_2;
        }
        if (RecipientAddress_Line_2.length()>35){
            RecipientAddress_Line_3 = RecipientAddress_Line_2.substring(35,RecipientAddress_Line_2.length());
            RecipientAddress_Line_2 = RecipientAddress_Line_2.substring(0,35);
        }
    }

    public String getRecipientAddress_Line_3() {
        return RecipientAddress_Line_3;
    }

    public void setRecipientAddress_Line_3(String recipientAddress_Line_3) {
       if (null == recipientAddress_Line_3){
        RecipientAddress_Line_3 = "";
       }else{
        System.out.println("111");
        if (RecipientAddress_Line_3.length()>0){
                RecipientAddress_Line_3 = RecipientAddress_Line_3 + recipientAddress_Line_3;

        }  else{
        RecipientAddress_Line_3 = recipientAddress_Line_3;
        }
        System.out.println("222");
         if (RecipientAddress_Line_3.length()>35){
                RecipientAddress_Line_3 = RecipientAddress_Line_3.substring(0,35);
            }
        System.out.println("333");
       }
    }

    public String getRecipientCity() {
        return RecipientCity;
    }

    public void setRecipientCity(String recipientCity) {
        if (recipientCity.length()>35){
         RecipientCity = recipientCity.substring(0,35);   
        } else{
        RecipientCity = recipientCity;
        }
    }

    public String getRecipientProvince() {
        return RecipientProvince;
    }

    public void setRecipientProvince(String recipientProvince) {
        RecipientProvince = recipientProvince;
    }

    public String getRecipientPostal_Code() {
        return RecipientPostal_Code;
    }

    public void setRecipientPostal_Code(String recipientPostal_Code) {
        if(recipientPostal_Code.length()>11){
           RecipientPostal_Code = recipientPostal_Code.substring(0,11);
        } else{
            RecipientPostal_Code = recipientPostal_Code;
        }

    }

    public String getRecipientCountry_Code() {
        return RecipientCountry_Code;
    }

    public void setRecipientCountry_Code(String recipientCountry_Code) {
        RecipientCountry_Code = recipientCountry_Code;
    }

    public String getRecipientPhone() {
        return RecipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        if (recipientPhone.length()>25){
         RecipientPhone = recipientPhone.substring(0,25);
        } else{
        RecipientPhone = recipientPhone;
        }
    }

    public String getRecipientEmail() {
        return RecipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        RecipientEmail = recipientEmail;
    }

    public String getPackageType() {
        return PackageType;
    }

    public void setPackageType(String packageType) {
        PackageType = packageType;
    }

    public String getTaxpayerID() {
        return TaxpayerID;
    }

    public void setTaxpayerID(String taxpayerID) {
        TaxpayerID = taxpayerID;
    }

    public String getShippingandHandling() {
        return ShippingandHandling;
    }

    public void setShippingandHandling(String shippingandHandling) {
        ShippingandHandling = shippingandHandling;
    }

    public String getShippingCurrencyType() {
        return ShippingCurrencyType;
    }

    public void setShippingCurrencyType(String shippingCurrencyType) {
        ShippingCurrencyType = shippingCurrencyType;
    }

    public String getPackageID() {
        return PackageID;
    }

    public void setPackageID(String packageID) {
        PackageID2 = packageID;

        PackageID = packageID;
    }

    public String getPackageWeight() {
        return PackageWeight;
    }

    public void setPackageWeight(String packageWeight) {
        PackageWeight = packageWeight;
    }

    public String getWeightUnit() {
        return WeightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        WeightUnit = weightUnit;
    }

    public String getPackageLength() {
        return PackageLength;
    }

    public void setPackageLength(String packageLength) {
        PackageLength = packageLength;
    }

    public String getPackageWidth() {
        return PackageWidth;
    }

    public void setPackageWidth(String packageWidth) {
        PackageWidth = packageWidth;
    }

    public String getPackageHeight() {
        return PackageHeight;
    }

    public void setPackageHeight(String packageHeight) {
        PackageHeight = packageHeight;
    }

    public String getUnitOfMeasurement() {
        return UnitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        UnitOfMeasurement = unitOfMeasurement;
    }

    public String getServiceType() {
        return ServiceType;
    }

    public void setServiceType(String serviceType) {
        ServiceType = serviceType;
    }

    public String getRateType() {
        return RateType;
    }

    public void setRateType(String rateType) {
        RateType = rateType;
    }

    public String getPackagePhysicalCount() {
        return PackagePhysicalCount;
    }

    public void setPackagePhysicalCount(String packagePhysicalCount) {
        PackagePhysicalCount = packagePhysicalCount;
    }

    public String getPostagePaid() {
        return PostagePaid;
    }

    public void setPostagePaid(String postagePaid) {
        PostagePaid = postagePaid;
    }

    public String getPostagePaidCurrencyType() {
        return PostagePaidCurrencyType;
    }

    public void setPostagePaidCurrencyType(String postagePaidCurrencyType) {
        PostagePaidCurrencyType = postagePaidCurrencyType;
    }

    public String getMailingAgentID() {
        return MailingAgentID;
    }

    public void setMailingAgentID(String mailingAgentID) {
        MailingAgentID = mailingAgentID;
    }

    public String getReturnAgentID() {
        return ReturnAgentID;
    }

    public void setReturnAgentID(String returnAgentID) {
        ReturnAgentID = returnAgentID;
    }

    public String getValueLoaded() {
        return ValueLoaded;
    }

    public void setValueLoaded(String valueLoaded) {
        ValueLoaded = valueLoaded;
    }

    public String getLicenseNumber() {
        return LicenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        LicenseNumber = licenseNumber;
    }

    public String getCertificateNumber() {
        return CertificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        CertificateNumber = certificateNumber;
    }

    public String getInvoiceNumber() {
        return InvoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        InvoiceNumber = invoiceNumber;
    }

    public String getPFCorEEL() {
        return PFCorEEL;
    }

    public void setPFCorEEL(String PFCorEEL) {
        this.PFCorEEL = PFCorEEL;
    }

    public String getReturnServiceRequested() {
        return ReturnServiceRequested;
    }

    public void setReturnServiceRequested(String returnServiceRequested) {
        ReturnServiceRequested = returnServiceRequested;
    }

    public List<Item> getItems() {
        return Items;
    }

    public void setItems(List<Item> items) {
        Items = items;
    }

    public String getSenderFirstName() {
        return SenderFirstName;
    }

    public void setSenderFirstName(String senderFirstName) {
        SenderFirstName = senderFirstName;
    }

    public String getSenderLastName() {
        return SenderLastName;
    }

    public void setSenderLastName(String senderLastName) {
        SenderLastName = senderLastName;
    }

    public String getSenderBusinessName() {
        return SenderBusinessName;
    }

    public void setSenderBusinessName(String senderBusinessName) {
        if(senderBusinessName.length()>35){
         SenderBusinessName = senderBusinessName.substring(0,35);
        } else{
        SenderBusinessName = senderBusinessName;
        }
    }

    public String getRecipientFirstName() {
        return RecipientFirstName;
    }

    public void setRecipientFirstName(String recipientFirstName) {
        recipientFirstName = recipientFirstName.replace("?","");
        if(recipientFirstName.length()>14){
           RecipientFirstName = recipientFirstName.substring(0,14);
        }   else{
        RecipientFirstName = recipientFirstName;
        }
    }

    public String getRecipientLastName() {
        return RecipientLastName;
    }

    public void setRecipientLastName(String recipientLastName) {
        if(recipientLastName.length()>20){
            RecipientLastName = recipientLastName.substring(0,20);
        }  else{
        RecipientLastName = recipientLastName;
        }
    }

    public String getRecipientBusinessName() {
        return RecipientBusinessName;
    }

    public void setRecipientBusinessName(String recipientBusinessName) {
        recipientBusinessName = recipientBusinessName.replace("-","");
        if (recipientBusinessName.length()>35){
            RecipientBusinessName = recipientBusinessName.substring(0,35);
        }   else{
        RecipientBusinessName = recipientBusinessName;
        }
    }
}
