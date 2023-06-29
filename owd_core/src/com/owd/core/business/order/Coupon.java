package com.owd.core.business.order;


import com.owd.core.OWDUtilities;
import com.owd.core.business.Address;
import com.owd.core.managers.ConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;


public class Coupon

{
private final static Logger log =  LogManager.getLogger();



	public static final int kMatchTypeEquals			=0;

	public static final int kMatchTypeContains		=1;

	public static final int kMatchTypeBeginsWith		=2;

	public static final int kMatchTypeEndsWith		=3;





	public static final int kCouponTypeAmount		=0;

	public static final int kCouponTypePercent		=1;

	public static final int kCouponTypeShipAmt		=2;

	public static final int kCouponTypeShipPct		=3;



	public static final String kExpiresYearValue = "expyr";

	public static final String kExpiresMonthValue = "expmn";

	public static final String kExpiresDayValue = "expdy";





	private int expYear = 2011;

	private int expMonth = 12;

	private int expDay = 31;



	//Attributes

	public static final 	String 	kdbCouponPKName 			= "coupon_id";

	public 			String 		coupon_id 					= "0";



	public static final 	String 	kdbCouponName 				= "coupon_name";

	public 			String 	coupon_name 				= "";



	public static final 	String 	kdbCouponUser 				= "coupon_user";

	public 			String 	coupon_user 					= "";



	public static final 	String 	kdbCouponType 				= "coupon_type";

	public 			int		coupon_type 					= 0;



	public static final 	String 	kdbCouponDiscAmt 			= "disc_amt";

	public 			double	disc_amt						= 0.0000;



	public static final 	String 	kdbCouponDiscPct 				= "disc_pct";

	public 			double	disc_pct						= 0.0000;



	public static final 	String 	kdbCouponShipAmt 			= "ship_amt";

	public 			double	ship_amt						= 0.0000;



	public static final 	String 	kdbCouponShipPct 				= "ship_pct";

	public 			double	ship_pct						= 0.0000;





	//Properties

	public static final 	String 	kdbCouponUsed				= "used_count";

	public 			int		used_count 					= 0;



	public static final 	String 	kdbCouponUseLimit 			= "use_limit";

	public 			int 		use_limit 						= 0;



	public static final 	String 	kdbCouponExpDate			= "exp_date";

	public 			String	exp_date						= "2011-12-31 00:00:00";



	public static final 	String 	kdbCouponCreatedDate 		= "created_date";

	public 			String	created_date					= "";



	public static final 	String 	kdbCouponCreatedBy 			= "created_by";

	public 			String	created_by					= "";



	public static final 	String 	kdbCouponModifiedDate 			= "modified_date";

	public 			String	modified_date 				= "";



	public static final 	String 	kdbCouponModifiedBy 			= "modified_by";

	public 			String	modified_by					= "";



	public static final 	String 	kdbCouponClientFkey			= "client_fkey";

	public 			int		client_fkey 					= 0;



	public static final 	String 	kdbCouponCode 				= "coupon_code";

	public 			String	coupon_code					="";



	public static final 	String 	kdbCouponMatchType 			= "code_match_type";

	public 			int		code_match_type				= 0;


	public static final 	String 	kdbCouponSKUMatch 				= "sku_match_flag";

	public 			int		sku_match_flag					=0;

	public static final 	String 	kdbCouponSKUMatchType 			= "sku_match_type";

	public 			int		sku_match_type				= 0;

	public static final 	String 	kdbCouponSKU 				= "sku_value";

	public 			String	sku_value					="";


	public static final 	String 	kdbCouponUSGood 			= "us_good";

	public 			int		us_good				= 1;


	public static final 	String 	kdbCouponCanadaGood 				= "canada_good";

	public 			int		canada_good					=1;

	public static final 	String 	kdbCouponIntlGood 			= "intl_good";

	public 			int		intl_good				= 1;

	public static final 	String 	kdbCouponMinSubtotal 				= "min_subtotal";

	public 			float	min_subtotal					=0.00f;






	public static final 	String 	kdbCouponTable 	    			= "owd_coupon";



	private static final 	String 	kDeleteCouponStatement = "DELETE FROM "+kdbCouponTable+" where "+kdbCouponPKName+" = ?";



	private 			boolean 	needsUpdate = false;



	private static final 	 String loadByCouponStatement = "select *"+

								" from owd_coupon i "+

								" where ("+

								"(coupon_code = ? and code_match_type="+kMatchTypeEquals+") OR "+

								"(coupon_code like ? and code_match_type="+kMatchTypeBeginsWith+") OR "+

								"(coupon_code like ? and code_match_type="+kMatchTypeEndsWith+") OR "+

								"(coupon_code like ? and code_match_type="+kMatchTypeContains+") "+

								") AND exp_date >= ? AND client_fkey = ? ";

	public String getID()

	{

		return (""+coupon_id);

	}



	public Coupon(String aclient_id)

	{

		try{

			client_fkey = (new Integer("0"+aclient_id).intValue());

		}catch(NumberFormatException ex){client_fkey = (int) 0;}

	}



	private Coupon(	String acoupon_id,

					String acoupon_name,

					String acoupon_user,

					String acoupon_type,

					String adisc_amt,

					String adisc_pct,

					String aship_amt,

					String aship_pct,

					String aused_count,

					String ause_limit,

					String aexp_date,

					String acreated_date,

					String acreated_by,

					String amodified_date,

					String amodified_by,

					String aclient_fkey,

					String acoupon_code,

					String acode_match_type,

					String asku_match_flag,

					String asku_match_type,

					String asku_value,
					String aus_good,
					String acanada_good,
					String aintl_good,
					String amin_subtotal


					)

	{





					coupon_id= acoupon_id;

					long ckID = new Long("0"+acoupon_id.trim()).longValue();

					coupon_name=  acoupon_name.trim();

					coupon_user=  acoupon_user.trim();

					coupon_type=  new Integer("0"+acoupon_type.trim()).intValue();

					disc_amt=  new Double("0"+adisc_amt.trim()).doubleValue();

					disc_pct=  new Double("0"+adisc_pct.trim()).doubleValue();

					ship_amt=  new Double("0"+aship_amt.trim()).doubleValue();

					ship_pct = new Double("0"+aship_pct.trim()).doubleValue();

					used_count=  new Integer("0"+aused_count.trim()).intValue();

					use_limit = new Integer("0"+ause_limit.trim()).intValue();

					exp_date = aexp_date.trim();

					parseExpiresDate(exp_date);

					created_date=  acreated_date.trim();

					created_by = acreated_by.trim();

					modified_date = amodified_date.trim();

					modified_by=  amodified_by.trim();

					client_fkey=  new Integer("0"+aclient_fkey.trim()).intValue();

					coupon_code = acoupon_code.trim();

					code_match_type = new Integer("0"+acode_match_type.trim()).intValue();

					sku_value = asku_value.trim();

					sku_match_flag = new Integer("0"+asku_match_flag.trim()).intValue();

					sku_match_type = new Integer("0"+asku_match_type.trim()).intValue();


					us_good = new Integer("0"+aus_good.trim()).intValue();

					canada_good = new Integer("0"+acanada_good.trim()).intValue();

					intl_good = new Integer("0"+aintl_good.trim()).intValue();

					min_subtotal = new Float("0"+amin_subtotal.trim()).floatValue();

	}





	public boolean isModified()

	{

		return needsUpdate;

	}



	public boolean isNew()

	{

		if ("0".equals( coupon_id))

			return true;



		return false;

	}



	public int getExpiresYear()

	{

		return expYear;

	}

	public int getExpiresMonth()

	{

		return expMonth;

	}

	public int getExpiresDay()

	{

		return expDay;

	}



	public void setExpiresDate(int day, int mon, int year)

	{

		expYear = year;

		expMonth = mon;

		expDay = day;



		exp_date = year+"-"+expMonth+"-"+expDay+" 00:00:00";





	}



	public void parseExpiresDate(String sqlDate)

	{

		expYear = new Integer(sqlDate.substring(0,sqlDate.indexOf("-"))).intValue();

		if(expYear < 2001)

		{

			expYear = 2011;

			expMonth = 12;

			expDay = 31;

		}else

		{

			sqlDate = sqlDate.substring(sqlDate.indexOf("-")+1);

			expMonth = new Integer(sqlDate.substring(0,sqlDate.indexOf("-"))).intValue();

			sqlDate = sqlDate.substring(sqlDate.indexOf("-")+1);

			expDay = new Integer(sqlDate.substring(0,sqlDate.indexOf(" "))).intValue();

		}

	}



	public String getDateAsSQLString()

	{

		return expYear+"-"+expMonth+"-"+expDay+" 00:00:00";

	}





	public void dbsave(Connection cxn) throws Exception

	{



		PreparedStatement stmt = null;

		ResultSet rs = null;

		created_by = OWDUtilities.getCurrentUserName();

		created_date = OWDUtilities.getSQLDateTimeForToday();

		modified_date = created_date;

		modified_by = created_by;



		validate();



		try

		{

			stmt = cxn.prepareStatement( "insert into "+kdbCouponTable+"("

						+kdbCouponName+","

						+kdbCouponUser+","

						+kdbCouponType+","

						+kdbCouponDiscAmt+","

						+kdbCouponDiscPct+","

						+kdbCouponShipAmt+","

						+kdbCouponShipPct+","

						+kdbCouponUsed+","

						+kdbCouponUseLimit+","

						+kdbCouponExpDate+","

						+kdbCouponCreatedDate+","

						+kdbCouponCreatedBy+","

						+kdbCouponModifiedDate+","

						+kdbCouponModifiedBy+","

						+kdbCouponClientFkey+","

						+kdbCouponSKUMatch+","

						+kdbCouponSKUMatchType+","

						+kdbCouponSKU+","

						+kdbCouponCode+","

						+kdbCouponMatchType+","

						+kdbCouponUSGood+","

						+kdbCouponCanadaGood+","

						+kdbCouponIntlGood+","

						+kdbCouponMinSubtotal+") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");



						stmt.setString(1,coupon_name);

						stmt.setString(2,coupon_user);

						stmt.setInt(3,coupon_type);

						stmt.setDouble(4,disc_amt);

						stmt.setDouble(5,disc_pct);

						stmt.setDouble(6,ship_amt);

						stmt.setDouble(7,ship_pct);

						stmt.setInt(8,used_count);

						stmt.setInt(9,use_limit);

						stmt.setString(10,getDateAsSQLString());

						stmt.setString(11,created_date);

						stmt.setString(12,created_by);

						stmt.setString(13,modified_date);

						stmt.setString(14,modified_by);

						stmt.setInt(15,client_fkey);


						stmt.setInt(16,sku_match_flag);

						stmt.setInt(17,sku_match_type);

						stmt.setString(18,sku_value);



						stmt.setString(19,coupon_code);

						stmt.setInt(20,code_match_type);


						stmt.setInt(21,us_good);

						stmt.setInt(22,canada_good);

						stmt.setInt(23,intl_good);

						stmt.setFloat(24,min_subtotal);



			int rowsUpdated = stmt.executeUpdate();



			if (rowsUpdated < 1)

				throw new Exception("Could not save coupon!");



			stmt.close();



			stmt = cxn.prepareStatement("select @@IDENTITY");



			stmt.executeQuery();

     			rs = stmt.getResultSet();

		      	if (rs.next())

		      	{

				coupon_id = rs.getString(1);

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



	static public Coupon getCouponForID(Connection cxn, String couponID, String clientID) throws Exception

	{

		return dbload(cxn, couponID, clientID);

	}



	static public Coupon getCouponForID(String couponID, String clientID) throws Exception

	{

		Connection cxn = null;



		try{

			cxn = ConnectionManager.getConnection();

			return getCouponForID(cxn,couponID, clientID);

		}catch(Exception ex)

		{

			throw ex;

		}finally

		{

			try{cxn.close();}catch(Exception e){}

		}

	}



	public static Coupon dbloadByCoupon( String coupon, String clientID) throws Exception

	{

		Connection cxn = null;



		try{

			cxn = ConnectionManager.getConnection();

			return dbloadByCoupon(cxn,coupon,clientID);

		}catch(Exception ex)

		{

			throw ex;

		}finally

		{

			try{cxn.close();}catch(Exception e){}

		}

	}





	public static Coupon dbloadByCoupon(Connection cxn, String coupon, String clientID) throws Exception

	{



		PreparedStatement stmt = null;

		Coupon pack = null;



		if(coupon == null)

			throw new Exception("Coupon is unavailable or expired. Please check the entered code or contact customer support.");







		coupon = coupon.replace('%',' ');

		coupon = coupon.replace('\'',' ');

		coupon = coupon.replace('\"',' ');



		coupon = coupon.trim();



		if(coupon.length() < 1)

			throw new Exception("Coupon is unavailable or expired. Please check the entered code or contact customer support.");



		try

		{

			stmt = cxn.prepareStatement(loadByCouponStatement);

			stmt.setString(1,coupon);

			stmt.setString(2,"%"+coupon);

			stmt.setString(3,coupon+"%");

			stmt.setString(4,"%"+coupon+"%");

			stmt.setString(5,OWDUtilities.getSQLDateTimeForToday());

			stmt.setInt(6,new Integer(clientID).intValue());

			stmt.executeQuery();

     			 ResultSet rs = stmt.getResultSet();

		      if (rs.next()) {

					pack = new Coupon(			rs.getString(kdbCouponPKName),

												rs.getString(kdbCouponName),

												rs.getString(kdbCouponUser),

												rs.getString(kdbCouponType),

												rs.getString(kdbCouponDiscAmt),

												rs.getString(kdbCouponDiscPct),

												rs.getString(kdbCouponShipAmt),

												rs.getString(kdbCouponShipPct),

												rs.getString(kdbCouponUsed),

												rs.getString(kdbCouponUseLimit),

												rs.getString(kdbCouponExpDate),

												rs.getString(kdbCouponCreatedDate),

												rs.getString(kdbCouponCreatedBy),

												rs.getString(kdbCouponModifiedDate),

												rs.getString(kdbCouponModifiedBy),

												rs.getString(kdbCouponClientFkey),

												rs.getString(kdbCouponCode),

												rs.getString(kdbCouponMatchType),

												rs.getString(kdbCouponSKUMatch),

												rs.getString(kdbCouponSKUMatchType),

												rs.getString(kdbCouponSKU),

												rs.getString(kdbCouponUSGood),

												rs.getString(kdbCouponCanadaGood),

												rs.getString(kdbCouponIntlGood),

												rs.getString(kdbCouponMinSubtotal)

												);

		      } else {

		        throw new Exception("Coupon is unavailable or expired. Please check the entered code or contact customer support.");

		       }





		}catch(Exception ex)

	  	{

	  		throw ex;

	  	}finally

	  	{

	  		try{ stmt.close(); }catch(Exception exc){}



	  	}



	  	return pack;

	}





	public boolean isExpired()

	{

		boolean expired = false;







		return expired;

	}


/*	static public float getOrderDiscount(float subtotal, float shippingTotal, String code, String clientID) throws Exception

	{
		return Coupon.getOrderDiscount(subtotal, shippingTotal, code, clientID,null, null);

	}

		static public float getOrderDiscount(float subtotal, float shippingTotal, String code, String clientID,java.util.Vector skuVector) throws Exception

	{
				return Coupon.getOrderDiscount(subtotal, shippingTotal, code, clientID,skuVector, null);


}*/


    static public void main(String[] args) throws Exception
    {

        Address shipAddress = new Address();
        shipAddress.address_one = "123 Main";
        shipAddress.city = "Vancouver";
        shipAddress.state="BC";
        shipAddress.zip = "123 VNH";
        shipAddress.country="Canada";

        log.debug("Discount="+Coupon.getOrderDiscount(60.00f, 5.75f, "50Canada5", "117",new Vector(),  shipAddress));

    }
	static public float getOrderDiscount(float subtotal, float shippingTotal, String code, String clientID,java.util.Vector skuVector, Address shipAddress) throws Exception

	{

		double discount = 0.0;

		Coupon coupon = null;

		try

		{

			coupon = dbloadByCoupon(code,clientID);

		}catch(Exception ex)

		{

			throw new Exception("Coupon is unavailable or expired. Please check the entered code or contact customer support.");

		}



		if(coupon == null)

			throw new Exception("Coupon is unavailable or expired. Please check the entered code or contact customer support.");

		if(coupon.sku_match_flag==1)
		{

			if(skuVector == null)
				throw new Exception("Coupon is unavailable or the order does not qualify. Please check the entered code or contact customer support.");

			boolean SkuOK = false;

			switch(coupon.sku_match_type)

			{
				case (kMatchTypeEquals):

					for(int i=0;i<skuVector.size();i++)
					{
						if(coupon.sku_value.equals(((LineItem)skuVector.elementAt(i)).client_part_no))
							SkuOK=true;
					}

					break;

				case kMatchTypeContains:

					for(int i=0;i<skuVector.size();i++)
					{
						if(((LineItem)skuVector.elementAt(i)).client_part_no.indexOf(coupon.sku_value)>=0)
							SkuOK=true;
					}


					break;

				case kMatchTypeBeginsWith:

					for(int i=0;i<skuVector.size();i++)
					{
						if(((LineItem)skuVector.elementAt(i)).client_part_no.startsWith(coupon.sku_value))
							SkuOK=true;
					}

					break;

				case kMatchTypeEndsWith:

					for(int i=0;i<skuVector.size();i++)
					{
						if(((LineItem)skuVector.elementAt(i)).client_part_no.endsWith(coupon.sku_value))
							SkuOK=true;
					}

					break;

			}

			if(SkuOK==false)
				throw new Exception("Coupon is unavailable or the order does not qualify. Please check the entered code or contact customer support.");


		}

		if(subtotal<coupon.min_subtotal)
			{
				throw new Exception("Coupon is unavailable or the order does not qualify. Please check the entered code or contact customer support.");
			}


			if(!(1==coupon.us_good) && shipAddress.isUS())
			{
				throw new Exception("Coupon is unavailable or the order does not qualify. Please check the entered code or contact customer support.");
			}

			if(!(1==coupon.canada_good) && shipAddress.isCanada())
			{
				throw new Exception("Coupon is unavailable or the order does not qualify. Please check the entered code or contact customer support.");
			}

			if(!(1==coupon.intl_good) && shipAddress.isInternational() && (!(shipAddress.isCanada())))
			{
				throw new Exception("Coupon is unavailable or the order does not qualify. Please check the entered code or contact customer support.");
			}


		switch(coupon.coupon_type)

		{



			case (kCouponTypeAmount):

				if(coupon.disc_amt >= 0.00)

				{

					discount = (subtotal>coupon.disc_amt?coupon.disc_amt:subtotal);

				}

				break;

			case kCouponTypePercent:

				if(coupon.disc_pct >=0.0000 && coupon.disc_pct <= 1.0000)

				{

					double testDiscount = subtotal*coupon.disc_pct;

					discount = (subtotal>testDiscount?testDiscount:subtotal);

				}

				break;

			case kCouponTypeShipAmt:

				if(coupon.ship_amt >= 0.00)

				{

					discount = (shippingTotal>coupon.ship_amt?coupon.ship_amt:shippingTotal);

				}

				break;

			case kCouponTypeShipPct:

				if(coupon.ship_pct >=0.0000 && coupon.ship_pct <= 1.0000)

				{

					double testDiscount = shippingTotal*coupon.ship_pct;

					discount = (shippingTotal>testDiscount?testDiscount:shippingTotal);

				}

				break;

		}



		return Math.abs(OWDUtilities.roundFloat((float)discount,2))*-1;



	}



	static public void useCoupon(Connection cxn, String code, String clientID) throws Exception

	{

		Coupon coupon = null;

		try

		{

			coupon = dbloadByCoupon(cxn,code,clientID);

		}catch(Exception ex)

		{

			throw new Exception("Coupon is unavailable or expired. Please check the entered code or contact customer support.");

		}



		if(coupon == null)

			throw new Exception("Coupon is unavailable or expired. Please check the entered code or contact customer support.");



		if(coupon.use_limit > 0)

		{

			if(coupon.use_limit <= coupon.used_count)

			{

				throw new Exception("This coupon is no longer available. Please check the entered code or contact customer support.");

			}

		}



		coupon.used_count++;

		coupon.dbupdate(cxn);

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

				stmt = cxn.prepareStatement(kDeleteCouponStatement);

				stmt.setString(1, coupon_id);



				int rowsUpdated = stmt.executeUpdate();



				if (rowsUpdated < 1) throw new Exception("Cannot delete coupon ID "+coupon_id);



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



	public void dbupdate(Connection cxn) throws Exception

	{



		if (isNew())

		{

			dbsave(cxn);

		}else{

			PreparedStatement stmt = null;

			modified_by = OWDUtilities.getCurrentUserName();

			modified_date = OWDUtilities.getSQLDateTimeForToday();



			validate();





			try

			{



				String isql = "update "+kdbCouponTable

						+" set "+kdbCouponName+"=?,"

						+kdbCouponUser+"=?,"

						+kdbCouponType+"=?,"

						+kdbCouponDiscAmt+"=?,"

						+kdbCouponDiscPct+"=?,"

						+kdbCouponShipAmt+"=?,"

						+kdbCouponShipPct+"=?,"

						+kdbCouponUsed+"=?,"

						+kdbCouponUseLimit+"=?,"

						+kdbCouponExpDate+"=?,"

						+kdbCouponCreatedDate+"=?,"

						+kdbCouponCreatedBy+"=?,"

						+kdbCouponModifiedDate+"=?,"

						+kdbCouponModifiedBy+"=?,"

						+kdbCouponClientFkey+"=?,"

						+kdbCouponSKUMatch+"=?,"

						+kdbCouponSKUMatchType+"=?,"

						+kdbCouponSKU+"=?,"

						+kdbCouponCode+"=?,"

						+kdbCouponMatchType+"=?,"

						+kdbCouponUSGood+"=?,"

						+kdbCouponCanadaGood+"=?,"

						+kdbCouponIntlGood+"=?,"

						+kdbCouponMinSubtotal+"=? WHERE "+kdbCouponPKName+" = "+coupon_id;



					stmt = cxn.prepareStatement(isql);



						stmt.setString(1,coupon_name);

						stmt.setString(2,coupon_user);

						stmt.setInt(3,coupon_type);

						stmt.setDouble(4,disc_amt);

						stmt.setDouble(5,disc_pct);

						stmt.setDouble(6,ship_amt);

						stmt.setDouble(7,ship_pct);

						stmt.setInt(8,used_count);

						stmt.setInt(9,use_limit);

						stmt.setString(10,getDateAsSQLString());

						stmt.setString(11,created_date);

						stmt.setString(12,created_by);

						stmt.setString(13,modified_date);

						stmt.setString(14,modified_by);

						stmt.setInt(15,client_fkey);

						stmt.setInt(16,sku_match_flag);

						stmt.setInt(17,sku_match_type);
						stmt.setString(18,sku_value);

						stmt.setString(19,coupon_code);

						stmt.setInt(20,code_match_type);


						stmt.setInt(21,us_good);
						stmt.setInt(22,canada_good);

						stmt.setInt(23,intl_good);

						stmt.setFloat(24,min_subtotal);




				int rowsUpdated = stmt.executeUpdate();



				if (rowsUpdated < 1)

					throw new Exception("Could not update coupon id "+coupon_id);



			}catch(Exception ex)

		  	{

		  		throw ex;

		  	}finally

		  	{

		  		try{ stmt.close(); }catch(Exception exc){}



		  	}



		}



	}



	private void validate() throws Exception

	{

		if(coupon_code == null) throw new Exception("Couldn't save coupon because coupon text was empty.");

		if(coupon_code.length() < 1) throw new Exception("Couldn't save coupon because coupon text was empty.");

		if(coupon_code.indexOf("%") >= 0) throw new Exception("Couldn't save coupon because a banned character was included.\nCoupons cannot include the percent sign, single quote, or double quote characters.");

		if(coupon_code.indexOf("'") >= 0) throw new Exception("Couldn't save coupon because a banned character was included.\nCoupons cannot include the percent sign, single quote, or double quote characters.");

		if(coupon_code.indexOf("\"") >= 0) throw new Exception("Couldn't save coupon because a banned character was included.\nCoupons cannot include the percent sign, single quote, or double quote characters.");


		if(sku_match_flag==1)
		{
			if(sku_value.length()<1)
				throw new Exception("Couldn't save coupon because the SKU entry was blank and SKU matching was selected.");


			if(sku_value.indexOf("%") >= 0) throw new Exception("Couldn't save coupon because a banned character was included in the SKU entry.\nSKUs cannot include the percent sign, single quote, or double quote characters.");

			if(sku_value.indexOf("'") >= 0) throw new Exception("Couldn't save coupon because a banned character was included in the SKU entry.\nSKUs cannot include the percent sign, single quote, or double quote characters.");

			if(sku_value.indexOf("\"") >= 0) throw new Exception("Couldn't save coupon because a banned character was included in the SKU entry.\nSKUs cannot include the percent sign, single quote, or double quote characters.");


		}else
		{
			sku_match_type=0;
			sku_value="";
		}

		if(disc_amt < 0) throw new Exception("Couldn't save coupon because discount amount was less than zero.");

		if(ship_amt < 0) throw new Exception("Couldn't save coupon because shipping discount amount was less than zero.");

		if(disc_pct < 0 || disc_pct > 1) throw new Exception("Couldn't save coupon because discount percentage value was greater than 1 or less than zero.");

		if(ship_pct < 0 || ship_pct > 1) throw new Exception("Couldn't save coupon because shipping discount percentage value was greater than 1 or less than zero.");



		if(use_limit < 0) throw new Exception("Couldn't save coupon because maximum uses was set to less than zero.");
		Coupon testCoupon = null;
		try
		{
			testCoupon = dbloadByCoupon(coupon_code,""+client_fkey);
		}catch(Exception ex)
		{
			testCoupon = null;
		}
		if(testCoupon != null)
		{
			if(!(testCoupon.coupon_id.equals(coupon_id)))
			{
				throw new Exception("This coupon's definition (the coupon code) overlaps with another unexpired coupon entry. Please change the definition to be unique before saving.");
			}
		}

	}



	private static Coupon dbload(Connection cxn, String id, String clientID) throws Exception

	{



		Statement stmt = null;

		ResultSet rs =  null;

		Coupon item = null;



		try

		{

			stmt = cxn.createStatement();

			String isql = "select * from "+kdbCouponTable+" where client_fkey = "+clientID+" AND "+kdbCouponPKName+" = "+id;



			boolean hasResultSet = stmt.execute(isql);



				rs = stmt.getResultSet();



				if(rs.next())

				{

					item = new Coupon(			rs.getString(kdbCouponPKName),

												rs.getString(kdbCouponName),

												rs.getString(kdbCouponUser),

												rs.getString(kdbCouponType),

												rs.getString(kdbCouponDiscAmt),

												rs.getString(kdbCouponDiscPct),

												rs.getString(kdbCouponShipAmt),

												rs.getString(kdbCouponShipPct),

												rs.getString(kdbCouponUsed),

												rs.getString(kdbCouponUseLimit),

												rs.getString(kdbCouponExpDate),

												rs.getString(kdbCouponCreatedDate),

												rs.getString(kdbCouponCreatedBy),

												rs.getString(kdbCouponModifiedDate),

												rs.getString(kdbCouponModifiedBy),

												rs.getString(kdbCouponClientFkey),

												rs.getString(kdbCouponCode),

												rs.getString(kdbCouponMatchType),

												rs.getString(kdbCouponSKUMatch),

												rs.getString(kdbCouponSKUMatchType),

												rs.getString(kdbCouponSKU),

												rs.getString(kdbCouponUSGood),

												rs.getString(kdbCouponCanadaGood),

												rs.getString(kdbCouponIntlGood),

												rs.getString(kdbCouponMinSubtotal)

												);





				}else{

					throw new Exception("Coupon id "+id+" not found!");

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



	  	return item;

	}





}
