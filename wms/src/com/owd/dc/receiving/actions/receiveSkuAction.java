package com.owd.dc.receiving.actions;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Aug 10, 2005
 * Time: 9:22:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class receiveSkuAction extends Action {


    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        return (mapping.findForward("success"));
    }
}
