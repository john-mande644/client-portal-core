package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels.spscommerce;

import okhttp3.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SPSCommerceLabelApiClient {

    String appId = null;
    String appSecret = null;

    final static int getAccessTokenRetries = 5;
    final static long getAccessTokenRetryDelayMilliseconds = 2000;
    final static int getShippingLabelRetries = 5;
    final static long getShippingLabelRetryDelayMilliseconds = 2000;
    final static String urlGetAccessToken = "https://auth.spscommerce.com/oauth/token";
    final static String urlBase = "https://api.spscommerce.com";
    final static String urlGetZplLabel = "/label/v1/%s/zpl";
    final static int httpTimeoutSeconds = 60;

    public final static String LabelId_Zappos = "d09c23bb";
    public final static String LabelId_WalmartSSCC14 = "6d922b25";
    public final static String LabelId_Moosejaw = "4c8995b0";
    public final static String LabelId_TheGolfWarehouse = "1a85fa51";
    public final static String LabelId_ScheelsAllSports = "961d00f6";
    public final static String LabelId_RetailConcepts = "a566aed7";

    public SPSCommerceLabelApiClient(String appId, String appSecret) {
        this.appId = appId;
        this.appSecret = appSecret;
    }

    // postJson
    private String postJson(String url, String jsonRequest, Boolean requiresAuthorization) throws Exception {

        String token = null;
        if(requiresAuthorization)
        {
            token = getAccessToken().accessToken;
        }

        OkHttpClient client = new OkHttpClient().newBuilder()
                .readTimeout(httpTimeoutSeconds, TimeUnit.SECONDS)
                .connectTimeout(httpTimeoutSeconds, TimeUnit.SECONDS)
                .writeTimeout(httpTimeoutSeconds, TimeUnit.SECONDS)
                .build();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, jsonRequest);
        Request.Builder builder = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json");
        if(requiresAuthorization)
        {
            builder.addHeader("Authorization", "Bearer " + token);
        }

        Request request = builder.build();
        Response response = client.newCall(request).execute();

        String jsonResponse = response.body().string();

        if(response.code() < 200 || response.code() >= 300)
        {
            throw new Exception(response.toString());
        }

        return jsonResponse;
    }

    private String postJson(String url, String jsonRequest) throws Exception {
        return postJson(url, jsonRequest, true);
    }

    private SPSCommerceAccessToken getAccessTokenFromAppData() throws Exception {
        Session hibernateSession = HibernateSession.currentSession();
        // NOTE: app_data.display is the only column wide enough to store the access token, so that's where it is.

        Query q = hibernateSession.createSQLQuery("select display from dbo.app_data where project = 'sps_label_api' and description = 'token' and variable = 'access_token'");
        List l = q.list();
        if (l.size() > 0) {
            String jsonAccessToken = l.get(0).toString();
            return SPSCommerceAccessToken.fromJson(jsonAccessToken);
        }

        return null;
    }

    private long getAccessTokenExpiresFromAppData() throws Exception {
        Session hibernateSession = HibernateSession.currentSession();

        Query q = hibernateSession.createSQLQuery("select value from dbo.app_data where project = 'sps_label_api' and description = 'token' and variable = 'access_token_expires'");
        List l = q.list();
        if (l.size() > 0) {
            String expires = l.get(0).toString();
            return Long.parseLong(expires);
        }

        return 0;
    }

    private void insertAppData(String project, String description, String variable, String value, String display) throws Exception {
        Session hibernateSession = HibernateSession.currentSession();
        String insert = "insert into app_data(project,description,variable,value,display,facility_code) values(:project,:description,:variable,:value,:display,null)";
        Query qInsert = hibernateSession.createSQLQuery(insert);
        qInsert.setParameter("project", project);
        qInsert.setParameter("description", description);
        qInsert.setParameter("variable", variable);
        qInsert.setParameter("value", value);
        qInsert.setParameter("display", display);
        qInsert.executeUpdate();
    }

    private void saveAccessTokenToAppData(SPSCommerceAccessToken accessToken, long expires) throws Exception {
        Session hibernateSession = HibernateSession.currentSession(); // this should create a new session if none open already.

        Transaction transaction = hibernateSession.getTransaction(); // currentSession starts a transaction, so latch onto it
        try {

            // NOTE: app_data.display is the only column wide enough to store the access token, so that's where it goes.

            SPSCommerceAccessToken currentAccessToken = getAccessTokenFromAppData();

            if (currentAccessToken != null) {

                String updateAccessToken = "update app_data set display = :accessTokenJson where project = 'sps_label_api' and description = 'token' and variable = 'access_token'";
                Query qUpdateAccessToken = hibernateSession.createSQLQuery(updateAccessToken);
                qUpdateAccessToken.setParameter("accessTokenJson", accessToken.toJson());
                qUpdateAccessToken.executeUpdate();

                String updateExpires = "update app_data set value = :expires where project = 'sps_label_api' and description = 'token' and variable = 'access_token_expires'";
                Query qUpdateExpires = hibernateSession.createSQLQuery(updateExpires);
                qUpdateExpires.setParameter("expires", String.valueOf(expires));
                qUpdateExpires.executeUpdate();
            } else {

                insertAppData("sps_label_api", "token", "access_token", "bearer", accessToken.toJson());
                insertAppData("sps_label_api", "token", "access_token_expires", String.valueOf(expires), "");
            }

            transaction.commit();
        }
        catch (Exception x)
        {
            transaction.rollback();
            throw x;
        }
        finally {
            HibernateSession.closeSession();
        }
    }

    // getAccessToken
    public SPSCommerceAccessToken getAccessToken() throws Exception {

        List<Exception> exceptions = new ArrayList();

        SPSCommerceAccessToken accessToken = null;
        long expires = 0;

        // get stored token (if there is one) and when it expires
        Session hibernateSession = HibernateSession.currentSession(); // this should create a new session if none open already.
        try {

            accessToken = getAccessTokenFromAppData();
            expires = getAccessTokenExpiresFromAppData();

            if (accessToken != null && System.currentTimeMillis() < expires)
                return accessToken;
        } finally {
            HibernateSession.closeSession();
        }

        // no token stored or it's expired, so get a new one

        for (int r = 0; r < getAccessTokenRetries; ++r) {
            try {
                SPSCommerceGetAccessTokenRequest request = new SPSCommerceGetAccessTokenRequest();
                request.clientId = appId;
                request.clientSecret = appSecret;

                String jsonResponse = postJson(urlGetAccessToken, request.toJson(), false);

                accessToken = SPSCommerceAccessToken.fromJson(jsonResponse);

                expires = System.currentTimeMillis() + (accessToken.expiresIn * 1000);

                if (accessToken != null) {
                    saveAccessTokenToAppData(accessToken, expires);
                    return accessToken;
                }

            } catch (Exception x) {
                exceptions.add(x);
                Thread.sleep(getAccessTokenRetryDelayMilliseconds);
                // TODO: log?
            }
        }


        String lastError = "";
        if (exceptions.size() > 0)
            lastError = exceptions.get(exceptions.size() - 1).getMessage();

        throw new Exception("Could not get access token for SPS Commerce Label API after " + getAccessTokenRetries + " tries. Last Error: " + lastError);
    }

    // GetZplLabel
    public SPSCommerceZPLData renderZPL(String labelId, SPSCommerceHeader header, List<SPSCommercePack> packs) throws Exception
    {
        String url = String.format(urlBase + urlGetZplLabel + "?mediaType=D", labelId);

        SPSCommerceGetShippingLabelRequest request = new SPSCommerceGetShippingLabelRequest();
        request.header = header;
        request.packs = packs;

        List<Exception> exceptions = new ArrayList();

        for(int r=0; r < getShippingLabelRetries; ++r)
        {
            try {
                String jsonRequest = request.toJson();
                String jsonResponse = postJson(url, jsonRequest);
                SPSCommerceZPLData zplData = SPSCommerceZPLData.fromJson(jsonResponse);
                return zplData;
            }
            catch(Exception x)
            {
                exceptions.add(x);
                Thread.sleep(getShippingLabelRetryDelayMilliseconds);
                // TODO: log?
            }
        }

        String lastError = "";
        if(exceptions.size() > 0)
            lastError = exceptions.get(exceptions.size() - 1).getMessage();

        throw new Exception("Could not get ZPL label from SPS Commerce after " + getShippingLabelRetries + " tries. LastError: " + lastError);

    }
}
