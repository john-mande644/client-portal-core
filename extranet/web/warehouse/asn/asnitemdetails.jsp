<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.css" />
<link rel="stylesheet" href="/webapps/css/style.asn.css" />
</head>
<body>
    <div class="container-fluid">
        <h1 class="center">Receive Details</h1>
        <div class="row mt5">
            <div class="col-md-1"></div>
            <div class="col-md-2 bg-info pv5 text-right"><strong>Receive ID</strong></div>
            <div class="col-md-3 pv5"><s:property value="receiveId" /></div>
            <div class="col-md-2 bg-info pv5 text-right"><strong>ASN</strong></div>
            <div class="col-md-3 pv5"><s:property value="asnId" /></div>
            <div class="col-md-1"></div>
        </div>
        <div class="row mt5">
            <div class="col-md-1"></div>
            <div class="col-md-2 bg-info pv5 text-right"><strong>Created By</strong></div>
            <div class="col-md-3 pv5"><s:property value="rcv.createdBy" /></div>
            <div class="col-md-2 bg-info pv5 text-right"><strong>Created On</strong></div>
            <div class="col-md-3 pv5"><s:property value="rcv.createdOn" /></div>
            <div class="col-md-1"></div>
        </div>
        <div class="row mt5">
            <div class="col-md-1"></div>
            <div class="col-md-2 bg-info pv5 text-right"><strong>Client</strong></div>
            <div class="col-md-3 pv5"><s:property value="client.companyName" /></div>
            <div class="col-md-2 bg-info pv5 text-right"><strong>Facility</strong></div>
            <div class="col-md-3 pv5"><s:property value="rcv.facilityCode" /></div>
            <div class="col-md-1"></div>
        </div>
        <div class="row mt5">
            <div class="col-md-1"></div>
            <div class="col-md-2 bg-info pv5 text-right"><strong>ASN Found</strong></div>
            <div class="col-md-3 pv5"><s:property value="asnFound" /></div>
            <div class="col-md-1"></div>
        </div>
        <div class="row mt5">
            <div class="col-md-1"></div>
            <div class="col-md-2 bg-info pv5 text-right"><strong>Pack Slip Found</strong></div>
            <div class="col-md-3 pv5"><s:property value="packSlipFound" /></div>
            <div class="col-md-2 bg-info pv5 text-right"><strong>Pack Slip Match</strong></div>
            <div class="col-md-3 pv5"><s:property value="packSlipMatch" /></div>
            <div class="col-md-1"></div>
        </div>
         <div class="row mt5">
            <div class="col-md-1"></div>
            <div class="col-md-2 bg-info pv5 text-right"><strong>Received By</strong></div>
            <div class="col-md-3 pv5"><s:property value="rcv.receiveBy" /></div>
            <div class="col-md-2 bg-info pv5 text-right"><strong>Minutes</strong></div>
            <div class="col-md-3 pv5"><s:property value="rcv.billedMinutes" /></div>
            <div class="col-md-1"></div>
        </div>
        <div class="row mt5">
            <div class="col-md-1"></div>
            <div class="col-md-2 bg-info pv5 text-right"><strong>Start Time</strong></div>
            <div class="col-md-3 pv5"><s:property value="rcv.startTimestamp" /></div>
            <div class="col-md-2 bg-info pv5 text-right"><strong>End Time</strong></div>
            <div class="col-md-3 pv5"><s:property value="rcv.endTimestamp" /></div>
            <div class="col-md-1"></div>
        </div>
        <div class="row mt5">
            <div class="col-md-1"></div>
            <div class="col-md-2 bg-info pv5 text-right"><strong>Posted</strong></div>
            <div class="col-md-3 pv5"><s:property value="posted" /></div>
            <div class="col-md-2 bg-info pv5 text-right"><strong>Post Date</strong></div>
            <div class="col-md-3 pv5"><s:property value="rcv.PostDate" /></div>
            <div class="col-md-1"></div>
        </div>
        <div class="row mt5">
            <div class="col-md-1"></div>
            <div class="col-md-2 bg-info pv5 text-right"><strong>Notes</strong></div>
            <div class="col-md-8"><s:property value="rcv.notes" /></div>
            <div class="col-md-1"></div>
        </div>
        <div class="row mt5">
            <div class="col-md-1"></div>
            <div class="col-md-2 bg-info pv5 text-right"><strong>Count Method</strong></div>
            <div class="col-md-3 pv5"><s:property value="rcv.countMethod" /></div>
            <div class="col-md-1"></div>
        </div>
        <div class="row mt5">
            <div class="col-md-1"></div>
            <div class="col-md-2 bg-info pv5 text-right"><strong>Carton/Package Count</strong></div>
            <div class="col-md-3 pv5"><s:property value="rcv.cartonCount" /></div>
            <div class="col-md-2 bg-info pv5 text-right"><strong>Pallet Count</strong></div>
            <div class="col-md-3 pv5"><s:property value="rcv.palletCount" /></div>
            <div class="col-md-1"></div>
        </div>
        <div class="row mt5">
            <div class="col-md-1"></div>
            <div class="col-md-2 bg-info pv5 text-right"><strong>SKU Count</strong></div>
            <div class="col-md-3 pv5"><s:property value="rcv.skuCount" /></div>
            <div class="col-md-2 bg-info pv5 text-right"><strong>Unit Count</strong></div>
            <div class="col-md-3 pv5"><s:property value="rcv.unitCount" /></div>
            <div class="col-md-1"></div>
        </div>
        <div class="row mt5">
            <div class="col-md-1"></div>
            <div class="col-md-10 text-center bg-info">
                <span class="text-center pv5"><strong>All Damaged Items Images for ASN</strong></span>
            </div>
            <div class="col-md-1"></div>
        </div>
        <div class="row mt5">
            <div class="col-md-1"></div>
            <div class="col-md-10">
                <s:iterator value="imagePacks">
                    <div class="col-md-2">
                        <a href="<s:property value='notes' />"><s:property value="name" /></a>
                    </div>
                </s:iterator>
            </div>
            <div class="col-md-1"></div>
        </div>
        <div class="row mt5">
            <div class="col-md-1"></div>
            <div class="col-md-10 text-center bg-info">
                <span class="text-center pv5"><strong>Received Items</strong></span>
            </div>
            <div class="col-md-1"></div>
        </div>
        <div class="row mt5">
            <div class="col-md-1"></div>
            <div class="col-md-10">
                <div class="row mt5">
                    <div class="col-md-2"><strong>SKU</strong></div>
                    <div class="col-md-2"><strong>Description</strong></div>
                    <div class="col-md-2"><strong>Received</strong></div>
                    <div class="col-md-2"><strong>Damaged</strong></div>
                    <div class="col-md-2"><strong>Notes</strong></div>
                    <div class="col-md-2"><strong>Images</strong></div>
                </div>
                <s:iterator value="lineItems">
                    <div class="row mt5">
                        <div class="col-md-2"><s:property value="inventoryNum" /></div>
                        <div class="col-md-2"><s:property value="description" /></div>
                        <div class="col-md-2"><s:property value="qtyReceived" /></div>
                        <div class="col-md-2"><s:property value="damaged" /></div>
                        <div class="col-md-2"><s:property value="notes" /></div>
                        <div class="col-md-2"><s:property value="images" /></div>
                        <div class="col-md-2"><s:property value="lineItem" /></div>
                    </div>
                </s:iterator>
            </div>
            <div class="col-md-1"></div>
        </div>
    </div>
</body>
</html>