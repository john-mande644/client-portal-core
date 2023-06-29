package com.owd.jobs.jobobjects.intacct

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.intacct.ShippingAccountUtilities
import com.owd.hibernate.HibUtils
import com.owd.hibernate.HibernateClientReportsSession

import java.sql.PreparedStatement

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 8/23/12
 * Time: 9:01 AM
 * To change this template use File | Settings | File Templates.
 */
class IntacctAPI {

    public static void main (String[] args)
    {
        //   updateARAccountLabels()
       // updateActivityInvoices("AV")

        Map clients = ShippingAccountUtilities.getARCustomerData()
        for(String acct:clients.keySet())
        {
            if(!(acct.endsWith("_1")))
            updateActivityInvoices(acct)
        }
    }

    public static void updateActivityInvoices(String clientAccountID)
    {


        List<Map> invoices =  ShippingAccountUtilities.getDetailedActivityARLedgerData(clientAccountID, false, 2000);
        for(Map invoice:invoices)
        {

            PreparedStatement ps = HibernateClientReportsSession.getPreparedStatement("""insert into intacct_invoice (transaction_date,intacct_id,total_amount,intacct_ref,intact_code)
                                                      select ?,?,?,?,? where not exists (select * from intacct_invoice where intacct_id=?)""")  ;

            ps.setString(1,(String)invoice.get("transaction_date")) ;
            ps.setInt(2,Integer.parseInt((String)invoice.get("intacct_id")));
            println "converting "+((String)invoice.get("total_amount"))+" to "+new Double((String)invoice.get("total_amount"))
            ps.setDouble(3,new Double((String)invoice.get("total_amount")))  ;
            ps.setString(4,(String)invoice.get("intacct_ref"))   ;
            ps.setString(5,clientAccountID);
            ps.setInt(6,Integer.parseInt((String)invoice.get("intacct_id")));

            ps.executeUpdate()
            ps.close()

            List<Map> items = (List<Map>) invoice.get("lines")
            Map<String,Map> newitems = new TreeMap<String,Map>()


            for(Map item:items)
            {
                String key= (String)item.get("araccountlabel") +((String)item.get("location"))+((String)item.get("department"))+((String)item.get("glaccountno"));
                if(newitems.containsKey(key))
                {
                    newitems.get(key).put("amount",new Double((String)newitems.get(key).get("amount"))+(new Double((String)item.get("amount"))))

                }   else
                {
                    Map<String,String> itemMap = new TreeMap<String,String>();
                    itemMap.put("araccountlabel",(String)item.get("araccountlabel"))
                    itemMap.put("glaccountno",(String)item.get("glaccountno"))
                    itemMap.put("location",(String)item.get("location"))
                    itemMap.put("department",(String)item.get("department"))
                    itemMap.put("amount",(String)item.get("amount"))

                    newitems.put(key,itemMap)
                }

            }

            for(Map item:newitems.values())
            {

               // println item
                PreparedStatement psitem = HibernateClientReportsSession.getPreparedStatement("""insert into intacct_invoice_items (invoice_fkey,amount,araccountlabel,glaccountno,location,department)
                                                      select ?,?,?,?,?,? where not exists (select * from intacct_invoice_items where invoice_fkey=? and araccountlabel=? and department=? and location=?)""")  ;

                psitem.setInt(1,Integer.parseInt((String)invoice.get("intacct_id")));

                psitem.setDouble(2,new Double((String)item.get("amount")))  ;
                psitem.setString(3,(String)item.get("araccountlabel")) ;

                psitem.setString(4,(String)item.get("glaccountno"))   ;
                psitem.setString(5,(String)item.get("location"))   ;
                psitem.setString(6,(String)item.get("department"))   ;

                psitem.setInt(7,Integer.parseInt((String)invoice.get("intacct_id")));
                psitem.setString(8,(String)item.get("araccountlabel")) ;
                psitem.setString(9,(String)item.get("department"))   ;
                psitem.setString(10,(String)item.get("location"))   ;
                psitem.executeUpdate()
                psitem.close()

            }

            HibUtils.commit(HibernateClientReportsSession.currentSession())

        }
    }
    public static void updateARAccountLabels()
    {
        def accountList = new XmlSlurper().parseText(ShippingAccountUtilities.getARAccountLabelXml())

        println(accountList)
        accountList.operation.result.data.araccountlabel.each { it ->
        println(it)
        PreparedStatement ps = HibernateClientReportsSession.getPreparedStatement("""insert into intacct_ar_accounts (glaccountno,araccountlabel,description)
                                                      select ?,?,? where not exists (select * from intacct_ar_accounts where araccountlabel=?)""")
         ps.setString(1,it.glaccountno.text())
            ps.setString(2,it.accountlabel.text())
            ps.setString(3,it.accountlabel.text().substring(it.accountlabel.text().indexOf(" ")+1))
            ps.setString(4,it.accountlabel.text())

            ps.executeUpdate()
            /*
             <araccountlabel>
                              <glaccountno>4001</glaccountno>
                              <accountlabel>Xfer to AR</accountlabel>
                              <description>This is for credit memos to reduce AR accounts</description>
                              <offsetglaccountno/>
                              <status>active</status>
                              <taxcode/>
                        </araccountlabel>
             */

        }
        HibUtils.commit(HibernateClientReportsSession.currentSession())

        // println(ShippingAccountUtilities.getARAccountLabelXml())
    }
}
