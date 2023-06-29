<%@ page contentType="text/html; charset=UTF-8" %>

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
<s:form action="getCount">
    <s:textfield name="sla" id='datepicker' label="SLA DATE"></s:textfield>
     <s:select list="facilities" name="facility" label="Facility"/>
    <s:select list="reports"  headerKey="-1" headerValue="--Select Reportt--" name="report" label="What Report"/>

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

    <s:if test="boxs != null">
        <table id = "orders">
            <thead>
            <th>Box</th>
            <th>Qty</th>

            </thead>

            <tbody>


            <s:iterator value="boxs">
                <tr>
                    <td><s:property value="boxName"/>
                    </td>
                    <td><s:property value="qty"/> </td>

                </tr>

            </s:iterator>

            </tbody>
        </table>

    </s:if>
    </div>
</body>

