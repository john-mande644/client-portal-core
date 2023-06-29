<%@ page import="com.owd.extranet.servlet.ExtranetServlet" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    ExtranetServlet.authenticateUser(request);
    request.setAttribute("clientselector","<FORM METHOD=POST ACTION=\""+ request.getContextPath() + "/webapps/billing/accountStatus.action" +"\">"+ ExtranetServlet.getClientSelector(request) +"</FORM>");
    request.setAttribute("areatitle","Account Status");
    request.setAttribute("headinserts","<script>var dojoConfig = {async: true}</script><script src=\"//ajax.googleapis.com/ajax/libs/dojo/1.8.0/dojo/dojo.js\"></script>");

%>
<jsp:include page="/extranettop.jsp" />
<script>require(["dojo/fx/Toggler", "dojo/dom", "dojo/on", "dojo/domReady!"],

        function(Toggler, dom, on){

             alert("hello");
            rshhFlipdown.style.display = 'none';
            rshhFlipup.style.display='block';

            rshFlipdown.style.display = 'none';
            rshFlipup.style.display='block';

            on.emit(rshFlipdown,'click', {
                cancelable: true,
                bubbles: true
            });

            on.emit(rshFlipup,'click', {
                cancelable: true,
                bubbles: true
            });

            on.emit(rshhFlipdown,'click', {
                cancelable: true,
                bubbles: true
            });

            on.emit(rshhFlipup,'click', {
                cancelable: true,
                bubbles: true
            });

            on(dom.byId("rshFlipdown"), "click", function(e){
                recentServiceHistory.style.display='none';
                rshFlipdown.style.display = 'none';
                rshFlipup.style.display='block';
            });
            on(dom.byId("rshFlipup"), "click", function(e){
                recentServiceHistory.style.display='block';
                rshFlipdown.style.display = 'block';
                rshFlipup.style.display='none';
            });

            on(dom.byId("rshhFlipdown"), "click", function(e){
                recentShippingHistory.style.display='none';
                rshhFlipdown.style.display = 'none';
                rshhFlipup.style.display='block';
            });

            on(dom.byId("rshhFlipup"), "click", function(e){
                recentShippingHistory.style.display='block';
                rshhFlipdown.style.display = 'block';
                rshhFlipup.style.display='none';
            });




        });


</script>
<script type="text/javascript">

    function toggleLayer(whichLayer)
    {
        var elem, vis;
        if (document.getElementById) // this is the way the standards work
            elem = document.getElementById(whichLayer);
        else if (document.all) // this is the way old msie versions work
            elem = document.all[whichLayer];
        else if (document.layers) // this is the way nn4 works
            elem = document.layers[whichLayer];
        vis = elem.style;
        if (vis.display == '' && elem.offsetWidth != undefined && elem.offsetHeight != undefined)
            vis.display = (elem.offsetWidth != 0 && elem.offsetHeight != 0) ? '' : 'none';
        vis.display = (vis.display == '' || vis.display == 'inline') ? 'none' : '';
    }

    function toggle() {

        toggleLayer("ckinputgroup");
        toggleLayer("ccinputgroup")
    }
</script>
<p></p>
<table width="100%">
<tr><td width="50%" valign="top">

    <div class="CenterTableTitle"><s:property value="%{getText('clientpayment.serviceaccountstatus')}"/></div>
   <div class="miniform"> <ul class="accountStatus">
            <li><span class="title"><s:text name="clientpayment.servicebalance"/>:</span><s:set scope="page" name="btype" value="%{context.currentClient.billingBalance >= 0 ? 'balancegood':'balancebad'}" />
                <span class="${btype}">
                    <s:text name="format.money"><s:param value="context.currentClient.billingBalance"/></s:text></span>
            </li>
             </ul>
    <div id="rshFlipup" style="vertical-align:top;cursor: pointer;">
        <span style="vertical-align:top;" >Show Recent Account Activity</span>
        <img id="rshup" src="<s:url value="/images/icon_sortright.gif" />" alt="" style="float:right;"/>
    </div>
       <div id="rshFlipdown" style="vertical-align:top;display:none;cursor: pointer;">
       <span style="vertical-align:top;" >Hide Recent Account Activity</span>
           <img id="rshdown" src="<s:url value="/images/icon_sortdown.gif" />" alt="" />
       </div>
 <div id="recentServiceHistory" style="display:none" >
     <table border="0" width="100%">
        <tr>

            <td width="100%">

                <table border="0" cellpadding=4 cellspacing="1" width="100%" class="tableBorder2">
                    <tr bgcolor="#AADDAA" valign="top">
                        <th align="right" colspan=2>Account Summary</th>

                        <th style="text-align:right">Amount</th>

                        <th style="text-align:right">Balance</th>
                    </tr>
                    <tr bgcolor="#EFEFEF">
                        <td colspan="3"><strong>Beginning Balance prior to <s:date
                                name="serviceAccountSummary.summaryStartDate" format="MM/dd/yyyy"/></strong></td>
                        <td style="text-align:right;white-space:nowrap">
                            <strong><s:text name="format.money"><s:param
                                    value="serviceAccountSummary.balanceBefore"/></s:text></strong></td>

                    </tr>
                    <s:iterator id="trans" value="serviceAccountSummary.transactions">
                        <tr>
                            <td><s:date name="#trans.transactionDate" format="MM/dd/yyyy"/></td>
                            <td>${trans.category}/${trans.description}</td>
                            <td style="text-align:right;white-space:nowrap"><s:text name="format.money"><s:param
                                    value="#trans.amount"/></s:text></td>
                            <td style="text-align:right;white-space:nowrap"><s:text name="format.money"><s:param
                                    value="#trans.runningBalance"/></s:text></td>
                        </tr>
                    </s:iterator>
                    <tr>
                        <td colspan="2" align="right"><strong>Totals and Balance as of <s:date
                                name="serviceAccountSummary.summaryEndDate" format="MM/dd/yyyy"/></strong></td>
                        <td style="text-align:right;white-space:nowrap"><strong><s:text name="format.money"><s:param
                                value="serviceAccountSummary.totalAmount"/></s:text></strong></td>
                        <td style="text-align:right;white-space:nowrap"><strong><s:text name="format.money"><s:param
                                value="serviceAccountSummary.currentBalance"/></s:text></strong></td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    </div>
    </div>
   <div class="CenterTableTitle"><s:text name="clientpayment.shippingaccountstatus"/></div>
   <s:if test="context.currentClient.isShippingOnHold==1"><p></p><div class="AlertBad">Shipping Hold Active - Replenish funds today!</div></s:if>
   <div class="miniform">
       <ul class="accountStatus">
            <li><span class="title"><s:text name="clientpayment.shippingbalance"/>:</span>
                <s:set scope="page" name="btype" value="%{shippingAccountSummary.currentBalance >= 0 ? 'balancegood':'balancebad'}" /><span class="${btype}" ><s:text name="format.money"><s:param
                    value="shippingAccountSummary.currentBalance"/></s:text></span>
            </li>
            <li><span class="title">Average Daily Usage:</span>
                <span class="balance"><s:text name="format.money"><s:param
                    value="shippingAccountSummary.perDayAverage"/></s:text></span></li>
            <li><span class="title">Ship Days Funded (est):</span>
                <span class="balance"><s:text name="format.days"><s:param
                    value="shippingAccountSummary.daysRemaining"/></s:text></span></li>
        </ul>
       <a id="rshhFlipup" style="vertical-align:top;cursor: pointer;" href="#">
           <span style="vertical-align:top;" >Show Recent Account Activity</span>
           <img id="rshhup" src="<s:url value="/images/icon_sortright.gif" />" alt="" />
       </a>
       <a id="rshhFlipdown" style="vertical-align:top;display:none;cursor: pointer;" href="#">
           <span style="vertical-align:top;" >Hide Recent Account Activity</span>
           <img id="rshhdown" src="<s:url value="/images/icon_sortdown.gif" />" alt="" />
       </a>
  <div id="recentShippingHistory" style="display:none">
     <table border="0" width="100%" >
        <tr>

            <td width="100%">

                <table border="0" cellpadding=4 cellspacing="1" width="100%" class="tableBorder2">
                    <tr bgcolor="#AADDAA" valign="top">
                        <th align="right" colspan=2>Account Summary</th>

                        <th style="text-align:right">Amount</th>

                        <th style="text-align:right">Balance</th>
                    </tr>
                    <tr bgcolor="#EFEFEF">
                        <td colspan="3"><strong>Beginning Balance prior to <s:date
                                name="shippingAccountSummary.summaryStartDate" format="MM/dd/yyyy"/></strong></td>
                        <td style="text-align:right;white-space:nowrap">
                            <strong><s:text name="format.money"><s:param
                                    value="shippingAccountSummary.balanceBefore"/></s:text></strong></td>

                    </tr>
                    <s:iterator id="trans" value="shippingAccountSummary.transactions">
                        <tr>
                            <td><s:date name="#trans.transactionDate" format="MM/dd/yyyy"/></td>
                            <td>${trans.category}/${trans.description}</td>
                            <td style="text-align:right;white-space:nowrap"><s:text name="format.money"><s:param
                                    value="#trans.amount"/></s:text></td>
                            <td style="text-align:right;white-space:nowrap"><s:text name="format.money"><s:param
                                    value="#trans.runningBalance"/></s:text></td>
                        </tr>
                    </s:iterator>
                    <tr>
                        <td colspan="2" align="right"><strong>Totals and Balance as of <s:date
                                name="shippingAccountSummary.summaryEndDate" format="MM/dd/yyyy"/></strong></td>
                        <td style="text-align:right;white-space:nowrap"><strong><s:text name="format.money"><s:param
                                value="shippingAccountSummary.totalAmount"/></s:text></strong></td>
                        <td style="text-align:right;white-space:nowrap"><strong><s:text name="format.money"><s:param
                                value="shippingAccountSummary.currentBalance"/></s:text></strong></td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
      </div>
    </div>


    </td>
    <td valign="top">

        <div class="CenterTableTitle">Make a Payment!</div>
        <div cssClass="miniform">
        <s:actionerror cssClass="AlertBad" cssStyle="list-style-type:none;display:block;"/>
         <s:if test="%{#parameters.successMessage}">
            <div cssClass="AlertGood" cssStyle="list-style-type:none;display:block;"><s:property value="#parameters.successMessage"/></div>
       </s:if>
        <s:form cssClass="miniform" action="accountStatus!makePayment" method="POST" validate="true"  namespace="/billing" >
        <tbody>
                <s:textfield name="name" value="%{name}"
                             label="%{getText('clientpayment.name')}"
                             size="50"/>
<TR>
    <td></td>
    <td>Enter the name of the primary accountholder for your checking/CC account
    </td>
</TR>
<s:textfield name="ft.address_one" value="%{ft.address_one}"
             label="%{getText('clientpayment.streetaddress')}"
             size="50"/>
<s:textfield name="ft.city" value="%{ft.city}"
             label="%{getText('clientpayment.city')}"
             size="50"/>
<s:textfield name="ft.state" value="%{ft.state}"
             label="%{getText('clientpayment.state')}"
             size="50"/>
<s:textfield name="ft.zip" value="%{ft.zip}"
             label="%{getText('clientpayment.zip')}"
             size="10"/>
<s:textfield name="ft.amount" value="%{ft.amount}"
             label="%{getText('clientpayment.amount')}"
             size="10"/>
<s:select label="%{getText('clientpayment.accountType')}" name="ft.intacct_acct_type"
          value="%{ft.intacct_acct_type}"
          list="#{'ACTIVITY':'Services Account', 'SHIPPING':'Shipping Account'}"
          headerKey="" headerValue="Choose an Account" required="true"/>
<s:select label="%{getText('clientpayment.paymentType')}" name="ft.fop"
          id="clientpayment.paymentType"
          value="%{ft.fop}"
          list="#{'3':'From a Checking Account', '0':'From a Credit Card'}"
          required="true" onchange="toggle();"/>
</tbody>
<s:set name="ckstyle" value="%{ft.fop==3?'display:':'display:none'}" />
<tbody id="ckinputgroup" style="${ckstyle}">
    <TR>
        <td></td>
        <td>We can debit your checking account directly to obtain funds.
        </td>
    </TR>
    <s:textfield name="ft.ck_bank" value="%{ft.ck_bank}"
                 label="%{getText('clientpayment.ckRouting')}"
                 size="18"/>
    <TR>
        <td></td>
        <td>Enter the first 9 digits, starting from the bottom left of your check
        </td>
    </TR>
    <s:textfield name="ft.ck_acct_num" value="%{ft.ck_acct_num}"
                 label="%{getText('clientpayment.ckAccount')}"
                 size="18"/>
    <TR>
        <td></td>
        <td>Enter the next 10 digits that appear after the routing number you entered above
        </td>
    </TR>
    <s:textfield name="ft.ck_number" value="%{ft.ck_number}"
                 label="%{getText('clientpayment.ckNumber')}"
                 size="18"/>
    <TR>
        <td></td>
        <td>Enter the check number that is on the check you are voiding, or simply a reference value for your own
            records
        </td>
    </TR>
</tbody>
<s:set name="ccstyle" value="%{ft.fop==0?'display:':'display:none'}" />
<tbody id="ccinputgroup" style="${ccstyle}">
    <s:textfield name="ft.cc_number" value="%{ft.cc_number}"
                 label="%{getText('clientpayment.ccAccount')}"
                 size="18"/>
    <tr>
        <td class="tdLabel"><label class="label"><s:property value="%{getText('clientpayment.ccExpires')}"/>:</label>
        </td>
        <td><s:fielderror>
                <s:param>ccExpMonth</s:param>
                <s:param>ccExpYear</s:param>
            </s:fielderror>
            <s:select theme="simple" label="%{getText('clientpayment.ccExpMonth')}" name="ccExpMonth"
                      value="%{ccExpMonth}" list="context.ccMonthMap"
                      required="true"/>

            <s:select theme="simple" label="%{getText('clientpayment.ccExpYear')}" name="ccExpYear"
                      value="%{ccExpYear}" list="context.ccYearMap"  required="true"/>

        </td>
    </tr>
    <TR>
        <td></td>
        <td>We accept VISA, MasterCard and American
            Express credit or debit cards.
        </td>
    </TR>
</tbody>
<tbody>
    <submit value="%{getText('clientpayment.submit')}"/>
</tbody>
</s:form>

</div>
</td>
</tr>
</table>

<hr>
<s:property value="sessionString"/>
</body>
</html>
