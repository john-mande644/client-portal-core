<%@ page import="com.owd.web.servlet.ExtranetManager,
                 com.owd.web.servlet.ExtranetServlet,
                 java.util.Iterator,
				 java.util.List"%><%
	ExtranetManager mgr = ExtranetServlet.getManager(request,response);



%><HTML><link REL="stylesheet" HREF="/webapps/screen.css" TYPE="text/css"><HEAD><TITLE>
One World Extranet
</TITLE>
<script language="JavaScript">
     // The following script is used to hide the calendar whenever you click the document.
     document.onmousedown=function(e){
     var n=!e?self.event.srcElement.name:e.target.name;
     if (document.layers) {
     with (gfPop) var l=pageX, t=pageY, r=l+clip.width,b=t+clip.height;
     if (n!="popcal"&&(e.pageX>r||e.pageX<l||e.pageY>b||e.pageY<t)) gfPop.fHideCal();
     return routeEvent(e); // must use return here.
     } else if (n!="popcal") gfPop.fHideCal();
            }
     if (document.layers) document.captureEvents(Event.MOUSEDOWN);
     </script>
</HEAD>
<body bgcolor="f0f0f0" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" link="#003366" vlink="#003366" alink="#660000">

<FORM METHOD=POST ACTION="<%= com.owd.web.servlet.ExtranetServlet.kExtranetURLPath %>">
	<!-- top row -->
	<table border="0" cellpadding="0" cellspacing="0" width=100%>
		<tr>
			<td bgcolor="#3C5490">
				<table width=100% cellpadding=0 cellspacing=0 border=0>
					<tr>
                         <td width=5>
							<img src="/images/border/spacer.gif" width=5 height=20 border=0>
						</td>
						<td valign=center nowrap ><H3><font color=white>&nbsp;OWD Extranet</font></H3>
						</td>
                        <td valign=center align=center width=100%><%= ExtranetServlet.getClientSelector(request) %>

                        </td>    </FORM>
						<td valign=center align=right  nowrap>
							<B><font color="#ffffff" size=1> User: <%= request.getUserPrincipal().getName() %>&nbsp; &nbsp; <A HREF="<%= com.owd.web.servlet.ExtranetServlet.kExtranetURLPath %>?logout=logout"><font color=white  >[Log Out]</font></A> &nbsp; &nbsp; </font>
                           </B></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
<P>
<H1>Reports</H1>
	<!-- end top row -->
<table border="0" cellpadding="0" cellspacing="0">
			<tr>
<% List links = ExtranetManager.getManagerList(request);
    Iterator it = links.iterator();
    while (it.hasNext())
    {
%>
<%= it.next() %>
                    <td width="1" bgcolor="#A0A0A0">
					<img width="1" height="1" alt="">
				</td>
				<td width="1" bgcolor="#ffffff">
					<img width="1" height="1" alt="">
				</td>
				<% } %>
			</tr>
		</table>

		<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#3C5490">
			<tr>
				<td bgcolor="#3C5490" nowrap>
					<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#3C5490">
						<tr>


			<% String[] urls = mgr.getManagerActions(request); for(int i=0;i<urls.length;i++){   %>
<td bgcolor="#3C5490" nowrap class=command><%= urls[i] %>&nbsp;&mdash;</td>

<%   } %>
							<td width=99%></TD>
						</tr>
					</table>
				</td>
			</tr>
		</table>
<!-- End Header -->

<!-- main content area -->