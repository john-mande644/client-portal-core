package com.owd.dc.warehouse.misc;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.owd.WMSUtils;
import com.owd.core.managers.FacilitiesManager;
import com.owd.dc.warehouse.misc.beans.boxCountBean;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 1/21/14
 * Time: 5:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class boxCountsAction  extends ActionSupport{

    private String sla;
    private String report;
    private List<String> reports;
    private List<boxCountBean> boxs = new ArrayList<boxCountBean>();
    private String runDate;
    private String facility;
    private List<String> facilities;


   private String sql = "";
    private String allSql = "SELECT\n" +
            " box.name,\n" +
            "    count(dbo.owd_order.order_num)\n" +
            "    \n" +
            "FROM\n" +
            "    dbo.owd_order\n" +
            "LEFT OUTER JOIN dbo.w_order_attributes\n" +
            "ON\n" +
            "    (\n" +
            "        dbo.owd_order.order_id = dbo.w_order_attributes.order_fkey\n" +
            "    )\n" +
            "OUTER APPLY (\n" +
            "SELECT top 1 \n" +
            "    dbo.owd_boxtypes.name\n" +
            "FROM\n" +
            "    dbo.package_box_count\n" +
            "INNER JOIN dbo.owd_boxtypes\n" +
            "ON\n" +
            "    (\n" +
            "        dbo.package_box_count.box_fkey = dbo.owd_boxtypes.id\n" +
            "    )\n" +
            "WHERE\n" +
            "    dbo.package_box_count.fingerprint = w_order_attributes.fingerprint \n" +
            "order by boxcount desc\n" +
            "\n" +
            ") as box\n" +
            "WHERE\n" +
            "    dbo.owd_order.order_status = 'At Warehouse'\n" +
            "AND dbo.owd_order.customer_vendor_no LIKE :sla \n" +
            "AND dbo.owd_order.facility_code = :facility and fingerprint is not null and box.name is not null\n" +
            "group by box.name ";
    private String candleSql = "execute getCandleBoxCountFacility :sla, :facility";


    public String execute(){
        facility = WMSUtils.getDefaultFacilityFromContext(ActionContext.getContext());

        return "success";
    }

    public String getCount(){
              try{
                  System.out.println(report);
                  if (report.equals("All Boxes")) sql = allSql;
                  if(report.equals("Candles")) sql = candleSql;

                  Query q = HibernateSession.currentSession().createSQLQuery(sql);
                  q.setParameter("sla","%"+sla+"%");
                  q.setParameter("facility",facility);
                  List results = q.list();
                  if (results.size()>0){
                      System.out.println("We have results!!:");
                       for (Object row:results){
                           Object[] data = (Object[]) row;
                         boxCountBean bcb = new boxCountBean();
                        bcb.setBoxName(data[0].toString());
                           bcb.setQty(data[1].toString());
                          boxs.add(bcb);
                       }

                  } else{
                      addActionError("Nothing found for this matchup");
                  }

              } catch (Exception e){
                  addActionError(e.getMessage());

              }

        return "success";
    }
    public String getSla() {
        return sla;
    }

    public void setSla(String sla) {
        this.sla = sla;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public List<String> getReports() {
        reports = Arrays.asList("All Boxes","Candles");
        return reports;
    }

    public void setReports(List<String> reports) {
        this.reports = reports;
    }

    public List<boxCountBean> getBoxs() {
        return boxs;
    }

    public void setBoxs(List<boxCountBean> boxs) {
        this.boxs = boxs;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public List<String> getFacilities() {
        try{
          facilities = FacilitiesManager.getActiveFacilityCodes();
        }catch(Exception e){

        }
        return facilities;
    }

    public void setFacilities(List<String> facilities) {
        this.facilities = facilities;
    }
}
