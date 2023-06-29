package com.owd.dc.manifest.Manifestxml;

import org.apache.axis.message.MessageElement;

import javax.xml.namespace.QName;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Apr 22, 2010
 * Time: 11:50:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class Item {
     private String xmlns ="mailerdataformatf07.xsd";
    
      private String ItemID= "" ;
      private String ItemDescription= "" ;
      private String CustomsDescription= "" ;
      private String UnitValue = "" ;
      private String Quantity = "" ;
      private String CountryOfOrigin ="";
      private String HTSNumber =  "";
      private String MultiSourceFlag = "";
      private String OriginalImportCost = "";
      private String ImportCostCurrencyType = "";

    public MessageElement getXml() throws Exception{
           MessageElement root = new MessageElement(new QName("Item"));

             root.addAttribute("","xmlns","mailerdataformatf07.xsd");

        if (ItemID.length()>0){
        root.addChildElement("ItemID").addTextNode(ItemID);
        }



        if(ItemDescription.length()>0){
        root.addChildElement("ItemDescription").addTextNode(ItemDescription);
        }
        
        if(CustomsDescription.length()>0){
        root.addChildElement("CustomsDescription").addTextNode(CustomsDescription);
        }
        if(UnitValue.length()>0){
           root.addChildElement("UnitValue").addTextNode(UnitValue);

        }

        if(Quantity.length()>0){
         root.addChildElement("Quantity").addTextNode(Quantity);

        }


         root.addChildElement("CountryOfOrigin").addTextNode(CountryOfOrigin);

       




          root.addChildElement("HTSNumber").addTextNode(HTSNumber);




           root.addChildElement("MultiSourceFlag").addTextNode(MultiSourceFlag);




          root.addChildElement("OriginalImportCost").addTextNode(OriginalImportCost);





         root.addChildElement("ImportCostCurrencyType").addTextNode(ImportCostCurrencyType);



       return root;
    }


    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String itemID) {
        if (itemID.length()>19){
             ItemID = itemID.substring(0,19);
        }
        else{
            ItemID = itemID;
        }

    }

    public String getItemDescription() {
        return ItemDescription;
    }

    public void setItemDescription(String itemDescription) {
        if(itemDescription.length()>49){
           ItemDescription = itemDescription.substring(0,49);
        }  else{
           ItemDescription = itemDescription;
        }

    }

    public String getCustomsDescription() {
        return CustomsDescription;
    }

    public void setCustomsDescription(String customsDescription) {
        CustomsDescription = customsDescription;
    }

    public String getUnitValue() {
        return UnitValue;
    }

    public void setUnitValue(String unitValue) {
        UnitValue = unitValue;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getCountryOfOrigin() {
        return CountryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        CountryOfOrigin = countryOfOrigin;
    }

    public String getHTSNumber() {
        return HTSNumber;
    }

    public void setHTSNumber(String HTSNumber) {
        this.HTSNumber = HTSNumber;
    }

    public String getMultiSourceFlag() {
        return MultiSourceFlag;
    }

    public void setMultiSourceFlag(String multiSourceFlag) {
        MultiSourceFlag = multiSourceFlag;
    }

    public String getOriginalImportCost() {
        return OriginalImportCost;
    }

    public void setOriginalImportCost(String originalImportCost) {
        OriginalImportCost = originalImportCost;
    }

    public String getImportCostCurrencyType() {
        return ImportCostCurrencyType;
    }

    public void setImportCostCurrencyType(String importCostCurrencyType) {
        ImportCostCurrencyType = importCostCurrencyType;
    }



}
