package com.owd.core.xml;

public class OrderXMLDoc  {



    static final public String kOrderTag = "OWD_New_Order";
	static final public String kRootTag = kOrderTag;
	static final public String kInvoiceTag = "invoice";

	static final public String kBillInfoTag = "bill_info";
	static final public String kShipInfoTag = "ship_info";
	static final public String kClientIDTag = "client_id";
	static final public String kTestClientIDTag = "client_test_id";
	static final public String kOrderIDTag = "order_id";
	static final public String kOrderDateTag = "order_date";
	static final public String kCustomerIDTag = "cust_id";
	static final public String kPONumberTag = "po_number";
	static final public String kSalesPersonTag = "sales_person";
	static final public String kGiftFlagTag = "gift_flag";
	static final public String kGiftNoteTag = "gift_note";
	static final public String kPackingNoteTag = "packing_note";
	static final public String kClientReferenceIDTag = "client_ref_id";
	static final public String kBillFNameTag = "bill_fname";
	static final public String kBillLNameTag = "bill_lname";
	static final public String kBillingNameTag = "bill_billname";
	static final public String kBillCompanyTag = "bill_company";
	static final public String kBillAddress1Tag = "bill_add1";
	static final public String kBillAddress2Tag = "bill_add2";
	static final public String kBillCityTag = "bill_city";
	static final public String kBillStateTag = "bill_state";
	static final public String kBillZipTag = "bill_zip";
	static final public String kBillPhoneTag = "bill_phone";
	static final public String kBillFaxTag = "bill_fax";
	static final public String kBillEmailTag = "bill_email";
	static final public String kBillCountryTag = "bill_country";
	static final public String kShipFNameTag = "ship_fname";
	static final public String kShipLNameTag = "ship_lname";
	static final public String kShipCompanyTag = "ship_company";
	static final public String kShipAddress1Tag = "ship_add1";
	static final public String kShipAddress2Tag = "ship_add2";
	static final public String kShipCityTag = "ship_city";
	static final public String kShipStateTag = "ship_st";
	static final public String kShipZipTag = "ship_zip";
	static final public String kShipPhoneTag = "ship_phone";
	static final public String kShipFaxTag = "ship_fax";
	static final public String kShipEmailTag = "ship_email_address";
	static final public String kShipCountryTag = "ship_country";
	static final public String kCostSubtotalTag = "cost_items_subtotal";
	static final public String kCostTaxTag = "cost_tax";
	static final public String kCostShipTag = "cost_shipping";
	static final public String kCostTotalTag = "cost_order_total";
	static final public String kCostWrapTag = "cost_gift_wrap";
	static final public String kCCTypeTag = "cc_type";
	static final public String kCCExpMonthTag = "cc_exp_mon";
	static final public String kCCNumberTag = "cc_num";
	static final public String kCCExpYearTag = "cc_exp_year";
	static final public String kCarrierTypeTag = "ship_carrier_code";
	static final public String kCarrierTermsTag = "carr_freight_terms";
	static final public String kCarrierThirdPartyAcctTag = "third_party_refnum";
	static final public String kPostDateTag = "post_date";
	static final public String kIsVoidTag = "is_void";

		static final public String kLineItemTag = "line_item";
		static final public String kItemIDTag = "line_item_id";
		static final public String kItemOrderIDTag = "order_id";
		static final public String kItemCostTag = "item_price";
		static final public String kItemRequestCountTag = "request_count";
		static final public String kItemActualCountTag = "actual_count";
		static final public String kItemBackorderCountTag = "backordered_count";
		static final public String kItemCostTotalTag = "item_total_price";
		static final public String kItemSizeTag = "item_size";
		static final public String kItemColorTag = "item_color";
		static final public String kItemDescTag = "item_description";
		static final public String kItemClientRefTag = "item_clientref";
		static final public String kItemInventoryIDTag = "inventory_id";
		static final public String kItemCreatedOnTag = "created_on";
		static final public String kItemCreatedByTag = "created_by";
		static final public String kItemModifiedOnTag = "modified_on";
		static final public String kItemModifiedByTag = "modified_by";
		static final public String kItemPartNumTag = "part_reference";

		static final public String kPackageTag = "package";
		static final public String kPackageIDTag = "package_id";
		static final public String kPackageOrderIDTag = "order_id";
		static final public String kPackageIndexTag = "line_index";
		static final public String kPackageTrackingNoTag = "tracking_no";
		static final public String kPackagePoundsWeighTagt = "weight";
		static final public String kPackageRatedCostTag = "total_billed";
		static final public String kPackageCostGoodsTag = "cost_of_goods";
		static final public String kPackageShipDateTag = "ship_date";
		static final public String kPackageMSNTag = "msn";
		static final public String kPackageVoidTag = "is_void";
		static final public String kPackageCreatedOnTag = "created_on";
		static final public String kPackageCreatedByTag = "created_by";
		static final public String kPackageModifiedOnTag = "modified_on";
		static final public String kPackageModifiedByTag = "modified_by";
		static final public String kPackageReportedTag = "reported";

		static final public String kTransactionTag = "transaction";
		static final public String kTransactionIDTag = "trans_id";
		static final public String kTransactionOrderIDTag = "order_fkey";
		static final public String kTransactionCustomerIDTag = "cust_fkey";
		static final public String kTransactionCreatedOnTag = "created_on";
		static final public String kTransactionCreatedByTag = "created_by";
		static final public String kTransactionModifiedByTag = "modified_by";
		static final public String kTransactionModifiedOnTag = "modified_on";
		static final public String kTransactionAmountTag = "amount";
		static final public String kTransactionTimestampTag = "trans_time";
		static final public String kTransactionStatusTag = "status";
		static final public String kTransactionFOPTag = "fop";
		static final public String kTransactionProcessedTag = "is_processed";
		static final public String kTransactionVoidedTag = "is_void";
		static final public String kTransactionTypeTag = "type";



		static final public String kPaymentStatusTag = "payment_status";
		static final public String kBackorderActionTag = "backorder_action";

//constants

		static final public String kHoldOrder = "HoldOrder";
		static final public String kRejectOrder = "Reject";

		static final public String kBackOrderAll = "BACKORDER";
		static final public String kPartialShip = "PARTIALSHIP";
		static final public String kHoldBackOrder = "HOLDORDER";
		static final public String kRejectBackOrder = "NOBACKORDER";
		static final public String kIgnoreBackOrder = "IGNOREBACKORDER";


		public static final String kPaymentStatusClientManaged = "Client Managed";
		public static final String kPaymentStatusWaitingForAuthorize = "Waiting For Authorization";
		public static final String kPaymentStatusAuthorized = "Authorized";
		public static final String kPaymentStatusWaitingForPayment = "Waiting For Payment";
		public static final String kPaymentStatusPaid = "Paid";
		public static final String kPaymentStatusNeedsRefund = "Waiting For  Refund";
		public static final String kPaymentStatusNeedsReview = "Waiting For  Review";
		public static final String kPaymentStatusFutureShip = "Future Ship";

}
