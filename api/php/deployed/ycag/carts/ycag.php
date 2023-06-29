<?php

require_once './objects/order.php';
require_once './objects/owd_api.php';
require_once './objects/billing.php';
require_once './objects/shipping.php';
require_once './objects/lineitem.php';
require_once '../dbconnect.php';

class cart{

	private $api;

    function __construct(){

    }

    private function convertCountry($id){
    	$query = "SELECT abbreviation from countries where id=" . $id;
    	$result = mysql_query($query);
    	if(1==mysql_num_rows($result)){
    		$arr = mysql_fetch_assoc($result);
    		return $arr['abbreviation'];
    	}else
    	{
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

    public function process_orders($id,$code,$testing=false,$redirect=null){
    	echo "[START]<br>\n";

    	$query = "SELECT * FROM invoices where id>= 11681 and DATE_ADD(curdate(), INTERVAL -7 DAY)<invoice_date order by id asc";
		$rs1 = mysql_query($query);
		echo mysql_num_rows($rs1);
		$c = 0;
		$items = array(	'GIFTBOX'=>'66621300001gift',
						'HIS8'=>'66621300002',
						'HERS8'=>'66621300001',
						'COND8'=>'66621300003',
						'NBG8'=>"66621300009",
						'HIS1'=>'66621300006',
						'HERS1'=>'66621300005',
						'COND1'=>'66621300007',
						'NBG1'=>'66621300008',
						'EXF64'=>'66621300009--64 oz',
						'COND64'=>'66621300003-64 oz',
						'HIM64'=>'66621300002-64 oz',
						'HER64'=>'66621300001-64 oz');
		while($row=@mysql_fetch_assoc($rs1)){
		$this->api = new owd_api($id,$code,$testing,$redirect);
			echo "Attempting invoice id: ".$row['id']."<br>\n";
			$count++;
			$query="SELECT * FROM invoices_items
					LEFT JOIN (products) ON invoices_items.product_id = products.id
					WHERE invoice_id = " . $row['id'];
			$rs2=mysql_query($query);
			$count=0;
			$line_items = array();
			while($line=mysql_fetch_assoc($rs2)){

			echo "adding SKU ".$line['code']."/".$items[$line['code']].":".$line['description']."<br>\n";

				$line_items[] = new lineItem(	$items[$line['code']],
												$line['description'],
												$line['quantity'],
												$line['price_each'],
												0.00,
												'',
												0,
												++$count);

			}
echo "1";
			$order = new order();
			$order->setOrder_reference($row['id']);		//CDATA   #REQUIRED
			$order->setLine_items($line_items);

			$shipping_name = $billing_name = $this->split_name(trim($row['name']));
echo "1";
			$billing = new billing();
			$billing->setLast_name($billing_name[1]);
			$billing->setFirst_name($billing_name[0]);
			$billing->setAddress_one($row['bill_street1']);
			$billing->setAddress_two($row['bill_street2']);
			$billing->setCity($row['bill_city']);
			$billing->setState($row['bill_state']);
			$billing->setZip($row['bill_postal']);
			$billing->setCountry($this->convertCountry($row['bill_country']));
			$billing->setTax($row['tax']);
			$billing->setPaid('YES');
			$billing->setPaid_date($row['invoice_date']);
			$billing->setPayment_type('CLIENT');
echo "1";
			$order->setBilling($billing);
echo "1";
			$shipping = new shipping();
			$shipping->setLast_name($shipping_name[1]);
			$shipping->setFirst_name($shipping_name[0]);
			$shipping->setAddress_one($row['ship_street1']);
			$shipping->setAddress_two($row['ship_street2']);
			$shipping->setCity($row['ship_city']);
			$shipping->setState($row['ship_state']);
			$shipping->setZip($row['ship_postal']);
			$shipping->setCountry($this->convertCountry($row['ship_country']));
			$shipping->setShip_cost($row['shipping']);
			//Don't pass these unless specifically requested
			//$shipping->setInsure_amount($row['total']);
			//$shipping->setDeclared_value($row['total']);
			$shipping->setTerms('SHIPPER');
echo "1";
			$order->setShipping($shipping);

			custom_code($order);				//CDATA #REQUIRED
echo "1";
			$this->api->order_create($order);
echo "1";

			echo "[RESPONSE]";
			print_r($this->api->array_xml);
			echo "<br>\n";
			if($this->api->xml_array()){
				if($this->api->getTesting()!="TRUE"){
					if($order->getHold_for_release()=='YES'){

					}elseif(array_key_exists('backorderOrderId',$api->array_xml[1])){

					}else{

					}

				}
			}else{
				//if($this->api->array_xml[1]=="Order reference already exists; cannot accept duplicate client order references - use OWD_ORDER_STATUS_REQUEST to retrieve existing order information"){

				//}else{
				//	echo "[ ERROR ] " . $this->api->array_xml[1] . "<br>";
				//}
			}

			$c++;
	    }
	    echo "[" . $c . " ORDERS]<br>\n";
	    echo "[ END ]<br>\n";
	}

	public function status_update($order_refnum){

	}
}


?>