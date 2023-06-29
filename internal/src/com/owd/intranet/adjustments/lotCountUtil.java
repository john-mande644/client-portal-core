package com.owd.intranet.adjustments;

import com.owd.core.dbUtilities;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.managers.InventoryManager;
import com.owd.core.managers.LotManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.*;
import com.owd.intranet.adjustments.beans.lotCountItemBean;
import com.owd.intranet.forms.adjustForm;
import com.owd.intranet.forms.lotCountForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;

import java.sql.Connection;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 7, 2006
 * Time: 4:05:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class lotCountUtil {
private final static Logger log =  LogManager.getLogger();

    public static lotCountForm saveLotCount(lotCountForm af, Session sess) throws Exception{

         ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

        try{
            log.debug("saving lot count for form:"+af) ;
             for(lotCountItemBean lot:af.getFormItems()) {
                 log.debug("resetting qty for count item:"+lot);
                 if(lot.getNewQty() != lot.getLotQty()) {
                     LotManager.resetLotQuantity(af.getCreatedBy(),Integer.parseInt(af.getInventoryId()),
                             FacilitiesManager.getFacilityForCode(af.getCurrentLocation()).getId(),
                             LotManager.getLotIdForValue(lot.getLotValue(), Integer.parseInt(af.getInventoryId())),
                             lot.getLotQty(),lot.getNewQty());
                 }
             }
            HibUtils.commit(HibernateSession.currentSession());
        } catch(Exception e){
             e.printStackTrace();

            throw (new Exception("Unable to save Adjustment ("+e.getMessage()+")"));

        }

        return af;
    }


    }


