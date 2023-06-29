package com.owd.jobs.jobobjects.commercehub.invoicing;


import com.owd.core.business.order.OrderStatus;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdLineItem;
import com.owd.hibernate.generated.OwdOrder;
import org.w3c.dom.Document;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by danny on 7/13/2019.
 */
public class CommerceHubInvoiceBase {


    private InvoiceMessageBatch invoiceBatch;
    protected  DocumentBuilderFactory builderFactory =  DocumentBuilderFactory.newInstance();
    protected  DocumentBuilder builder;
    protected XPath xPath = XPathFactory.newInstance().newXPath();
    protected String partnerName="";
    protected String partnerRoleType="";
    protected String partner="";
   // private SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyyMMdd");
    private DateTimeFormatter defaultDateFormat = DateTimeFormatter.ofPattern("yyyyMMdd").withZone(ZoneId.systemDefault());
    private BigDecimal trxShippingCharges = BigDecimal.ZERO;
    private BigDecimal trxHandlingCharges = BigDecimal.ZERO;
    private BigDecimal trxTaxCharges = BigDecimal.ZERO;
    private BigDecimal trxCreditsCharges = BigDecimal.ZERO;
    private BigDecimal trxTotalUnitCharges = BigDecimal.ZERO;
    private BigDecimal discPercent = BigDecimal.ZERO;
    private int discDaysDue = 0;
    private int netDaysDue = 30;
    private BigDecimal taxPercent = BigDecimal.ZERO;
    private List<String> taxBreakoutIds = new ArrayList<>();
    private List<TaxBreakout> taxBreakouts = new ArrayList<>();
    private List<DiscountBreakout> discountBreakouts = new ArrayList<>();



    public CommerceHubInvoiceBase(String name, String role, String id){
        partnerName = name;
        partnerRoleType = role;
        partner = id;
    }



    /**
     * Load invoice data and store in invoiceBatch
     *
     * @param orderId Order ID to invoice
     * @throws Exception
     */
    public void loadInvoiceFromOrderId(int orderId) throws Exception{

        try{
            OrderStatus order = new OrderStatus("" + orderId);
            if(!order.getStatusString().equalsIgnoreCase("shipped")){
                throw new Exception("Error: Order is not shipped and cannot be invoiced.");
            }
            Map<String, String> tagmap = order.getTagMap();
            builder = builderFactory.newDocumentBuilder();

            Document document = builder.parse(new ByteArrayInputStream(tagmap.get("COMMERCEHUB_PO_XML").getBytes("UTF-8")));

            //todo check for no xml and throw error

         //Order and xml loaded fill out invoice
          ObjectFactory invoiceFactory = new ObjectFactory();
            invoiceBatch = invoiceFactory.createInvoiceMessageBatch();
            invoiceBatch.setPartnerID(loadPartnerId(invoiceFactory,document));
            invoiceBatch.getHubInvoice().add(loadHubInvoice(order,invoiceFactory,document));
            invoiceBatch.setMessageCount(invoiceBatch.hubInvoice.size()+"");








        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private InvoiceMessageBatch.HubInvoice loadHubInvoice(OrderStatus order, ObjectFactory invoiceFactory,Document doc) throws Exception{
        InvoiceMessageBatch.HubInvoice invoice = invoiceFactory.createInvoiceMessageBatchHubInvoice();
        invoice.getParticipatingParty().add(loadParticipatingParty(invoiceFactory, doc));
        invoice.setPartnerTrxID(loadPartnerTrxID(order));
        invoice.setPartnerTrxDate(loadPartnerTrxDate());
        invoice.setPoNumber(loadPoNumber(order));
        invoice.setTrxShipping(loadTrxShipping(order));
        invoice.setTrxHandling(loadTrxHandling(order));
        invoice.setTrxCredits(loadTrxCredits(order));

        invoice.getHubAction().addAll(loadHubActions(order,invoiceFactory,doc));

        invoice.setTrxTax(loadTrxTax(invoiceFactory));
        invoice.setTrxBalanceDue(loadTrxBalance());

        invoice.setTrxData(loadTrxData(invoiceFactory));







        return invoice;
    }

    protected InvoiceMessageBatch.HubInvoice.TrxData loadTrxData(ObjectFactory invoiceFactory){
        InvoiceMessageBatch.HubInvoice.TrxData trxData = invoiceFactory.createInvoiceMessageBatchHubInvoiceTrxData();


            trxData.getDiscountBreakout().add(loadDiscountBreakout(invoiceFactory));




        return trxData;
    }

    protected DiscountBreakout loadDiscountBreakout(ObjectFactory invoiceFactory){
        DiscountBreakout discountBreakout = invoiceFactory.createDiscountBreakout();
        if(discPercent.compareTo(BigDecimal.ZERO)==1) {
            discountBreakout.setDiscPercent(discPercent.toString());
        }
        if(discDaysDue>0) {
            discountBreakout.setDiscDaysDue(discDaysDue + "");
        }
        discountBreakout.setNetDaysDue(netDaysDue+"");

        return discountBreakout;
    }

    protected List<InvoiceMessageBatch.HubInvoice.HubAction> loadHubActions(OrderStatus order, ObjectFactory invoiceFactory,Document doc) throws Exception{

        List<InvoiceMessageBatch.HubInvoice.HubAction> actions = new ArrayList<>();

        OwdOrder owdOrder = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, Integer.parseInt(order.order_id));

        for(OwdLineItem item : owdOrder.getLineitems()){
            if(item.getQuantityActual()>0 && item.getCustRefnum().trim().length()>0) {
                actions.add(loadHubActionFromLine(item, invoiceFactory));
            }
        }


        return actions;
    }

    protected InvoiceMessageBatch.HubInvoice.HubAction loadHubActionFromLine(OwdLineItem item,ObjectFactory invoiceFactory){
        InvoiceMessageBatch.HubInvoice.HubAction action = invoiceFactory.createInvoiceMessageBatchHubInvoiceHubAction();
        action.setAction("v_invoice");
        action.setMerchantLineNumber(item.getCustRefnum());
        action.setTrxQty(item.getQuantityActual().toString());
        action.setTrxUnitCost(loadItemUnitCost(item));
        return action;
    }

    protected String loadItemUnitCost(OwdLineItem item){
        //todo lookup prices in table
        trxTotalUnitCharges = trxTotalUnitCharges.add(item.getPrice().multiply(new BigDecimal(item.getQuantityActual())));
        return item.getPrice().toString();
    }

    protected String loadTrxBalance(){
        BigDecimal currentTotal = BigDecimal.ZERO;
        currentTotal = currentTotal.add(trxShippingCharges);
        currentTotal = currentTotal.add(trxHandlingCharges);
        currentTotal = currentTotal.add(trxTotalUnitCharges);
        currentTotal = currentTotal.subtract(trxHandlingCharges);
        currentTotal = currentTotal.add(trxTaxCharges);

        return currentTotal.toString();
    }


    protected String loadTrxTax(ObjectFactory invoiceFactory){
        BigDecimal currentTotal = BigDecimal.ZERO;
        currentTotal = currentTotal.add(trxShippingCharges);
        currentTotal = currentTotal.add(trxHandlingCharges);
        currentTotal = currentTotal.add(trxTotalUnitCharges);
        currentTotal = currentTotal.subtract(trxHandlingCharges);

        trxTaxCharges = currentTotal.multiply(taxPercent.divide(new BigDecimal("100"),2, RoundingMode.CEILING));
        if(trxTaxCharges.compareTo(BigDecimal.ZERO)==1){
            TaxBreakout taxBreakout = invoiceFactory.createTaxBreakout();
            String id = "TX001";
            taxBreakoutIds.add(id);
            taxBreakout.setTaxType("Sales");
            taxBreakout.setCurrencyUnit("USD");
            taxBreakout.setValue(trxTaxCharges.toString());
            taxBreakouts.add(taxBreakout);
        }

        return trxTaxCharges.toString();


    }
    protected String loadTrxCredits(OrderStatus order){

        return "0";
    }

    protected  String loadTrxHandling(OrderStatus order){
        //todo lookup handling charges as needed.  All current shipping is 3rd party so build out as needed
        return "0";
    }

    protected String loadTrxShipping(OrderStatus order){
        //todo lookup shipping charges and include if not ThirdParty. All current shipping is 3rd party so build out as needed
        return "0";
    }

    protected String loadPoNumber(OrderStatus order){
        return order.po_num;
    }

    protected String loadPartnerTrxDate(){
        return defaultDateFormat.format(Instant.now());
    }


    /**
     *
     * @param order OrderStatus we are working with
     * @return default is to return OWDorderReference
     */
    protected String loadPartnerTrxID(OrderStatus order){
        return order.OWDorderReference;
    }

    /**
     * Fill out participating Party info from CommerceHub xml file.
     * ParticipationCode needs to be To:
     *
     * @param invoiceFactory
     * @param doc CommerceHubXML Document
     * @return
     */
    protected InvoiceMessageBatch.HubInvoice.ParticipatingParty loadParticipatingParty(ObjectFactory invoiceFactory, Document doc)throws Exception{
        InvoiceMessageBatch.HubInvoice.ParticipatingParty party = invoiceFactory.createInvoiceMessageBatchHubInvoiceParticipatingParty();
        String NameExp = "/hubOrder/participatingParty/@name";
        String roleExp = "/hubOrder/participatingParty/@roleType";
        String partyExp = "/hubOrder/participatingParty";
        party.setName(xPath.compile(NameExp).evaluate(doc));
        party.setRoleType(xPath.compile(roleExp).evaluate(doc));
        party.setValue(xPath.compile(partyExp).evaluate(doc));
        party.setParticipationCode("To:");

        return party;
    }





    protected InvoiceMessageBatch.PartnerID loadPartnerId(ObjectFactory invoiceFactory, Document doc){
        InvoiceMessageBatch.PartnerID partnerID = invoiceFactory.createInvoiceMessageBatchPartnerID();
        partnerID.setName(partnerName);
        partnerID.setRoleType(partnerRoleType);
        partnerID.setValue(partner);



        return partnerID;
    }

    public String getInvoiceXMLData()throws Exception{
        JAXBContext jaxbContext = JAXBContext.newInstance(InvoiceMessageBatch.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);



        ByteArrayOutputStream b = new ByteArrayOutputStream();
        jaxbMarshaller.marshal(invoiceBatch,b);

        String response = b.toString("UTF-8");

        if(taxBreakouts.size()>0){
            for(TaxBreakout breakout:taxBreakouts){
                JAXBContext context = JAXBContext.newInstance(TaxBreakout.class);
                Marshaller taxMarshaller = context.createMarshaller();
                taxMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
                taxMarshaller.setProperty(Marshaller.JAXB_FRAGMENT,true);
                ByteArrayOutputStream tb = new ByteArrayOutputStream();
                taxMarshaller.marshal(breakout,tb);
                response = response + tb.toString("UTF-8");
            }
        }
        response = response.replaceAll("\\n\\n","\n");
        System.out.println(response);


        return response;


    }

    public BigDecimal getDiscPercent() {
        return discPercent;
    }

    public void setDiscPercent(BigDecimal discPercent) {
        this.discPercent = discPercent;
    }

    public int getDiscDaysDue() {
        return discDaysDue;
    }

    public void setDiscDaysDue(int discDaysDue) {
        this.discDaysDue = discDaysDue;
    }

    public int getNetDaysDue() {
        return netDaysDue;
    }

    public void setNetDaysDue(int netDaysDue) {
        this.netDaysDue = netDaysDue;
    }

    public BigDecimal getTaxPercent() {
        return taxPercent;
    }

    public void setTaxPercent(BigDecimal taxPercent) {
        this.taxPercent = taxPercent;
    }
}
