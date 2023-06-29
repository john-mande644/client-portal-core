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
	if("q3".equals(request.getParameter("src")))
	{
	data._19 = request.getParameter("19");
	data._20 = request.getParameter("20");
	data._21 = request.getParameter("21");
	data._18 = new java.util.ArrayList();
	String[] vals = request.getParameterValues("18");
	if(vals!=null)
	{
	for(int i=0;i<vals.length;i++)
	{
		data._18.add(vals[i]);
	}
	}

	data._18a = request.getParameter("18a");
	data._20a = request.getParameter("20a");

	data._22 = request.getParameter("22");
	data._23 = request.getParameter("23");
	data._24 = request.getParameter("24");
	data._25 = request.getParameter("25");
	data._25a = request.getParameter("25a");

	}

	if(data._20.length()<1 || data._20a.length()<1 ||
data._21.length()<1 ||data._23.length()<1 || data._24.length()<1
||data._25.length()<1 )
{
	errormessage = "In order to continue, all survey questions must be answered. Please answer any question still marked as \"Choose One\".";
}

if(data._21.equals("No") && data._22.length()<1)
{
	errormessage = "Please provide a suggested price for MasterWriter";
}


if(data._20.equals(data._20a))
{
	errormessage = "Please make sure you provide two different qualities to consider when purchasing software";
}

if(data._25.equals("No") && data._25a.trim().length()<1)
{
	errormessage = "Please provide a reason why you will not be purchasing MasterWriter";
}

if(data._18a.length()<1 && data._18.size()<1)
{
	errormessage = "Please indicate any other programs you use to write songs, or select \"None\"";
}

if((data._18a.length()>0 || (!data._18.contains("None"))) && data._19.length()<1)
{
	errormessage = "Please indicate which program you would compare MasterWriter to";
}
 	try
	{
			if(errormessage == null)
				data.saveOrderData();
	}catch(Exception ex)
	{
		errormessage = ex.getMessage();
	}
	if(errormessage != null)
	{
		request.setAttribute("errormessage",errormessage);
		request.getRequestDispatcher("q3.jsp").forward(request,response);
	}else
	{


%>
	<html>
<head>
<title>MasterWriter Survey</title>
</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="751" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr align="center">
    <td colspan="3"><img src="spacer.gif" width="751" height="23"></td>
  </tr>
  <tr align="center">
    <td colspan="3" align="left" height="0"><img src="mwart.jpg" width="126" height="334" align="left" vspace="0" hspace="0" border="0">
      <p align="left"><font face="Arial, Helvetica, sans-serif" size="2" color="#333333"><img src="spacer.gif" width="33" height="334" align="left" vspace="0" hspace="0" border="0"></font></p>
      <font color="#333333" size="4" face="Verdana, Arial, Helvetica, sans-serif"><center><b>MasterWriter Survey</b></center><HR></font>
        <font color="#333333" size="4" face="Verdana, Arial, Helvetica, sans-serif"><center>Thank you for taking the MasterWriter Survey!</center><P><BR><P><font size=2>You should receive an email shortly with the details about how to receive your Questionnaire Discount.<p><center><A HREF="http://www.masterwriter.com/">http://www.masterwriter.com/</A>
<%

String msg = "\nThank you for taking the time to take our Survey. If you decide to purchase MasterWriter be sure to use your Questionnaire Discount, which entitles you to purchase a copy of MasterWriter for $199.00. \n\nQuestionnaire Discount: 8125\n\nTo order: \nOnline: www.masterwriter.com \nPhone: 866-848-8484\n\nContact Information:\nAll Tech Support or Customer Service questions should be directed to MasterWriter. \n\nYou can contact us at:\n\nTech Support\nPhone: 805-892-2686\nEmail: support@masterwriter.com\n\nGeneral Info and Customer Service\nPhone: 805-892-2656\nEmail: info@masterwriter.com\n\nInstallations:\nYour purchase entitles you to two installs of MasterWriter. You may install the software on two Macs or two Windows machines or one Mac and one Windows machine. You may purchase additional installs for: $19.95 for 1 additional install or $29.95 for 2 additional installs. The additional installs however, must be registered to the same name.\n\nLimited time offer: Questionnaire Discount is valid until July 1st, 2005." ;
request.getSession(true).setAttribute("mwdata",null);
com.owd.core.Mailer.sendMail("MasterWriter Questionnaire",msg,data.email,"support@masterwriter.com");




%>

      <p>&nbsp;</p>
    </td>
  </tr>
</table>
</body>
</html>
<% } %>