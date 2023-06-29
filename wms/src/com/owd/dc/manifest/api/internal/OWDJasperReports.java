package com.owd.dc.manifest.api.internal;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.JasperCompileManager;

import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Nov 3, 2008
 * Time: 11:55:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class OWDJasperReports {
    private JasperReport upsCustoms;
    public JasperReport getUpsCustoms() throws Exception{
        if (upsCustoms == null){
            System.out.println("compiling jasper report");
            compileUpsCustoms();
        }
        return upsCustoms;
    }
   private static OWDJasperReports me;

    public synchronized static OWDJasperReports getInstance(){
        if (me == null){
            me = new OWDJasperReports();
              }
        return me;
    }
   public OWDJasperReports() {
      //System.out.println("settign client list");


   }
    private void compileUpsCustoms() throws Exception{
        System.out.println(JRPropertiesMap.class.getPackage().getImplementationVersion());
       InputStream is = getClass().getClassLoader().getResourceAsStream("comminvoice.jrxml");

       upsCustoms  = JasperCompileManager.compileReport(is);
        

    }
}
