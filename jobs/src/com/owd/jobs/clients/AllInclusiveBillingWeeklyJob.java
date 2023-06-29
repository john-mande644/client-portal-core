package com.owd.jobs.clients;

import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.excel.ExcelUtils;
import com.owd.jobs.jobobjects.excelreports.AllInclusiveBillingWeeklyReportDefinition;
import com.owd.jobs.jobobjects.excelreports.DailyLotErrorReportDefinition;
import com.owd.jobs.jobobjects.excelreports.ExcelReportEmailSender;
import org.hibernate.Query;

import java.util.Arrays;
import java.util.List;

/**
 * Created by danny on 2/2/2018.
 */
public class AllInclusiveBillingWeeklyJob extends OWDStatefulJob {



    public static void main(String[] args){

        run();


    }

    public void internalExecute(){



        String sql = "select client_id, company_name from owd_client where AllInclusive = 1;";


        try {

        Query q = HibernateSession.currentSession().createSQLQuery(sql);
            List l = q.list();
            for(Object row:l){

                Object[] data = (Object[]) row;
                ExcelUtils.deliverReports(Arrays.asList(new  AllInclusiveBillingWeeklyReportDefinition(Integer.parseInt(data[0].toString()),data[1].toString())), Arrays.asList(new ExcelReportEmailSender(Arrays.asList("accounting@owd.com"))));



            }


        }catch (Exception e){
            e.printStackTrace();
        }




    }
}
