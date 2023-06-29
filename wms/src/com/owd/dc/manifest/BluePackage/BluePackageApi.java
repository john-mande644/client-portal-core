package com.owd.dc.manifest.BluePackage;

import com.easypost.model.Parcel;
import com.easypost.model.Shipment;
import com.google.gson.Gson;
import com.owd.connectship.xml.api.OWDShippingResponse.LABELDATA;
import com.owd.connectship.xml.api.OWDShippingResponse.SHIPMENT;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderFactory;
import com.owd.dc.manifest.BluePackage.classes.*;
import com.owd.dc.manifest.Dimension;
import com.owd.dc.manifest.ExternalApis.OWDEasyPost.Customs.EasypostItemCustoms;
import com.owd.dc.manifest.ExternalApis.OWDEasyPost.EasyShipmentUtils;
import com.owd.dc.manifest.api.internal.AMPConnectShipShipment;
import com.owd.dc.manifest.api.internal.ShipConfig;
import com.owd.dc.packing.AutoBatchPrinting.PackingABUtils;
import com.owd.dc.warehouse.ABShipments.ABUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderShipInfo;
import com.squareup.okhttp.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.net.Facility;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.Proxy;
import java.sql.Clob;
import java.util.*;

public class BluePackageApi {

    protected final static Logger log = LogManager.getLogger();

    private static String URL = "https://us.iabol.com/api/hapi/consignment";
    private static String USERNAME = "oneworlddirect";
    private static String PASSWORD = "1worldbpd";

    private static OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static Authenticator auth = new Authenticator() {
        @Override
        public Request authenticate(Proxy proxy, Response response) throws IOException {
            return response.request().newBuilder().header("Authorization", Credentials.basic(USERNAME, PASSWORD)).build();
        }

        @Override
        public Request authenticateProxy(Proxy proxy, Response response) throws IOException {
            return null;
        }
    };

    private static String doGetRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private static String doPostRequest(String json) throws IOException {
        client.setAuthenticator(auth);
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private static String doDeleteRequest(String external_id) throws IOException {
        client.setAuthenticator(auth);
        Request request = new Request.Builder()
                .url(URL + "/" + external_id)
                .delete()
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    public static void main(String args[]) {
//        jsonTest();
        loadOrderTest();
//        loadResultTest();
    }

    public static void testShipObj(){

    }
    public static boolean jsonTest() {
        System.out.println("in test");
        Gson gson = new Gson();
        try {
            System.out.println("loadOrderTest");
            AMPConnectShipShipment ship = new AMPConnectShipShipment();
            System.out.println("loadOrderTestBeginning");
            ship.loadOrderFromOrderReference("p10330382*21861542*b1", true, "DC6");
            System.out.println(gson.toJson(ship));
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static boolean loadResultTest(){
        Gson gson = new Gson();
        List<String> results = new ArrayList<>();
        results.add("{\"MessageId\":\"dccc23da-ed1a-4bcc-9e6d-a6260d474ebe\",\"Status\":{\"Code\":0,\"Description\":\"SUCCESS\",\"Messages\":[{\"Code\":0,\"Description\":\"SUCCESS\"}]},\"ConsignmentID\":\"659514\",\"RouteGroupIDOrServiceCodeList\":\"1202\",\"AccountNumber\":\"124563\",\"AllocationFilter\":null,\"LabelType\":3,\"LabelSize\":0,\"LabelDPI\":0,\"PackageList\":[{\"HazmatContentList\":[],\"ExportLineItemList\":[],\"Base64LabelString1\":null,\"Base64LabelString2\":null,\"Base64LabelString3\":null,\"Base64LabelString4\":null,\"Base64LabelString5\":null,\"ShipToAttn\":null,\"Labels\":[{\"Base64\":\"XlhBDQpeUE9ODQpeUFJDDQpeTEgwLDBeRlMNCl5MTDEyMTgNCl5NRDANCl5NTlkNCl5MSDAsMF5GUw0KfkRHMDAwLkdSRiwwNTM3NiwwMjgsDQosOjo6Ojo6Ojo6Ojo6Ojo6Ojo6ZzAzRixnMEhGRTAsWTAzRklGRTAsWTBNRkMwLFgwM0ZNRkMsWDA3Rk9GMCxYMDdGODdGTkYwLFcwMUZFMEgwM0ZORjAsVzAzRkMwSTBPRkUsVzAzRjgwSjAxRk5GQyxXMDdGME0wMUZORkMsVzBGRTBPMDNGTkZFLFYwMUZDMFAwN0ZORjgwLFYwM0Y4MFIwN0ZMRkUwLFYwN0YwVDAzRktGRTAsVjBGRTBWMExGMCxVMDFGQzBXMDFGSUYwLFUwM0Y4MFgwMUZIRjAsVTA3RjBZMDFGSEY4LFUwRkUwWTAxRkhGOCxUMDFGQzBZMDNGREY4LFQwM0Y4MFkwN0Y4RjgsVDA3RjBnMEhGMEZDLFQwRkUwWTAxRkUwRkUsUzAxRkMwWTAzRkMwN0UsUzAzRjgwWTA3RjgwN0UsUzA3RjBnMEhGSDA3RSxTMEZFMFkwMUZFMDAzRixSMDFGQzBZMDNGQzAwM0YsUjAzRjgwWTA3RjgwMDNGLFIwN0YwZzBIRkkwMUY4MCxSMEZFMFkwMUZFMEgwMUY4MCxRMDFGQzBZMDNGQzBIMDFGODAsUTAzRjgwWTA3RjgwSTBGODAsUTA3RjBnMEhGSzBGQzAsUTBGRTBZMDFGRTBKMEZFMCxQMDFGQzBZMDNGQzBKMDdFMCxQMDNGODBZMDdGODBKMDdFMCxQMDdGMGcwSEZMMDdDMCxQMEZFMFkwMUZFMEswN0UwLE8wMUZDMFkwM0ZDMEswM0YwLE8wM0Y4MFkwN0Y4MEswMUYwLE8wN0YwZzBIRk0wMUYwLE8wRkUwWTAxRkUwTDAxRjAsTjAxRkMwWTAzRkMwTDAxRjgsTjAzRjgwWTA3RjgwTDAxRjgsTjA3RjBnMEhGTzBGQyxOMEZFMFkwMUZFME4wRkMsTTAxRkMwWTAzRkMwTjBGQyxNMDNGODBZMDdGODBOMDdFLE0wN0YwZzBIRlAwN0UsTTBGRTBZMDFGRTBPMDdFLEwwMUZDMFkwM0ZDME8wN0UsTDAzRjgwWTA3RjgwTzAzRixMMDdGMGcwSEZRMDNGLEwwRkUwWTAxRkUwUDAxRixLMDFGQzBZMDNGQzBQMDFGODAsSzAxRkMwWTA3RjgwUDAxRkMwLEswMUZIRjgwVzBIRlMwRkMwLEswMUZKRlYwMUZFMFIwRkMwLEswM0ZMRjgwUjAzRkMwUjBGQzAsSzAzRk1GRTBRMDdGODBSMEZFMCxLMDFGODNGTUZQMEhGVDA3RTAsSzAxRjgwMDNGTEZDMEwwMUZFMFMwM0UwLEswMUZDMEgwT0ZDMEowM0ZDMFMwN0UwLEwwRkMwSjA3Rk1GQzBIMDdGODBTMDNGMCxMMEZDMEwwM0ZNRjgwRkYwVDAzRjAsTDBGQzBOMDFGTkZFMFQwMUYwLEwwRkUwTzA3Rk1GQzBUMDFGOCxMMDdFMFEwM0ZLRjgwVDAxRjgsTDA3RTBTMDFGSUZXMEZDLEwwN0YwVjBIRlcwRjgsTDAzRjBWMDdFMFYwRkMsTDAxRjBWMDNGMFYwRkUsTDAxRjBWMDNGMFYwN0UsTDAxRjgwVTAzRjBWMDNFLEwwMUY4MFUwMUY4MFUwM0UsTTBGQzBVMDFGODBVMDNGLDpNMEZDMFYwRkMwVTAxRjgwLE0wN0UwVjBGQzBVMDFGODAsTTA3RTBWMDdDMFUwMUY4MCxNMDdFMFYwN0MwVTAxRkMwLE0wM0UwVjA3RTBVMDFGQzAsTTAzRjBWMDdGMFUwMUZDMCxNMDNGMFYwM0YwVTAzRjgwLE0wMUYwVjAzRjBVMDdGODAsTTAxRjgwVTAzRjBVMEhGLE0wMUZDMFUwM0Y4MFMwMUZFLE4wRkMwVTAxRjgwUzAzRkMsTjBGQzBWMEY4MFMwN0Y4LE4wRkMwVjBGQzBTMEhGMCxOMEZFMFYwRkMwUjAxRkUwLE4wN0UwVjA3QzBSMDNGQzAsTjAzRTBWMDdFMFIwN0Y4MCxOMDNFMFYwN0UwUjBIRixOMDNGMFYwN0UwUTAxRkUsTjAzRjgwVTAzRjBRMDNGQyxOMDFGMFYwM0YwUTA3RjgsTjAxRjgwVTAzRjBRMEhGMCxOMDFGODBVMDNGODBPMDFGRTAsTzBGQzBVMDFGODBPMDNGQzAsTzBGQzBWMEY4ME8wN0Y4MCxPMEZDMFYwRkMwTzBIRixPMEZFMFYwRkMwTjAxRkUsTzA3RTBWMEZDME4wM0ZDLE8wM0UwVjA3RTBOMDdGOCxPMDNFMFYwN0UwTjBIRjAsTzAzRjBWMDdFME0wMUZFMCxPMDNGODBVMDNGME0wM0ZDMCxPMDFGODBVMDNGME0wN0Y4MCxPMDFGODBVMDNGME0wSEYsTzAxRjgwVTAxRjgwSzAxRkUsUDBGQzBVMDFGODBLMDNGQyxQMEZDMFYwRjgwSzA3RjgsUDA3QzBWMEY4MEswSEYwLFAwN0UwVjBGQzBKMDFGRTAsUDA3RTBWMEZFMEowM0ZDMCxQMDdFMFYwN0UwSjA3RjgwLFAwM0UwVjA3RTBKMEhGLFAwM0YwVjA3RTBJMDFGRSxQMDNGODBVMDNGMEkwM0ZDLFAwMUY4MFUwM0YwSTA3RjgsUDAxRjgwVTAxRjBJMEhGMCxQMDFGODBVMDFGODAwMUZFMCxRMEhGRTBUMDFGODAwM0ZDMCxRMEpGQzBTMEY4MDA3RjgwLFEwN0ZKRkMwUTBGODAwRkYsUTA3RkxGUTBGQzAxRkUsUTA3Rk5GTzBGRTA3RkMsUjBQRkUwTDA3RTA3RjgsUzA3Rk9GRTBKMDdFMEZGMCxUMFJGSjA3RTFGRTAsVjA3RlBGODA3RkJGQzAsWDBRRkVGSUY4MCxZMFVGLGdHMFJGRSxnSTBQRkMsZ0swTkY4LGdMME1GMCxnTjBKRkUwLGdPMDFGQywsOjo6Ojo6Ojo6Ojo6Ojo6Ojo6Ojo6Ojo6Ojo6Ojo6XlhBDQpeRlQ1MTAsMzcwXlhHMDAwLkdSRiwxLDFeRlMNCl5GTzQ1MCw1Ml5HQjMyNSwxNDAsNF5GUw0KXkZPMzY1LDU1XkEwTiw2Nyw1MF5DSTEzXkZEQV5GUw0KXkZPNTI1LDYyXkEwTiwyNSwyN15DSTEzXkZEUEFSQ0VMIFNFTEVDVF5GUyAgICAgICAgIA0KXkZPNDU4LDk0XkEwTiwyNSwyN15DSTEzXkZEVS5TLiBQT1NUQUdFICYgRkVFUyBQQUlEXkZTDQpeRk81ODUsMTI2XkEwTiwyNSwyN15DSTEzXkZEQlBEXkZTDQpeRk81ODUsMTU4XkEwTiwyNSwyN15DSTEzXkZEZVZTXkZTDQpeRk8yNSw0NV5BME4sMzMsMjZeQ0kxM15GRFJlbGl6ZW4gUmV0dXJuc15GUw0KXkZPMjUsNzVeQTBOLDMzLDI2XkNJMTNeRkRTaGlwcGluZyBEZXBhcnRtZW50XkZTIA0KXkZPMjUsMTA1XkEwTiwzMywyNl5DSTEzXkZEMzMyNSBNYW5pdG91IENvdXJ0XkZTIA0KXkZPMjUsMTM1XkEwTiwzMywyNl5DSTEzXkZETWlyYSBMb21hIENBIDkxNzUyXkZTIA0KXkZPMjUsMTY1XkEwTiwzMywyNl5DSTEzXkZEXkZTDQpeRk8zMywzMDBeQTBOLDMzLDMwXkNJMTNeRkRDQVJSSUVSIExFQVZFIElGIE5PIFJFU1BPTlNFXkZTDQpeRk8xNjgsMzk1XkEwTiwzMywzMl5DSTEzXkZEUGhvbmUjXkZTDQpeRk8yNzUsMzk1XkEwTjMzLDMyXkNJMTNeRkQ5MjU5Nzg3NjM1XkZTDQpeRk8yMCwzOTVeQTBOLDUwLDUwXkNJMTNeRkRTSElQXkZTDQpeRk8yMCw0MzZeQTBOLDUwLDUwXkNJMTNeRkRUTzpeRlMNCl5GTzE2OCw0MzZeQTBOLDMzLDMyXkNJMTNeRkRQQVRSSUNJQSBCT1RFTEhPXkZTICANCl5GTzE2OCw0NjleQTBOLDMzLDMyXkNJMTNeRkQuXkZTIA0KXkZPMTY4LDUwMl5BME4sMzMsMzJeQ0kxM15GRDU2MCBNVCBPTElWRVQgUExeRlMNCl5GTzE2OCw1MzVeQTBOLDMzLDMyXkNJMTNeRkRDTEFZVE9OIENBIDk0NTE3XkZTIA0KXkZPMTY4LDU2OF5BME4sMzUsMzVeQ0kxM15GRF5GUw0KXkZPMjAsNjExXkdCNzkwLDAsMTReRlMNCl5GTzE0MCw2NTNeQTBOLDMyLDMxXkNJMTNeRkReRlMNCl5GTzIxMCw2NTNeQTBOLDMyLDMxXkNJMTNeRkQgICAgICBVU1BTIFRSQUNLSU5HICMgZVZTXkZTDQpeRk84MCw3MDheQlkzLDMuMF5CQ04sMTYwLE4sWSxOXkZSXkZEPjs+ODQyMDk0NTE3Pjg5MjYxMjE1MTUxNTE0ODAwMDIxNTIxXkZTDQpeRk8xNTAsODk4XkEwTiwzMiwzMV5DSTEzXkZEXkZTDQpeRk8yMTAsODk4XkEwTiwzMiwzMV5DSTEzXkZEOTI2MSAyMTUxIDUxNTEgNDgwMCAwMjE1IDIxXkZTDQpeRk8yMCw5NTFeR0I3OTAsMCwxNF5GUw0KXkZPNTAsMTAyMV5BME4sMjQsMjleQ0kxM15GRDQyMDk0NTE3OTI2MTIxNTE1MTUxNDgwMDAyMTUyMV5GUw0KXkZPNTAsOTgxXkEwTiwyMSwyNV5DSTEzXkZEMTY0NTU5MTReRlMNCl5GTzUwLDEwMDFeQTBOLDIxLDI1XkNJMTNeRkQyMzUxMjEyOF5GUw0KXkZPMzUwLDEwNTBeQTBOLDY3LDUyXkNJMTNeRkQgICAgIENDUiAtQSAtMTReRlMNCl5GTzEwMCwxMTQyXkEwTiw2Nyw1MF5DSTEzXkZERERVXkZTDQpeRk8yMDAsMTEzN15BME4sMzMsMjZeQ0kxM15GRDYxNTAgQ0VOVEVSIFNUXkZTDQpeRk8yMDAsMTE2N15BME4sMzMsMjZeQ0kxM15GRENMQVlUT04sIENBIDk0NTE3XkZTDQpeRk82OTUsMTE2N15BME4sMzAsMjReQ0kxM15GREJQOTFeRlMNCl5QUTEsMCwwLE4NCl5YWg0KXkZYIEVuZCBvZiBqb2INCl5YQQ0KXlBPTg0KXklEUjpJRCouKg0KXlha\",\"LabelType\":3,\"LabelSize\":0}],\"PackagingType\":105,\"AddlPackageReference1\":\"16455914\",\"AddlPackageReference2\":\"23512128\",\"AddlPackageReference3\":\"\",\"AddlPackageReference4\":\"\",\"AddlPackageReference5\":\"\",\"AddlPackageReference6\":\"\",\"AddlPackageReference7\":\"\",\"AddlPackageReference8\":\"\",\"ReferenceNumber\":\"\",\"TrackingNumber\":\"9261215151514800021521\",\"PackageId\":\"659514-0001\",\"Description\":null,\"Length\":4.0,\"Height\":2.0,\"Width\":6.0,\"Weight\":1.0,\"BaseRate\":0.0,\"TotalRate\":0.0,\"BillWeight\":0.0,\"VasFees\":[{\"Code\":452,\"Amount\":0.0}]}],\"VASList\":[{\"Code\":452,\"Amount\":0.0}],\"FreightShipment\":null,\"ShipFrom\":{\"Type\":0,\"PhoneAreaCode\":\"JFK1\",\"Company\":\"Relizen Returns\",\"ResidentialFlag\":false,\"Department\":null,\"Attn\":\"Shipping Department\",\"AddressLine1\":\"3325 Manitou Court\",\"AddressLine2\":\"\",\"AddressLine3\":null,\"City\":\"Mira Loma\",\"StateCode\":\"CA\",\"Zip\":\"91752\",\"Zip4\":null,\"CountryCode\":\"US\",\"CountryName\":\"United States\",\"Phone\":\"9999999999\",\"PhoneExt\":null,\"Fax\":null,\"EMail\":\"test@iabol.com\",\"Reference\":null},\"ShipTo\":{\"Type\":0,\"PhoneAreaCode\":null,\"Company\":\".\",\"ResidentialFlag\":false,\"Department\":null,\"Attn\":\"Patricia Botelho\",\"AddressLine1\":\"560 MT OLIVET PL\",\"AddressLine2\":\"\",\"AddressLine3\":null,\"City\":\"CLAYTON\",\"StateCode\":\"CA\",\"Zip\":\"94517\",\"Zip4\":\"1610\",\"CountryCode\":\"US\",\"CountryName\":\"United States\",\"Phone\":\"9259787635\",\"PhoneExt\":null,\"Fax\":null,\"EMail\":null,\"Reference\":null},\"Return\":{\"Type\":0,\"PhoneAreaCode\":null,\"Company\":\"Relizen Returns\",\"ResidentialFlag\":false,\"Department\":null,\"Attn\":\"Shipping Department\",\"AddressLine1\":\"3325 Manitou Court\",\"AddressLine2\":\"\",\"AddressLine3\":null,\"City\":\"Mira Loma\",\"StateCode\":\"CA\",\"Zip\":\"91752\",\"Zip4\":null,\"CountryCode\":\"US\",\"CountryName\":\"United States\",\"Phone\":\"9999999999\",\"PhoneExt\":null,\"Fax\":null,\"EMail\":\"test@iabol.com\",\"Reference\":null},\"ShipFromHub\":{\"Type\":0,\"PhoneAreaCode\":null,\"Company\":null,\"ResidentialFlag\":false,\"Department\":null,\"Attn\":null,\"AddressLine1\":null,\"AddressLine2\":null,\"AddressLine3\":null,\"City\":null,\"StateCode\":null,\"Zip\":null,\"Zip4\":null,\"CountryCode\":null,\"CountryName\":null,\"Phone\":\"\",\"PhoneExt\":null,\"Fax\":null,\"EMail\":null,\"Reference\":null},\"USPSLocation\":{\"Type\":0,\"PhoneAreaCode\":null,\"Company\":null,\"ResidentialFlag\":false,\"Department\":null,\"Attn\":null,\"AddressLine1\":null,\"AddressLine2\":null,\"AddressLine3\":null,\"City\":null,\"StateCode\":null,\"Zip\":null,\"Zip4\":null,\"CountryCode\":null,\"CountryName\":null,\"Phone\":null,\"PhoneExt\":null,\"Fax\":null,\"EMail\":null,\"Reference\":null},\"HoldAtLocation\":{\"Type\":0,\"PhoneAreaCode\":null,\"Company\":null,\"ResidentialFlag\":false,\"Department\":null,\"Attn\":null,\"AddressLine1\":null,\"AddressLine2\":null,\"AddressLine3\":null,\"City\":null,\"StateCode\":null,\"Zip\":null,\"Zip4\":null,\"CountryCode\":null,\"CountryName\":null,\"Phone\":\"\",\"PhoneExt\":null,\"Fax\":null,\"EMail\":null,\"Reference\":null},\"CODRecipient\":{\"Type\":0,\"PhoneAreaCode\":null,\"Company\":null,\"ResidentialFlag\":false,\"Department\":null,\"Attn\":null,\"AddressLine1\":null,\"AddressLine2\":null,\"AddressLine3\":null,\"City\":null,\"StateCode\":null,\"Zip\":null,\"Zip4\":null,\"CountryCode\":null,\"CountryName\":null,\"Phone\":\"\",\"PhoneExt\":null,\"Fax\":null,\"EMail\":null,\"Reference\":null},\"NaftaProducer\":{\"Type\":0,\"PhoneAreaCode\":null,\"Company\":null,\"ResidentialFlag\":false,\"Department\":null,\"Attn\":null,\"AddressLine1\":null,\"AddressLine2\":null,\"AddressLine3\":null,\"City\":null,\"StateCode\":null,\"Zip\":null,\"Zip4\":null,\"CountryCode\":null,\"CountryName\":null,\"Phone\":null,\"PhoneExt\":null,\"Fax\":null,\"EMail\":null,\"Reference\":null},\"Importer\":{\"Type\":0,\"PhoneAreaCode\":null,\"Company\":null,\"ResidentialFlag\":false,\"Department\":null,\"Attn\":null,\"AddressLine1\":null,\"AddressLine2\":null,\"AddressLine3\":null,\"City\":null,\"StateCode\":null,\"Zip\":null,\"Zip4\":null,\"CountryCode\":null,\"CountryName\":null,\"Phone\":\"\",\"PhoneExt\":null,\"Fax\":null,\"EMail\":null,\"Reference\":null},\"Broker\":{\"Type\":0,\"PhoneAreaCode\":null,\"Company\":null,\"ResidentialFlag\":false,\"Department\":null,\"Attn\":null,\"AddressLine1\":null,\"AddressLine2\":null,\"AddressLine3\":null,\"City\":null,\"StateCode\":null,\"Zip\":null,\"Zip4\":null,\"CountryCode\":null,\"CountryName\":null,\"Phone\":\"\",\"PhoneExt\":null,\"Fax\":null,\"EMail\":null,\"Reference\":null},\"UltConsignee\":{\"Type\":0,\"PhoneAreaCode\":null,\"Company\":null,\"ResidentialFlag\":false,\"Department\":null,\"Attn\":null,\"AddressLine1\":null,\"AddressLine2\":null,\"AddressLine3\":null,\"City\":null,\"StateCode\":null,\"Zip\":null,\"Zip4\":null,\"CountryCode\":null,\"CountryName\":null,\"Phone\":null,\"PhoneExt\":null,\"Fax\":null,\"EMail\":null,\"Reference\":null},\"Forwarder\":{\"Type\":0,\"PhoneAreaCode\":null,\"Company\":null,\"ResidentialFlag\":false,\"Department\":null,\"Attn\":null,\"AddressLine1\":null,\"AddressLine2\":null,\"AddressLine3\":null,\"City\":null,\"StateCode\":null,\"Zip\":null,\"Zip4\":null,\"CountryCode\":null,\"CountryName\":null,\"Phone\":null,\"PhoneExt\":null,\"Fax\":null,\"EMail\":null,\"Reference\":null},\"IntermediateConsignee\":{\"Type\":0,\"PhoneAreaCode\":null,\"Company\":null,\"ResidentialFlag\":false,\"Department\":null,\"Attn\":null,\"AddressLine1\":null,\"AddressLine2\":null,\"AddressLine3\":null,\"City\":null,\"StateCode\":null,\"Zip\":null,\"Zip4\":null,\"CountryCode\":null,\"CountryName\":null,\"Phone\":null,\"PhoneExt\":null,\"Fax\":null,\"EMail\":null,\"Reference\":null},\"DutyBillPayor\":{\"Type\":0,\"PhoneAreaCode\":null,\"Company\":null,\"ResidentialFlag\":false,\"Department\":null,\"Attn\":null,\"AddressLine1\":null,\"AddressLine2\":null,\"AddressLine3\":null,\"City\":null,\"StateCode\":null,\"Zip\":null,\"Zip4\":null,\"CountryCode\":null,\"CountryName\":null,\"Phone\":\"\",\"PhoneExt\":null,\"Fax\":null,\"EMail\":null,\"Reference\":null},\"BillPayor\":{\"Type\":0,\"PhoneAreaCode\":null,\"Company\":null,\"ResidentialFlag\":false,\"Department\":null,\"Attn\":null,\"AddressLine1\":null,\"AddressLine2\":null,\"AddressLine3\":null,\"City\":null,\"StateCode\":null,\"Zip\":null,\"Zip4\":null,\"CountryCode\":null,\"CountryName\":null,\"Phone\":\"\",\"PhoneExt\":null,\"Fax\":null,\"EMail\":null,\"Reference\":null},\"ExpectedDeliveryDate\":null,\"BillType\":102,\"BillAccountNumber\":\"124563\",\"BillAccountZipCode\":null,\"CreditCardNumber\":null,\"CreditCardType\":0,\"CreditCardExpireDate\":null,\"CreditCardCode\":null,\"DeliveryType\":0,\"DropOffType\":0,\"SortCode1\":\"CCR -A -14\",\"SortCode2\":\"94517\",\"SortCode3\":\"6150 CENTER ST\",\"SortCode4\":\"CLAYTON, CA\",\"SortCode5\":\"DDU\",\"SortCode6\":\"A\",\"CertifiedMailFlag\":false,\"ReturnsPackCollectFlag\":false,\"NDCPresortDiscountFlag\":false,\"ODNCPresortDiscountFlag\":false,\"CODReferenceIndicatorType\":0,\"CODRecipientAccountNumber\":null,\"CODAddlChargeType\":0,\"CODAmt\":0.0,\"CODPayType\":0,\"DimensionUnit\":\"IN\",\"WeightUnit\":\"LBS\",\"ShipDate\":\"04/17/2018 00:00:00\",\"TrailerNumber\":null,\"DropZip\":\"\",\"ACSFlag\":false,\"ReturnLabelRequiredFlag\":false,\"MultiPieceShipmentTrackingNumber\":null,\"MultiPieceShipmentTotalCount\":0,\"MultiPieceShipmentPieceCounter\":0,\"InboundLoadId\":null,\"InboundLoadNumber\":null,\"OutboundLoadId\":null,\"OutboundLoadNumber\":null,\"BookingNumber\":0,\"ShipperReleaseFlag\":false,\"ReturnType\":0,\"DeclarationStatementFlag\":false,\"RoutedTransactionIndFlag\":false,\"UseAddlCommentsFlag\":false,\"ProductCode\":null,\"CurrencyCode\":\"USD\",\"ImageRotation\":null,\"OverrideShipToAddressValidationFlag\":false,\"SupplyUsageFlag\":false,\"SignatureReleaseNumber\":null,\"PackageListEnclosedFlag\":false,\"RegulatoryControlType\":0,\"IsUPSHundredWeightFlag\":false,\"AddlShipmentReference1\":\"16455914\",\"AddlShipmentReference2\":\"23512128\",\"AddlShipmentReference3\":\"\",\"AddlShipmentReference4\":\"\",\"AddlShipmentReference5\":\"\",\"AddlShipmentReference6\":\"\",\"AddlShipmentReference7\":\"\",\"AddlShipmentReference8\":\"\",\"DeliveryConfirmationFlag\":true,\"SignatureConfirmationFlag\":false,\"SignatureConfirmationType\":0,\"USPSRateIndicator\":null,\"DryIceWeight\":0,\"SignatureReleaseFlag\":false,\"InsuranceAmount\":0.0,\"InsurancePayType\":0,\"DeclaredValue\":0.0,\"InsuranceType\":null,\"EndorsementType\":100,\"FragileFlag\":false,\"USPSSortLevel\":0,\"USPSODEnclosedMailClass\":0,\"ProcessCategory\":0,\"OriginallyCapturedPackageId\":null,\"Harmonizedcode\":null,\"SalesTaxAmount\":0.0,\"DutyAmount\":0.0,\"ContentDescription\":null,\"AdditionalHandlingFlag\":false,\"NotificationType\":0,\"NotificationValue\":null,\"CN22DescriptionType\":0,\"ShipperECCN\":null,\"ShipperXTN\":null,\"ShipperFTSR\":null,\"Incoterms\":\"-1\",\"ExportLicense\":null,\"ImportLicense\":null,\"ConsigneeCode\":null,\"ConsigneeEIN\":null,\"ConsigneeVAT\":null,\"SignatureName\":null,\"SignatureTitle\":null,\"ExportReasonCode\":0,\"ShipperEin\":null,\"SEDFilingOption\":0,\"SignaturePhone\":\"\",\"SignatureEmailAddress\":null,\"IsDepartmentOfStateShipment\":false,\"IsDepartmentOfStateExempt\":false,\"DOS22CFRExemptNumber\":null,\"CommercialInvoiceType\":null,\"CommercialInvoiceNumber\":null,\"CommercialInvoiceOtherCharges\":0.0,\"Use3rdPartyBrokerFlag\":false,\"BrokerShipAlertFlag\":false,\"BrokerDeliveryNotificationFlag\":false,\"BrokerExceptionNotificationFlag\":false,\"BrokerEmailFormat\":null,\"BrokerAccountNumber\":null,\"AdditionalComments\":null,\"DutiesTaxAccountNumber\":null,\"AdmissibilityPackagingType\":0,\"SenderTINType\":null,\"SenderTINNumber\":null,\"RecipientTINNumber\":null,\"AESTransactionNumber\":null,\"PartiesToTrans\":null,\"ExportInformationCode\":0,\"ExportLicenceExpDate\":null,\"BrokerageId\":null,\"WorldEaseFlag\":false,\"WorldEaseCounter\":0,\"GCCNNumber\":null,\"WorldEaseTrackingNumber\":null,\"WorldEasePortNumber\":null,\"WorldEasePortZip\":null,\"WorldEasePortName\":null,\"WorldEasePortCountry\":null,\"WorldEasePackageCount\":0,\"FTRExemtionCode\":null,\"ShippingFormContent1\":null,\"ShippingFormContent2\":null,\"ShippingFormContent3\":null,\"ShippingFormContent4\":null,\"ShippingFormContent5\":null,\"ShippingFormContent6\":null,\"FormRequested\":null,\"FormGenerationType\":0,\"NaftaBlanketPeriod_BeginDate\":null,\"NaftaBlanketPeriod_EndDate\":null,\"NaftaProducerTaxId\":null,\"NaftaProducerDeterminationType\":0,\"ExportDate\":null,\"ExportingCarrier\":null,\"InbondCode\":0,\"EntryNumber\":null,\"PointOfOrigin\":null,\"ModeOfTransport\":null,\"PortOfExport\":null,\"PortOfUnloading\":null,\"LoadingPier\":null,\"RoutedExportTransactionIndicator\":false,\"ContainerizedIndicator\":false,\"ExportType\":0,\"ForwarderTaxId\":null,\"ExceptionCode\":0,\"SoldToOption\":0,\"TradeDirectProductType\":0,\"ImporterAccountNumber\":null,\"CustomFormType\":0,\"ImportControlFlag\":false,\"CommercialInvoiceRemovalFlag\":false,\"CommercialInvoiceFreightChargeAmount\":0.0,\"IssuingAgencyCode\":null,\"LicencePlateNumber\":null,\"UltimateConsigneeAccountNumber\":null,\"UltimateConsigneeTaxId\":null,\"SccAccountNumber\":null,\"DutyAccountNumber\":null,\"DutyBillType\":0,\"DutyServiceType\":0,\"ImporterVATNumber\":null,\"RecipientCustomIdType\":0,\"CommercialImporterFlag\":null,\"TotalMonthlyImportedWeight\":null,\"TotalMonthlyImportedValue\":null,\"AuthorizedReturnsNumber\":null,\"Documents\":[],\"ErrorCode\":null,\"ErrorMessage\":null,\"DriverInstructions\":null,\"BaseRate\":0.0,\"TotalRate\":0.0,\"ManifestGroupName\":null,\"ShipmentID\":\"659514\"}");

        for(String res: results){
            BluePackageResponse resp = gson.fromJson(res,BluePackageResponse.class);

        }
        return true;
    }

    public static boolean loadOrderTest() {
        Gson gson = new Gson();
        try {
            System.out.println("loadOrderTest");
            ArrayList<AMPConnectShipShipment> shipments = new ArrayList<>();
            List<String> packages = new ArrayList();
            packages.add("p11387088*23937514*b1");
//            packages.add("p11188999*23512128*b1");
//            packages.add("p11188998*23510209*b1");
//            packages.add("p11188997*23502736*b1");
//            packages.add("p11188996*23522979*b1");
//            packages.add("p11188995*23508720*b1");
//            packages.add("p11188994*23522980*b1");
//            packages.add("p11188993*23522973*b1");
//            packages.add("p11188992*23522972*b1");
//            packages.add("p11188991*23522971*b1");
//            packages.add("p11188990*23511746*b1");
//            packages.add("p11188989*23522970*b1");
//            packages.add("p11188988*23522969*b1");
//            packages.add("p11188987*23522967*b1");
//            packages.add("p11188986*23522966*b1");
//            packages.add("p11188985*23522962*b1");
//            packages.add("p11188984*23522961*b1");
//            packages.add("p11188983*23522960*b1");
//            packages.add("p11188982*23522959*b1");
//            packages.add("p11188981*23522958*b1");
//            packages.add("p11188980*23522957*b1");
//            packages.add("p11188979*23522956*b1");
//            packages.add("p11188978*23522953*b1");
//            packages.add("p11188976*23522949*b1");
//            packages.add("p11188975*23522945*b1");
//            packages.add("p11189027*23511733*b1");
//            packages.add("p11189026*23509393*b1");
//            packages.add("p11189025*23509860*b1");
//            packages.add("p11189024*23510618*b1");
//            packages.add("p11187580*23508795*b29");
//            packages.add("p11189023*23506547*b1");
//            packages.add("p11189022*23509231*b1");
//            packages.add("p11189021*23508580*b1");
//            packages.add("p11189020*23510206*b1");
//            packages.add("p11189019*23511727*b1");
//            packages.add("p11189018*23503128*b1");
//            packages.add("p11189017*23508883*b1");
//            packages.add("p11189016*23508336*b1");
//            packages.add("p11189015*23511898*b1");
//            packages.add("p11189014*23507056*b1");
//            packages.add("p11189013*23502971*b1");
//            packages.add("p11189012*23511745*b1");
//            packages.add("p11189011*23362450*b1");
//            packages.add("p11189010*23503120*b1");
//            packages.add("p11189009*23509467*b1");
//            packages.add("p11189008*23507837*b1");
//            packages.add("p11187580*23508795*b28");
//            packages.add("p11189007*23462224*b1");
//            packages.add("p11189006*23512127*b1");
//            packages.add("p11189005*23511728*b1");
//            packages.add("p11189004*23508890*b1");
//            packages.add("p11189003*23502930*b1");
//            packages.add("p11189002*23507825*b1");
//            packages.add("p11189001*23503118*b1");
//            packages.add("p11187580*23508795*b27");
            for (String pack : packages) {
                try {
                    AMPConnectShipShipment ship = new AMPConnectShipShipment();
                    ship.loadOrderFromOrderReference(pack, true, "DC6");
                    ship.setValue(ShipConfig.WEIGHT, new Double("1"));
                    shipments.add(ship);
                    String [][] stringArray = ship.getOrderItemData();
                    int x = 0;
                    HashMap map = ship.getMap();
                    for(Object a: map.values()){
                        System.out.println("map vals: " + String.valueOf(a));
                    }
                    for(String[] strArr: stringArray){
                        int y = 0;
                        for(String str: strArr){
                            System.out.println(String.valueOf(x) + "," + String.valueOf(y) + " : " + str);
                            y++;
                        }
                        x++;
                    }
                    System.out.println("OWD_Order_id: " + ship.getValue(ShipConfig.OWD_ORDER_ID));
                    System.out.println("OWD_ORDER_NUM: " + ship.getValue(ShipConfig.OWD_ORDER_NUM));
                    System.out.println("ORDER_NUM_BARCODE: " + ship.getValue(ShipConfig.ORDER_NUM_BARCODE));
                    System.out.println("PKG_NUM: " + ship.getValue(ShipConfig.PKG_NUM));
                    System.out.println("PACKAGE_LIST_ID: " + ship.getValue(ShipConfig.PACKAGE_LIST_ID));
                    System.out.println("PKG_SHIP_INFO: " + ship.getValue(ShipConfig.PKG_SHIP_INFO));
                    System.out.println("BAR_CODE: " + ship.getValue(ShipConfig.BAR_CODE));
                    System.out.println("BAR_CODE_2: " + ship.getValue(ShipConfig.BAR_CODE_2));
                    System.out.println("HAEMONIZED_CODE: " + ship.getValue(ShipConfig.HARMONIZED_CODE));
                    System.out.println("CONSIGNEE_REGERENCE: " + ship.getValue(ShipConfig.CONSIGNEE_REFERENCE));
                    System.out.println("PACKAGE_BARCODE: " + ship.getValueAsString("PACKAGE_BARCODE"));
                    System.out.println("SHIP CODE: " + ship.getValueAsString(ShipConfig.SHIPPER_REFERENCE));
                    System.out.println("ORIGINAL SHIP CODE: " + ship.getOriginalAssignedServiceCode());

                    OwdOrder order = OrderFactory.getOwdOrderFromOrderID(
                            Integer.valueOf(ship.getValueAsString(ShipConfig.OWD_ORDER_ID)),
                            ship.getClientID(),
                            false
                    );
                    BluePackagePackage p = new BluePackagePackage(ship);
                    System.out.println("REFNUM:" + p.getReferenceNumber());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            submitOrders(shipments,"DC1");
            System.out.println(new java.sql.Timestamp(new Date().getTime()).toString());
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        return true;
    }

    public static List<SHIPMENT> submitOrders(List<AMPConnectShipShipment> shipments, String facility) {
        Gson gson = new Gson();
        List<SHIPMENT> shippedPackages = new ArrayList<SHIPMENT>();
        for (AMPConnectShipShipment ship : shipments) {
            BluePackageRequest req = new BluePackageRequest(ship);
            req.setConsignmentID("");
            req.setImageRotation("0");
            req.setLabelSize(0);
            req.setLabelDPI(0);
            req.setLabelType(3);
            req.setDimensionUnit("IN");
            req.setBillType(102);
            java.sql.Timestamp sqlDate = new java.sql.Timestamp(new Date().getTime());
            req.setShipDate(sqlDate.toString());
            req.setResidentialFlag("false");
            req.setCurrencyCode("USD");
            req.setWeightUnit("LBS");
            req.setSignatureConfirmationFlag(false);
            req.setSignatureConfirmationType(0);
            req.setRouteGroupIDOrServiceCodeList("S,1202");
            req.setDeclarationStatementFlag(false);
            req.setDeliveryConfirmationFlag(false);
            BluePackageMessage message = new BluePackageMessage();
            message.setConsignment(req);
            message.setMessageId("");
            System.out.println(gson.toJson(message));
            try {
                String respStr = doPostRequest(gson.toJson(message));
                BluePackageResponse resp = gson.fromJson(respStr,BluePackageResponse.class);
                if(resp.getStatus().getCode() == 0) {
                    postTrackingInfo(ship,resp,HibernateSession.currentSession(),1,facility);
                    SHIPMENT shipment = new SHIPMENT();
                    LABELDATA ld = new LABELDATA();
                    ld.setValue(resp.getPackageList().get(0).getLabels().get(0).getBase64());
                    ld.setCopies_Needed("1");
                    shipment.getLABELDATA().add(ld);
                    shippedPackages.add(shipment);
                }else{
                    return shippedPackages;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return shippedPackages;
            } catch (Exception e){
                e.printStackTrace();
                return shippedPackages;
            }
        }
        return shippedPackages;
    }

    public static BluePackageRequest createRequest(List<AMPConnectShipShipment> shipments) {
        Gson gson = new Gson();
        BluePackageRequest req = new BluePackageRequest(shipments.remove(0));
        req.setConsignmentID("");
        req.setLabelSize(0);
        req.setLabelType(3);
        req.setRouteGroupIDOrServiceCodeList("S,1202");
        for (AMPConnectShipShipment ship : shipments) {
            BluePackagePackage pack = new BluePackagePackage(ship);
            System.out.println(gson.toJson(pack));
            req.getPackageList().add(new BluePackagePackage(ship));
        }
        System.out.println("Sent to: " + URL);
        System.out.println(gson.toJson(req.getPackageList()));
        return req;
    }

    public static LABELDATA reprintLabelFromPackageBarcode(String barcode) throws Exception{
        LABELDATA ld = new LABELDATA();
        String sql = "select label_string from package where pack_barcode = :barcode";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("barcode",barcode);
        List l = q.list();
        if(l.size()>0){
            Clob clob = (Clob) l.get(0);
            ld.setCopies_Needed("1");
            ld.setValue(PackingABUtils.convertClobToString(clob));
        }else{
            throw new Exception("No info found for BluePackagePackage Barcode: "+barcode);
        }


        return  ld;
    }

    public static void packageCancel(String external_id,String barcode){
        try {
            doDeleteRequest(external_id);
            shipmentCancel(barcode);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void shipmentCancel(String barcode) throws Exception{
            boolean success = false;
            String sql = "SELECT\n" +
                    "    dbo.package.order_track_fkey,\n" +
                    "    dbo.package.external_id,\n" +
                    "    dbo.order_ship_info2.external_api_key\n" +
                    "FROM\n" +
                    "    dbo.package_order\n" +
                    "INNER JOIN\n" +
                    "    dbo.package\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.package_order.id = dbo.package.package_order_fkey)\n" +
                    "INNER JOIN\n" +
                    "    dbo.order_ship_info2\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.package_order.owd_order_fkey = dbo.order_ship_info2.order_fkey)\n" +
                    "WHERE\n" +
                    "    dbo.package_order.id = :id";
            String id = barcode.substring(barcode.indexOf("p") + 1, barcode.indexOf("*"));
            log.debug(id);
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("id", id);
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);


            List results = q.list();

            for (Object row : results) {
                Map data = (Map) row;
                String trackFkey = data.get("order_track_fkey").toString();
                String externalId = data.get("external_id").toString();
                String easypostApiKey = data.get("external_api_key").toString();
                log.debug(trackFkey);
                log.debug(externalId);
                com.owd.core.business.order.Package.voidPackageShipment(Integer.parseInt(trackFkey), "EasypostVoid");

            }

    }
    private static void postTrackingInfo(AMPConnectShipShipment ship, BluePackageResponse resp, Session sess, int index, String location) throws Exception{

        // String updatePackageSql = "update package set gss_shipment = 0, customs_docs = :customs, ship_time=getdate(),order_track_fkey=:trackid,owd_insurance_cost=:cost, insured_amount=:insured, external_id = :externalId where pack_barcode=:pack_barcode";
    //    String updatePackageSql = "execute sp_updatePackageShipAppExternal :customs, :trackid, :cost,:insured,:externalId,:pack_barcode";
        String updatePackageSql = "update package set ship_time=getdate(),order_track_fkey=:trackid,owd_insurance_cost=:cost, insured_amount=:insured, label_string = :label, external_id=:externalId where pack_barcode=:pack_barcode";

        String updatePackageOrdersql = "update package_order set packs_shipped=(packs_shipped+1), consolidator='bpd' where owd_order_fkey=:orderfkey and is_void=0";
        String updateOrdersql   ="exec update_order_shipment_info :orderfkey";
        String  createTrackingRecord = "insert into owd_order_track (order_fkey, line_index,tracking_no,external_id, weight,total_billed,cost_of_goods,ship_date,created_date,created_by,modified_date,modified_by,"
                + "msn,is_void,reported,email_sent,carrier_code,service_code) VALUES ( :order_fkey, :line_index, :tracking_no, :external_id,  :weight, :total_billed , 0 ,convert(datetime,convert(varchar,getdate(),101)),getdate(),:location,getdate(),:location,0,0, 0,0,:carrier_code,:service_code)";


        Query q = sess.createSQLQuery(createTrackingRecord);
        q.setParameter("order_fkey", ship.getValueAsString(ShipConfig.OWD_ORDER_ID));
        q.setParameter("line_index",index);
        q.setParameter("tracking_no",resp.getPackageList().get(0).getTrackingNumber());
        q.setParameter("external_id",resp.getShipmentID());
        q.setParameter("weight",ship.getValue(ShipConfig.WEIGHT));
        q.setParameter("total_billed","0.0");
        q.setParameter("location",location);
        q.setParameter("carrier_code",ship.getAssignedCarrierCode());
        q.setParameter("service_code",ship.getAssignedServiceCode());

        System.out.println("Creating Tracking");
        int results = q.executeUpdate();
        if(results<0) throw new Exception("Unable to create tracking for " + resp.getPackageList().get(0).getTrackingNumber());


        //tracking has been updated.
///////////
        String sql = "select order_track_id from owd_order_track where order_fkey = :fkey and tracking_no = :track";
        Query  qq = sess.createSQLQuery(sql);
        qq.setString("fkey", ship.getValueAsString(ShipConfig.OWD_ORDER_ID));
        System.out.println(ship.getValueAsString(ShipConfig.OWD_ORDER_ID));
        qq.setString("track",resp.getPackageList().get(0).getTrackingNumber());
        System.out.println(resp.getPackageList().get(0).getTrackingNumber());
        List l = qq.list();
        System.out.println(l);
        System.out.println("Got order track Id");
        String trackId = l.get(0).toString();
        System.out.println("Tracking Id: "+ trackId);

        //grabbed tracking id

        //update package info

        q = sess.createSQLQuery(updatePackageSql);
        q.setString("trackid",trackId);
        q.setString("pack_barcode", ship.getValueAsString("PACKAGE_BARCODE"));

        //todo figure out insurance
        q.setString("cost","0");
        q.setString("insured", "0");
        q.setString("label",resp.getPackageList().get(0).getLabels().get(0).getBase64());

        q.setParameter("externalId",resp.getConsignmentID());


        System.out.println("Right before updateing package");
        results = q.executeUpdate();
        System.out.println("Done updateing pacakge with trackid");
        if (results < 1) throw new Exception("update for BluePackagePackage returned 0");


        q = sess.createSQLQuery(updatePackageOrdersql);
        q.setString("orderfkey",ship.getValueAsString(ShipConfig.OWD_ORDER_ID));
        results = q.executeUpdate();
        System.out.println("Done updating package order ");
        if (results < 1) throw new Exception("update for BluePackagePackage ORder returned 0");
        if(!ABUtils.isWeightVerifiedPacked(ship.getValueAsString(ShipConfig.OWD_ORDER_ID))) {
            q = sess.createSQLQuery(updateOrdersql);
            q.setString("orderfkey",ship.getValueAsString(ShipConfig.OWD_ORDER_ID));
            results = q.executeUpdate();
            System.out.println("Done updateting order sql");
            if (results < 1) throw new Exception("update Order returned 0");
        }



    }
}
