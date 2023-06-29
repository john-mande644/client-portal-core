package com.owd.dc.manifest.Manifestxml.Responses;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: May 14, 2010
 * Time: 11:09:17 AM
 * To change this template use File | Settings | File Templates.
 */
@XStreamAlias("Manifest")
public class PackageSuccessfulResponse {
    private dispatchConfirmation Dispatch_Confirmation;
    private uspspackage Package;

    public dispatchConfirmation getDispatch_Confirmation() {
        return Dispatch_Confirmation;
    }

    public void setDispatch_Confirmation(dispatchConfirmation dispatch_Confirmation) {
        Dispatch_Confirmation = dispatch_Confirmation;
    }

    public uspspackage getPackage() {
        return Package;
    }

    public void setPackage(uspspackage aPackage) {
        Package = aPackage;
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
   public static class  packageIdentifier{
       private String PackageID;
       private String PackageID_Type;
       private String AgentName;

       public String getAgentName() {
           return AgentName;
       }

       public void setAgentName(String agentName) {
           AgentName = agentName;
       }

       public String getPackageID() {
           return PackageID;
       }

       public void setPackageID(String packageID) {
           PackageID = packageID;
       }

       public String getPackageID_Type() {
           return PackageID_Type;
       }

       public void setPackageID_Type(String packageID_Type) {
           PackageID_Type = packageID_Type;
       }
   }
    public static class uspspackage{
        @XStreamAsAttribute
        private String PackageID;
        private String Postage_Paid;
        private String Postage_Paid_Currency_Type;
        private packageIdentifier Package_Identifier;

        public String getPackageID() {
            return PackageID;
        }

        public void setPackageID(String packageID) {
            PackageID = packageID;
        }

        public String getPostage_Paid() {
            return Postage_Paid;
        }

        public void setPostage_Paid(String postage_Paid) {
            Postage_Paid = postage_Paid;
        }

        public String getPostage_Paid_Currency_Type() {
            return Postage_Paid_Currency_Type;
        }

        public void setPostage_Paid_Currency_Type(String postage_Paid_Currency_Type) {
            Postage_Paid_Currency_Type = postage_Paid_Currency_Type;
        }

        public packageIdentifier getPackage_Identifier() {
            return Package_Identifier;
        }

        public void setPackage_Identifier(packageIdentifier package_Identifier) {
            Package_Identifier = package_Identifier;
        }
    }

    public static XStream getXstream(){

         XStream x = new XStream();
                                  x.alias("Manifest",PackageSuccessfulResponse.class);
                                 /* x.useAttributeFor(PackageSuccessfulResponse.uspspackage.class,"PackageID");*/

        return x;
        
    }
}
