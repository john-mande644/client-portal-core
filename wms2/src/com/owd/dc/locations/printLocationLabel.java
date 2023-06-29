package com.owd.dc.locations;


import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;
import org.hibernate.Session;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Map;


public class printLocationLabel {

    public static void main(String args[]) {
       /* List l = new ArrayList();
        l.add("Zone: a1");
        l.add("Aisle: 4");
        l.add("Rack: 6");
        l.add("Shelf: 4");
        l.add("Section: 3");

        String id = "123456";
        String printer = "zebra1.dc1.owd.com";
        System.out.println(printLocation(l,id,printer));
      */
      /*  try{
        System.out.println(printLocationById("37130","zebra1.dc1.owd.com",HibernateSession.currentSession()));
        }catch  (Exception e){
            e.printStackTrace();
        }*/
        //uploadTest("zebra1.dc1.owd.com");
        //  printHHHelpers("61001","Pair","zebra1.dc1.owd.com");
        //   printHHHelpers("Scanner Connected","Scan To test Scanner","zebra1.dc1.owd.com");
        //  printHHHelpers("Scanner Connected","Scan To test Scanner","zebra1.dc1.owd.com");
        //   printHHHelpers("Scanner Connected","Scan To test Scanner","zebra1.dc1.owd.com");
        //   printHHHelpers("Scanner Connected","Scan To test Scanner","zebra1.dc1.owd.com");
        //  System.out.println(getPrinterSize("zebra2.dc1.owd.com"));
       /* try{
        printLocationById("100587","172.16.10.2",HibernateSession.currentSession());
            }catch(Exception e){
            e.printStackTrace();
        }*/
        //  printMobile("Bin: 1234567","1234567","172.16.10.2");

        try {
            // loadAndPrintMoblie("37065", "172.16.10.2", HibernateSession.currentSession());
            List<String> newLocations = addNewLocation.addNewMobileLocations(HibernateSession.currentSession(), "8", "10", "danny", "172.16.10.2", 50);
            HibUtils.commit(HibernateSession.currentSession());
            for (String loc : newLocations) {
                printLocationLabel.printLocationById(loc, "172.16.10.2", HibernateSession.currentSession());


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.println(printMobile("Pallet: 12345","1236","zebra1.dc1.owd.com"));
        /*System.out.println(printMobile("Pallet: 12346","12356","zebra1.dc1.owd.com"));
        System.out.println(printMobile("Pallet: 12347","456","zebra1.dc1.owd.com"));
        System.out.println(printMobile("Pallet: 12348","4123456","zebra1.dc1.owd.com"));
        System.out.println(printMobile("Pallet: 12349","15523456","zebra1.dc1.owd.com"));
        System.out.println(printMobile("Pallet: 123410","1223456","zebra1.dc1.owd.com"));
        System.out.println(printMobile("Pallet: 123411","1236456","zebra1.dc1.owd.com"));
        System.out.println(printMobile("Pallet: 12341","12345996","zebra1.dc1.owd.com"));

        try{
        Thread.sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(printMobile("Bin: 41","1996","zebra1.dc1.owd.com"));*/

      /*  try{

            Session sess = HibernateSession.currentSession();

          resultBean b = printLocationTree("8","zebra1.dc1.owd.com",sess);
            System.out.println(b.getErrors());
            System.out.println(b.getMessages());

       // System.out.println(printLocationById("36433","zebra1.dc1.owd.com", sess));
     /*   System.out.println(printLocationById("36454","zebra1.dc1.owd.com", sess));
            System.out.println(printLocationById("36455","zebra1.dc1.owd.com", sess));
            System.out.println(printLocationById("36457","zebra1.dc1.owd.com", sess));*/
        /*

        }catch (Exception e){
            e.printStackTrace();
        } */
    }

    public static resultBean printTreeForLocationInMap(Map<String, String> locations, String printer, Session sess) {
        resultBean b = new resultBean();
        try {

            for (String s : locations.keySet()) {
                resultBean be = printLocationTree(s, printer, sess);
                if (be.getErrors().length() > 0) b.addError(be.getErrors());
            }
            b.addMessage("Printed Tree good");
            b.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            b.addError(e.getMessage());
        }
        return b;
    }

    public static resultBean printLocationTree(String id, String printer, Session sess) {
        resultBean b = new resultBean();
        try {

            List<String> locs = com.owd.dc.locations.locationUtilities.getChildTreeList(sess, id);

            for (String s : locs) {
                if (!locationUtilities.isMobile(s, sess)) {
                    resultBean be = printLocationById(s, printer, sess);
                    if (be.getErrors().length() > 0) b.addError(be.getErrors());
                }
            }

            b.addMessage("Printed Location Tree");
            b.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            b.addError(e.getMessage());
        }

        return b;
    }

    public static resultBean printDirectChildrenNoMobile(int id, String printer, Session sess) {
        return printDirectChildrenNoMobile(id + "", printer, sess);
    }

    public static resultBean printDirectChildrenNoMobile(String id, String printer, Session sess) {
        resultBean b = new resultBean();
        try {
            b = loadAndPrintDirectChildrenNoMobile(id, printer, sess);
        } catch (Exception e) {
            e.printStackTrace();
            b.addError(e.getMessage());
        }

        return b;
    }

    public static resultBean printLocationsInMap(Map<String, String> locations, String printer, Session sess) {
        resultBean b = new resultBean();
        try {

            for (String s : locations.keySet()) {
                resultBean be = printLocationById(s, printer, sess);
                if (be.getErrors().length() > 0) b.addError(be.getErrors());
            }
            b.addMessage("Printed all good");
            b.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            b.addError(e.getMessage());
        }
        return b;
    }

    private static resultBean loadAndPrintDirectChildrenNoMobile(String id, String printer, Session sess) {
        resultBean b = new resultBean();

        try {
            Map<String, String> children = com.owd.dc.locations.locationUtilities.getDirectChildMapForIdNoMobile(sess, id);

            for (String s : children.keySet()) {
                resultBean be = printLocationById(s, printer, sess);
                if (be.getErrors().length() > 0) b.addError(be.getErrors());

            }

            b.setSuccess(true);
            b.addMessage("Printed Children Locations for: " + id);
        } catch (Exception e) {
            e.printStackTrace();
            b.addError(e.getMessage());
        }
        return b;
    }

    public static resultBean printLocationById(String id, String printer, Session sess) {
        resultBean b = new resultBean();
        try {
            if (com.owd.dc.locations.locationUtilities.isMobile(id, sess)) {
                b = loadAndPrintMoblie(id, printer, sess);
            } else {
                System.out.println("Doing static locaiton label");
                b = loadAndPrintLocationById(id, printer, sess);
            }


        } catch (Exception e) {
            e.printStackTrace();
            b.addError(e.getMessage());
        }
        return b;

    }

    private static resultBean loadAndPrintMoblie(String id, String printer, Session sess) {
        resultBean b = new resultBean();
        try {
            b = printMobile(com.owd.dc.locations.locationUtilities.getLocNameForMobile(id, sess), id, printer);
        } catch (Exception e) {
            System.out.println("fail");
            b.addError(e.getMessage());
        }

        return b;
    }

    private static resultBean loadAndPrintLocationById(String id, String printer, Session sess) {
        resultBean b = new resultBean();
        try {
            List<String> locList = com.owd.dc.locations.locationUtilities.getLocationTreeForIdFilterByType(sess, id, 5);

            b = printLocation(locList, id, printer);


        } catch (Exception e) {
            e.printStackTrace();
            b.addError(e.getMessage());
        }

        return b;
    }

    private static void uploadTest(String printer) {
        try {

            String data = "~DGE:RARROW.GRF,4800,24,oU0ChM06hM038hL03ChL01FhL01F8hK01FChL0HFhL0HF8hK07FEgX03L07HFgX0EL03HF8gV01CL03HFEgV07CL01IFgU01F8L01IFCgT03F8M0IFEgT0HF8M0JFgS01HFN07IFCgR07HFN07IFEgR0HFEN07JF8gP03HFEN03JFCgP07HFCN03JFEgO01IFCN01KF8gN07IF8N01KFCgN0JF8O0LFgM03JFP0LF8gL07JFP07EJFCgK01JFEP07LFgK03JFEP03LF8gJ0KFEP03LFEgI01KFCP01MFgI07KFCP01HFEJF8gG01LF8P01MFEgG03LF8Q0NFgG0MFR0NFCY01MFR07MFEY07LFER07IFEJFY0MFER03FBLFCW03MFCR03NFEW07MFCR01OF8U01NF8R01OFCU07MFECS0OFEU0OF8S0KFEJF8S03OFT07HFDLFCS07NF7T07PFR01OFET07PF8Q03NFBET03FENFCQ0NFBFET03LFEJFP01MFEHFCT01QF8O07PFCT01QFEN01QF8U0JFDMFN03NF7HF8U0RF8M0RFV07BPFEL01RFV07KFBFDJFL07NFBHFEV03RFCK0NFEIFEV03RFEJ03MFEJFCV01OFBIFJ07MFBJFCV01HFEPFCH01SF8V01SFEH07SF8W0TFH0NFDKF8W0TFC3TFX07IFEHFBLFEUFX07KFEJFBQFEKFEX03gHFBKFEX03FBYFBLFCX01NFBQFEMFCX01gNF8Y0IFBgJF8Y0RF7MF7MFg07gMFg07OF7VFEg07JFDSF7LFHEg03WFBMFBEg03SFBEF7MFEFCg01LFEYFCg01FDgJF8gG0IFDJFDIFDSF8gG0PFEF7PFEHF8gG07gKFgH07gHF7HFgH03KF7UF7HFEgH03gFBIFEgH01YFEJFCgH01YFBJFCgH01HF7FBHFBVF8gI0IFEKFBJFDEHFBKF8gI0WFBLFgJ07gIFgJ07gHFEgJ03RFDOFEgJ03PFBF7OFEgJ01KFEVFCgJ01gHFCgK0FBKFETF8gK0gHF8gK07NFDRFgL07HF7XFgL07JF7OFBEJFEgL03gFEgL03WFEFBCgL01PFEHFDLFCgL01LFBSFCgM0RFEMF8gM0gF8gM07MF7QFgN07HFBKFEPFgN03F7HFBSFEgN03VF7FEgN03UFDHFCgN01WFECgO0QF7LFCgO0XF8gO0LFBMFDIF8gO07WFgP07MF7F7HFBJFgP03HFEFBIFDMFEgP03HFBSFEgP01VFCgP01FDTFCgQ0QFDJF8gQ0VF8gQ0VF8gQ07TFBgR03SFEFgR03KFDF7KFBEgR03RFEFEgR01JFEOFCgR01OFDJFCgS0JF7LFEHFCgS0QFBHF8gS07SF8gS07HF7PFgT03FBQFgT03MFEJFEgT03RFEgT01MF7JFCgU0RFCgU0RF8gU0LFBHFEHF8gU07QFgV07QFgV03JFBKFEgV03IFELFEgV01IFBLFEgV01HFEMFCgW0HF7MFCgW0PF8gW0NFDF8gW07MF7FgX03OFgX03NFEgX03LFBFEgX01NFEgX01NFCgY0NFCgY0NF8gY07JFDHF8gY07MFh03F7KFh03LFEh03IFBHFEh01LFEhG0LFChG0LF8hG0LF8hG07KF8hG07KFhH03FEHFBhH03JFEhH01JFEhH01JFEhI0JFChI0IFD8hI0JF8hI07IF8hI03IFhJ03FDFhJ03HFEhJ01HFEhJ01HFChK0HFChK0HFChK07F8hK07FhL03FhL03FhL03EhL01EhM0ChM0ChM0CsH0";
            sentToPrinter(data, printer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static resultBean printHHHelpers(String text, String visable, String printer) {
        resultBean b = new resultBean();
        StringBuffer sb = new StringBuffer();
        sb.append("^XA" +
                "^PR5" +


                "^FO300 ,0" +
                "^FB600,2,5,R," +
                "^ABr,40,24^FD");
        sb.append(visable);
        sb.append("^FS" +

                "^FO35,20" +

                "^BCR,75,N,N^FD");
        sb.append(text);
        sb.append("^FS" +


                "^FO200,0^GBO,900,4^FS" +
                "^XZ");

        try {
            sentToPrinter(sb.toString(), printer);
            b.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            b.addError(e.getMessage());
        }


        return b;
    }

    private static resultBean printMobile(String text, String id, String printer) {
        String size = "large";
        try {
            size = getPrinterSize(printer);
            if (size == "none") {
                size = "large";
            }
        } catch (Exception e) {
            size = "large";
        }

        if (size.equals("small")) {
            return printMobileSmall(text, id, printer);

        }
        return printMobileLarge(text, id, printer);


    }

    private static resultBean printMobileSmall(String text, String id, String printer) {
        resultBean b = new resultBean();
        StringBuffer sb = new StringBuffer();
        sb.append("" +
                "^XA" +
                "^MMT" +
                "^PW406" +
                "^LL0203" +
                "^LS0" +
                "^BY2,3,69^FT75,81^BCN,,N,N" +
                "^FD");
        sb.append("//" + id);
        sb.append("^FS");
        sb.append("^FT44,169^A0N,42,43^FB170,1,0,R^FH^FD");
        sb.append(text.substring(0, text.length() - 3));
        sb.append("^FS");
        sb.append("^FT223,174^A0N,82,79^FH^FD");

        sb.append(text.substring(text.length() - 3));
        sb.append("^FS");
        sb.append("^XZ");
        try {
            sentToPrinter(sb.toString(), printer);
            b.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            b.addError(e.getMessage());
        }
        return b;

    }

    private static resultBean printMobileLarge(String text, String id, String printer) {
        resultBean b = new resultBean();
        StringBuffer sb = new StringBuffer();
        sb.append("^XA" +
                "^PR5^PW406" +

                "^FO290,600" +
                "^FB200,2,5,L," +
                "^ABr,60,48^FD");
        sb.append(text.substring(text.length() - 3));
        sb.append("^FS");

        sb.append("^FO300 ,0" +
                "^FB600,2,5,R," +
                "^ABr,40,24^FD");
        sb.append(text.substring(0, text.length() - 3));
        sb.append("^FS" +

                "^FO35,250" +

                "^B3R,N,100,N,N^FD");
        sb.append("//" + id);
        sb.append("^FS" +


                "^FO200,0^GBO,900,4^FS" +
                "^XZ");

        try {
            sentToPrinter(sb.toString(), printer);
            b.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            b.addError(e.getMessage());
        }


        return b;
    }

    private static String getPrinterSize(String printer) {
        String size = "none";
        try {
            String sql = "select display from app_data where value = :printer";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("printer", printer);
            String value = q.list().get(0).toString();
            if (value.contains("-Large")) {
                size = "large";
            }
            if (value.contains("-Small")) {
                size = "small";
            }
            if (value.contains("-XXL")) {
                size = "xxl";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    private static resultBean printLocation(List<String> locationTree, String id, String printer) {
        System.out.println("Doing " + id);
        resultBean b = new resultBean();
        StringBuffer sb = new StringBuffer();
        String size = "large";
        try {
            size = getPrinterSize(printer);
            if (size == "none") {
                size = "large";
            }
        } catch (Exception e) {
            size = "large";
        }
        if (size == "xxl") {
            sb.append("" +
                    "^XA\n" +
                    "^MMT\n" +
                    "^PW812\n" +
                    "^LL1218\n" +
                    "^LS0\n" +
                    "^FT385,814^A0R,407,597^FH\\^FD");
            if (locationTree.size() == 5) {
                if (locationTree.get(4).contains("Pallet")) {
                    sb.append(locationTree.get(4).replace("Pallet ", ""));
                } else {
                    sb.append(locationTree.get(4).replace("Section ", ""));
                }
            }
            sb.append("^FS\n");

            sb.append("^FT631,29^A0R,104,98^FH\\^FD");
            sb.append(locationTree.get(0));
            sb.append("^FS\n");
            if (locationTree.size() >= 2) {
                sb.append("^FT491,29^A0R,104,98^FH\\^FD");
                sb.append(locationTree.get(1));
                sb.append("^FS\n");
            }
            if (locationTree.size() >= 3) {
                sb.append("^FT352,29^A0R,104,98^FH\\^FD");
                sb.append(locationTree.get(2));
                sb.append("^FS\n");
            }

            if (locationTree.size() >= 4) {
                sb.append("^FT213,29^A0R,104,98^FH\\^FD");
                sb.append(locationTree.get(3));
                sb.append("^FS\n");
            }
            if (locationTree.size() >= 5) {
                sb.append("\"^FT73,29^A0R,104,98^FH\\^FD");
                sb.append(locationTree.get(4));
                sb.append("^FS\\n");
            }

            sb.append("^BY5,3,237^FT48,559^BCR,,N,N\n" +
                    "^FD>://");
            sb.append(id);
            sb.append("^FS\n");
            if (locationTree.size() >= 5) {
                sb.append("^LRY^FO348,807^GB0,383,350^FS^LRN\n");
            }
            sb.append("^PQ1,0,1,Y^XZ\n");


        }
        if (size == "large") {
            sb.append("^XA^PR5^PW406");
            int treeStart = 370;
            for (String s : locationTree) {
                sb.append("^FO");
                sb.append(treeStart);
                sb.append(",10^Adr,36,20^FD");
                sb.append(s);
                sb.append("^FS");
                treeStart = treeStart - 50;

            }
            sb.append(
                    "^FO300,300" +
                            "^FB500,2,5,C," +
                            "^ABr,40,24^FD");
            sb.append(locationTree.get(locationTree.size() - 1));
            sb.append("^FS");
            if (locationTree.get(locationTree.size() - 1).contains("Section")) {
                System.out.println("sectioning");
                sb.append("^FO130,625^XGE:RARROW.GRF,1,1^FS");
            }
            sb.append("^FO35,325" +
                    "^FB500,1,,C," +
                    "^B3R,N,100,N,N^FD");
            sb.append("//" + id);
            sb.append("^FS" +

                    "^FO0,300^GB550,0,4,^FS" +
                    "^FO225,300^GBO,350,4^FS" +
                    "^XZ");
        }
        if (size == "small") {

            sb.append("^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR6,6~SD15^JUS^LRN^CI0^XZ");
            sb.append("~DG000.GRF,00512,008,");
            sb.append(",:::::::::::H020,H038,H03E80,H01FC0,I0FE0,I07FC,I03FE80J080,I01FFC0,J0IF0,J07FFC,J0JF80,J07FHFC0,J03FIF8,J01FIFC,K0KF80,K07FIFC0,K07FJF0,:K0KFA0,J01FIFC,J03FIF8,J07FHF40,J0IFE80,I01FHF4,I03FFE0,I07FF,I0HFE,H01FF0,H03F80,H07C,H0F8,H0C0,,:::::::::::::::::::^XA");
            sb.append("^MMT");
            sb.append("^PW406");
            sb.append("^LL0203");
            sb.append("^LS0");
            if (locationTree.get(locationTree.size() - 1).contains("Section")) {

                sb.append("^FT288,128^XG000.GRF,1,1^FS");
                System.out.println("sectioning");
            }
            sb.append("^BY2,3,75^FT121,191^BCN,,N,N");
            sb.append("^FD");
            sb.append("//" + id);
            sb.append("^FS");

            sb.append("^FT180,62^A0N,34,33^FH^FD");
            sb.append(locationTree.get(locationTree.size() - 1));
            sb.append("^FS");
            int count = 1;
            for (String loc : locationTree) {
                if (count == 1) {
                    sb.append("^FT7,30^A0N,23,24^FH^FD");
                    sb.append(loc);
                    sb.append("^FS");
                }
                if (count == 2) {
                    sb.append("^FT7,62^A0N,23,24^FH^FD");
                    sb.append(loc);
                    sb.append("^FS");
                }
                if (count == 3) {
                    sb.append("^FT7,94^A0N,23,24^FH^FD");
                    sb.append(loc);
                    sb.append("^FS");
                }
                if (count == 4) {
                    sb.append("^FT7,126^A0N,23,24^FH^FD");
                    sb.append(loc);
                    sb.append("^FS");
                }
                if (count == 5) {
                    sb.append("^FT7,158^A0N,23,24^FH^FD");
                    sb.append(loc);
                    sb.append("^FS");
                }


                count++;
            }
            sb.append("^FO123,89^GB159,0,3^FS");
            sb.append("^FO105,6^GB0,188,3^FS");
            sb.append("^PQ1,0,1,Y^XZ");
            sb.append("^XA^ID000.GRF^FS^XZ");


        }

        try {

            if (size == "none") {
                throw new Exception("Unable to determine printer size");
            }
            sentToPrinter(sb.toString(), printer);
            b.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            b.addError(e.getMessage());
        }


        return b;
    }

    private static void sentToPrinter(String label, String printer) throws IOException {

        Socket clientSocket = new Socket(printer, 9100);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        outToServer.writeBytes(label);
        clientSocket.close();
    }
}
