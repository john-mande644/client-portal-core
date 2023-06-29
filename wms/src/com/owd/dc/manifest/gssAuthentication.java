package com.owd.dc.manifest;

import GSSMailer.ProcessPackageLocator;
import GSSMailer.ProcessPackageSoap_PortType;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: May 19, 2010
 * Time: 4:05:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class gssAuthentication {

   private String workStationID;
     private final String MailingAgentID = "ONEWORLDRUSM";
    private final String DefaultEEL = "NOEEI 30.37(a)";
   // private final String locationId = "MOBRIDSDONEWORLDRUSM";
   // private final String password = "0792owdgss";
   // private final String userID = "OWDuser";
    private String AccessToken;
    private long authenticationTime = 0;
    private String location;


    public gssAuthentication(String workstationId,String facilityLocation){



        workStationID = workstationId;
         location = facilityLocation;



    }

    public String getWorkStationID() {
        return workStationID;
    }

    public String getLocation() {
        return location;
    }

    public void setWorkStationID(String workStationID) {
        this.workStationID = workStationID;
    }

    public String getAccessToken() throws Exception {
        System.out.println("getting Authentication");
        if (workStationID.length() < 1) throw new Exception("Please supply a valid Workstation ID");
        boolean authenticate = true;
        gssLoginDetails loginDetails = new gssLoginDetails(location);

        if (null != AccessToken) {
            if (authenticationTime == 0 || (Calendar.getInstance().getTimeInMillis() - authenticationTime) > 900000) {

            } else {
                authenticate = false;
            }

        }
        if (authenticate) {

            ProcessPackageLocator locator = new ProcessPackageLocator();
            ProcessPackageSoap_PortType service = locator.getProcessPackageSoap();
            System.out.println("gettting authentication");
            System.out.println(loginDetails.getUserID());
            GSSMailer.AuthenticateResult ar = service.authenticateUser(loginDetails.getUserID(), loginDetails.getPassword(), loginDetails.getLocationId(), workStationID);
              System.out.println("got authentication");
            /* ProcessPackageSoap ps = new ProcessPackage().getProcessPackageSoap();
         System.out.println("Getting Authentication Key");
         AuthenticateResult ar = ps.authenticateUser(userID,password,locationId,WorkStationId);*/
            if (ar.getResponseCode() != 0) {
                throw new Exception(ar.getMessage());

            }
            AccessToken = ar.getAccessToken();
            authenticationTime = Calendar.getInstance().getTimeInMillis();


        }
        System.out.println("good Authentication");
        System.out.println(AccessToken);
        return AccessToken;

    }
    public static void main(String[] args){
        try{
        gssAuthentication auth = new gssAuthentication("TEsting","DC6");
        System.out.println(auth.getAccessToken());
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public class gssLoginDetails{

        public gssLoginDetails(String location){
             if(location.equalsIgnoreCase("DC1")){
                locationId = "MOBRIDSDONEWORLDRUSM";
                password = "0792owdgss";
                userID = "OWDuser";

            }
            if(location.equalsIgnoreCase("DC7")){
                locationId = "COLUMBOHONEWORLDRUSM";
                password = "OWDOH0511gss";
                userID = "OWDOHUSER";

            }
               if(location.equalsIgnoreCase("DC6")){
                 locationId= "RANCHOCAONEWORLDRUSM";
                 password=    "GSSOWD0213";
                 userID =     "OWDRancho";
             }
        }
    private String locationId = "MOBRIDSDONEWORLDRUSM";
    private String password = "0792owdgss";
    private String userID = "OWDuser";

        public String getLocationId() {
            return locationId;
        }


        public String getPassword() {
            return password;
        }

        public String getUserID() {
            return userID;
        }


    }

}
