package com.owd.dc.manifest.ExternalApis.OWDEasyPost;

/**
 * Created by danny on 5/8/2017.
 */

import com.easypost.EasyPost;
import com.easypost.model.ApiKey;
import com.easypost.model.CarrierAccount;
import com.easypost.model.User;
import com.owd.dc.manifest.api.internal.AMPConnectShipShipment;
import com.owd.dc.manifest.api.internal.ShipConfig;
import com.owd.hibernate.generated.OwdClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EasyPostAccountsAndUsers {

    static final String xTESTEasypostKey = "hl0OMczdmtkyyCHY8dRxtQ";
    static final String xLIVEEasypostKey = "fZYWVmoq0OsLErY2vyVx8g";
    static final String xDC1LiveEasypostKey = "RnAfn900njkaEDr0J6zmBQ";
    static final String xDC1TestEasypostKey = "lIpV34IWf8O7zDWCNIrQBQ";

    static final String xBumblePurolatorLiveKey = "GdObLI4PuTTzYQOs0Xrpzg";


        // fiji live key SzsyZlqNNQ629HH5tgxzZw UPS only
    public static String getAndSetApiKeyForShipment(AMPConnectShipShipment ship, OwdClient client) throws Exception{
        String key = "";
        if(client.getClientId() == 489){
            if(ship.getAssignedServiceCode().toUpperCase().contains("PUROLATOR")&&ship.getValue((ShipConfig.GROUP_NAME)).equals("bumbleride")){
                return xBumblePurolatorLiveKey;
            }

        }else if(client.getClientId() == 575){
            if(ship.getAssignedServiceCode().toUpperCase().contains("PUROLATOR")){
                return xBumblePurolatorLiveKey;
            }

        }else{
            throw new Exception("This client is not allowed to ship this method");
        }

        return key;
    }


    public static void CreateNewUPSAccountForKey(String apiKey, String accountNumber, String userId, String password, String accessLicense, String description, String reference){

        try{
            Map<String, Object> credentials = new HashMap<String, Object>();
            credentials.put("account_number", accountNumber);
            credentials.put("user_id", userId);
            credentials.put("password", password);
            credentials.put("access_license_number", accessLicense);

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("type", "UpsAccount");
            params.put("description", description);
            params.put("reference", reference);
            params.put("credentials", credentials);

            CarrierAccount ca = CarrierAccount.create(params,apiKey);


        }catch (Exception e){
            e.printStackTrace();
        }


    }
    public static void CreateNewUser(String name){

        Map<String, Object> userMap = new HashMap<String, Object>();
        userMap.put("name", name);
        try {
            User user = User.create(userMap, xLIVEEasypostKey);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void UpdateUser(String id, String email, String password){



    }

    public static void main(String[] args){
      //  CreateNewUser("DC1 Mobridge");
       // ListLiveOwdUsers();
        GetInfoFromKey(xBumblePurolatorLiveKey);
       // ListCarrierAccount(xBumblePurolatorLiveKey);
     //   CreateNewUPSAccountForKey(xDC1LiveEasypostKey,"E58715","ktorevell","owd57601","aln","DC1 Mobridge UPS Account","DC1");
     //   ListCarrierAccount(xDC1LiveEasypostKey);


    }

    public static void ListCarrierAccount(String api){

        try{
            Map<String, Object> listHash = new HashMap<String, Object>();
            listHash.put("type", "UpsAccount");
            List<CarrierAccount> carrierAccounts = CarrierAccount.all(listHash,api);
            for(CarrierAccount ca : carrierAccounts){
                System.out.println(ca.getId());
                System.out.println(ca.getDescription());
                System.out.println(ca.prettyPrint());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void ListLiveOwdUsers(){

        EasyPost.apiKey = xLIVEEasypostKey;

        try {
            User user = User.retrieveMe();
            System.out.println(user.getName());

            System.out.println(user.getChildren().size());
            for(User u: user.getChildren()){
                System.out.println(u.getName());
                System.out.println(u.getId());
                List<ApiKey> keys =  (u.apiKeys());
                for(ApiKey key : keys){
                    System.out.println(key.getKey() +  "   " + key.getMode());

                }
                System.out.println(u.getEmail());



            }


        }catch (Exception e){
            e.printStackTrace();
        }




    }

    public static void GetInfoFromKey(String apikey){

        EasyPost.apiKey = apikey;

        try {
            User user = User.retrieveMe();
            System.out.println(user.getName());

            System.out.println(user.getChildren().size());
            System.out.println(user.getEmail());
            System.out.println(user.getPhoneNumber());
            for(User u: user.getChildren()){
                System.out.println(u.getName());
                System.out.println(u.getId());
                List<ApiKey> keys =  (u.apiKeys());
                for(ApiKey key : keys){
                    System.out.println(key.getKey() +  "   " + key.getMode());

                }
                System.out.println(u.getEmail());



            }


        }catch (Exception e){
            e.printStackTrace();
        }




    }


    public static Map<String,String> getCarrierAccounts(AMPConnectShipShipment ship,Map<String,Object> carriers,String facility,OwdClient client){


        Map<String,String> accounts = new HashMap<String, String>();
        if(client.getClientId()==575 && carriers.get("carrier").equals("Purolator")) {
            accounts.put("id","ca_3989ea69598240ecaf83ce707a100ed6");

        }

        if(ship.getValue((ShipConfig.GROUP_NAME)).equals("fijiwater")) {
            if(facility.equals("DC6")){
                accounts.put("id","ca_f780bf19963a425ea823c1b5e8e065ff");
            }
            if(facility.equals("DC7")){

                accounts.put("id","ca_d32d23bccdec49638116e2899619b548");

            }


        }



        return accounts;
    }
}
