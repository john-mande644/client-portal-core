<%@ page import="org.jfree.data.category.CategoryDataset,
                 org.jfree.data.category.DefaultCategoryDataset,
                 org.jfree.chart.JFreeChart,
                 org.jfree.chart.ChartFactory,
                 org.jfree.chart.plot.PlotOrientation,
                 org.jfree.chart.ChartUtilities,
                 org.jfree.data.general.DatasetUtilities,
                 org.jfree.data.xy.XYSeriesCollection,
                 org.jfree.data.xy.XYDataset,
                 org.jfree.data.xy.XYSeries,
                 org.jfree.data.xy.IntervalXYDataset,
                 java.util.Map,
                 java.util.TreeMap,
                 com.owd.core.managers.ConnectionManager,
                 org.jfree.chart.axis.DateAxis,
                 java.text.SimpleDateFormat,
                 org.jfree.data.time.Hour,
                 org.jfree.data.time.TimeSeries,
                 org.jfree.data.time.TimeSeriesCollection,
                 org.jfree.chart.annotations.XYTextAnnotation,
                 org.jfree.chart.annotations.XYAnnotation,
                 org.jfree.chart.plot.XYPlot,
                 java.awt.*,
                 com.owd.hibernate.HibernateSession,
                 org.jfree.data.general.PieDataset,
                 org.jfree.data.general.DefaultPieDataset,
                 java.sql.ResultSet,
                 com.owd.hibernate.HibernateFogbugzSession,
                 org.jfree.util.TableOrder" %>
<%


    java.sql.PreparedStatement stmt = null;

    DefaultCategoryDataset series = new DefaultCategoryDataset();

    try {


        Map clientMap = new TreeMap();

        ResultSet rs = HibernateSession.getResultSet("select client_id,company_name from owd_client");
        while (rs.next()) {
            clientMap.put(rs.getString(1), rs.getString(2));

        }
        clientMap.put("", "Unknown");
        clientMap.put("0", "Unknown");
        clientMap.put("", "Unknown");


        rs = HibernateSession.getResultSet(HibernateFogbugzSession.currentSession(), new StringBuffer()
                .append("select b.sComputer , sProject,")
                .append("count(*) from bug b  ")
                .append("join category c on c.ixCategory=b.ixCategory ")
                .append("join person p on p.ixPerson=b.ixPersonAssignedTo  ")
                .append("join status s on s.ixStatus=b.ixStatus ")
                .append("join project pro on pro.ixProject=b.ixProject ")
                .append("where fopen=1 group by b.sComputer, sProject").toString());


        while (rs.next()) {

            series.addValue(rs.getDouble(3), (String) clientMap.get(rs.getString(1)), rs.getString(2));


        }

        JFreeChart chart = ChartFactory.createMultiplePieChart3D("Active Work Orders by Client", // Title
                series,         // Dataset
                TableOrder.BY_COLUMN,
                false,                     // Show legend
                true,     //boolean - tooltips?
                false      //boolean -   gen URLs?
        );

        // chart.setAntiAlias(true);
        chart.setBackgroundPaint(Color.white);
        chart.getPlot().setBackgroundPaint(Color.white);

        //    XYPlot plot = (XYPlot) chart.getPlot(); XYAnnotation annotation = new XYTextAnnotation("Now is to the right ->", 599.0, 25.0); plot.addAnnotation(annotation);

        // DateAxis axis = (DateAxis) chart.getXYPlot().getDomainAxis();
        // axis.setDateFormatOverride(new SimpleDateFormat("dd MMM hh"));

        ChartUtilities.writeChartAsJPEG(response.getOutputStream(), chart, 800, 600);


    } catch (Exception ex) {
        ex.printStackTrace();
    } finally {
        HibernateFogbugzSession.closeSession();

    }


%>