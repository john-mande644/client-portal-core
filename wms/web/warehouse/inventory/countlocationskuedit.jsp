<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<link href="<html:rewrite page="/warehouse/warehouse.css" />" rel="stylesheet" type="text/css" media="screen">




<body>
<html:form action="/loadCountLocation">
<html:hidden property="id"/>
<html:hidden property="location"/>
<html:submit value="Done"/>
</html:form>
<c:if test='${outcome != null}'>
<font color='blue' size="+2">
<b>${outcome}</b>
</font>
<hr>
</c:if>
<div id="header"><span class="label">Editing Inventory Id ${listOfCountEditForms.invId} for Location ${listOfCountEditForms.location}</span></div>
<html:errors/>
<div class="content">
<html:form action="/locationCountSave">
	<table border="0" class="invCount">

		<tr><th>Quanity</th><th>Who</th><th>Delete</th></tr>
		<c:forEach var="formItems" items="${requestScope.listOfCountEditForms.formItems}" varStatus="itemStatus">
			<tr>

				<td><html:text name="formItems" property="quanity" indexed="true" size="10"/>
                  <html:hidden name="formItems" property="itemId" indexed="true"/> </td>
                <td>${formItems.who}</td>
                <td><center><html:checkbox name="formItems" property="delete" value="yes" indexed="true"/></center></td>
			</tr>

		</c:forEach>
        <tr><td colspan="3"><center><html:submit title="Order Products"/></center></td></tr>
        </table>

        <html:hidden  property="id"/>
        <html:hidden  property="invId"/>
        <html:hidden  property="location"/>
       
     </html:form>




</div>

</body>
