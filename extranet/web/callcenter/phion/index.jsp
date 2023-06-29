<%@ page import="
java.util.Iterator,
java.util.List"
%>

<%
	 List findResults = (List) request.getAttribute("findresults");


%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>  <link REL="stylesheet" HREF="/webapps/owd.css" TYPE="text/css"><HEAD><TITLE>PhIon Wholesale Order Form</TITLE>
<BODY bgColor=#ffffff>     <P><font color=red><B><%= (request.getAttribute("errormessage")!=null?request.getAttribute("errormessage"):"") %></B></font>
<P>
<B>Step 1: Identify Customer</B>   <P>
I'll need to pull up your customer account information. May I...<P>
<B>To CSR: You must try a search by zip code first. Only after a search has been attempted will a button to add a new customer appear. Please make sure you have absolutely determined that the caller does not have an existing record before adding a new customer.
<P>
<OL><LI><FORM METHOD=POST ACTION="phion.jsp?action=findcustomer" ><INPUT TYPE=HIDDEN NAME=findcustomertype VALUE=zip >...get the zip code of your location?<INPUT TYPE=TEXT NAME=findcriteria VALUE="">&nbsp;<INPUT TYPE=SUBMIT NAME=Find VALUE=Find ><BR>
<font size=-2>Search will match all names beginning with the entered numbers. Zip codes are numbers only.<BR>If the zip code is 9 digits, put a dash between the first 5 and last 4 characters (e.g., 90045-1932)</font></FORM>
<LI><FORM METHOD=POST ACTION="phion.jsp?action=findcustomer" ><INPUT TYPE=HIDDEN NAME=findcustomertype VALUE=state >...get the state where you are located?
<SELECT name=findcriteria>
    <OPTION VALUE="">Click to Select
	<OPTION VALUE="AL">Alabama
	<OPTION VALUE="AK">Alaska
	<OPTION VALUE="AZ">Arizona
	<OPTION VALUE="AR">Arkansas
	<OPTION VALUE="CA">California
	<OPTION VALUE="CO">Colorado
	<OPTION VALUE="CT">Connecticut
	<OPTION VALUE="DE">Delaware
	<OPTION VALUE="DC">District of Columbia
	<OPTION VALUE="FL">Florida
	<OPTION VALUE="GA">Georgia
	<OPTION VALUE="HI">Hawaii
	<OPTION VALUE="ID">Idaho
	<OPTION VALUE="IL">Illinois
	<OPTION VALUE="IN">Indiana
	<OPTION VALUE="IA">Iowa
	<OPTION VALUE="KS">Kansas
	<OPTION VALUE="KY">Kentucky
	<OPTION VALUE="LA">Louisiana
	<OPTION VALUE="ME">Maine
	<OPTION VALUE="MD">Maryland
	<OPTION VALUE="MA">Massachusetts
	<OPTION VALUE="MI">Michigan
	<OPTION VALUE="MN">Minnesota
	<OPTION VALUE="MS">Mississippi
	<OPTION VALUE="MO">Missouri
	<OPTION VALUE="MT">Montana
	<OPTION VALUE="NE">Nebraska
	<OPTION VALUE="NV">Nevada
	<OPTION VALUE="NH">New Hampshire
	<OPTION VALUE="NJ">New Jersey
	<OPTION VALUE="NM">New Mexico
	<OPTION VALUE="NY">New York
	<OPTION VALUE="NC">North Carolina
	<OPTION VALUE="ND">North Dakota
	<OPTION VALUE="OH">Ohio
	<OPTION VALUE="OK">Oklahoma
	<OPTION VALUE="OR">Oregon
	<OPTION VALUE="PA">Pennsylvania
	<OPTION VALUE="RI">Rhode Island
	<OPTION VALUE="SC">South Carolina
	<OPTION VALUE="SD">South Dakota
	<OPTION VALUE="TN">Tennessee
	<OPTION VALUE="TX">Texas
	<OPTION VALUE="UT">Utah
	<OPTION VALUE="VT">Vermont
	<OPTION VALUE="VA">Virginia
	<OPTION VALUE="WA">Washington
	<OPTION VALUE="WV">West Virginia
	<OPTION VALUE="WI">Wisconsin
	<OPTION VALUE="WY">Wyoming</SELECT>&nbsp;<INPUT TYPE=SUBMIT NAME=Find VALUE=Find ></FORM>
<%
	if(request.getParameter("action") != null)
	{
%><LI><FORM METHOD=POST ACTION="phion.jsp?action=pickcustomer" ><B>If a record cannot be found:&nbsp;<INPUT TYPE=SUBMIT NAME=Find VALUE="New Customer" ><BR>
<%
	}

%></OL>

<%
	if(findResults != null)
	{
		%><TABLE WIDTH=100%><HR>Found Customers<HR> <%
		Iterator it = findResults.iterator();
		while(it.hasNext())
		{

%><TR WIDTH=100%><%= (String) it.next() %></TR><%
		}
		%></TABLE ><%
	}


%>
<HR>
</BODY></HTML>
