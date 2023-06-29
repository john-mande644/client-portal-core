package com.owd.dc.inventory.labels;

import com.owd.dc.beans.jsonResultBean;
import com.owd.dc.warehouse.labelMaker.labelMakerUtilities;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import org.hibernate.Session;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: 10/19/11
 * Time: 11:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class inventoryLabelUtilities {
    public static void main(String[] args) {
        try {
            //  jsonResultBean rb = printLargeInventoryLabelById(36351, "zebra1.dc1.owd.com", "2",HibernateSession.currentSession());
            //  jsonResultBean rb = printSmallInventoryLabelById(21589, "zebra1.dc1.owd.com", "1", HibernateSession.currentSession());
            // printBreakLabelSmall("zebra12.dc1.owd.com");
            //  printSkuArrowSmall("zebra12.dc1.owd.com","The Test");
            Map<Integer, String> items = new HashMap<Integer, String>();
            items.put(159952, "5");
            items.put(157937, "6");
            //    printSmallLabelMapById(items,"192.168.10.2");
            printSmallInventoryLabelById(157937, "192.168.10.2", "1", HibernateSession.currentSession());
            //upcA
            // jsonResultBean rb = printSmallInventoryLabelById(36351, "zebra1.dc1.owd.com", "1",HibernateSession.currentSession());
            //upcE

            //     jsonResultBean rb = printSmallInventoryLabelById(103628, "zebra1.dc1.owd.com", "1",HibernateSession.currentSession());
            //jsonResultBean rb = printSmallInventoryLabelById(130104, "zebra1.dc1.owd.com", "1",HibernateSession.currentSession());
            //   jsonResultBean rb = printSmallInventoryLabelById(277, "zebra1.dc1.owd.com", "1",HibernateSession.currentSession());
            //   System.out.println(getSizeFromPrinter("zebra1.dc1.owd.com",HibernateSession.currentSession()));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static boolean printSmallLabelMapById(Map<Integer, String> items, String printer) throws Exception {
        boolean success = false;
        for (Integer id : items.keySet()) {
            OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, id);
            printSkuArrowSmall(printer, inv.getInventoryNum());

            printSmallInventoryLabelById(id, printer, items.get(id), HibernateSession.currentSession());
            printBreakLabelSmall(printer);
        }


        return success;

    }

    public static jsonResultBean printInventory(Integer id, String qty, String printer, Session sess) {
        jsonResultBean b = new jsonResultBean();
        try {

            String labelSize = labelMakerUtilities.getSizeFromPrinter(printer, sess);

            if (labelSize.equals("large")) {
                b = printLargeInventoryLabelById(id, printer, qty, sess);
            }

            if (labelSize.endsWith("small")) {
                b = printSmallInventoryLabelById(id, printer, qty, sess);
            }


        } catch (Exception e) {
            b.setError("Problem printing" + e.getMessage());
        }


        return b;

    }

    private static jsonResultBean printSmallInventoryLabelById(Integer id, String printer, String qty, Session sess) throws Exception {
        jsonResultBean b = new jsonResultBean();
        StringBuffer sb = new StringBuffer();
        OwdInventory inv = (OwdInventory) sess.load(OwdInventory.class, id);
        if (inv.getUpcCode().length() > 0) {
            //Check for upca or upcE
            if (inv.getUpcCode().length() == 12) {
                sb.append("^XA^PR5^PQ");
                sb.append(qty);
                sb.append("^FO60,40^BY3^BUN,75,Y,N,Y^FD");
                sb.append(inv.getUpcCode().substring(0, 11));
                sb.append("^FS");
                sb.append("^XZ");
            } else if (inv.getUpcCode().length() == 13) {
                System.out.println("Upc E 13 digit");
                sb.append("^XA^PR5^PQ");
                sb.append(qty);
                sb.append("^FO60,40^BY3^BEN,75,Y,N,^FD");
                sb.append(inv.getUpcCode().substring(0, 12));
                sb.append("^FS");
                sb.append("^XZ");


            } else if (inv.getUpcCode().length() == 14) {
                sb.append("^XA^PR5^PQ");
                sb.append(qty);
                sb.append("^FO15,40^BY2^BcN,75,Y,N,N^FD");
                sb.append(inv.getUpcCode());
                sb.append("^FS");
                sb.append("^XZ");

            } else {
                throw new Exception("Bad UPC data. Please contact IT");
            }

        } else if (inv.getIsbnCode().length() > 0) {

            System.out.println("Upc E 13 digit");
            sb.append("^XA^PR5^PQ");
            sb.append(qty);
            sb.append("^FO60,40^^BY3^BEN,75,Y,N,^FD");
            sb.append(inv.getIsbnCode().substring(0, 12));
            sb.append("^FS");
            sb.append("^XZ");


        } else {
            System.out.println("Doing a normal small label");

            sb.append("^XA^PR5^PQ");
            sb.append(qty);
            sb.append("^FO75,10^FB480,2,0,C,0^BY3^BCN,40,Y,N,N^FD");
            sb.append(inv.getInventoryId());
            sb.append("^FS");

            sb.append("^FO0,88^FB420,2,5,C,0^ABn,14,14^FD");
            sb.append(inv.getInventoryNum());
            sb.append("^FS");

            sb.append("^FO5,135^FB8420,2,5,L,0^A0n,14,14^FD");
            sb.append(inv.getDescription());
            sb.append("^FS");
            sb.append("^XZ");


        }
        try {
            sentToPrinter(sb.toString(), printer);
            b.setMessage("Printed!!");
        } catch (Exception e) {
            e.printStackTrace();
            b.setError(e.getMessage());
        }


        return b;
    }

    private static void printBreakLabelSmall(String printer) throws Exception {

        sentToPrinter("^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR6,6~SD15^JUS^LRN^CI0^XZ\n" +
                "^XA\n" +
                "^MMT\n" +
                "^PW406\n" +
                "^LL0203\n" +
                "^LS0\n" +
                "^FT264,170^A0N,45,45^FH\\^FDBreak^FS\n" +
                "^FT121,112^A0N,45,45^FH\\^FDBreak^FS\n" +
                "^FT7,50^A0N,45,45^FH\\^FDBreak^FS\n" +
                "^FO25,88^GE77,77,8^FS\n" +
                "^FO284,21^GE97,86,8^FS\n" +
                "^PQ1,0,1,Y^XZ", printer);
    }

    private static void printSkuArrowSmall(String printer, String sku) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR6,6~SD15^JUS^LRN^CI0^XZ\n" +
                "~DG000.GRF,02560,020,\n" +
                ",::::::::Y0780,Y0FC0,X01FF0,X07FF0,X0IFC,:W03FHFE,W07FIF,W0HF7FF80,V01FE3FFC0,V03FC1FFE0,V07F80FHF8,V0HFH07FF8,U01FE003FFC,U03FC001FFE,U07F80H0IF,U0FE0I07FF80,T01FE0I03FFC0,T03FC0I01FFE0,T07F80J0IF0,T0HFL07FF8,S01FC0K03FFC,S03F80K01FFE,S07F0M0IF,S0FE0M07FF80,R01FC0M03FFC0,R03F80M01FFE0,R07F0O0IF0,R0FE0O07FF8,Q01FC0O03FFC,Q07F80O01FFE,Q0HFR0IF,Q0FE0Q07FF80,P03FC0Q03FFC0,P07F80Q01FFE0,P0HFT0IF0,O01FE0S07FF8,O03FC0S03FFC,O07F80S01FFE,O0HFV0IF,N01FE0U07FF80,N03FC0U07FFC0,N07F80U01FFE0,N0HFX0IF0,M01FE0W07FF8,M03FC0W03FFC,M07F80W01FFE,M0HFY01FHF,L01FC0Y07FF80,L03FC0Y03FFC0,L07F0g01FFE0,L0FE0K07E0M0FC0H01FHF0,K01FE0I03FHFM01FHFI07FF8,K03F8003FJFM07FJF03FFC,K07F01FKFE0L0PFE,K0FEFMFE0L0QF,J01FOFE0L0QF80,J07FOFE0L0QFC0,J07FLF01FE0L0HF8FMFC0,J0LF8003FE0L0HF803FKFC0,I01FIFC0I03FE0L07FC0H0JFE,I01FFE0K03FE0L07FC0I01FFE,J060M03FC0L07FC0K038,S03FC0L07FC,:S07FC0L07FC,:S07FC0L03FC,S07FC0L03FE,S07F80L03FE,::S0HF80L03FE,S0HF80L03FF,:S0HFN01FF,::::R01FF0M01FF80,:R01FE0N0HF80,::::R03FE0N0HF80,R03FE0N07FC0,:R03FC0N07FC0,::R07FC0N07FE0,:R07FC0N03FE0,:R07F80N03FE0,::R0HF80N03FE0,R0HF80N03FF0,R0HFP03FF0,R0HFP01FF0,:::Q01FF0O01FF0,Q01FF0P0HF8,Q01FTF8,::Q01FTF0,Q01FSF80,:,:::~DG001.GRF,03200,020,\n" +
                ",:::::::::::::g0F0,Y01F8,Y07FC,Y0HFE,X01FHF,X03FHF80,X07FHFC0,X0JFE0,W01FDFHF0,W03F8FHF8,W07F07FFC,V01FE01FFE,V03FC01FHF,V03F8007FF80,V0HFI07FFC0,U01FE0H03FFE0,U03FC0I0IF0,U07F80I0IF8,U0HFK07FFC,T01FE0J03FFE,T03FC0J01FHF,T07F80K0IF80,T0HFM03FFC0,S01FE0L03FFE0,S03FC0L01FHF0,S07F80M07FF8,S0HFO03FFC,R01FE0N03FFE,R03FC0N01FHF,R07F80O0IF80,R0HFQ07FFC0,Q01FC0P03FFE0,Q03F80P01FHF0,Q07F0R0IF8,Q0FE0R07FFC,P01FC0R01FFE,P07F80R01FHF,P07F0T0IF80,P0FE0T07FFC0,O03FC0T03FFE0,O07F80T01FHF0,O0HFW0IF8,N01FE0V07FFC,N03FC0V03FFE,N07F80V01FHF,N0HFY0IF80,M01FE0X07FFC0,M03FC0X03FFE0,M07F80Y0IF0,M0HFgG0IF8,L01FE0g07FFC,L03FC0J01F80L03F80H03FFE,L07F80I0IFC0L03FFE001FHF,L0FE0H0KFC0L0KFE0FHF80,K01FE07FKFC0K01FOFC0,K03FBFMFC0K03FOFE0,K07FOFC0K03FPF0,J01FPFC0K01FPF8,J01FLFC07F80K01FF1FMF8,J03FJFE0H07F80K01FF007FKF8,J07FIFK07F80K01FF0H01FIF80,J07FF80K0HF80K01FF0J03FF80,J0180M0HF80K01FF80K07,T0HF80K01FF80,T0HFN0HF80,::::S01FF0M07FC0,:S01FE0M07FC0,::S03FE0M07FC0,S03FE0M07FE0,:S03FE0M03FE0,S03FC0M03FE0,:::S07FC0M03FF0,:S07F80M01FF0,::::S0HF80M01FF0,S0HF80M01FF8,S0HFP0HF8,::R01FF0O0HF8,R01FF0O0HFC,:R01FF0O07FC,:R01FE0O07FC,::R03FE0O07FC,R03FE0O07FE,R03FE0O03FE,:R03FC0O03FE,::R07FSFE,R07FTF,R07FSFE,R07FSFC,R07FSF0,R03FRFE0,,::::::::::::::::::::::::::::::^XA\n" +
                "^MMT\n" +
                "^PW406\n" +
                "^LL0203\n" +
                "^LS0\n" +
                "^FT256,128^XG000.GRF,1,1^FS\n" +
                "^FT0,160^XG001.GRF,1,1^FS\n" +
                "^FT19,158^A0N,25,26^FH\\^FD");
        sb.append(sku);
        sb.append("^FS\n" +
                "^PQ1,0,1,Y^XZ\n" +
                "^XA^ID000.GRF^FS^XZ\n" +
                "^XA^ID001.GRF^FS^XZ");
        sentToPrinter(sb.toString(), printer);


    }

    private static jsonResultBean printLargeInventoryLabelById(Integer id, String printer, String qty, Session sess) throws Exception {

        OwdInventory inv = (OwdInventory) sess.load(OwdInventory.class, id);
        System.out.println("Doing " + id);
        jsonResultBean b = new jsonResultBean();
        StringBuffer sb = new StringBuffer();
        sb.append("^XA^PR5^PW406^PQ");
        sb.append(qty);

        //  sb.append("^FO260,325^B3R,N,150,Y,N^FD");
        sb.append("^FO260,325^BY3^BCR,130,Y,N,N^FD");
        sb.append(",10^Adr,36,20^FD");
        sb.append(inv.getInventoryId());
        sb.append("^FS");

        sb.append("^FO105,10^FB845,2,5,C,0^ABr,40,24^FD");
        sb.append(inv.getInventoryNum());
        sb.append("^FS");

        sb.append("^FO50,10^FB845,2,5,L,0^A0R,32,25^FD");
        sb.append(inv.getDescription());
        sb.append("^FS");

        sb.append("^XZ");
        try {
            sentToPrinter(sb.toString(), printer);
            b.setMessage("Printed!!");
        } catch (Exception e) {
            e.printStackTrace();
            b.setError(e.getMessage());
        }


        return b;
    }

    private static void sentToPrinter(String label, String printer) throws IOException {
        Socket clientSocket = new Socket();
        try {
            clientSocket = new Socket(printer, 9100);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes(label);
            clientSocket.close();
        } catch (Exception e) {
            try {
                clientSocket.close();
            } catch (Exception ee) {

            }
        }
    }
}
