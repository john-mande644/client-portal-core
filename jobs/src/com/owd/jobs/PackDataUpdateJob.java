package com.owd.jobs;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateClientReportsSession;
import com.owd.LogableException;

import java.sql.PreparedStatement;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Stewart Buskirk
 * Date: Apr 8, 2010
 * Time: 2:26:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class PackDataUpdateJob  extends OWDStatefulJob
{
private final static Logger log =  LogManager.getLogger();

    public static void main(String[] args)
    {
        run();

    }
    @Override
    public void internalExecute()
    {
        try
        {
            log.debug(OWDUtilities.getSQLDateTimeForAdjustedDate(-1));
        //To change body of implemented methods use File | Settings | File Templates.
        String updateSQL1 = "sp_updatepackdata '"+ OWDUtilities.getSQLDateTimeForAdjustedDate(-1)+"';";
        PreparedStatement stmt = HibernateClientReportsSession.getPreparedStatement(updateSQL1);
        stmt.executeUpdate();
         stmt.close();
        HibUtils.commit(HibernateClientReportsSession.currentSession());
        }catch(Exception ex)
        {    try
        {
            HibUtils.rollback(HibernateClientReportsSession.currentSession());      }catch(Exception exx)
        {    exx.printStackTrace();                  }
            ex.printStackTrace();
            throw new LogableException(ex, ex.getMessage(),
                    "TS:"+ Calendar.getInstance().getTimeInMillis(),
                    "55",
                    this.getClass().getName(),
                    LogableException.errorTypes.INTERNAL);        }finally
        {
            HibernateClientReportsSession.closeSession();
        }
    }
}
