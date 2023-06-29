package com.owd.OWDShippingAPI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.owd.OWDShippingAPI.Models.AddressValidationRequest;
import com.owd.OWDShippingAPI.Models.AddressValidationResponse;
import com.owd.OWDShippingAPI.Services.AddressValidationService;
import com.owd.OWDShippingAPI.Services.OWDShippingAPIServiceGenerator;
import com.owd.OWDShippingAPI.authentication.OWDShippingAPIToken;
import com.owd.core.business.Address;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderShipInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import retrofit2.Call;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 12/19/2019.
 */
public class AddressValidationUtilities {
    private final static Logger log = LogManager.getLogger();



    public Address getValidatedAddressFromOrderId(Integer orderId) throws Exception{

        AddressValidationResponse response = validateAddressFromOrderId(orderId);

        checkAddressScoreForClientOrderId(response, orderId);

        return loadAddressFromResponse(response);

    }

    public void getValidatedAddress(Address add, Integer clientId) throws Exception{
        AddressValidationResponse response = validateAddress(add);
        //reset country to original value
        response.setInputCountry(add.getCountry());
        checkAddressScoreForClient(response, clientId);
        Address updated =  loadAddressFromResponse(response);
        add.setAddress_one(updated.getAddress_one());
        add.setAddress_two(updated.getAddress_two());
        add.setCity(updated.getCity());
        add.setState(updated.getState());
        add.setZip(updated.getZip());
        add.setCompany_name(updated.getCompany_name());
        add.setCountry(updated.getCountry());
        add.isResidential = updated.isResidential;
        add.verificationNote = updated.verificationNote;
    }

    private Address loadAddressFromResponse(AddressValidationResponse response){
        Address a = new Address();
        a.setAddress_one(response.getOutputAddress1());
        a.setAddress_two(response.getOutputAddress2()==null?"":response.getOutputAddress2());
        a.setCity(response.getOutputCity());
        a.setState(response.getOutputState());
        a.setZip(response.getOutputPostalCode());
        a.setCountry(response.getInputCountry());
        a.setCompany_name(response.getOutputCompany()==null?".":response.getOutputCompany());
        //Residential Indicator will be R for residential address. Anything else is not
       a.isResidential = response.getResidentialFlagIndicator().equals("R");
        a.verificationNote = response.getAddressScore()+": "+response.getAddressNote();
        return a;
    }

    private void checkAddressScoreForClient(AddressValidationResponse response, Integer clientId)throws Exception{
        if(null != response.getError()){
            throw new Exception(response.getAddressScore()+": "+  response.getError());
        }
        List<String> validScores = getValidScoresForClientId(clientId);
        if(validScores.contains(response.getAddressScore())){

            // Per FS request, all 92 and 93 will be allowed through by default.
           /* if(response.getAddressScore().equals("92")){
                if(response.getAddressNote().endsWith("AABB")||response.getAddressNote().endsWith("AACC")
                        ||response.getAddressNote().endsWith("AAF1")||response.getAddressNote().endsWith("AARR")||response.getAddressNote().endsWith("AA")){

                }else{
                    throw new Exception(generateErrorFromResponse(response));
                }

            }
            if(response.getAddressScore().equals("93")){
                if(response.getAddressNote().endsWith("AABB")||response.getAddressNote().endsWith("AACC")
                        ||response.getAddressNote().endsWith("AAF1")||response.getAddressNote().endsWith("AARR")||response.getAddressNote().endsWith("AA")){

                }else{
                    throw new Exception(generateErrorFromResponse(response));
                }

            }*/



        }else{
            throw new Exception(generateErrorFromResponse(response));
        }

    }

    private void checkAddressScoreForClientOrderId(AddressValidationResponse response, Integer orderId)throws Exception{
        if(null != response.getError()){
            throw new Exception("Rejected Score: "+response.getAddressScore()+": "+  response.getError());
        }
        List<String> validScores = getValidScoresForOrderId(orderId);
        if(validScores.contains(response.getAddressScore())){
            if(response.getAddressScore().equals("92")){
                if(response.getAddressNote().endsWith("AABB")||response.getAddressNote().endsWith("AACC")
                        ||response.getAddressNote().endsWith("AAF1")||response.getAddressNote().endsWith("AARR")||response.getAddressNote().endsWith("AA")){

                }else{
                    throw new Exception(generateErrorFromResponse(response));
                }

            }
            if(response.getAddressScore().equals("93")){
                if(response.getAddressNote().endsWith("AABB")||response.getAddressNote().endsWith("AACC")
                        ||response.getAddressNote().endsWith("AAF1")||response.getAddressNote().endsWith("AARR")||response.getAddressNote().endsWith("AA")){

                }else{
                    throw new Exception(generateErrorFromResponse(response));
                }

            }



        }else{
            throw new Exception(generateErrorFromResponse(response));
        }

    }

    private String generateErrorFromResponse(AddressValidationResponse response){
        StringBuilder sb = new StringBuilder();
        sb.append("Rejected Score: ");
        sb.append(response.getAddressScore());
        sb.append(" Note: " );
        sb.append(response.getAddressNote());
        if(null!=response.getOutputAddress1()){
            sb.append(" Possible Address Fix: \n");
            sb.append("Address 1: ");
            sb.append(response.getOutputAddress1());
            sb.append("\nAddress2: ");
            sb.append(response.getOutputAddress2());
            sb.append("\nCity: ");
            sb.append(response.getOutputCity());
            sb.append("\nState: ");
            sb.append(response.getOutputState());
            sb.append("\nZip: " );
            sb.append(response.getOutputPostalCode());

        }

        return sb.toString();
    }

    private List<String> getValidScoresForClientId(Integer clientId){
        List<String> l = new ArrayList<>();
        try{
            Query q = HibernateSession.currentSession().createSQLQuery("exec addressVerificationScoresForClient :orderId");
            q.setParameter("orderId",clientId);
            List ll = q.list();
            if(ll.size()>0){
                GsonBuilder builder = new GsonBuilder();

                Gson gson = builder.create();
                String s = ll.get(0).toString();
                l = gson.fromJson(s,List.class);
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return l;
    }

    private List<String> getValidScoresForOrderId(Integer orderId){
        List<String> l = new ArrayList<>();
        try{
            Query q = HibernateSession.currentSession().createSQLQuery("exec addressVerificationScoresForOrderId :orderId");
            q.setParameter("orderId",orderId);
            List ll = q.list();
            if(ll.size()>0){
                GsonBuilder builder = new GsonBuilder();

                Gson gson = builder.create();
                String s = ll.get(0).toString();
                l = gson.fromJson(s,List.class);
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return l;
    }

    /**
     * This method loads the address from the orderShipInfo hibernate object loaded from the order ID
     * It returns a filled out AddressValidationResponse. It is up to the caller to determine what to do with the results
     * @param orderId orderId you want to validate
     * @return
     * @throws Exception
     */
    public AddressValidationResponse validateAddressFromOrderId(Integer orderId) throws Exception{
            AddressValidationRequest request = loadAddressValidateRequestFromOrderId(orderId);
        return validateAddress(request);

    }

    public AddressValidationResponse validateAddress(Address add) throws Exception{
        AddressValidationRequest request = loadAddressValidateRequest(add);
        return validateAddress(request);
    }

    private AddressValidationRequest loadAddressValidateRequest(Address add) throws Exception{
        AddressValidationRequest request = new AddressValidationRequest();
        request.setInputContact(add.getCompany_name());
       request.setInputAddress1(add.getAddress_one());
        request.setInputAddress2(add.getAddress_two());
        request.setInputCity(add.getCity());
        request.setInputState(add.getState());
        request.setInputPostalCode(add.getZip());
        if(add.getCountry().length()>2){
            ShippingUtilities su = new ShippingUtilities();
            request.setInputCountry(su.getIANACountry(add.getCountry(),add.getCity()));
        }else{
            request.setInputCountry(add.getCountry());
        }

        return request;
    }

    /**
     * This method loads the AddressValidationRequest with ship to Address info from the OWDOrderShipInfo.
     *
     * @param orderId orderId you want to validate
     * @return filled out AddressValidationRequest
     * @throws Exception throws error if order cannot be loaded from ID
     */
    private AddressValidationRequest loadAddressValidateRequestFromOrderId(Integer orderId) throws Exception{
        OwdOrder order = (OwdOrder) HibernateSession.currentSession().get(OwdOrder.class,orderId);
        if(order!=null){
            AddressValidationRequest request = new AddressValidationRequest();
            OwdOrderShipInfo info = order.getShipinfo();
            request.setInputContact(info.getShipFirstName()+ " "+ info.getShipLastName());
            request.setInputAddress1(info.getShipAddressOne());
            request.setInputAddress2(info.getShipAddressTwo());
            request.setInputCity(info.getShipCity());
            request.setInputState(info.getShipState());
            request.setInputPostalCode(info.getShipZip());
            ShippingUtilities su = new ShippingUtilities();
            request.setInputCountry(su.getIANACountry(info.getShipCountry(), info.getShipCity()));
            return request;
        }else{
            throw new Exception("Order Id Not found");
        }



    }


    /**
     * This method send the request to validate address. It will return the results with output Address
     * along with a score that can be evaluated to determine if you want to accept the changes
     *
     *
     *
     * @param request AddressValidationRequest filled out request of address to be checked
     * @return results of the address check
     * @throws Exception
     */
    public AddressValidationResponse validateAddress(AddressValidationRequest request) throws Exception{
        request.setServiceEngine("PS_ALF.SATORI.AV1");

        return getValidationResponse(request);


    }




    private AddressValidationResponse getValidationResponse(AddressValidationRequest request)throws Exception{
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        log.debug(gson.toJson(request));
        String token= OWDShippingAPIToken.grabNewToken();
        AddressValidationService sService = OWDShippingAPIServiceGenerator.createService(AddressValidationService.class, token);

        Call<AddressValidationResponse> callSync = sService.getValidation(request);
        Response< AddressValidationResponse> response = callSync.execute();
        log.debug(response.code());
        AddressValidationResponse addressResponse;
        if(response.code() == 200) {
            addressResponse = response.body();

            log.debug(gson.toJson(addressResponse));


        }else{
            log.debug(response.message());
            //todo better error handling with proper messages
            throw new Exception(response.message());
        }

        return addressResponse;



    }

    public static void main(String[] args){
        generateValidScoreJson();
    }
    private static void generateValidScoreJson(){
        List<String> l = new ArrayList<>();
        l.add("0");
        l.add("7");
        l.add("8");
        l.add("9");
        l.add("10");
        l.add("11");
        l.add("12");
        l.add("13");
        l.add("21");
        l.add("22");
        l.add("23");
        l.add("24");
        l.add("25");
        l.add("26");
        l.add("27");
        l.add("31");
        l.add("32");
        l.add("33");
        l.add("34");
        l.add("35");
        l.add("63");
        l.add("64");
        l.add("65");
        l.add("69");
        l.add("71");
        l.add("79");
        l.add("80");
        l.add("84");
        l.add("86");
        l.add("92");
        GsonBuilder builder = new GsonBuilder();

        Gson gson = builder.create();
        System.out.println(gson.toJson(l));

    }
}
