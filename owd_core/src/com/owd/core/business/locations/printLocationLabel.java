package com.owd.core.business.locations;

import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Map;


public class printLocationLabel {
private final static Logger log =  LogManager.getLogger();

    public static void main(String args[]){
       /* List l = new ArrayList();
        l.add("Zone: a1");
        l.add("Aisle: 4");
        l.add("Rack: 6");
        l.add("Shelf: 4");
        l.add("Section: 3");

        String id = "123456";
        String printer = "zebra1.dc1.owd.com";
        log.debug(printLocation(l,id,printer));

        
        log.debug(printMobile("Pallet: 12345","1236","zebra1.dc1.owd.com"));
        log.debug(printMobile("Pallet: 12346","12356","zebra1.dc1.owd.com"));
        log.debug(printMobile("Pallet: 12347","456","zebra1.dc1.owd.com"));
        log.debug(printMobile("Pallet: 12348","4123456","zebra1.dc1.owd.com"));
        log.debug(printMobile("Pallet: 12349","15523456","zebra1.dc1.owd.com"));
        log.debug(printMobile("Pallet: 123410","1223456","zebra1.dc1.owd.com"));
        log.debug(printMobile("Pallet: 123411","1236456","zebra1.dc1.owd.com"));
        log.debug(printMobile("Pallet: 12341","12345996","zebra1.dc1.owd.com"));
        try{
        Thread.sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }
        log.debug(printMobile("Bin: 41","1996","zebra1.dc1.owd.com"));*/

        try{

            Session sess = HibernateSession.currentSession();

          resultBean b = printLocationTree("8","zebra1.dc1.owd.com",sess);
            log.debug(b.getErrors());
            log.debug(b.getMessages());

       // log.debug(printLocationById("36433","zebra1.dc1.owd.com", sess));
     /*   log.debug(printLocationById("36454","zebra1.dc1.owd.com", sess));
            log.debug(printLocationById("36455","zebra1.dc1.owd.com", sess));
            log.debug(printLocationById("36457","zebra1.dc1.owd.com", sess));*/


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static resultBean printTreeMap(Map<String,String> locs,String printer,Session sess){
        resultBean b = new resultBean();
        try{

            for(String s:locs.keySet()){
                resultBean be = printLocationTree(s,printer,sess);
                if (be.getErrors().length()>0) b.addError(be.getErrors());
            }
            b.addMessage("Printed Tree good");
            b.setSuccess(true);
        } catch(Exception e){
            e.printStackTrace();
            b.addError(e.getMessage());
        }
        return b;
    }
    public static resultBean printLocationTree(String id,String printer, Session sess){
        resultBean b = new resultBean();
                 try{

              List<String> locs = locationUtilities.getChildTreeList(sess,id);

                  for(String s:locs){

                    resultBean be = printLocationById(s,printer,sess);
                if (be.getErrors().length()>0) b.addError(be.getErrors());
                  }

                b.addMessage("Printed Location Tree");
                     b.setSuccess(true);
                 } catch(Exception e){
                     e.printStackTrace();
                     b.addError(e.getMessage());
                 }

        return b;
    }
    public static resultBean printDirectChildrenNoMobile(int id, String printer, Session sess){
        return printDirectChildrenNoMobile(id+"",printer,sess);
    }

    public static resultBean printDirectChildrenNoMobile(String id, String printer, Session sess){
        resultBean b = new resultBean();
                 try{
                   b = loadAndPrintDirectChildrenNoMobile(id,printer,sess);
                 } catch(Exception e){
                     e.printStackTrace();
                     b.addError(e.getMessage());
                 }

        return b;
    }
    public static resultBean printLocationMap(Map<String,String> locs,String printer, Session sess){
        resultBean b = new resultBean();
        try{

            for(String s:locs.keySet()){
                resultBean be = printLocationById(s,printer,sess);
                if (be.getErrors().length()>0) b.addError(be.getErrors());
            }
            b.addMessage("Printed all good");
            b.setSuccess(true);
        } catch(Exception e){
            e.printStackTrace();
            b.addError(e.getMessage());
        }
        return b;
    }
    private static resultBean loadAndPrintDirectChildrenNoMobile(String id, String printer, Session sess){
        resultBean b = new resultBean();

        try{
       Map<String,String> children = locationUtilities.getDirectChildMapForIdNoMobile(sess,id);

            for(String s:children.keySet()){
                resultBean be = printLocationById(s,printer,sess);
                if (be.getErrors().length()>0) b.addError(be.getErrors());

            }
            
           b.setSuccess(true);
            b.addMessage("Printed Children Locations for: "+id);
        } catch(Exception e){
            e.printStackTrace();
            b.addError(e.getMessage());
        }
        return b;
    }
    public static resultBean printLocationById(String id,String printer, Session sess){
        resultBean b = new resultBean();
        try{
                 if(locationUtilities.isMobile(id,sess)){
                     b = loadAndPrintMoblie(id,printer,sess);
                 }else{
                     log.debug("Doing static locaiton label");
                    b = loadAndPrintLocationById(id,printer,sess);
                 }

        
        }catch (Exception e){
            e.printStackTrace();
            b.addError(e.getMessage());
        }
        return b;

    } 
   private static resultBean loadAndPrintMoblie(String id, String printer, Session sess){
        resultBean b = new resultBean();
        try{
              b = printMobile(locationUtilities.getLocNameForMobile(id,sess),id,printer);
        } catch(Exception e){
            log.debug("fail");
            b.addError(e.getMessage());
        }

       return b;
   }
    private static resultBean loadAndPrintLocationById(String id, String printer, Session sess){
       resultBean b = new resultBean();
                 try{
                     List<String> locList = locationUtilities.getLocationTreeForIdFilterByType(sess,id,5);

                    b = printLocation(locList,id,printer);


                 } catch (Exception e){
                     e.printStackTrace();
                     b.addError(e.getMessage());
                 }

        return b;
    }

    private static resultBean printMobile(String text, String id, String printer){
         resultBean b = new resultBean();
            StringBuffer sb = new StringBuffer();
        sb.append("^XA" +
                "^PR5" +

                "^FO300,0" +
                "^FB900,2,5,C," +
                "^ABr,40,24^FD");
        sb.append(text);
        sb.append("^FS" +

                "^FO35,250" +

                "^B3R,N,100,N,N^FD");
        sb.append(id);
        sb.append("^FS" +


                "^FO200,0^GBO,900,4^FS" +
                "^XZ");

         try{
               sentToPrinter(sb.toString(),printer);
             b.setSuccess(true);
         }catch (Exception e){
             e.printStackTrace();
             b.addError(e.getMessage());
         }


        return b;
    }
    private static resultBean printLocation(List<String> locationTree, String id, String printer){
       log.debug("Doing "+ id);
        resultBean b = new resultBean();
          StringBuffer sb = new StringBuffer();
        sb.append("^XA^PR5");
        int treeStart = 370;
        for(String s:locationTree){
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
       sb.append(locationTree.get(locationTree.size()-1));
        sb.append("^FS" +

                "^FO35,350" +
                "^FB500,1,,C," +
                "^B3R,N,100,N,N^FD");
        sb.append(id);
        sb.append("^FS" +

                "^FO0,300^GB550,0,4,^FS" +
                "^FO200,300^GBO,500,4^FS" +
                "^XZ");
       try{
        sentToPrinter(sb.toString(),printer);
           b.setSuccess(true);
       }catch (Exception e){
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
