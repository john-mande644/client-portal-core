<html>
<head>
    <title>Treeview example</title>
    <script>
        function op() { //This function is used with folders that do not open pages themselves. See online docs.
        }
    </script>
</head>

<!--
(Please keep all copyright notices.)
This frameset document includes the Treeview script.
Script found in: http://www.treeview.net
Author: Marcelino Alves Martins
-->
<%


    request.getSession(true).setAttribute("treeinvoice", request.getParameter("invoice"));

%>
<FRAMESET cols="200,*">
    <FRAME src="demoLargeLeftFrame.jsp" name="treeframe">
    <FRAME SRC="demoLargeRightFrame.jsp" name="basefrm">
</FRAMESET>


</HTML>
