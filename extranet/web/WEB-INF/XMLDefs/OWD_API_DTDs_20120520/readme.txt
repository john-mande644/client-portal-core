
One World Direct XML API Readme file

Documentation of the XML API elements, attributes and usage are in the DTD files in this folder.

OWD's API consists of exchanging XML formatted data over HTTP, using POST data payloads and HTTP response data.

There are two types of documentation DTDs - files ending in *_api.dtd, and others.

Any _api file represents a collection of requests and response definitions related to a single entity type. For example, ASN (Advance Ship Notice) calls and responses are all located in the owd_asn_api.dtd file.

The other type has request and response types in separate dtd files. For these, it is best to open the request file you are interested in, which will specify the response for the given request.

The bottom of each did file typically has some examples of complete data transmissions.

All requests are wrapped in a single OWD_API_REQUEST element. Likewise, all responses are wrapped in a single OWD_API_RESPONSE element.

The URL to use when posting requests to our API is:

https://secure.owd.com/webapps/api/api.jsp

The XML request should be sent as POST data in an HTTP POST request, with a Content-Type of "text/xml". The assumed XML text encoding is UTF-8. If you are using ISO-8859 encoding, that should be specified in the XML document preamble. We will reply with the same encoding as the request.

Following are some brief notes on where to look for various functions:

OWD_API_REQUEST.dtd
OWD_API_RESPONSE.dtd

These two files define the wrapper elements and provide generic request or response level information. You should review the attributes for both elements before making your first request.

OWD_ORDER_CREATE_REQUEST.dtd
OWD_ORDER_RELEASE_REQUEST.dtd
OWD_ORDER_CANCEL_REQUEST.dtd
OWD_ORDER_STATUS_REQUEST.dtd
OWD_ORDER_SUMMARY_RESPONSE.dtd
OWD_ORDER_STATUS_RESPONSE.dtd

These are the order-related DTDs. There is no order updating API at this time, but it is in development. Most calls return an ORDER_STATUS_RESPONSE, but the ORDER_STATUS_REQUEST can optionally return an ORDER_SUMMARY_RESPONSE instead, which just provides details relevant after an order has shipped.

OWD_INVENTORY_API.dtd
OWD_INVENTORY_COUNT_REQUEST.dtd
OWD_INVENTORY_COUNT_RESPONSE.dtd
OWD_INVENTORY_REPORT_API.dtd

These files are related to SKUs (inventory items and stock information). The INVENTORY_API document describes searching for, creating and updating inventory records. The COUNT dtds describe a specialized call to retrieve stock level status for one or all SKUs. The INVENTORY_REPORT provides a summary of all inventory movements for a period, including receives, returns and other adjustments.

OWD_ASN_API.dtd

This file contains all functions related to creating, updating, searching and deleting ASN records. Asn status reposes provide information on receives performed against that ASN. Note that shipments received without an ASN will cause OWD to generate a new "blind" ASN that will be visible in these functions as well as the OWD_INVENTORY_REPORT API (above).

OWD_SHIPPING_RATE_API.dtd

This file describes a call to obtain shipping rates and validate addresses for a given shipping address and collection of items.

OWD_SUBSCRIPTION_API.dtd

This file provides functions to work with recurring orders that are generated and shipped on a periodic basis automatically.


A sample order creation request is (whitespace/linefeeds inserted for clarity - real requests would strip all whitespace between elements):

<?xml version="1.0"?>
<OWD_API_REQUEST api_version="1.0" client_id="9999" client_authorization="xxxxxxxxxxxx=" testing="TRUE">
    <OWD_ORDER_CREATE_REQUEST order_reference="71698-1" order_source="Web Order" is_gift="NO" backorder_rule="NOBACKORDER">
        <SHIPPING_INFO last_name="Ghany" first_name="Faheemah" company_name="" address_one="48 GILL LANE APT 1B" address_two="" city="ISELIN" state="NJ" zip="08830" country="USA" phone="+1 (513) 8842986" email="" ship_type="UPS.GND" ship_cost="0" />
        <BILLING_INFO last_name="GHANY" first_name="FAZEELA" company_name="" address_one="Evenlyn Trace" address_two="" city="San Juan" state="" zip="12345" country="US" phone="+1 (868) 6380154" email="faheemah28@hotmail.com" payment_type="CLIENT" order_discount="0" paid="YES" />
        <LINE_ITEM part_reference="ABUR2" description="Burdah" requested="1" cost="12.95" />
        <LINE_ITEM part_reference="BSAF" description="Shapes and Forms" requested="2" cost="0.00" />
    </OWD_ORDER_CREATE_REQUEST>
</OWD_API_REQUEST>

We have also included a CSV copy of our list of shipping methods and codes. The "ship_type" attribute of the SHIPPING_INFO element is the attribute that takes as a value one of the shipping codes listed.

Any questions can be directed to servicerequests@owd.com for assistance. Up and running support for developers implementing our API is always free and unlimited.

