<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<META HTTP-Equiv="Scanner" content="Enabled">
<META HTTP-Equiv="Scanner" content="AIM_TYPE_TRIGGER">
<META HTTP-Equiv="Scanner" content="AutoEnter">
<jsp:include page="/rfapps/includes/androidStuff.jsp"/>
<SCRIPT>
    function insertAndSubmit(scanData) {
        document.getElementById("autoSubmit").value = scanData;
        document.getElementById("autoSubmit").form.submit();
    }
</SCRIPT>
<body>
<div class="body">
    <c:if test='${error != null}'>
        <script>
            try {
                javautil.playErrorSound();
            } catch (err) {

            }
        </script>
        <B class="error">${error}</B>
        <script>
            var gen = new ActiveXObject("SymbolBrowser.Generic");
            gen.PlayWave("\\err.wav", 0);
        </script>
        <br>
    </c:if>
    <c:if test='${outcome != null}'>
        <font color='blue' size="+2">
            <b>${outcome}</b>
        </font>
        <hr size="3">
    </c:if>
    <span class="label">Please Scan Serial Numbers for Sku<br> </span>
    Qty Entered: ${serialForm.currentNum} &nbsp &nbsp Qty To Enter: ${serialForm.total}
    <br>
    <center>
        <span class="inventoryid">${serialForm.invId} </span> <br>
        <span class="inventorynum">${serialForm.invNum}</span><br> ${serialForm.description}
    </center>

    <table>
        <html:form action="pickGetSerial" focus="serial">
            <tr>
                <td class="right">Serial Number:</td>
                <td>
                    <html:text property="serial" value="" styleId="autoSubmit"/>
                    <html:hidden property="invId"/>
                    <html:hidden property="invNum"/>
                    <html:hidden property="description"/>
                    <html:hidden property="pItemId"/>
                    <html:hidden property="total"/>
                    <html:hidden property="currentNum"/>
                </td>
            </tr>
            <tr>
                <td><html:submit value="Submit"/></td>
            </tr>

        </html:form>
    </table>
    <br>
</div>
</body>

