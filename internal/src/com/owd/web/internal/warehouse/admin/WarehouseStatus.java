package com.owd.web.internal.warehouse.admin;

import com.owd.core.ConnectshipManifest;
import com.owd.core.ConnectshipTransmissionFile;
import com.owd.core.managers.ManifestingManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.core.managers.ConnectionManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdFacility;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import java.awt.*;
import java.io.Serializable;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Aug 18, 2003
 * Time: 1:17:16 PM
 * To change this template use Options | File Templates.
 */
public class WarehouseStatus {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) {

         try{
             WarehouseStatus status = new WarehouseStatus("DC1");
             System.out.println(status.getProductivityData("DC1"));
         //log.debug("start");
        log.debug(WarehouseStatus.getFacilityList());
         }catch(Exception ex){}
         // status = new WarehouseStatus("DC5");
        //log.debug("stop");
    }

    public static class ProductivityDataBean implements Serializable {
       private   String name;
        private   Float packHours;
        private   Float pickHours;
        private   int pickUnits;
        private   int packUnits;
        private   Float pickMean;
        private   Float packMean;

        public ProductivityDataBean()
        {
            super();
        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Float getPackHours() {
            return packHours;
        }

        public void setPackHours(Float packHours) {
            this.packHours = packHours;
        }

        public Float getPickHours() {
            return pickHours;
        }

        public void setPickHours(Float pickHours) {
            this.pickHours = pickHours;
        }

        public int getPickUnits() {
            return pickUnits;
        }

        public void setPickUnits(int pickUnits) {
            this.pickUnits = pickUnits;
        }

        public int getPackUnits() {
            return packUnits;
        }

        public void setPackUnits(int packUnits) {
            this.packUnits = packUnits;
        }

        public Float getPickMean() {
            return pickMean;
        }

        public void setPickMean(Float pickMean) {
            this.pickMean = pickMean;
        }

        public Float getPackMean() {
            return packMean;
        }

        public void setPackMean(Float packMean) {
            this.packMean = packMean;
        }
    }
    private static Map<String,List<ProductivityDataBean>> productivityMap = new TreeMap<String,List<ProductivityDataBean>>();

    private static long cacheExpiration = 0L;

    public synchronized static List getProductivityData(String locationCode)
    {
        try{


         if(Calendar.getInstance().getTimeInMillis()>cacheExpiration || productivityMap.size()==0)
         {
             productivityMap = new TreeMap<String,List<ProductivityDataBean>>();
             ResultSet rs = HibernateSession.getResultSet("select * from vw_production_metrics_picks where workdate=convert(datetime,convert(varchar,getdate(),101))");
             while(rs.next())
             {
                 if(!(productivityMap.containsKey(rs.getString("dept"))))
                 {
                     productivityMap.put(rs.getString("dept"),new ArrayList<ProductivityDataBean>());
                 }
                 List<ProductivityDataBean> rowList = productivityMap.get(rs.getString("dept"));
                 ProductivityDataBean bean = new ProductivityDataBean();
                 bean.setName(rs.getString("name"));
                 bean.setPackHours(rs.getFloat("Pack Hours"));
                 bean.setPickHours(rs.getFloat("Pick Hours"));
                 bean.setPickUnits(rs.getInt("picklines"));
                 bean.setPackUnits(rs.getInt("packunits"));
                 bean.setPickMean(bean.getPickHours()>0?OWDUtilities.roundFloat(((float) bean.getPickUnits())/bean.getPickHours(),0):0.00f);
                 bean.setPackMean(bean.getPackHours() > 0 ? OWDUtilities.roundFloat(((float) bean.getPackUnits()) / bean.getPackHours(), 0) : 0.00f);
                 rowList.add(bean);
             }
             rs.close();

             cacheExpiration = Calendar.getInstance().getTimeInMillis()+(1000L*(60*10));
         }


            return productivityMap.get(locationCode);

        }catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
    public static String getCurrentLocation(HttpServletRequest req)
    {
      /*  log.debug("STARTING CURRENT LOCATION");
        log.debug("REQ:"+req);
        log.debug("SESS:"+req.getSession(true));
        log.debug("ATTR:"+req.getSession(true).getAttribute("owd_current_location"));
        log.debug("ATTR:"+req.getSession(true).getAttribute("owd_default_location"));
        for (Enumeration e = req.getSession(true).getAttributeNames(); e.hasMoreElements(); ) {
            String attribName = (String) e.nextElement();
            Object attribValue = req.getSession(true).getAttribute(attribName);
            log.debug(attribName+":"+attribValue);
        }*/
            if(req.getSession(true).getAttribute("owd_current_location")==null)
        {
            req.getSession(true).setAttribute("owd_current_location",req.getSession(true).getAttribute("owd_default_location"));
        }

        log.debug("GOT CURRENT LOCATION : "+req.getSession(true).getAttribute("owd_current_location")==null?"(NULL)":req.getSession(true).getAttribute("owd_current_location"));
        return (String) req.getSession(true).getAttribute("owd_current_location");
    }

    public static String getCurrentAdjustmentLocation(HttpServletRequest req)
    {
      /*  log.debug("STARTING CURRENT LOCATION");
        log.debug("REQ:"+req);
        log.debug("SESS:"+req.getSession(true));
        log.debug("ATTR:"+req.getSession(true).getAttribute("owd_current_location"));
        log.debug("ATTR:"+req.getSession(true).getAttribute("owd_default_location"));
        for (Enumeration e = req.getSession(true).getAttributeNames(); e.hasMoreElements(); ) {
            String attribName = (String) e.nextElement();
            Object attribValue = req.getSession(true).getAttribute(attribName);
            log.debug(attribName+":"+attribValue);
        }*/
        if(req.getSession(true).getAttribute("owd_current_adjustment_location")==null)
        {
            req.getSession(true).setAttribute("owd_current_adjustment_location",req.getSession(true).getAttribute("owd_default_location"));
        }

        log.debug("GOT CURRENT LOCATION : "+req.getSession(true).getAttribute("owd_current_adjustment_location")==null?"(NULL)":req.getSession(true).getAttribute("owd_current_adjustment_location"));
        return (String) req.getSession(true).getAttribute("owd_current_adjustment_location");
    }

    static List<OwdFacility> facilityList = null;


    static public List<OwdFacility> getFacilityList() throws Exception
    {
        if(facilityList == null)
        {
           facilityList = (List<OwdFacility>)(HibernateSession.currentSession().createQuery("from OwdFacility where isActive=1").list());
        }
        return  facilityList;

    }


    //static private WarehouseStatus oldStatus = null;
    static private Map<String,WarehouseStatus> warehouseOldStatusMap = new TreeMap<String,WarehouseStatus>();
    static private Map<String,Long> creationTimeMap = new TreeMap<String,Long>();

    public static final int kClientOrderList = 1;
    public static final int kMethodOrderList = 2;
    public static final int kSLAOrderList = 3;

  //  protected static long creationTime = 0;

    List clientStatusList = new ArrayList();
    //client - posted - shipped

    List methodStatusList = new ArrayList();
    List slaStatusList = new ArrayList();

    Map methodDetailsMap = new TreeMap();
    Map clientDetailsMap = new TreeMap();
    Map slaDetailsMap = new TreeMap();
  //  Map<String, List<ConnectshipTransmissionFile>> untransmittedManifests = new HashMap<String, List<ConnectshipTransmissionFile>>();
  //  Map<String, List<ConnectshipManifest>> openManifests = new HashMap<String, List<ConnectshipManifest>>();


    public static void setOldStatus(String location, WarehouseStatus oldStatus) {
        warehouseOldStatusMap.put(location, oldStatus);
    }

    public JFreeChart getOldChart() {
        return oldChart;
    }

    public void setOldChart(JFreeChart oldChart) {
        this.oldChart = oldChart;
    }

    public JFreeChart getOldChart2() {
        return oldChart2;
    }

    public void setOldChart2(JFreeChart oldChart2) {
        this.oldChart2 = oldChart2;
    }

    public JFreeChart oldChart = null;
    public JFreeChart oldChart2 = null;

    public JFreeChart getOldChart3() {
        return oldChart3;
    }

    public void setOldChart3(JFreeChart oldChart3) {
        this.oldChart3 = oldChart3;
    }

    public JFreeChart oldChart3 = null;


    static {
        //	oldStatus = new WarehouseStatus();
    }

    public int getPostedOrdersLines() {
        return postedOrdersLines;
    }

    public void setPostedOrdersLines(int postedOrdersLines) {
        this.postedOrdersLines = postedOrdersLines;
    }

    public int getPostedOrdersUnits() {
        return postedOrdersUnits;
    }

    public void setPostedOrdersUnits(int postedOrdersUnits) {
        this.postedOrdersUnits = postedOrdersUnits;
    }

    public int getShippedOrdersLines() {
        return shippedOrdersLines;
    }

    public void setShippedOrdersLines(int shippedOrdersLines) {
        this.shippedOrdersLines = shippedOrdersLines;
    }

    public int getShippedOrdersUnits() {
        return shippedOrdersUnits;
    }

    public void setShippedOrdersUnits(int shippedOrdersUnits) {
        this.shippedOrdersUnits = shippedOrdersUnits;
    }

    public int getPickedOrders() {
        return pickedOrders;
    }

    public void setPickedOrders(int pickedOrders) {
        this.pickedOrders = pickedOrders;
    }

    public int getPickedOrdersLines() {
        return pickedOrdersLines;
    }

    public void setPickedOrdersLines(int pickedOrdersLines) {
        this.pickedOrdersLines = pickedOrdersLines;
    }

    public int getPickedOrdersUnits() {
        return pickedOrdersUnits;
    }

    public void setPickedOrdersUnits(int pickedOrdersUnits) {
        this.pickedOrdersUnits = pickedOrdersUnits;
    }


    public int getPackedOrders() {
        return packedOrders;
    }

    public void setPackedOrders(int packedOrders) {
        this.packedOrders = packedOrders;
    }

    public int getPackedOrdersLines() {
        return packedOrdersLines;
    }

    public void setPackedOrdersLines(int packedOrdersLines) {
        this.packedOrdersLines = packedOrdersLines;
    }

    public int getPackedOrdersUnits() {
        return packedOrdersUnits;
    }

    public void setPackedOrdersUnits(int packedOrdersUnits) {
        this.packedOrdersUnits = packedOrdersUnits;
    }

    public int getPackedUnshippedOrders() {
        return packedUnshippedOrders;
    }

    public void setPackedUnshippedOrders(int packedUnshippedOrders) {
        this.packedUnshippedOrders = packedUnshippedOrders;
    }

    public int getPackedUnshippedOrdersLines() {
        return packedUnshippedOrdersLines;
    }

    public void setPackedUnshippedOrdersLines(int packedUnshippedOrdersLines) {
        this.packedUnshippedOrdersLines = packedUnshippedOrdersLines;
    }

    public int getPackedUnshippedOrdersUnits() {
        return packedUnshippedOrdersUnits;
    }

    public void setPackedUnshippedOrdersUnits(int packedUnshippedOrdersUnits) {
        this.packedUnshippedOrdersUnits = packedUnshippedOrdersUnits;
    }

    public int getPickedUnpackedOrders() {
        return pickedUnpackedOrders;
    }

    public void setPickedUnpackedOrders(int pickedUnpackedOrders) {
        this.pickedUnpackedOrders = pickedUnpackedOrders;
    }

    public int getPickedUnpackedOrdersLines() {
        return pickedUnpackedOrdersLines;
    }

    public void setPickedUnpackedOrdersLines(int pickedUnpackedOrdersLines) {
        this.pickedUnpackedOrdersLines = pickedUnpackedOrdersLines;
    }

    public int getPickedUnpackedOrdersUnits() {
        return pickedUnpackedOrdersUnits;
    }

    public void setPickedUnpackedOrdersUnits(int pickedUnpackedOrdersUnits) {
        this.pickedUnpackedOrdersUnits = pickedUnpackedOrdersUnits;
    }

    int postedOrders = 0;
    int postedOrdersLines = 0;
    int postedOrdersUnits = 0;
    int shippedOrders = 0;
    int shippedOrdersLines = 0;
    int shippedOrdersUnits = 0;
    int pickedOrders = 0;
    int pickedOrdersLines = 0;
    int pickedOrdersUnits = 0;
    int packedOrders = 0;
    int packedOrdersLines = 0;
    int packedOrdersUnits = 0;



    int packedUnshippedOrders = 0;
    int packedUnshippedOrdersLines = 0;
    int packedUnshippedOrdersUnits = 0;


    int pickedUnpackedOrders = 0;
    int pickedUnpackedOrdersLines = 0;
    int pickedUnpackedOrdersUnits = 0;

    Map ordersWaitingToPrint = new HashMap();

    public static long getCreationTime(String location) {
        //	synchronized(oldStatus)
        //	{
        if(creationTimeMap.get(location)==null)
        {
            return 0;
        }            else
        {
        return creationTimeMap.get(location);
        }
        //	}
    }

    public static WarehouseStatus getOldStatus(String location) {

        //	synchronized(oldStatus)
        //	{
        return warehouseOldStatusMap.get(location);
        //	}
    }

  /*  public Map<String, List<ConnectshipManifest>> getOpenManifests() {
        return openManifests;
    }

    public Map<String, List<ConnectshipTransmissionFile>> getUntransmittedManifests() {
        return untransmittedManifests;
    }*/

    public List getClientStatusList() {
        return clientStatusList;
    }

    public List getMethodStatusList() {
        return methodStatusList;
    }

    public int getPostedOrders() {
        return postedOrders;
    }

    public int getShippedOrders() {
        return shippedOrders;
    }

    public Map getOrdersWaitingToPrint() {
        return ordersWaitingToPrint;
    }

    public Date getReferenceDate() {
        return referenceDate;
    }

    public List getPickHistory() {
        return picklist;
    }

    List picklist = new ArrayList();
    Date referenceDate = new Date();
    static String getCurrentDateTimeSQL = "select getdate()";

    /*
    order_num, ship_country,post_date,ship_first_name+\' \'+ship_last_name, "
        +"carr_service

        7 1 select order_num,
        8 2 ship_country,
        9 3 post_date,
        10 4 ship_first_name+\' \'+ship_last_name,

         carr_service,
        CASE WHEN ship_date is NULL then 0 else 1 END,
        7 CASE WHEN ship_date >= \'" + OWDUtilities.getSQLDateNoTimeForToday() + "\'  THEN 1 else 0 END,
         company_name

        */

    String getOrderStatusSQL = "exec sp_getwarehousestatusdataforlocation";

    String PickHistory = "select ph.pick_by, oc.company_name , count(*) as numpicked from order_pick_history ph (NOLOCK), " +
            "owd_order oo (NOLOCK), owd_client oc (NOLOCK) where ph.order_id = oo.order_id and oo.facility_code=? and oo.client_fkey = oc.client_id and " +
            "ph.start_pick_time > '" + OWDUtilities.getSQLDateNoTimeForToday() + "' group by ph.pick_by, oc.company_name order by ph.pick_by";

    static String getPrintingStatusSQL = "select printer_name,count(*) from owd_print_queue_sl q (NOLOCK) join owd_order o (NOLOCK) on q.order_id=o.order_id and facility_code=? group by printer_name";


    public List getSlaStatusList() {
        return slaStatusList;
    }

    public void setSlaStatusList(List slaStatusList) {
        this.slaStatusList = slaStatusList;
    }

    public WarehouseStatus(String location) {
        //synchronized(oldStatus)
        //	{
        // oldStatus = this;

     //   openManifests = ManifestingManager.getConnectshipOpenManifestsForToday(location) ;
      //  untransmittedManifests =  ManifestingManager.getConnectshipUntransmittedForToday(location) ;
        java.sql.Connection cxn = null;
        java.sql.PreparedStatement stmt = null;
        java.sql.ResultSet rs = null;

        creationTimeMap.put(location, Calendar.getInstance().getTime().getTime());


        List currentClientList = new ArrayList();
        List currentMethodList = new ArrayList();
        List currentSLAList = new ArrayList();
        try {

            cxn = ConnectionManager.getConnection();
            stmt = cxn.prepareStatement(getPrintingStatusSQL);
            stmt.setString(1,location);
            stmt.executeQuery();
            rs = stmt.getResultSet();
            ordersWaitingToPrint = new HashMap();
            while (rs.next()) {
                ordersWaitingToPrint.put(rs.getString(1),(""+rs.getInt(2)));

            }
            rs.close();
            stmt.close();


            stmt = cxn.prepareStatement(getOrderStatusSQL+"'"+location+"'");
            stmt.executeQuery();
            rs = stmt.getResultSet();

            while (rs.next()) {
              //  //log.debug("got unshipped order");
                String client = rs.getString(1);
                String method = rs.getString(2);
                String sla = rs.getString(11);
                int posted = 1;
                int shipped = rs.getInt(3);
                int isShipmentForToday = rs.getInt(5);
                int isPicked = rs.getInt(6);
                int isPacked = rs.getInt(16);
                int lines = rs.getInt(14);
                int units = rs.getInt(15);

                if (isShipmentForToday == 1 || shipped == 0) {

                    Map cMap = new TreeMap();

                    cMap.put("order_num", rs.getString(7));
                    cMap.put("ship_country", rs.getString(8));
                    cMap.put("post_date", rs.getString(9));
                    cMap.put("name", rs.getString(10));
                    cMap.put("carr_service", method);
                    cMap.put("shipped", "" + rs.getInt(3));
                    cMap.put("client", "" + client);
                    cMap.put("picked", "" + rs.getInt(6));
                    String mysla = sla.trim();
                    //U-1 : 2011-10-26 18:15:00
                 //   log.debug(mysla);
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date slaDate = df.parse(mysla);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(slaDate);
                    if("DC6".equals(location))
                    {
                        cal.add(Calendar.HOUR, -2);
                    }
                    cMap.put("sla", "" + df.format(cal.getTime()));
                    sla= df.format(cal.getTime());
                    cMap.put("on_hold", "" + rs.getString(12));
                    cMap.put("lines", "" + rs.getString(14));
                    cMap.put("units", "" + rs.getString(15));
                    cMap.put("packed", "" + rs.getInt(16));

                    if (1 != rs.getInt(12)) {

                        if (currentClientList.contains(client)) {
                            Map currentItemMap = ((Map) clientStatusList.get(currentClientList.indexOf(client)));
                            currentItemMap.put("POST", new Integer(posted + ((Integer) currentItemMap.get("POST")).intValue()));
                            currentItemMap.put("SHIP", new Integer(shipped + ((Integer) currentItemMap.get("SHIP")).intValue()));
                            currentItemMap.put("UNSHIP", new Integer(posted - shipped + ((Integer) currentItemMap.get("UNSHIP")).intValue()));
                            currentItemMap.put("PICKED", new Integer(isPicked + ((Integer) currentItemMap.get("PICKED")).intValue()));
                            currentItemMap.put("PACKED", new Integer(isPacked + ((Integer) currentItemMap.get("PACKED")).intValue()));
                            currentItemMap.put("PACKEDUNSHIPPED", new Integer((shipped == 1 ? 0 : isPacked) + ((Integer) currentItemMap.get("PACKEDUNSHIPPED")).intValue()));


                            currentItemMap.put("PICKEDUNPACKED", new Integer((isPacked==1  || shipped==1? 0 : isPicked) + ((Integer) currentItemMap.get("PICKEDUNPACKED")).intValue()));


                            currentItemMap.put("LINES", new Integer(lines + ((Integer) currentItemMap.get("LINES")).intValue()));
                            currentItemMap.put("UNITS", new Integer(units + ((Integer) currentItemMap.get("UNITS")).intValue()));
                            if (cMap.get("order_num") != null) {
                                List currentClientDetailsList = ((List) clientDetailsMap.get(client));
                                currentClientDetailsList.add(cMap);
                            }

                        } else {
                            Map info = new TreeMap();
                            info.put("CLIENT", client);

                            info.put("POST", new Integer(posted));
                            info.put("SHIP", new Integer(shipped));
                            info.put("UNSHIP", new Integer(posted - shipped));
                            info.put("PICKED", new Integer(isPicked));
                            info.put("PACKED", new Integer(isPacked));
                            info.put("PICKEDUNPACKED", new Integer(isPacked == 1  || shipped==1? 0 : isPicked));
                            info.put("PACKEDUNSHIPPED", new Integer(shipped == 1 ? 0 : isPacked));
                            info.put("LINES", new Integer(lines));
                            info.put("UNITS", new Integer(units));
                            clientStatusList.add(info);
                            currentClientList.add(client);


                            if (cMap.get("order_num") != null) {
                                List currentClientDetailsList = new ArrayList();
                                currentClientDetailsList.add(cMap);
                                clientDetailsMap.put(client, currentClientDetailsList);
                            }

                        }
                        if (currentMethodList.contains(method)) {
                            Map currentItemMap = ((Map) methodStatusList.get(currentMethodList.indexOf(method)));
                            currentItemMap.put("POST", new Integer(posted + ((Integer) currentItemMap.get("POST")).intValue()));
                            currentItemMap.put("SHIP", new Integer(shipped + ((Integer) currentItemMap.get("SHIP")).intValue()));
                            currentItemMap.put("UNSHIP", new Integer(posted - shipped + ((Integer) currentItemMap.get("UNSHIP")).intValue()));
                            currentItemMap.put("PICKED", new Integer(isPicked + ((Integer) currentItemMap.get("PICKED")).intValue()));
                            currentItemMap.put("PACKED", new Integer(isPacked + ((Integer) currentItemMap.get("PACKED")).intValue()));
                            currentItemMap.put("PACKEDUNSHIPPED", new Integer((shipped == 1 ? 0 : isPacked) + ((Integer) currentItemMap.get("PACKEDUNSHIPPED")).intValue()));
                            currentItemMap.put("PICKEDUNPACKED", new Integer((isPacked == 1  || shipped==1? 0 : isPicked) + ((Integer) currentItemMap.get("PICKEDUNPACKED")).intValue()));



                            currentItemMap.put("LINES", new Integer(lines + ((Integer) currentItemMap.get("LINES")).intValue()));
                            currentItemMap.put("UNITS", new Integer(units + ((Integer) currentItemMap.get("UNITS")).intValue()));
                            if (cMap.get("order_num") != null) {
                                List currentMethodDetailsList = ((List) methodDetailsMap.get(method));
                                currentMethodDetailsList.add(cMap);
                            }

                        } else {
                            Map info = new TreeMap();
                            info.put("METHOD", method);
                            info.put("POST", new Integer(posted));
                            info.put("SHIP", new Integer(shipped));
                            info.put("UNSHIP", new Integer(posted - shipped));
                            info.put("PICKED", new Integer(isPicked));
                            info.put("PACKED", new Integer(isPacked));
                            info.put("PACKEDUNSHIPPED", new Integer(shipped == 1 ? 0 : isPacked));
                            info.put("PICKEDUNPACKED", new Integer(isPacked == 1  || shipped==1? 0 : isPicked));

                            info.put("LINES", new Integer(lines));
                            info.put("UNITS", new Integer(units));

                            methodStatusList.add(info);
                            currentMethodList.add(method);

                            if (cMap.get("order_num") != null) {
                                List currentMethodDetailsList = new ArrayList();
                                currentMethodDetailsList.add(cMap);
                                if(method==null)
                                {
                                    log.debug("NULL:"+cMap);
                                }  else
                                {
                                methodDetailsMap.put(method, currentMethodDetailsList);
                                }
                            }

                        }

                        if(!client.equalsIgnoreCase("Lyft, Inc. -MH")) {
                            if (currentSLAList.contains(sla)) {
                                // //log.debug("appending to sla: " + sla);
                                Map currentSLAMap = ((Map) slaStatusList.get(currentSLAList.indexOf(sla)));
                                currentSLAMap.put("POST", new Integer(posted + ((Integer) currentSLAMap.get("POST")).intValue()));
                                currentSLAMap.put("SHIP", new Integer(shipped + ((Integer) currentSLAMap.get("SHIP")).intValue()));
                                currentSLAMap.put("UNSHIP", new Integer(posted - shipped + ((Integer) currentSLAMap.get("UNSHIP")).intValue()));
                                currentSLAMap.put("PICKED", new Integer(isPicked + ((Integer) currentSLAMap.get("PICKED")).intValue()));
                                currentSLAMap.put("PICKEDUNPACKED", new Integer((isPacked == 1 || shipped == 1 ? 0 : isPicked) + ((Integer) currentSLAMap.get("PICKEDUNPACKED")).intValue()));
                                currentSLAMap.put("PACKED", new Integer(isPacked + ((Integer) currentSLAMap.get("PACKED")).intValue()));
                                currentSLAMap.put("PACKEDUNSHIPPED", new Integer((shipped == 1 ? 0 : isPacked) + ((Integer) currentSLAMap.get("PACKEDUNSHIPPED")).intValue()));

                                currentSLAMap.put("LINES", new Integer(lines + ((Integer) currentSLAMap.get("LINES")).intValue()));
                                currentSLAMap.put("UNITS", new Integer(units + ((Integer) currentSLAMap.get("UNITS")).intValue()));

                                if (cMap.get("order_num") != null) {
                                    //    //log.debug("true append");
                                    List currentSLADetailsList = ((List) slaDetailsMap.get(sla));
                                    currentSLADetailsList.add(cMap);
                                }

                            } else {
                                //log.debug("adding sla: " + sla);
                                Map info = new TreeMap();
                                info.put("SLA", sla);
                                info.put("POST", new Integer(posted));
                                info.put("SHIP", new Integer(shipped));
                                info.put("UNSHIP", new Integer(posted - shipped));
                                info.put("PICKED", new Integer(isPicked));
                                info.put("PACKEDUNSHIPPED", new Integer(shipped == 1 ? 0 : isPacked));
                                info.put("PACKED", new Integer(isPacked));
                                info.put("PICKEDUNPACKED", new Integer(isPacked == 1 || shipped == 1 ? 0 : isPicked));

                                info.put("LINES", new Integer(lines));
                                info.put("UNITS", new Integer(units));

                                slaStatusList.add(info);
                                currentSLAList.add(sla);

                                if (cMap.get("order_num") != null) {
                                    //  //log.debug("true add");
                                    List currentSLADetailsList = new ArrayList();
                                    currentSLADetailsList.add(cMap);
                                    slaDetailsMap.put(sla, currentSLADetailsList);
                                }

                            }
                        }


                        postedOrders += posted;
                        postedOrdersLines += posted * lines;
                        postedOrdersUnits += posted * units;

                        pickedOrders += isPicked;
                        pickedOrdersLines += isPicked * lines;
                        pickedOrdersUnits += isPicked * units;

                        packedOrders += isPacked;
                        packedOrdersLines += isPacked * lines;
                        packedOrdersUnits += isPacked * units;
                        shippedOrders += shipped;

                        shippedOrdersLines += shipped * lines;
                        shippedOrdersUnits += shipped * units;
                        packedUnshippedOrders += (shipped == 1 ? 0 : isPacked);
                        pickedUnpackedOrders += (isPacked == 1 ? 0 : isPicked);

                    }
                }
            }
           rs.close();
            stmt.close();

            stmt = cxn.prepareStatement(PickHistory);
            stmt.setString(1,location);
            stmt.executeQuery();
            rs = stmt.getResultSet();

            //take pick history rs and put in picklist List
            while (rs.next()) {
                Map picklist1 = new TreeMap();
                picklist1.put("Picker", rs.getString(1));
                picklist1.put("client", rs.getString(2));
                picklist1.put("count", rs.getString(3));
                picklist.add(picklist1);
            }


            stmt = cxn.prepareStatement(getCurrentDateTimeSQL);
            stmt.executeQuery();
            rs = stmt.getResultSet();

            if (rs.next()) {
                referenceDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(rs.getString(1));


            }
            rs.close();
            stmt.close();

            warehouseOldStatusMap.put(location,this);

            TimeSeries series = new TimeSeries("Orders Posted",Hour.class);

            try {


                stmt = cxn.prepareStatement("select  hour_of_day,day_of_day,month_of_day,year_of_day,quantity as 'Orders Posted' " +
                        "from ops_hourly_summaries_byloc (NOLOCK) where chart_type='Orders Posted' and location=?");
                stmt.setString(1,location);
                stmt.executeQuery();
                rs = stmt.getResultSet();

                while (rs.next()) {

                    series.add(new Hour(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4)), rs.getDouble(5));


                }
                TimeSeriesCollection xyDataset = new TimeSeriesCollection(series);

               rs.close();
                stmt.close();

                /* TimeSeries seriesp = new TimeSeries("Orders Picked", Hour.class);

                 cxn =  ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();
                 stmt = cxn.prepareStatement("select  datepart(hour,pick_time),datepart(day,pick_time),datepart(month,pick_time),datepart(year,pick_time),count(*) as 'Orders Picked' from pick_events\n" +
                         "      where pick_time is not null and pick_end=1 and datediff(hour,pick_time,getdate()) < 49  \n" +
                         "      group by datepart(hour,pick_time),datepart(day,pick_time),datepart(month,pick_time),datepart(year,pick_time)");

                 stmt.executeQuery();
                 rs = stmt.getResultSet();

                 while (rs.next()) {

                     seriesp.add(new Hour(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4)), rs.getDouble(5));


                 }
                 rs.close();
 */

                //   xyDataset.addSeries(seriesp);
                JFreeChart chart = ChartFactory.createXYBarChart("5-Day Order History", // Title
                        "Hourly Period", // X-Axis label
                        true, //is x-axis date values?
                        "Orders Posted", // Y-Axis label
                        xyDataset, // Dataset
                        PlotOrientation.VERTICAL, //Plot orientation
                        false, // Show legend
                        true, //boolean - tooltips?
                        false      //boolean -   gen URLs?
                );


                //    XYPlot plot = (XYPlot) chart.getPlot();
                // XYAnnotation annotation = new XYTextAnnotation("Now is to the right ->", 599.0, 25.0); plot.addAnnotation(annotation);

                //    DateAxis xaxis = new DateAxis();
                //    chart.getXYPlot().setDomainAxis(xaxis);
                DateAxis axis = (DateAxis) chart.getXYPlot().getDomainAxis();

                axis.setDateFormatOverride(new SimpleDateFormat("dd-MMM"));
                axis.setInverted(true);
                chart.setAntiAlias(true);
                chart.setBackgroundPaint(Color.white);
                chart.getPlot().setBackgroundPaint(Color.white);

                oldChart = chart;




                TimeSeries series2 = new TimeSeries("Orders Picked",Hour.class);


                stmt = cxn.prepareStatement("select  hour_of_day,day_of_day,month_of_day,year_of_day,quantity as 'Orders Picked' " +
                        "from ops_hourly_summaries_byloc (NOLOCK) where chart_type='Orders Picked' and location=?");
                stmt.setString(1,location);

                stmt.executeQuery();
                rs = stmt.getResultSet();

                while (rs.next()) {

                    series2.add(new Hour(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4)), rs.getDouble(5));


                }
                TimeSeriesCollection xyDataset2 = new TimeSeriesCollection(series2);


                JFreeChart chart2 = ChartFactory.createXYBarChart("5-Day Picking History", // Title
                        "Hourly Period", // X-Axis label
                        true, //x-axis is dates?
                        "Orders Picked", // Y-Axis label
                        xyDataset2, // Dataset
                        PlotOrientation.VERTICAL, //Plot orientation
                        false, // Show legend
                        true, //boolean - tooltips?
                        false      //boolean -   gen URLs?
                );


                //    XYPlot plot = (XYPlot) chart.getPlot();
                // XYAnnotation annotation = new XYTextAnnotation("Now is to the right ->", 599.0, 25.0); plot.addAnnotation(annotation);

                DateAxis axis2 = (DateAxis) chart2.getXYPlot().getDomainAxis();
                axis2.setDateFormatOverride(new SimpleDateFormat("dd-MMM"));
                axis2.setInverted(true);
                chart2.setAntiAlias(true);
                chart2.setBackgroundPaint(Color.white);
                chart2.getPlot().setBackgroundPaint(Color.white);

                oldChart2 = chart2;

                       TimeSeries series3 = new TimeSeries("Orders Shipped",Hour.class);


                stmt = cxn.prepareStatement("select  hour_of_day,day_of_day,month_of_day,year_of_day,quantity as 'Orders Shipped' " +
                        "from ops_hourly_summaries_byloc (NOLOCK) where chart_type='Orders Shipped' and location=?");
                stmt.setString(1,location);

                stmt.executeQuery();
                rs = stmt.getResultSet();

                while (rs.next()) {

                    series3.add(new Hour(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4)), rs.getDouble(5));


                }
                TimeSeriesCollection xyDataset3 = new TimeSeriesCollection(series3);


                JFreeChart chart3 = ChartFactory.createXYBarChart("5-Day Shipping History", // Title
                        "Hourly Period", // X-Axis label
                        true, //x-axis is dates?
                        "Orders Shipped", // Y-Axis label
                        xyDataset3, // Dataset
                        PlotOrientation.VERTICAL, //Plot orientation
                        false, // Show legend
                        true, //boolean - tooltips?
                        false      //boolean -   gen URLs?
                );


                //    XYPlot plot = (XYPlot) chart.getPlot();
                // XYAnnotation annotation = new XYTextAnnotation("Now is to the right ->", 599.0, 25.0); plot.addAnnotation(annotation);

                DateAxis axis3 = (DateAxis) chart3.getXYPlot().getDomainAxis();
                axis3.setDateFormatOverride(new SimpleDateFormat("dd-MMM"));
                axis3.setInverted(true);
                chart3.setAntiAlias(true);
                chart3.setBackgroundPaint(Color.white);
                chart3.getPlot().setBackgroundPaint(Color.white);

                oldChart3 = chart3;
                rs.close();
                stmt.close();


            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                // HibernateSession.closeSession();
                try {
                    rs.close();
                } catch (Exception e) {
                }
                try {
                    stmt.close();
                } catch (Exception e) {
                }

                try {
                    cxn.close();
                } catch (Exception e) {
                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }

            try {
                cxn.close();
            } catch (Exception e) {
            }
            // HibernateSession.closeSession();
        }





        //}

    }

    public List getOrderInfoList(int listType, String listQualifier) {
        List aList = null;

        if (listType == kClientOrderList) {
            aList = (List) clientDetailsMap.get(listQualifier);
        } else if (listType == kMethodOrderList) {
            aList = (List) methodDetailsMap.get(listQualifier);
        } else {
            aList = (List) slaDetailsMap.get(listQualifier);
        }

        if (aList == null) return new ArrayList();

        return aList;
    }

}
