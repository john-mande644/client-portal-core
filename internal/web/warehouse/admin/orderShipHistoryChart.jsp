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
                 com.owd.hibernate.HibernateSession,
                 org.jfree.data.time.Hour,
                 org.jfree.data.time.TimeSeriesCollection,
                 org.jfree.data.time.TimeSeries,
                 org.jfree.chart.axis.DateAxis,
                 java.text.SimpleDateFormat,
                 java.awt.*,
                 com.owd.web.internal.warehouse.admin.WarehouseStatus,
                 java.util.Calendar,
                 org.jfree.chart.plot.XYPlot" %>
<%


    try {
        WarehouseStatus status = null;
        String currentLocation = WarehouseStatus.getCurrentLocation(request);


        if (WarehouseStatus.getCreationTime(currentLocation) < (Calendar.getInstance().getTime().getTime() - (.5 * 1000 * 60))) //5 minute interval
        {
            status = new WarehouseStatus(currentLocation);
        } else {
            status = WarehouseStatus.getOldStatus(currentLocation);

        }
        ((XYPlot) status.oldChart3.getPlot()).getRenderer().setPaint(Color.green);
        ChartUtilities.writeChartAsJPEG(response.getOutputStream(), status.oldChart3, 800, 400);


    } catch (Exception ex) {
        ex.printStackTrace();
    } finally {

    }


%>