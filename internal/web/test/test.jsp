<%@ page import="java.sql.ResultSet,
                 com.owd.hibernate.HibernateSession,
                 java.util.List,
                 java.util.ArrayList,
                 java.net.URLEncoder,
                 org.apache.commons.beanutils.RowSetDynaClass,
                 java.util.Iterator" %>
<%
    String clientID = request.getParameter("currClientID");
    String clientName = request.getParameter("currClientName");
    String urlToPostSKUTo = request.getParameter("urlToPostSKUTo");

    if (urlToPostSKUTo == null) {
        urlToPostSKUTo = "";
    }

    if (clientID == null) {

        clientID = "55";
        clientName = "OWD Internal";
    }


%>
<html>
<head>
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Expires" CONTENT="-1">
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
    <style type="text/css">
        body {
            font-family: Tahoma, Verdana;
            font-size: 11px;
        }
    </style>
    <script language=JavaScript>

        var ModalDialogWindow;
        var ModalDialogInterval;
        var ModalDialog = new Object;

        ModalDialog.value = '';
        ModalDialog.eventhandler = '';


        function ModalDialogMaintainFocus()
        {
            try
            {
                if (ModalDialogWindow.closed)
                {
                    window.clearInterval(ModalDialogInterval);
                    eval(ModalDialog.eventhandler);
                    return;
                }
                ModalDialogWindow.getObject("q").focus();
            }
            catch (everything) {
            }
        }

        function ModalDialogRemoveWatch()
        {
            ModalDialog.value = '';
            ModalDialog.eventhandler = '';
        }

        function ModalDialogShow(Title, pageURL, EventHandler)
        {

            ModalDialogRemoveWatch();
            ModalDialog.eventhandler = EventHandler;

            var args = 'width=400,height=500,left=325,top=300,toolbar=0,';
            args += 'location=0,status=0,menubar=0,scrollbars=1,resizable=1';

            ModalDialogWindow = window.open(pageURL, "", args);

            ModalDialogWindow.focus();
            ModalDialogInterval = window.setInterval("ModalDialogMaintainFocus()", 30);

        }

    </script>

    <script language=JavaScript>


        function lookupSKU(BodyText, EventHandler)
        {
            ModalDialogShow("SKU Finder", BodyText, EventHandler);
        }


        function lookupValueSet()
        {
            document.getElementById('modalreturn1').value = ModalDialog.value;
            ModalDialogRemoveWatch();
        }


    </script>


<BODY>

<table border=1 cellpadding=2 cellspacing=2 align=center width="60%">
    <tr><td align=left></td></tr>
    <tr><td align=left></td></tr>
    <tr><td align=left></td></tr>
    <tr>
        <td align=left><a
                href="javascript:lookupSKU('http://stewart.owd.com:8080/internal/test/index.jsp','lookupValueSet()');">Lookup
            SKU</a>
            1. <input type=text id=modalreturn1 name=modalreturn1 value=''></td>
    </tr>


</table>

</BODY>
</HTML>