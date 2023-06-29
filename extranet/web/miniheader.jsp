<%@ taglib prefix="s" uri="/struts-tags" %>
<table width="100%" padding="0">
    <tr ><td><img src="<s:url value="/images/owdlogo.jpg" />" alt="" /></td>
        <td align="center" style="text-align:center;" width="100%">
            <s:if test="%{context.internalUser}">
                <s:form action="changeClient" namespace="/internal" method="post">
                    <s:hidden name="redirectAction" value="%{#request['redirectAction']}"/>
                    <s:hidden name="redirectNamespace" value="%{#request['redirectNamespace']}"/>
                    <s:select theme="simple" label="" name="changeClientId"
                              value="%{context.currentClient.clientId}" list="context.clients" listKey="clientId" listValue="companyName"
                              headerKey="0" headerValue="Any Client" onchange="submit()"/>
                </s:form>
               </s:if>
            <s:else><span style="background-color:lightgreen;font-size:2em;font-weight:bold;border-width:1px;border:solid black;padding:10px;display:block;margin-left:10px;margin-right:10px"><s:property  value="context.currentClient.companyName"/></span></s:else>
        </td>
        <td style="text-align:right;white-space:nowrap;">
            Welcome <s:property value="%{#session['extranet.authlogin']}" />!<br>
            <a style="font-size:1em;font-weight:bold;" href="<s:url value="/clienttools" />">Back to Extranet</a><br>
            <a style="font-size:1em;font-weight:bold;" href="<s:url action="logout"></s:url>">Logout</a>
        </td>
    </tr>
</table>
<hr height="5em"/>