<%@ page import="com.owd.web.internal.warehouse.admin.WarehouseStatus,
                 java.text.DecimalFormat,
                 java.util.*,
                 java.text.DateFormat,
                 com.owd.core.managers.ConnectionManager,
                 java.text.SimpleDateFormat,
                 java.sql.ResultSet,
                 com.owd.web.internal.navigation.*,
                 com.owd.hibernate.HibernateSession,
                 org.hibernate.Session,
                 org.apache.commons.beanutils.RowSetDynaClass,
                 com.owd.core.business.order.OrderManager,
                 com.owd.hibernate.generated.OwdOrder,
                 com.owd.core.business.order.OrderFactory,
                 com.owd.hibernate.generated.OwdUser,
                 com.owd.core.business.user.UserFactory,
                 com.owd.hibernate.generated.OwdOrderShipHold,
                 com.owd.web.internal.warehouse.admin.WarehouseHoldUtilities,
                 com.owd.web.internal.servlet.HomeServlet" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%
    String cid = HomeServlet.getSessionString(request, HomeServlet.kExtranetClientID);
    if (cid == null) cid = "0";

    if ("0".equals(cid) || "".equals(cid)) cid = "146";

    cid = "146";
    HomeServlet.setSessionString(request, HomeServlet.kExtranetClientID, cid);

    UIUtilities.setRequestNavigationElements(UIUtilities.kWarehouseAreaName, "Inventory Adjustments", request);
    try {


%>


<HTML>
<HEAD>
<script language=JavaScript>
    function CloseForm(Response)
    {


        document.getElementById('modalreturn1').value = Response;

    }
</script>
<TITLE><%= request.getAttribute("lf_CURRMANAGER") %></TITLE>

<link rel="stylesheet" type="text/css" href="/internal/stylesheets/style.css"/>
<link rel="stylesheet" type="text/css" href="/internal/stylesheets/subModal.css"/>
<script type="text/javascript" src="/internal/javascripts/common.js"></script>
<script type="text/javascript" src="/internal/javascripts/subModal.js"></script>
<script type="text/javascript" src="/internal/test/style.css"></script>
<script type="text/javascript" src="/internal/javascripts/skulocate.js"></script>
<script type='text/javascript' src='/internal/dwr/interface/SkuLocator.js'></script>
<script type='text/javascript' src='/internal/dwr/engine.js'></script>
<script type='text/javascript' src='/internal/dwr/util.js'></script>
<jsp:include page="/includes/owdtop.jsp"/>


<style>
    span.pagelinks {
        border: none;
    }
</style>

<div id="pagecommands" style="padding-top: 8px;">
    <h3>
        <a class="admin" href="/internal/warehouse/admin">Cancel and go back</a>

    </h3>
</div>

<font color=red>
    <B><%= (request.getAttribute("errormessage") != null ? "<div class=\"AlertBad\">" + request.getAttribute("errormessage") + "</div>" : "") %></B>
</font>
<%


%>

<FORM METHOD=POST ACTION="/internal/warehouse/admin/adjustments/addadjustment">
    <table border=0 cellpadding=0><TR><TD ALIGN=RIGHT NOWRAP>
        <B>First, choose a client::<%= cid%>&nbsp;</B></TD><TD ALIGN=LEFT NOWRAP>
        <SELECT NAME=adjcid
                onchange="loadXMLDoc('/internal/test/setcid.jsp?q='+this.value)"><%= HomeServlet.getClientSelector(request, "name", cid)%></SELECT>
    </TD></TR>
        <TR><TD></TD><TD ALIGN=LEFT NOWRAP></TD></TR>
        <tr>
            <td align=left colspan=2><a
                    href="javascript:showPopWin('/internal/test/modalContent.jsp', 500, 300, CloseForm);">Lookup SKU</a>
                <input type=text id=modalreturn1 name=modalreturn1 value=''></td>
        </tr>
        <TR><TD ALIGN=RIGHT><B></B></TD><TD ALIGN=LEFT NOWRAP>
            &nbsp;&nbsp;<INPUT TYPE=SUBMIT NAME=SUBMIT VALUE="Next Step"></TD></TR>
    </table>
</FORM>
<ul><li>
    getTestHTML(
    );
    <input class='ibutton' type='button' onclick='SkuLocator.getTestHTML(reply0);' value='Execute'
           title='Calls SkuLocator.getTestHTML(). View source for details.'/>
    <script type='text/javascript'>
        var reply0 = function(data)
        {
            if (data != null && typeof data == 'object') alert(DWRUtil.toDescriptiveString(data, 2));
            else DWRUtil.setValue('d0', DWRUtil.toDescriptiveString(data, 1));
        }
    </script>
    <span id='d0' class='reply'></span>
</li>
</ul>
<jsp:include page="/includes/owdbottom.jsp"/>
<div id="popupMask">&nbsp;</div>

<div id="popupContainer">
    <div id="popupInner">
        <div id="popupTitleBar">
            <div id="popupTitle"></div>

            <div id="popupControls">
                <img src="/internal/images/close.gif" onclick="hidePopWin(false);"/>
            </div>
        </div>
        <iframe src="/internal/test/loading.html" style="width:100%;height:100%;background-color:transparent;"
                scrolling="auto" frameborder="0" allowtransparency="true" id="popupFrame" name="popupFrame" width="100%"
                height="100%"></iframe>
    </div>
</div>
<script language=JavaScript>


    var target;
    target = this;
    /*   this.document.getElementById("loaderContainer").style.display = "none";
    */
    DWREngine.setPreHook(function() {



        /* target.document.getElementById("loaderContainer").style.display = "";       */
        target.document.getElementById("popupTitleBar").style.height = "0em";
        target.document.getElementById("popupTitle").style.display = "none";
        target.document.getElementById("popupControls").style.display = "none";
        showPopWin('/internal/test/loadingcontent.jsp', 250, 50, '');
    });

    DWREngine.setPostHook(function() {
        /* target.document.getElementById("loaderContainer").style.display = "none";*/
        hidePopWin(false);
        target.document.getElementById("popupTitleBar").style.height = "1.3em";

        target.document.getElementById("popupTitle").style.display = "block";
        target.document.getElementById("popupControls").style.display = "block";
    });

</script>
</BODY></HTML>

<% } catch (Exception ex) {
    ex.printStackTrace();
} finally {
    HibernateSession.closeSession();
}%>

