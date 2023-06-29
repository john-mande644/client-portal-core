package com.owd.dc.packing;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: May 10, 2007
 * Time: 10:11:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class packageBean {
    SimpleDateFormat formater = new SimpleDateFormat("MM/dd/yyy hh:mm:ss a");
        SimpleDateFormat formater2 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss ");
    private String orderFkey;
    private String packer;
    private String start;
    private String stop;
    private String boxFkey;
    private String boxCost;
    private String depth;
    private String width;
    private String height;
    private String boxNumber;
    private String boxsTotal;
    private String scannedBarcode;
    private List<packItemBean> packedLines;
    private String weight;
    private String facility;
    private String packType;
    private String dunnageFactor;

    public String getDunnageFactor() {
        return dunnageFactor;
    }

    public void setDunnageFactor(String dunnageFactor) {
        this.dunnageFactor = dunnageFactor;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public List getPackedLines() {
        if(null == packedLines) packedLines = new ArrayList();
        return packedLines;
    }

    public void setPackedLines(List packedLines) {
        this.packedLines = packedLines;
    }

    public String getScannedBarcode() {
        return scannedBarcode;
    }

    public void setScannedBarcode(String scannedBarcode) {
        this.scannedBarcode = scannedBarcode;
    }

    public String getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(String boxNumber) {
        this.boxNumber = boxNumber;
    }

    public String getBoxsTotal() {
        return boxsTotal;
    }

    public void setBoxsTotal(String boxsTotal) {
        this.boxsTotal = boxsTotal;
    }

    public int getOrderFkey() {
        return Integer.parseInt(orderFkey);
    }

    public void setOrderFkey(String orderFkey) {
        this.orderFkey = orderFkey;
    }

    public String getPacker() {
        return packer;
    }

    public void setPacker(String packer) {
        this.packer = packer;
    }

    public String getStart() {
return returnDate(start);
    }

    public void setStart(String start) {
        this.start = start;
    }
    public String GetStop(){
        return stop;
    }
    public String GetStart(){
        return start;
    }
    public String getStop() {

        return returnDate(stop);
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public int getBoxFkey() {
        return Integer.parseInt(boxFkey);
    }

    public void setBoxFkey(String boxFkey) {
        this.boxFkey = boxFkey;
    }

    public float getBoxCost() {
        return Float.parseFloat(boxCost);
    }

    public void setBoxCost(String boxCost) {
        this.boxCost = boxCost;
    }

    public float getDepth() {
        return Float.parseFloat(depth);
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public float getWidth() {
        return Float.parseFloat(width);
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public float getHeight() {
        return Float.parseFloat(height);
    }

    public void setHeight(String height) {
        this.height = height;
    }
    private String returnDate(String s) {
        try{
        java.util.Date parsedDate = formater.parse(s);
            return (formater2.format(parsedDate));
        }catch (Exception e){
            return null;
        }
    }

    public String getPackType() {
        return packType;
    }

    public void setPackType(String packType) {
        this.packType = packType;
    }

    public String getXmlWithBoxNumber(){
        StringBuffer sb = new StringBuffer();
        sb.append("<OWDPack>");
        sb.append("<order>");
        sb.append("<orderfkey>");
        sb.append(orderFkey);
        sb.append("</orderfkey><packer>");
        sb.append(packer);
        sb.append("</packer><start>");
        sb.append(start);
        sb.append("</start><stop>");
        sb.append(stop);
        sb.append("</stop><barcode>");
        sb.append(scannedBarcode);
        sb.append("</barcode><facility>");
        sb.append(facility);
        sb.append("</facility><box><fkey>");
        sb.append(boxFkey);
        sb.append("</fkey><cost>");
        sb.append(boxCost);
        sb.append("</cost><depth>");
        sb.append(depth);
        sb.append("</depth><width>");
        sb.append(width);
        sb.append("</width><height>");
        sb.append(height);
        sb.append("</height>");
        sb.append("<boxNumber>");
        sb.append(boxNumber);
        sb.append("</boxNumber>");
        sb.append("<weight>");
        sb.append(weight);
        sb.append("</weight>");

        sb.append("</box><items>");
        for(packItemBean pib :  packedLines){
            sb.append("<line><fkey>");
            sb.append(pib.getLineKey());
            sb.append("</fkey><qty>");
            sb.append(pib.getQty());
            sb.append("</qty></line>");

        }
        sb.append("</items></order><AdditionalInfo></AdditionalInfo><packType>");
        sb.append(packType);
        sb.append("</packType></OWDPack>");



        return sb.toString();
    }

    public String getXml(){
        StringBuffer sb = new StringBuffer();
        sb.append("<OWDPack>");
        sb.append("<order>");
        sb.append("<orderfkey>");
        sb.append(orderFkey);
        sb.append("</orderfkey><packer>");
        sb.append(packer);
        sb.append("</packer><start>");
        sb.append(start);
        sb.append("</start><stop>");
        sb.append(stop);
        sb.append("</stop><barcode>");
        sb.append(scannedBarcode);
        sb.append("</barcode><facility>");
        sb.append(facility);
        sb.append("</facility><box><fkey>");
        sb.append(boxFkey);
        sb.append("</fkey><cost>");
        sb.append(boxCost);
        sb.append("</cost><depth>");
        sb.append(depth);
        sb.append("</depth><width>");
        sb.append(width);
        sb.append("</width><height>");
        sb.append(height);
        sb.append("</height><weight>");
        sb.append(weight);
        sb.append("</weight></box><items>");
        for(packItemBean pib :  packedLines){
            sb.append("<line><fkey>");
            sb.append(pib.getLineKey());
            sb.append("</fkey><qty>");
            sb.append(pib.getQty());
            sb.append("</qty></line>");

        }
        sb.append("</items></order><AdditionalInfo></AdditionalInfo><packType>");
        sb.append(packType);
        sb.append("</packType></OWDPack>");



        return sb.toString();
    }


    public static void main(String[] args){
        packageBean pb = new packageBean();
        pb.setStop("8/24/2007 10:45:50 AM");
        System.out.println(pb.getStop());
   SimpleDateFormat formater = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
       SimpleDateFormat formater2 = new SimpleDateFormat("dd/MM/yyy HH:mm:ss ");

        try{
         System.out.println(formater2.format(formater.parse("8/24/2007 10:45:50 AM")));
        }  catch (Exception e){

        }




    }
}
