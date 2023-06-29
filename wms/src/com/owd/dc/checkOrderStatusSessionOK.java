package com.owd.dc;

import com.owd.hibernate.generated.OrderPickStatus;
import com.owd.dc.picking.PickUtilities;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 13, 2005
 * Time: 10:16:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class checkOrderStatusSessionOK {

    public static boolean checkx(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        OrderPickStatus currpstat = (OrderPickStatus) req.getSession(true).getAttribute("currpickorder");

        //from database (see checkAuthentication method)
        OrderPickStatus oldpstat = (OrderPickStatus) req.getAttribute("oldpickorder");


        boolean pickOK = false;
        if (currpstat != null && oldpstat != null) {

            System.out.println("both pickstatus not null");
            //verify that they are the same
            if (currpstat.getId().intValue() == oldpstat.getId().intValue()) {
                //OK
                System.out.println("pick status match");
             //   System.out.println(currpstat.getCurrentItemIndex());
              //  System.out.println(oldpstat.getCurrentItemIndex());
                req.getSession(true).setAttribute("currpickorder", req.getAttribute("oldpickorder"));
                if ("pick".equals(req.getAttribute("action")) || "pick-start".equals(req.getAttribute("action"))) {
                   // req.getRequestDispatcher("/rfapps/pick/recoverpick.jsp").forward(req, resp);
                    pickOK = false;
                  return pickOK;
              } else {
                    pickOK = true;

                }


            } else {
                //not the same. Ignore session pick, throw to recover pick page
                System.out.println("pick status no match");

                PickUtilities.clearSessionPickStatus(req);
               // req.getRequestDispatcher("/rfapps/pick/recoverpick.jsp").forward(req, resp);

              pickOK = false;
               return pickOK;
            }
        } else if (oldpstat != null) {
            //pstat null, old exists. Throw to recover pick page
            PickUtilities.clearSessionPickStatus(req);
            System.out.println("pick status old not null, current is null");
            pickOK = false;
           // req.getRequestDispatcher("/rfapps/pick/recoverpick.jsp").forward(req, resp);
            return pickOK;
        } else if (currpstat != null) {
            //pstat not null, oldpstat is null. Should not happen. If does, ignore w/message
            System.out.println("*** Error loading old pickorder - session exists, db backing does not");
            PickUtilities.clearSessionPickStatus(req);
            pickOK = true;

            System.out.println("pick status old  null, current is not null");


        } else {
            //all null
            pickOK = true;
            PickUtilities.updateSessionPickStatusFromOld(req, (OrderPickStatus) req.getAttribute("oldpickorder"));

            System.out.println("pick status all null");

        }

        return pickOK;
    }
}
