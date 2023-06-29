<%@ page import="com.owd.extranet.servlet.ExtranetServlet"%>
<%


    com.owd.extranet.servlet.ExtranetManager mgr = ExtranetServlet.getManager(request,response);
    ExtranetServlet.authenticateUser(request);
    request.setAttribute("clientselector","<FORM METHOD=POST ACTION=\""+ request.getContextPath() + ExtranetServlet.kExtranetURLPath +"\">"+ ExtranetServlet.getClientSelector(request) +"</FORM>");
    request.setAttribute("areatitle",ExtranetServlet.getSessionString(request,ExtranetServlet.kExtranetCurrMgr));
%>
<jsp:include page="/extranettop.jsp" />


<table border="0" cellpadding="0" cellspacing="0" width="100%" style="padding:10px;">
    <tbody><tr>
        <td rowspan="2" height="100%" nowrap="nowrap" valign="TOP">

            <table border="0" cellpadding="0" cellspacing="0" width="5%" style="padding-right:10px;">

                <tbody><tr><td nowrap="nowrap" style="border-right:2px;">

                    <table border="0" cellpadding="0" cellspacing="0" width="100%">
                        <tbody><tr bgcolor="#0065a2"><td style="padding:5px;color: rgb(255, 255, 255); font-family: myriad-pro,Verdana,Geneva,sans-serif; overflow: hidden; width: auto; background: none repeat scroll 0% 0% transparent; font-size: 15px; font-weight: bold;" align="LEFT" nowrap="nowrap" valign="CENTER">&nbsp;&nbsp;Activities&nbsp;&nbsp;</td></tr>
                        <tr><td nowrap="nowrap" style="padding-top:5px;"><span class="commandName">
<% String[] urls = mgr.getManagerActions(request); for(int i=0;i<urls.length;i++){   %>
&nbsp;&nbsp;<%= urls[i] %>&nbsp;&nbsp;<br />
<%   } %></span></TD></TR>



                    </TABLE>
                </TD>

                </TR>
            </TABLE>
        </TD>
    </TR></FORM>
    <TR><TD WIDTH=100%>
