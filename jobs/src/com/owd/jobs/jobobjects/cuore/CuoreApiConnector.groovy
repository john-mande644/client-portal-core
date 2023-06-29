package com.owd.jobs.jobobjects.cuore

import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient
import org.codehaus.groovy.runtime.DateGroovyMethods

/**
 * Created by stewartbuskirk1 on 11/2/15.
 */
class CuoreApiConnector {

    def static endpoint = new RESTClient("http://c3s.cuoregroup.com")

    String token = "";

    public CuoreApiConnector(String atoken) {
        token = atoken;
    }

    public String retrieveTestOrderDataFromCuore(int daysBack) {

        return retrieveOrderDataFromCuore(daysBack, true);

    }

    public String retrieveLiveOrderDataFromCuore(int daysBack) {
        //daysBack is zero for current date

        return retrieveOrderDataFromCuore(daysBack, false);

    }

    private String retrieveOrderDataFromCuore(int daysBack, boolean useTestData) {
        //daysBack is zero for current date

        //  http://c3s.cuoregroup.com/tools/perhapstonightexport.php?token=b5cc247bcb6c88a32b3fb1cfb0a841a7&thedate=20151027&test=true
        println("testing? " + useTestData)
        println(DateGroovyMethods.format((new Date()) - daysBack, 'yyyyMMdd'))
        def resp = endpoint.get(path: '/tools/perhapstonightexport.php',
                query: [token  : token,
                        test   : useTestData ? 'true' : 'false',
                        thedate: DateGroovyMethods.format((new Date()) - daysBack, 'yyyyMMdd')],
                contentType: ContentType.TEXT
        )

        println("success")

        String data = resp.data?.text
        println(data)

        return data
    }
}
