package com.owd.jobs.archives

import com.owd.jobs.OWDStatefulJob
import com.owd.jobs.jobobjects.cuore.CuoreOrderProcessor
import com.owd.jobs.jobobjects.shopify.ShopifyAPI
import groovy.util.logging.Log4j
import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient
import org.codehaus.groovy.runtime.DateGroovyMethods

import java.io.InputStreamReader
import java.util.logging.Level

/**
 * Created by stewartbuskirk1 on 11/1/15.
 */

@Log4j
class PerhapsTonightCuoreOrderImportJob extends OWDStatefulJob {

    public static void main(String[] args) {
         run();
    }

    @Override
    public void internalExecute() {

        CuoreOrderProcessor cop= new CuoreOrderProcessor(571,'b5cc247bcb6c88a32b3fb1cfb0a841a7')
        cop.importOrders();
    }



}
