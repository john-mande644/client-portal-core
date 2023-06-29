<?xml version="1.0" encoding="utf-8"?>
<wsdl:definitions xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:tm="http://microsoft.com/wsdl/mime/textMatching/" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/" xmlns:mime="http://schemas.xmlsoap.org/wsdl/mime/" xmlns:tns="http://www.usps-cpas.com/usps-cpas/GSSAPI/" xmlns:s="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/" xmlns:http="http://schemas.xmlsoap.org/wsdl/http/" targetNamespace="http://www.usps-cpas.com/usps-cpas/GSSAPI/" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">
  <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">GSS Mailer Web Service</wsdl:documentation>
  <wsdl:types>
    <s:schema elementFormDefault="qualified" targetNamespace="http://www.usps-cpas.com/usps-cpas/GSSAPI/">
      <s:element name="AuthenticateUser">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="UserID" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="Password" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="LocationID" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="WorkstationID" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="AuthenticateUserResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="AuthenticateUserResult" type="tns:AuthenticateResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="AuthenticateResult">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="ResponseCode" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Message" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="AccessToken" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:element name="LogoutUser">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="AccessToken" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="LogoutUserResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="LogoutUserResult" type="tns:CommonResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="CommonResult">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="ResponseCode" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Message" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:element name="GetLabelsForPackage">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="PackageID" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="MailingAgentID" type="s:string" />
            <s:element minOccurs="1" maxOccurs="1" name="BoxNumber" type="s:int" />
            <s:element minOccurs="0" maxOccurs="1" name="LabelPrinterType" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="FileFormat" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="AccessToken" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="GetLabelsForPackageResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetLabelsForPackageResult" type="tns:LabelResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="LabelResult">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="ResponseCode" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Message" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Label" type="tns:ArrayOfBase64Binary" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ArrayOfBase64Binary">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="base64Binary" nillable="true" type="s:base64Binary" />
        </s:sequence>
      </s:complexType>
      <s:element name="GetImageLabelsForPackage">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="PackageID" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="MailingAgentID" type="s:string" />
            <s:element minOccurs="1" maxOccurs="1" name="BoxNumber" type="s:int" />
            <s:element minOccurs="0" maxOccurs="1" name="FileFormat" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="AccessToken" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="GetImageLabelsForPackageResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetImageLabelsForPackageResult" type="tns:LabelResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="RemoveLabeledPackage">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="PackageID" type="s:string" />
            <s:element minOccurs="1" maxOccurs="1" name="BoxNumber" type="s:int" />
            <s:element minOccurs="0" maxOccurs="1" name="AccessToken" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="RemoveLabeledPackageResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="RemoveLabeledPackageResult" type="tns:CommonResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="RemovePackageFromOpenDispatch">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="PackageID" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="MailingAgentID" type="s:string" />
            <s:element minOccurs="1" maxOccurs="1" name="BoxNumber" type="s:int" />
            <s:element minOccurs="0" maxOccurs="1" name="AccessToken" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="RemovePackageFromOpenDispatchResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="RemovePackageFromOpenDispatchResult" type="tns:CommonResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="ProcessThePackage">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="PackageID" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="MailingAgentID" type="s:string" />
            <s:element minOccurs="1" maxOccurs="1" name="BoxNumber" type="s:int" />
            <s:element minOccurs="0" maxOccurs="1" name="AccessToken" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="ProcessThePackageResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="ProcessThePackageResult" type="tns:CommonResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="LoadAndProcessPackageData">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="xmlDoc">
              <s:complexType mixed="true">
                <s:sequence>
                  <s:any />
                </s:sequence>
              </s:complexType>
            </s:element>
            <s:element minOccurs="0" maxOccurs="1" name="AccessToken" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="LoadAndProcessPackageDataResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="LoadAndProcessPackageDataResult">
              <s:complexType mixed="true">
                <s:sequence>
                  <s:any />
                </s:sequence>
              </s:complexType>
            </s:element>
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="LoadAndRecordLabeledPackage">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="xmlDoc">
              <s:complexType mixed="true">
                <s:sequence>
                  <s:any />
                </s:sequence>
              </s:complexType>
            </s:element>
            <s:element minOccurs="0" maxOccurs="1" name="AccessToken" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="LoadAndRecordLabeledPackageResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="LoadAndRecordLabeledPackageResult">
              <s:complexType mixed="true">
                <s:sequence>
                  <s:any />
                </s:sequence>
              </s:complexType>
            </s:element>
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="ProcessLabeledPackage">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="PackageID" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="MailingAgentID" type="s:string" />
            <s:element minOccurs="1" maxOccurs="1" name="BoxNumber" type="s:int" />
            <s:element minOccurs="0" maxOccurs="1" name="AccessToken" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="ProcessLabeledPackageResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="ProcessLabeledPackageResult" type="tns:CommonResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="TrackPackage">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="PackageID" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="MailingAgentID" type="s:string" />
            <s:element minOccurs="1" maxOccurs="1" name="BoxNumber" type="s:int" />
            <s:element minOccurs="0" maxOccurs="1" name="AccessToken" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="TrackPackageResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="TrackPackageResult" type="tns:TrackResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="TrackResult">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="ResponseCode" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Message" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="TrackingId" type="tns:ArrayOfString" />
          <s:element minOccurs="0" maxOccurs="1" name="TrackingEvent" type="tns:ArrayOfTrackingEvent" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ArrayOfString">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="string" nillable="true" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="ArrayOfTrackingEvent">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="unbounded" name="TrackingEvent" nillable="true" type="tns:TrackingEvent" />
        </s:sequence>
      </s:complexType>
      <s:complexType name="TrackingEvent">
        <s:sequence>
          <s:element minOccurs="0" maxOccurs="1" name="TrackingCode" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Description" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Location" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="DetailInfo" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="EventDate" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="TimeZone" type="s:string" />
        </s:sequence>
      </s:complexType>
      <s:element name="CloseDispatch">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="VehicleNum" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="VehicleType" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="DepDateTime" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="ArrDateTime" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="AccessToken" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="CloseDispatchResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="CloseDispatchResult" type="tns:CloseDispatchResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="CloseDispatchResult">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="ResponseCode" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Message" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="DispatchID" type="s:string" />
          <s:element minOccurs="1" maxOccurs="1" name="PackageCount" type="s:int" />
        </s:sequence>
      </s:complexType>
      <s:element name="CloseDispatchToDestinationLocation">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="DestinationLocationID" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="VehicleNum" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="VehicleType" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="DepDateTime" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="ArrDateTime" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="AccessToken" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="CloseDispatchToDestinationLocationResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="CloseDispatchToDestinationLocationResult" type="tns:CloseDispatchResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="GetAvailableReportsForDispatch">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="DispatchID" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="AccessToken" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="GetAvailableReportsForDispatchResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetAvailableReportsForDispatchResult" type="tns:GetAvailableReportResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GetAvailableReportResult">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="ResponseCode" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Message" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Report" type="tns:ArrayOfString" />
        </s:sequence>
      </s:complexType>
      <s:element name="GenerateReport">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="DispatchID" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="ReportID" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="AccessToken" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="GenerateReportResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GenerateReportResult" type="tns:GenerateReportResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="GenerateReportResult">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="ResponseCode" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Message" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="Report" type="s:base64Binary" />
        </s:sequence>
      </s:complexType>
      <s:element name="GenerateActivityReport">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="MailingAgentID" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="LocationID" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="StartDate" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="StopDate" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="ReportID" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="AccessToken" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="GenerateActivityReportResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GenerateActivityReportResult" type="tns:GenerateReportResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="OpenReceptacle">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="DestinationLocationID" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="AccessToken" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="OpenReceptacleResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="OpenReceptacleResult" type="tns:CommonResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="GetDestinationLocations">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="AccessToken" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="GetDestinationLocationsResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="GetDestinationLocationsResult" type="tns:DestinationLocationsResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:complexType name="DestinationLocationsResult">
        <s:sequence>
          <s:element minOccurs="1" maxOccurs="1" name="ResponseCode" type="s:int" />
          <s:element minOccurs="0" maxOccurs="1" name="Message" type="s:string" />
          <s:element minOccurs="0" maxOccurs="1" name="DestinationLoc" type="tns:ArrayOfString" />
        </s:sequence>
      </s:complexType>
      <s:element name="CloseReceptacle">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="ReceptacleID" type="s:string" />
            <s:element minOccurs="0" maxOccurs="1" name="AccessToken" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="CloseReceptacleResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="CloseReceptacleResult" type="tns:CommonResult" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="RefreshWebComponent">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="Val" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="RefreshWebComponentResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="1" maxOccurs="1" name="RefreshWebComponentResult" type="s:boolean" />
          </s:sequence>
        </s:complexType>
      </s:element>
    </s:schema>
  </wsdl:types>
  <wsdl:message name="AuthenticateUserSoapIn">
    <wsdl:part name="parameters" element="tns:AuthenticateUser" />
  </wsdl:message>
  <wsdl:message name="AuthenticateUserSoapOut">
    <wsdl:part name="parameters" element="tns:AuthenticateUserResponse" />
  </wsdl:message>
  <wsdl:message name="LogoutUserSoapIn">
    <wsdl:part name="parameters" element="tns:LogoutUser" />
  </wsdl:message>
  <wsdl:message name="LogoutUserSoapOut">
    <wsdl:part name="parameters" element="tns:LogoutUserResponse" />
  </wsdl:message>
  <wsdl:message name="GetLabelsForPackageSoapIn">
    <wsdl:part name="parameters" element="tns:GetLabelsForPackage" />
  </wsdl:message>
  <wsdl:message name="GetLabelsForPackageSoapOut">
    <wsdl:part name="parameters" element="tns:GetLabelsForPackageResponse" />
  </wsdl:message>
  <wsdl:message name="GetImageLabelsForPackageSoapIn">
    <wsdl:part name="parameters" element="tns:GetImageLabelsForPackage" />
  </wsdl:message>
  <wsdl:message name="GetImageLabelsForPackageSoapOut">
    <wsdl:part name="parameters" element="tns:GetImageLabelsForPackageResponse" />
  </wsdl:message>
  <wsdl:message name="RemoveLabeledPackageSoapIn">
    <wsdl:part name="parameters" element="tns:RemoveLabeledPackage" />
  </wsdl:message>
  <wsdl:message name="RemoveLabeledPackageSoapOut">
    <wsdl:part name="parameters" element="tns:RemoveLabeledPackageResponse" />
  </wsdl:message>
  <wsdl:message name="RemovePackageFromOpenDispatchSoapIn">
    <wsdl:part name="parameters" element="tns:RemovePackageFromOpenDispatch" />
  </wsdl:message>
  <wsdl:message name="RemovePackageFromOpenDispatchSoapOut">
    <wsdl:part name="parameters" element="tns:RemovePackageFromOpenDispatchResponse" />
  </wsdl:message>
  <wsdl:message name="ProcessThePackageSoapIn">
    <wsdl:part name="parameters" element="tns:ProcessThePackage" />
  </wsdl:message>
  <wsdl:message name="ProcessThePackageSoapOut">
    <wsdl:part name="parameters" element="tns:ProcessThePackageResponse" />
  </wsdl:message>
  <wsdl:message name="LoadAndProcessPackageDataSoapIn">
    <wsdl:part name="parameters" element="tns:LoadAndProcessPackageData" />
  </wsdl:message>
  <wsdl:message name="LoadAndProcessPackageDataSoapOut">
    <wsdl:part name="parameters" element="tns:LoadAndProcessPackageDataResponse" />
  </wsdl:message>
  <wsdl:message name="LoadAndRecordLabeledPackageSoapIn">
    <wsdl:part name="parameters" element="tns:LoadAndRecordLabeledPackage" />
  </wsdl:message>
  <wsdl:message name="LoadAndRecordLabeledPackageSoapOut">
    <wsdl:part name="parameters" element="tns:LoadAndRecordLabeledPackageResponse" />
  </wsdl:message>
  <wsdl:message name="ProcessLabeledPackageSoapIn">
    <wsdl:part name="parameters" element="tns:ProcessLabeledPackage" />
  </wsdl:message>
  <wsdl:message name="ProcessLabeledPackageSoapOut">
    <wsdl:part name="parameters" element="tns:ProcessLabeledPackageResponse" />
  </wsdl:message>
  <wsdl:message name="TrackPackageSoapIn">
    <wsdl:part name="parameters" element="tns:TrackPackage" />
  </wsdl:message>
  <wsdl:message name="TrackPackageSoapOut">
    <wsdl:part name="parameters" element="tns:TrackPackageResponse" />
  </wsdl:message>
  <wsdl:message name="CloseDispatchSoapIn">
    <wsdl:part name="parameters" element="tns:CloseDispatch" />
  </wsdl:message>
  <wsdl:message name="CloseDispatchSoapOut">
    <wsdl:part name="parameters" element="tns:CloseDispatchResponse" />
  </wsdl:message>
  <wsdl:message name="CloseDispatchToDestinationLocationSoapIn">
    <wsdl:part name="parameters" element="tns:CloseDispatchToDestinationLocation" />
  </wsdl:message>
  <wsdl:message name="CloseDispatchToDestinationLocationSoapOut">
    <wsdl:part name="parameters" element="tns:CloseDispatchToDestinationLocationResponse" />
  </wsdl:message>
  <wsdl:message name="GetAvailableReportsForDispatchSoapIn">
    <wsdl:part name="parameters" element="tns:GetAvailableReportsForDispatch" />
  </wsdl:message>
  <wsdl:message name="GetAvailableReportsForDispatchSoapOut">
    <wsdl:part name="parameters" element="tns:GetAvailableReportsForDispatchResponse" />
  </wsdl:message>
  <wsdl:message name="GenerateReportSoapIn">
    <wsdl:part name="parameters" element="tns:GenerateReport" />
  </wsdl:message>
  <wsdl:message name="GenerateReportSoapOut">
    <wsdl:part name="parameters" element="tns:GenerateReportResponse" />
  </wsdl:message>
  <wsdl:message name="GenerateActivityReportSoapIn">
    <wsdl:part name="parameters" element="tns:GenerateActivityReport" />
  </wsdl:message>
  <wsdl:message name="GenerateActivityReportSoapOut">
    <wsdl:part name="parameters" element="tns:GenerateActivityReportResponse" />
  </wsdl:message>
  <wsdl:message name="OpenReceptacleSoapIn">
    <wsdl:part name="parameters" element="tns:OpenReceptacle" />
  </wsdl:message>
  <wsdl:message name="OpenReceptacleSoapOut">
    <wsdl:part name="parameters" element="tns:OpenReceptacleResponse" />
  </wsdl:message>
  <wsdl:message name="GetDestinationLocationsSoapIn">
    <wsdl:part name="parameters" element="tns:GetDestinationLocations" />
  </wsdl:message>
  <wsdl:message name="GetDestinationLocationsSoapOut">
    <wsdl:part name="parameters" element="tns:GetDestinationLocationsResponse" />
  </wsdl:message>
  <wsdl:message name="CloseReceptacleSoapIn">
    <wsdl:part name="parameters" element="tns:CloseReceptacle" />
  </wsdl:message>
  <wsdl:message name="CloseReceptacleSoapOut">
    <wsdl:part name="parameters" element="tns:CloseReceptacleResponse" />
  </wsdl:message>
  <wsdl:message name="RefreshWebComponentSoapIn">
    <wsdl:part name="parameters" element="tns:RefreshWebComponent" />
  </wsdl:message>
  <wsdl:message name="RefreshWebComponentSoapOut">
    <wsdl:part name="parameters" element="tns:RefreshWebComponentResponse" />
  </wsdl:message>
  <wsdl:portType name="ProcessPackageSoap">
    <wsdl:operation name="AuthenticateUser">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">This method is called first to consume GSS Mailer Web Services. Use the returned access token for each subsequent request.  The token has a session timeout of 20 minutes.</wsdl:documentation>
      <wsdl:input message="tns:AuthenticateUserSoapIn" />
      <wsdl:output message="tns:AuthenticateUserSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="LogoutUser">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Logs out the user.</wsdl:documentation>
      <wsdl:input message="tns:LogoutUserSoapIn" />
      <wsdl:output message="tns:LogoutUserSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetLabelsForPackage">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Retrieves labels for a specified mailer package in PDF format.</wsdl:documentation>
      <wsdl:input message="tns:GetLabelsForPackageSoapIn" />
      <wsdl:output message="tns:GetLabelsForPackageSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetImageLabelsForPackage">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Retrieves labels for a specified mailer package in image(JPG,PNG) format.</wsdl:documentation>
      <wsdl:input message="tns:GetImageLabelsForPackageSoapIn" />
      <wsdl:output message="tns:GetImageLabelsForPackageSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="RemoveLabeledPackage">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Removes a labeled package. Used by labeling locations only.</wsdl:documentation>
      <wsdl:input message="tns:RemoveLabeledPackageSoapIn" />
      <wsdl:output message="tns:RemoveLabeledPackageSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="RemovePackageFromOpenDispatch">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Removes a package from the open dispatch</wsdl:documentation>
      <wsdl:input message="tns:RemovePackageFromOpenDispatchSoapIn" />
      <wsdl:output message="tns:RemovePackageFromOpenDispatchSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="ProcessThePackage">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Process a removed or new package.</wsdl:documentation>
      <wsdl:input message="tns:ProcessThePackageSoapIn" />
      <wsdl:output message="tns:ProcessThePackageSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="LoadAndProcessPackageData">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Accepts package data from a mailer in the predefined XML format and processes it.</wsdl:documentation>
      <wsdl:input message="tns:LoadAndProcessPackageDataSoapIn" />
      <wsdl:output message="tns:LoadAndProcessPackageDataSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="LoadAndRecordLabeledPackage">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Accepts package data from an originating mailer in the predefined XML format to label package. Used by labeling locations only.</wsdl:documentation>
      <wsdl:input message="tns:LoadAndRecordLabeledPackageSoapIn" />
      <wsdl:output message="tns:LoadAndRecordLabeledPackageSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="ProcessLabeledPackage">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Process a labeled package at consolidator mailer location.</wsdl:documentation>
      <wsdl:input message="tns:ProcessLabeledPackageSoapIn" />
      <wsdl:output message="tns:ProcessLabeledPackageSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="TrackPackage">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Retrieve the history of tracking events for a package.</wsdl:documentation>
      <wsdl:input message="tns:TrackPackageSoapIn" />
      <wsdl:output message="tns:TrackPackageSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="CloseDispatch">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Closes the dispatch for the mailer at his location.</wsdl:documentation>
      <wsdl:input message="tns:CloseDispatchSoapIn" />
      <wsdl:output message="tns:CloseDispatchSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="CloseDispatchToDestinationLocation">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Closes the dispatch to the destination office of exchange(OE) location.  Used only by mailers using receptacles.</wsdl:documentation>
      <wsdl:input message="tns:CloseDispatchToDestinationLocationSoapIn" />
      <wsdl:output message="tns:CloseDispatchToDestinationLocationSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetAvailableReportsForDispatch">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Returns a list of all the reports that are available for a dispatch.</wsdl:documentation>
      <wsdl:input message="tns:GetAvailableReportsForDispatchSoapIn" />
      <wsdl:output message="tns:GetAvailableReportsForDispatchSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GenerateReport">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Gererate the requested report for a dispatch.</wsdl:documentation>
      <wsdl:input message="tns:GenerateReportSoapIn" />
      <wsdl:output message="tns:GenerateReportSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GenerateActivityReport">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Generate the requested report of a mailing agent for a specific loction and time period.</wsdl:documentation>
      <wsdl:input message="tns:GenerateActivityReportSoapIn" />
      <wsdl:output message="tns:GenerateActivityReportSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="OpenReceptacle">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Open a receptacle for a destination location.  Used only by mailers using receptacles.</wsdl:documentation>
      <wsdl:input message="tns:OpenReceptacleSoapIn" />
      <wsdl:output message="tns:OpenReceptacleSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="GetDestinationLocations">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Returns a list of all the destination locations that are available for a mailer.  Used only by mailers using receptacles.</wsdl:documentation>
      <wsdl:input message="tns:GetDestinationLocationsSoapIn" />
      <wsdl:output message="tns:GetDestinationLocationsSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="CloseReceptacle">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">Set the Receptacle Id and close the receptacle when packages are available.  Otherwise delete it.  Used only by mailers using receptacles.</wsdl:documentation>
      <wsdl:input message="tns:CloseReceptacleSoapIn" />
      <wsdl:output message="tns:CloseReceptacleSoapOut" />
    </wsdl:operation>
    <wsdl:operation name="RefreshWebComponent">
      <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">GSS Support Only: Refreshes the Web Component</wsdl:documentation>
      <wsdl:input message="tns:RefreshWebComponentSoapIn" />
      <wsdl:output message="tns:RefreshWebComponentSoapOut" />
    </wsdl:operation>
  </wsdl:portType>
  <wsdl:binding name="ProcessPackageSoap" type="tns:ProcessPackageSoap">
    <soap:binding transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="AuthenticateUser">
      <soap:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/AuthenticateUser" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="LogoutUser">
      <soap:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/LogoutUser" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetLabelsForPackage">
      <soap:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/GetLabelsForPackage" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetImageLabelsForPackage">
      <soap:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/GetImageLabelsForPackage" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="RemoveLabeledPackage">
      <soap:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/RemoveLabeledPackage" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="RemovePackageFromOpenDispatch">
      <soap:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/RemovePackageFromOpenDispatch" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ProcessThePackage">
      <soap:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/ProcessThePackage" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="LoadAndProcessPackageData">
      <soap:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/LoadAndProcessPackageData" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="LoadAndRecordLabeledPackage">
      <soap:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/LoadAndRecordLabeledPackage" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ProcessLabeledPackage">
      <soap:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/ProcessLabeledPackage" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="TrackPackage">
      <soap:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/TrackPackage" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="CloseDispatch">
      <soap:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/CloseDispatch" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="CloseDispatchToDestinationLocation">
      <soap:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/CloseDispatchToDestinationLocation" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetAvailableReportsForDispatch">
      <soap:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/GetAvailableReportsForDispatch" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GenerateReport">
      <soap:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/GenerateReport" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GenerateActivityReport">
      <soap:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/GenerateActivityReport" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="OpenReceptacle">
      <soap:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/OpenReceptacle" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetDestinationLocations">
      <soap:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/GetDestinationLocations" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="CloseReceptacle">
      <soap:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/CloseReceptacle" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="RefreshWebComponent">
      <soap:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/RefreshWebComponent" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:binding name="ProcessPackageSoap12" type="tns:ProcessPackageSoap">
    <soap12:binding transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="AuthenticateUser">
      <soap12:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/AuthenticateUser" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="LogoutUser">
      <soap12:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/LogoutUser" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetLabelsForPackage">
      <soap12:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/GetLabelsForPackage" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetImageLabelsForPackage">
      <soap12:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/GetImageLabelsForPackage" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="RemoveLabeledPackage">
      <soap12:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/RemoveLabeledPackage" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="RemovePackageFromOpenDispatch">
      <soap12:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/RemovePackageFromOpenDispatch" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ProcessThePackage">
      <soap12:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/ProcessThePackage" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="LoadAndProcessPackageData">
      <soap12:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/LoadAndProcessPackageData" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="LoadAndRecordLabeledPackage">
      <soap12:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/LoadAndRecordLabeledPackage" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="ProcessLabeledPackage">
      <soap12:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/ProcessLabeledPackage" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="TrackPackage">
      <soap12:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/TrackPackage" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="CloseDispatch">
      <soap12:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/CloseDispatch" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="CloseDispatchToDestinationLocation">
      <soap12:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/CloseDispatchToDestinationLocation" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetAvailableReportsForDispatch">
      <soap12:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/GetAvailableReportsForDispatch" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GenerateReport">
      <soap12:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/GenerateReport" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GenerateActivityReport">
      <soap12:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/GenerateActivityReport" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="OpenReceptacle">
      <soap12:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/OpenReceptacle" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="GetDestinationLocations">
      <soap12:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/GetDestinationLocations" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="CloseReceptacle">
      <soap12:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/CloseReceptacle" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
    <wsdl:operation name="RefreshWebComponent">
      <soap12:operation soapAction="http://www.usps-cpas.com/usps-cpas/GSSAPI/RefreshWebComponent" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:service name="ProcessPackage">
    <wsdl:documentation xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">GSS Mailer Web Service</wsdl:documentation>
    <wsdl:port name="ProcessPackageSoap" binding="tns:ProcessPackageSoap">
      <soap:address location="https://www.usps-cpas.com/usps-cpas/TestGSSAPI/GSSMailerWebService.asmx" />
    </wsdl:port>
    <wsdl:port name="ProcessPackageSoap12" binding="tns:ProcessPackageSoap12">
      <soap12:address location="https://www.usps-cpas.com/usps-cpas/TestGSSAPI/GSSMailerWebService.asmx" />
    </wsdl:port>
  </wsdl:service>
</wsdl:definitions>