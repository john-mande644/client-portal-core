<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>Return Label</title>
    <style>
        .label{
            font-weight:bold;
        }
        .cancel{
            float:right;
        }

        .error{
            color:red;
        }
        .lines{
            width:100%;
            border-collapse: collapse;
        }
        .lines td{
            text-align:center;
            padding:5px;
        }
        .row-sku{
            width:20%;
        }
        .row-description{
            width:30%;
        }
        .row-qty{
            width:10%;
        }
        .row-short{
            width:10%;
        }
        .row-reason{
            width:10%;
        }
        .oddrow {
            background-color: lightgrey;
        }
        .evenrow{

        }
        .info{
            text-align: center;
            background-color: eeeeee;

        }
    </style>


</head>

<body>
<div class="error"><s:actionerror/></div>
<div>
    Return label for <s:property value="order.billFirstName"/> <s:property value="order.billLastName"/>.<br/>
    Order Reference: <s:property value="order.orderRefnum"/>
</div>
<div>
    <img src="<s:property value="imageUrl"/>">
</div>


</body>
</html>