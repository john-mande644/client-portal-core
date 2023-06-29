package com.owd.jobs;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateAdHocSession;
import com.owd.hibernate.HibernateClientReportsSession;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: eric
 * Date: Aug 27, 2008
 * Time: 4:01:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class HourlySyncDBsJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) throws Exception {

        //run();
        syncUserListToAdHocDb();
    }

    public void internalExecute() {

        try {
            //step one get a result set of clients from SQLserver
            syncClientListToReportingDb();
            syncUserListToAdHocDb();
            updateContactAgentIds();
            updateOpsSummaryByLoc();
            updateClientCasetrackerMailboxes();
            fixBollBranchGiftMessages();
            /*

exec update_ops_hourly_summaries_byloc;
update cc_cl_contacts set agent_id=agent from  cc_cl_contacts cc join OWD.dbo.ccagents cca on cc.contact_id=cca.contact_id and  cc.agent_id='' ;
             */
            HibUtils.rollback(HibernateSession.currentSession());

        } catch (Exception ex) {
            try {
                HibUtils.rollback(HibernateAdHocSession.currentSession());
            } catch (Exception exx) {
                exx.printStackTrace();
            }
            ex.printStackTrace();
        } finally {

            HibernateAdHocSession.closeSession();
            HibernateSession.closeSession();
            HibernateClientReportsSession.closeSession();
        }
    }


    private void fixBollBranchGiftMessages()
    {


        try{

            Session adhocSession = HibernateSession.currentSession();

        //    adhocSession.beginTransaction();
            Statement stmt = ((SessionImplementor)adhocSession).getJdbcConnectionAccess().obtainConnection().createStatement();
            stmt.execute("update owd_order set gift_message= REPLACE(REPLACE(REPLACE(gift_message, CHAR(13), ' '), CHAR(10), ' '),'  ',' ') \n" +
                    "        from owd_order o\n" +
                    "        where group_name='bollandbranch' and order_status not in ('Shipped','Void')\n" +
                    "        and len(gift_message)>0");
            stmt.close();


            HibUtils.commit(adhocSession);

        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        //
    }


    private void updateClientCasetrackerMailboxes() throws Exception {


          try{

            Session adhocSession = HibernateSession.currentSession();

          //  adhocSession.beginTransaction();
            Statement stmt = ((SessionImplementor)adhocSession).getJdbcConnectionAccess().obtainConnection().createStatement();
            stmt.execute("truncate table client_mailboxes;");
            stmt.execute("insert into client_mailboxes select * from vw_client_mailboxes;");
            stmt.close();


            HibUtils.commit(adhocSession);

        }catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }
         private void updateOpsSummaryByLoc() throws Exception {


          try{

            Session adhocSession = HibernateSession.currentSession();

           // adhocSession.beginTransaction();
            Statement stmt = ((SessionImplementor)adhocSession).getJdbcConnectionAccess().obtainConnection().createStatement();
            stmt.execute("exec update_ops_hourly_summaries_byloc;");
            stmt.close();

            HibUtils.commit(adhocSession);

        }catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

          private void updateContactAgentIds() throws Exception {


          try{

            Session adhocSession = HibernateClientReportsSession.currentSession();

           // adhocSession.beginTransaction();
            Statement stmt = ((SessionImplementor)adhocSession).getJdbcConnectionAccess().obtainConnection().createStatement();
            stmt.executeUpdate("update cc_cl_contacts set agent_id=agent from  cc_cl_contacts cc join OWD.dbo.ccagents cca on cc.contact_id=cca.contact_id and  cc.agent_id='' ;");
            stmt.close();

            HibUtils.commit(adhocSession);

              adhocSession = HibernateSession.currentSession();

          //  adhocSession.beginTransaction();
             stmt = ((SessionImplementor)adhocSession).getJdbcConnectionAccess().obtainConnection().createStatement();
            stmt.executeUpdate("update cc_cl_contacts set agent_id=agent from  cc_cl_contacts cc join ccagents cca on cc.contact_id=cca.contact_id and  cc.agent_id='' ;");
            stmt.close();

            HibUtils.commit(adhocSession);
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

      private static void syncUserListToAdHocDb() throws Exception {


          try{

      Session adhocSession = HibernateAdHocSession.currentSession();
            Session owdSession = HibernateSession.currentSession();

         //   adhocSession.beginTransaction();
            Statement stmt = ((SessionImplementor)adhocSession).getJdbcConnectionAccess().obtainConnection().createStatement();
            stmt.execute("exec sp_setup_user_tables;");
            stmt.close();

            List users = owdSession.createSQLQuery("select * from vw_adhoc_users").list();

            log.debug("got "+users.size()+" users...");
              PreparedStatement ps = ((SessionImplementor)adhocSession).getJdbcConnectionAccess().obtainConnection().prepareStatement("INSERT\n" +
                    "INTO\n" +
                    "    dbo.owd_user_data\n" +
                    "    (\n" +
                    "        UserName,\n" +
                    "        UserID,\n" +
                    "        Password,\n" +
                    "        FirstName,\n" +
                    "        Email,\n" +
                    "        LastDatabaseID,\n" +
                    "        LastName,\n" +
                    "        GroupID,\n" +
                    "        Locked\n" +
                    "    )\n" +
                    "    VALUES\n" +
                    "    (\n" +
                    "        ?,\n" +
                    "        ?,\n" +
                    "        ?,\n" +
                    "        ?,\n" +
                    "        ?,\n" +
                    "        ?,\n" +
                    "        ?,\n" +
                    "        ?,\n" +
                    "        ?\n" +
                    "    )");

            for(Object[] data:(List<Object[]>)users)
            {
            ps.setString(1,(String)data[0]);
            ps.setInt(2, (Integer) data[1]);
            ps.setString(3,(String)data[2]);
            ps.setString(4,(String)data[3]);
            ps.setString(5,(String)data[4]);
            ps.setInt(6, (Integer) data[5]);
            ps.setString(7,(String)data[6]);
            ps.setInt(8, (Integer) data[7]);
            ps.setInt(9, (Integer) data[8]);

                ps.execute();

            }

            ps.close();

            List usergroups = owdSession.createSQLQuery("select uid,gid from vw_adhoc_user_groups").list();

            log.debug("got "+usergroups.size()+" users...");

            ps = ((SessionImplementor)adhocSession).getJdbcConnectionAccess().obtainConnection().prepareStatement("INSERT\n" +
                      "INTO\n" +
                      "    dbo.owd_user_group_data\n" +
                      "    (\n" +
                      "        UserID,\n" +
                      "        RoleID\n" +
                      "    )\n" +
                      "    VALUES\n" +
                      "    (\n" +
                      "        ?,\n" +
                      "        ?\n" +
                      "    )");

            for(Object[] data:(List<Object[]>)usergroups)
            {
            ps.setInt(1,(Integer)data[0]);
            ps.setInt(2, (Integer) data[1]);

                ps.execute();

            }

            ps.close();



            stmt = ((SessionImplementor)adhocSession).getJdbcConnectionAccess().obtainConnection().createStatement();
            stmt.execute("exec sp_owd_usersync;");
            stmt.close();


            HibUtils.commit(adhocSession);

        }catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

    private void syncClientListToReportingDb() throws Exception {



        PreparedStatement insertStmt = ((SessionImplementor)HibernateClientReportsSession.currentSession()).getJdbcConnectionAccess().obtainConnection()
                .prepareStatement("drop table owd_client; select * into owd_client from OWD.dbo.owd_client; ");
         insertStmt.execute();


        HibUtils.commit(HibernateClientReportsSession.currentSession());
    }

}
