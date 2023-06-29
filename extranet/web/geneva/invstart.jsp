<%@ taglib uri="struts/bean-el" prefix="bean" %>
<%@ taglib uri="struts/html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link href="<html:rewrite page="/css/geneva.css" />" rel="stylesheet" type="text/css">
    <link href="<html:rewrite page="/css/intranet.css" module=""/>" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="<html:rewrite page="/js/owd.js" />"></script>

    <title>Geneva Warehouse</title>
</head>

<body onload="toggleVisibility('throb','hidden','hidden','hidden')">
 <html:form action="warehouse">
 <div id="container">
   <div id=header>
     <div id="hcenter">
       <center>
           <html:link href="warehouse.do">
           <img src="<html:rewrite page="/images/orders.png"/>" alt="Orders" border=0/></html:link>
           <html:link href="inventory.do"><img src="<html:rewrite page="/images/inventory.png"/>" alt="Inventory" border=0/></html:link>
           <img src="<html:rewrite page="/images/Reports.png"/>" alt="Reports"/></center>
      <br>
         <ul>

<li><html:link action="/startReturn"  title="Enter Return Page">Enter Return</html:link></li>
<li><html:link action="/startAdjust"  title="Enter Adjustments Page">Enter Adjust</html:link></li>
    <li><html:link action="/viewAdjust"  title="Go to View Adjustments Page">View Adjust</html:link></li>
</ul>

     </div>
   </div>
   <div id="bottom">


   </div>
     </div>
<div id="throb"> <img src="<html:rewrite page="/images/indicator_white.gif"/>" alt="" /> </div>
 </html:form>
</body>
</html>