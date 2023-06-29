<?php
require_once './objects/order.php';
require_once './objects/owd_api.php';
require_once './objects/billing.php';
require_once './objects/shipping.php';
require_once './objects/lineitem.php';

class cart {

	private $api;
	private $sql_tbl;
		private $ship_type = array(
			0 => "",
			1 => "UPS.GND",
			2 => "UPS.2DA",
			110 => "UPS.STDCAMX"
		);

	function __construct() {
		require_once '../includes/global.inc.php';
		mysql_connect ( $glob['dbhost'], $glob['dbusername'], $glob['dbpassword'] );
		mysql_select_db ( $glob['dbdatabase'] );
		$this->sql_tbl=array(
			'orders'=> $glob['dbprefix'] .'CubeCart_order_sum',
			'orders_details'=> $glob['dbprefix'] .'CubeCart_order_inv',
			'countries'=> $glob['dbprefix'] . 'CubeCart_iso_countries'
		);
	}

	private function country($id){
		$query = "SELECT iso from " . $this->sql_tbl['countries'] . " where printable_name='" . $id . "'";
    		$result = mysql_query($query);
    		if(1==mysql_num_rows($result)){
    			$arr = mysql_fetch_assoc($result);
    			return $arr['iso'];
    		}else{
    			//default to US in any unexpected situation (no rows or more than one row returned)
    			return 'US';
    		}
	}

	private function split_name($name){
    	$name = trim($name);
    	if($pos = strpos($name," ")){
    		$n[] = substr($name,0,$pos);
    		$n[] = substr($name,$pos+1);
    	}else{
    		$n[1] = $name;
    	}
    	return $n;
    }
	private function productSize($option){
		if(strstr($option,"Size - X-Large")){
				return '_XL';
		}
		if(strstr($option,"Size - XX-Large")){
				return '_XXL';
		}
		if(strstr($option,"Size - XXX-Large")){
				return '_XXXL';
		}
		if(strstr($option,"Size - Large")){
				return '_L';
		}
		if(strstr($option,"Size - Medium")){
				return '_M';
		}
		if(strstr($option,"Size - Small")){
				return '_S';
		}
	}


	public function process_orders($id, $code, $testing = false, $redirect = null) {
		echo "[START]<br>\n";
		$query = "SELECT * FROM " . $this->sql_tbl ['orders'] . " WHERE status=2 LIMIT 100";
		$rs1 = mysql_query ( $query );
		$c = 0;
		while ( $row = @mysql_fetch_assoc ( $rs1 ) ) {
			$this->api = new owd_api ( $id, $code, $testing, $redirect );
			$query = "SELECT * FROM " . $this->sql_tbl['orders_details'] . " WHERE cart_order_id='" . $row['cart_order_id'] . "'";

			$rs2 = mysql_query ( $query );
			$count = 0;
			$line_items = array ();
			while ( $line = mysql_fetch_assoc ( $rs2 ) ) {
				if($line['productCode'] == '75BestAlive'){
					$line['productCode']='75_Best_Alive';
				}
				echo "ADDING ITEM:".$line['productCode']."<BR>";
				$line_items [] = new lineItem ( $line['productCode'] , $line['name'], $line['quantity'], $line['price'], $line['price'], NULL, NULL, ++$count );
				//.  $this->productSize($line['product_options'])
			}

			$order = new order ( );
			$order->setOrder_reference ( $row ['cart_order_id'] ); //CDATA   #REQUIRED
			$order->setOrder_source ( 'Web' ); //CDATA   #IMPLIED
			$order->setBackorder_rule ( 'BACKORDER' ); //(BACKORDER|PARTIALSHIP|IGNOREBACKORDER|HOLDORDER|NOBACKORDER)	"NOBACKORDER"
			$order->setLine_items ( $line_items );

			$bill_name = $this->split_name($row['name']);

			$billing = new billing ( );
			$billing->setLast_name ( $bill_name[1] );
			$billing->setFirst_name ( $bill_name[0] );
			$billing->setAddress_one ( $row ['add_1'] );
			$billing->setAddress_two($row['add_2']);
			$billing->setCity ( $row ['town'] );
			$billing->setState ( $row ['county'] );
			$billing->setZip ( $row ['postcode'] );
			$billing->setCountry ($this->country($row['country']));
			$billing->setPhone ( $row ['phone'] );
			$billing->setEmail ( $row ['email'] );
			$billing->setTax ( $row['tax'] );
			$billing->setPaid ( 'YES' );
			$billing->setPaid_date ( $row ['time'] );
			$billing->setPayment_type ( 'CLIENT' );

			$order->setBilling ( $billing );

			$ship_name = $this->split_name($row['name_d']);

			$shipping = new shipping ( );
			$shipping->setLast_name ( $ship_name[1] );
			$shipping->setFirst_name ( $ship_name[0] );
			$shipping->setAddress_one ( $row ['add_1_d'] );
			$shipping->setAddress_two($row['add_2_d']);
			$shipping->setCity ( $row ['town_d'] );
			$shipping->setState ( $row ['county_d'] );
			$shipping->setZip ( $row ['postcode_d'] );
			$shipping->setCountry ($this->country($row['country_d']));
			$shipping->setShip_cost ( $row['total_ship'] );
			$shipping->setTerms ( 'SHIPPER' );
			$shipping->setShip_type($row['shipMethod']);
			$c++;
			$order->setShipping ( $shipping );
			custom_code($order);

			$this->api->order_create ( $order );

			if ($this->api->xml_array ()) {
				if ($this->api->getTesting() != "TRUE") {
					if ($order->getHold_for_release () == 'YES') {
						$status_query = "UPDATE " . $this->sql_tbl['orders'] . " SET status='3' WHERE cart_order_id = '" . $row ['cart_order_id'] . "' LIMIT 1";
					} elseif (array_key_exists ( 'backorderOrderId', $this->api->array_xml[1] )) {
						$status_query = "UPDATE " . $this->sql_tbl['orders'] . " SET status='3' WHERE cart_order_id = '" . $row ['cart_order_id'] . "' LIMIT 1";
					} else {
						$status_query = "UPDATE " . $this->sql_tbl['orders'] . " SET status=3 WHERE cart_order_id = '" . $row ['cart_order_id'] . "' LIMIT 1";
					}
					mysql_query ( $status_query );
				}else{
					print_r($row);
				}
			} else {
				if ($this->api->array_xml [1] == "Order reference already exists; cannot accept duplicate client order references - use OWD_ORDER_STATUS_REQUEST to retrieve existing order information") {
					$status_query = "UPDATE " . $this->sql_tbl['orders'] . " SET status=3 WHERE cart_order_id = '" . $row ['cart_order_id'] . "' LIMIT 1";
					print_r ( mysql_fetch_assoc ( mysql_query ( $status_query ) ) );
				} else {
					echo "[ ERROR ] " . $this->api->array_xml [1] . "<br>";
				}
			}

			echo "[RESPONSE]";
			print_r ( $this->api->array_xml );
			echo "<br>\n";

		}
		echo "[" . $c . " ORDERS]<br>\n";
		echo "[ END ]<br>\n";
	}

	public function status_update($order_refnum) {
		$query = "UPDATE " . $this->tbl_orders . " SET orders_status = 113 WHERE orders_id = " . $order_refnum . " LIMIT 1";
		mysql_query ( $query );
	}
}

?>