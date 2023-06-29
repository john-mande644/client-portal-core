package com.owd.web.internal.zplutils;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AddressFormServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("locations", getLocationsString());
        request.getRequestDispatcher("address_form.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String shipTo = request.getParameter("ship_to");
        String address1 = request.getParameter("address1");
        String address2 = request.getParameter("address2");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zip = request.getParameter("zip");

        String result = "";

        try {
            String queryStr = "INSERT INTO ship_locations (address1, address2, city, state, ship_to, zip_code) VALUES " +
                    "(:address1, :address2, :city, :state, :shipTo, :zipCode);";
            Query q = HibernateSession.currentSession().createSQLQuery(queryStr);
            q.setParameter("address1", address1);
            q.setParameter("address2", address2);
            q.setParameter("city", city);
            q.setParameter("state", state);
            q.setParameter("shipTo", shipTo);
            q.setParameter("zipCode", zip);

            int c = q.executeUpdate();

            if (c == 0) {
                result = "No changes made to database\n";
            } else {
                result = "Ship location successfully added";
                HibUtils.commit(HibernateSession.currentSession());
            }
        } catch(Exception e) {
            e.printStackTrace();
            result = "Exception occured: " + e;
        }

        request.setAttribute("result", result);
        request.setAttribute("locations", getLocationsString());
        request.getRequestDispatcher("address_form.jsp").forward(request, response);
    }

    private String getLocationsString() {
        List<ShipLocation> locations = DBUtils.getLocations();
        StringBuilder sb = new StringBuilder();
        for(ShipLocation s : locations) {
            sb.append("<li>" + s.getDisplay() + "</li>");
        }

        return sb.toString();
    }
}
