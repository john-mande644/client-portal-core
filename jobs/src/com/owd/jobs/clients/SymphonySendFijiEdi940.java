package com.owd.jobs.clients;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.LogableException;
import com.owd.jobs.jobobjects.edi.OwdEdiComRemoteFTP;
import com.owd.mapforce.dswater.MappingMapTo940;
import org.hibernate.engine.spi.SessionImplementor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Created by stewartbuskirk1 on 4/6/15.
 */
public class SymphonySendFijiEdi940   extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();



    public void internalExecute() {

  /*      try{


            ResultSet rs = HibernateSession.getResultSet("select * from edi_queue " +
                    "where status=0 and type='DSWater' and doctype='940' order by id asc");

            List<Map<String,Object>> ediList = new ArrayList<Map<String,Object>>();

            while(rs.next()) {
               Map<String,Object>  ediMap = new HashMap<String,Object>();
                ediMap.put("id",rs.getInt("id"));
                ediMap.put("order_fkey",rs.getInt("order_fkey"));
                ediMap.put("type",rs.getString("type"));
                ediMap.put("doctype",rs.getString("doctype"));
                ediList.add(ediMap);

            }
            rs.close();

             for(Map<String, Object> emap:ediList) {
                 int controlcode = (Integer) emap.get("id");
                 int oid = (Integer) emap.get("order_fkey");
                 String type = (String) emap.get("type");
                 String docType = (String) emap.get("doctype");
                 PreparedStatement ps = HibernateSession.getPreparedStatement("update edi_queue set status=? where id=?");

                 ps.setInt(1, 1);
                 ps.setInt(2, controlcode);
                 ps.executeUpdate();
                 HibUtils.commit(HibernateSession.currentSession());

                 try {


                     com.altova.io.StringOutput output = new com.altova.io.StringOutput();

                     MappingMapTo940 MappingMapTo940Object = new MappingMapTo940();

                     MappingMapTo940Object.run(
                             ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(),
                             oid,
                             String.format("%08d", controlcode),
                             output);

                     log.debug(output.getString());

                     OwdEdiComRemoteFTP ftp = new OwdEdiComRemoteFTP();
                     ftp.putOutboundFile(type, output.getString().toString().getBytes(), type + "_" + docType + "_" + controlcode + ".edi");

                     ps = HibernateSession.getPreparedStatement("update edi_queue set status=? where id=?");

                     ps.setInt(1, 2);
                     ps.setInt(2, controlcode);
                     ps.executeUpdate();
                     HibUtils.commit(HibernateSession.currentSession());

                 } catch (Exception ex) {
                     ex.printStackTrace();
                     LogableException le = new LogableException(ex, "Error generating DS Water 940", "" + oid, "489", "EDI", LogableException.errorTypes.ORDER_IMPORT);

                 }


             }
            log.debug("done");

    }catch(Exception ex)
    {
        ex.printStackTrace();
        LogableException le = new LogableException(ex, "Error generating DS Water 940","TS:"+Calendar.getInstance().getTimeInMillis(), "489", "EDI", LogableException.errorTypes.ORDER_IMPORT);

    }finally
        {
        }*/

    }
    public static void main(String[] args) throws Exception{

        run();
/*
        com.altova.io.StringOutput output = new com.altova.io.StringOutput();

        MappingMapTo940 MappingMapTo940Object = new MappingMapTo940();

        MappingMapTo940Object.run(
                    ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(),
                8886208,
                    "00002174",
                    output);

        log.debug(output.getString());*/

    }
}
