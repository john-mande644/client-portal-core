package com.owd.dc.manifest.Manifestxml.Responses;

import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: May 13, 2010
 * Time: 10:05:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class PackageUnsuccessfulResponse {
     private dispatchConfirmation Dispatch_Confirmation;
    private packageHold Package_Hold;
    private packageError Package_Error;
    private String Package_Warning;
    private String Package_Message;

    public dispatchConfirmation getDispatch_Confirmation() {
        return Dispatch_Confirmation;
    }

    public void setDispatch_Confirmation(dispatchConfirmation dispatch_Confirmation) {
        Dispatch_Confirmation = dispatch_Confirmation;
    }

    public packageHold getPackage_Hold() {
        return Package_Hold;
    }

    public void setPackage_Hold(packageHold package_Hold) {
        Package_Hold = package_Hold;
    }

    public packageError getPackage_Error() {
        return Package_Error;
    }

    public void setPackage_Error(packageError package_Error) {
        Package_Error = package_Error;
    }

    public String getPackage_Warning() {
        return Package_Warning;
    }

    public void setPackage_Warning(String package_Warning) {
        Package_Warning = package_Warning;
    }

    public String getPackage_Message() {
        return Package_Message;
    }

    public void setPackage_Message(String package_Message) {
        Package_Message = package_Message;
    }

    public static class dispatchConfirmation{
        private String DispatchID;
        private String Reject_Package_Count;
        private String Version_Number;

        public String getDispatchID() {
            return DispatchID;
        }

        public void setDispatchID(String dispatchID) {
            DispatchID = dispatchID;
        }

        public String getReject_Package_Count() {
            return Reject_Package_Count;
        }

        public void setReject_Package_Count(String reject_Package_Count) {
            Reject_Package_Count = reject_Package_Count;
        }

        public String getVersion_Number() {
            return Version_Number;
        }

        public void setVersion_Number(String version_Number) {
            Version_Number = version_Number;
        }

    }

    public static class packageHold{
        private String PackageID;
        private String Line_Number;
        private String Reason_Code;
        private String Reason_Description;

        public String getPackageID() {
            return PackageID;
        }

        public void setPackage_ID(String packageID) {
            PackageID = packageID;
        }

        public String getLine_Number() {
            return Line_Number;
        }

        public void setLine_Number(String line_Number) {
            Line_Number = line_Number;
        }

        public String getReason_Code() {
            return Reason_Code;
        }

        public void setReason_Code(String reason_Code) {
            Reason_Code = reason_Code;
        }

        public String getReason_Description() {
            return Reason_Description;
        }

        public void setReason_Description(String reason_Description) {
            Reason_Description = reason_Description;
        }

    }

    public static class packageError{
        private List errors;

        public List getErrors() {
            return errors;
        }

        public void setErrors(List errors) {
            this.errors = errors;
        }
    }
    public XStream getXstream(){

           XStream x = new XStream();
                                   x.alias("Manifest",PackageUnsuccessfulResponse.class);
                                    x.addImplicitCollection(PackageUnsuccessfulResponse.packageError.class,"errors");
                                   x.alias("Error_Description",String.class);
        
        return x;
    }

   

}
