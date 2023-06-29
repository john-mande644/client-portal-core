package com.owd.intranet.adjustments;

import com.owd.core.managers.FacilitiesManager;
import com.owd.core.managers.LotManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.intranet.adjustments.beans.lotCountItemBean;
import com.owd.intranet.forms.lotCountForm;
import com.owd.intranet.util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.engine.spi.SessionImplementor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 2, 2006
 * Time: 3:45:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class indexLotCountAction extends Action {
private final static Logger log =  LogManager.getLogger();

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)



            throws Exception {
        try {
           Calendar cal = Calendar.getInstance();
           lotCountForm af = (lotCountForm) form;
            af.setCreatedBy(request.getRemoteUser());
            af.setCreatedDate(cal.getTime().toString());

            request.setAttribute("outcome","");
            form.reset(mapping, request);
            return (mapping.findForward("success"));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errors",e.getMessage());

            return mapping.findForward("error");

        }
    }
}
