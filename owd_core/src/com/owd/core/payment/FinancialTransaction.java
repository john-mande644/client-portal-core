package com.owd.core.payment;


import com.owd.core.OWDUtilities;
import com.owd.core.business.Client;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.managers.ConnectionManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.engine.spi.SessionImplementor;

import java.sql.*;


public class FinancialTransaction

{
private final static Logger log =  LogManager.getLogger();

	//transaction types

	public static final int transTypeAuthOnlyRequest 	= 0;

	public static final int transTypeCaptureOutsideAuthRequest 	= 1;

	public static final int transTypeAuthCaptureRequest 	= 2;

	public static final int transTypeVoidTransactionRequest 	= 3;

	public static final int transTypeCreditRequest 	= 4;

	public static final int transTypeCheckTransactionRequest 	= 5;

    public static final int transTypeCaptureOnlyRequest = 6;

	public static final int transTypeECheckTransactionRequest 	= 7;


	//transaction form of payment

	public static final int TRANS_CC 		= 0;

	public static final int TRANS_CK 		= 1;

	public static final int TRANS_PAYPAL 	= 2;

    public static final int TRANS_ECK 		= 3;



	//transaction status

	public static final String TRANS_OK 		= "Approved";

	public static final String TRANS_DECLINE 		= "Declined";

	public static final String TRANS_ERROR	= "Error";

	public static final String TRANS_NEW	= "Unprocessed";

	//future charge status

	public static final int FUTURE_WAIT 	= 0;

	public static final int FUTURE_OK 		= 1;

	public static final int FUTURE_FAILED	= 2;

	//unsaved attributes
	public String cvvCode = "";


	//transaction attributes

	private static final 	String 	kdbTransactionPKName 		= "trans_id";

	public String 		trans_id = "0";



	private static final 	String 	kdbTransactionOrderFkey 		= "order_fkey";

	public String 		order_fkey = "0";



	private static final 	String 	kdbTransactionCustomerFkey 		= "customer_fkey";

	public String 		customer_fkey = "0";



	private static final 	String 	kdbTransactionCreatedOn 		= "created_on";

	public String 		created_on = "";



	private static final 	String 	kdbTransactionCreatedBy 		= "created_by";

	public String 		created_by = "";



	private static final 	String 	kdbTransactionModifiedBy 		= "modified_by";

	public String 		modified_by = "";



	private static final 	String 	kdbTransactionModifiedOn 		= "modified_on";

	public String 		modified_on = "";





	//transaction properties

	private static final 	String 	kdbTransactionAmount 		= "amount";

	public float 		amount = 0.00f;



	private static final 	String 	kdbTransactionTimestamp 		= "trans_time";

	public String 		trans_time = "";



	private static final 	String 	kdbTransactionStatus 		= "status";

	public String		status = "";



	private static final 	String 	kdbTransactionFOP 		= "fop";

	public String		fop = "";



	private static final 	String 	kdbTransactionType 		= "type";

	public String		type = "";



	private static final 	String 	kdbTransactionProcessed 		= "is_processed";

	public int		is_processed = 0;



	private static final 	String 	kdbTransactionVoided 		= "is_void";

	public int		is_void = 0;



	private static final 	String 	kdbTransactionLName 		= "lname";

	public String 		lname = "";



	private static final 	String 	kdbTransactionFName 		= "fname";

	public String 		fname = "";



	private static final 	String 	kdbTransactionAddressOne 		= "address_one";

	public String 		address_one = "";



	private static final 	String 	kdbTransactionAddressTwo 		= "address_two";

	public String 		address_two = "";



	private static final 	String 	kdbTransactionZip 		= "zip";

	public String 		zip = "";



	private static final 	String 	kdbTransactionCompany 		= "company";

	public String 		company = "";



	private static final 	String 	kdbTransactionCity 		= "city";

	public String 		city = "";



	private static final 	String 	kdbTransactionCountry 		= "country";

	public String 		country = "";



	private static final 	String 	kdbTransactionState 		= "state";

	public String 		state = "";



	private static final 	String 	kdbTransactionEmail 		= "email";

	public String 		email = "";



	private static final 	String 	kdbTransactionPhone 		= "phone";

	public String 		phone = "";



	private static final 	String 	kdbTransactionDescription 		= "description";

	public String 		description = "";



	private static final 	String 	kdbTransactionShipping 		= "shipping";

	public String 		shipping = "0.00";



	private static final 	String 	kdbTransactionTax 		= "tax";

	public String 		tax = "0.00";



	private static final 	String 	kdbTransactionProcessorTransID 		= "proc_trans_id";

	public String 		proc_trans_id = "";



	private static final 	String 	kdbTransactionProcessorAuthCode 		= "proc_auth_code";

	public String 		proc_auth_code = "";



	private static final 	String 	kdbTransactionProcessorAVSCode 		= "proc_avs_code";

	public String 		proc_avs_code = "";



	private static final 	String 	kdbTransactionOldTransID 		= "old_trans_id";

	public String 		old_trans_id = "";



	private static final 	String 	kdbTransactionOldAuthCode 		= "old_auth_code";

	public String 		old_auth_code = "";



//CC info

	private static final 	String 	kdbTransactionCCExpiration 		= "cc_exp";

	public String 		cc_exp = "";



	private static final 	String 	kdbTransactionCCNumber 		= "cc_number";

	public String 		cc_number = "";



//CK info

	private static final 	String 	kdbTransactionCKABANum 		= "ck_aba_num";

	public String 		ck_aba_num = "";



	private static final 	String 	kdbTransactionCKAcctNum 		= "ck_acct_num";

	public String 		ck_acct_num = "";



	private static final 	String 	kdbTransactionCKAcctType 		= "ck_acct";

	public String 		ck_acct = "";



	private static final 	String 	kdbTransactionCKBankName 		= "ck_bank";

	public String 		ck_bank = "";



	private static final 	String 	kdbTransactionCKNumber 		= "ck_number";

	public String 		ck_number = "";



	private static final 	String 	kdbTransactionErrorResponse 		= "error_response";

	public String 		error_reponse = "";




	private static final 	String 	kdbTransactionFutureFlag 		= "is_future_charge";
	public int 		is_future_charge = 0;

	private static final 	String 	kdbTransactionFutureStatus 		= "charge_status";
	public int 		charge_status = 0;

	private static final 	String 	kdbTransactionFutureDate 		= "charge_date";
	public String 		charge_date = OWDUtilities.getSQLDateTimeForToday();


	private static final 	String 	kdbTransactionProcessorCVVCode 		= "proc_cvv_code";
	public String 		proc_cvv_code = "";

    private static final 	String 	kdbTransactionFromClientID 		= "from_client_fkey";
	public int 		from_client_fkey = 0;


	private static final 	String 	kdbTransactionIntacctAcctType 		= "intacct_acct_type";
	public String 		intacct_acct_type = "ACTIVITY";

    public String trans_key = "";//Required by AuthorizeNet's AIM only

    public String vendor_id = "";	//Required by Verisign's Payflow only

    public String partner = "";	//Requeired by Verisign's Payflow only

    public String currency_code = ""; //only available for AuthorizeNet transactions


	private static final 	String 	kdbTransactionTable 	    		= "owd_trans";

	private static final 	String 	kDeleteTransactionStatement = "DELETE FROM "+kdbTransactionTable+" where "+kdbTransactionPKName+" = ?";


	private boolean needsUpdate = false;

	public float oldTransactionRefAmount = 0.00f;

    public FinancialTransaction()
    {

    }

    public String getCvvCode() {
        return cvvCode;
    }

    public void setCvvCode(String cvvCode) {
        this.cvvCode = cvvCode;
    }

    public String getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(String trans_id) {
        this.trans_id = trans_id;
    }

    public String getOrder_fkey() {
        return order_fkey;
    }

    public void setOrder_fkey(String order_fkey) {
        this.order_fkey = order_fkey;
    }

    public String getCustomer_fkey() {
        return customer_fkey;
    }

    public void setCustomer_fkey(String customer_fkey) {
        this.customer_fkey = customer_fkey;

    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public String getModified_on() {
        return modified_on;
    }

    public void setModified_on(String modified_on) {
        this.modified_on = modified_on;
    }

    public String getTrans_time() {
        return trans_time;
    }

    public void setTrans_time(String trans_time) {
        this.trans_time = trans_time;
    }

    public String getFop() {
        return fop;
    }

    public void setFop(String fop) {
        this.fop = fop;
    }

    public int getIs_processed() {
        return is_processed;
    }

    public void setIs_processed(int is_processed) {
        this.is_processed = is_processed;
    }

    public String getIntacct_acct_type() {
        return intacct_acct_type;
    }

    public void setIntacct_acct_type(String intacct_acct_type) {
        this.intacct_acct_type = intacct_acct_type;
    }

    public int getIs_void() {
        return is_void;
    }

    public void setIs_void(int is_void) {
        this.is_void = is_void;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getAddress_one() {
        return address_one;
    }

    public void setAddress_one(String address_one) {
        this.address_one = address_one;
    }

    public String getAddress_two() {
        return address_two;
    }

    public void setAddress_two(String address_two) {
        this.address_two = address_two;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getProc_trans_id() {
        return proc_trans_id;
    }

    public void setProc_trans_id(String proc_trans_id) {
        this.proc_trans_id = proc_trans_id;
    }

    public String getProc_auth_code() {
        return proc_auth_code;
    }

    public void setProc_auth_code(String proc_auth_code) {
        this.proc_auth_code = proc_auth_code;
    }

    public String getProc_avs_code() {
        return proc_avs_code;
    }

    public void setProc_avs_code(String proc_avs_code) {
        this.proc_avs_code = proc_avs_code;
    }

    public String getOld_trans_id() {
        return old_trans_id;
    }

    public void setOld_trans_id(String old_trans_id) {
        this.old_trans_id = old_trans_id;
    }

    public String getOld_auth_code() {
        return old_auth_code;
    }

    public void setOld_auth_code(String old_auth_code) {
        this.old_auth_code = old_auth_code;
    }

    public String getCc_exp() {
        return cc_exp;
    }

    public void setCc_exp(String cc_exp) {
        this.cc_exp = cc_exp;
    }

    public String getCc_number() {
        return cc_number;
    }

    public void setCc_number(String cc_number) {
        this.cc_number = cc_number;
    }

    public String getCk_aba_num() {
        return ck_aba_num;
    }

    public void setCk_aba_num(String ck_aba_num) {
        this.ck_aba_num = ck_aba_num;
    }

    public String getCk_acct_num() {
        return ck_acct_num;
    }

    public void setCk_acct_num(String ck_acct_num) {
        this.ck_acct_num = ck_acct_num;
    }

    public String getCk_acct() {
        return ck_acct;
    }

    public void setCk_acct(String ck_acct) {
        this.ck_acct = ck_acct;
    }

    public String getCk_bank() {
        return ck_bank;
    }

    public void setCk_bank(String ck_bank) {
        this.ck_bank = ck_bank;
    }

    public String getCk_number() {
        return ck_number;
    }

    public void setCk_number(String ck_number) {
        this.ck_number = ck_number;
    }

    public String getError_reponse() {
        return error_reponse;
    }

    public void setError_reponse(String error_reponse) {
        this.error_reponse = error_reponse;
    }

    public int getIs_future_charge() {
        return is_future_charge;
    }

    public void setIs_future_charge(int is_future_charge) {
        this.is_future_charge = is_future_charge;
    }

    public int getCharge_status() {
        return charge_status;
    }

    public void setCharge_status(int charge_status) {
        this.charge_status = charge_status;
    }

    public String getCharge_date() {
        return charge_date;
    }

    public void setCharge_date(String charge_date) {
        this.charge_date = charge_date;
    }

    public String getProc_cvv_code() {
        return proc_cvv_code;
    }

    public void setProc_cvv_code(String proc_cvv_code) {
        this.proc_cvv_code = proc_cvv_code;
    }

    public String getTrans_key() {
        return trans_key;
    }

    public void setTrans_key(String trans_key) {
        this.trans_key = trans_key;
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public boolean isNeedsUpdate() {
        return needsUpdate;
    }

    public void setNeedsUpdate(boolean needsUpdate) {
        this.needsUpdate = needsUpdate;
    }

    public float getOldTransactionRefAmount() {
        return oldTransactionRefAmount;
    }

    public void setOldTransactionRefAmount(float oldTransactionRefAmount) {
        this.oldTransactionRefAmount = oldTransactionRefAmount;
    }

    public FinancialTransaction( 	String atrans_id,

							String aorder_fkey,

							String acustomer_fkey,

							String acreatedOn,

							String acreatedBy,

							String amodifiedBy,

							String amodifiedOn,

							float aamount,

							String atrans_time,

							String astatus,

							String afop,

							int ais_processed,

							int ais_void,

							String atype,

							String alname,

							String afname,

							String aaddress_one,

							String aaddress_two,

							String azip,

							String acompany,

							String acity,

							String acountry,

							String astate,

							String aemail,

							String aphone,

							String adescription,

							String ashipping,

							String atax,

							String aprocessorTransactionID,

							String aprocessorAuthorizationCode,

							String aprocessorAVSCode,

							String aoldTransactionID,

							String aoldAuthorizationCode,

							String acc_exp,

							String acc_number,

							String ack_aba_num,

							String ack_acct_num,

							String ack_acct,

							String ack_bank,

							String ack_number,

							String aerror_reponse,

							int ais_future_charge,

							int acharge_status,

							String acharge_date,

							String aprocessorCVVCode)

	{



		trans_id = atrans_id;

		order_fkey = aorder_fkey;

		customer_fkey = acustomer_fkey;

		created_on = acreatedOn;

		created_by = acreatedBy;

		modified_by = amodifiedBy;

		modified_on = amodifiedOn;



		amount = aamount;

		trans_time = atrans_time;

		status = astatus;

		fop = afop;

		type = atype;

		is_processed = ais_processed;

		is_void = ais_void;

		lname = alname;

		fname = afname;

		address_one = aaddress_one;

		address_two = aaddress_two;

		zip = azip;

		company = acompany;

		city = acity;

		country = acountry;

		state = astate;

		email = aemail;

		phone = aphone;

		description = adescription;
if(description.indexOf(":")<0)
{
	//description=description+":"+Calendar.getInstance().getTime().getTime();
	}
		shipping = ashipping;

		tax = atax;

		proc_trans_id = aprocessorTransactionID;

		proc_auth_code = aprocessorAuthorizationCode;

		proc_avs_code = aprocessorAVSCode;

		proc_cvv_code = aprocessorCVVCode;

		old_trans_id = aoldTransactionID;

		old_auth_code = aoldAuthorizationCode;

		cc_exp = acc_exp;

		cc_number = acc_number;

		ck_aba_num = ack_aba_num;

		ck_acct_num = ack_acct_num;

		ck_acct = ack_acct;

		ck_bank = ack_bank;

		ck_number = ack_number;

		error_reponse = aerror_reponse;


		is_future_charge = ais_future_charge;

		charge_status = acharge_status;

		charge_date = acharge_date;

        cvvCode = aprocessorCVVCode;

	}

	public FinancialTransaction( 	String atrans_id,

							String aorder_fkey,

							String acustomer_fkey,

							String acreatedOn,

							String acreatedBy,

							String amodifiedBy,

							String amodifiedOn,

							float aamount,

							String atrans_time,

							String astatus,

							String afop,

							int ais_processed,

							int ais_void,

							String atype,

							String alname,

							String afname,

							String aaddress_one,

							String aaddress_two,

							String azip,

							String acompany,

							String acity,

							String acountry,

							String astate,

							String aemail,

							String aphone,

							String adescription,

							String ashipping,

							String atax,

							String aprocessorTransactionID,

							String aprocessorAuthorizationCode,

							String aprocessorAVSCode,

							String aoldTransactionID,

							String aoldAuthorizationCode,

							String acc_exp,

							String acc_number,

							String ack_aba_num,

							String ack_acct_num,

							String ack_acct,

							String ack_bank,

							String ack_number,

							String aerror_reponse,

							int ais_future_charge,

							int acharge_status,

							String acharge_date)

	{



		trans_id = atrans_id;

		order_fkey = aorder_fkey;

		customer_fkey = acustomer_fkey;

		created_on = acreatedOn;

		created_by = acreatedBy;

		modified_by = amodifiedBy;

		modified_on = amodifiedOn;



		amount = aamount;

		trans_time = atrans_time;

		status = astatus;

		fop = afop;

		type = atype;

		is_processed = ais_processed;

		is_void = ais_void;

		lname = alname;

		fname = afname;

		address_one = aaddress_one;

		address_two = aaddress_two;

		zip = azip;

		company = acompany;

		city = acity;

		country = acountry;

		state = astate;

		email = aemail;

		phone = aphone;

description = adescription;

if(description.indexOf(":")<0)
{
	//description=description+":"+Calendar.getInstance().getTime().getTime();
	}

		shipping = ashipping;

		tax = atax;

		proc_trans_id = aprocessorTransactionID;

		proc_auth_code = aprocessorAuthorizationCode;

		proc_avs_code = aprocessorAVSCode;


		old_trans_id = aoldTransactionID;

		old_auth_code = aoldAuthorizationCode;

		cc_exp = acc_exp;

		cc_number = acc_number;

		ck_aba_num = ack_aba_num;

		ck_acct_num = ack_acct_num;

		ck_acct = ack_acct;

		ck_bank = ack_bank;

		ck_number = ack_number;

		error_reponse = aerror_reponse;


		is_future_charge = ais_future_charge;

		charge_status = acharge_status;

		charge_date = acharge_date;

	}


    public String toString() {
        return "FinancialTransaction{" +
                "cvvCode='" + cvvCode + '\'' +
                ", trans_id='" + trans_id + '\'' +
                ", order_fkey='" + order_fkey + '\'' +
                ", customer_fkey='" + customer_fkey + '\'' +
                ", created_on='" + created_on + '\'' +
                ", created_by='" + created_by + '\'' +
                ", modified_by='" + modified_by + '\'' +
                ", modified_on='" + modified_on + '\'' +
                ", amount=" + amount +
                ", trans_time='" + trans_time + '\'' +
                ", status='" + status + '\'' +
                ", fop='" + fop + '\'' +
                ", type='" + type + '\'' +
                ", is_processed=" + is_processed +
                ", is_void=" + is_void +
                ", lname='" + lname + '\'' +
                ", fname='" + fname + '\'' +
                ", address_one='" + address_one + '\'' +
                ", address_two='" + address_two + '\'' +
                ", zip='" + zip + '\'' +
                ", company='" + company + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", description='" + description + '\'' +
                ", shipping='" + shipping + '\'' +
                ", tax='" + tax + '\'' +
                ", proc_trans_id='" + proc_trans_id + '\'' +
                ", proc_auth_code='" + proc_auth_code + '\'' +
                ", proc_avs_code='" + proc_avs_code + '\'' +
                ", old_trans_id='" + old_trans_id + '\'' +
                ", old_auth_code='" + old_auth_code + '\'' +
                ", cc_exp='" + cc_exp + '\'' +
                ", cc_number='" + cc_number + '\'' +
                ", ck_aba_num='" + ck_aba_num + '\'' +
                ", ck_acct_num='" + ck_acct_num + '\'' +
                ", ck_acct='" + ck_acct + '\'' +
                ", ck_bank='" + ck_bank + '\'' +
                ", ck_number='" + ck_number + '\'' +
                ", error_reponse='" + error_reponse + '\'' +
                ", is_future_charge=" + is_future_charge +
                ", charge_status=" + charge_status +
                ", charge_date='" + charge_date + '\'' +
                ", proc_cvv_code='" + proc_cvv_code + '\'' +
                ", trans_key='" + trans_key + '\'' +
                ", vendor_id='" + vendor_id + '\'' +
                ", partner='" + partner + '\'' +
                ", currency_code='" + currency_code + '\'' +
                ", needsUpdate=" + needsUpdate +
                ", oldTransactionRefAmount=" + oldTransactionRefAmount +
                '}';
    }

public FinancialTransaction cloneForFutureCharge(float famount,String chargeDate) throws Exception

	{



		StringBuffer str = new StringBuffer();



		FinancialTransaction ft = new FinancialTransaction(
		"0",
		order_fkey,
		customer_fkey,
		OWDUtilities.getSQLDateTimeForToday(),
		OWDUtilities.getCurrentUserName(),
		OWDUtilities.getCurrentUserName(),
		OWDUtilities.getSQLDateTimeForToday(),
		famount,
		OWDUtilities.getSQLDateTimeForToday(),
		TRANS_NEW,
		fop,
		0,
		0,
		type,
		lname,
		fname,
		address_one,
		address_two,
		zip,
		company,
		city,
		country,
		state,
		email,
		phone,
		description,
		"0",
		"0",
		"",
		"",
		"",
		"",
		"",
		cc_exp,
		cc_number,
		ck_aba_num,
		ck_acct_num,
		ck_acct,
		ck_bank,
		ck_number,
		"",
		1,
		0,
		chargeDate,
		proc_cvv_code);

	java.sql.Connection cxn = null;

	try{
		cxn = ConnectionManager.getConnection();

		ft.dbsave(cxn);

		cxn.commit();
	}catch(Exception ex)
	{
		ex.printStackTrace();
		throw ex;
	}finally{
		try{cxn.close();}catch (Exception exe){}
	}

	return ft;


	}

	public String getStatus()

	{

		return status;

	}



	public int getFOP()

	{

		return new Integer(fop).intValue();

	}



	public int getType()

	{

		return new Integer(type).intValue();

	}



	public float getAmount()

	{

		return amount;

	}



	public void setAmount(float newAmount)

	{

		amount = newAmount;

	}



	public boolean isFutureCharge()
	{
		return (is_future_charge == 1);

	}

	public boolean isProcessed()

	{

		return (is_processed == 1);

	}



	public boolean isVoid()

	{

		return (is_void == 1);

	}



	public void confirmCC(String transid, String auth, String AVS,String response)

	{

		confirmCC(transid,auth,AVS,response,"");

	}

	public void confirmCC(String transid, String auth, String AVS,String response, String cvvCode)

	{

		proc_trans_id = transid;

		trans_time = OWDUtilities.getSQLDateTimeForToday();

		proc_auth_code = auth;

		proc_avs_code = AVS;

		status = TRANS_OK;

		error_reponse = response;

		is_processed = 1;

		proc_cvv_code = cvvCode;

	}



	public void declineCC(String reason)

	{

		error_reponse = reason;

		status = TRANS_DECLINE;

		is_processed = 1;

	}



	public void reportError(String error)

	{

		if(error_reponse.indexOf("AuthorizeNet server not responding")>=0)

		{

			error_reponse = error;

			status = TRANS_ERROR;



		}else{

			//retry

			error_reponse=error;

			is_processed = 1;

			status = TRANS_ERROR;

		}



	}






	public static void main(String[] args) throws Exception
	{ }

    public static void issueCreditAgainstTransaction(int oldTransactionId, int clientId,double amount) throws Exception
    {


        try
        {
            Connection cxn =  ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();
            PreparedStatement stmt = cxn.prepareStatement("select * from owd_trans where trans_id=?");
            stmt.setInt(1,oldTransactionId);

            ResultSet rs = stmt.executeQuery();
            if(rs.next())
            {


                    //log.debug(":::trying payment");
                    FinancialTransaction ft = new FinancialTransaction("0", rs.getString(kdbTransactionOrderFkey), "0", "", "", "", "",
                            new Float(amount), "", FinancialTransaction.TRANS_NEW,
                            FinancialTransaction.TRANS_CC + "", 0, 0, "-1",
                            rs.getString(kdbTransactionLName),
                            rs.getString(kdbTransactionFName),
                            rs.getString(kdbTransactionAddressOne), "",
                            rs.getString(kdbTransactionZip), rs.getString(kdbTransactionCompany),
                            "", "", "", "", "",
                            rs.getString(kdbTransactionDescription), "0", "0", "", "", "", rs.getString(kdbTransactionProcessorTransID), "",
                            rs.getString(kdbTransactionCCExpiration), OWDUtilities.decryptData(rs.getString(kdbTransactionCCNumber)), "", "", "", "", "", "", 0, 0, OWDUtilities.getSQLDateTimeForToday());

                    ft.cvvCode = "";




                ft.creditCC(Client.getClientForID(cxn, clientId+""), false);

                ft.dbsave(cxn);

                HibUtils.commit(HibernateSession.currentSession());
                



            }   else
            {
                throw new Exception("Transaction ID "+oldTransactionId+" not found!");
            }

        }   catch(Exception ex)
        {

                HibUtils.rollback(HibernateSession.currentSession());
            throw ex;
        }   finally
        {

            HibernateSession.closeSession();

        }
    }

    public static java.util.List getTransactionsForCustomer(Connection cxn,String custNum) throws Exception

	{
		return getTransactionsForCustomer(cxn,custNum,false);
	}
	public static java.util.List getTransactionsForOrder(Connection cxn,String orderID) throws Exception

	{
		return getTransactionsForOrder(cxn,orderID,false);
	}

    public static java.util.List getTransactionsForCustomer(Connection cxn,String custNum,boolean lastSuccessOnly) throws Exception

	{

		PreparedStatement stmt = null;

		ResultSet rs =  null;

		FinancialTransaction trans = null;

		java.util.List transactions = new java.util.ArrayList();



		try

		{
if(lastSuccessOnly)
{
			stmt = cxn.prepareStatement("select t.* from owd_trans t (NOLOCK) join owd_order o (NOLOCK) join owd_customer c (NOLOCK) on c.customer_num=o.customer_num on order_id=t.order_fkey" +
                    " where c.customer_num =?  and status = 'Approved' and t.is_void=0 order by trans_time desc");
}else{
            stmt = cxn.prepareStatement("select t.* from owd_trans t (NOLOCK) join owd_order o (NOLOCK) " +
                    " join owd_customer c (NOLOCK) on c.customer_num=o.customer_num on order_id=t.order_fkey where c.customer_num=? order by trans_id desc");
}
			stmt.setString(1,custNum);



			stmt.executeQuery();



				rs = stmt.getResultSet();



				while(rs.next())

				{
if(lastSuccessOnly && transactions.size() > 0)
{
}else
{
					transactions.add(new FinancialTransaction(	rs.getString(kdbTransactionPKName),

												rs.getString(kdbTransactionOrderFkey),

												rs.getString(kdbTransactionCustomerFkey),

												rs.getString(kdbTransactionCreatedOn),

												rs.getString(kdbTransactionCreatedBy),

												rs.getString(kdbTransactionModifiedBy),

												rs.getString(kdbTransactionModifiedOn),

												rs.getFloat(kdbTransactionAmount),

												rs.getString(kdbTransactionTimestamp),

												rs.getString(kdbTransactionStatus),

												rs.getString(kdbTransactionFOP),

												rs.getInt(kdbTransactionProcessed),

												rs.getInt(kdbTransactionVoided),

												rs.getString(kdbTransactionType),

												rs.getString(kdbTransactionLName),

												rs.getString(kdbTransactionFName),

												rs.getString(kdbTransactionAddressOne),

												rs.getString(kdbTransactionAddressTwo),

												rs.getString(kdbTransactionZip),

												rs.getString(kdbTransactionCompany),

												rs.getString(kdbTransactionCity),

												rs.getString(kdbTransactionCountry),

												rs.getString(kdbTransactionState),

												rs.getString(kdbTransactionEmail),

												rs.getString(kdbTransactionPhone),

												rs.getString(kdbTransactionDescription),

												rs.getString(kdbTransactionShipping),

												rs.getString(kdbTransactionTax),

												rs.getString(kdbTransactionProcessorTransID),

												rs.getString(kdbTransactionProcessorAuthCode),

												rs.getString(kdbTransactionProcessorAVSCode),

												rs.getString(kdbTransactionOldTransID),

												rs.getString(kdbTransactionOldAuthCode),

												rs.getString(kdbTransactionCCExpiration),

												OWDUtilities.decryptData(rs.getString(kdbTransactionCCNumber)),

												rs.getString(kdbTransactionCKABANum),

												OWDUtilities.decryptData(rs.getString(kdbTransactionCKAcctNum)),

												rs.getString(kdbTransactionCKAcctType),

												rs.getString(kdbTransactionCKBankName),

												rs.getString(kdbTransactionCKNumber),

												rs.getString(kdbTransactionErrorResponse),

												rs.getInt(kdbTransactionFutureFlag),

												rs.getInt(kdbTransactionFutureStatus),

												rs.getString(kdbTransactionFutureDate),

												rs.getString(kdbTransactionProcessorCVVCode)

												));



				}

				}





		}catch(Exception ex)

	  	{

	  		throw ex;

	  	}finally

	  	{

	  		try{ rs.close(); }catch(Exception exc){}

	  		try{ stmt.close(); }catch(Exception exc){}



	  	}



	  	return transactions;





	}

	public static java.util.List getTransactionsForOrder(Connection cxn,String orderID,boolean lastSuccessOnly) throws Exception

	{

		PreparedStatement stmt = null;

		ResultSet rs =  null;

		FinancialTransaction trans = null;

		java.util.List transactions = new java.util.ArrayList();



		try

		{
if(lastSuccessOnly)
{
			stmt = cxn.prepareStatement("select * from "+kdbTransactionTable+" (NOLOCK) where "+kdbTransactionOrderFkey+" = ? and status = \'Approved\' and owd_trans.is_void=0 order by trans_time desc");
}else{
stmt = cxn.prepareStatement("select * from "+kdbTransactionTable+" (NOLOCK) where "+kdbTransactionOrderFkey+" = ? order by trans_id desc");
}
			stmt.setInt(1,new Integer(orderID).intValue());



			stmt.executeQuery();



				rs = stmt.getResultSet();



				while(rs.next())

				{
if(lastSuccessOnly && transactions.size() > 0)
{
}else
{
					transactions.add(new FinancialTransaction(	rs.getString(kdbTransactionPKName),

												rs.getString(kdbTransactionOrderFkey),

												rs.getString(kdbTransactionCustomerFkey),

												rs.getString(kdbTransactionCreatedOn),

												rs.getString(kdbTransactionCreatedBy),

												rs.getString(kdbTransactionModifiedBy),

												rs.getString(kdbTransactionModifiedOn),

												rs.getFloat(kdbTransactionAmount),

												rs.getString(kdbTransactionTimestamp),

												rs.getString(kdbTransactionStatus),

												rs.getString(kdbTransactionFOP),

												rs.getInt(kdbTransactionProcessed),

												rs.getInt(kdbTransactionVoided),

												rs.getString(kdbTransactionType),

												rs.getString(kdbTransactionLName),

												rs.getString(kdbTransactionFName),

												rs.getString(kdbTransactionAddressOne),

												rs.getString(kdbTransactionAddressTwo),

												rs.getString(kdbTransactionZip),

												rs.getString(kdbTransactionCompany),

												rs.getString(kdbTransactionCity),

												rs.getString(kdbTransactionCountry),

												rs.getString(kdbTransactionState),

												rs.getString(kdbTransactionEmail),

												rs.getString(kdbTransactionPhone),

												rs.getString(kdbTransactionDescription),

												rs.getString(kdbTransactionShipping),

												rs.getString(kdbTransactionTax),

												rs.getString(kdbTransactionProcessorTransID),

												rs.getString(kdbTransactionProcessorAuthCode),

												rs.getString(kdbTransactionProcessorAVSCode),

												rs.getString(kdbTransactionOldTransID),

												rs.getString(kdbTransactionOldAuthCode),

												rs.getString(kdbTransactionCCExpiration),

												OWDUtilities.decryptData(rs.getString(kdbTransactionCCNumber)),

												rs.getString(kdbTransactionCKABANum),

												OWDUtilities.decryptData(rs.getString(kdbTransactionCKAcctNum)),

												rs.getString(kdbTransactionCKAcctType),

												rs.getString(kdbTransactionCKBankName),

												rs.getString(kdbTransactionCKNumber),

												rs.getString(kdbTransactionErrorResponse),

												rs.getInt(kdbTransactionFutureFlag),

												rs.getInt(kdbTransactionFutureStatus),

												rs.getString(kdbTransactionFutureDate),

												rs.getString(kdbTransactionProcessorCVVCode)

												));



				}

				}





		}catch(Exception ex)

	  	{

	  		throw ex;

	  	}finally

	  	{

	  		try{ rs.close(); }catch(Exception exc){}

	  		try{ stmt.close(); }catch(Exception exc){}
	  	}



	  	return transactions;





	}

	public static FinancialTransaction getTransactionForTransactionRef(Connection cxn,String oldTransactionRef, int orderID) throws Exception

	{

		PreparedStatement stmt = null;

		ResultSet rs =  null;

		FinancialTransaction trans = null;




		try

		{

stmt = cxn.prepareStatement("select * from "+kdbTransactionTable+" where "+kdbTransactionProcessorTransID+" = ? and order_fkey = ? order by trans_id desc");

			stmt.setString(1,oldTransactionRef);
				stmt.setInt(2,orderID);


			stmt.executeQuery();



				rs = stmt.getResultSet();



				if(rs.next())

				{
					trans = new FinancialTransaction(	rs.getString(kdbTransactionPKName),

												rs.getString(kdbTransactionOrderFkey),

												rs.getString(kdbTransactionCustomerFkey),

												rs.getString(kdbTransactionCreatedOn),

												rs.getString(kdbTransactionCreatedBy),

												rs.getString(kdbTransactionModifiedBy),

												rs.getString(kdbTransactionModifiedOn),

												rs.getFloat(kdbTransactionAmount),

												rs.getString(kdbTransactionTimestamp),

												rs.getString(kdbTransactionStatus),

												rs.getString(kdbTransactionFOP),

												rs.getInt(kdbTransactionProcessed),

												rs.getInt(kdbTransactionVoided),

												rs.getString(kdbTransactionType),

												rs.getString(kdbTransactionLName),

												rs.getString(kdbTransactionFName),

												rs.getString(kdbTransactionAddressOne),

												rs.getString(kdbTransactionAddressTwo),

												rs.getString(kdbTransactionZip),

												rs.getString(kdbTransactionCompany),

												rs.getString(kdbTransactionCity),

												rs.getString(kdbTransactionCountry),

												rs.getString(kdbTransactionState),

												rs.getString(kdbTransactionEmail),

												rs.getString(kdbTransactionPhone),

												rs.getString(kdbTransactionDescription),

												rs.getString(kdbTransactionShipping),

												rs.getString(kdbTransactionTax),

												rs.getString(kdbTransactionProcessorTransID),

												rs.getString(kdbTransactionProcessorAuthCode),

												rs.getString(kdbTransactionProcessorAVSCode),

												rs.getString(kdbTransactionOldTransID),

												rs.getString(kdbTransactionOldAuthCode),

												rs.getString(kdbTransactionCCExpiration),

												OWDUtilities.decryptData(rs.getString(kdbTransactionCCNumber)),

												rs.getString(kdbTransactionCKABANum),

												OWDUtilities.decryptData(rs.getString(kdbTransactionCKAcctNum)),

												rs.getString(kdbTransactionCKAcctType),

												rs.getString(kdbTransactionCKBankName),

												rs.getString(kdbTransactionCKNumber),

												rs.getString(kdbTransactionErrorResponse),

												rs.getInt(kdbTransactionFutureFlag),

												rs.getInt(kdbTransactionFutureStatus),

												rs.getString(kdbTransactionFutureDate),

												rs.getString(kdbTransactionProcessorCVVCode)

												);





				}


           return trans;


		}catch(Exception ex)

	  	{
			ex.printStackTrace();
	  		throw ex;

	  	}finally

	  	{

	  		try{ rs.close(); }catch(Exception exc){}

	  		try{ stmt.close(); }catch(Exception exc){}



	  	}
    }


    public static FinancialTransaction dbload(Connection cxn, String id) throws Exception {


		Statement stmt = null;

		ResultSet rs =  null;

		FinancialTransaction trans = null;


        try {

			stmt = cxn.createStatement();

			String isql = "select * from "+kdbTransactionTable+" where "+kdbTransactionPKName+" = "+id;


            rs = stmt.executeQuery(isql);


            if (rs!=null) {



                if (rs.next()) {

					trans = new FinancialTransaction(	rs.getString(kdbTransactionPKName),

												rs.getString(kdbTransactionOrderFkey),

												rs.getString(kdbTransactionCustomerFkey),

												rs.getString(kdbTransactionCreatedOn),

												rs.getString(kdbTransactionCreatedBy),

												rs.getString(kdbTransactionModifiedBy),

												rs.getString(kdbTransactionModifiedOn),

												rs.getFloat(kdbTransactionAmount),

												rs.getString(kdbTransactionTimestamp),

												rs.getString(kdbTransactionStatus),

												rs.getString(kdbTransactionFOP),

												rs.getInt(kdbTransactionProcessed),

												rs.getInt(kdbTransactionVoided),

												rs.getString(kdbTransactionType),

												rs.getString(kdbTransactionLName),

												rs.getString(kdbTransactionFName),

												rs.getString(kdbTransactionAddressOne),

												rs.getString(kdbTransactionAddressTwo),

												rs.getString(kdbTransactionZip),

												rs.getString(kdbTransactionCompany),

												rs.getString(kdbTransactionCity),

												rs.getString(kdbTransactionCountry),

												rs.getString(kdbTransactionState),

												rs.getString(kdbTransactionEmail),

												rs.getString(kdbTransactionPhone),

												rs.getString(kdbTransactionDescription),

												rs.getString(kdbTransactionShipping),

												rs.getString(kdbTransactionTax),

												rs.getString(kdbTransactionProcessorTransID),

												rs.getString(kdbTransactionProcessorAuthCode),

												rs.getString(kdbTransactionProcessorAVSCode),

												rs.getString(kdbTransactionOldTransID),

												rs.getString(kdbTransactionOldAuthCode),

												rs.getString(kdbTransactionCCExpiration),

												OWDUtilities.decryptData(rs.getString(kdbTransactionCCNumber)),

												rs.getString(kdbTransactionCKABANum),

												OWDUtilities.decryptData(rs.getString(kdbTransactionCKAcctNum)),

												rs.getString(kdbTransactionCKAcctType),

												rs.getString(kdbTransactionCKBankName),

												rs.getString(kdbTransactionCKNumber),

												rs.getString(kdbTransactionErrorResponse),

												rs.getInt(kdbTransactionFutureFlag),

												rs.getInt(kdbTransactionFutureStatus),

												rs.getString(kdbTransactionFutureDate),

                            rs.getString(kdbTransactionProcessorCVVCode));


				}else{

					throw new Exception("Transaction id "+id+" not found!");

				}


				rs.close();


			}


			stmt.close();


        } catch (Exception ex) {

	  		throw ex;

        } finally {

            try {
                rs.close();
            } catch (Exception exc) {
            }

            try {
                stmt.close();
            } catch (Exception exc) {
            }


	  	}


	  	return trans;

	}



	public boolean isModified()

	{

		return needsUpdate;

	}



	public boolean isNew()

	{

		if ("0".equals(trans_id))

			return true;



		return false;

	}





public void dbdelete(Connection cxn)  throws Exception

	{



		if (isNew())

		{

			//do nothing

		}else{

			PreparedStatement stmt = null;

			try

			{

				stmt = cxn.prepareStatement(kDeleteTransactionStatement);

				stmt.setString(1, trans_id);



				int rowsUpdated = stmt.executeUpdate();



				if (rowsUpdated < 1) throw new Exception("Cannot delete transaction ID "+trans_id);



				stmt.close();



			}catch(Exception ex)

		  	{

		  		throw ex;

		  	}finally

		  	{

		  		try{ stmt.close(); }catch(Exception exc){}

		  	}



		}

	}

	public void dbdelete() throws Exception
	{
		java.sql.Connection cxn = null;

		try{
			cxn = ConnectionManager.getConnection();

			dbdelete(cxn);

			cxn.commit();
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}finally{
			try{cxn.close();}catch (Exception exe){}
		}

		}

	public void dbupdate() throws Exception
	{
		java.sql.Connection cxn = null;

		try{
			cxn = ConnectionManager.getConnection();

			dbupdate(cxn);

			cxn.commit();
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}finally{
			try{cxn.close();}catch (Exception exe){}
		}

		}

    public int getFrom_client_fkey() {
        return from_client_fkey;
    }

    public void setFrom_client_fkey(int from_client_fkey) {
        this.from_client_fkey = from_client_fkey;
    }

    public void dbupdate(Connection cxn) throws Exception

	{



		if (isNew())

		{

			dbsave(cxn);

		}else{

			PreparedStatement stmt = null;

			modified_by = OWDUtilities.getCurrentUserName();

			modified_on = OWDUtilities.getSQLDateTimeForToday();

			try

			{

				stmt = cxn.prepareStatement("update "+kdbTransactionTable+" set "

							+kdbTransactionOrderFkey+" = ?, "

							+kdbTransactionCustomerFkey+" = ?, "

							+kdbTransactionCreatedOn+" = ?, "

							+kdbTransactionCreatedBy+" = ?, "

							+kdbTransactionModifiedBy+" = ?, "

							+kdbTransactionModifiedOn+" = ?, "

							+kdbTransactionAmount+" = ?, "

							+kdbTransactionTimestamp+" = ?, "

							+kdbTransactionStatus+" = ?, "

							+kdbTransactionFOP+" = ?, "

							+kdbTransactionProcessed+" = ?, "

							+kdbTransactionVoided+" = ?, "

							+kdbTransactionType+" = ?, "

							+kdbTransactionLName+" = ?, "

							+kdbTransactionFName+" = ?, "

							+kdbTransactionAddressOne+" = ?, "

							+kdbTransactionAddressTwo+" = ?, "

							+kdbTransactionZip+" = ?, "

							+kdbTransactionCompany+" = ?, "

							+kdbTransactionCity+" = ?, "

							+kdbTransactionCountry+" = ?, "

							+kdbTransactionState+" = ?, "

							+kdbTransactionEmail+" = ?, "

							+kdbTransactionPhone+" = ?, "

							+kdbTransactionDescription+" = ?, "

							+kdbTransactionShipping+" = ?, "

							+kdbTransactionTax+" = ?, "

							+kdbTransactionProcessorTransID+" = ?, "

							+kdbTransactionProcessorAuthCode+" = ?, "

							+kdbTransactionProcessorAVSCode+" = ?, "

							+kdbTransactionOldTransID+" = ?, "

							+kdbTransactionOldAuthCode+" = ?, "

							+kdbTransactionCCExpiration+" = ?, "

							+kdbTransactionCCNumber+" = ?, "

							+kdbTransactionCKABANum+" = ?, "

							+kdbTransactionCKAcctNum+" = ?, "

							+kdbTransactionCKAcctType+" = ?, "

							+kdbTransactionCKBankName+" = ?, "

							+kdbTransactionCKNumber+" = ?, "

							+kdbTransactionErrorResponse+" = ?, "

							+kdbTransactionFutureFlag+" = ?, "

							+kdbTransactionFutureStatus+" = ?, "

							+kdbTransactionProcessorCVVCode+" = ?, "

							+kdbTransactionFutureDate+" = ? WHERE "+kdbTransactionPKName+" = "+trans_id);

							stmt.setInt(1,new Integer(order_fkey).intValue());

							stmt.setInt(2,new Integer(customer_fkey).intValue());

							stmt.setString(3,created_on);

							stmt.setString(4,created_by);

							stmt.setString(5,modified_by);

							stmt.setString(6,modified_on);

							stmt.setFloat(7,new Float(amount).floatValue());

							stmt.setString(8,trans_time);

							stmt.setString(9, OrderUtilities.limitStr(50,status));

							stmt.setInt(10,new Integer(fop).intValue());

							stmt.setInt(11,new Integer(is_processed).intValue());

							stmt.setInt(12,new Integer(is_void).intValue());

							stmt.setInt(13,new Integer(type).intValue());

							stmt.setString(14, OrderUtilities.limitStr(82,lname));

							stmt.setString(15, OrderUtilities.limitStr(82,fname));

							stmt.setString(16, OrderUtilities.limitStr(82,address_one));

							stmt.setString(17, OrderUtilities.limitStr(82,address_two));

							stmt.setString(18, OrderUtilities.limitStr(13,zip));

							stmt.setString(19, OrderUtilities.limitStr(82,company));

							stmt.setString(20, OrderUtilities.limitStr(82,city));

							stmt.setString(21, OrderUtilities.limitStr(32,country));

							stmt.setString(22, OrderUtilities.limitStr(82,state));

							stmt.setString(23, OrderUtilities.limitStr(82,email));

							stmt.setString(24, OrderUtilities.limitStr(30,phone));

							stmt.setString(25, OrderUtilities.limitStr(253,description));

							stmt.setFloat(26,new Float(shipping).floatValue());

							stmt.setFloat(27,new Float(tax).floatValue());

							stmt.setString(28, OrderUtilities.limitStr(30,proc_trans_id));

							stmt.setString(29, OrderUtilities.limitStr(30,proc_auth_code));

							stmt.setString(30, OrderUtilities.limitStr(3,proc_avs_code));

							stmt.setString(31, OrderUtilities.limitStr(30,old_trans_id));

							stmt.setString(32, OrderUtilities.limitStr(30,old_auth_code));

							stmt.setString(33, OrderUtilities.limitStr(4,cc_exp));

							stmt.setString(34,OWDUtilities.encryptData(cc_number));

							stmt.setString(35, OrderUtilities.limitStr(50,ck_aba_num));

							stmt.setString(36,OWDUtilities.encryptData(ck_acct_num));

							stmt.setString(37, OrderUtilities.limitStr(13,ck_acct));

							stmt.setString(38, OrderUtilities.limitStr(50,ck_bank));

							stmt.setString(39, OrderUtilities.limitStr(50,ck_number));

							stmt.setString(40, OrderUtilities.limitStr(253,error_reponse));

							stmt.setInt(41,new Integer(is_future_charge).intValue());

							stmt.setInt(42,new Integer(charge_status).intValue());

							stmt.setString(43,proc_cvv_code);
							stmt.setString(44,charge_date);


				int rowsUpdated = stmt.executeUpdate();



				if (rowsUpdated < 1)

					throw new Exception("Could not update transaction id "+trans_id);



			}catch(Exception ex)

		  	{

		  		throw ex;

		  	}finally

		  	{

		  		try{ stmt.close(); }catch(Exception exc){}



		  	}



		}



	}



	public void dbsave(Connection cxn) throws Exception

	{



		PreparedStatement stmt = null;
		ResultSet rs = null;

        if(created_by==null) created_by="";
        if(created_by.equals(""))
        {
        created_by = OWDUtilities.getCurrentUserName();
        }

        created_on = OWDUtilities.getSQLDateTimeForToday();

		modified_on = created_on;

		modified_by = created_by;


        //log.debug(this.toString());
		try

		{

						stmt = cxn.prepareStatement(" SET ANSI_WARNINGS OFF;insert into "+kdbTransactionTable+" ("

							+kdbTransactionOrderFkey+","

							+kdbTransactionCustomerFkey+","

							+kdbTransactionCreatedOn+","

							+kdbTransactionCreatedBy+","

							+kdbTransactionModifiedBy+","

							+kdbTransactionModifiedOn+","

							+kdbTransactionAmount+","

							+kdbTransactionTimestamp+","

							+kdbTransactionStatus+","

							+kdbTransactionFOP+","

							+kdbTransactionProcessed+","

							+kdbTransactionVoided+","

							+kdbTransactionType+","

							+kdbTransactionLName+","

							+kdbTransactionFName+","

							+kdbTransactionAddressOne+","

							+kdbTransactionAddressTwo+","

							+kdbTransactionZip+","

							+kdbTransactionCompany+","

							+kdbTransactionCity+","

							+kdbTransactionCountry+","

							+kdbTransactionState+","

							+kdbTransactionEmail+","

							+kdbTransactionPhone+","

							+kdbTransactionDescription+","

							+kdbTransactionShipping+","

							+kdbTransactionTax+","

							+kdbTransactionProcessorTransID+","

							+kdbTransactionProcessorAuthCode+","

							+kdbTransactionProcessorAVSCode+","

							+kdbTransactionOldTransID+","

							+kdbTransactionOldAuthCode+","

							+kdbTransactionCCExpiration+","

							+kdbTransactionCCNumber+","

							+kdbTransactionCKABANum+","

							+kdbTransactionCKAcctNum+","

							+kdbTransactionCKAcctType+","

							+kdbTransactionCKBankName+","

							+kdbTransactionCKNumber+","

							+kdbTransactionErrorResponse+","

							+kdbTransactionFutureFlag+","

							+kdbTransactionFutureStatus+","

							+kdbTransactionProcessorCVVCode+","

							+kdbTransactionFutureDate+","

							+kdbTransactionFromClientID+","

							+kdbTransactionIntacctAcctType+") VALUES ("

							+"?,?,?,?,?,"

							+"?,?,?,?,?,"

							+"?,?,?,?,?,"

							+"?,?,?,?,?,"

							+"?,?,?,?,?,"

							+"?,?,?,?,?,"

							+"?,?,?,?,?,"

							+"?,?,?,?,?," +
                             "?,?,?,?,?," +
                             "?"+");"
                        );

							stmt.setInt(1,new Integer(order_fkey).intValue());

							stmt.setInt(2,new Integer(customer_fkey).intValue());

							stmt.setString(3,created_on);

							stmt.setString(4, OrderUtilities.limitStr(30,created_by));

							stmt.setString(5, OrderUtilities.limitStr(30,modified_by));

							stmt.setString(6,modified_on);

							stmt.setFloat(7,new Float(amount).floatValue());

							stmt.setString(8,trans_time);

							stmt.setString(9, OrderUtilities.limitStr(50,status));

							stmt.setInt(10,new Integer(fop).intValue());

							stmt.setInt(11,new Integer(is_processed).intValue());

							stmt.setInt(12,new Integer(is_void).intValue());

							stmt.setInt(13,new Integer(type).intValue());

							stmt.setString(14, OrderUtilities.limitStr(82,lname));

							stmt.setString(15, OrderUtilities.limitStr(82,fname));

							stmt.setString(16, OrderUtilities.limitStr(82,address_one));

							stmt.setString(17, OrderUtilities.limitStr(82,address_two));

							stmt.setString(18, OrderUtilities.limitStr(13,zip));

							stmt.setString(19, OrderUtilities.limitStr(82,company));

							stmt.setString(20, OrderUtilities.limitStr(82,city));

							stmt.setString(21, OrderUtilities.limitStr(32,country));

							stmt.setString(22, OrderUtilities.limitStr(82,state));

							stmt.setString(23, OrderUtilities.limitStr(82,email));

							stmt.setString(24, OrderUtilities.limitStr(30,phone));

							stmt.setString(25, OrderUtilities.limitStr(253,description));

							stmt.setFloat(26,new Float(shipping).floatValue());

							stmt.setFloat(27,new Float(tax).floatValue());

							stmt.setString(28, OrderUtilities.limitStr(30,proc_trans_id));

							stmt.setString(29, OrderUtilities.limitStr(30,proc_auth_code));

							stmt.setString(30, OrderUtilities.limitStr(3,proc_avs_code));

							stmt.setString(31, OrderUtilities.limitStr(30,old_trans_id));

							stmt.setString(32, OrderUtilities.limitStr(30,old_auth_code));

							stmt.setString(33, OrderUtilities.limitStr(4,cc_exp));

							stmt.setString(34,OWDUtilities.encryptData(cc_number));

							stmt.setString(35, OrderUtilities.limitStr(50,ck_aba_num));

							stmt.setString(36,OWDUtilities.encryptData(ck_acct_num));

							stmt.setString(37, OrderUtilities.limitStr(13,ck_acct));

							stmt.setString(38, OrderUtilities.limitStr(50,ck_bank));

							stmt.setString(39, OrderUtilities.limitStr(50,ck_number));

							stmt.setString(40, OrderUtilities.limitStr(253,error_reponse));
							stmt.setInt(41, new Integer(is_future_charge));

            stmt.setInt(42, new Integer(charge_status));

            stmt.setString(43, proc_cvv_code);
            stmt.setString(44, charge_date);

            if (from_client_fkey == 0) {
                stmt.setNull(45, Types.INTEGER);
            } else {
                stmt.setInt(45, from_client_fkey);

            }

            stmt.setString(46, intacct_acct_type);

            int rowsUpdated = stmt.executeUpdate();



			if (rowsUpdated < 1)

				throw new Exception("Could not save transaction!");

			stmt.close();

			stmt = cxn.prepareStatement("select @@IDENTITY");

			stmt.executeQuery();
			rs = stmt.getResultSet();
			if (rs.next())
			{
				trans_id = rs.getString(1);
			}
			rs.close();
			stmt.close();

		}catch(Exception ex)

	  	{
	  		throw ex;
	  	}finally

	  	{
			try{ rs.close(); }catch(Exception exc){}
	  		try{ stmt.close(); }catch(Exception exc){}
	  	}



	}



	public void authorizeCC(Client client, boolean testing) throws Exception

	{



		PaymentProcessor pp = (PaymentProcessor)Class.forName(client.pp_proc).newInstance();

		pp.setLogin(client.pp_login, client.pp_pass);

		type = ""+transTypeAuthOnlyRequest;

		pp.process(this,testing);



	}


    public void creditCC(Client client, boolean testing) throws Exception {

		////////log.debug(":::crediting CC");
        if (client.pp_proc.indexOf("com.owd.payment") >= 0) {
            client.pp_proc = "com.owd.core." + client.pp_proc.substring(client.pp_proc.indexOf("owd.") + 4);
            //log.debug(client.pp_proc);
        }
		PaymentProcessor pp = (PaymentProcessor)Class.forName(client.pp_proc).newInstance();

		pp.setLogin(client.pp_login, client.pp_pass);

		type = ""+ transTypeCreditRequest;

		try{

			////////log.debug(":::processing");

			pp.process(this,testing);

			////////log.debug(":::processed");

        } catch (Exception ex) {

			error_reponse="RETRY"+ex.getMessage();

		}

        if (error_reponse.indexOf("RETRY") >= 0) {

			try{

				////////log.debug(":::processing");

				pp.process(this,testing);

				////////log.debug(":::processed");

            } catch (Exception ex) {

				error_reponse=ex.getMessage();

			}

		}

	}

    public void captureOutsideAuthCC(OwdClient client, boolean testing) throws Exception {

		//log.debug(":::charging CC");
        if (client.getPpProc().indexOf("com.owd.payment") >= 0) {
            client.setPpProc("com.owd.core." + client.getPpProc().substring(client.getPpProc().indexOf("owd.") + 4));
            //log.debug(client.getPpProc());
        }
		PaymentProcessor pp = (PaymentProcessor)Class.forName(client.getPpProc()).newInstance();
		//log.debug(":::charging CC::"+client.getCompanyName()+"::"+client.getPpLogin()+"::"+client.getPpPass());
		pp.setLogin(client.getPpLogin(), client.getPpPass());

		type = ""+ transTypeCaptureOutsideAuthRequest;

		try{

			//log.debug(":::processing");

			pp.process(this,testing);

			//log.debug(":::processed");

		}catch(Exception ex)

		{

			error_reponse="RETRY"+ex.getMessage();

		}

		if(error_reponse.indexOf("RETRY") >= 0)

		{

			try{

				//log.debug(":::processing");

				pp.process(this,testing);

				//log.debug(":::processed");

			}catch(Exception ex)

			{

				error_reponse=ex.getMessage();

			}

		}

	}
       public void chargeCCOwdClient(OwdClient client, boolean testing) throws Exception {

		//log.debug(":::charging CC");
        if (client.getPpProc().indexOf("com.owd.payment") >= 0) {
            client.setPpProc("com.owd.core." + client.getPpProc().substring(client.getPpProc().indexOf("owd.") + 4));
            //log.debug(client.getPpProc());
        }
		PaymentProcessor pp = (PaymentProcessor)Class.forName(client.getPpProc()).newInstance();
		//log.debug(":::charging CC::"+client.getCompanyName()+"::"+client.getPpLogin()+"::"+client.getPpPass());
		pp.setLogin(client.getPpLogin(), client.getPpPass());

		type = ""+ transTypeAuthCaptureRequest;

		try{

			//log.debug(":::processing");

			pp.process(this,testing);

			//log.debug(":::processed");

		}catch(Exception ex)

		{

			error_reponse="RETRY"+ex.getMessage();

		}

		if(error_reponse.indexOf("RETRY") >= 0)

		{

			try{

				//log.debug(":::processing");

				pp.process(this,testing);

				//log.debug(":::processed");

			}catch(Exception ex)

			{

				error_reponse=ex.getMessage();

			}

		}

	}
    public void chargeCC(Client client, boolean testing) throws Exception {

		//log.debug(":::charging CC");
        if (client.pp_proc.indexOf("com.owd.payment") >= 0) {
            client.pp_proc = "com.owd.core." + client.pp_proc.substring(client.pp_proc.indexOf("owd.") + 4);
            //log.debug(client.pp_proc);
        }
		PaymentProcessor pp = (PaymentProcessor)Class.forName(client.pp_proc).newInstance();
		//log.debug(":::charging CC::"+client.company_name+"::"+client.pp_login+"::"+client.pp_pass);
		pp.setLogin(client.pp_login, client.pp_pass);

		type = ""+ transTypeAuthCaptureRequest;

		try{

			//log.debug(":::processing");

			pp.process(this,testing);

			//log.debug(":::processed");

		}catch(Exception ex)

		{

			error_reponse="RETRY"+ex.getMessage();

		}

		if(error_reponse.indexOf("RETRY") >= 0)

		{

			try{

				//log.debug(":::processing");

				pp.process(this,testing);

				//log.debug(":::processed");

			}catch(Exception ex)

			{

				error_reponse=ex.getMessage();

			}

		}

	}


    public void chargeCC(OwdClient client, boolean testing) throws Exception {

		//log.debug(":::charging CC");
        if (client.getPpProc().indexOf("com.owd.payment") >= 0) {
            client.setPpProc("com.owd.core." + client.getPpProc().substring(client.getPpProc().indexOf("owd.") + 4));
            //log.debug(client.pp_proc);
        }
		PaymentProcessor pp = (PaymentProcessor)Class.forName(client.getPpProc()).newInstance();
		//log.debug(":::charging CC::"+client.company_name+"::"+client.pp_login+"::"+client.pp_pass);
		pp.setLogin(client.getPpLogin(), client.getPpPass());

		type = ""+ transTypeAuthCaptureRequest;

		try{

			//log.debug(":::processing");

			pp.process(this,testing);

			//log.debug(":::processed");

		}catch(Exception ex)

		{

			error_reponse="RETRY"+ex.getMessage();

		}

		if(error_reponse.indexOf("RETRY") >= 0)

		{

			try{

				//log.debug(":::processing");

				pp.process(this,testing);

				//log.debug(":::processed");

			}catch(Exception ex)

			{

				error_reponse=ex.getMessage();

			}

		}

	}


  public void chargeEcheck(Client client, boolean testing) throws Exception {

		//log.debug(":::charging Echeck");
        if (client.check_proc.indexOf("com.owd.payment") >= 0) {

            client.check_proc = "com.owd.core." + client.check_proc.substring(client.check_proc.indexOf("owd.") + 4);
            //log.debug(client.check_proc);
        }
		PaymentProcessor pp = (PaymentProcessor)Class.forName(client.check_proc).newInstance();
		//log.debug(":::charging CK::"+client.company_name+"::"+client.check_login+"::"+client.check_pass);
		pp.setLogin(client.check_login, client.check_pass);

		type = ""+ transTypeECheckTransactionRequest;
      fop = ""+FinancialTransaction.TRANS_ECK;

        try{

			//log.debug(":::processing");

			pp.process(this,testing);

			//log.debug(":::processed");

		}catch(Exception ex)

		{

			error_reponse="RETRY"+ex.getMessage();

		}

		if(error_reponse.indexOf("RETRY") >= 0)

		{

			try{

				//log.debug(":::processing");

				pp.process(this,testing);

				//log.debug(":::processed");

			}catch(Exception ex)

			{

				error_reponse=ex.getMessage();

			}

		}

	}

  public void chargeEcheck(OwdClient client, boolean testing) throws Exception {

		//log.debug(":::charging Echeck");
        if (client.getCheckProc().indexOf("com.owd.payment") >= 0) {

            client.setCheckProc("com.owd.core." + client.getCheckProc().substring(client.getCheckProc().indexOf("owd.") + 4));
            //log.debug(client.check_proc);
        }
		PaymentProcessor pp = (PaymentProcessor)Class.forName(client.getCheckProc()).newInstance();
		//log.debug(":::charging CK::"+client.company_name+"::"+client.check_login+"::"+client.check_pass);
		pp.setLogin(client.getCheckLogin(), client.getCheckPass());

		type = ""+ transTypeCheckTransactionRequest;
      fop = ""+FinancialTransaction.TRANS_ECK;

		try{

			//log.debug(":::processing");

			pp.process(this,testing);

			//log.debug(":::processed");

		}catch(Exception ex)

		{

			error_reponse="RETRY"+ex.getMessage();

		}

		if(error_reponse.indexOf("RETRY") >= 0)

		{

			try{

				//log.debug(":::processing");

				pp.process(this,testing);

				//log.debug(":::processed");

			}catch(Exception ex)

			{

				error_reponse=ex.getMessage();

			}

		}

	}


}
