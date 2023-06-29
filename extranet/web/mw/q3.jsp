<% 

com.masterwriter.MWData data = (com.masterwriter.MWData) request.getSession(true).getAttribute("mwdata");


String errormessage = null;

if (data == null)
{
	data = new com.masterwriter.MWData();
	request.getSession(true).setAttribute("mwdata",data);
	}
		if(data.fname == null)
	{
		data = new com.masterwriter.MWData();
	request.getSession(true).setAttribute("mwdata",data);

	}
	if("q2".equals(request.getParameter("src")))
	{
	data._01 = request.getParameter("01");
	data._02 = request.getParameter("02");
	data._03 = request.getParameter("03");
	data._04 = new java.util.ArrayList();
	String[] vals = request.getParameterValues("04");
	if(vals!=null)
	{
	for(int i=0;i<vals.length;i++)
	{
		data._04.add(vals[i]);
	}
	}
	
	data._05 = new java.util.ArrayList();
	
	vals = request.getParameterValues("05");
	if(vals!=null)
	{
	for(int i=0;i<vals.length;i++)
	{
		data._05.add(vals[i]);
	}
	}
	data._04a = request.getParameter("04a");
	data._05a = request.getParameter("05a");
	
	data._06 = request.getParameter("06");
	data._07 = request.getParameter("07");
	data._08 = request.getParameter("08");
	data._09 = request.getParameter("09");
	data._10 = request.getParameter("10");
	data._11 = request.getParameter("11");
	data._12 = request.getParameter("12");
	data._13 = request.getParameter("13");
	data._14 = request.getParameter("14");
	data._15 = request.getParameter("15");
	data._16 = request.getParameter("16");
	data._17 = request.getParameter("17");
	
	}
	
	if(data._01.length() < 1 || data._02.length() < 1 ||
	data._03.length()<1 || data._06.length()<1 ||
data._07.length()<1 || data._08.length()<1 ||data._09.length()<1 || data._10.length()<1 || data._12.length()<1  || data._16.length()<1)
{
	errormessage = "You must respond to all the survey questions before continuing!";
}

if(data._05a.length()<1 && data._05.size()<1)
{
	errormessage = "You must respond to all the survey questions before continuing!";
}

if(data._10.equals("Yes") && data._11.length()<1)
{
	errormessage = "Please indicate how helpful MasterWriter was when writing songs";
}

if(data._04a.length()<1 && data._04.size()<1)
{
	errormessage = "You must respond to all the survey questions before continuing!";
}

if(data._16.equals("Yes") && data._17.trim().length()<1)
{
	errormessage = "Please identify the Digital Recording or Sequence software you use";
}
	
	if(errormessage != null)
	{
		request.setAttribute("errormessage",errormessage);
		request.getRequestDispatcher("q2.jsp").forward(request,response);
	}else
	{
	
%>
	<html>
<head>
<title>MasterWriter Survey</title>
</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<FORM ACTION="final.jsp" METHOD="POST"><INPUT TYPE=HIDDEN NAME=src value=q3>
<table width="751" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr align="center"> 
    <td colspan="3"><img src="spacer.gif" width="751" height="23"></td>
  </tr>
  <tr align="center"> 
    <td colspan="3" align="left" height="0"><img src="mwart.jpg" width="126" height="334" align="left" vspace="0" hspace="0" border="0"> 
      <p align="left"><font face="Arial, Helvetica, sans-serif" size="2" color="#333333"><img src="spacer.gif" width="33" height="334" align="left" vspace="0" hspace="0" border="0"></font></p>
      <font color="#333333" size="4" face="Verdana, Arial, Helvetica, sans-serif"><center><b>MasterWriter Survey</b></center><HR></font>
        <font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><center>Page 2 of 2</center><P><BR><P>Please answer each of the following questions - the entire survey must be completed:<p>

<table border=0 cellspacing=10>



<TR><TD align=LEFT COLSPAN=2><font color="red" size="3" face="Verdana, Arial, Helvetica, sans-serif"><B><%= request.getAttribute("errormessage")!=null?(String)request.getAttribute("errormessage"):"" %></B></TD></TR>
<TR><TD align=RIGHT valign=TOP><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><B>&nbsp;Do you use any of the following programs when writing songs:<BR>(You can select more than one.)&nbsp;</B></TD><TD ALIGN=LEFT valign=BOTTOM><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif">
<INPUT TYPE=CHECKBOX NAME="18"  VALUE="None" <%= data._18.contains("None")?" CHECKED":""%>>None<BR>
<INPUT TYPE=CHECKBOX NAME="18" VALUE="Microsoft Word" <%= data._18.contains("Microsoft Word")?" CHECKED":""%>>Microsoft Word<BR>
<INPUT TYPE=CHECKBOX NAME="18" VALUE="Word Processor" <%= data._18.contains("Word Processor")?" CHECKED":""%>>Word Processor<BR>
<INPUT TYPE=CHECKBOX NAME="18" VALUE="Rhyme Wizard" <%= data._18.contains("Rhyme Wizard")?" CHECKED":""%>>Rhyme Wizard<BR>
<INPUT TYPE=CHECKBOX NAME="18" VALUE="A Zillion Kajillion Rhymes" <%= data._18.contains("A Zillion Kajillion Rhymes")?" CHECKED":""%>>A Zillion Kajillion Rhymes<BR>
<INPUT TYPE=CHECKBOX NAME="18" VALUE="Songster" <%= data._18.contains("Songster")?" CHECKED":""%>>Songster<BR>
<INPUT TYPE=CHECKBOX NAME="18" VALUE="Lyricist" <%= data._18.contains("Lyricist")?" CHECKED":""%>>Lyricist<BR>
<INPUT TYPE=CHECKBOX NAME="18" VALUE="Any online Dictionary" <%= data._18.contains("Any online Dictionary")?" CHECKED":""%>>Any online Dictionary<BR>
<BR><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif">Other:<BR><INPUT NAME="18a" VALUE="<%= data._18a %>"></TD></TR>
       
       <TR><TD align=RIGHT valign=TOP><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><B>&nbsp;Which of the software programs you are currently using would you compare MasterWriter to?&nbsp;</B></TD><TD ALIGN=LEFT valign=BOTTOM><INPUT TYPE=TEXT NAME="19" VALUE="<%= data._19 %>"></TD></TR>


<TR><TD align=RIGHT valign=TOP><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><B>&nbsp;What is the most important consideration when you decide to purchase 
       software:&nbsp;</B></TD><TD ALIGN=LEFT valign=BOTTOM><SELECT NAME="20">
<OPTION VALUE="" >Choose One
<OPTION VALUE="Price" <%= data._20.equals("Price")?" SELECTED":""%>>Price
<OPTION VALUE="Name Brand" <%= data._20.equals("Name Brand")?" SELECTED":""%>>Name Brand
<OPTION VALUE="Reviews" <%= data._20.equals("Reviews")?" SELECTED":""%>>Reviews
<OPTION VALUE="Word of Mouth" <%= data._20.equals("Word of Mouth")?" SELECTED":""%>>Word of Mouth
<OPTION VALUE="Features" <%= data._20.equals("Features")?" SELECTED":""%>>Features
</SELECT></TD></TR>

<TR><TD align=RIGHT valign=TOP><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><B>&nbsp;What is the next most important consideration:&nbsp;</B></TD><TD ALIGN=LEFT valign=BOTTOM><SELECT NAME="20a">
<OPTION VALUE="" >Choose One (not the same as your previous answer)
<OPTION VALUE="Price" <%= data._20a.equals("Price")?" SELECTED":""%>>Price
<OPTION VALUE="Name Brand" <%= data._20a.equals("Name Brand")?" SELECTED":""%>>Name Brand
<OPTION VALUE="Reviews" <%= data._20a.equals("Reviews")?" SELECTED":""%>>Reviews
<OPTION VALUE="Word of Mouth" <%= data._20a.equals("Word of Mouth")?" SELECTED":""%>>Word of Mouth
<OPTION VALUE="Features" <%= data._20a.equals("Features")?" SELECTED":""%>>Features
</SELECT></TD></TR>



<TR><TD align=RIGHT valign=TOP><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><B>&nbsp;Do you think MasterWriter is fairly priced to comparable software on the Market today:&nbsp;</B></TD><TD ALIGN=LEFT valign=BOTTOM><SELECT NAME="21">
<OPTION VALUE="" >Choose One
<OPTION VALUE="Yes" <%= data._21.equals("Yes")?" SELECTED":""%>>Yes
<OPTION VALUE="No" <%= data._21.equals("No")?" SELECTED":""%>>No
</SELECT></TD></TR>

<TR><TD align=RIGHT valign=TOP></TD><TD ALIGN=LEFT valign=BOTTOM><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><B>&nbsp;If you answered No, what would you consider a fair price?&nbsp;</B><BR><INPUT TYPE=TEXT NAME="22" VALUE="<%= data._22 %>"></TD></TR>




<TR><TD align=RIGHT valign=TOP><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><B>&nbsp;Where do you usually purchase software?&nbsp;</B></TD><TD ALIGN=LEFT valign=BOTTOM><SELECT NAME="23">
<OPTION VALUE="" >Choose One
<OPTION VALUE="Store" <%= data._23.equals("Store")?" SELECTED":""%>>Store
<OPTION VALUE="Online" <%= data._23.equals("Online")?" SELECTED":""%>>Online
</SELECT></TD></TR>

<TR><TD align=RIGHT valign=TOP><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><B>&nbsp;Where do you usually make music equipment purchases?&nbsp;</B></TD><TD ALIGN=LEFT valign=BOTTOM><SELECT NAME="24">
<OPTION VALUE="" >Choose One
<OPTION VALUE="Store" <%= data._24.equals("Store")?" SELECTED":""%>>Store
<OPTION VALUE="Online" <%= data._24.equals("Online")?" SELECTED":""%>>Online
</SELECT></TD></TR>


<TR><TD align=RIGHT valign=TOP><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><B>&nbsp;Are you planning to purchase MasterWriter?&nbsp;</B></TD><TD ALIGN=LEFT valign=BOTTOM><SELECT NAME="25">
<OPTION VALUE="" >Choose One
<OPTION VALUE="Yes" <%= data._25.equals("Yes")?" SELECTED":""%>>Yes
<OPTION VALUE="No" <%= data._25.equals("No")?" SELECTED":""%>>No
</SELECT></TD></TR>

<TR><TD align=RIGHT valign=TOP><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><B>&nbsp;If not, why?&nbsp;</B></TD><TD ALIGN=LEFT valign=BOTTOM><TEXTAREA NAME="25a" ROWS=5 COLS=30><%= data._25a %></TEXTAREA></TD></TR>


<TR><TD align=RIGHT valign=TOP></TD><TD align=RIGHT valign=TOP><INPUT TYPE=SUBMIT NAME="SUBMIT" VALUE="Finish the Survey"></TD></TR>
</TABLE>

      <p>&nbsp;</p>
    </td>
  </tr>
</table>
</FORM>
</body>
</html>
<% } %>