package com.owd.apiUtils

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

/**
 * Created by danny on 10/9/2017.
 */
class OwdInternalApi {

    private static String apiUrl = "http://api.owd.com"
    private static String authHeader = "x-owd-api-token";
    private static String authHeaderValue = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoidXNlciIsImlhdCI6MTUwNTI0NzgxM30.ARsjNcfFxrtWWv08XwRZPe0JxJuW1pq5Z9X-9h2T94A";
    public static void main(String[] args){

        println(clearToteById("2080"));

    }



    public static boolean clearToteById(String toteId){

        boolean success = false;
            def api;
        api = new HTTPBuilder(apiUrl);

        api.request(Method.PUT, ContentType.JSON){req ->
            uri.path = "/order/"+toteId +"/clearTote";
            headers.'x-owd-api-token' = authHeaderValue;
            println("Set header")
            println(uri.path);

            response.success = { resp, json ->
                assert resp.status == 200
               println(json);
                success = true;
            }
            // not logged in response
            response.'302' = { resp ->
                throw new Exception("Stopping at item POST: uri: " + uri + "\n" +
                        "   You are not logging in properly. Item will not be created.")
            }
            response.failure = { resp, json ->
                throw new Exception("Stopping at item POST: uri: " + uri + "\n" +
                        "   Unknown error clearing location" +
                        "\njson = ${json}")
            }
        }


        return success;
    }
}
