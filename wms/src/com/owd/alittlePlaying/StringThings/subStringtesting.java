package com.owd.alittlePlaying.StringThings;

/**
 * Created by danny on 5/1/2017.
 */
public class subStringtesting {

    public static void main(String[] args){
        /*    System.out.println(getBeginPO("122412009_3331"));
        System.out.println(getSubPO("122412009_3331"));
            System.out.println(jcpenneyD2Sname("ATTN AMANDA BLAKE             ORDERID 1236 SCAN LABEL"));
        System.out.println(jcpenneyD2SORderId("ATTN AMANDA BLAKE             ORDERID 1236 SCAN LABEL"));*/

     /*   String s = "Title: 12 Inch Inner Tube | Item: 31733362 | SupplierSKU: RP-SA-05 |";

        System.out.println(s.substring(s.indexOf("SupplierSKU:"),s.lastIndexOf("|")).trim().replace("SupplierSKU: ",""));*/

      //  String s = "//tote-123456";
      //  System.out.println(s.substring(s.indexOf("-")+1));
        String s = "170221";
       System.out.println( s.substring(2,4) + "/"+s.substring(4)+"/"+s.substring(0,2));
        String ss = "12345678R1";
        System.out.println(ss.substring(0,ss.indexOf("R")));


    }




    public static String jcpenneyD2Sname(String s){
       return s.substring(0,s.indexOf("       "));



    }
    public static String jcpenneyD2SORderId(String s){
        return s.substring(s.lastIndexOf("             "), s.indexOf("SCAN")).trim();
    }
    public static String getBeginPO(String s){
        return s.substring(0,s.indexOf("_"));
    }
    public static String getSubPO(String s){
        return s.substring(s.indexOf("_")+1);
    }
}
