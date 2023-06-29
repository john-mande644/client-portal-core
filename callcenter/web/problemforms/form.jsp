<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<html>
<head>
<title>OWD Problem Form</title>
 <style>


    INPUT {
        font: 11pt Verdana;
        color: #000000;

    }

    P {
        font: 11pt Verdana;
        color: #000000;
    }

    td {
        font: 11pt Verdana;
        color: #222222;
    }
     th {
        font: bold 14pt Verdana;
        color: #222222;

    }

    A {
        font: 8pt Verdana;
        color: #364FA7;
    }

    A:hover	{
        text-decoration: none;
        color:#4F6D8F;
    }

    .tableBorder1 {
        Border-top: 2px Solid #AAAADD;
        border-right: 2px Solid #AAAADD;
        border-bottom: 2px Solid #AAAADD;
        border-left: 2px Solid #AAAADD;
         padding:5px;
    }

    .tableBorder2 {
        Border-top: 2px Solid #AADDAA;
        border-right: 2px Solid #AADDAA;
        border-bottom: 2px Solid #AADDAA;
        border-left: 2px Solid #AADDAA;
          padding:5px;
    }

td.pfr {
    vertical-align: top;
    text-align: right;
    font-weight:bold;
}

 pff {
    vertical-align: top;
    text-align: left;
    font-weight:bold;
}

     span.errors {
    font-weight: bold;
    color: red;
}


    </style></head><body>
<div id=divver>
<html:form action="problemForm" >
    <html:hidden property="callId"/>
    <html:hidden property="client"/>

    ${callId}
<table class="tableBorder2">
    <tr bgcolor="#AADDAA" valign="top"><th colspan=2>CSR Problem Form (${problemFormForm.client})</th></tr>
    <span class="errors"><html:errors/></span>
     <tr>
        <td colspan=2 align=center><p>&nbsp;</p><b>What type of issue is this?</b>
        <br><html:select  property="type">
            <html:option value="">Choose the type of issue being reported</html:option>
            <html:option value="MDDWI">Missing/Damaged/Defective/Wrong Items</html:option>

<html:option value="OI">Order Inquiry (Lost Order, Tracking Info, Status)</html:option>
<html:option value="CRRE">Cancel/Refund/Return/Exchange (PPS Request)</html:option>
<html:option value="PI">Product Inquiry</html:option>
<html:option value="CSI">Client System Issues (Website, Accounts, Logins, Discount Error)</html:option>
<html:option value="CR">Contact Request (Invoice/Confirmation Email, Marketing/Sales)</html:option>

<html:option value="MISC">Misc. Other</html:option>
            </html:select>
         
            </td>
    </tr>
    <tr>
        <td colspan=2 align=center><p>&nbsp;</p><b>What's the Problem?</b>
        <br><html:text property="subject" size="50" /><br><font size=-1>
        Provide a one-line brief summary of the issue
        </font>
            </td>
    </tr>

    <tr><td colspan=2 align=center><p>&nbsp;</p><table  class="tableBorder1">
         <tr bgcolor="#AAAADD" valign="top">
                        <th align="center" colspan=2>Customer/Caller</th>

                        </tr>
    <tr>
        <td class="pfr">Name:</td>
        <td class="pf"><html:text property="name" size="30"styleClass="pff"/> </td>
    </tr>
    <tr>
        <td class="pfr">E-Mail:</td>
        <td class="pf"><html:text property="email" size="30"styleClass="pff"/> </td>
    </tr>
      <tr>
        <td class="pfr">Phone Number:</td>
        <td class="pf"><html:text property="phone" size="30"styleClass="pff"/> </td>
    </tr>
    <tr>
        <td class="pfr">Account Password (if applicable):</td>
        <td class="pf"><html:text property="password" size="30"styleClass="pff"/> </td>
    </tr>
      <tr>
        <td class="pfr">OWD Order#:</td>
        <td class="pf"><html:text property="owdOrder" size="30"styleClass="pff"/> </td>
    </tr>
    <tr>
        <td class="pfr">Client Reference#:</td>
        <td class="pf"><html:text property="clientReference" size="30"styleClass="pff"/> </td>
    </tr>
     <tr>
        <td class="pfr">Contact Method:</td>
        <td class="pf"><html:select  property="contactMethod">
         <html:option value="">Select Contact Method</html:option>
          <html:option value="Email">E-Mail</html:option>
             <html:option value="Phone">Phone</html:option>
        </html:select>
        </td>
    </tr>
    </table>
        </td>
    </tr>

    <%--<tr>
        <td class="pfr">Select Action Requested</td>
        <td class="pf">
            <html:select name="problemFormForm" property="issue"
               title="Selectissue" styleClass="pff">
            <html:optionsCollection name="problemFormForm" property="issueList" value="action"
              label="display"/></html:select> </td>
    </tr>--%>
    <tr>
         <td colspan=2 align=center><p>&nbsp;</p><b>What happened?</b>
        <br><html:textarea property="issue" cols="100" rows="6"styleClass="pff"/><br><font size=-1>
        Describe the history of the problem and what is being reported to you, or that you found out through research.<br>Include dates, actual SKUs, etc.
        </font>
            </td>
    </tr>
    <tr>
         <td colspan=2 align=center><p>&nbsp;</p><b>What happens next?</b>
        <br><html:textarea property="request" cols="100" rows="6"styleClass="pff"/><br><font size=-1>
Describe what is being requested or what the next steps are to resolve this:<br>Include new address, payment info, actual SKUs, etc.        </font>
            </td>
    </tr>
     <tr>
         <td colspan=2 align=center><p>&nbsp;</p><b>Why not you?</b>
        <br><html:textarea property="needs" cols="100" rows="6"styleClass="pff"/><br><font size=-1>
       Explain why you are unable to handle this issue yourself and resolve it immediately.
        </font>
            </td>
    </tr>

     <tr>
         <td colspan=2 align=center><p>&nbsp;</p>   <html:checkbox property="high" value="yes" />Mark this request as high priority!<p>&nbsp;<hr></p>
    <html:submit property="submit"><bean:message key="button.problemForm"/></html:submit>

            </td>
    </tr>
</table>


</html:form>

</div>

</body>
</html>
