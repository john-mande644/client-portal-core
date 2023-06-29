<%@ page contentType="text/html; charset=UTF-8"  %>
<%@ taglib prefix="s" uri="/struts-tags" %>


<html>
<body>
  <CENTER><H2>NOX Personalized Sticker Processor</H2><BR>

<HR>
     <font color="red"> <s:actionerror /></font>
      <font color="blue"><s:actionmessage/></font>
 <s:form action="loadSticker" >

      <s:select label="Select Client from List:"
           name="clientId"
           list="#{'160':'Bill O Reilly','293':'Bill Bennett','294':'Mike Gallagher','296':'Michael Medved','292':'Rusty Humphries','335':'JPAmerica'}"
      />
      

      <s:textfield name="stickerSku" label="Enter Sticker SKU from Sheet"/>

      <s:textfield name="forSku" label="Enter \"For SKU\" from sheet"/>

      <s:textfield name="orderRefNum" label="Scan NOX Sticker Order Barcode" value="" />


    <s:submit label="Add to List" value="Add to List"/>

 </s:form>
<script language="JavaScript"> document.loadSticker.loadSticker_orderRefNum.focus(); </script>
<hr>
<s:form action="processStickers">
    <s:hidden name="clientId" />
    <s:submit value="Process Orders"/>
    
</s:form>
      <hr></CENTER>
      <s:iterator  value="#session.borOrderList" status="status">
      <s:property value="#status.count"/>:&nbsp;
      <s:property/> <br>
          
      </s:iterator>

</body>

</html>