<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>

<head>
    <!-- DataTables CSS -->
    <link rel="stylesheet" type="text/css" href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/css/jquery.dataTables.css">

    <!-- jQuery -->
    <script type="text/javascript" charset="utf8" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.2.min.js"></script>

    <!-- DataTables -->
    <script type="text/javascript" charset="utf8" src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/jquery.dataTables.min.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
    <link rel="stylesheet" type="text/css" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/themes/black-tie/jquery-ui.min.css">
    <script>
       $(document).ready(function(){
           $('#orders').dataTable(
                   {
                       "aaSorting":[[1,"desc"]],
                       "bStateSave":true
                   }

           );
           $("#datepicker").datepicker({ dateFormat: "yy-mm-dd" });

       })
   </script>


</head>

<body>
   <s:form action="loadData">
          <s:textfield name="date" id='datepicker' label="SLA DATE"></s:textfield>
       <s:textfield name="batchSize" label="Batch Size Minimum"></s:textfield>
      <s:select list="clients" listKey="action" listValue="display" headerKey="-1" headerValue="--Select Client--" name="clientId" label="Client"/>
      <s:hidden name="facility"/>
       <s:submit/>


   </s:form>
   <s:if test="hasActionErrors()">
       <div id="errorDiv" style="padding-left: 10px; margin-bottom: 5px">
           <script>
               try{
                   javautil.playErrorSound();
               }catch(err){

               }
           </script>
     <span class="error">
     <s:iterator value="actionErrors">
         <span class="errorMessage"><s:property escape="false" /></span>
     </s:iterator>
     </span>
       </div>
   </s:if>
      <div>
          <s:actionmessage/>
      </div>
   <div>
       <s:if test="theData != null">
           <table id = "orders">
               <thead>
               <th>Count</th>
               <th>Fingerprint</th>
               <th>Group</th>
               <th>Print</th>
               </thead>

              <tbody>


           <s:iterator value="theData">
              <tr>
                  <td><s:property value="count"/>
                  </td>
              <td><s:property value="fingerprint"/> </td>
                  <td><s:property value="groupname"/> </td>
                  <td>
                  <s:iterator id="print" value="printerMap">
                      <a href="/wms/packSlipRelease/printFingerprint.action?batchSize=<s:property value="batchSize"/>&date=<s:property value="date"/>&clientId=<s:property value="clientId"/>&fingerprint=<s:property value="fingerprint"/>&printer=<s:property value="#print.key"/>&facility=<s:property value="facility"/>"><s:property value="#print.value"/></a>
                  <br/>
                  </s:iterator>

                  </td>
              </tr>

           </s:iterator>

              </tbody>
           </table>

       </s:if>
   </div>

</body>
</html>