/**
 * ProcessPackageSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package GSSMailer;

public interface ProcessPackageSoap_PortType extends java.rmi.Remote {

    /**
     * This method is called first to consume GSS Mailer Web Services.
     * Use the returned access token for each subsequent request.  The token
     * has a session timeout of 20 minutes.
     */
    public GSSMailer.AuthenticateResult authenticateUser(java.lang.String userID, java.lang.String password, java.lang.String locationID, java.lang.String workstationID) throws java.rmi.RemoteException;

    /**
     * Logs out the user.
     */
    public GSSMailer.CommonResult logoutUser(java.lang.String accessToken) throws java.rmi.RemoteException;

    /**
     * Retrieves labels for a specified mailer package in PDF format.
     */
    public GSSMailer.LabelResult getLabelsForPackage(java.lang.String packageID, java.lang.String mailingAgentID, int boxNumber, java.lang.String labelPrinterType, java.lang.String fileFormat, java.lang.String accessToken) throws java.rmi.RemoteException;

    /**
     * Retrieves labels for a specified mailer package in image(JPG,PNG)
     * format.
     */
    public GSSMailer.LabelResult getImageLabelsForPackage(java.lang.String packageID, java.lang.String mailingAgentID, int boxNumber, java.lang.String fileFormat, java.lang.String accessToken) throws java.rmi.RemoteException;

    /**
     * Removes a labeled package. Used by labeling locations only.
     */
    public GSSMailer.CommonResult removeLabeledPackage(java.lang.String packageID, int boxNumber, java.lang.String accessToken) throws java.rmi.RemoteException;

    /**
     * Removes a package from the open dispatch
     */
    public GSSMailer.CommonResult removePackageFromOpenDispatch(java.lang.String packageID, java.lang.String mailingAgentID, int boxNumber, java.lang.String accessToken) throws java.rmi.RemoteException;

    /**
     * Process a removed or new package.
     */
    public GSSMailer.CommonResult processThePackage(java.lang.String packageID, java.lang.String mailingAgentID, int boxNumber, java.lang.String accessToken) throws java.rmi.RemoteException;

    /**
     * Accepts package data from a mailer in the predefined XML format
     * and processes it.
     */
    public GSSMailer.LoadAndProcessPackageDataResponseLoadAndProcessPackageDataResult loadAndProcessPackageData(GSSMailer.LoadAndProcessPackageDataXmlDoc xmlDoc, java.lang.String accessToken) throws java.rmi.RemoteException;

    /**
     * Accepts package data from a mailer in the predefined XML format.
     * Overwrites previous version if reloaded.
     */
    public GSSMailer.LoadPackageDataResponseLoadPackageDataResult loadPackageData(GSSMailer.LoadPackageDataXmlDoc xmlDoc, java.lang.String accessToken) throws java.rmi.RemoteException;

    /**
     * Accepts package data from an originating mailer in the predefined
     * XML format to label package. Used by labeling locations only.
     */
    public GSSMailer.LoadAndRecordLabeledPackageResponseLoadAndRecordLabeledPackageResult loadAndRecordLabeledPackage(GSSMailer.LoadAndRecordLabeledPackageXmlDoc xmlDoc, java.lang.String accessToken) throws java.rmi.RemoteException;

    /**
     * Process a labeled package at consolidator mailer location.
     */
    public GSSMailer.CommonResult processLabeledPackage(java.lang.String packageID, java.lang.String mailingAgentID, int boxNumber, java.lang.String accessToken) throws java.rmi.RemoteException;

    /**
     * Retrieve the history of tracking events for a package.
     */
    public GSSMailer.TrackResult trackPackage(java.lang.String packageID, java.lang.String mailingAgentID, int boxNumber, java.lang.String accessToken) throws java.rmi.RemoteException;

    /**
     * Closes the dispatch for the mailer at his location.
     */
    public GSSMailer.CloseDispatchResult closeDispatch(java.lang.String vehicleNum, java.lang.String vehicleType, java.lang.String depDateTime, java.lang.String arrDateTime, java.lang.String accessToken) throws java.rmi.RemoteException;

    /**
     * Closes the dispatch to the destination office of exchange(OE)
     * location.  Used only by mailers using receptacles.
     */
    public GSSMailer.CloseDispatchResult closeDispatchToDestinationLocation(java.lang.String destinationLocationID, java.lang.String vehicleNum, java.lang.String vehicleType, java.lang.String depDateTime, java.lang.String arrDateTime, java.lang.String accessToken) throws java.rmi.RemoteException;

    /**
     * Returns a list of all the reports that are available for a
     * dispatch.
     */
    public GSSMailer.GetAvailableReportResult getAvailableReportsForDispatch(java.lang.String dispatchID, java.lang.String accessToken) throws java.rmi.RemoteException;

    /**
     * Gererate the requested report for a dispatch.
     */
    public GSSMailer.GenerateReportResult generateReport(java.lang.String dispatchID, java.lang.String reportID, java.lang.String accessToken) throws java.rmi.RemoteException;

    /**
     * Generate the requested report of a mailing agent for a specific
     * loction and time period.
     */
    public GSSMailer.GenerateReportResult generateActivityReport(java.lang.String mailingAgentID, java.lang.String locationID, java.lang.String startDate, java.lang.String stopDate, java.lang.String reportID, java.lang.String accessToken) throws java.rmi.RemoteException;

    /**
     * Open a receptacle for a destination location.  Used only by
     * mailers using receptacles.
     */
    public GSSMailer.CommonResult openReceptacle(java.lang.String destinationLocationID, java.lang.String accessToken) throws java.rmi.RemoteException;

    /**
     * Returns a list of all the destination locations that are available
     * for a mailer.  Used only by mailers using receptacles.
     */
    public GSSMailer.DestinationLocationsResult getDestinationLocations(java.lang.String accessToken) throws java.rmi.RemoteException;

    /**
     * Set the Receptacle Id and close the receptacle when packages
     * are available.  Otherwise delete it.  Used only by mailers using receptacles.
     */
    public GSSMailer.CommonResult closeReceptacle(java.lang.String receptacleID, java.lang.String accessToken) throws java.rmi.RemoteException;

    /**
     * GSS Support Only: Refreshes the Web Component
     */
    public boolean refreshWebComponent(java.lang.String val) throws java.rmi.RemoteException;

    /**
     * Freezes dispatch for a give location
     */
    public GSSMailer.FrozenDispatchResult freezeDispatch(java.lang.String freezeDispatchID, java.lang.String accessToken) throws java.rmi.RemoteException;

    /**
     * Closes the frozen dispatch for the mailer at his location.
     */
    public GSSMailer.CloseDispatchResult closeFrozenDispatch(java.lang.String freezeDispatchID, java.lang.String vehicleNum, java.lang.String vehicleType, java.lang.String depDateTime, java.lang.String arrDateTime, java.lang.String accessToken) throws java.rmi.RemoteException;

    /**
     * Freezes dispatch for a location
     */
    public GSSMailer.CommonResult addPackageToFrozenDispatch(java.lang.String freezeDispatchID, java.lang.String packageID, java.lang.String accessToken) throws java.rmi.RemoteException;

    /**
     * Removes a package from Frozen dispatch
     */
    public GSSMailer.CommonResult removePackageFromFrozenDispatch(java.lang.String freezeDispatchID, java.lang.String packageID, java.lang.String mailingAgentID, int boxNumber, java.lang.String accessToken) throws java.rmi.RemoteException;

    /**
     * Retrives Frozen dispatches in a location
     */
    public GSSMailer.FrozenDispatchesResult retrieveFrozenDispatchesInfo(java.lang.String accessToken) throws java.rmi.RemoteException;

    /**
     * Retrives Frozen dispatch Package details
     */
    public GSSMailer.FrozenDispatchPackagesResult retrieveFrozenDispatchPackagesInfo(java.lang.String freezeDispatchID, java.lang.String accessToken) throws java.rmi.RemoteException;

    /**
     * Retrieve the history of tracking events with Postal Code for
     * a package .
     */
    public GSSMailer.TrackingWithPostalCodeResult trackPackageWithPostalCode(java.lang.String packageID, java.lang.String mailingAgentID, int boxNumber, java.lang.String accessToken) throws java.rmi.RemoteException;
}
