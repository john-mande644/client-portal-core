<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <s:head theme="xhtml" debug="true"/>
	<title>Order Tracking</title>
    <link rel="stylesheet" href="<s:url value="/styles/trace.css" />" type="text/css" media="screen" charset="utf-8">
</head>

<body>
<a href="http://biotanicalhealth.3dcartstores.com/" ><img src="<s:url value="/images/biotanical/LOGO_Biotanical.gif" />" alt="" border="0"/></a>
 <s:iterator id="o" value="orders">
     <s:div id="order">
    <span class="title">Order Number:</span> ${o.orderRefnum}
	<br>
	<span class="title">Status:</span> ${o.orderStatus}
	<br>
	<span class="title">Order Date:</span> <s:date name="#o.createdDate" format="MM/dd/yyyy"/>
	<br>   
	<s:if test="%{#o.orderStatus eq 'Shipped'}" >
    <span class="title">Ship Date:</span> <s:date name="#o.shippedOn" format="MM/dd/yyyy"/>
	<br>

	<span class="title">Tracking Number(s):
		<br>
	</span> <a href="https://tools.usps.com/go/TrackConfirmAction.action?tLabels=${o.trackingNums}" target="_blank">${o.trackingNums}</a>
	<br>
    </s:if>
    <span class="title">Shipping Address:</span>
	<ul>
		${o.shipinfo.shipFirstName} ${o.shipinfo.shipLastName}
        <s:if test="%{#o.shipinfo.companyName.length() > 0}" >
            <br>
           ${o.shipinfo.shipCompanyName}
            </s:if>
        <br>
		${o.shipinfo.shipAddressOne}
        <s:if test="%{#o.shipinfo.shipAddressTwo.length() > 0}" >
            <br>
		${o.shipinfo.shipAddressTwo}
            </s:if>
        <br>
		${o.shipinfo.shipCity}, ${o.shipinfo.shipState} ${o.shipinfo.shipZip}
		<br>
		${o.shipinfo.shipPhoneNum}
		<br>
		${o.shipinfo.shipEmailAddress}
		<br>
	</ul>
	<s:if test="%{#o.orderStatus eq 'Shipped'}" >
	<span class="title">Items Shipped:</span>
	<table border="0">
		<tr>
			<td>
				<strong>Qty</strong>
			</td>
			<td>
				<strong>SKU</strong>
			</td>
			<td>
				<strong>Description</strong>
			</td>
		</tr>
        <s:iterator id="item" value="#o.lineitems">
         <s:if test="%{#item.quantityActual > 0}" >
        <tr>
			<td>
				${item.quantityActual}
			</td>
			<td>
				${item.inventoryNum}
			</td>
			<td>
				${item.description}
			</td>
		</tr>
            </s:if>
        </s:iterator>
	</table>
     </s:if>
     </s:div>
 </s:iterator>
</body>
</html>
