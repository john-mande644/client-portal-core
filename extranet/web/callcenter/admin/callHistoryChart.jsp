<%@ page import="com.owd.web.callcenter.admin.CallCenterStatus,
                 org.jfree.chart.ChartUtilities,
                 java.util.Calendar"%><%


try
{


          CallCenterStatus status = null;

    synchronized(CallCenterStatus.lockObject)
    {
	if(CallCenterStatus.getCreationTime() < (Calendar.getInstance().getTime().getTime()-(1*1000*60))) //5 minute interval
	{
	status = new CallCenterStatus();
	}else{
		status = CallCenterStatus.getOldStatus();

	}
                               ChartUtilities.writeChartAsJPEG(response.getOutputStream(),status.getOldChart(),800,250);
      }

             } catch (Exception ex) {
                 ex.printStackTrace();
             } finally {

             }




%>