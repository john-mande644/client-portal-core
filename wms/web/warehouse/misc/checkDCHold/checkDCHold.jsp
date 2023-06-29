<%@ taglib prefix="s" uri="/struts-tags" %>
<html>

<head>
    <jsp:include page="/rfapps/includes/androidStuff.jsp"/>
    <style>
        .onhold {
            background-color: #FF0000;

            height: 200px;
            padding: 10px;
            width: 200px;
        }
        .good {
            background-color: #008000;

            height: 200px;
            padding: 10px;
            width: 200px;
        }
    </style>
</head>
<body>

<a href="/wms/miscMenu/start.action" class="button">MENU</a> <P class="button>">
<s:if test="hasActionErrors()">
    <div id="errorDiv" style="padding-left: 10px; margin-bottom: 5px">
     <span class="error">
     <s:iterator value="actionErrors">
         <span class="errorMessage"><s:property escape="false" /></span>
     </s:iterator>
     </span>
    </div>
</s:if>
<s:actionmessage/>

    <s:form action="check.action">
        Please Scan the Pack Error Label to check <br>
        <s:textfield name="orderNum" id="autoSubmit" value=""/>
        <s:submit></s:submit>
    </s:form>

 <s:if test="orderNum.length()>0">

     <s:if test="onHold">

      <div class="onhold">
          <s:property value="orderNum"/><br/>
           On Hold

      </div>



     </s:if>
<s:else>
    <div class="good">
        <s:property value="orderNum"/><br/>
         Ship It!!
    </div>
</s:else>

 </s:if>

</body>
</html>
