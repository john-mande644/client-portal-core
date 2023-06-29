<?php

require_once './objects/order.php';
require_once './objects/owd_api.php';
require_once './objects/billing.php';
require_once './objects/shipping.php';
require_once './objects/lineitem.php';
require_once '../../includes/configure.php';

class cart{

	private $api;
	private $tbl_categories_description;
    private $tbl_countries;
    private $tbl_customers;
    private $tbl_orders;
    private $tbl_orders_products;
    private $tbl_orders_products_attributes;
    private $tbl_orders_total;
    private $tbl_products_to_categories;
    private $tbl_products_options_values;
    
    function __construct(){
        mysql_connect(DB_SERVER,DB_SERVER_USERNAME,DB_SERVER_PASSWORD);
        mysql_select_db(DB_DATABASE);

        //map database tables so they have the proper prefix set in ../includes/configure.php
        $this->tbl_categories_description = DB_PREFIX . "categories_description";
        $this->tbl_countries = DB_PREFIX ."countries";
        $this->tbl_customers = DB_PREFIX ."customers";
        $this->tbl_orders = DB_PREFIX ."orders";
        $this->tbl_orders_products = DB_PREFIX . "orders_products";
        $this->tbl_orders_products_attributes = DB_PREFIX . "orders_products_attributes";
        $this->tbl_orders_total = DB_PREFIX . "orders_total";
        $this->tbl_products_to_categories = DB_PREFIX . "products_to_categories";
        $this->tbl_products_options_values = DB_PREFIX . "products_options_values";

    }
    
	private function convert_country($country){
		$query_country = "SELECT countries_iso_code_2 FROM $this->tbl_countries WHERE countries_name = '" . $country;
		$rs = mysql_query($query_country);
		$result = mysql_fetch_assoc($rs);
		$country = $result['countries_iso_code_2'];
		if('FX'==$country){
			$country='FR';
		}
		return $country;
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
	
    public function process_orders($id,$code,$testing=false,$redirect=null){
    	echo "[START]<br>\n";
    	$query = "SELECT * FROM " . $this->tbl_orders . " WHERE orders_status=1 LIMIT 100";
		$rs1 = mysql_query($query);
		$count = 0;
		while($row=@mysql_fetch_assoc($rs1)){
			$count++;
			$this->api = new owd_api($id,$code,$testing,$redirect);
			$query="SELECT * FROM $this->tbl_orders_products WHERE orders_id = " . $row['orders_id'];
			$rs2=mysql_query($query);
			$count=0;
			$line_items = array();
			$media = true; //flag is set at the begining of each loop iteration
			while($line=mysql_fetch_assoc($rs2)){
				$query_attributes="SELECT * FROM $this->tbl_orders_products_attributes 
					LEFT JOIN ($this->tbl_products_options_values)
					ON $this->tbl_orders_products_attributes.products_options_values_id = $this->tbl_products_options_values.products_options_values_id
					WHERE $this->tbl_orders_products_attributes.orders_id = " . $row['orders_id'] . " and " . $this->tbl_orders_products_attributes . ".orders_products_id = " . $line['orders_products_id'];
				$rs3=array();
				$rs3=mysql_query($query_attributes);
				$attributes = array();
				$attributes = mysql_fetch_assoc($rs3);
				$size_is = null;
				if($size_is = $attributes['products_options_values_name']){
					$size = $sizes[$size_is];
				}else{
					$size = false;
				}
				$line_items[] = new lineItem($line['products_model'], $line['products_name'], $line['products_quantity'],
				$line['final_price'], $line['products_price'], $line['products_'],	$line['products_'], ++$count);

				$tax = $line['products_tax'] / 100;
		 		$query_catagory="SELECT * FROM";
				if($size){ 	//verify if item does not have a size if not toggle $media flag to false  
					$media=false;
				}
				
			}
			$order = new order();		
			$order->setOrder_reference($row['orders_id']);		//CDATA   #REQUIRED
			$order->setOrder_source('Web');		//CDATA   #IMPLIED
	        $order->setBackorder_rule('PARTIALSHIP');		//(BACKORDER|PARTIALSHIP|IGNOREBACKORDER|HOLDORDER|NOBACKORDER)	"NOBACKORDER"
			$order->setLine_items($line_items);
			
		
			$total_query = "SELECT * FROM " . $this->tbl_orders_total . " WHERE orders_id = " . $row['orders_id'];
			$rs4 = mysql_query($total_query);
			$subtotal = array();
			while($st = mysql_fetch_assoc($rs4)){
				$subtotal[$st['class']] = $st['value'];
			}
			
			$country_bil=$this->convert_country($row['billing_country']);
			$country_del=$this->convert_country($row['delivery_country']);
				
			$billing_name = $this->split_name($row['billing_name']);
		
			$billing = new billing();
			$billing->setLast_name($billing_name[1]);
			$billing->setFirst_name($billing_name[0]);
			$billing->setCompany_name($row['billing_company']);
			$billing->setAddress_one($row['billing_street_address']);
			$billing->setAddress_two($row['billing_suburb']);
			$billing->setCity($row['billing_city']);
			$billing->setState($row['billing_state']);
			$billing->setZip($row['billing_postcode']);
			$billing->setCountry($country_bil);
			$billing->setPhone($row['customers_telephone']);
			$billing->setEmail($row['customers_email_address']);
			$billing->setTax($subtotal['ot_tax']);
			$billing->setPaid('YES');
			$billing->setPaid_date($row['date_purchased']);
			$billing->setPayment_type('CLIENT');
			
			
			$order->setBilling($billing);
			
			if("Check/Money Order" == $row['payment_method']){
				$order->setHold_for_release('YES');
			}
				
			$shipping_name = $this->split_name($row['delivery_name']);
			
			$shipping = new shipping();
			$shipping->setLast_name($shipping_name[1]);
			$shipping->setFirst_name($shipping_name[0]);
			$shipping->setCompany_name($row['delivery_company']);
			$shipping->setAddress_one($row['delivery_street_address']);
			$shipping->setAddress_two($row['delivery_suburb']);
			$shipping->setCity($row['delivery_city']);
			$shipping->setState($row['delivery_state']);
			$shipping->setZip($row['delivery_postcode']);
			$shipping->setCountry($country_del);
			$shipping->setShip_cost($subtotal['ot_shipping']);
			$shipping->setInsure_amount($subtotal['ot_subtotal']);
			$shipping->setDeclared_value($subtotal['ot_subtotal']);
			$shipping->setTerms('SHIPPER');
			
			$order->setShipping($shipping);
			
			custom_code($order);				//CDATA #REQUIRED
			
			$this->api->order_create($order);
			
			if($this->api->xml_array()){
				if($this->api->getTesting()!="TRUE"){
					if($order->getHold_for_release()=='YES'){
						$status_query = "UPDATE $this->tbl_orders SET orders_status=2 WHERE orders_id = ". $row['orders_id'] ." LIMIT 1";
					}elseif(array_key_exists('backorderOrderId',$api->array_xml[1])){
						$status_query = "UPDATE $this->tbl_orders SET orders_status=114 WHERE orders_id = ". $row['orders_id'] ." LIMIT 1";
					}else{
						$status_query = "UPDATE $this->tbl_orders SET orders_status=112 WHERE orders_id = ". $row['orders_id'] ." LIMIT 1";
					}
					mysql_query($status_query);
				}
			}else{
				if($api->array_xml[1]=="Order reference already exists; cannot accept duplicate client order references - use OWD_ORDER_STATUS_REQUEST to retrieve existing order information"){
					$status_query = "UPDATE $tbl_orders SET orders_status=112 WHERE orders_id = ". $row['orders_id'] ." LIMIT 1";
					print_r(mysql_fetch_assoc(mysql_query($status_query)));
				}else{
					echo "[ ERROR ] " . $api->array_xml[1] . "<br>"; 
				}
			}
			
			echo "[RESPONSE]";
			print_r($this->api->array_xml);
			echo "<br>\n";
			
	    }
	    echo "[" . $count . " ORDERS]<br>\n";
	    echo "[ END ]<br>\n";
	}

	public function status_update($order_refnum){
		$query="UPDATE ". $this->tbl_orders . " SET orders_status = 113 WHERE orders_id = " . $order_refnum . " LIMIT 1";
		mysql_query($query);
		echo "DONE";
	}
}


?>