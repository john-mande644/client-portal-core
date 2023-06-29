<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>
</head>
<body>
<div class="errors">
    <s:if test="hasActionErrors()">
        <script>
            try{
                javautil.playErrorSound();
            }catch(err){

            }
        </script>
        <s:iterator value="actionErrors">
<span class="errorMessage"><s:property escape="false" />
</span>
        </s:iterator>
    </s:if>


</div>

<div>
  Location Audit Numbers<br>


</div>
<div class="today">
    Todays numbers.<br>
    <s:if test="today != null">
        <table>
         <thead>
         <tr>
         <th>Facility</th>
         <th>Total</th>
         <th>Good</th>
         <th>Added</th>
         <th>Removed</th>
         </tr>
         </thead>

        <s:iterator value="today">
            <tr>
          <td>  <s:property value="facility"/></td>
                <td><s:property value="total"/> </td>
           <td><s:property value="good"/> / <s:property value="goodPercent" /> </td>
                <td><s:property value="added"/> / <s:property value="addedPercent"/> </td>
                <td><s:property value="removed"/> / <s:property value="removedPercent"/> </td>
            </tr>

        </s:iterator>
        </table>
    </s:if>
</div>
<div class="weekly">
    Weekly numbers.<br>
    <s:if test="weekly != null">
        <table>
            <thead>
            <tr>
                <th>Facility</th>
                <th>Total</th>
                <th>Good</th>
                <th>Added</th>
                <th>Removed</th>
            </tr>
            </thead>

            <s:iterator value="weekly">
                <tr>
                    <td>  <s:property value="facility"/></td>
                    <td><s:property value="total"/> </td>
                    <td><s:property value="good"/> / <s:property value="goodPercent" /> </td>
                    <td><s:property value="added"/> / <s:property value="addedPercent"/> </td>
                    <td><s:property value="removed"/> / <s:property value="removedPercent"/> </td>
                </tr>

            </s:iterator>
        </table>
    </s:if>
</div>
<div class="monthly">
    Monthly numbers.<br>
    <s:if test="monthly != null">
        <table>
            <thead>
            <tr>
                <th>Facility</th>
                <th>Total</th>
                <th>Good</th>
                <th>Added</th>
                <th>Removed</th>
            </tr>
            </thead>

            <s:iterator value="monthly">
                <tr>
                    <td>  <s:property value="facility"/></td>
                    <td><s:property value="total"/> </td>
                    <td><s:property value="good"/> / <s:property value="goodPercent" /> </td>
                    <td><s:property value="added"/> / <s:property value="addedPercent"/> </td>
                    <td><s:property value="removed"/> / <s:property value="removedPercent"/> </td>
                </tr>

            </s:iterator>
        </table>
    </s:if>
</div>
</body>
</html>