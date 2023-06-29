
package com.owd.jobs.jobobjects.commercehub.invoicing;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.owd.jobs.jobobjects.commercehub.invoicing package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.owd.jobs.jobobjects.commercehub.invoicing
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InvoiceMessageBatch }
     * 
     */
    public InvoiceMessageBatch createInvoiceMessageBatch() {
        return new InvoiceMessageBatch();
    }

    /**
     * Create an instance of {@link InvoiceMessageBatch.HubInvoice }
     * 
     */
    public InvoiceMessageBatch.HubInvoice createInvoiceMessageBatchHubInvoice() {
        return new InvoiceMessageBatch.HubInvoice();
    }

    /**
     * Create an instance of {@link InvoiceMessageBatch.HubInvoice.InvoiceDetail }
     * 
     */
    public InvoiceMessageBatch.HubInvoice.InvoiceDetail createInvoiceMessageBatchHubInvoiceInvoiceDetail() {
        return new InvoiceMessageBatch.HubInvoice.InvoiceDetail();
    }

    /**
     * Create an instance of {@link InvoiceMessageBatch.HubInvoice.HubAction }
     * 
     */
    public InvoiceMessageBatch.HubInvoice.HubAction createInvoiceMessageBatchHubInvoiceHubAction() {
        return new InvoiceMessageBatch.HubInvoice.HubAction();
    }

    /**
     * Create an instance of {@link TaxBreakout }
     * 
     */
    public TaxBreakout createTaxBreakout() {
        return new TaxBreakout();
    }

    /**
     * Create an instance of {@link MiscChargeBreakout }
     * 
     */
    public MiscChargeBreakout createMiscChargeBreakout() {
        return new MiscChargeBreakout();
    }

    /**
     * Create an instance of {@link InvoiceMessageBatch.PartnerID }
     * 
     */
    public InvoiceMessageBatch.PartnerID createInvoiceMessageBatchPartnerID() {
        return new InvoiceMessageBatch.PartnerID();
    }

    /**
     * Create an instance of {@link HandlingBreakout }
     * 
     */
    public HandlingBreakout createHandlingBreakout() {
        return new HandlingBreakout();
    }

    /**
     * Create an instance of {@link CreditBreakout }
     * 
     */
    public CreditBreakout createCreditBreakout() {
        return new CreditBreakout();
    }

    /**
     * Create an instance of {@link DiscountBreakout }
     * 
     */
    public DiscountBreakout createDiscountBreakout() {
        return new DiscountBreakout();
    }

    /**
     * Create an instance of {@link InvoiceMessageBatch.HubInvoice.ParticipatingParty }
     * 
     */
    public InvoiceMessageBatch.HubInvoice.ParticipatingParty createInvoiceMessageBatchHubInvoiceParticipatingParty() {
        return new InvoiceMessageBatch.HubInvoice.ParticipatingParty();
    }

    /**
     * Create an instance of {@link InvoiceMessageBatch.HubInvoice.TrxData }
     * 
     */
    public InvoiceMessageBatch.HubInvoice.TrxData createInvoiceMessageBatchHubInvoiceTrxData() {
        return new InvoiceMessageBatch.HubInvoice.TrxData();
    }

    /**
     * Create an instance of {@link InvoiceMessageBatch.HubInvoice.PackageDetail }
     * 
     */
    public InvoiceMessageBatch.HubInvoice.PackageDetail createInvoiceMessageBatchHubInvoicePackageDetail() {
        return new InvoiceMessageBatch.HubInvoice.PackageDetail();
    }

    /**
     * Create an instance of {@link InvoiceMessageBatch.HubInvoice.PersonPlace }
     * 
     */
    public InvoiceMessageBatch.HubInvoice.PersonPlace createInvoiceMessageBatchHubInvoicePersonPlace() {
        return new InvoiceMessageBatch.HubInvoice.PersonPlace();
    }

    /**
     * Create an instance of {@link InvoiceMessageBatch.HubInvoice.InvoiceDetail.RemitTo }
     * 
     */
    public InvoiceMessageBatch.HubInvoice.InvoiceDetail.RemitTo createInvoiceMessageBatchHubInvoiceInvoiceDetailRemitTo() {
        return new InvoiceMessageBatch.HubInvoice.InvoiceDetail.RemitTo();
    }

    /**
     * Create an instance of {@link InvoiceMessageBatch.HubInvoice.HubAction.InvoiceDetailLink }
     * 
     */
    public InvoiceMessageBatch.HubInvoice.HubAction.InvoiceDetailLink createInvoiceMessageBatchHubInvoiceHubActionInvoiceDetailLink() {
        return new InvoiceMessageBatch.HubInvoice.HubAction.InvoiceDetailLink();
    }

    /**
     * Create an instance of {@link InvoiceMessageBatch.HubInvoice.HubAction.PackageDetailLink }
     * 
     */
    public InvoiceMessageBatch.HubInvoice.HubAction.PackageDetailLink createInvoiceMessageBatchHubInvoiceHubActionPackageDetailLink() {
        return new InvoiceMessageBatch.HubInvoice.HubAction.PackageDetailLink();
    }

}
