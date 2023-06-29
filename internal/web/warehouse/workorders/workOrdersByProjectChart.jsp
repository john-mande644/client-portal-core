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
                 com.owd.hibernate.HibernateFogbugzSession" %>
<%


    java.sql.PreparedStatement stmt = null;

    DefaultPieDataset series = new DefaultPieDataset();

    try {


        ResultSet rs = HibernateSession.getResultSet(HibernateFogbugzSession.currentSession(), new StringBuffer()
                .append("select sProject, ")
                .append("count(*) from bug b  ")
                .append("join category c on c.ixCategory=b.ixCategory ")
                .append("join person p on p.ixPerson=b.ixPersonAssignedTo  ")
                .append("join status s on s.ixStatus=b.ixStatus ")
                .append("join project pro on pro.ixProject=b.ixProject ")
                .append("where fopen=1 group by sProject").toString());


        while (rs.next()) {

            series.setValue(rs.getString(1), rs.getDouble(2));


        }

        JFreeChart chart = ChartFactory.createPieChart3D("Active Work Orders by Project", // Title
                series,         // Dataset
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

        ChartUtilities.writeChartAsJPEG(response.getOutputStream(), chart, 300, 200);


    } catch (Exception ex) {
        ex.printStackTrace();
    } finally {
        HibernateFogbugzSession.closeSession();

    }


%>