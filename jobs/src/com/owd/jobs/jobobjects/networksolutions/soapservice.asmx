<?xml version="1.0" encoding="utf-8"?>
<wsdl:definitions xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:tm="http://microsoft.com/wsdl/mime/textMatching/" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/" xmlns:mime="http://schemas.xmlsoap.org/wsdl/mime/" xmlns:tns="urn:networksolutions:apis" xmlns:s="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/" xmlns:http="http://schemas.xmlsoap.org/wsdl/http/" targetNamespace="urn:networksolutions:apis" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">
  <wsdl:types>
    <s:schema elementFormDefault="qualified" targetNamespace="urn:networksolutions:apis">
      <s:element name="CreateWarehouseRequest" type="tns:CreateWarehouseRequestType" />
      <s:complexType name="CreateWarehouseRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Warehouse" type="tns:WarehouseType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="BaseRequestType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="RequestId" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Version" type="s:decimal" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="DeleteAttributeRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Attribute" type="tns:AttributeType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="AttributeType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="SortOrder" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Display" type="tns:AttributeDisplayCodeType" />
          <s:element minOccurs="0" maxOccurs="unbounded" name="AttributeValueList" type="tns:AttributeValueType" />
        </s:sequence>
        <s:attribute form="unqualified" name="AttributeId" type="s:long" />
      </s:complexType>
      <s:simpleType name="AttributeDisplayCodeType">
        <s:restriction base="s:string">
          <s:enumeration value="CheckBox" />
          <s:enumeration value="CheckBoxList" />
          <s:enumeration value="Text" />
          <s:enumeration value="Custom" />
        </s:restriction>
      </s:simpleType>
      <s:complexType name="AttributeValueType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Value" type="s:string" />
        </s:sequence>
        <s:attribute form="unqualified" name="AttributeValueId" type="s:long" />
        <s:attribute form="unqualified" name="Delete" type="s:boolean" />
        <s:attribute form="unqualified" name="Disassociate" type="s:boolean" />
      </s:complexType>
      <s:complexType name="GetUserTokenRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="UserToken" type="tns:UserTokenType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="UserTokenType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="UserKey" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="UserToken" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Expiration" type="s:dateTime" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="UpdateWarehouseRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Warehouse" type="tns:WarehouseType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="WarehouseType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="EmailAddress" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="EmailHeader" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="EmailPricing" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="EmailProducts" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="EmailShipping" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="Enabled" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="Notes" type="s:string" />
        </s:sequence>
        <s:attribute form="unqualified" name="WarehouseId" type="s:long" />
        <s:attribute form="unqualified" name="Disassociate" type="s:boolean" />
      </s:complexType>
      <s:complexType name="DeleteWarehouseRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Warehouse" type="tns:WarehouseType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="CreateOrderStatusRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="OrderStatus" type="tns:OrderStatusType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="OrderStatusType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="EmailCustomer" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="EmailAdministrator" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="Parent" type="tns:OrderStatusType" />
          <s:element minOccurs="0" maxOccurs="unbounded" name="NextStatusList" type="tns:OrderStatusType" />
        </s:sequence>
        <s:attribute form="unqualified" name="OrderStatusId" type="s:long" />
      </s:complexType>
      <s:complexType name="UpdateOrderStatusRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="OrderStatus" type="tns:OrderStatusType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="DeleteOrderStatusRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="OrderStatus" type="tns:OrderStatusType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="CreateCategoryRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Category" type="tns:CategoryType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="CategoryType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Description" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="FullDescription" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Depth" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Enabled" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="SortOrder" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="PageUrl" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Parent" type="tns:CategoryType" />
          <s:element minOccurs="0" maxOccurs="1" name="Image" type="tns:ImageType" />
          <s:element minOccurs="0" maxOccurs="1" name="SearchInformation" type="tns:SearchInformationType" />
        </s:sequence>
        <s:attribute form="unqualified" name="CategoryId" type="s:long" />
        <s:attribute form="unqualified" name="Disassociate" type="s:boolean" />
      </s:complexType>
      <s:complexType name="ImageType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="PathUrl" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="AlternateText" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="SearchInformationType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="PageTitle" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="MetaKeyword" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="MetaDescription" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="UpdateCategoryRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Category" type="tns:CategoryType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="DeleteCategoryRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Category" type="tns:CategoryType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="CreateManufacturerRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Manufacturer" type="tns:ManufacturerType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ManufacturerType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Description" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="SortOrder" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Image" type="tns:ImageType" />
        </s:sequence>
        <s:attribute form="unqualified" name="ManufacturerId" type="s:long" />
        <s:attribute form="unqualified" name="Disassociate" type="s:boolean" />
      </s:complexType>
      <s:complexType name="UpdateManufacturerRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Manufacturer" type="tns:ManufacturerType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="DeleteManufacturerRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Manufacturer" type="tns:ManufacturerType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="CreateGiftCertificateRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="GiftCertificate" type="tns:GiftCertificateType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="GiftCertificateType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Adjustment" type="tns:AmountType" />
          <s:element minOccurs="0" maxOccurs="1" name="CreateTime" type="s:dateTime" />
          <s:element minOccurs="0" maxOccurs="1" name="Expiration" type="s:dateTime" />
          <s:element minOccurs="0" maxOccurs="1" name="GiftCertificateStatus" type="tns:GiftCertificateStatusCodeType" />
          <s:element minOccurs="0" maxOccurs="1" name="Notes" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Original" type="tns:AmountType" />
          <s:element minOccurs="0" maxOccurs="1" name="Remaining" type="tns:AmountType" />
          <s:element minOccurs="0" maxOccurs="1" name="Order" type="tns:OrderType" />
          <s:element minOccurs="0" maxOccurs="1" name="Product" type="tns:ProductType" />
        </s:sequence>
        <s:attribute form="unqualified" name="Code" type="s:string" />
      </s:complexType>
      <s:complexType name="AmountType">
        <s:simpleContent>
          <s:extension base="s:decimal">
            <s:attribute form="unqualified" name="Currency" type="tns:CurrencyCodeType" />
          </s:extension>
        </s:simpleContent>
      </s:complexType>
      <s:simpleType name="CurrencyCodeType">
        <s:restriction base="s:string">
          <s:enumeration value="AED" />
          <s:enumeration value="AFN" />
          <s:enumeration value="ALL" />
          <s:enumeration value="AMD" />
          <s:enumeration value="ANG" />
          <s:enumeration value="AOA" />
          <s:enumeration value="ARS" />
          <s:enumeration value="AUD" />
          <s:enumeration value="AWG" />
          <s:enumeration value="AZN" />
          <s:enumeration value="BAM" />
          <s:enumeration value="BBD" />
          <s:enumeration value="BDT" />
          <s:enumeration value="BGN" />
          <s:enumeration value="BHD" />
          <s:enumeration value="BIF" />
          <s:enumeration value="BMD" />
          <s:enumeration value="BND" />
          <s:enumeration value="BOB" />
          <s:enumeration value="BRL" />
          <s:enumeration value="BSD" />
          <s:enumeration value="BTN" />
          <s:enumeration value="BWP" />
          <s:enumeration value="BYR" />
          <s:enumeration value="BZD" />
          <s:enumeration value="CAD" />
          <s:enumeration value="CDF" />
          <s:enumeration value="CHF" />
          <s:enumeration value="CLP" />
          <s:enumeration value="CNY" />
          <s:enumeration value="COP" />
          <s:enumeration value="CRC" />
          <s:enumeration value="CUC" />
          <s:enumeration value="CUP" />
          <s:enumeration value="CVE" />
          <s:enumeration value="CZK" />
          <s:enumeration value="DJF" />
          <s:enumeration value="DKK" />
          <s:enumeration value="DOP" />
          <s:enumeration value="DZD" />
          <s:enumeration value="EEK" />
          <s:enumeration value="EGP" />
          <s:enumeration value="ERN" />
          <s:enumeration value="ETB" />
          <s:enumeration value="EUR" />
          <s:enumeration value="FJD" />
          <s:enumeration value="FKP" />
          <s:enumeration value="GBP" />
          <s:enumeration value="GEL" />
          <s:enumeration value="GGP" />
          <s:enumeration value="GHS" />
          <s:enumeration value="GIP" />
          <s:enumeration value="GMD" />
          <s:enumeration value="GNF" />
          <s:enumeration value="GTQ" />
          <s:enumeration value="GYD" />
          <s:enumeration value="HKD" />
          <s:enumeration value="HNL" />
          <s:enumeration value="HRK" />
          <s:enumeration value="HTG" />
          <s:enumeration value="HUF" />
          <s:enumeration value="IDR" />
          <s:enumeration value="ILS" />
          <s:enumeration value="IMP" />
          <s:enumeration value="INR" />
          <s:enumeration value="IQD" />
          <s:enumeration value="IRR" />
          <s:enumeration value="ISK" />
          <s:enumeration value="JEP" />
          <s:enumeration value="JMD" />
          <s:enumeration value="JOD" />
          <s:enumeration value="JPY" />
          <s:enumeration value="KES" />
          <s:enumeration value="KGS" />
          <s:enumeration value="KHR" />
          <s:enumeration value="KMF" />
          <s:enumeration value="KPW" />
          <s:enumeration value="KRW" />
          <s:enumeration value="KWD" />
          <s:enumeration value="KYD" />
          <s:enumeration value="KZT" />
          <s:enumeration value="LAK" />
          <s:enumeration value="LBP" />
          <s:enumeration value="LKR" />
          <s:enumeration value="LRD" />
          <s:enumeration value="LSL" />
          <s:enumeration value="LTL" />
          <s:enumeration value="LVL" />
          <s:enumeration value="LYD" />
          <s:enumeration value="MAD" />
          <s:enumeration value="MDL" />
          <s:enumeration value="MGA" />
          <s:enumeration value="MKD" />
          <s:enumeration value="MMK" />
          <s:enumeration value="MNT" />
          <s:enumeration value="MOP" />
          <s:enumeration value="MRO" />
          <s:enumeration value="MUR" />
          <s:enumeration value="MVR" />
          <s:enumeration value="MWK" />
          <s:enumeration value="MXN" />
          <s:enumeration value="MYR" />
          <s:enumeration value="MZN" />
          <s:enumeration value="NAD" />
          <s:enumeration value="NGN" />
          <s:enumeration value="NIO" />
          <s:enumeration value="NOK" />
          <s:enumeration value="NPR" />
          <s:enumeration value="NZD" />
          <s:enumeration value="OMR" />
          <s:enumeration value="PAB" />
          <s:enumeration value="PEN" />
          <s:enumeration value="PGK" />
          <s:enumeration value="PHP" />
          <s:enumeration value="PKR" />
          <s:enumeration value="PLN" />
          <s:enumeration value="PYG" />
          <s:enumeration value="QAR" />
          <s:enumeration value="RON" />
          <s:enumeration value="RSD" />
          <s:enumeration value="RUB" />
          <s:enumeration value="RWF" />
          <s:enumeration value="SAR" />
          <s:enumeration value="SBD" />
          <s:enumeration value="SCR" />
          <s:enumeration value="SDG" />
          <s:enumeration value="SEK" />
          <s:enumeration value="SGD" />
          <s:enumeration value="SHP" />
          <s:enumeration value="SLL" />
          <s:enumeration value="SOS" />
          <s:enumeration value="SPL" />
          <s:enumeration value="SRD" />
          <s:enumeration value="STD" />
          <s:enumeration value="SVC" />
          <s:enumeration value="SYP" />
          <s:enumeration value="SZL" />
          <s:enumeration value="THB" />
          <s:enumeration value="TJS" />
          <s:enumeration value="TMM" />
          <s:enumeration value="TMT" />
          <s:enumeration value="TND" />
          <s:enumeration value="TOP" />
          <s:enumeration value="TRY" />
          <s:enumeration value="TTD" />
          <s:enumeration value="TVD" />
          <s:enumeration value="TWD" />
          <s:enumeration value="TZS" />
          <s:enumeration value="UAH" />
          <s:enumeration value="UGX" />
          <s:enumeration value="USD" />
          <s:enumeration value="UYU" />
          <s:enumeration value="UZS" />
          <s:enumeration value="VEB" />
          <s:enumeration value="VEF" />
          <s:enumeration value="VND" />
          <s:enumeration value="VUV" />
          <s:enumeration value="WST" />
          <s:enumeration value="XAF" />
          <s:enumeration value="XCD" />
          <s:enumeration value="XOF" />
          <s:enumeration value="XPF" />
          <s:enumeration value="YER" />
          <s:enumeration value="ZAR" />
          <s:enumeration value="ZMK" />
          <s:enumeration value="ZWD" />
          <s:enumeration value="Custom" />
        </s:restriction>
      </s:simpleType>
      <s:simpleType name="GiftCertificateStatusCodeType">
        <s:restriction base="s:string">
          <s:enumeration value="Active" />
          <s:enumeration value="Discharged" />
          <s:enumeration value="Expired" />
          <s:enumeration value="Custom" />
        </s:restriction>
      </s:simpleType>
      <s:complexType name="OrderType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="AdminNotes" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Archived" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="CreateDate" type="s:dateTime" />
          <s:element minOccurs="0" maxOccurs="1" name="Notes" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="SalesRep" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="AffiliateCode" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="ReferringURL" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Customer" type="tns:CustomerType" />
          <s:element minOccurs="0" maxOccurs="1" name="Invoice" type="tns:InvoiceType" />
          <s:element minOccurs="0" maxOccurs="1" name="Payment" type="tns:PaymentType" />
          <s:element minOccurs="0" maxOccurs="1" name="Shipping" type="tns:ShippingType" />
          <s:element minOccurs="0" maxOccurs="1" name="Status" type="tns:OrderStatusType" />
          <s:element minOccurs="0" maxOccurs="unbounded" name="QuestionList" type="tns:QuestionType" />
        </s:sequence>
        <s:attribute form="unqualified" name="OrderId" type="s:long" />
        <s:attribute form="unqualified" name="OrderNumber" type="s:string" />
      </s:complexType>
      <s:complexType name="CustomerType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Browser" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Registered" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="IpAddress" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="EmailAddress" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Password" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="NonTaxable" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="TaxIdentification" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="BillingAddress" type="tns:AddressType" />
          <s:element minOccurs="0" maxOccurs="1" name="ShippingAddress" type="tns:AddressType" />
          <s:element minOccurs="0" maxOccurs="1" name="PriceLevel" type="tns:PriceLevelType" />
          <s:element minOccurs="0" maxOccurs="1" name="CreateDate" type="s:dateTime" />
        </s:sequence>
        <s:attribute form="unqualified" name="CustomerId" type="s:string" />
        <s:attribute form="unqualified" name="CustomerNumber" type="s:string" />
      </s:complexType>
      <s:complexType name="AddressType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="FirstName" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="LastName" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Company" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Address1" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Address2" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="City" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="StateProvince" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="PostalCode" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Country" type="tns:CountryCodeType" />
          <s:element minOccurs="0" maxOccurs="1" name="Phone" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:simpleType name="CountryCodeType">
        <s:restriction base="s:string">
          <s:enumeration value="AF" />
          <s:enumeration value="AX" />
          <s:enumeration value="AL" />
          <s:enumeration value="DZ" />
          <s:enumeration value="AS" />
          <s:enumeration value="AD" />
          <s:enumeration value="AO" />
          <s:enumeration value="AI" />
          <s:enumeration value="AQ" />
          <s:enumeration value="AG" />
          <s:enumeration value="AR" />
          <s:enumeration value="AM" />
          <s:enumeration value="AW" />
          <s:enumeration value="AU" />
          <s:enumeration value="AT" />
          <s:enumeration value="AZ" />
          <s:enumeration value="BS" />
          <s:enumeration value="BH" />
          <s:enumeration value="BD" />
          <s:enumeration value="BB" />
          <s:enumeration value="BY" />
          <s:enumeration value="BE" />
          <s:enumeration value="BZ" />
          <s:enumeration value="BJ" />
          <s:enumeration value="BM" />
          <s:enumeration value="BT" />
          <s:enumeration value="BO" />
          <s:enumeration value="BA" />
          <s:enumeration value="BW" />
          <s:enumeration value="BV" />
          <s:enumeration value="BR" />
          <s:enumeration value="IO" />
          <s:enumeration value="BN" />
          <s:enumeration value="BG" />
          <s:enumeration value="BF" />
          <s:enumeration value="BI" />
          <s:enumeration value="KH" />
          <s:enumeration value="CM" />
          <s:enumeration value="CA" />
          <s:enumeration value="CV" />
          <s:enumeration value="KY" />
          <s:enumeration value="CF" />
          <s:enumeration value="TD" />
          <s:enumeration value="CL" />
          <s:enumeration value="CN" />
          <s:enumeration value="CX" />
          <s:enumeration value="CC" />
          <s:enumeration value="CO" />
          <s:enumeration value="KM" />
          <s:enumeration value="CG" />
          <s:enumeration value="CD" />
          <s:enumeration value="CK" />
          <s:enumeration value="CR" />
          <s:enumeration value="CI" />
          <s:enumeration value="HR" />
          <s:enumeration value="CU" />
          <s:enumeration value="CY" />
          <s:enumeration value="CZ" />
          <s:enumeration value="DK" />
          <s:enumeration value="DJ" />
          <s:enumeration value="DM" />
          <s:enumeration value="DO" />
          <s:enumeration value="EC" />
          <s:enumeration value="EG" />
          <s:enumeration value="SV" />
          <s:enumeration value="GQ" />
          <s:enumeration value="ER" />
          <s:enumeration value="EE" />
          <s:enumeration value="ET" />
          <s:enumeration value="FK" />
          <s:enumeration value="FO" />
          <s:enumeration value="FJ" />
          <s:enumeration value="FI" />
          <s:enumeration value="FR" />
          <s:enumeration value="GF" />
          <s:enumeration value="PF" />
          <s:enumeration value="TF" />
          <s:enumeration value="GA" />
          <s:enumeration value="GM" />
          <s:enumeration value="GE" />
          <s:enumeration value="DE" />
          <s:enumeration value="GH" />
          <s:enumeration value="GI" />
          <s:enumeration value="GR" />
          <s:enumeration value="GL" />
          <s:enumeration value="GD" />
          <s:enumeration value="GP" />
          <s:enumeration value="GU" />
          <s:enumeration value="GT" />
          <s:enumeration value="GG" />
          <s:enumeration value="GN" />
          <s:enumeration value="GW" />
          <s:enumeration value="GY" />
          <s:enumeration value="HT" />
          <s:enumeration value="HM" />
          <s:enumeration value="VA" />
          <s:enumeration value="HN" />
          <s:enumeration value="HK" />
          <s:enumeration value="HU" />
          <s:enumeration value="IS" />
          <s:enumeration value="IN" />
          <s:enumeration value="ID" />
          <s:enumeration value="IR" />
          <s:enumeration value="IQ" />
          <s:enumeration value="IE" />
          <s:enumeration value="IM" />
          <s:enumeration value="IL" />
          <s:enumeration value="IT" />
          <s:enumeration value="JM" />
          <s:enumeration value="JP" />
          <s:enumeration value="JE" />
          <s:enumeration value="JO" />
          <s:enumeration value="KZ" />
          <s:enumeration value="KE" />
          <s:enumeration value="KI" />
          <s:enumeration value="KP" />
          <s:enumeration value="KR" />
          <s:enumeration value="KW" />
          <s:enumeration value="KG" />
          <s:enumeration value="LA" />
          <s:enumeration value="LV" />
          <s:enumeration value="LB" />
          <s:enumeration value="LS" />
          <s:enumeration value="LR" />
          <s:enumeration value="LY" />
          <s:enumeration value="LI" />
          <s:enumeration value="LT" />
          <s:enumeration value="LU" />
          <s:enumeration value="MO" />
          <s:enumeration value="MK" />
          <s:enumeration value="MG" />
          <s:enumeration value="MW" />
          <s:enumeration value="MY" />
          <s:enumeration value="MV" />
          <s:enumeration value="ML" />
          <s:enumeration value="MT" />
          <s:enumeration value="MH" />
          <s:enumeration value="MQ" />
          <s:enumeration value="MR" />
          <s:enumeration value="MU" />
          <s:enumeration value="YT" />
          <s:enumeration value="MX" />
          <s:enumeration value="FM" />
          <s:enumeration value="MD" />
          <s:enumeration value="MC" />
          <s:enumeration value="MN" />
          <s:enumeration value="MS" />
          <s:enumeration value="MA" />
          <s:enumeration value="MZ" />
          <s:enumeration value="MM" />
          <s:enumeration value="NA" />
          <s:enumeration value="NR" />
          <s:enumeration value="NP" />
          <s:enumeration value="NL" />
          <s:enumeration value="AN" />
          <s:enumeration value="NC" />
          <s:enumeration value="NZ" />
          <s:enumeration value="NI" />
          <s:enumeration value="NE" />
          <s:enumeration value="NG" />
          <s:enumeration value="NU" />
          <s:enumeration value="NF" />
          <s:enumeration value="MP" />
          <s:enumeration value="NO" />
          <s:enumeration value="OM" />
          <s:enumeration value="PK" />
          <s:enumeration value="PW" />
          <s:enumeration value="PS" />
          <s:enumeration value="PA" />
          <s:enumeration value="PG" />
          <s:enumeration value="PY" />
          <s:enumeration value="PE" />
          <s:enumeration value="PH" />
          <s:enumeration value="PN" />
          <s:enumeration value="PL" />
          <s:enumeration value="PT" />
          <s:enumeration value="PR" />
          <s:enumeration value="QA" />
          <s:enumeration value="RE" />
          <s:enumeration value="RO" />
          <s:enumeration value="RU" />
          <s:enumeration value="RW" />
          <s:enumeration value="SH" />
          <s:enumeration value="KN" />
          <s:enumeration value="LC" />
          <s:enumeration value="PM" />
          <s:enumeration value="VC" />
          <s:enumeration value="WS" />
          <s:enumeration value="SM" />
          <s:enumeration value="ST" />
          <s:enumeration value="SA" />
          <s:enumeration value="SN" />
          <s:enumeration value="CS" />
          <s:enumeration value="SC" />
          <s:enumeration value="SL" />
          <s:enumeration value="SG" />
          <s:enumeration value="SK" />
          <s:enumeration value="SI" />
          <s:enumeration value="SB" />
          <s:enumeration value="SO" />
          <s:enumeration value="ZA" />
          <s:enumeration value="GS" />
          <s:enumeration value="ES" />
          <s:enumeration value="LK" />
          <s:enumeration value="SD" />
          <s:enumeration value="SR" />
          <s:enumeration value="SJ" />
          <s:enumeration value="SZ" />
          <s:enumeration value="SE" />
          <s:enumeration value="CH" />
          <s:enumeration value="SY" />
          <s:enumeration value="TW" />
          <s:enumeration value="TJ" />
          <s:enumeration value="TZ" />
          <s:enumeration value="TH" />
          <s:enumeration value="TL" />
          <s:enumeration value="TG" />
          <s:enumeration value="TK" />
          <s:enumeration value="TO" />
          <s:enumeration value="TT" />
          <s:enumeration value="TN" />
          <s:enumeration value="TR" />
          <s:enumeration value="TM" />
          <s:enumeration value="TC" />
          <s:enumeration value="TV" />
          <s:enumeration value="UG" />
          <s:enumeration value="UA" />
          <s:enumeration value="AE" />
          <s:enumeration value="GB" />
          <s:enumeration value="US" />
          <s:enumeration value="UM" />
          <s:enumeration value="UY" />
          <s:enumeration value="UZ" />
          <s:enumeration value="VU" />
          <s:enumeration value="VE" />
          <s:enumeration value="VN" />
          <s:enumeration value="VG" />
          <s:enumeration value="VI" />
          <s:enumeration value="WF" />
          <s:enumeration value="EH" />
          <s:enumeration value="YE" />
          <s:enumeration value="ZM" />
          <s:enumeration value="ZW" />
          <s:enumeration value="RS" />
          <s:enumeration value="ME" />
          <s:enumeration value="Custom" />
        </s:restriction>
      </s:simpleType>
      <s:complexType name="PriceLevelType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="BasePrice" type="tns:AmountType" />
          <s:element minOccurs="0" maxOccurs="1" name="UnitPrice" type="tns:AmountType" />
          <s:element minOccurs="0" maxOccurs="1" name="Message" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="PreventPurchase" type="s:boolean" />
        </s:sequence>
        <s:attribute form="unqualified" name="PriceLevelId" type="s:long" />
        <s:attribute form="unqualified" name="Disassociate" type="s:boolean" />
      </s:complexType>
      <s:complexType name="InvoiceType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Subtotal" type="tns:AmountType" />
          <s:element minOccurs="0" maxOccurs="1" name="Handling" type="tns:AmountType" />
          <s:element minOccurs="0" maxOccurs="1" name="Shipping" type="tns:AmountType" />
          <s:element minOccurs="0" maxOccurs="1" name="Tax" type="tns:TaxAppliedType" />
          <s:element minOccurs="0" maxOccurs="1" name="Surcharge" type="tns:AmountType" />
          <s:element minOccurs="0" maxOccurs="1" name="Total" type="tns:AmountType" />
          <s:element minOccurs="0" maxOccurs="1" name="Discount" type="tns:AmountType" />
          <s:element minOccurs="0" maxOccurs="1" name="GiftCertificate" type="tns:AmountType" />
          <s:element minOccurs="0" maxOccurs="1" name="CombineHandling" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="GiftCertificateCode" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Weight" type="tns:WeightType" />
          <s:element minOccurs="0" maxOccurs="unbounded" name="LineItemList" type="tns:LineItemType" />
          <s:element minOccurs="0" maxOccurs="1" name="DiscountCode" type="s:string" />
          <s:element minOccurs="0" maxOccurs="unbounded" name="OrderDiscountList" type="tns:DiscountType" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="TaxAppliedType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Amount" type="s:decimal" />
          <s:element minOccurs="0" maxOccurs="1" name="Handling" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="Rate" type="s:decimal" />
          <s:element minOccurs="0" maxOccurs="1" name="Shipping" type="s:boolean" />
        </s:sequence>
        <s:attribute form="unqualified" name="Currency" type="tns:CurrencyCodeType" />
      </s:complexType>
      <s:complexType name="WeightType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Major" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Minor" type="s:decimal" />
          <s:element minOccurs="0" maxOccurs="1" name="Length" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Width" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Height" type="s:int" />
        </s:sequence>
        <s:attribute form="unqualified" name="UnitOfMeasure" type="tns:MeasureCodeType" />
      </s:complexType>
      <s:simpleType name="MeasureCodeType">
        <s:restriction base="s:string">
          <s:enumeration value="Imperial" />
          <s:enumeration value="Metric" />
          <s:enumeration value="Custom" />
        </s:restriction>
      </s:simpleType>
      <s:complexType name="LineItemType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="FreeShipping" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="NonTaxable" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="PartNumber" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="ProductId" type="s:long" />
          <s:element minOccurs="0" maxOccurs="1" name="ProductClass" type="tns:ProductCodeType" />
          <s:element minOccurs="0" maxOccurs="1" name="QtySold" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Weight" type="tns:WeightType" />
          <s:element minOccurs="0" maxOccurs="1" name="UnitPrice" type="tns:AmountType" />
          <s:element minOccurs="0" maxOccurs="1" name="ShippingOption" type="tns:ShippingOptionCodeType" />
          <s:element minOccurs="0" maxOccurs="unbounded" name="QuestionList" type="tns:QuestionType" />
          <s:element minOccurs="0" maxOccurs="unbounded" name="SelectedVariationList" type="tns:SelectedVariationType" />
        </s:sequence>
        <s:attribute form="unqualified" name="LineItemId" type="s:long" />
      </s:complexType>
      <s:simpleType name="ProductCodeType">
        <s:restriction base="s:string">
          <s:enumeration value="Regular" />
          <s:enumeration value="GiftCertificate" />
          <s:enumeration value="Electronic" />
          <s:enumeration value="Custom" />
        </s:restriction>
      </s:simpleType>
      <s:simpleType name="ShippingOptionCodeType">
        <s:restriction base="s:string">
          <s:enumeration value="Shippable" />
          <s:enumeration value="Free" />
          <s:enumeration value="NonShippable" />
          <s:enumeration value="Custom" />
        </s:restriction>
      </s:simpleType>
      <s:complexType name="QuestionType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Title" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Display" type="tns:DisplayCodeType" />
          <s:element minOccurs="0" maxOccurs="1" name="Enabled" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="Required" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="SortOrder" type="s:int" />
          <s:choice minOccurs="0" maxOccurs="unbounded">
            <s:element minOccurs="0" maxOccurs="1" name="BooleanAnswerList" type="tns:BooleanAnswerType" />
            <s:element minOccurs="0" maxOccurs="1" name="TextAnswerList" type="tns:TextAnswerType" />
          </s:choice>
        </s:sequence>
        <s:attribute form="unqualified" name="QuestionId" type="s:long" />
        <s:attribute form="unqualified" name="Delete" type="s:boolean" />
      </s:complexType>
      <s:simpleType name="DisplayCodeType">
        <s:restriction base="s:string">
          <s:enumeration value="CheckBox" />
          <s:enumeration value="CheckBoxList" />
          <s:enumeration value="DropDown" />
          <s:enumeration value="Radio" />
          <s:enumeration value="Text" />
          <s:enumeration value="TextList" />
          <s:enumeration value="Custom" />
        </s:restriction>
      </s:simpleType>
      <s:complexType name="BooleanAnswerType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Thumbnail" type="tns:ImageType" />
          <s:element minOccurs="0" maxOccurs="1" name="Answer" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Surcharge" type="tns:AmountType" />
          <s:element minOccurs="0" maxOccurs="1" name="Value" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="SortOrder" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Enabled" type="s:boolean" />
        </s:sequence>
        <s:attribute form="unqualified" name="BooleanAnswerId" type="s:long" />
        <s:attribute form="unqualified" name="Delete" type="s:boolean" />
      </s:complexType>
      <s:complexType name="TextAnswerType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Label" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Thumbnail" type="tns:ImageType" />
          <s:element minOccurs="0" maxOccurs="1" name="Surcharge" type="tns:AmountType" />
          <s:element minOccurs="0" maxOccurs="1" name="CharacterCharge" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="Rows" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="CharactersPerRow" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Value" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="MaxLength" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="SortOrder" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Enabled" type="s:boolean" />
        </s:sequence>
        <s:attribute form="unqualified" name="TextAnswerId" type="s:long" />
        <s:attribute form="unqualified" name="Delete" type="s:boolean" />
      </s:complexType>
      <s:complexType name="SelectedVariationType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Group" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Option" type="s:string" />
        </s:sequence>
        <s:attribute form="unqualified" name="VariationOptionId" type="s:long" />
      </s:complexType>
      <s:complexType name="DiscountType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Code" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Applied" type="tns:AmountType" />
          <s:element minOccurs="0" maxOccurs="1" name="DiscountType" type="tns:DiscountTypeCodeType" />
        </s:sequence>
      </s:complexType>
      <s:simpleType name="DiscountTypeCodeType">
        <s:restriction base="s:string">
          <s:enumeration value="Order" />
          <s:enumeration value="Product" />
          <s:enumeration value="Quantity" />
          <s:enumeration value="Shipping" />
          <s:enumeration value="Custom" />
        </s:restriction>
      </s:simpleType>
      <s:complexType name="PaymentType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="AuthorizationId" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="TransactionId" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="CreditCard" type="tns:CreditCardType" />
          <s:element minOccurs="0" maxOccurs="1" name="OtherPayment" type="tns:OtherPaymentType" />
          <s:element minOccurs="0" maxOccurs="1" name="PaymentMethod" type="tns:PaymentMethodCodeType" />
          <s:element minOccurs="0" maxOccurs="1" name="PaymentStatus" type="tns:PaymentStatusCodeType" />
          <s:element minOccurs="0" maxOccurs="1" name="CreateDate" type="s:dateTime" />
        </s:sequence>
        <s:attribute form="unqualified" name="OrderId" type="s:long" />
        <s:attribute form="unqualified" name="OrderNumber" type="s:string" />
      </s:complexType>
      <s:complexType name="CreditCardType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Expiration" type="s:date" />
          <s:element minOccurs="0" maxOccurs="1" name="FirstName" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="LastName" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Number" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Issuer" type="tns:CreditCardCodeType" />
          <s:element minOccurs="0" maxOccurs="1" name="Verification" type="tns:VerificationType" />
        </s:sequence>
      </s:complexType>
      <s:simpleType name="CreditCardCodeType">
        <s:restriction base="s:string">
          <s:enumeration value="AmericanExpress" />
          <s:enumeration value="Diners" />
          <s:enumeration value="Discover" />
          <s:enumeration value="JCB" />
          <s:enumeration value="MasterCard" />
          <s:enumeration value="Visa" />
          <s:enumeration value="Custom" />
        </s:restriction>
      </s:simpleType>
      <s:complexType name="VerificationType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Card" type="tns:VerificationCodeType" />
          <s:element minOccurs="0" maxOccurs="1" name="PostalCode" type="tns:VerificationCodeType" />
          <s:element minOccurs="0" maxOccurs="1" name="Street" type="tns:VerificationCodeType" />
        </s:sequence>
      </s:complexType>
      <s:simpleType name="VerificationCodeType">
        <s:restriction base="s:string">
          <s:enumeration value="Match" />
          <s:enumeration value="NoMatch" />
          <s:enumeration value="Unavailable" />
          <s:enumeration value="Custom" />
        </s:restriction>
      </s:simpleType>
      <s:complexType name="OtherPaymentType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Number" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="PaymentDate" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="PurchaseNumber" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:simpleType name="PaymentMethodCodeType">
        <s:restriction base="s:string">
          <s:enumeration value="AuthorizeNet" />
          <s:enumeration value="Cybersource" />
          <s:enumeration value="Google" />
          <s:enumeration value="LinkPoint" />
          <s:enumeration value="NetworkSolutions" />
          <s:enumeration value="NetworkMerchantsInc" />
          <s:enumeration value="NotRequired" />
          <s:enumeration value="OfflineAccount" />
          <s:enumeration value="OfflineCheck" />
          <s:enumeration value="OfflineCashOnDelivery" />
          <s:enumeration value="OfflineCreditCard" />
          <s:enumeration value="OfflineMoneyOrder" />
          <s:enumeration value="OfflineStorePayment" />
          <s:enumeration value="PayPalDirect" />
          <s:enumeration value="PayPalExpress" />
          <s:enumeration value="PayPalPayFlowPro" />
          <s:enumeration value="PaymentTech" />
          <s:enumeration value="PlugNPay" />
          <s:enumeration value="QBMS" />
          <s:enumeration value="USAePay" />
          <s:enumeration value="PayGov" />
          <s:enumeration value="eCheckIt" />
          <s:enumeration value="Custom" />
        </s:restriction>
      </s:simpleType>
      <s:simpleType name="PaymentStatusCodeType">
        <s:restriction base="s:string">
          <s:enumeration value="Authorized" />
          <s:enumeration value="Chargeable" />
          <s:enumeration value="Chargeback" />
          <s:enumeration value="Charged" />
          <s:enumeration value="Failure" />
          <s:enumeration value="Processing" />
          <s:enumeration value="Purged" />
          <s:enumeration value="Refunded" />
          <s:enumeration value="Unavailable" />
          <s:enumeration value="Voided" />
          <s:enumeration value="Custom" />
        </s:restriction>
      </s:simpleType>
      <s:complexType name="ShippingType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Carrier" type="tns:CarrierCodeType" />
          <s:element minOccurs="0" maxOccurs="unbounded" name="PackageList" type="tns:PackageType" />
        </s:sequence>
      </s:complexType>
      <s:simpleType name="CarrierCodeType">
        <s:restriction base="s:string">
          <s:enumeration value="Customer" />
          <s:enumeration value="FedEx" />
          <s:enumeration value="NotRequired" />
          <s:enumeration value="UPS" />
          <s:enumeration value="USPS" />
          <s:enumeration value="Custom" />
        </s:restriction>
      </s:simpleType>
      <s:complexType name="PackageType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="TrackingNumber" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ProductType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="AverageRating" type="s:decimal" />
          <s:element minOccurs="0" maxOccurs="1" name="CategorySpecial" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="CreateTime" type="s:dateTime" />
          <s:element minOccurs="0" maxOccurs="1" name="Description" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Enabled" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="FreeShipping" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="FullDescription" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="HomePageSpecial" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="InStockMessage" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="MaxOrderQty" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="ManufacturerPartNumber" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="MinOrderQty" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="NonTaxable" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="AdminNotes" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="OutStockMessage" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="PageUrl" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="ParentProduct" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="PartNumber" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="PreventPurchase" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="ProductClass" type="tns:ProductCodeType" />
          <s:element minOccurs="0" maxOccurs="1" name="QtyInStock" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="SortOrder" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="UpdateTime" type="s:dateTime" />
          <s:element minOccurs="0" maxOccurs="1" name="DownloadInformation" type="tns:DownloadProductType" />
          <s:element minOccurs="0" maxOccurs="1" name="Manufacturer" type="tns:ManufacturerType" />
          <s:element minOccurs="0" maxOccurs="1" name="Parent" type="tns:ProductType" />
          <s:element minOccurs="0" maxOccurs="1" name="Price" type="tns:ProductPriceType" />
          <s:element minOccurs="0" maxOccurs="1" name="SearchInformation" type="tns:SearchInformationType" />
          <s:element minOccurs="0" maxOccurs="1" name="Warehouse" type="tns:WarehouseType" />
          <s:element minOccurs="0" maxOccurs="1" name="Weight" type="tns:WeightType" />
          <s:element minOccurs="0" maxOccurs="1" name="ShippingOption" type="tns:ShippingOptionCodeType" />
          <s:element minOccurs="0" maxOccurs="1" name="ShippingLabel" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="QtyUnlimited" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="unbounded" name="CategoryList" type="tns:CategoryType" />
          <s:element minOccurs="0" maxOccurs="unbounded" name="ImageList" type="tns:ProductImageType" />
          <s:element minOccurs="0" maxOccurs="unbounded" name="QuestionList" type="tns:QuestionType" />
          <s:element minOccurs="0" maxOccurs="unbounded" name="RelatedProductList" type="tns:ProductType" />
          <s:element minOccurs="0" maxOccurs="unbounded" name="SelectedVariationList" type="tns:SelectedVariationType" />
          <s:element minOccurs="0" maxOccurs="unbounded" name="VariationList" type="tns:VariationGroupType" />
          <s:element minOccurs="0" maxOccurs="1" name="GiftCertificateInformation" type="tns:GiftCertificateProductType" />
          <s:element minOccurs="0" maxOccurs="1" name="QtyReorder" type="s:int" />
          <s:element minOccurs="0" maxOccurs="unbounded" name="ProductAttributeList" type="tns:AttributeType" />
        </s:sequence>
        <s:attribute form="unqualified" name="ProductId" type="s:long" />
        <s:attribute form="unqualified" name="Disassociate" type="s:boolean" />
      </s:complexType>
      <s:complexType name="DownloadProductType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="File" type="s:string" />
          <s:element minOccurs="0" maxOccurs="unbounded" name="LicenseList" type="tns:ProductKeyType" />
          <s:element minOccurs="0" maxOccurs="1" name="KeyLimited" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="DownloadLimit" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Instructions" type="s:string" />
        </s:sequence>
        <s:attribute form="unqualified" name="ProductId" type="s:long" />
      </s:complexType>
      <s:complexType name="ProductKeyType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="License" type="s:string" />
        </s:sequence>
        <s:attribute form="unqualified" name="ProductKeyId" type="s:long" />
        <s:attribute form="unqualified" name="Delete" type="s:boolean" />
      </s:complexType>
      <s:complexType name="ProductPriceType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="BasePrice" type="tns:AmountType" />
          <s:element minOccurs="0" maxOccurs="1" name="Cost" type="tns:AmountType" />
          <s:element minOccurs="0" maxOccurs="1" name="UnitPrice" type="tns:AmountType" />
          <s:element minOccurs="0" maxOccurs="1" name="Handling" type="tns:AmountType" />
          <s:element minOccurs="0" maxOccurs="1" name="MaxUnitPrice" type="tns:AmountType" />
          <s:element minOccurs="0" maxOccurs="1" name="MinUnitPrice" type="tns:AmountType" />
          <s:element minOccurs="0" maxOccurs="1" name="Msrp" type="tns:AmountType" />
          <s:element minOccurs="0" maxOccurs="1" name="Message" type="s:string" />
          <s:element minOccurs="0" maxOccurs="unbounded" name="PriceLevelList" type="tns:PriceLevelType" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ProductImageType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="AlternateText" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Caption" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="ThumbnailUrl" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="DisplayUrl" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="DetailUrl" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="VariationGroupType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="SortOrder" type="s:int" />
          <s:element minOccurs="0" maxOccurs="unbounded" name="OptionList" type="tns:VariationOptionType" />
        </s:sequence>
        <s:attribute form="unqualified" name="VariationGroupId" type="s:long" />
        <s:attribute form="unqualified" name="Delete" type="s:boolean" />
      </s:complexType>
      <s:complexType name="VariationOptionType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Name" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Enabled" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="SortOrder" type="s:int" />
        </s:sequence>
        <s:attribute form="unqualified" name="VariationOptionId" type="s:long" />
        <s:attribute form="unqualified" name="Delete" type="s:boolean" />
      </s:complexType>
      <s:complexType name="GiftCertificateProductType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Expiration" type="tns:ExpirationCodeType" />
          <s:element minOccurs="0" maxOccurs="1" name="Duration" type="s:int" />
        </s:sequence>
      </s:complexType>
      <s:simpleType name="ExpirationCodeType">
        <s:restriction base="s:string">
          <s:enumeration value="Day" />
          <s:enumeration value="Month" />
          <s:enumeration value="Never" />
          <s:enumeration value="Week" />
          <s:enumeration value="Year" />
          <s:enumeration value="Custom" />
        </s:restriction>
      </s:simpleType>
      <s:complexType name="UpdateGiftCertificateRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="GiftCertificate" type="tns:GiftCertificateType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="DeleteGiftCertificateRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="GiftCertificate" type="tns:GiftCertificateType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="CreateProductRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Product" type="tns:ProductType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="UpdateProductRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Product" type="tns:ProductType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="DeleteProductRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Product" type="tns:ProductType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="CreateCustomerRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Customer" type="tns:CustomerType" />
            </s:sequence>
            <s:attribute form="unqualified" name="emailCustomer" type="s:boolean" />
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="UpdateCustomerRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Customer" type="tns:CustomerType" />
            </s:sequence>
            <s:attribute form="unqualified" name="emailCustomer" type="s:boolean" />
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="DeleteCustomerRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Customer" type="tns:CustomerType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="UpdateOrderRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Order" type="tns:OrderType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="UpdateInventoryRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Inventory" type="tns:InventoryType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="InventoryType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="QtyInStock" type="tns:ProductQuantityType" />
        </s:sequence>
        <s:attribute form="unqualified" name="ProductId" type="s:long" />
      </s:complexType>
      <s:complexType name="ProductQuantityType">
        <s:simpleContent>
          <s:extension base="s:int">
            <s:attribute form="unqualified" name="Adjustment" type="s:boolean" />
          </s:extension>
        </s:simpleContent>
      </s:complexType>
      <s:complexType name="PerformMultipleRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="unbounded" name="UpdateInventoryRequestList" type="tns:UpdateInventoryRequestType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="GetUserKeyRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="UserKey" type="tns:UserKeyType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="UserKeyType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="LoginUrl" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="UserKey" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="SuccessUrl" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="FailureUrl" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="UpdateAttributeRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Attribute" type="tns:AttributeType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ReadSiteSettingRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="DetailSize" type="tns:SizeCodeType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:simpleType name="SizeCodeType">
        <s:restriction base="s:string">
          <s:enumeration value="Small" />
          <s:enumeration value="Medium" />
          <s:enumeration value="Large" />
          <s:enumeration value="Custom" />
        </s:restriction>
      </s:simpleType>
      <s:complexType name="CreatePriceLevelRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="PriceLevel" type="tns:PriceLevelType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="UpdatePriceLevelRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="PriceLevel" type="tns:PriceLevelType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="DeletePriceLevelRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="PriceLevel" type="tns:PriceLevelType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="CreateAttributeRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Attribute" type="tns:AttributeType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ReadBaseRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseRequestType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="DetailSize" type="tns:SizeCodeType" />
              <s:element minOccurs="0" maxOccurs="1" name="PageRequest" type="tns:PaginationType" />
              <s:element minOccurs="0" maxOccurs="unbounded" name="FilterList" type="tns:FilterType" />
              <s:element minOccurs="0" maxOccurs="unbounded" name="SortList" type="tns:SortType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="PaginationType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Page" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Size" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="TotalSize" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="HasMore" type="s:boolean" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="FilterType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Field" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Operator" type="tns:OperatorCodeType" />
          <s:element minOccurs="0" maxOccurs="unbounded" name="ValueList" type="s:string" />
          <s:element minOccurs="0" maxOccurs="unbounded" name="SubFilterList" type="tns:FilterType" />
        </s:sequence>
        <s:attribute form="unqualified" name="OrClause" type="s:boolean" />
      </s:complexType>
      <s:simpleType name="OperatorCodeType">
        <s:restriction base="s:string">
          <s:enumeration value="Between" />
          <s:enumeration value="Equal" />
          <s:enumeration value="GreaterEqual" />
          <s:enumeration value="GreaterThan" />
          <s:enumeration value="In" />
          <s:enumeration value="IsNull" />
          <s:enumeration value="LessEqual" />
          <s:enumeration value="LessThan" />
          <s:enumeration value="Like" />
          <s:enumeration value="NotEqual" />
          <s:enumeration value="Custom" />
        </s:restriction>
      </s:simpleType>
      <s:complexType name="SortType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Field" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Direction" type="tns:DirectionCodeType" />
        </s:sequence>
      </s:complexType>
      <s:simpleType name="DirectionCodeType">
        <s:restriction base="s:string">
          <s:enumeration value="Ascending" />
          <s:enumeration value="Descending" />
          <s:enumeration value="Custom" />
        </s:restriction>
      </s:simpleType>
      <s:complexType name="ReadOrderStatusRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:ReadBaseRequestType" />
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ReadOrderRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:ReadBaseRequestType" />
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ReadCategoryRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:ReadBaseRequestType" />
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ReadWarehouseRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:ReadBaseRequestType" />
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ReadPaymentRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:ReadBaseRequestType" />
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ReadPriceLevelRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:ReadBaseRequestType" />
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ReadManufacturerRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:ReadBaseRequestType" />
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ReadGiftCertificateRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:ReadBaseRequestType" />
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ReadProductRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:ReadBaseRequestType" />
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ReadCustomerRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:ReadBaseRequestType" />
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ReadAttributeRequestType">
        <s:complexContent mixed="false">
          <s:extension base="tns:ReadBaseRequestType" />
        </s:complexContent>
      </s:complexType>
      <s:element name="CreateWarehouseResponse" type="tns:CreateWarehouseResponseType" />
      <s:complexType name="CreateWarehouseResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Warehouse" type="tns:WarehouseType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="BaseResponseType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="RequestId" type="s:string" />
          <s:element minOccurs="1" maxOccurs="1" name="Status" type="tns:StatusCodeType" />
          <s:element minOccurs="1" maxOccurs="1" name="TimeStamp" type="s:dateTime" />
          <s:element minOccurs="0" maxOccurs="1" name="StoreUrl" type="s:string" />
          <s:element minOccurs="0" maxOccurs="unbounded" name="ErrorList" type="tns:ErrorType" />
          <s:element minOccurs="0" maxOccurs="1" name="Version" type="s:decimal" />
        </s:sequence>
      </s:complexType>
      <s:simpleType name="StatusCodeType">
        <s:restriction base="s:string">
          <s:enumeration value="Failure" />
          <s:enumeration value="Success" />
          <s:enumeration value="PartialFailure" />
          <s:enumeration value="Custom" />
        </s:restriction>
      </s:simpleType>
      <s:complexType name="ErrorType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Message" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Number" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Severity" type="tns:SeverityCodeType" />
          <s:element minOccurs="0" maxOccurs="1" name="FieldInfo" type="tns:FieldInfoType" />
        </s:sequence>
      </s:complexType>
      <s:simpleType name="SeverityCodeType">
        <s:restriction base="s:string">
          <s:enumeration value="Error" />
          <s:enumeration value="Warning" />
          <s:enumeration value="Custom" />
        </s:restriction>
      </s:simpleType>
      <s:complexType name="FieldInfoType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Field" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Information" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="CreatePriceLevelResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="PriceLevel" type="tns:PriceLevelType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="UpdatePriceLevelResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="PriceLevel" type="tns:PriceLevelType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="DeletePriceLevelResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="PriceLevel" type="tns:PriceLevelType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="CreateAttributeResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Attribute" type="tns:AttributeType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="UpdateAttributeResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Attribute" type="tns:AttributeType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ReadBaseResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="PageResponse" type="tns:PaginationType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ReadCategoryResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:ReadBaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="unbounded" name="CategoryList" type="tns:CategoryType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ReadPriceLevelResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:ReadBaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="unbounded" name="PriceLevelList" type="tns:PriceLevelType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ReadWarehouseResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:ReadBaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="unbounded" name="WarehouseList" type="tns:WarehouseType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ReadPaymentResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:ReadBaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="unbounded" name="PaymentList" type="tns:PaymentType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ReadManufacturerResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:ReadBaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="unbounded" name="ManufacturerList" type="tns:ManufacturerType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ReadGiftCertificateResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:ReadBaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="unbounded" name="GiftCertificateList" type="tns:GiftCertificateType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ReadProductResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:ReadBaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="unbounded" name="ProductList" type="tns:ProductType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ReadCustomerResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:ReadBaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="unbounded" name="CustomerList" type="tns:CustomerType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ReadOrderResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:ReadBaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="unbounded" name="OrderList" type="tns:OrderType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ReadOrderStatusResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:ReadBaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="unbounded" name="OrderStatusList" type="tns:OrderStatusType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ReadAttributeResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:ReadBaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="unbounded" name="AttributeList" type="tns:AttributeType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="UpdateWarehouseResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Warehouse" type="tns:WarehouseType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="DeleteWarehouseResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Warehouse" type="tns:WarehouseType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="CreateOrderStatusResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="OrderStatus" type="tns:OrderStatusType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="UpdateOrderStatusResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="OrderStatus" type="tns:OrderStatusType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="DeleteOrderStatusResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="OrderStatus" type="tns:OrderStatusType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="CreateCategoryResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Category" type="tns:CategoryType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="UpdateCategoryResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Category" type="tns:CategoryType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="DeleteCategoryResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Category" type="tns:CategoryType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="CreateManufacturerResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Manufacturer" type="tns:ManufacturerType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="UpdateManufacturerResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Manufacturer" type="tns:ManufacturerType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="DeleteManufacturerResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Manufacturer" type="tns:ManufacturerType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="CreateGiftCertificateResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="GiftCertificate" type="tns:GiftCertificateType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="UpdateGiftCertificateResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="GiftCertificate" type="tns:GiftCertificateType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="DeleteGiftCertificateResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="GiftCertificate" type="tns:GiftCertificateType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="CreateProductResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Product" type="tns:ProductType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="UpdateProductResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Product" type="tns:ProductType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="DeleteProductResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Product" type="tns:ProductType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="CreateCustomerResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Customer" type="tns:CustomerType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="UpdateCustomerResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Customer" type="tns:CustomerType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="DeleteCustomerResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Customer" type="tns:CustomerType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="UpdateOrderResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Order" type="tns:OrderType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="DeleteAttributeResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Attribute" type="tns:AttributeType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="GetUserKeyResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="UserKey" type="tns:UserKeyType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="GetUserTokenResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="UserToken" type="tns:UserTokenType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="ReadSiteSettingResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="SiteSetting" type="tns:SiteSettingType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="SiteSettingType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="CompanyName" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Enabled" type="s:boolean" />
          <s:element minOccurs="0" maxOccurs="1" name="StoreUrl" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="StoreSecureUrl" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Software" type="tns:SoftwareCodeType" />
          <s:element minOccurs="0" maxOccurs="1" name="SoftwareVersion" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:simpleType name="SoftwareCodeType">
        <s:restriction base="s:string">
          <s:enumeration value="Professional" />
          <s:enumeration value="Basic" />
          <s:enumeration value="Custom" />
        </s:restriction>
      </s:simpleType>
      <s:complexType name="UpdateInventoryResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="1" name="Inventory" type="tns:InventoryType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:complexType name="PerformMultipleResponseType">
        <s:complexContent mixed="false">
          <s:extension base="tns:BaseResponseType">
            <s:sequence>
              <s:element minOccurs="0" maxOccurs="unbounded" name="UpdateInventoryResponse" type="tns:UpdateInventoryResponseType" />
            </s:sequence>
          </s:extension>
        </s:complexContent>
      </s:complexType>
      <s:element name="SecurityCredential" type="tns:SecurityCredentialType" />
      <s:complexType name="SecurityCredentialType">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="Application" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Certificate" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="UserToken" type="s:string" />
        </s:sequence>
        <s:anyAttribute />
      </s:complexType>
      <s:element name="ReadWarehouseRequest" type="tns:ReadWarehouseRequestType" />
      <s:element name="ReadWarehouseResponse" type="tns:ReadWarehouseResponseType" />
      <s:element name="UpdateWarehouseRequest" type="tns:UpdateWarehouseRequestType" />
      <s:element name="UpdateWarehouseResponse" type="tns:UpdateWarehouseResponseType" />
      <s:element name="DeleteWarehouseRequest" type="tns:DeleteWarehouseRequestType" />
      <s:element name="DeleteWarehouseResponse" type="tns:DeleteWarehouseResponseType" />
      <s:element name="CreateManufacturerRequest" type="tns:CreateManufacturerRequestType" />
      <s:element name="CreateManufacturerResponse" type="tns:CreateManufacturerResponseType" />
      <s:element name="ReadManufacturerRequest" type="tns:ReadManufacturerRequestType" />
      <s:element name="ReadManufacturerResponse" type="tns:ReadManufacturerResponseType" />
      <s:element name="UpdateManufacturerRequest" type="tns:UpdateManufacturerRequestType" />
      <s:element name="UpdateManufacturerResponse" type="tns:UpdateManufacturerResponseType" />
      <s:element name="DeleteManufacturerRequest" type="tns:DeleteManufacturerRequestType" />
      <s:element name="DeleteManufacturerResponse" type="tns:DeleteManufacturerResponseType" />
      <s:element name="CreateCategoryRequest" type="tns:CreateCategoryRequestType" />
      <s:element name="CreateCategoryResponse" type="tns:CreateCategoryResponseType" />
      <s:element name="ReadCategoryRequest" type="tns:ReadCategoryRequestType" />
      <s:element name="ReadCategoryResponse" type="tns:ReadCategoryResponseType" />
      <s:element name="UpdateCategoryRequest" type="tns:UpdateCategoryRequestType" />
      <s:element name="UpdateCategoryResponse" type="tns:UpdateCategoryResponseType" />
      <s:element name="DeleteCategoryRequest" type="tns:DeleteCategoryRequestType" />
      <s:element name="DeleteCategoryResponse" type="tns:DeleteCategoryResponseType" />
      <s:element name="CreateProductRequest" type="tns:CreateProductRequestType" />
      <s:element name="CreateProductResponse" type="tns:CreateProductResponseType" />
      <s:element name="ReadProductRequest" type="tns:ReadProductRequestType" />
      <s:element name="ReadProductResponse" type="tns:ReadProductResponseType" />
      <s:element name="UpdateProductRequest" type="tns:UpdateProductRequestType" />
      <s:element name="UpdateProductResponse" type="tns:UpdateProductResponseType" />
      <s:element name="DeleteProductRequest" type="tns:DeleteProductRequestType" />
      <s:element name="DeleteProductResponse" type="tns:DeleteProductResponseType" />
      <s:element name="CreateCustomerRequest" type="tns:CreateCustomerRequestType" />
      <s:element name="CreateCustomerResponse" type="tns:CreateCustomerResponseType" />
      <s:element name="ReadCustomerRequest" type="tns:ReadCustomerRequestType" />
      <s:element name="ReadCustomerResponse" type="tns:ReadCustomerResponseType" />
      <s:element name="UpdateCustomerRequest" type="tns:UpdateCustomerRequestType" />
      <s:element name="UpdateCustomerResponse" type="tns:UpdateCustomerResponseType" />
      <s:element name="DeleteCustomerRequest" type="tns:DeleteCustomerRequestType" />
      <s:element name="DeleteCustomerResponse" type="tns:DeleteCustomerResponseType" />
      <s:element name="ReadOrderRequest" type="tns:ReadOrderRequestType" />
      <s:element name="ReadOrderResponse" type="tns:ReadOrderResponseType" />
      <s:element name="UpdateOrderRequest" type="tns:UpdateOrderRequestType" />
      <s:element name="UpdateOrderResponse" type="tns:UpdateOrderResponseType" />
      <s:element name="CreateOrderStatusRequest" type="tns:CreateOrderStatusRequestType" />
      <s:element name="CreateOrderStatusResponse" type="tns:CreateOrderStatusResponseType" />
      <s:element name="ReadOrderStatusRequest" type="tns:ReadOrderStatusRequestType" />
      <s:element name="ReadOrderStatusResponse" type="tns:ReadOrderStatusResponseType" />
      <s:element name="UpdateOrderStatusRequest" type="tns:UpdateOrderStatusRequestType" />
      <s:element name="UpdateOrderStatusResponse" type="tns:UpdateOrderStatusResponseType" />
      <s:element name="DeleteOrderStatusRequest" type="tns:DeleteOrderStatusRequestType" />
      <s:element name="DeleteOrderStatusResponse" type="tns:DeleteOrderStatusResponseType" />
      <s:element name="ReadPaymentRequest" type="tns:ReadPaymentRequestType" />
      <s:element name="ReadPaymentResponse" type="tns:ReadPaymentResponseType" />
      <s:element name="CreatePriceLevelRequest" type="tns:CreatePriceLevelRequestType" />
      <s:element name="CreatePriceLevelResponse" type="tns:CreatePriceLevelResponseType" />
      <s:element name="ReadPriceLevelRequest" type="tns:ReadPriceLevelRequestType" />
      <s:element name="ReadPriceLevelResponse" type="tns:ReadPriceLevelResponseType" />
      <s:element name="UpdatePriceLevelRequest" type="tns:UpdatePriceLevelRequestType" />
      <s:element name="UpdatePriceLevelResponse" type="tns:UpdatePriceLevelResponseType" />
      <s:element name="DeletePriceLevelRequest" type="tns:DeletePriceLevelRequestType" />
      <s:element name="DeletePriceLevelResponse" type="tns:DeletePriceLevelResponseType" />
      <s:element name="CreateAttributeRequest" type="tns:CreateAttributeRequestType" />
      <s:element name="CreateAttributeResponse" type="tns:CreateAttributeResponseType" />
      <s:element name="ReadAttributeRequest" type="tns:ReadAttributeRequestType" />
      <s:element name="ReadAttributeResponse" type="tns:ReadAttributeResponseType" />
      <s:element name="UpdateAttributeRequest" type="tns:UpdateAttributeRequestType" />
      <s:element name="UpdateAttributeResponse" type="tns:UpdateAttributeResponseType" />
      <s:element name="DeleteAttributeRequest" type="tns:DeleteAttributeRequestType" />
      <s:element name="DeleteAttributeResponse" type="tns:DeleteAttributeResponseType" />
      <s:element name="UpdateInventoryRequest" type="tns:UpdateInventoryRequestType" />
      <s:element name="UpdateInventoryResponse" type="tns:UpdateInventoryResponseType" />
      <s:element name="PerformMultipleRequest" type="tns:PerformMultipleRequestType" />
      <s:element name="PerformMultipleResponse" type="tns:PerformMultipleResponseType" />
      <s:element name="ReadSiteSettingRequest" type="tns:ReadSiteSettingRequestType" />
      <s:element name="ReadSiteSettingResponse" type="tns:ReadSiteSettingResponseType" />
      <s:element name="GetUserTokenRequest" type="tns:GetUserTokenRequestType" />
      <s:element name="GetUserTokenResponse" type="tns:GetUserTokenResponseType" />
      <s:element name="GetUserKeyRequest" type="tns:GetUserKeyRequestType" />
      <s:element name="GetUserKeyResponse" type="tns:GetUserKeyResponseType" />
      <s:element name="CreateGiftCertificateRequest" type="tns:CreateGiftCertificateRequestType" />
      <s:element name="CreateGiftCertificateResponse" type="tns:CreateGiftCertificateResponseType" />
      <s:element name="ReadGiftCertificateRequest" type="tns:ReadGiftCertificateRequestType" />
      <s:element name="ReadGiftCertificateResponse" type="tns:ReadGiftCertificateResponseType" />
      <s:element name="UpdateGiftCertificateRequest" type="tns:UpdateGiftCertificateRequestType" />
      <s:element name="UpdateGiftCertificateResponse" type="tns:UpdateGiftCertificateResponseType" />
      <s:element name="DeleteGiftCertificateRequest" type="tns:DeleteGiftCertificateRequestType" />
      <s:element name="DeleteGiftCertificateResponse" type="tns:DeleteGiftCertificateResponseType" />
    </s:schema>
  </wsdl:types>
  <wsdl:message name="CreateWarehouseSoapIn">
    <wsdl:part name="CreateWarehouseRequest" element="tns:CreateWarehouseRequest" />
  </wsdl:message>
  <wsdl:message name="CreateWarehouseSoapOut">
    <wsdl:part name="CreateWarehouseResult" element="tns:CreateWarehouseResponse" />
  </wsdl:message>
  <wsdl:message name="CreateWarehouseSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="ReadWarehouseSoapIn">
    <wsdl:part name="ReadWarehouseRequest" element="tns:ReadWarehouseRequest" />
  </wsdl:message>
  <wsdl:message name="ReadWarehouseSoapOut">
    <wsdl:part name="ReadWarehouseResult" element="tns:ReadWarehouseResponse" />
  </wsdl:message>
  <wsdl:message name="ReadWarehouseSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="UpdateWarehouseSoapIn">
    <wsdl:part name="UpdateWarehouseRequest" element="tns:UpdateWarehouseRequest" />
  </wsdl:message>
  <wsdl:message name="UpdateWarehouseSoapOut">
    <wsdl:part name="UpdateWarehouseResult" element="tns:UpdateWarehouseResponse" />
  </wsdl:message>
  <wsdl:message name="UpdateWarehouseSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="DeleteWarehouseSoapIn">
    <wsdl:part name="CreateWarehouseManager" element="tns:DeleteWarehouseRequest" />
  </wsdl:message>
  <wsdl:message name="DeleteWarehouseSoapOut">
    <wsdl:part name="DeleteWarehouseResult" element="tns:DeleteWarehouseResponse" />
  </wsdl:message>
  <wsdl:message name="DeleteWarehouseSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="CreateManufacturerSoapIn">
    <wsdl:part name="CreateManufacturerRequest" element="tns:CreateManufacturerRequest" />
  </wsdl:message>
  <wsdl:message name="CreateManufacturerSoapOut">
    <wsdl:part name="CreateManufacturerResult" element="tns:CreateManufacturerResponse" />
  </wsdl:message>
  <wsdl:message name="CreateManufacturerSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="ReadManufacturerSoapIn">
    <wsdl:part name="ReadManufacturerRequest" element="tns:ReadManufacturerRequest" />
  </wsdl:message>
  <wsdl:message name="ReadManufacturerSoapOut">
    <wsdl:part name="ReadManufacturerResult" element="tns:ReadManufacturerResponse" />
  </wsdl:message>
  <wsdl:message name="ReadManufacturerSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="UpdateManufacturerSoapIn">
    <wsdl:part name="UpdateManufacturerRequest" element="tns:UpdateManufacturerRequest" />
  </wsdl:message>
  <wsdl:message name="UpdateManufacturerSoapOut">
    <wsdl:part name="UpdateManufacturerResult" element="tns:UpdateManufacturerResponse" />
  </wsdl:message>
  <wsdl:message name="UpdateManufacturerSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="DeleteManufacturerSoapIn">
    <wsdl:part name="CreateManufacturerManager" element="tns:DeleteManufacturerRequest" />
  </wsdl:message>
  <wsdl:message name="DeleteManufacturerSoapOut">
    <wsdl:part name="DeleteManufacturerResult" element="tns:DeleteManufacturerResponse" />
  </wsdl:message>
  <wsdl:message name="DeleteManufacturerSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="CreateCategorySoapIn">
    <wsdl:part name="CreateCategoryRequest" element="tns:CreateCategoryRequest" />
  </wsdl:message>
  <wsdl:message name="CreateCategorySoapOut">
    <wsdl:part name="CreateCategoryResult" element="tns:CreateCategoryResponse" />
  </wsdl:message>
  <wsdl:message name="CreateCategorySecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="ReadCategorySoapIn">
    <wsdl:part name="ReadCategoryRequest" element="tns:ReadCategoryRequest" />
  </wsdl:message>
  <wsdl:message name="ReadCategorySoapOut">
    <wsdl:part name="ReadCategoryResult" element="tns:ReadCategoryResponse" />
  </wsdl:message>
  <wsdl:message name="ReadCategorySecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="UpdateCategorySoapIn">
    <wsdl:part name="UpdateCategoryRequest" element="tns:UpdateCategoryRequest" />
  </wsdl:message>
  <wsdl:message name="UpdateCategorySoapOut">
    <wsdl:part name="UpdateCategoryResult" element="tns:UpdateCategoryResponse" />
  </wsdl:message>
  <wsdl:message name="UpdateCategorySecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="DeleteCategorySoapIn">
    <wsdl:part name="CreateCategoryManager" element="tns:DeleteCategoryRequest" />
  </wsdl:message>
  <wsdl:message name="DeleteCategorySoapOut">
    <wsdl:part name="DeleteCategoryResult" element="tns:DeleteCategoryResponse" />
  </wsdl:message>
  <wsdl:message name="DeleteCategorySecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="CreateProductSoapIn">
    <wsdl:part name="CreateProductRequest" element="tns:CreateProductRequest" />
  </wsdl:message>
  <wsdl:message name="CreateProductSoapOut">
    <wsdl:part name="CreateProductResult" element="tns:CreateProductResponse" />
  </wsdl:message>
  <wsdl:message name="CreateProductSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="ReadProductSoapIn">
    <wsdl:part name="ReadProductRequest" element="tns:ReadProductRequest" />
  </wsdl:message>
  <wsdl:message name="ReadProductSoapOut">
    <wsdl:part name="ReadProductResult" element="tns:ReadProductResponse" />
  </wsdl:message>
  <wsdl:message name="ReadProductSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="UpdateProductSoapIn">
    <wsdl:part name="UpdateProductRequest" element="tns:UpdateProductRequest" />
  </wsdl:message>
  <wsdl:message name="UpdateProductSoapOut">
    <wsdl:part name="UpdateProductResult" element="tns:UpdateProductResponse" />
  </wsdl:message>
  <wsdl:message name="UpdateProductSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="DeleteProductSoapIn">
    <wsdl:part name="CreateProductManager" element="tns:DeleteProductRequest" />
  </wsdl:message>
  <wsdl:message name="DeleteProductSoapOut">
    <wsdl:part name="DeleteProductResult" element="tns:DeleteProductResponse" />
  </wsdl:message>
  <wsdl:message name="DeleteProductSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="CreateCustomerSoapIn">
    <wsdl:part name="CreateCustomerRequest" element="tns:CreateCustomerRequest" />
  </wsdl:message>
  <wsdl:message name="CreateCustomerSoapOut">
    <wsdl:part name="CreateCustomerResult" element="tns:CreateCustomerResponse" />
  </wsdl:message>
  <wsdl:message name="CreateCustomerSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="ReadCustomerSoapIn">
    <wsdl:part name="ReadCustomerRequest" element="tns:ReadCustomerRequest" />
  </wsdl:message>
  <wsdl:message name="ReadCustomerSoapOut">
    <wsdl:part name="ReadCustomerResult" element="tns:ReadCustomerResponse" />
  </wsdl:message>
  <wsdl:message name="ReadCustomerSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="UpdateCustomerSoapIn">
    <wsdl:part name="UpdateCustomerRequest" element="tns:UpdateCustomerRequest" />
  </wsdl:message>
  <wsdl:message name="UpdateCustomerSoapOut">
    <wsdl:part name="UpdateCustomerResult" element="tns:UpdateCustomerResponse" />
  </wsdl:message>
  <wsdl:message name="UpdateCustomerSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="DeleteCustomerSoapIn">
    <wsdl:part name="CreateCustomerManager" element="tns:DeleteCustomerRequest" />
  </wsdl:message>
  <wsdl:message name="DeleteCustomerSoapOut">
    <wsdl:part name="DeleteCustomerResult" element="tns:DeleteCustomerResponse" />
  </wsdl:message>
  <wsdl:message name="DeleteCustomerSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="ReadOrderSoapIn">
    <wsdl:part name="ReadOrderRequest" element="tns:ReadOrderRequest" />
  </wsdl:message>
  <wsdl:message name="ReadOrderSoapOut">
    <wsdl:part name="ReadOrderResult" element="tns:ReadOrderResponse" />
  </wsdl:message>
  <wsdl:message name="ReadOrderSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="UpdateOrderSoapIn">
    <wsdl:part name="UpdateOrderRequest" element="tns:UpdateOrderRequest" />
  </wsdl:message>
  <wsdl:message name="UpdateOrderSoapOut">
    <wsdl:part name="UpdateOrderResult" element="tns:UpdateOrderResponse" />
  </wsdl:message>
  <wsdl:message name="UpdateOrderSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="CreateOrderStatusSoapIn">
    <wsdl:part name="CreateOrderStatusRequest" element="tns:CreateOrderStatusRequest" />
  </wsdl:message>
  <wsdl:message name="CreateOrderStatusSoapOut">
    <wsdl:part name="CreateOrderStatusResult" element="tns:CreateOrderStatusResponse" />
  </wsdl:message>
  <wsdl:message name="CreateOrderStatusSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="ReadOrderStatusSoapIn">
    <wsdl:part name="ReadOrderStatusRequest" element="tns:ReadOrderStatusRequest" />
  </wsdl:message>
  <wsdl:message name="ReadOrderStatusSoapOut">
    <wsdl:part name="ReadOrderStatusResult" element="tns:ReadOrderStatusResponse" />
  </wsdl:message>
  <wsdl:message name="ReadOrderStatusSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="UpdateOrderStatusSoapIn">
    <wsdl:part name="UpdateOrderStatusRequest" element="tns:UpdateOrderStatusRequest" />
  </wsdl:message>
  <wsdl:message name="UpdateOrderStatusSoapOut">
    <wsdl:part name="UpdateOrderStatusResult" element="tns:UpdateOrderStatusResponse" />
  </wsdl:message>
  <wsdl:message name="UpdateOrderStatusSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="DeleteOrderStatusSoapIn">
    <wsdl:part name="CreateOrderStatusManager" element="tns:DeleteOrderStatusRequest" />
  </wsdl:message>
  <wsdl:message name="DeleteOrderStatusSoapOut">
    <wsdl:part name="DeleteOrderStatusResult" element="tns:DeleteOrderStatusResponse" />
  </wsdl:message>
  <wsdl:message name="DeleteOrderStatusSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="ReadPaymentSoapIn">
    <wsdl:part name="ReadPaymentRequest" element="tns:ReadPaymentRequest" />
  </wsdl:message>
  <wsdl:message name="ReadPaymentSoapOut">
    <wsdl:part name="ReadPaymentResult" element="tns:ReadPaymentResponse" />
  </wsdl:message>
  <wsdl:message name="ReadPaymentSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="CreatePriceLevelSoapIn">
    <wsdl:part name="CreatePriceLevelRequest" element="tns:CreatePriceLevelRequest" />
  </wsdl:message>
  <wsdl:message name="CreatePriceLevelSoapOut">
    <wsdl:part name="CreatePriceLevelResult" element="tns:CreatePriceLevelResponse" />
  </wsdl:message>
  <wsdl:message name="CreatePriceLevelSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="ReadPriceLevelSoapIn">
    <wsdl:part name="ReadPriceLevelRequest" element="tns:ReadPriceLevelRequest" />
  </wsdl:message>
  <wsdl:message name="ReadPriceLevelSoapOut">
    <wsdl:part name="ReadPriceLevelResult" element="tns:ReadPriceLevelResponse" />
  </wsdl:message>
  <wsdl:message name="ReadPriceLevelSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="UpdatePriceLevelSoapIn">
    <wsdl:part name="UpdatePriceLevelRequest" element="tns:UpdatePriceLevelRequest" />
  </wsdl:message>
  <wsdl:message name="UpdatePriceLevelSoapOut">
    <wsdl:part name="UpdatePriceLevelResult" element="tns:UpdatePriceLevelResponse" />
  </wsdl:message>
  <wsdl:message name="UpdatePriceLevelSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="DeletePriceLevelSoapIn">
    <wsdl:part name="CreatePriceLevelManager" element="tns:DeletePriceLevelRequest" />
  </wsdl:message>
  <wsdl:message name="DeletePriceLevelSoapOut">
    <wsdl:part name="DeletePriceLevelResult" element="tns:DeletePriceLevelResponse" />
  </wsdl:message>
  <wsdl:message name="DeletePriceLevelSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="CreateAttributeSoapIn">
    <wsdl:part name="CreateAttributeRequest" element="tns:CreateAttributeRequest" />
  </wsdl:message>
  <wsdl:message name="CreateAttributeSoapOut">
    <wsdl:part name="CreateAttributeResult" element="tns:CreateAttributeResponse" />
  </wsdl:message>
  <wsdl:message name="CreateAttributeSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="ReadAttributeSoapIn">
    <wsdl:part name="ReadAttributeRequest" element="tns:ReadAttributeRequest" />
  </wsdl:message>
  <wsdl:message name="ReadAttributeSoapOut">
    <wsdl:part name="ReadAttributeResult" element="tns:ReadAttributeResponse" />
  </wsdl:message>
  <wsdl:message name="ReadAttributeSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="UpdateAttributeSoapIn">
    <wsdl:part name="UpdateAttributeRequest" element="tns:UpdateAttributeRequest" />
  </wsdl:message>
  <wsdl:message name="UpdateAttributeSoapOut">
    <wsdl:part name="UpdateAttributeResult" element="tns:UpdateAttributeResponse" />
  </wsdl:message>
  <wsdl:message name="UpdateAttributeSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="DeleteAttributeSoapIn">
    <wsdl:part name="CreateAttributeManager" element="tns:DeleteAttributeRequest" />
  </wsdl:message>
  <wsdl:message name="DeleteAttributeSoapOut">
    <wsdl:part name="DeleteAttributeResult" element="tns:DeleteAttributeResponse" />
  </wsdl:message>
  <wsdl:message name="DeleteAttributeSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="UpdateInventorySoapIn">
    <wsdl:part name="UpdateInventoryRequest" element="tns:UpdateInventoryRequest" />
  </wsdl:message>
  <wsdl:message name="UpdateInventorySoapOut">
    <wsdl:part name="UpdateInventoryResult" element="tns:UpdateInventoryResponse" />
  </wsdl:message>
  <wsdl:message name="UpdateInventorySecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="PerformMultipleSoapIn">
    <wsdl:part name="PerformMultipleRequest" element="tns:PerformMultipleRequest" />
  </wsdl:message>
  <wsdl:message name="PerformMultipleSoapOut">
    <wsdl:part name="PerformMultipleResult" element="tns:PerformMultipleResponse" />
  </wsdl:message>
  <wsdl:message name="PerformMultipleSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="ReadSiteSettingSoapIn">
    <wsdl:part name="ReadSiteSettingRequest" element="tns:ReadSiteSettingRequest" />
  </wsdl:message>
  <wsdl:message name="ReadSiteSettingSoapOut">
    <wsdl:part name="ReadSiteSettingResult" element="tns:ReadSiteSettingResponse" />
  </wsdl:message>
  <wsdl:message name="ReadSiteSettingSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="GetUserTokenSoapIn">
    <wsdl:part name="GetUserTokenRequest" element="tns:GetUserTokenRequest" />
  </wsdl:message>
  <wsdl:message name="GetUserTokenSoapOut">
    <wsdl:part name="GetUserTokenResult" element="tns:GetUserTokenResponse" />
  </wsdl:message>
  <wsdl:message name="GetUserTokenSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="GetUserKeySoapIn">
    <wsdl:part name="GetUserKeyRequest" element="tns:GetUserKeyRequest" />
  </wsdl:message>
  <wsdl:message name="GetUserKeySoapOut">
    <wsdl:part name="GetUserKeyResult" element="tns:GetUserKeyResponse" />
  </wsdl:message>
  <wsdl:message name="GetUserKeySecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="CreateGiftCertificateSoapIn">
    <wsdl:part name="CreateGiftCertificateRequest" element="tns:CreateGiftCertificateRequest" />
  </wsdl:message>
  <wsdl:message name="CreateGiftCertificateSoapOut">
    <wsdl:part name="CreateGiftCertificateResult" element="tns:CreateGiftCertificateResponse" />
  </wsdl:message>
  <wsdl:message name="CreateGiftCertificateSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="ReadGiftCertificateSoapIn">
    <wsdl:part name="ReadGiftCertificateRequest" element="tns:ReadGiftCertificateRequest" />
  </wsdl:message>
  <wsdl:message name="ReadGiftCertificateSoapOut">
    <wsdl:part name="ReadGiftCertificateResult" element="tns:ReadGiftCertificateResponse" />
  </wsdl:message>
  <wsdl:message name="ReadGiftCertificateSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="UpdateGiftCertificateSoapIn">
    <wsdl:part name="UpdateGiftCertificateRequest" element="tns:UpdateGiftCertificateRequest" />
  </wsdl:message>
  <wsdl:message name="UpdateGiftCertificateSoapOut">
    <wsdl:part name="UpdateGiftCertificateResult" element="tns:UpdateGiftCertificateResponse" />
  </wsdl:message>
  <wsdl:message name="UpdateGiftCertificateSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:message name="DeleteGiftCertificateSoapIn">
    <wsdl:part name="DeleteGiftCertificateRequest" element="tns:DeleteGiftCertificateRequest" />
  </wsdl:message>
  <wsdl:message name="DeleteGiftCertificateSoapOut">
    <wsdl:part name="DeleteGiftCertificateResult" element="tns:DeleteGiftCertificateResponse" />
  </wsdl:message>
  <wsdl:message name="DeleteGiftCertificateSecurityCredential">
    <wsdl:part name="SecurityCredential" element="tns:SecurityCredential" />
  </wsdl:message>
  <wsdl:portType name="NetSolEcomServiceSoap">
    <wsdl:operation name="CreateWarehouse">
      <wsdl:input message="tns:CreateWarehouseSoapIn" />
      <wsdl:output message="tns:CreateWarehouseSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="ReadWarehouse">
      <wsdl:input message="tns:ReadWarehouseSoapIn" />
      <wsdl:output message="tns:ReadWarehouseSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="UpdateWarehouse">
      <wsdl:input message="tns:UpdateWarehouseSoapIn" />
      <wsdl:output message="tns:UpdateWarehouseSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="DeleteWarehouse">
      <wsdl:input message="tns:DeleteWarehouseSoapIn" />
      <wsdl:output message="tns:DeleteWarehouseSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="CreateManufacturer">
      <wsdl:input message="tns:CreateManufacturerSoapIn" />
      <wsdl:output message="tns:CreateManufacturerSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="ReadManufacturer">
      <wsdl:input message="tns:ReadManufacturerSoapIn" />
      <wsdl:output message="tns:ReadManufacturerSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="UpdateManufacturer">
      <wsdl:input message="tns:UpdateManufacturerSoapIn" />
      <wsdl:output message="tns:UpdateManufacturerSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="DeleteManufacturer">
      <wsdl:input message="tns:DeleteManufacturerSoapIn" />
      <wsdl:output message="tns:DeleteManufacturerSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="CreateCategory">
      <wsdl:input message="tns:CreateCategorySoapIn" />
      <wsdl:output message="tns:CreateCategorySoapOut" />
    </wsdl:operation>
    <wsdl:operation name="ReadCategory">
      <wsdl:input message="tns:ReadCategorySoapIn" />
      <wsdl:output message="tns:ReadCategorySoapOut" />
    </wsdl:operation>
    <wsdl:operation name="UpdateCategory">
      <wsdl:input message="tns:UpdateCategorySoapIn" />
      <wsdl:output message="tns:UpdateCategorySoapOut" />
    </wsdl:operation>
    <wsdl:operation name="DeleteCategory">
      <wsdl:input message="tns:DeleteCategorySoapIn" />
      <wsdl:output message="tns:DeleteCategorySoapOut" />
    </wsdl:operation>
    <wsdl:operation name="CreateProduct">
      <wsdl:input message="tns:CreateProductSoapIn" />
      <wsdl:output message="tns:CreateProductSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="ReadProduct">
      <wsdl:input message="tns:ReadProductSoapIn" />
      <wsdl:output message="tns:ReadProductSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="UpdateProduct">
      <wsdl:input message="tns:UpdateProductSoapIn" />
      <wsdl:output message="tns:UpdateProductSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="DeleteProduct">
      <wsdl:input message="tns:DeleteProductSoapIn" />
      <wsdl:output message="tns:DeleteProductSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="CreateCustomer">
      <wsdl:input message="tns:CreateCustomerSoapIn" />
      <wsdl:output message="tns:CreateCustomerSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="ReadCustomer">
      <wsdl:input message="tns:ReadCustomerSoapIn" />
      <wsdl:output message="tns:ReadCustomerSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="UpdateCustomer">
      <wsdl:input message="tns:UpdateCustomerSoapIn" />
      <wsdl:output message="tns:UpdateCustomerSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="DeleteCustomer">
      <wsdl:input message="tns:DeleteCustomerSoapIn" />
      <wsdl:output message="tns:DeleteCustomerSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="ReadOrder">
      <wsdl:input message="tns:ReadOrderSoapIn" />
      <wsdl:output message="tns:ReadOrderSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="UpdateOrder">
      <wsdl:input message="tns:UpdateOrderSoapIn" />
      <wsdl:output message="tns:UpdateOrderSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="CreateOrderStatus">
      <wsdl:input message="tns:CreateOrderStatusSoapIn" />
      <wsdl:output message="tns:CreateOrderStatusSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="ReadOrderStatus">
      <wsdl:input message="tns:ReadOrderStatusSoapIn" />
      <wsdl:output message="tns:ReadOrderStatusSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="UpdateOrderStatus">
      <wsdl:input message="tns:UpdateOrderStatusSoapIn" />
      <wsdl:output message="tns:UpdateOrderStatusSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="DeleteOrderStatus">
      <wsdl:input message="tns:DeleteOrderStatusSoapIn" />
      <wsdl:output message="tns:DeleteOrderStatusSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="ReadPayment">
      <wsdl:input message="tns:ReadPaymentSoapIn" />
      <wsdl:output message="tns:ReadPaymentSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="CreatePriceLevel">
      <wsdl:input message="tns:CreatePriceLevelSoapIn" />
      <wsdl:output message="tns:CreatePriceLevelSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="ReadPriceLevel">
      <wsdl:input message="tns:ReadPriceLevelSoapIn" />
      <wsdl:output message="tns:ReadPriceLevelSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="UpdatePriceLevel">
      <wsdl:input message="tns:UpdatePriceLevelSoapIn" />
      <wsdl:output message="tns:UpdatePriceLevelSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="DeletePriceLevel">
      <wsdl:input message="tns:DeletePriceLevelSoapIn" />
      <wsdl:output message="tns:DeletePriceLevelSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="CreateAttribute">
      <wsdl:input message="tns:CreateAttributeSoapIn" />
      <wsdl:output message="tns:CreateAttributeSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="ReadAttribute">
      <wsdl:input message="tns:ReadAttributeSoapIn" />
      <wsdl:output message="tns:ReadAttributeSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="UpdateAttribute">
      <wsdl:input message="tns:UpdateAttributeSoapIn" />
      <wsdl:output message="tns:UpdateAttributeSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="DeleteAttribute">
      <wsdl:input message="tns:DeleteAttributeSoapIn" />
      <wsdl:output message="tns:DeleteAttributeSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="UpdateInventory">
      <wsdl:input message="tns:UpdateInventorySoapIn" />
      <wsdl:output message="tns:UpdateInventorySoapOut" />
    </wsdl:operation>
    <wsdl:operation name="PerformMultiple">
      <wsdl:input message="tns:PerformMultipleSoapIn" />
      <wsdl:output message="tns:PerformMultipleSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="ReadSiteSetting">
      <wsdl:input message="tns:ReadSiteSettingSoapIn" />
      <wsdl:output message="tns:ReadSiteSettingSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetUserToken">
      <wsdl:input message="tns:GetUserTokenSoapIn" />
      <wsdl:output message="tns:GetUserTokenSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetUserKey">
      <wsdl:input message="tns:GetUserKeySoapIn" />
      <wsdl:output message="tns:GetUserKeySoapOut" />
    </wsdl:operation>
    <wsdl:operation name="CreateGiftCertificate">
      <wsdl:input message="tns:CreateGiftCertificateSoapIn" />
      <wsdl:output message="tns:CreateGiftCertificateSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="ReadGiftCertificate">
      <wsdl:input message="tns:ReadGiftCertificateSoapIn" />
      <wsdl:output message="tns:ReadGiftCertificateSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="UpdateGiftCertificate">
      <wsdl:input message="tns:UpdateGiftCertificateSoapIn" />
      <wsdl:output message="tns:UpdateGiftCertificateSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="DeleteGiftCertificate">
      <wsdl:input message="tns:DeleteGiftCertificateSoapIn" />
      <wsdl:output message="tns:DeleteGiftCertificateSoapOut" />
    </wsdl:operation>
  </wsdl:portType>
  <wsdl:binding name="NetSolEcomServiceSoap" type="tns:NetSolEcomServiceSoap">
    <soap:binding transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="CreateWarehouse">
      <soap:operation soapAction="http://networksolutions.com/CreateWarehouse" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:CreateWarehouseSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ReadWarehouse">
      <soap:operation soapAction="http://networksolutions.com/ReadWarehouse" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:ReadWarehouseSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateWarehouse">
      <soap:operation soapAction="http://networksolutions.com/UpdateWarehouse" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:UpdateWarehouseSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="DeleteWarehouse">
      <soap:operation soapAction="http://networksolutions.com/DeleteWarehouse" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:DeleteWarehouseSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="CreateManufacturer">
      <soap:operation soapAction="http://networksolutions.com/CreateManufacturer" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:CreateManufacturerSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ReadManufacturer">
      <soap:operation soapAction="http://networksolutions.com/ReadManufacturer" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:ReadManufacturerSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateManufacturer">
      <soap:operation soapAction="http://networksolutions.com/UpdateManufacturer" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:UpdateManufacturerSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="DeleteManufacturer">
      <soap:operation soapAction="http://networksolutions.com/DeleteManufacturer" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:DeleteManufacturerSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="CreateCategory">
      <soap:operation soapAction="http://networksolutions.com/CreateCategory" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:CreateCategorySecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ReadCategory">
      <soap:operation soapAction="http://networksolutions.com/ReadCategory" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:ReadCategorySecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateCategory">
      <soap:operation soapAction="http://networksolutions.com/UpdateCategory" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:UpdateCategorySecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="DeleteCategory">
      <soap:operation soapAction="http://networksolutions.com/DeleteCategory" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:DeleteCategorySecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="CreateProduct">
      <soap:operation soapAction="http://networksolutions.com/CreateProduct" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:CreateProductSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ReadProduct">
      <soap:operation soapAction="http://networksolutions.com/ReadProduct" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:ReadProductSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateProduct">
      <soap:operation soapAction="http://networksolutions.com/UpdateProduct" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:UpdateProductSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="DeleteProduct">
      <soap:operation soapAction="http://networksolutions.com/DeleteProduct" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:DeleteProductSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="CreateCustomer">
      <soap:operation soapAction="http://networksolutions.com/CreateCustomer" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:CreateCustomerSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ReadCustomer">
      <soap:operation soapAction="http://networksolutions.com/ReadCustomer" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:ReadCustomerSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateCustomer">
      <soap:operation soapAction="http://networksolutions.com/UpdateCustomer" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:UpdateCustomerSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="DeleteCustomer">
      <soap:operation soapAction="http://networksolutions.com/DeleteCustomer" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:DeleteCustomerSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ReadOrder">
      <soap:operation soapAction="http://networksolutions.com/ReadOrder" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:ReadOrderSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateOrder">
      <soap:operation soapAction="http://networksolutions.com/UpdateOrder" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:UpdateOrderSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="CreateOrderStatus">
      <soap:operation soapAction="http://networksolutions.com/CreateOrderStatus" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:CreateOrderStatusSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ReadOrderStatus">
      <soap:operation soapAction="http://networksolutions.com/ReadOrderStatus" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:ReadOrderStatusSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateOrderStatus">
      <soap:operation soapAction="http://networksolutions.com/UpdateOrderStatus" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:UpdateOrderStatusSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="DeleteOrderStatus">
      <soap:operation soapAction="http://networksolutions.com/DeleteOrderStatus" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:DeleteOrderStatusSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ReadPayment">
      <soap:operation soapAction="http://networksolutions.com/ReadPayment" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:ReadPaymentSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="CreatePriceLevel">
      <soap:operation soapAction="http://networksolutions.com/CreatePriceLevel" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:CreatePriceLevelSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ReadPriceLevel">
      <soap:operation soapAction="http://networksolutions.com/ReadPriceLevel" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:ReadPriceLevelSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdatePriceLevel">
      <soap:operation soapAction="http://networksolutions.com/UpdatePriceLevel" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:UpdatePriceLevelSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="DeletePriceLevel">
      <soap:operation soapAction="http://networksolutions.com/DeletePriceLevel" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:DeletePriceLevelSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="CreateAttribute">
      <soap:operation soapAction="http://networksolutions.com/CreateAttribute" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:CreateAttributeSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ReadAttribute">
      <soap:operation soapAction="http://networksolutions.com/ReadAttribute" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:ReadAttributeSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateAttribute">
      <soap:operation soapAction="http://networksolutions.com/UpdateAttribute" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:UpdateAttributeSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="DeleteAttribute">
      <soap:operation soapAction="http://networksolutions.com/DeleteAttribute" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:DeleteAttributeSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateInventory">
      <soap:operation soapAction="http://networksolutions.com/UpdateInventory" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:UpdateInventorySecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="PerformMultiple">
      <soap:operation soapAction="http://networksolutions.com/PerformMultiple" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:PerformMultipleSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ReadSiteSetting">
      <soap:operation soapAction="http://networksolutions.com/ReadSiteSetting" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:ReadSiteSettingSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetUserToken">
      <soap:operation soapAction="http://networksolutions.com/GetUserToken" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:GetUserTokenSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetUserKey">
      <soap:operation soapAction="http://networksolutions.com/GetUserKey" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:GetUserKeySecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="CreateGiftCertificate">
      <soap:operation soapAction="http://networksolutions.com/CreateGiftCertificate" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:CreateGiftCertificateSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ReadGiftCertificate">
      <soap:operation soapAction="http://networksolutions.com/ReadGiftCertificate" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:ReadGiftCertificateSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateGiftCertificate">
      <soap:operation soapAction="http://networksolutions.com/UpdateGiftCertificate" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:UpdateGiftCertificateSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="DeleteGiftCertificate">
      <soap:operation soapAction="http://networksolutions.com/DeleteGiftCertificate" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
        <soap:header message="tns:DeleteGiftCertificateSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:binding name="NetSolEcomServiceSoap12" type="tns:NetSolEcomServiceSoap">
    <soap12:binding transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="CreateWarehouse">
      <soap12:operation soapAction="http://networksolutions.com/CreateWarehouse" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:CreateWarehouseSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ReadWarehouse">
      <soap12:operation soapAction="http://networksolutions.com/ReadWarehouse" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:ReadWarehouseSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateWarehouse">
      <soap12:operation soapAction="http://networksolutions.com/UpdateWarehouse" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:UpdateWarehouseSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="DeleteWarehouse">
      <soap12:operation soapAction="http://networksolutions.com/DeleteWarehouse" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:DeleteWarehouseSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="CreateManufacturer">
      <soap12:operation soapAction="http://networksolutions.com/CreateManufacturer" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:CreateManufacturerSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ReadManufacturer">
      <soap12:operation soapAction="http://networksolutions.com/ReadManufacturer" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:ReadManufacturerSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateManufacturer">
      <soap12:operation soapAction="http://networksolutions.com/UpdateManufacturer" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:UpdateManufacturerSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="DeleteManufacturer">
      <soap12:operation soapAction="http://networksolutions.com/DeleteManufacturer" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:DeleteManufacturerSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="CreateCategory">
      <soap12:operation soapAction="http://networksolutions.com/CreateCategory" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:CreateCategorySecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ReadCategory">
      <soap12:operation soapAction="http://networksolutions.com/ReadCategory" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:ReadCategorySecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateCategory">
      <soap12:operation soapAction="http://networksolutions.com/UpdateCategory" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:UpdateCategorySecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="DeleteCategory">
      <soap12:operation soapAction="http://networksolutions.com/DeleteCategory" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:DeleteCategorySecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="CreateProduct">
      <soap12:operation soapAction="http://networksolutions.com/CreateProduct" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:CreateProductSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ReadProduct">
      <soap12:operation soapAction="http://networksolutions.com/ReadProduct" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:ReadProductSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateProduct">
      <soap12:operation soapAction="http://networksolutions.com/UpdateProduct" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:UpdateProductSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="DeleteProduct">
      <soap12:operation soapAction="http://networksolutions.com/DeleteProduct" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:DeleteProductSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="CreateCustomer">
      <soap12:operation soapAction="http://networksolutions.com/CreateCustomer" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:CreateCustomerSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ReadCustomer">
      <soap12:operation soapAction="http://networksolutions.com/ReadCustomer" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:ReadCustomerSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateCustomer">
      <soap12:operation soapAction="http://networksolutions.com/UpdateCustomer" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:UpdateCustomerSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="DeleteCustomer">
      <soap12:operation soapAction="http://networksolutions.com/DeleteCustomer" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:DeleteCustomerSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ReadOrder">
      <soap12:operation soapAction="http://networksolutions.com/ReadOrder" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:ReadOrderSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateOrder">
      <soap12:operation soapAction="http://networksolutions.com/UpdateOrder" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:UpdateOrderSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="CreateOrderStatus">
      <soap12:operation soapAction="http://networksolutions.com/CreateOrderStatus" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:CreateOrderStatusSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ReadOrderStatus">
      <soap12:operation soapAction="http://networksolutions.com/ReadOrderStatus" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:ReadOrderStatusSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateOrderStatus">
      <soap12:operation soapAction="http://networksolutions.com/UpdateOrderStatus" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:UpdateOrderStatusSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="DeleteOrderStatus">
      <soap12:operation soapAction="http://networksolutions.com/DeleteOrderStatus" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:DeleteOrderStatusSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ReadPayment">
      <soap12:operation soapAction="http://networksolutions.com/ReadPayment" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:ReadPaymentSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="CreatePriceLevel">
      <soap12:operation soapAction="http://networksolutions.com/CreatePriceLevel" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:CreatePriceLevelSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ReadPriceLevel">
      <soap12:operation soapAction="http://networksolutions.com/ReadPriceLevel" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:ReadPriceLevelSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdatePriceLevel">
      <soap12:operation soapAction="http://networksolutions.com/UpdatePriceLevel" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:UpdatePriceLevelSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="DeletePriceLevel">
      <soap12:operation soapAction="http://networksolutions.com/DeletePriceLevel" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:DeletePriceLevelSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="CreateAttribute">
      <soap12:operation soapAction="http://networksolutions.com/CreateAttribute" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:CreateAttributeSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ReadAttribute">
      <soap12:operation soapAction="http://networksolutions.com/ReadAttribute" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:ReadAttributeSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateAttribute">
      <soap12:operation soapAction="http://networksolutions.com/UpdateAttribute" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:UpdateAttributeSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="DeleteAttribute">
      <soap12:operation soapAction="http://networksolutions.com/DeleteAttribute" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:DeleteAttributeSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateInventory">
      <soap12:operation soapAction="http://networksolutions.com/UpdateInventory" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:UpdateInventorySecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="PerformMultiple">
      <soap12:operation soapAction="http://networksolutions.com/PerformMultiple" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:PerformMultipleSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ReadSiteSetting">
      <soap12:operation soapAction="http://networksolutions.com/ReadSiteSetting" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:ReadSiteSettingSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetUserToken">
      <soap12:operation soapAction="http://networksolutions.com/GetUserToken" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:GetUserTokenSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetUserKey">
      <soap12:operation soapAction="http://networksolutions.com/GetUserKey" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:GetUserKeySecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="CreateGiftCertificate">
      <soap12:operation soapAction="http://networksolutions.com/CreateGiftCertificate" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:CreateGiftCertificateSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ReadGiftCertificate">
      <soap12:operation soapAction="http://networksolutions.com/ReadGiftCertificate" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:ReadGiftCertificateSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="UpdateGiftCertificate">
      <soap12:operation soapAction="http://networksolutions.com/UpdateGiftCertificate" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:UpdateGiftCertificateSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="DeleteGiftCertificate">
      <soap12:operation soapAction="http://networksolutions.com/DeleteGiftCertificate" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
        <soap12:header message="tns:DeleteGiftCertificateSecurityCredential" part="SecurityCredential" use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:service name="NetSolEcomService">
    <wsdl:port name="NetSolEcomServiceSoap" binding="tns:NetSolEcomServiceSoap">
      <soap:address location="https://ecomapi.networksolutions.com/SoapService.asmx" />
    </wsdl:port>
    <wsdl:port name="NetSolEcomServiceSoap12" binding="tns:NetSolEcomServiceSoap12">
      <soap12:address location="https://ecomapi.networksolutions.com/SoapService.asmx" />
    </wsdl:port>
  </wsdl:service>
</wsdl:definitions>