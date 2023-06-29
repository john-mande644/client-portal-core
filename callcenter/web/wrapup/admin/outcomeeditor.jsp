<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<html>
<body onload="toggleVisibility('throb','hidden','hidden','hidden')">
<link href="<html:rewrite page="/css/intranet.css" module=""/>" rel="stylesheet" type="text/css">


<script type="text/javascript" src="<html:rewrite page="/js/Ajax.js" module=""/>"></script>


<div id="header"><p class="header">Callcenter Outcome Changes</p></div>

<div id="cccontent">
   <span id="errors"> <span class="errors">${errors}</span></span>
    <html:form action="loadClientOutcome">
        <html:select name="outcomeForm" property="campaign" title="Select Campaign to Edit"
                     onchange="javascript:retrieveURLt('/callcenter/loadClientOutcome.do?test=hi','outcomeForm');" styleClass="outcomeform2">
            <html:optionsCollection name="outcomeForm" property="clientList" value="action"
                                    label="display"/></html:select> Select Client to edit.

        <br>
    <span id="calltype">
   <table>
       <c:if test="${null!=outcomeForm.callTypeList}">
           <th colspan="2">Call Type</th>

           <tr>
               <td rowspan="5">
                   <html:select name="outcomeForm" property="type" multiple="true"
                                onchange="javascript:retrieveURLt('/callcenter/loadOutcome.do?test=hi','outcomeForm');"
                                title="Select Call Type to Add Outcome to or Remove" styleClass="outcomeform2" size="${outcomeForm.numOfCallType}">
                       <html:optionsCollection name="outcomeForm" property="callTypeList" value="action"
                                               label="display"/></html:select></td>

           <tr><td><html:text property="typeAdd" size="20" styleClass="outcomeform2"/><html:link
                   href="javascript:retrieveURLt('/callcenter/addClientItem.do?test=hi','outcomeForm');">
               Add</html:link></td></tr>
              </c:if>


               <c:if test="${null!=outcomeForm.outcomeList}">
               <tr><td><html:link href="javascript:retrieveURLt('/callcenter/removeCallType.do?test=hi','outcomeForm');" title="Click here to remove selected Call Type, all Outcomes will also be removed">
                                 Remove</html:link></td></tr>

               <tr><td><html:text property="defaultText" size="20" styleClass="outcomeform2"/>          <html:link
                                                                       href="javascript:retrieveURLt('/callcenter/changeDefaultText.do?test=hi','outcomeForm');">
                             Update Default Notes</html:link></td></tr>
                             <tr><td>Order Number Entry:
                         <html:checkbox name="outcomeForm" property="doOrder" value="1" title="Do Order" onclick="javascript:retrieveURLt('/callcenter/doOrder.do?test=hi','outcomeForm');"></html:checkbox>
                           </td></tr>









       <tr><td colspan="2" align="center"><label class="label">Outcome</label></td></tr>
           <tr><td rowspan="2">
               <html:select name="outcomeForm" property="outcome" multiple="true" title="Select Outcome to Remove" styleClass="outcomeform2" size="${outcomeForm.numOfOutcomes}">
                   <html:optionsCollection name="outcomeForm" property="outcomeList" value="action"
                                           label="display"/></html:select></td>
               <td><html:link href="javascript:retrieveURLt('/callcenter/removeOutcome.do?test=hi','outcomeForm');" title="Click here to remove selected Outcome">
                   Remove</html:link></td></tr>
           <tr><td><html:text property="outcomeAdd" size="20" styleClass="outcomeform2"/><html:link
                   href="javascript:retrieveURLt('/callcenter/addOutcome.do?test=hi','outcomeForm');">Add</html:link></td>
           </tr>
       </c:if>
   </table>

    </span>
    </html:form>



</div>
 <div id="throb"> <img src="<html:rewrite page="/images/indicator_white.gif" module=""/>" alt="" /> </div>
</body>
</html>