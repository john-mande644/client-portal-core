package com.owd.dc.setup;

import com.owd.WMSUtils;
import com.owd.dc.HandheldUtilities;
import com.owd.dc.checkAuthentication;
import com.owd.hibernate.HibernateSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Nov 3, 2005
 * Time: 9:08:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class setupStartAction extends Action {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
            try {
                checkAuthentication.check(request, response);
            } catch (Exception ex) {
                return (mapping.findForward("login"));
            }
            buttons info = new buttons();
            buttonForm buttonForm = (buttonForm)form;
          /*  buttonForm.setT5(info.getT5());
            buttonForm.setT6(info.getT6());
            buttonForm.setT7(info.getT7());
            buttonForm.setT8(info.getT8());
            buttonForm.setRed(info.getRed());
            buttonForm.setGreen(info.getGreen());*/
            System.out.println("here is the login name we are using");
            System.out.println(request.getAttribute("loginName").toString());
            buttonForm.setPrinter(info.getPrinter(request.getAttribute("loginName").toString()));
            buttonForm.setSmallPrinter(info.getSmallPrinter(request.getAttribute("loginName").toString()));
            buttonForm.setPalletTag(info.getPalletTag(request.getAttribute("loginName").toString()));
            buttonForm.setTeleport(HandheldUtilities.canTeleportByName(request.getAttribute("loginName").toString()));

            buttonForm.setFacility(HandheldUtilities.getStoredFacilityForName(request.getAttribute("loginName").toString()));
            List btnList = new ArrayList();
            String[] list = buttonForm.getList();
            String[] visible = buttonForm.getVisibleList();
            for(int i=0;i<buttonForm.getList().length;i++){
                System.out.println(list[i]);
                selectList btn = new selectList();
              btn.setAction(list[i]);
                btn.setDisplay(visible[i]);
              btnList.add(i,btn);
            }
           buttonForm.setPrinterlist(getLargePrinters());
           buttonForm.setLists(btnList);
            buttonForm.setSmallprinterlist(getSmallPrinters());
            buttonForm.setPalletTags(getPalletTags());
            buttonForm.setFacilities(WMSUtils.getSelectListActiveFacilities());

            return (mapping.findForward("success"));


        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            return (mapping.findForward("error"));

        }
    }

    public static List getSmallPrinters() throws Exception{
        List printers = new ArrayList();
        String query="select value, display from app_data where description = 'labelMaker' and variable = 'printer' and display like  '%Small'";

        ResultSet rs =HibernateSession.getResultSet(query);
        int i = 0;
        while (rs.next()){
            selectList btn = new selectList();
              btn.setAction(rs.getString(1));
                btn.setDisplay(rs.getString(2));
             printers.add(i,btn);
            i++;
        }

        return printers;

}
    public static List getPalletTags() throws Exception{
        List printers = new ArrayList();
        String query="select value, display from app_data where description = 'labelMaker' and variable = 'pallet' ";

        ResultSet rs =HibernateSession.getResultSet(query);
        int i = 0;
        while (rs.next()){
            selectList btn = new selectList();
            btn.setAction(rs.getString(1));
            btn.setDisplay(rs.getString(2));
            printers.add(i,btn);
            i++;
        }

        return printers;

    }
    public static List getLargePrinters() throws Exception{
        List printers = new ArrayList();
        String query="select value, display from app_data where description = 'labelMaker' and variable = 'printer' and display like  '%Large'";
        ResultSet rs =HibernateSession.getResultSet(query);
        int i = 0;
        while (rs.next()){
            selectList btn = new selectList();
              btn.setAction(rs.getString(1));
                btn.setDisplay(rs.getString(2));
             printers.add(i,btn);
            i++;
        }

        return printers;

}
}
