<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link href="<html:rewrite page="/css/intranet.css" />" rel="stylesheet" type="text/css">

    <title> OWD Intranet!!!!!!</title>
</head>

<body>
<div id="header"><p class="header">OWD Reprint App</p></div>
<div id="leftMenu">
    <jsp:include page="../adjustments/adjust/menu.jsp"/>
</div>
<html:form action="reprint" focus="orderNum">
    <div id="rightContent1">

        <div id="contentLeft">
           <span class="errors"><html:errors/></span>
            <html:text property="orderNum" size="10"/> <p></p>
            <html:submit property="submit"><bean:message key="button.loadReprint"/></html:submit>

        </div>
       <c:if test="${reprintForm.order!=null}">
        <div id="contentRight">



              <span class="outcome">  ${outcome}</span>
                <html:select name="reprintForm" property="printer">
                    <html:optionsCollection name="reprintForm" property="printerList" value="action" label="display"/>
                </html:select>
                <html:submit property="submit"><bean:message key="button.doReprint"/></html:submit><p></p>
                <span class="label">Order Num#: </span>${reprintForm.order.orderNum}<br>
                <span class="label">Order Ref#: </span>${reprintForm.order.orderRefnum}<br>
                <span class="label">Customer:</span> ${reprintForm.order.billFirstName}
                &nbsp${reprintForm.order.billLastName}<br>
                <span class="label">Company: </span>${reprintForm.order.billCompanyName}<br>
                <span class="label">Client:  </span>${reprintForm.order.client.companyName}



        </div>
       </c:if>

    </div>
</html:form>
</body>
</html>
