package com.owd.fedEx;

import com.owd.core.TagUtilities;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.preMailingEfficiency.PreMailingValidator;
import com.owd.core.managers.ScanManager;
import com.owd.fedEx.ShipService.*;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.*;
import org.apache.axis.types.NonNegativeInteger;
import org.apache.axis.types.PositiveInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by danny on 11/11/2018.
 */
public class SmartPostReturn {


    protected final static Logger log = LogManager.getLogger();




    public static void generateAndSaveLabel(OwdOrder order){
        generateAndSaveLabel(order,false,false);
    }
    public static void generateAndSaveLabel(OwdOrder order,boolean testing,boolean write){

        try {
            //Generate the label
            //todo deal with errors.
            ProcessShipmentReply reply = SmartPostReturn.getSmartPostReturnLabelForOwdOrder(order,testing,write);
            //get Label data and save
            String tracking = printMasterTrackingNumber(reply.getCompletedShipmentDetail());

            CompletedShipmentDetail csd = reply.getCompletedShipmentDetail();


            ShippingDocument doc = csd.getCompletedPackageDetails(0).getLabel();
            ShippingDocumentPart[] parts = doc.getParts();
            ShippingDocumentPart part = parts[0];
           addLabelToAmazonBucket(tracking, order, part.getImage());
            //Save tracking
            addReturnTrackingToOrder(order,tracking);
            //add url to order
            addUrlToOrder(order,tracking);

        }catch (Exception e){
            e.printStackTrace();

        }

    }



    public static void addReturnTrackingToOrder(OwdOrder order,String tracking) throws Exception{



        try {



            OrderShipInfo2 info = order.getShipInfo2();
            info.setReturnTracking(tracking);

            HibernateSession.currentSession().saveOrUpdate(info);
            HibernateSession.currentSession().flush();
            log.debug("Committing Update");
            HibUtils.commit(HibernateSession.currentSession());
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("Unable to save return tracking to order");
        }



    }

    /**
     *
     * @param order OWDOrder to check
     * @return returns int. 0 for no return needed. 1 for label to be saved and printed on packing slip
     */
    public static int doesOrderNeedReturnLabel(OwdOrder order){

        Map<String,String> tags = TagUtilities.getTagMap("ORDER",order.getOrderId());
        if(tags.size()>0&&tags.containsKey(TagUtilities.kSPReturn)){

            return Integer.parseInt(tags.get(TagUtilities.kSPReturn));

        }
        return 0 ;

    }

    public static byte[] getLabelFromAmazonBucket(OwdOrder order){

        String filename = "";
        try {

            Criteria crit = HibernateSession.currentSession().createCriteria(ScanReturnLabel.class);
            crit.add(Restrictions.eq("scan_id", order.getOrderId()));



            List<ScanReturnLabel> scandocs = crit.list();
            if(scandocs.size()>0){
                filename = scandocs.get(0).getName();
                return ScanManager.getReturnLabelFromOwdOrder(order, filename);
            }else{
                return new byte[0];
            }




        }catch (Exception e){
            e.printStackTrace();
        }
        return new byte[0];
    }

    public static boolean addUrlToOrder(OwdOrder order, String tracking){
        boolean success = false;

        try{
            String url = "https://s3-us-west-2.amazonaws.com/owd.s3.returnlabels/returnlabel/"+order.getClient().getClientId()+"/"+order.getOrderId() + "_" + tracking + ".png";
            TagUtilities.setOrderTagForOwdOrder(order,TagUtilities.kReturnLabel,url);
            success = true;

        }catch (Exception e){
            e.printStackTrace();
        }



        return success;
    }

    public static boolean addLabelToAmazonBucket(String tracking, OwdOrder order, byte[] data){
        boolean success = false;
        try {
            ScanManager.addReturnLabelToOwdOrder(order, data, order.getOrderId() + "_" + tracking + ".png", "");

            success = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return success;
    }
    public static void markOrderForSmartPostShipmentViaOrder(Order order) throws Exception{
            order.addTag(TagUtilities.kSPReturn,"1");
    }
    public static void martOrderForSmartPostShipmentViaOrderId(int orderId){
        TagUtilities.setOrderTag(orderId,TagUtilities.kSPReturn,"1");
    }

    public static ProcessShipmentReply getSmartPostReturnLabelForOwdOrder(OwdOrder order, boolean testing) throws Exception{
        return getSmartPostReturnLabelForOwdOrder(order,testing,false);
    }

    public static ProcessShipmentReply getSmartPostReturnLabelForOwdOrder(OwdOrder order, boolean testing,boolean writeFiles) throws Exception{

        ProcessShipmentRequest request = loadSmartPostReturnShipmentFromOwdOrder(order,testing);


        ShipPortType port;
        if(testing){
            System.out.println("Doing test service locator");
            TestShipServiceLocator service = new TestShipServiceLocator();
            port = service.getShipServicePort();
        }else{
            ShipServiceLocator service = new ShipServiceLocator();
            port = service.getShipServicePort();

        }


        ProcessShipmentReply reply = port.processShipment(request); // This is the call to the ship web service passing in a request object and returning a reply object

        if (isResponseOk(reply.getHighestSeverity())&&writeFiles) // check if the call was successful
        {

            writeServiceOutput(reply);
        }
        printNotifications(reply.getNotifications());



        return reply;


    }



    public static ProcessShipmentRequest loadSmartPostReturnShipmentFromOwdOrder(OwdOrder order ,boolean testing){


        ProcessShipmentRequest request = new ProcessShipmentRequest();


        request.setClientDetail(createClientDetail(testing,order.getFacilityCode()));
        request.setWebAuthenticationDetail(createWebAuthenticationDetail(testing,order.getFacilityCode()));
        //
        TransactionDetail transactionDetail = new TransactionDetail();
        transactionDetail.setCustomerTransactionId("SmartPostLabel-"+ order.getOrderNum()); // The client will get the same value back in the response
        request.setTransactionDetail(transactionDetail);

        //
        VersionId versionId = new VersionId();
        versionId.setServiceId("ship");
        versionId.setMajor(23);
        versionId.setMinor(0);
        versionId.setIntermediate(0);

        request.setVersion(versionId);
        //
        RequestedShipment requestedShipment = new RequestedShipment();
        requestedShipment.setShipTimestamp(Calendar.getInstance()); // Ship date and time
        requestedShipment.setServiceType(ServiceType.SMART_POST); // Service types are STANDARD_OVERNIGHT, PRIORITY_OVERNIGHT, FEDEX_GROUND ...
        requestedShipment.setDropoffType(DropoffType.REGULAR_PICKUP);
        requestedShipment.setPackagingType(PackagingType.YOUR_PACKAGING); // Packaging type FEDEX_BOX, FEDEX_PAK, FEDEX_TUBE, YOUR_PACKAGING, ...

        //This set required RETURN_SHIPMENT value
        requestedShipment.setSpecialServicesRequested(getSpecialServices());

      /*  ShipmentAuthorizationDetail shipmentAuthorizationDetail = new ShipmentAuthorizationDetail();
        shipmentAuthorizationDetail.setAccountNumber("612041369");
        requestedShipment.setShipmentAuthorizationDetail(shipmentAuthorizationDetail);*/


        //
        requestedShipment.setShipper(addShipper(order.getFacilityCode())); // Sender information
        //
        requestedShipment.setRecipient(addRecipient(order));
        //
        requestedShipment.setShippingChargesPayment(addShippingChargesPayment(testing,order.getFacilityCode()));
        //

        SmartPostShipmentDetail smartPost = new SmartPostShipmentDetail();
       // smartPost.setAncillaryEndorsement(SmartPostAncillaryEndorsementType.ADDRESS_CORRECTION);
        smartPost.setCustomerManifestId(order.getOrderNum());
        smartPost.setHubId(getHub(testing,order.getFacilityCode()));
        smartPost.setIndicia(SmartPostIndiciaType.PARCEL_RETURN);
        requestedShipment.setSmartPostDetail(smartPost);

        requestedShipment.setLabelSpecification(addLabelSpecification());

        //
        requestedShipment.setPackageCount(new NonNegativeInteger("1"));

        RequestedPackageLineItem[] items = new RequestedPackageLineItem[1];
                items[0] = addRequestedPackageLineItem(order.getOrderNum());
         requestedShipment.setRequestedPackageLineItems(items);


        //
       // requestedShipment.setRequestedPackageLineItems(new RequestedPackageLineItem[] { addSmartpostPackageLineItem() });
        //
        request.setRequestedShipment(requestedShipment);

        return request;
    }

    private static RequestedPackageLineItem addRequestedPackageLineItem(String orderNumber){
        RequestedPackageLineItem item = new RequestedPackageLineItem();
        item.setSequenceNumber(new PositiveInteger("1"));
      //  item.setGroupPackageCount(new PositiveInteger("1"));
        Weight w = new Weight();
        w.setUnits(WeightUnits.LB);
        w.setValue(new BigDecimal(1));
        item.setWeight(w);
        CustomerReference ref = new CustomerReference();
        ref.setCustomerReferenceType(CustomerReferenceType.RMA_ASSOCIATION);
        ref.setValue(orderNumber);
        CustomerReference[] refs = new CustomerReference[1];
        refs[0] = ref;
        item.setCustomerReferences(refs);
        return item;
    }

    private static LabelSpecification addLabelSpecification(){
        LabelSpecification labelSpecification = new LabelSpecification(); // Label specification
        labelSpecification.setImageType(ShippingDocumentImageType.PNG);// Image types PDF, PNG, DPL, ...
        labelSpecification.setLabelFormatType(LabelFormatType.COMMON2D); //LABEL_DATA_ONLY, COMMON2D
        labelSpecification.setLabelStockType(LabelStockType.value1); // STOCK_4X6.75_LEADING_DOC_TAB
        //labelSpecification.setLabelPrintingOrientation(LabelPrintingOrientationType.TOP_EDGE_OF_TEXT_FIRST);
        return labelSpecification;
    }

    private static String getHub(boolean testing, String facility){
        //todo return proper testing and facility id
        if(testing) {
            return "5531";
        }else{
            if(facility.equalsIgnoreCase("DC6")){
                return "5929";
            }
            if(facility.equalsIgnoreCase("DC1")){
                return "5552";
            }
            if(facility.equalsIgnoreCase("DC7")){
                return "5431";
            }
        }
        return "";
    }

    private static ShipmentSpecialServicesRequested getSpecialServices(){
        ShipmentSpecialServicesRequested services = new ShipmentSpecialServicesRequested();
        ShipmentSpecialServiceType[] types = new ShipmentSpecialServiceType[1];
        types[0] = ShipmentSpecialServiceType.RETURN_SHIPMENT;
        services.setSpecialServiceTypes(types);
        ReturnShipmentDetail detail = new ReturnShipmentDetail();
        detail.setReturnType(ReturnType.PRINT_RETURN_LABEL);
        services.setReturnShipmentDetail(detail);

        return services;
    }

    private static ClientDetail createClientDetail(boolean testing, String facility) {
        ClientDetail clientDetail = new ClientDetail();
        String accountNumber = System.getProperty("accountNumber");
        String meterNumber = System.getProperty("meterNumber");

        if(testing){
            if(facility.equalsIgnoreCase("DC6")) {//DC6 test account
                accountNumber = "612041369";
                meterNumber = "119092117";
            }
            if(facility.equalsIgnoreCase("DC1")) {//DC6 test account
                accountNumber = "510087100";
                meterNumber = "114070391";
            }
        }else{
            if(facility.equalsIgnoreCase("DC6")){
                accountNumber = "876991233";
                meterNumber = "113761496";
            }
            if(facility.equalsIgnoreCase("DC1")){
                accountNumber = "675425590";
                meterNumber = "251498813";
            }
        }
        //
        // See if the accountNumber and meterNumber properties are set,
        // if set use those values, otherwise default them to "XXX"
        //
        if (accountNumber == null) {
            accountNumber = "XXX"; // Replace "XXX" with clients account number
        }
        if (meterNumber == null) {
            meterNumber = "XXX"; // Replace "XXX" with clients meter number
        }
        clientDetail.setAccountNumber(accountNumber);
        clientDetail.setMeterNumber(meterNumber);
        return clientDetail;
    }
    private static Party addShipper(String facility){
        Party shipperParty = new Party(); // Sender information
        Contact shipperContact = new Contact();
        shipperContact.setPersonName("Returns");
        shipperContact.setCompanyName("OneWorldDirect");
        shipperContact.setPhoneNumber("6058457172");
        Address shipperAddress = new Address();
        shipperAddress.setStreetLines(new String[] {"1915 10th AVe E"});
        shipperAddress.setCity("Mobridge");
        shipperAddress.setStateOrProvinceCode("SD");
        shipperAddress.setPostalCode("57601");
        shipperAddress.setCountryCode("US");
        shipperParty.setContact(shipperContact);
        shipperParty.setAddress(shipperAddress);
        return shipperParty;
    }
    private static Party addRecipient(OwdOrder order){
        Party recipientParty = new Party(); // Recipient information
        Contact recipientContact = new Contact();
        recipientContact.setPersonName(order.getShipinfo().getShipFirstName() + " " +order.getShipinfo().getShipLastName());
        recipientContact.setCompanyName(order.getShipinfo().getShipCompanyName());
        recipientContact.setPhoneNumber(order.getShipinfo().getShipPhoneNum());
        if(recipientContact.getPhoneNumber().length()==0){
            recipientContact.setPhoneNumber("6058457172");
        }
        Address recipientAddress = new Address();
        recipientAddress.setStreetLines(new String[] {order.getShipinfo().getShipAddressOne(),order.getShipinfo().getShipAddressTwo()});
        recipientAddress.setCity(order.getShipinfo().getShipCity());
        recipientAddress.setStateOrProvinceCode(order.getShipinfo().getShipState());
        recipientAddress.setPostalCode(order.getShipinfo().getShipZip());
        recipientAddress.setCountryCode("US");
       // recipientAddress.setResidential(false);
        recipientParty.setContact(recipientContact);
        recipientParty.setAddress(recipientAddress);
        return recipientParty;
    }



    private static WebAuthenticationDetail createWebAuthenticationDetail(boolean testing,String facility) {
        WebAuthenticationCredential userCredential = new WebAuthenticationCredential();
        String key ="";
        String password="";
        if(testing){
            if(facility.equalsIgnoreCase("DC6")) {
                key = "fVBcbhZCIIz2Fxhm";
                password = "m29KzrYxQWVhsCgQh1vYuuZKk";
            }
            if(facility.equalsIgnoreCase("DC1")) {
                key = "ZjGx3plFLHgjw6FJ";
                password = "853W5gBgkVJsTuGPLSwOj2HoV";
            }
        }else{
            if(facility.equalsIgnoreCase("DC6")){
                key = "0kg67HQ2IdgqlaK0";
                password = "7kRrA54Xd4oJoWL4yPEfDlktf";
            }
            if(facility.equalsIgnoreCase("DC1")){
                key = "HbCS66eyBAmJlL32";
                password = "BA6xM7KMMKo4N9XSEYPUGighn";
            }
        }
        //
        // See if the key and password properties are set,
        // if set use those values, otherwise default them to "XXX"
        //
        if ( key.length()==0) {
            key = "XXX"; // Replace "XXX" with clients key
        }
        if (password.length()==0) {
            password = "XXX"; // Replace "XXX" with clients password
        }
        userCredential.setKey(key);
        userCredential.setPassword(password);

        WebAuthenticationCredential parentCredential = null;
        Boolean useParentCredential=false; //Set this value to true is using a parent credential
        if(useParentCredential){

            String parentKey = System.getProperty("parentkey");
            String parentPassword = System.getProperty("parentpassword");
            //
            // See if the parentkey and parentpassword properties are set,
            // if set use those values, otherwise default them to "XXX"
            //
            if (parentKey == null) {
                parentKey = "XXX"; // Replace "XXX" with clients parent key
            }
            if (parentPassword == null) {
                parentPassword = "XXX"; // Replace "XXX" with clients parent password
            }
            parentCredential = new WebAuthenticationCredential();
            parentCredential.setKey(parentKey);
            parentCredential.setPassword(parentPassword);
        }
        WebAuthenticationDetail detail = new WebAuthenticationDetail();
       // detail.setParentCredential(parentCredential);
                detail.setUserCredential(userCredential);
        return  detail;
    }
    private static Payment addShippingChargesPayment(boolean testing,String facility){
        Payment payment = new Payment(); // Payment information
        payment.setPaymentType(PaymentType.SENDER);
        Payor payor = new Payor();
        Party responsibleParty = new Party();
        responsibleParty.setAccountNumber(getPayorAccountNumber(testing,facility));
        Address responsiblePartyAddress = new Address();
        responsiblePartyAddress.setCountryCode("US");
        responsibleParty.setAddress(responsiblePartyAddress);
        responsibleParty.setContact(new Contact());
        payor.setResponsibleParty(responsibleParty);
        payment.setPayor(payor);
        return payment;
    }

    private static String getPayorAccountNumber(boolean testing,String facility){
        //todo fill in live and test accounts for each facility
        if(testing) {
            if(facility.equalsIgnoreCase("DC6")) {
                return "612041369";
            }
            if(facility.equalsIgnoreCase("DC1")) {
                return "510087100";
            }
        }else{
            if(facility.equalsIgnoreCase("DC6")){
                return "876991233";
            }
            if(facility.equalsIgnoreCase("DC1")) {
                return "675425590";
            }
        }
        return "XXXX";
    }

    private static void writeServiceOutput(ProcessShipmentReply reply) throws Exception
    {
        try
        {
            System.out.println(reply.getTransactionDetail().getCustomerTransactionId());
            CompletedShipmentDetail csd = reply.getCompletedShipmentDetail();
            String masterTrackingNumber=printMasterTrackingNumber(csd);
            printShipmentOperationalDetails(csd.getOperationalDetail());
            printShipmentRating(csd.getShipmentRating());
            CompletedPackageDetail cpd[] = csd.getCompletedPackageDetails();
            printPackageDetails(cpd);
            saveShipmentDocumentsToFile(csd.getShipmentDocuments(), masterTrackingNumber);
            //  If Express COD shipment is requested, the COD return label is returned as an Associated Shipment.
          //  getAssociatedShipmentLabels(csd.getAssociatedShipments());
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            //
        }
    }
    private static String printMasterTrackingNumber(CompletedShipmentDetail csd){
        String trackingNumber="";
        if(null != csd.getMasterTrackingId()){
            trackingNumber = csd.getMasterTrackingId().getTrackingNumber();
            System.out.println("Master Tracking Number");
            System.out.println("  Type: "
                    + csd.getMasterTrackingId().getTrackingIdType());
            System.out.println("  Tracking Number: "
                    + trackingNumber);
        }
        return trackingNumber;
    }
    private static void saveShipmentDocumentsToFile(ShippingDocument[] shippingDocument, String trackingNumber) throws Exception{
        if(shippingDocument!= null){
            for(int i=0; i < shippingDocument.length; i++){
                ShippingDocumentPart[] sdparts = shippingDocument[i].getParts();
                for (int a=0; a < sdparts.length; a++) {
                    ShippingDocumentPart sdpart = sdparts[a];
                    String labelLocation = System.getProperty("file.label.location");
                    if (labelLocation == null) {
                        labelLocation = "c:\\temp\\";
                    }
                    String labelName = shippingDocument[i].getType().getValue();
                    String shippingDocumentLabelFileName =  new String(labelLocation + labelName + "." + trackingNumber + "_" + a + ".png");
                    File shippingDocumentLabelFile = new File(shippingDocumentLabelFileName);
                    FileOutputStream fos = new FileOutputStream( shippingDocumentLabelFile );
                    fos.write(sdpart.getImage());
                    fos.close();
                    System.out.println("\nAssociated shipment label file name " + shippingDocumentLabelFile.getAbsolutePath());
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + shippingDocumentLabelFile.getAbsolutePath());
                }
            }
        }
    }
    private static void printNotifications(Notification[] notifications) {
        System.out.println("Notifications:");
        if (notifications == null || notifications.length == 0) {
            System.out.println("  No notifications returned");
        }
        for (int i=0; i < notifications.length; i++){
            Notification n = notifications[i];
            System.out.print("  Notification no. " + i + ": ");
            if (n == null) {
                System.out.println("null");
                continue;
            } else {
                System.out.println("");
            }
            NotificationSeverityType nst = n.getSeverity();

            System.out.println("    Severity: " + (nst == null ? "null" : nst.getValue()));
            System.out.println("    Code: " + n.getCode());
            System.out.println("    Message: " + n.getMessage());
            System.out.println("    Source: " + n.getSource());
        }
    }

    private static void printMoney(Money money, String description, String space){
        if(money!=null){
            System.out.println(space + description + ": " + money.getAmount() + " " + money.getCurrency());
        }
    }
    private static void printWeight(Weight weight, String description, String space){
        if(weight!=null){
            System.out.println(space + description + ": " + weight.getValue() + " " + weight.getUnits());
        }
    }
    private static void printString(String value, String description, String space){
        if(value!=null){
            System.out.println(space + description + ": " + value);
        }
    }

    private static Money addMoney(String currency, Double value){
        Money money = new Money();
        money.setCurrency(currency);
        money.setAmount(new BigDecimal(value));
        return money;
    }

    private static Weight addPackageWeight(Double packageWeight, WeightUnits weightUnits){
        Weight weight = new Weight();
        weight.setUnits(weightUnits);
        weight.setValue(new BigDecimal(packageWeight));
        return weight;
    }

    private static Dimensions addPackageDimensions(Integer length, Integer height, Integer width, LinearUnits linearUnits){
        Dimensions dimensions = new Dimensions();
        dimensions.setLength(new NonNegativeInteger(length.toString()));
        dimensions.setHeight(new NonNegativeInteger(height.toString()));
        dimensions.setWidth(new NonNegativeInteger(width.toString()));
        dimensions.setUnits(linearUnits);
        return dimensions;
    }

    //Shipment level reply information
    private static void printShipmentOperationalDetails(ShipmentOperationalDetail shipmentOperationalDetail){
        if(shipmentOperationalDetail!=null){
            System.out.println("Routing Details");
            printString(shipmentOperationalDetail.getUrsaPrefixCode(), "URSA Prefix", "  ");
            if(shipmentOperationalDetail.getCommitDay()!=null)
                printString(shipmentOperationalDetail.getCommitDay().getValue(), "Service Commitment", "  ");
            printString(shipmentOperationalDetail.getAirportId(), "Airport Id", "  ");
            if(shipmentOperationalDetail.getDeliveryDay()!=null)
                printString(shipmentOperationalDetail.getDeliveryDay().getValue(), "Delivery Day", "  ");
            System.out.println();
        }
    }

    private static void printShipmentRating(ShipmentRating shipmentRating){
        if(shipmentRating!=null){
            System.out.println("Shipment Rate Details");
            ShipmentRateDetail[] srd = shipmentRating.getShipmentRateDetails();
            for(int j=0; j < srd.length; j++)
            {
                System.out.println("  Rate Type: " + srd[j].getRateType().getValue());
                printWeight(srd[j].getTotalBillingWeight(), "Shipment Billing Weight", "    ");
                printMoney(srd[j].getTotalBaseCharge(), "Shipment Base Charge", "    ");
                printMoney(srd[j].getTotalNetCharge(), "Shipment Net Charge", "    ");
                printMoney(srd[j].getTotalSurcharges(), "Shipment Total Surcharge", "    ");
                if (null != srd[j].getSurcharges())
                {
                    System.out.println("    Surcharge Details");
                    Surcharge[] s = srd[j].getSurcharges();
                    for(int k=0; k < s.length; k++)
                    {
                        printMoney(s[k].getAmount(),s[k].getSurchargeType().getValue(), "      ");
                    }
                }
               // printFreightDetail(srd[j].getFreightRateDetail());
                System.out.println();
            }
        }
    }

    //Package level reply information
    private static void printPackageOperationalDetails(PackageOperationalDetail packageOperationalDetail){
        if(packageOperationalDetail!=null){
            System.out.println("  Routing Details");
            printString(packageOperationalDetail.getAstraHandlingText(), "Astra", "    ");
            printString(packageOperationalDetail.getGroundServiceCode(), "Ground Service Code", "    ");
            System.out.println();
        }
    }

    private static void printPackageDetails(CompletedPackageDetail[] cpd) throws Exception{
        if(cpd!=null){
            System.out.println("Package Details");
            for (int i=0; i < cpd.length; i++) { // Package details / Rating information for each package
                String trackingNumber = cpd[i].getTrackingIds()[0].getTrackingNumber();
                printTrackingNumbers(cpd[i]);
                System.out.println();
                //
                printPackageRating(cpd[i].getPackageRating());
                //	Write label buffer to file
                ShippingDocument sd = cpd[i].getLabel();
                saveLabelToFile(sd, trackingNumber);
                printPackageOperationalDetails(cpd[i].getOperationalDetail());
                // If Ground COD shipment is requested, the COD return label is returned as in CodReturnPackageDetail.
               // printGroundCodLabel(cpd[i], trackingNumber);
                System.out.println();
            }
        }
    }
    //Saving and displaying shipping documents (labels)
    private static void saveLabelToFile(ShippingDocument shippingDocument, String trackingNumber) throws Exception {
        ShippingDocumentPart[] sdparts = shippingDocument.getParts();
        for (int a=0; a < sdparts.length; a++) {
            ShippingDocumentPart sdpart = sdparts[a];
            String labelLocation = System.getProperty("file.label.location");
            if (labelLocation == null) {
                labelLocation = "c:\\temp\\";
            }
            String shippingDocumentType = shippingDocument.getType().getValue();
            String labelFileName =  new String(labelLocation + shippingDocumentType + "." + trackingNumber + "_" + a + ".png");
            File labelFile = new File(labelFileName);
            FileOutputStream fos = new FileOutputStream( labelFile );
            fos.write(sdpart.getImage());
            fos.close();
            System.out.println("\nlabel file name " + labelFile.getAbsolutePath());
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + labelFile.getAbsolutePath());
        }
    }

    private static void printPackageRating(PackageRating packageRating){
        if(packageRating!=null){
            System.out.println("Package Rate Details");
            PackageRateDetail[] prd = packageRating.getPackageRateDetails();
            for(int j=0; j < prd.length; j++)
            {
                System.out.println("  Rate Type: " + prd[j].getRateType().getValue());
                printWeight(prd[j].getBillingWeight(), "Billing Weight", "    ");
                printMoney(prd[j].getBaseCharge(), "Base Charge", "    ");
                printMoney(prd[j].getNetCharge(), "Net Charge", "    ");
                printMoney(prd[j].getTotalSurcharges(), "Total Surcharge", "    ");
                if (null != prd[j].getSurcharges())
                {
                    System.out.println("    Surcharge Details");
                    Surcharge[] s = prd[j].getSurcharges();
                    for(int k=0; k < s.length; k++)
                    {
                        printMoney(s[k].getAmount(),s[k].getSurchargeType().getValue(), "      ");
                    }
                }
                System.out.println();
            }
        }
    }

    private static void printTrackingNumbers(CompletedPackageDetail completedPackageDetail){
        if(completedPackageDetail.getTrackingIds()!=null){
            TrackingId[] trackingId = completedPackageDetail.getTrackingIds();
            for(int i=0; i< trackingId.length; i++){
                String trackNumber = trackingId[i].getTrackingNumber();
                String trackType = trackingId[i].getTrackingIdType().getValue();
                String formId = trackingId[i].getFormId();
                printString(trackNumber, trackType + " tracking number", "  ");
                printString(formId, "Form Id", "  ");
            }
        }
    }
    private static boolean isResponseOk(NotificationSeverityType notificationSeverityType) {
        if (notificationSeverityType == null) {
            return false;
        }
        if (notificationSeverityType.equals(NotificationSeverityType.WARNING) ||
                notificationSeverityType.equals(NotificationSeverityType.NOTE)    ||
                notificationSeverityType.equals(NotificationSeverityType.SUCCESS)) {
            return true;
        }
        return false;
    }

    /**
     * Sean 12/3/2019 case 715152 Stanley B&D
     * check if sku level item is flagged for smart post returns. If so, then tag the order as a smart post return
     * return boolean if it's smart post return order
     */
    public static boolean isSmartPostReturnItem (OwdOrder order){
        Set<OwdLineItem> lineItems = order.getLineitems();
        if(lineItems!=null){
            for(OwdLineItem lineItem: lineItems){
                Map<String, String> tagMap = TagUtilities.getTagMap("inventory" ,lineItem.getOwdInventory().getInventoryId());
                if(tagMap != null) {
                    String value = tagMap.get(TagUtilities.kSPReturn);
                    if(value != null) {
                        if (value.compareTo("1") == 0) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    /**
     * Sean 12/3/2019 case 715152 Stanley B&D
     * tag the order as a smart post return
     */
    public static void setSmartPostReturnFlag (OwdOrderShipInfo info){
        TagUtilities.setOrderTagForOwdOrder(info.getOrder(), TagUtilities.kSPReturn, "1");
    }


}
