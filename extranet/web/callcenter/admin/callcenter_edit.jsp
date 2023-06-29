<%@ page import="com.owd.core.managers.ConnectionManager,
				 com.owd.web.servlet.ExtranetServlet,
				 java.util.HashMap,
				 java.util.Map"%><!doctype html public "-//w3c/dtd HTML 4.0//en">

<html>

<head>

<title>OWD Call Center Screen Pop Configuration</title>

</head>
<BODY BGCOLOR="white" LEFTMARGIN="0" TOPMARGIN="0" MARGINHEIGHT="0" MARGINWIDTH="0" ALINK="blue" VLINK="blue" LINK="blue" TEXT="black" >

<BASEFONT >
OWD Call Center Screen Pop Configuration<P><B><A href="callcenter_config.jsp">Back to List</A></B><HR>

<table border=3 cellpadding=5>

<%

	

	java.sql.Connection cxn = null;

	java.sql.PreparedStatement stmt = null;

	java.sql.ResultSet rs = null;
	String reportAction = "";
	String actionRequest = request.getParameter("ACTION");
	reportAction = request.getParameter("reportAction");
	String id = request.getParameter("id");
	try
	{
	
	int tester = new Integer(id).intValue();
	}catch(Exception ex)
	{
		id="0";
	}
	if(actionRequest == null) actionRequest = "";

	if(actionRequest.equals("DELETE"))
	{
try{
String sql = "delete from owd_client_callcenter where id = ?";

	cxn = ConnectionManager.getConnection();

			stmt = cxn.prepareStatement(sql);

			stmt.setInt(1,new Integer(request.getParameter("id")).intValue());

			int rows = stmt.executeUpdate();
cxn.commit();

			reportAction=rows+" record deleted";

			



}catch(Throwable th)

{

	th.printStackTrace();

	%>

	<tr><td><font size=2 color=red>Error: <%= th.getMessage() %></font></td></tr>

	<%

}finally

{

	try{rs.close();}catch(Exception ex){}

	try{stmt.close();}catch(Exception ex){}

	try{cxn.close();}catch(Exception ex){}



}

	}else if (actionRequest.equals("SAVE CHANGES"))
	{
try{
String sql = "update owd_client_callcenter set announce_script=?,do_entry=?,do_status=?,do_service=?,client_fkey=?,url_status=?,url_entry=?, "
		+"url_service=?,Mlog_campaign_name=?,url_kb_base=?, url_entry_pop=?,url_service_pop=?,url_status_pop=?,url_kb_base_pop=?,"
    +"do_orderref_entry=?,url_quick_ref=?, ticketbox=?, email_domain=? where id = ?";

	cxn = ConnectionManager.getConnection();

			stmt = cxn.prepareStatement(sql);
			stmt.setString(1,request.getParameter("announce_script"));
			stmt.setInt(2,new Integer((request.getParameter("do_entry")==null?"0":"1")).intValue());
			stmt.setInt(3,new Integer((request.getParameter("do_status")==null?"0":"1")).intValue());
			stmt.setInt(4,new Integer((request.getParameter("do_service")==null?"0":"1")).intValue());
			stmt.setInt(5,new Integer((request.getParameter("client_fkey")==null?"0":request.getParameter("client_fkey"))).intValue());
			stmt.setString(6,request.getParameter("url_status"));
			stmt.setString(7,request.getParameter("url_entry"));
			stmt.setString(8,request.getParameter("url_service"));
			stmt.setString(9,request.getParameter("Mlog_campaign_name"));
			stmt.setString(10,request.getParameter("url_kb_base"));
			stmt.setInt(11,new Integer(request.getParameter("url_entry_pop")==null?"0":"1").intValue());
			stmt.setInt(12,new Integer(request.getParameter("url_service_pop")==null?"0":"1").intValue());
			stmt.setInt(13,new Integer(request.getParameter("url_status_pop")==null?"0":"1").intValue());
			stmt.setInt(14,new Integer(request.getParameter("url_kb_base_pop")==null?"0":"1").intValue());
            stmt.setInt(15,new Integer(request.getParameter("do_orderref_entry")==null?"0":"1").intValue());
            stmt.setString(16,request.getParameter("url_quick_ref"));
            stmt.setString(17, request.getParameter("ticketbox"));
            stmt.setString(18, request.getParameter("email_domain"));
			stmt.setInt(19,new Integer(request.getParameter("id")).intValue());





			int rows = stmt.executeUpdate();
cxn.commit();

			reportAction=rows+" record updated";

			



}catch(Throwable th)

{

	th.printStackTrace();

	%>

	<tr><td><font size=2 color=red>Error: <%= th.getMessage() %></font></td></tr>

	<%

}finally

{

	try{rs.close();}catch(Exception ex){}

	try{stmt.close();}catch(Exception ex){}

	try{cxn.close();}catch(Exception ex){}



}
	} else if (actionRequest.equals("SAVE NEW ENTRY"))
	{
try{
String sql = "insert into owd_client_callcenter (announce_script,do_entry,do_status,do_service,client_fkey,url_status,url_entry, "
		+"url_service,Mlog_campaign_name,url_kb_base,url_entry_pop,url_service_pop,url_status_pop,url_kb_base_pop,do_orderren_entry,url_quick_ref,ticketbox,email_domain) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	cxn = ConnectionManager.getConnection();

			stmt = cxn.prepareStatement(sql);
			stmt.setString(1,request.getParameter("announce_script"));
			stmt.setInt(2,new Integer((request.getParameter("do_entry")==null?"0":"1")).intValue());
			stmt.setInt(3,new Integer((request.getParameter("do_status")==null?"0":"1")).intValue());
			stmt.setInt(4,new Integer((request.getParameter("do_service")==null?"0":"1")).intValue());
			stmt.setInt(5,new Integer((request.getParameter("client_fkey")==null?"0":request.getParameter("client_fkey"))).intValue());
			stmt.setString(6,request.getParameter("url_status"));
			stmt.setString(7,request.getParameter("url_entry"));
			stmt.setString(8,request.getParameter("url_service"));
			stmt.setString(9,request.getParameter("Mlog_campaign_name"));
			stmt.setString(10,request.getParameter("url_kb_base"));
			stmt.setInt(11,new Integer(request.getParameter("url_entry_pop")==null?"0":"1").intValue());
			stmt.setInt(12,new Integer(request.getParameter("url_service_pop")==null?"0":"1").intValue());
			stmt.setInt(13,new Integer(request.getParameter("url_status_pop")==null?"0":"1").intValue());
			stmt.setInt(14,new Integer(request.getParameter("url_kb_base_pop")==null?"0":"1").intValue());
            stmt.setInt(15,new Integer(request.getParameter("do_orderref_entry")==null?"0":"1").intValue());
            stmt.setString(16,request.getParameter("url_quick_ref"));
            stmt.setString(17,request.getParameter("ticketbox"));
            stmt.setString(18,request.getParameter("email_domain"));



			int rows = stmt.executeUpdate();
cxn.commit();

			reportAction=rows+" record added";

			



}catch(Throwable th)

{

	th.printStackTrace();

	%>

	<tr><td><font size=2 color=red>Error: <%= th.getMessage() %></font></td></tr>

	<%

}finally

{

	try{rs.close();}catch(Exception ex){}

	try{stmt.close();}catch(Exception ex){}

	try{cxn.close();}catch(Exception ex){}



}	
	}
	

	
if(null != reportAction)
{
%>
<TR><TD><font color=red><B><%=reportAction  %></B></font></TD></TR>
<%
}
	try{

	String sql = "select * from owd_client_callcenter where id="+id;

	cxn = ConnectionManager.getConnection();

			stmt = cxn.prepareStatement(sql);

		

			stmt.executeQuery();

			

			rs = stmt.getResultSet();

			

	if(rs.next())

	{

%>

<tr><td><font size=-1><FORM METHOD=POST ACTION=callcenter_edit.jsp>
<INPUT TYPE=HIDDEN NAME=id value=<%=rs.getString("id") %>>
<B>OWD Client Name : <SELECT NAME=client_fkey><%= ExtranetServlet.getClientSelector(request,"client_fkey",rs.getString("client_fkey")) %></SELECT><P>
Microlog Campaign Name : <INPUT TYPE=TEXT SIZE=50 NAME=Mlog_campaign_name VALUE="<%= rs.getString("Mlog_campaign_name") %>" ><P>
<INPUT TYPE=CHECKBOX NAME=do_entry VALUE=1 <%= (rs.getInt("do_entry") == 1?"CHECKED":"") %> >Use Order Entry URL : <INPUT TYPE=TEXT NAME=url_entry SIZE=100 VALUE="<%= rs.getString("url_entry") %>" >
<BR>&nbsp;&nbsp;<INPUT TYPE=CHECKBOX NAME=url_entry_pop VALUE=1 <%= (rs.getInt("url_entry_pop") == 1?"CHECKED":"") %> >Open in New Window<P>
<INPUT TYPE=CHECKBOX NAME=do_status VALUE=1 <%= (rs.getInt("do_status") == 1?"CHECKED":"") %> >Use Order Status URL : <INPUT TYPE=TEXT NAME=url_status SIZE=100 VALUE="<%= rs.getString("url_status") %>" >
<BR>&nbsp;&nbsp;<INPUT TYPE=CHECKBOX NAME=url_status_pop VALUE=1 <%= (rs.getInt("url_status_pop") == 1?"CHECKED":"") %> >Open in New Window<P>
<INPUT TYPE=CHECKBOX NAME=do_service VALUE=1 <%= (rs.getInt("do_service") == 1?"CHECKED":"") %> >Use Customer Service URL : <INPUT TYPE=TEXT NAME=url_service SIZE=100 VALUE="<%= rs.getString("url_service") %>" >
<BR>&nbsp;&nbsp;<INPUT TYPE=CHECKBOX NAME=url_service_pop VALUE=1 <%= (rs.getInt("url_service_pop") == 1?"CHECKED":"") %> >Open in New Window<P>
OWD Knowledgebase link : <INPUT TYPE=TEXT NAME=url_kb_base SIZE=100 VALUE="<%= rs.getString("url_kb_base") %>" >
<BR>&nbsp;&nbsp;<INPUT TYPE=CHECKBOX NAME=url_kb_base_pop VALUE=1 <%= (rs.getInt("url_kb_base_pop") == 1?"CHECKED":"") %> >Open in New Window<P>
Knowledgebase quick ref : <input type=text name=url_quick_ref size=100 value="<%=rs.getString("url_quick_ref")%>" ><P>
<input type=checkbox name=do_orderref_entry Value=1 <%= (rs.getInt("do_orderref_entry") ==1?"CHecked":"")%>  > Do order Reference Entry??  <P>
Announce Script<BR><TEXTAREA NAME=announce_script ROWS=10 COLS=80><%= htmlescape(rs.getString("announce_script")) %></TEXTAREA>

<P>
Fill out if Client does e-mail through MailFlow<br>
Ticketbox: <input type=text name=ticketbox size=100 value="<%=rs.getString("ticketbox")%>"><p>
E-mail domain <input type=text name=email_domain size=100 value="<%=rs.getString("email_domain")%>"><p>
<INPUT TYPE=SUBMIT NAME=ACTION VALUE="SAVE CHANGES"><INPUT TYPE=SUBMIT NAME=ACTION VALUE=DELETE>
</FORM></td></tr>

<%

	}else
	{
	%>
<tr><td><font size=-1><FORM METHOD=POST ACTION=callcenter_edit.jsp>
<font size=1>(Add New Screen Pop Entry)<font size=-1>
<B>Choose OWD Client Name : <SELECT NAME=client_fkey><%= ExtranetServlet.getClientSelector(request,"client_fkey","0") %></SELECT><P>
Microlog Campaign Name : <INPUT TYPE=TEXT SIZE=50 NAME=Mlog_campaign_name VALUE="" ><P>
For all URLS, type "&lt;AGENT_ID&gt;" without the quotes to insert the login id of the logged-in agent<P><INPUT TYPE=CHECKBOX NAME=do_entry VALUE=1 <%= ("") %> >Use Order Entry URL : <INPUT TYPE=TEXT NAME=url_entry SIZE=100 VALUE="" >
<BR>&nbsp;&nbsp;<INPUT TYPE=CHECKBOX NAME=url_entry_pop VALUE=1 <%= ("") %> >Open in New Window<P>
<INPUT TYPE=CHECKBOX NAME=do_status VALUE=1 <%= ("") %> >Use Order Status URL : <INPUT TYPE=TEXT NAME=url_status SIZE=100 VALUE="" >
<BR>&nbsp;&nbsp;<INPUT TYPE=CHECKBOX NAME=url_status_pop VALUE=1 <%= ("") %> >Open in New Window<P>
<INPUT TYPE=CHECKBOX NAME=do_service VALUE=1 <%= ("") %> >Use Customer Service URL : <INPUT TYPE=TEXT NAME=url_service SIZE=100 VALUE="" >
<BR>&nbsp;&nbsp;<INPUT TYPE=CHECKBOX NAME=url_service_pop VALUE=1 <%= ("") %> >Open in New Window<P>
OWD Knowledgebase link : <INPUT TYPE=TEXT NAME=url_kb_base SIZE=100 VALUE="" >
<BR>&nbsp;&nbsp;<INPUT TYPE=CHECKBOX NAME=url_kb_base_pop VALUE=1 <%= ("") %> >Open in New Window<P>
Knowledgebase quick ref : <input type=text name=url_quick_ref size=100 value=""><P>
<input type=checkbox name=do_orderref_entry Value=1> Do order Reference Entry??  <P>
Announce Script - (Type "&lt;AGENT_NAME&gt;" without the quotes to insert the first name of the logged-in agent)<BR><TEXTAREA NAME=announce_script ROWS=10 COLS=80></TEXTAREA>
<P>
Fill out if Client does e-mail through MailFlow<br>
hi Ticketbox: <INPUT TYPE="TEXT" NAME="ticketbox" SIZE="100" VALUE=""><P>
E-mail domain <input type="text" name="email_domain" size="100" value=""><P>
<INPUT TYPE=SUBMIT NAME=ACTION VALUE="SAVE NEW ENTRY">
</FORM></td></tr>
	<%
	}





}catch(Throwable th)

{

	th.printStackTrace();

	%>

	<tr><td colspan=6>Error: <%= th.getMessage() %></td></tr>

	<%

}finally

{

	try{rs.close();}catch(Exception ex){}

	try{stmt.close();}catch(Exception ex){}

	try{cxn.close();}catch(Exception ex){}



}

%>

</table>

</body>

</html>
<%!
	static Object[][] entities = {
		  // {"#39", new Integer(39)},       // ' - apostrophe
		   {"quot", new Integer(34)},      // " - double-quote
		   {"amp", new Integer(38)},       // & - ampersand
		   {"lt", new Integer(60)},        // < - less-than
		   {"gt", new Integer(62)},        // > - greater-than
		   {"nbsp", new Integer(160)},     // non-breaking space
		   {"copy", new Integer(169)},     // � - copyright
		   {"reg", new Integer(174)},      // � - registered trademark
		   {"Agrave", new Integer(192)},   // � - uppercase A, grave accent
		   {"Aacute", new Integer(193)},   // � - uppercase A, acute accent
		   {"Acirc", new Integer(194)},    // � - uppercase A, circumflex accent
		   {"Atilde", new Integer(195)},   // � - uppercase A, tilde
		   {"Auml", new Integer(196)},     // � - uppercase A, umlaut
		   {"Aring", new Integer(197)},    // ? - uppercase A, ring
		   {"AElig", new Integer(198)},    // � - uppercase AE
		   {"Ccedil", new Integer(199)},   // � - uppercase C, cedilla
		   {"Egrave", new Integer(200)},   // � - uppercase E, grave accent
		   {"Eacute", new Integer(201)},   // � - uppercase E, acute accent
		   {"Ecirc", new Integer(202)},    // � - uppercase E, circumflex accent
		   {"Euml", new Integer(203)},     // � - uppercase E, umlaut
		   {"Igrave", new Integer(204)},   // � - uppercase I, grave accent
		   {"Iacute", new Integer(205)},   // � - uppercase I, acute accent
		   {"Icirc", new Integer(206)},    // � - uppercase I, circumflex accent
		   {"Iuml", new Integer(207)},     // � - uppercase I, umlaut
		   {"ETH", new Integer(208)},      // ? - uppercase Eth, Icelandic
		   {"Ntilde", new Integer(209)},   // � - uppercase N, tilde
		   {"Ograve", new Integer(210)},   // � - uppercase O, grave accent
		   {"Oacute", new Integer(211)},   // � - uppercase O, acute accent
		   {"Ocirc", new Integer(212)},    // � - uppercase O, circumflex accent
		   {"Otilde", new Integer(213)},   // � - uppercase O, tilde
		   {"Ouml", new Integer(214)},     // � - uppercase O, umlaut
		   {"Oslash", new Integer(216)},   // � - uppercase O, slash
		   {"Ugrave", new Integer(217)},   // � - uppercase U, grave accent
		   {"Uacute", new Integer(218)},   // � - uppercase U, acute accent
		   {"Ucirc", new Integer(219)},    // � - uppercase U, circumflex accent
		   {"Uuml", new Integer(220)},     // � - uppercase U, umlaut
		   {"Yacute", new Integer(221)},   // ? - uppercase Y, acute accent
		   {"THORN", new Integer(222)},    // ? - uppercase THORN, Icelandic
		   {"szlig", new Integer(223)},    // � - lowercase sharps, German
		   {"agrave", new Integer(224)},   // � - lowercase a, grave accent
		   {"aacute", new Integer(225)},   // � - lowercase a, acute accent
		   {"acirc", new Integer(226)},    // � - lowercase a, circumflex accent
		   {"atilde", new Integer(227)},   // � - lowercase a, tilde
		   {"auml", new Integer(228)},     // � - lowercase a, umlaut
		   {"aring", new Integer(229)},    // � - lowercase a, ring
		   {"aelig", new Integer(230)},    // � - lowercase ae
		   {"ccedil", new Integer(231)},   // ? - lowercase c, cedilla
		   {"egrave", new Integer(232)},   // ? - lowercase e, grave accent
		   {"eacute", new Integer(233)},   // � - lowercase e, acute accent
		   {"ecirc", new Integer(234)},    // ? - lowercase e, circumflex accent
		   {"euml", new Integer(235)},     // � - lowercase e, umlaut
		   {"igrave", new Integer(236)},   // � - lowercase i, grave accent
		   {"iacute", new Integer(237)},   // � - lowercase i, acute accent
		   {"icirc", new Integer(238)},    // � - lowercase i, circumflex accent
		   {"iuml", new Integer(239)},     // � - lowercase i, umlaut
		   {"igrave", new Integer(236)},   // � - lowercase i, grave accent
		   {"iacute", new Integer(237)},   // � - lowercase i, acute accent
		   {"icirc", new Integer(238)},    // � - lowercase i, circumflex accent
		   {"iuml", new Integer(239)},     // � - lowercase i, umlaut
		   {"eth", new Integer(240)},      // ? - lowercase eth, Icelandic
		   {"ntilde", new Integer(241)},   // � - lowercase n, tilde
		   {"ograve", new Integer(242)},   // � - lowercase o, grave accent
		   {"oacute", new Integer(243)},   // � - lowercase o, acute accent
		   {"ocirc", new Integer(244)},    // � - lowercase o, circumflex accent
		   {"otilde", new Integer(245)},   // � - lowercase o, tilde
		   {"ouml", new Integer(246)},     // � - lowercase o, umlaut
		   {"oslash", new Integer(248)},   // � - lowercase o, slash
		   {"ugrave", new Integer(249)},   // ? - lowercase u, grave accent
		   {"uacute", new Integer(250)},   // � - lowercase u, acute accent
		   {"ucirc", new Integer(251)},    // � - lowercase u, circumflex accent
		   {"uuml", new Integer(252)},     // � - lowercase u, umlaut
		   {"yacute", new Integer(253)},   // ? - lowercase y, acute accent
		   {"thorn", new Integer(254)},    // ? - lowercase thorn, Icelandic
		   {"yuml", new Integer(255)},     // � - lowercase y, umlaut
		   {"euro", new Integer(8364)},    // Euro symbol
	   };
	   static Map e2i = new HashMap();
	   static Map i2e = new HashMap();
	   static {
		   for (int i=0; i<entities.length; ++i) {
			   e2i.put(entities[i][0], entities[i][1]);
			   i2e.put(entities[i][1], entities[i][0]);
		   }
	   }

	/**
		 * Turns funky characters into HTML entity equivalents<p>
		 * e.g. <tt>"bread" & "butter"</tt> => <tt>&amp;quot;bread&amp;quot; &amp;amp; &amp;quot;butter&amp;quot;</tt>.
		 * Update: supports nearly all HTML entities, including funky accents. See the source code for more detail.
		 *
		 **/
		public static String htmlescape(String s1)
		{
			StringBuffer buf = new StringBuffer();
			int i;
			for (i=0; i<s1.length(); ++i) {
				char ch = s1.charAt(i);
				String entity = (String)i2e.get( new Integer((int)ch) );
				if (entity == null) {
					if (((int)ch) > 128) {
						buf.append("&#" + ((int)ch) + ";");
					}
					else {
						buf.append(ch);
					}
				}
				else {
					buf.append("&" + entity + ";");
				}
			}
			return buf.toString();
		}

		/**
		 * Given a string containing entity escapes, returns a string
		 * containing the actual Unicode characters corresponding to the
		 * escapes.
		 *
		 * Note: nasty bug fixed by Helge Tesgaard (and, in parallel, by
		 * Alex, but Helge deserves major props for emailing me the fix).
		 * 15-Feb-2002 Another bug fixed by Sean Brown <sean@boohai.com>
		 *
		 *
		 **/
		public static String htmlunescape(String s1) {
			StringBuffer buf = new StringBuffer();
			int i;
			for (i=0; i<s1.length(); ++i) {
				char ch = s1.charAt(i);
				if (ch == '&') {
					int semi = s1.indexOf(';', i+1);
					if (semi == -1) {
						buf.append(ch);
						continue;
					}
					String entity = s1.substring(i+1, semi);
					Integer iso;
					if (entity.charAt(0) == '#') {
						iso = new Integer(entity.substring(1));
					}
					else {
						iso = (Integer)e2i.get(entity);
					}
					if (iso == null) {
						buf.append("&" + entity + ";");
					}
					else {
						buf.append((char)(iso.intValue()));
					}
					i = semi;
				}
				else {
					buf.append(ch);
				}
			}
			return buf.toString();
		}

%>