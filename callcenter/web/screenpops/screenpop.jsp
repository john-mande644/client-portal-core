<%@ taglib uri="struts/bean-el" prefix="bean" %>
<%@ taglib uri="struts/html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html smlns="http://www.w3.org/1999/xhtml">
<head>
    <link href="<html:rewrite page="/css/screenpop.css" module=""/>" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="<html:rewrite page="/js/Ajax.js" module=""/>"></script>
</head>
<body>
<html:form action="loadscreenpop.do">

</html:form>
<!----- Left side menu ---------------------------------------------- -->
<div class="box">
    <div class="boxtop">
        <div></div>
    </div>

    <div class="content">
        <!-- menu content -->
        <center><script language="JavaScript">
TargetDate = new Date();
BackColor = "white";
ForeColor = "navy";
CountActive = true;
CountStepper = 1;
LeadingZero = true;
DisplayFormat = " %%M%%:%%S%% ";
FinishMessage = "It is finally here!";
</script>
On Call: <script language="JavaScript" src="<html:rewrite page="/js/timer.js" module=""/>"></script><br><b>${spForm.campaign}</b></center>

        <br><br>
        <center>
            <iframe name="gToday:mini:agenda.js" id="gToday:mini:agenda.js" src="screenpops/mini/iflateng.htm"
                    scrolling="no" frameborder="0">
            </iframe>
        </center>
        <br><br>
        <b>SEND:</b><br>
        <center>
            <html:link action="problemForm?client=${spForm.campaign}&callId=${spForm.callId}"
                       module="callcenter" target="_blank" title="send problem form">Problem Form
            </html:link>
            <p>
            <a href="http://internal.owd.com:8080/callcenter/caseSearch.do" target="_blank">ProblemForm Search</a>
            <p>
        </center>
        <span id="doscript">

        <c:if test="${spForm.doScript == '1'}">
            <b>Scripts:</b><br>
            <center>
                <html:link href="http://internal.owd.com/think/display/CC/${spForm.campaign}" target="_blank">Up Sell
                </html:link>
            </center>
        </c:if>
       </span>
    </div>
    <div class="boxbottom">
        <div></div>
    </div>

</div>

<!------------------  Header links and stuff ----------------------------->
<div class="tbox">
    <div class="tboxtop">
        <div></div>
    </div>
    <div class="tcontent">
      <span id="links">
       <!--  link content ---------->
          <center>
              <table width="100%" class="linkss">
                  <tr>

                      <c:if test="${spForm.urlEntry!=null && spForm.urlEntry!=''}">
                          <td>
                              <html:link href="${spForm.urlEntry}" target="_blank">Place Order</html:link>
                          </td>
                      </c:if>


                      <c:if test="${spForm.urlStatus!=null && spForm.urlStatus!=''}">
                          <td>
                              <html:link href="${spForm.urlStatus}" target="_blank">Order Status</html:link>
                          </td>
                      </c:if>


                      <c:if test="${spForm.urlService!=null && spForm.urlService!=''}">
                          <td>
                              <html:link href="${spForm.urlService}" target="_blank">Customer Service</html:link>
                          </td>
                      </c:if>


                      <c:if test="${spForm.kb!=null && spForm.kb!=''}">
                          <td>
                              <html:link href="${spForm.kb}" target="_blank">Kbase</html:link>
                          </td>
                      </c:if>


                      <c:if test="${spForm.quickRef!=null && spForm.quickRef!=''}">
                          <td>
                              <html:link href="${spForm.quickRef}" target="_blank">Quick ref</html:link>
                          </td>
                      </c:if>

                  </tr>
              </table>


          </center>



      </span></div>
    <div class="tboxbottom">
        <div></div>
    </div>
</div>
<!--- main script area-->

<div class="sbox">
    <div class="sboxtop">
        <div></div>
    </div>
   <span id="scont">
    <div class="scontent">

              <center>
                  <div class="script">
                      <c:if test="${spForm.script==null}">
                          <img src="<html:rewrite page="/images/callload.gif" module=""/>" alt="loalessding call"/>
                      </c:if>

                      ${spForm.script}
                  </div>
              </center>


        <center>
            <c:if test="${spForm.extras != null}">


                <div id="extras">
                    <c:forEach var="extra" items="${sessionScope.spForm.extras}">

                        <a onClick="javascript:switchMenu('${extra.action}');"><font color = "${extra.color}">${extra.action}</font><img src="/callcenter/images/arrow.gif"/>  </a>
                         
                        <div id="${extra.action}" class="switch" style="display: none;">
                                ${extra.display}
                        </div>
                        <br>
                    </c:forEach>
                </div>
            </c:if>
        </center>
        
    </div>
       </span>
    <div class="sboxbottom">
        <div></div>
    </div>

</div>

<script type="text/javascript">
    retrieveURL('/callcenter/loadscreenpop.do?ask=COMMAND_NAME_1', 'spForm');
</script>

</body>
</html>