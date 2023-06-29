<% 

com.masterwriter.MWData data = (com.masterwriter.MWData) request.getSession(true).getAttribute("mwdata");


String errormessage = "";

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
	
	if("qmain".equals(request.getParameter("src")))
	{
	data.fname = request.getParameter("fname");
	data.lname = request.getParameter("lname");
	data.phone = request.getParameter("phone");
	data.email = request.getParameter("email");
	}
	
if(data.fname.length() < 1)
	errormessage = "A First Name entry is required";
if(data.lname.length() < 1)
	errormessage = "A Last Name entry is required";
//if(data.phone.length() < 10)
//	errormessage = "A phone number (with area code) entry is required";
if(data.email.length() < 1)
	errormessage = "An email address entry is required";


	if(errormessage.length()>0)
	{
        System.out.println("MW: got error:"+errormessage);
		request.setAttribute("errormessage",errormessage);
		request.getRequestDispatcher("qmain.jsp").forward(request,response);
	}else
	{
	
%>
	<html>
<head>
<title>MasterWriter Survey</title>
</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<FORM ACTION="q3.jsp" METHOD="POST"><INPUT TYPE=HIDDEN NAME=src value=q2>
<table width="751" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr align="center"> 
    <td colspan="3"><img src="spacer.gif" width="751" height="23"></td>
  </tr>
  <tr align="center"> 
    <td colspan="3" align="left" height="0"><img src="mwart.jpg" width="126" height="334" align="left" vspace="0" hspace="0" border="0"> 
      <p align="left"><font face="Arial, Helvetica, sans-serif" size="2" color="#333333"><img src="spacer.gif" width="33" height="334" align="left" vspace="0" hspace="0" border="0"></font></p>
      <font color="#333333" size="4" face="Verdana, Arial, Helvetica, sans-serif"><center><b>MasterWriter Survey</b></center><HR></font>
        <font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><center>Page 1 of 2</center><P><BR><P>Please answer each of the following questions - the entire survey must be completed:<p>

<table border=0 cellspacing=20>

<TR><TD align=LEFT COLSPAN=2><font color="red" size="3" face="Verdana, Arial, Helvetica, sans-serif"><B><%= request.getAttribute("errormessage")!=null?(String)request.getAttribute("errormessage"):"" %></B></TD></TR>
<TR><TD align=RIGHT valign=TOP><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><B>&nbsp;How would you describe yourself as a songwriter:&nbsp;</B></TD><TD ALIGN=LEFT valign=BOTTOM><SELECT NAME="01">
<OPTION VALUE="" >Choose One
<OPTION VALUE="Professional" <%= data._01.equals("Professional")?" SELECTED":""%>>Professional
<OPTION VALUE="Semi-Professional" <%= data._01.equals("Semi-Professional")?" SELECTED":""%>>Semi-Professional
<OPTION VALUE="It's a Serious Hobby" <%= data._01.equals("It\'s a Serious Hobby")?" SELECTED":""%>>It's a Serious Hobby
</SELECT></TD></TR>


<TR><TD align=RIGHT valign=TOP><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><B>&nbsp;How much time do you spend writing songs:&nbsp;</B></TD><TD ALIGN=LEFT valign=BOTTOM><SELECT NAME="02">
<OPTION VALUE="" >Choose One
<OPTION VALUE="More than 25 hours per week" <%= data._02.equals("More than 25 hours per week")?" SELECTED":""%>>More than 25 hours per week
<OPTION VALUE="Less than 25 hours per week" <%= data._02.equals("Less than 25 hours per week")?" SELECTED":""%>>Less than 25 hours per week
<OPTION VALUE="Less than 10 hours per week" <%= data._02.equals("Less than 10 hours per week")?" SELECTED":""%>>Less than 10 hours per week
</SELECT></TD></TR>


<TR><TD align=RIGHT valign=TOP><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><B>&nbsp;How would you rate your computer skills:&nbsp;</B></TD><TD ALIGN=LEFT valign=BOTTOM><SELECT NAME="03">
<OPTION VALUE="" >Choose One
<OPTION VALUE="Excellent" <%= data._03.equals("Excellent")?" SELECTED":""%>>Excellent
<OPTION VALUE="Fairly Proficient" <%= data._03.equals("Fairly Proficient")?" SELECTED":""%>>Fairly Proficient
<OPTION VALUE="Average" <%= data._03.equals("Average")?" SELECTED":""%>>Average
<OPTION VALUE="Below Average" <%= data._03.equals("Below Average")?" SELECTED":""%>>Below Average
</SELECT></TD></TR>


<TR><TD align=RIGHT valign=TOP><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><B>&nbsp;How did you hear about MasterWriter:&nbsp;</B></TD><TD ALIGN=LEFT valign=BOTTOM><select name="04"><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif">
<OPTION VALUE="">Select One Or Use Box Below
<OPTION VALUE="Magazine Ad or Review" <%= data._04.contains("Magazine Ad or Review")?" SELECTED":""%>>Magazine Ad or Review
<OPTION VALUE="BMI" <%= data._04.contains("BMI")?" SELECTED":""%>>BMI
<OPTION VALUE="NSAI" <%= data._04.contains("NSAI")?" SELECTED":""%>>NSAI
<OPTION VALUE="ASCAP" <%= data._04.contains("ASCAP")?" SELECTED":""%>>ASCAP
<OPTION VALUE="Word of Mouth" <%= data._04.contains("Word of Mouth")?" SELECTED":""%>>Word of Mouth
<OPTION VALUE="Google" <%= data._04.contains("Google")?" SELECTED":""%>>Google
</select><BR><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif">Other:<BR><INPUT NAME="04a" VALUE="<%= data._04a %>"></TD></TR>




<TR><TD align=RIGHT valign=TOP><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif">
<B>&nbsp;Do you read any of the following magazines:<BR>(You can select more than one.)&nbsp;</B></TD><TD ALIGN=LEFT valign=BOTTOM>
<font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif">
<INPUT TYPE=CHECKBOX NAME="05" VALUE="None" <%= data._05.contains("None")?" CHECKED":""%>>None<BR>

<INPUT TYPE=CHECKBOX NAME="05" VALUE="Guitar Player" <%= data._05.contains("Guitar Player")?" CHECKED":""%>>Guitar Player<BR>
<INPUT TYPE=CHECKBOX NAME="05" VALUE="Guitar One" <%= data._05.contains("Guitar One")?" CHECKED":""%>>Guitar One<BR>
<INPUT TYPE=CHECKBOX NAME="05" VALUE="Guitar World" <%= data._05.contains("Guitar World")?" CHECKED":""%>>Guitar World<BR>
<INPUT TYPE=CHECKBOX NAME="05" VALUE="Keyboard" <%= data._05.contains("Keyboard")?" CHECKED":""%>>Keyboard<BR>
<INPUT TYPE=CHECKBOX NAME="05" VALUE="Electronic Musician" <%= data._05.contains("Electronic Musician")?" CHECKED":""%>>Electronic Musician<BR>
<INPUT TYPE=CHECKBOX NAME="05" VALUE="EQ" <%= data._05.contains("EQ")?" CHECKED":""%>>EQ<BR>
<INPUT TYPE=CHECKBOX NAME="05" VALUE="Remix" <%= data._05.contains("Remix")?" CHECKED":""%>>Remix<BR>
<INPUT TYPE=CHECKBOX NAME="05" VALUE="Music Connection" <%= data._05.contains("Music Connection")?" CHECKED":""%>>Music Connection<BR>
<INPUT TYPE=CHECKBOX NAME="05" VALUE="Recording" <%= data._05.contains("Recording")?" CHECKED":""%>>Recording<BR>
<INPUT TYPE=CHECKBOX NAME="05" VALUE="Performing Songwriter" <%= data._05.contains("Performing Songwriter")?" CHECKED":""%>>Performing Songwriter<BR>
<font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif">Other:<BR><INPUT NAME="05a" VALUE="<%= data._05a %>"></TD></TR>


<TR><TD align=RIGHT valign=TOP><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><B>&nbsp;Do you use a computer when you write songs?&nbsp;</B></TD><TD ALIGN=LEFT valign=BOTTOM><SELECT NAME="06">
<OPTION VALUE="" >Choose One
<OPTION VALUE="Yes" <%= data._06.equals("Yes")?" SELECTED":""%>>Yes
<OPTION VALUE="No" <%= data._06.equals("No")?" SELECTED":""%>>No
</SELECT></TD></TR>

<TR><TD align=RIGHT valign=TOP><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><B>&nbsp;Do you use printed reference dictionaries (Rhyming, Phrases, Thesaurus) when you write songs?&nbsp;</B></TD><TD ALIGN=LEFT valign=BOTTOM><SELECT NAME="07">
<OPTION VALUE="" >Choose One
<OPTION VALUE="Yes" <%= data._07.equals("Yes")?" SELECTED":""%>>Yes
<OPTION VALUE="No" <%= data._07.equals("No")?" SELECTED":""%>>No
</SELECT></TD></TR>

<TR><TD align=RIGHT valign=TOP><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><B>&nbsp;Have you viewed the 18-minute Flash Demo on MasterWriter?&nbsp;</B></TD><TD ALIGN=LEFT valign=BOTTOM><SELECT NAME="08">
<OPTION VALUE="" >Choose One
<OPTION VALUE="Yes" <%= data._08.equals("Yes")?" SELECTED":""%>>Yes
<OPTION VALUE="No" <%= data._08.equals("No")?" SELECTED":""%>>No
</SELECT></TD></TR>

<TR><TD align=RIGHT valign=TOP><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><B>&nbsp;How much time have you spent using the program:&nbsp;</B></TD><TD ALIGN=LEFT valign=BOTTOM><SELECT NAME="09">
<OPTION VALUE="" >Choose One
<OPTION VALUE="More than 10 hours" <%= data._09.equals("More than 10 hours")?" SELECTED":""%>>More than 10 hours
<OPTION VALUE="Less than 10 hours" <%= data._09.equals("Less than 10 hours")?" SELECTED":""%>>Less than 10 hours
<OPTION VALUE="Less than 2 hours" <%= data._09.equals("Less than 2 hours")?" SELECTED":""%>>Less than 2 hours
</SELECT></TD></TR>




<TR><TD align=RIGHT valign=TOP><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><B>&nbsp;Have you written any songs using MasterWriter?&nbsp;</B></TD><TD ALIGN=LEFT valign=BOTTOM><SELECT NAME="10">
<OPTION VALUE="" >Choose One
<OPTION VALUE="Yes" <%= data._10.equals("Yes")?" SELECTED":""%>>Yes
<OPTION VALUE="No" <%= data._10.equals("No")?" SELECTED":""%>>No
</SELECT></TD></TR>

<TR><TD align=RIGHT valign=TOP><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><B>&nbsp;If you answered yes to the last question, please indicate how helpful MasterWriter was:&nbsp;</B></TD><TD ALIGN=LEFT valign=BOTTOM><SELECT NAME="11">
<OPTION VALUE="" >Choose One
<OPTION VALUE="Very Helpful" <%= data._11.equals("Very Helpful")?" SELECTED":""%>>Very Helpful
<OPTION VALUE="Fairly Helpful" <%= data._11.equals("Fairly Helpful")?" SELECTED":""%>>Fairly Helpful
<OPTION VALUE="Of little help" <%= data._11.equals("Of little help")?" SELECTED":""%>>Of little help
</SELECT></TD></TR>



<TR><TD align=RIGHT valign=TOP><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><B>&nbsp;Did you find MasterWriter was:&nbsp;</B></TD><TD ALIGN=LEFT valign=BOTTOM><SELECT NAME="12">
<OPTION VALUE="" >Choose One
<OPTION VALUE="Very Easy To Use" <%= data._12.equals("Very Easy To Use")?" SELECTED":""%>>Very Easy To Use
<OPTION VALUE="Fairly Easy To Use" <%= data._12.equals("Fairly Easy To Use")?" SELECTED":""%>>Fairly Easy To Use
<OPTION VALUE="Difficult To Use" <%= data._12.equals("Difficult To Use")?" SELECTED":""%>>Difficult To Use
</SELECT></TD></TR>


<TR><TD align=RIGHT valign=TOP><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><B>&nbsp;What are your favorite features in MasterWriter?&nbsp;</B></TD><TD ALIGN=LEFT valign=BOTTOM><TEXTAREA NAME="13" ROWS=5 COLS=30><%= data._13 %></TEXTAREA></TD></TR>


<TR><TD align=RIGHT valign=TOP><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><B>&nbsp;Is there anything you didn't like about MasterWriter?&nbsp;</B></TD><TD ALIGN=LEFT valign=BOTTOM><TEXTAREA NAME="14" ROWS=5 COLS=30><%= data._14 %></TEXTAREA></TD></TR>


<TR><TD align=RIGHT valign=TOP><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><B>&nbsp;List any features you would like to see added or improved on in MasterWriter:&nbsp;</B></TD><TD ALIGN=LEFT valign=BOTTOM><TEXTAREA NAME="15" ROWS=5 COLS=30><%= data._15 %></TEXTAREA></TD></TR>


<TR><TD align=RIGHT valign=TOP><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><B>&nbsp;Do you use any Digital Recording or Sequencing Software?&nbsp;</B></TD><TD ALIGN=LEFT valign=BOTTOM><SELECT NAME="16">
<OPTION VALUE="" >Choose One
<OPTION VALUE="Yes" <%= data._16.equals("Yes")?" SELECTED":""%>>Yes
<OPTION VALUE="No" <%= data._16.equals("No")?" SELECTED":""%>>No
</SELECT></TD></TR>


<TR><TD align=RIGHT valign=TOP><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><B>&nbsp;If you answered yes to the last question, which one?&nbsp;</B></TD><TD ALIGN=LEFT valign=BOTTOM><INPUT TYPE=TEXT NAME="17" VALUE="<%= data._17 %>"></TD></TR>



<TR><TD align=RIGHT valign=TOP></TD><TD align=RIGHT valign=TOP><INPUT TYPE=SUBMIT NAME="SUBMIT" VALUE="Continue"></TD></TR>
</TABLE>

      <p>&nbsp;</p>
    </td>
  </tr>
</table>
</FORM>
</body>
</html>
<% } %>