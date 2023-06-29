package com.owd.dc.setup;

import org.apache.struts.action.ActionForm;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Nov 2, 2005
 * Time: 2:51:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class buttonForm extends ActionForm {
    private String t5;
    private String t6;
    private String t7;
    private String t8;
    private String red;
    private String green;
    private String printer;
    private String[] list = {"find","assign","remove","upc","doinventory","menu","loc-menu","inv-menu","pickstart","checkupcstart","printskulabel"};
    private String[] visibleList = {"Find","Assign","Remove","Assign UPC","Inventory","Main Menu","Location Menu","Inventory Menu","Pick","Check UPC","Print Sku"};
   private String smallPrinter;
   private List lists;
    private List printerlist;
    private List smallprinterlist;
    private List palletTags;
    private List facilities;
    private String facility;
   private boolean teleport;

    public boolean isTeleport() {
        return teleport;
    }

    public void setTeleport(boolean teleport) {
        this.teleport = teleport;
    }

    public List getFacilities() {
        return facilities;
    }

    public void setFacilities(List facilities) {
        this.facilities = facilities;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    private String palletTag;

    public List getPalletTags() {
        return palletTags;
    }

    public void setPalletTags(List palletTags) {
        this.palletTags = palletTags;
    }

    public String getPalletTag() {
        return palletTag;
    }

    public void setPalletTag(String palletTag) {
        this.palletTag = palletTag;
    }

    public List getLists() {
        return lists;
    }

    public void setLists(List lists) {
        this.lists = lists;
    }

    public List getPrinterlist() {
        return printerlist;
    }

    public void setPrinterlist(List printerlist) {
        this.printerlist = printerlist;
    }

    public List getSmallprinterlist() {
        return smallprinterlist;
    }

    public void setSmallprinterlist(List smallprinterlist) {
        this.smallprinterlist = smallprinterlist;
    }

    public String getSmallPrinter() {
        return smallPrinter;
    }

    public void setSmallPrinter(String smallPrinter) {
        this.smallPrinter = smallPrinter;
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }


   public String getT6() {
        return t6;
    }

    public void setT6(String t6) {
        this.t6 = t6;
    }

    public String getT7() {
        return t7;
    }

    public void setT7(String t7) {
        this.t7 = t7;
    }

    public String getT8() {
        return t8;
    }

    public void setT8(String t8) {
        this.t8 = t8;
    }

    public String getRed() {
        return red;
    }

    public void setRed(String red) {
        this.red = red;
    }

    public String getGreen() {
        return green;
    }

    public void setGreen(String green) {
        this.green = green;
    }

    public String getT5() {
        return t5;
    }

    public void setT5(String t5) {
        this.t5 = t5;
    }

    public String[] getList() {
        return list;
    }

    public String[] getVisibleList() {
        return visibleList;
    }






}
