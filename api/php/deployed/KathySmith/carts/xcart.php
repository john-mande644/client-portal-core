<?php

require_once './objects/order.php';
require_once './objects/owd_api.php';
require_once './objects/billing.php';
require_once './objects/shipping.php';
require_once './objects/lineitem.php';

class cart {

	private $api;
	private $sql_tbl=array(
			"categories" => "xcart_categories",
			"categories_subcount" => "xcart_categories_subcount",
			"categories_lng" => "xcart_categories_lng",
			"cc_gestpay_data" => "xcart_cc_gestpay_data",
			"cc_pp3_data" => "xcart_cc_pp3_data",
			"ccprocessors" => "xcart_ccprocessors",
			"chprocessors" => "xcart_chprocessors",
			"config" => "xcart_config",
			"contact_fields" => "xcart_contact_fields",
			"contact_field_values" => "xcart_contact_field_values",
			"counters" => "xcart_counters",
			"counties" => "xcart_counties",
			"countries" => "xcart_countries",
			"customers" => "xcart_customers",
			"delivery" => "xcart_delivery",
			"discount_coupons" => "xcart_discount_coupons",
			"discounts" => "xcart_discounts",
			"download_keys" => "xcart_download_keys",
			"extra_fields" => "xcart_extra_fields",
			"extra_field_values" => "xcart_extra_field_values",
			"featured_products" => "xcart_featured_products",
			"fedex_rates" => "xcart_fedex_rates",
			"fedex_zips" => "xcart_fedex_zips",
			"giftcerts" => "xcart_giftcerts",
			"icons" => "xcart_icons",
			"images" => "xcart_images",
			"languages" => "xcart_languages",
			"languages_alt" => "xcart_languages_alt",
			"login_history" => "xcart_login_history",
		    "manufacturers" => "xcart_manufacturers",
			"modules" => "xcart_modules",
			"newsletter" => "xcart_newsletter",
			"newslist_subscription" => "xcart_newslist_subscription",
			"newslists" => "xcart_newslists",
			"old_passwords" => "xcart_old_passwords",
			"order_details" => "xcart_order_details",
		    "order_extras" => "xcart_order_extras",
			"orders" => "xcart_orders",
			"pages" => "xcart_pages",
			"payment_methods" => "xcart_payment_methods",
			"php_sessions" => "xcart_php_sessions",
			"pricing" => "xcart_pricing",
			"product_bookmarks" => "xcart_product_bookmarks",
			"product_links" => "xcart_product_links",
			"product_reviews" => "xcart_product_reviews",
			"product_taxes" => "xcart_product_taxes",
			"product_votes" => "xcart_product_votes",
			"products" => "xcart_products",
			"products_categories" => "xcart_products_categories",
			"products_lng" => "xcart_products_lng",
			"referers" => "xcart_referers",
			"register_fields" => "xcart_register_fields",
		    "register_field_values" => "xcart_register_field_values",
			"sessions_data" => "xcart_sessions_data",
			"shipping" => "xcart_shipping",
			"shipping_options" => "xcart_shipping_options",
			"shipping_rates" => "xcart_shipping_rates",
			"states" => "xcart_states",
		    "stats_adaptive" => "xcart_stats_adaptive",
			"stats_cart_funnel" => "xcart_stats_cart_funnel",
			"stats_customers_products" => "xcart_stats_customers_products",
			"stats_pages" => "xcart_stats_pages",
			"stats_pages_paths" => "xcart_stats_pages_paths",
			"stats_pages_views" => "xcart_stats_pages_views",
		    "stats_search" => "xcart_stats_search",
			"stats_shop" => "xcart_stats_shop",
			"subscription_customers" => "xcart_subscription_customers",
			"subscriptions" => "xcart_subscriptions",
			"tax_rates" => "xcart_tax_rates",
			"taxes" => "xcart_taxes",
			"thumbnails" => "xcart_thumbnails",
			"wishlist" => "xcart_wishlist",
			"users_online" => "xcart_users_online",
			"zone_element" => "xcart_zone_element",
			"zones" => "xcart_zones"
		);

		private $ship_type = array(
			0 => "Picked Up",
			1 => "UPS.GND",
			2 => "UPS.2DA",
			8 => "UPS.WEXP",
            23=> "UPS.3DA",
			18 => "UPS.2DA",
			20=> "UPS.NDS",
			71=> "UPS.NDS",
			66=> "UPS.NDS",
			22=> "UPS.NDS",
			110 => "UPS.STDCAMX",
			52=> "OWD.1ST.PRIORITY",
			53=> "OWD.1ST.LETTER",
			56=> "OWD.4TH.BOOK",
			50=> "POS.EXP",
			100=> "OWD_USPS_I_PRIORITY"
		);

	function __construct() {
		$sql_host = 'localhost';
		$sql_user = 'ks_store';
		$sql_db = 'ks_xcart';
		$sql_password = 'hidden';

		mysql_connect ( $sql_host, $sql_user, $sql_password );
		mysql_select_db ( $sql_db );
	}

	public function process_orders($id, $code, $testing = false, $redirect = null) {
		echo "[START]<br>\n";
		$query = "SELECT * FROM " . $this->sql_tbl ['orders'] . " WHERE status='p' and orderid > 5684 LIMIT 1000";
		$rs1 = mysql_query ( $query );
		$c = 0;
		while ( $row = @mysql_fetch_assoc ( $rs1 ) ) {
		//	echo "new row shipid=".$row['shippingid']."<br>\n";
			if($row['shippingid']){
				$ship_type = $this->ship_type[$row['shippingid']];
			}else{
				echo "[DOWNLOAD ONLY!]<br>\n";
					$status_query = "UPDATE " . $this->sql_tbl['orders'] . " SET status='C' WHERE orderid = " . $row ['orderid'] . " LIMIT 1";
						mysql_query ( $status_query );
							continue;
					}
				
			

			$this->api = new owd_api ( $id, $code, $testing, $redirect );
			$query = "SELECT " . $this->sql_tbl ['order_details'] .
			".*, " . $this->sql_tbl ['products'] . ".product" .
			" FROM " . $this->sql_tbl ['order_details'] .
			" LEFT JOIN " . $this->sql_tbl ['products'] .
			" ON " . $this->sql_tbl ['order_details'] . ".productcode=" .
			$this->sql_tbl ['products'] . ".productcode ".
			" WHERE orderid = " . $row ['orderid'];
			$rs2 = mysql_query ( $query );
			$count = 0;
			$line_items = array ();
			while ( $line = mysql_fetch_assoc ( $rs2 ) ) {
				echo "orderid=".$row ['orderid']." for line ".$line;
				$line_items [] = new lineItem ( $line['productcode'], $line['product'], $line['amount'], $line['price'], NULL, NULL, NULL, ++$count );
			}
			$order = new order();
			$order->setOrder_reference ( $row ['orderid'] ); //CDATA   #REQUIRED
			$order->setOrder_source ( 'Web' ); //CDATA   #IMPLIED
			$order->setBackorder_rule ( 'BACKORDER' ); //(BACKORDER|PARTIALSHIP|IGNOREBACKORDER|HOLDORDER|NOBACKORDER)	"NOBACKORDER"
			$order->setLine_items ( $line_items );

			$billing = new billing ( );
			$billing->setLast_name ( $row['b_lastname'] );
			$billing->setFirst_name ( $row['b_firstname'] );
			$billing->setCompany_name ( $row ['company'] );
			$billing->setAddress_one ( $row ['b_address'] );
			$billing->setCity ( $row ['b_city'] );
			$billing->setState ( $row ['b_state'] );
			$billing->setZip ( $row ['b_zipcode'] );
			$billing->setCountry ( $row['b_country'] );
			$billing->setPhone ( $row ['phone'] );
			$billing->setEmail ( $row ['email'] ); 
			$billing->setTax ( $row['tax'] );
			echo "new row lines 2 ".$row['coupon_discount']."<br>\n";
						echo "new row lines 3<br>\n";
			$billing->setPaid ( 'YES' );
			$billing->setPaid_date ( $row ['date'] );
			$billing->setPayment_type ( 'CLIENT' );

			$billing->setPoNumber(strtoupper(str_replace("percent``","",str_replace("absolute``","",$row['coupon']))));
			$billing->setOrderDiscount($row['coupon_discount']);
	
			$order->setBilling ( $billing );

			$shipping = new shipping ( );
			$shipping->setLast_name ( $row['s_lastname'] );
			$shipping->setFirst_name ( $row['s_firstname'] );
			$shipping->setCompany_name ( $row ['company'] );
			$shipping->setAddress_one ( $row ['s_address'] );
			$shipping->setCity ( $row ['s_city'] );
			$shipping->setState ( $row ['s_state'] );
			$shipping->setZip ( $row ['s_zipcode'] );
			$shipping->setCountry ( $row['country'] );
			$shipping->setShip_cost ( $row['shipping_cost'] );
			$shipping->setTerms ( 'SHIPPER' );
			$shipping->setShip_type($ship_type);
			$c++;
			$order->setShipping ( $shipping );
			$this->api->order_create ( $order );
			if ($this->api->xml_array ()) {
				if ($this->api->getTesting() != "TRUE") {
					if ($order->getHold_for_release () == 'YES') {
						$status_query = "UPDATE " . $this->sql_tbl['orders'] . " SET status='B' WHERE orderid = " . $row ['orderid'] . " LIMIT 1";
					} elseif (array_key_exists ( 'backorderOrderId', $api->array_xml [1] )) {
						$status_query = "UPDATE " . $this->sql_tbl['orders'] . " SET status='B' WHERE orderid = " . $row ['orderid'] . " LIMIT 1";
					} else {
						$status_query = "UPDATE " . $this->sql_tbl['orders'] . " SET status='C' WHERE orderid = " . $row ['orderid'] . " LIMIT 1";
					}
					mysql_query ( $status_query );
				}
			} else {
				if ($this->api->array_xml [1] == "Order reference already exists; cannot accept duplicate client order references - use OWD_ORDER_STATUS_REQUEST to retrieve existing order information") {
					$status_query = "UPDATE " . $this->sql_tbl['orders'] . " SET status='C' WHERE orderid = " . $row ['orderid'] . " LIMIT 1";
					print_r ( mysql_fetch_assoc ( mysql_query ( $status_query ) ) );
				} else {
					echo "[ ERROR ] " . $this->api->array_xml [1] . "<br>";
				}
			}

			echo "[RESPONSE]";
			echo "<br>\n";

		}
		echo "[" . $c . " ORDERS]<br>\n";
		echo "[ END ]<br>\n";
	}

	public function status_update($order_refnum) {
		$query = "UPDATE " . $this->tbl_orders . " SET orders_status = 113 WHERE orders_id = " . $order_refnum . " LIMIT 1";
		mysql_query ( $query );
	}

public function tester()
{
	echo "[TEST]<br>\n";
		$query = "SELECT * FROM " . $this->sql_tbl ['orders'] . " WHERE  coupon<>''";
		$rs1 = mysql_query ( $query );
		$c = 0;
		while ( $row = @mysql_fetch_assoc ( $rs1 ) ) {
			echo $row['orderid']."=".$row['coupon_discount']."<br>\n";
			echo strtoupper(str_replace("percent``","",str_replace("absolute``","",$row['coupon'])));
	//		print_r($row);
		}
			echo "[TEST-END]<br>\n";
	
	
}

}


?>