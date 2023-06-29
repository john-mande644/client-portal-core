package com.owd.alittlePlaying.multivaluedMapStuff;

import com.owd.dc.packing.AutoBatchPrinting.PackingABUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ABtesting {


    static  String s = "";
    static String s2 = "";
    static String s3 = "";
    static String s4 = "";
    static String s5 = "";
    public static void main(String[] args) {
        System.setProperty("com.owd.environment", "test");


        Thread t = new Thread() {
            public void run() {
                s = PackingABUtils.getXmlAbBatchForFingerprintFacility("LYFT150:1 | FedEx SmartPost Parcel Select", "DC6", "2019-04-09 19:00:00", "Lyft, Inc.");
                s2 = PackingABUtils.getXmlAbBatchForFingerprintFacility("LYFT150:1 | FedEx SmartPost Parcel Select", "DC6", "2019-04-09 19:00:00", "Lyft, Inc.");
                try {
                    Files.write(Paths.get("s.xml"), s.getBytes(), StandardOpenOption.CREATE);
                    Files.write(Paths.get("s2.xml"), s2.getBytes(), StandardOpenOption.CREATE);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        };

        Thread t2 = new Thread() {
            public void run() {
                s3 = PackingABUtils.getXmlAbBatchForFingerprintFacility("LYFT150:1 | FedEx SmartPost Parcel Select", "DC6", "2019-04-09 19:00:00", "Lyft, Inc.");
                try {
                    Files.write(Paths.get("s3.xml"), s3.getBytes(), StandardOpenOption.CREATE);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }


        };
        Thread t3 = new Thread() {
            public void run() {
                s4 = PackingABUtils.getXmlAbBatchForFingerprintFacility("LYFT150:1 | FedEx SmartPost Parcel Select", "DC6", "2019-04-09 19:00:00", "Lyft, Inc.");
                try {
                    Files.write(Paths.get("s4.xml"), s4.getBytes(), StandardOpenOption.CREATE);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }


        };
        Thread t4 = new Thread() {
            public void run() {
                s5 = PackingABUtils.getXmlAbBatchForFingerprintFacility("LYFT150:1 | FedEx SmartPost Parcel Select", "DC6", "2019-04-09 19:00:00", "Lyft, Inc.");
                try {
                    Files.write(Paths.get("s5.xml"), s5.getBytes(), StandardOpenOption.CREATE);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }


        };
        t.start();
        t2.start();
        t3.start();
        t4.start();


    }
}
