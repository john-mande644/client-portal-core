<%@ page import="com.owd.core.Mailer,
				 com.owd.core.managers.ConnectionManager,
				 java.sql.Connection,
				 java.sql.PreparedStatement,
				 java.sql.ResultSet,
				 java.sql.ResultSetMetaData"%><%
	boolean sentFile = false;
					 boolean sendEmail = false;
					 String emailAddress = "";
	emailAddress = (String) request.getParameter("emailaddress");
	if(emailAddress == null) emailAddress = "";
					 if (emailAddress.length() > 0)
					 {
						 sendEmail = true;
					 }
	String reporttype = request.getParameter("reporttype");
	if(reporttype==null)reporttype="";
	if(reporttype.length()>0)
	{
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");

		String sql = "select * from mw_responses ";
		String startsql = " created_date >= ? ";
		String endsql = " created_date < dateadd(day,1,?) ";
		Connection cxn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try
		{
			cxn = ConnectionManager.getConnection();


       if(startdate != null && enddate != null)
	   {
		   sql = sql+"where"+startsql+"and"+endsql;
		   stmt = cxn.prepareStatement(sql+" order by id asc");
		   stmt.setString(1,startdate);
		   stmt.setString(2,enddate);
	   }   else if(startdate != null)
	   {
		   sql = sql+"where"+startsql;

		   stmt = cxn.prepareStatement(sql+" order by id asc");
		   stmt.setString(1,startdate);
	   }   else if (enddate != null)
	   {
		   sql = sql+"where"+endsql;

		   stmt = cxn.prepareStatement(sql+" order by id asc");
		   stmt.setString(1,enddate);
	   }   else
	   {

	   }
			StringBuffer sb = new StringBuffer();
		if(stmt != null)
		{
			rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
            for(int i=1;i<= columnCount;i++)
			{
					sb.append((i>1?"\t\"":"\"")+rsmd.getColumnName(i)+(i==columnCount?"\"\n":"\""));
			}

			while(rs.next())
			{
				for(int i=1;i<= columnCount;i++)
			{
				sb.append((i>1?"\t\"":"\"")+rs.getString(i).replace('\"','\'').replace('\r',' ').replace('\n',' ')+(i==columnCount?"\"\n":"\""));
			}
			}

			if(sendEmail)
			{

				Mailer.sendMailWithAttachment("MasterWriter Survey Results for "+startdate+" to "+enddate,"Data file attached",emailAddress,sb.toString().getBytes(),startdate.replace('/','_')+"_"+enddate.replace('/','_')+".txt","text/tab-separated-values");
			}else
			{

			out.write(sb.toString());

			 response.setContentType("text/tab-separated-values");
    response.setContentLength(sb.length());
    response.setHeader("Content-Disposition", "inline; filename=\""
                  + startdate.replace('/','_')+"_"+enddate.replace('/','_')+".tsv" + "\"");

    // The file content
    out.write(sb.toString());

    // Close
    out.close();

			sentFile = true;
			}
		}



      }catch(Exception ex)
		{
          ex.printStackTrace();
		}   finally
		{
          try{rs.close();}catch(Exception ex){}
			try{stmt.close();}catch(Exception ex){}
			try{cxn.close();}catch(Exception ex){}
		}
	}
	if(!sentFile)
	{
	%><HTML><script language="JavaScript">

       // The following script is used to hide the calendar whenever you click the document.
       document.onmousedown=function(e){
       ��var n=!e?self.event.srcElement.name:e.target.name;
       ��if (document.layers) {
       ����with (gfPop) var l=pageX, t=pageY, r=l+clip.width,b=t+clip.height;
       ����if (n!="popcal"&&(e.pageX>r||e.pageX<l||e.pageY>b||e.pageY<t)) gfPop.fHideCal();
       ����return routeEvent(e); // must use return here.
       ��} else if (n!="popcal") gfPop.fHideCal();
       }
       if (document.layers) document.captureEvents(Event.MOUSEDOWN);
       </script>

	   <link REL="stylesheet" HREF="/webapps/owd.css" TYPE="text/css">

<B>MasterWriter Survey Report Downloads</B>
<P>
Reports are delivered as a download in tab-delimited, text format. They are suitable for opening directly in Microsoft Excel or import into any database or spreadsheet program.
<P>
<FORM METHOD=POST name="dateform" ACTION="index.jsp">
<TABLE border=0 cellpadding=3>
<TR><TD ALIGN=RIGHT><B>Get Report Type:</B></TD><TD>
<SELECT NAME="reporttype">
<OPTION VALUE="responsedata">Response Data
<!-- <OPTION VALUE="calldata">Call Volume/Statistics -->
</SELECT></TD></TR>
<TR><TD COLSPAN=2>For the following dates (inclusive):</TD></TR>
<TR><TD ALIGN=RIGHT><B>Start Date:</TD><TD><input name="startdate" value="" size="11" onfocus="this.blur()" readonly><a href="javascript:void(0)" onclick="gfPop.fStartPop(document.dateform.startdate,document.dateform.enddate);return false;" HIDEFOCUS name="popcal" ><img name="popcali" align="absbottom" src="/webapps/images/calbtn.gif" width="34" height="22" border="0" alt=""></A>
</TD></TR><TR><TD ALIGN=RIGHT><B>End Date:</TD><TD><input name="enddate" value="" size="11" onfocus="this.blur()" readonly><a href="javascript:void(0)" onclick="gfPop.fEndPop(document.dateform.startdate,document.dateform.enddate);return false;" HIDEFOCUS><img name="popcal" align="absbottom" src="/webapps/images/calbtn.gif" width="34" height="22" border="0" alt=""></A>
</TD></TR>
<TR><TD ALIGN=RIGHT><B>Email To:</TD><TD><input type=text name="emailaddress" value="" >
</TD></TR>

<TR><TD COLSPAN=2><INPUT TYPE=SUBMIT NAME=SUBMIT VALUE="Download Report"></TD></TR>
 </TABLE>  </FORM>
<!--  PopCalendar(tag name and id must match) Tags should sit at the page bottom -->
<iframe width=152 height=163 name="gToday:outlook:agenda.js" id="gToday:outlook:agenda.js" src="/webapps/popcal/outlook/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0px;">
<LAYER name="gToday:outlook:agenda.js" src="/webapps/popcal/npopeng.htm">     </LAYER>
</iframe>
</FORM></HTML>
<% } %>