package jobobjects.jobobjects.commercehub.invoicing;

import com.owd.jobs.jobobjects.commercehub.invoicing.CommerceHubInvoiceBase;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by danny on 7/13/2019.
 */
public class CommereHubInvoiceBaseTesting {

    @Test
    public void loadInvoiceFromOrderIdTest(){
        System.setProperty("com.owd.environment","test");

        CommerceHubInvoiceBase invoice = new CommerceHubInvoiceBase("JustBrand Limited","vendor","justbrand");
       try {
           invoice.setTaxPercent(new BigDecimal("4.5"));
           invoice.setDiscDaysDue(5);
           invoice.setNetDaysDue(30);
           invoice.setDiscPercent(new BigDecimal("5"));
           invoice.loadInvoiceFromOrderId(18022299);


           String s = invoice.getInvoiceXMLData();
       }catch (Exception e){
           e.printStackTrace();
           fail();

       }


    }

}


