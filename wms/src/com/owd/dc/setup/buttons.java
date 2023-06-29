package com.owd.dc.setup;

import com.owd.dc.HandheldUtilities;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Nov 2, 2005
 * Time: 4:06:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class buttons {
     private String t5;
    private String t6;
    private String t7;
    private String t8;
    private String red;
    private String green;
    private String printer;
    private String smallPrinter;
    private String palletTag;
    private String facility;

    public String getPalletTag(String tcid) {
         String s = "";
              try{
                  s = HandheldUtilities.getPalletTagPrinterForUser(tcid);
              } catch(Exception e){

              }
        return s;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public void setPalletTag(String palletTag) {
        this.palletTag = palletTag;
    }
    public String getStoredPallet(){
        return palletTag;
    }
    public String getStoredPrinter(){
         return printer;
     }
    public String getStoredSmallPrinter(){
        return smallPrinter;
        
    }
    public String getSmallPrinter(String tcid) {
        String s = "";
        try{
                 s = HandheldUtilities.getSmallPrinterForUser(tcid);
        } catch(Exception e){
            e.printStackTrace();

        }

        return    s ;
    }

    public void setSmallPrinter(String smallPrinter) {
        this.smallPrinter = smallPrinter;
    }

    public String getPrinter(String tcid) {
        String s ="";
        try{
            s = HandheldUtilities.getPrinterForUser(tcid);
        } catch(Exception e){
            e.printStackTrace();

        }

        return s;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public String getT5() {
        return t5;
    }

    public void setT5(String t5) {
        this.t5 = t5;
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
}
