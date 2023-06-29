package com.owd.jobs;

import com.owd.core.Mailer;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateClientReportsSession;

import java.sql.PreparedStatement;
import java.util.Vector;

/**
 * Created by danny on 3/12/2019.
 */
public class Five9CopyToReportingServerJob extends OWDStatefulJob{



    public void internalExecute(){

        String lastStatement = "";
        try {

          PreparedStatement ps2 = HibernateClientReportsSession.getPreparedStatement("exec f9_update_owdreports_cc_contact;");
        ps2.executeUpdate();
        ps2.close();
        lastStatement="exec f9_update_owdreports_cc_contact;commit";

        HibUtils.commit(HibernateClientReportsSession.currentSession());

        //smarterttrack emails
        lastStatement="exec update_smartertrack_cc_contact;";

        PreparedStatement ps3 = HibernateClientReportsSession.getPreparedStatement("exec update_smartertrack_cc_contact;");
        ps3.executeUpdate();
        ps3.close();
        lastStatement="exec update_smartertrack_cc_contact;commit";
            HibUtils.commit(HibernateClientReportsSession.currentSession());

        }catch (Exception  ex){

            ex.printStackTrace();
            Vector emailsx = new Vector();
            emailsx.add("owditadmin@owd.com");
            try{
                Mailer.postMailMessage("OWD-Five9-CallLog Import Status Notification", lastStatement + "\r\n" + ex.getMessage(), emailsx, "do-not-reply@owd.com");
            } catch (Exception exx) {
                exx.printStackTrace();

            }
        }

    }
}
