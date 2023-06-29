<?php

class owd_api{
	
	private $ch;
	private $client_id;
	private $client_auth_code;
	private $testing;
	private $redirect;
	private $xml_w;
	private $xml_r;
	private $version;
	public $response;
	public $raw_xml;
	public $array_xml;

	function __construct($id,$code,$testing = 'FALSE',$redirect=null){
		
		$this->client_id = $id;
		$this->client_auth_code = $code;
		$this->testing = $testing;
		$this->redirect = $redirect;
		
		//Start XMLWriter and call new_request
		
		$this->xml_w = new XMLWriter();
		$this->xml_w->openMemory();
		
				
		//Start XMLReader and create response array
		
		$this->xml_r = new XMLReader();
		
		//Start Curl and set universal variables
		
		$this->ch = curl_init('https://secure.owd.com/webapps/api/api.jsp');
		$user_agent = $_SERVER['HTTP_USER_AGENT'];
		$header[] = "Content-type: text/xml;charset=\"utf-8\"";
		curl_setopt($this->ch, CURLOPT_FAILONERROR, 1);
		curl_setopt($this->ch, CURLOPT_FOLLOWLOCATION, 1);
		curl_setopt($this->ch, CURLOPT_RETURNTRANSFER,1); 
		curl_setopt($this->ch, CURLOPT_PORT, 443);
		curl_setopt($this->ch, CURLOPT_TIMEOUT, 15);
		curl_setopt($this->ch, CURLOPT_USERAGENT, $user_agent);
		curl_setopt($this->ch, CURLOPT_SSL_VERIFYHOST, 2);
		curl_setopt($this->ch, CURLOPT_SSL_VERIFYPEER, 0);
		curl_setopt($this->ch, CURLOPT_HTTPHEADER,$header);
	}
	
	function __destruct(){
		$this->xml_r->close();
		curl_close($this->ch);
	}
	
	function new_request(){
		//Start XMLWriter and set universal variables
		

		$this->xml_w->startDocument('1.0','UTF-8');
		$this->xml_w->startElement('OWD_API_REQUEST');
		$this->xml_w->writeAttribute('api_version',$this->version);
		$this->xml_w->writeAttribute('client_id',$this->client_id);
		$this->xml_w->writeAttribute('client_authorization',$this->client_auth_code);
		$this->xml_w->writeAttribute('testing',$this->testing);
		if(!is_null($this->redirect)){
			$this->xml_w->writeAttribute('redirect',$this->redirect);	
		}
	}
/*	
	function asn_create($asn){
		//<OWD_REPORT_REQUEST start_date="20010901" end_date="20010901" report_type="RETURNS" format="XML" />
		$this->xml_w->startElement('OWD_ASN_CREATE_REQUEST');
		foreach($asn as $k=>$v){
			$this->xml_w->writeAttribute($k,$v);
		}
	}
	
	function asn_add($item){
		$this->xml_w->startElement('item');
		foreach($item as $k=>$v){
			$this->xml_w->writeAttribute($k,$v);
		}
		$this->xml_w->endElement();
	}
	
	function asn_send(){
		return $this->send();
	}
	
	function cc_transaction($card){
		$this->xml_w->startElement('OWD_CC_TRANSACTION_REQUEST');
		foreach($card as $k=>$v){
			$this->xml_w->writeAttribute($k,$v);
		}
		return $this->send();	
	}
*/
	function conv($dirty){
		return iconv("ISO-8859-1","UTF-8", $dirty);
	}

	function writeAttribute($arr){
		foreach($arr as $k=>$v){
			$this->xml_w->writeAttribute($k,$this->conv($v));
		}
	}

	function inventory_count($inv){
		$this->version = '1.2';
		$this->new_request();
		
		//Write XML
		
		$this->xml_w->startElement('OWD_INVENTORY_COUNT_REQUEST');
		foreach($inv as $k=>$v){
			$this->xml_w->writeAttribute($k,$v);
		}
		return $this->send();
	}
		
	function order_cancel($order_num){
		$this->version = '1.2';
		$this->new_request();
		$this->xml_w->startElement('OWD_ORDER_CANCEL_REQUEST');
		foreach($order_num as $k=>$v){
			$this->xml_w->writeAttribute($k,$v);
		}
		return $this->send();
	}
	
	function order_create($order){	
		$this->version = '1.6';
		$this->new_request();
		$this->xml_w->startElement('OWD_ORDER_CREATE_REQUEST');
		$this->xml_w->writeAttribute('order_reference',$this->conv($order->getOrder_reference()));
		$this->xml_w->writeAttribute('order_source',$this->conv($order->getOrder_source()));
		$this->xml_w->writeAttribute('backorder_rule',$this->conv($order->getBackorder_rule()));
		$this->xml_w->writeAttribute('hold_for_release',$this->conv($order->getHold_for_release()));
		$this->order_billing($order->getBilling());
		$this->order_shipping($order->getShipping());
		foreach($order->getLine_items() as $item){
			$this->order_line_item($item);
		}
		$this->order_comments($order->getComments());
		$this->order_message($order->getMessage());
		$this->order_warehouse_notes($order->getWarehouse_notes());
		$this->order_send();
	}
	
	function order_billing($billing){
		$this->xml_w->startElement('BILLING_INFO');
			
		$this->xml_w->writeAttribute('last_name',$this->conv($billing->getLast_name()));
		$this->xml_w->writeAttribute('first_name',$this->conv($billing->getFirst_name()));
		$this->xml_w->writeAttribute('comapny_name',$this->conv($billing->getCompany_name()));
		$this->xml_w->writeAttribute('address_one',$this->conv($billing->getAddress_one()));
		$this->xml_w->writeAttribute('address_two',$this->conv($billing->getAddress_two()));
		$this->xml_w->writeAttribute('city',$this->conv($billing->getCity()));
		$this->xml_w->writeAttribute('state',$this->conv($billing->getState()));
		$this->xml_w->writeAttribute('zip',$this->conv($billing->getZip()));
		$this->xml_w->writeAttribute('country',$this->conv($billing->getCountry()));
		$this->xml_w->writeAttribute('phone',$this->conv($billing->getPhone()));
		$this->xml_w->writeAttribute('email',$this->conv($billing->getEmail()));
		$this->xml_w->writeAttribute('tax',$this->conv($billing->getTax()));
		$this->xml_w->writeAttribute('paid',$this->conv($billing->getPaid()));
		$this->xml_w->writeAttribute('paid_date',$this->conv($billing->getPaid_date()));
		$this->xml_w->writeAttribute('payment_type',$this->conv($billing->getPayment_type()));
		
		$this->xml_w->endElement();
	}
	
	function order_line_item($item){
		$this->xml_w->startElement('LINE_ITEM');
		
		$this->xml_w->writeAttribute('part_reference',$this->conv($item->getPart_reference()));
    	$this->xml_w->writeAttribute('description',$this->conv($item->getDescription()));
    	$this->xml_w->writeAttribute('requested',$this->conv($item->getRequested()));
    	$this->xml_w->writeAttribute('cost',$this->conv($item->getCost()));
    	//$this->xml_w->writeAttribute('declared_value',$this->conv($item->getDeclared_value()));
    	//$this->xml_w->writeAttribute('customs_desc',$this->conv($item->getCustoms_desc()));
    	$this->xml_w->writeAttribute('backordered',$this->conv($item->getBackordered()));
    	$this->xml_w->writeAttribute('line_number',$this->conv($item->getLine_number()));
		
		$this->writeAttribute($item);
		$this->xml_w->endElement();
	}
	
	function order_message($message){
		$this->xml_w->startElement('MESSAGE');
		$this->xml_w->writeCdata($message);
		$this->xml_w->endElement();
	}
	
	function order_comments($comments){
		$this->xml_w->startElement('COMMENTS');
		$this->xml_w->writeCdata($comments);
		$this->xml_w->endElement();
	}
	
	function order_warehouse_notes($notes){
		$this->xml_w->startElement('WAREHOUSENOTES');
		$this->xml_w->writeCdata($notes);
		$this->xml_w->endElement();
	}
	
	function order_shipping($shipping){
		$this->xml_w->startElement('SHIPPING_INFO');
		
		$this->xml_w->writeAttribute('last_name',$this->conv($shipping->getLast_name()));
		$this->xml_w->writeAttribute('first_name',$this->conv($shipping->getFirst_name()));
		$this->xml_w->writeAttribute('company_name',$this->conv($shipping->getCompany_name()));
		$this->xml_w->writeAttribute('address_one',$this->conv($shipping->getAddress_one()));
		$this->xml_w->writeAttribute('address_two',$this->conv($shipping->getAddress_two()));
		$this->xml_w->writeAttribute('city',$this->conv($shipping->getCity()));
		$this->xml_w->writeAttribute('state',$this->conv($shipping->getState()));
		$this->xml_w->writeAttribute('zip',$this->conv($shipping->getZip()));
		$this->xml_w->writeAttribute('country',$this->conv($shipping->getCountry()));
		$this->xml_w->writeAttribute('ship_cost',$this->conv($shipping->getShip_cost()));
		$this->xml_w->writeAttribute('insure_amount',$this->conv($shipping->getInsure_amount()));
		$this->xml_w->writeAttribute('declared_value',$this->conv($shipping->getDeclared_value()));
		$this->xml_w->writeAttribute('ship_type',$this->conv($shipping->getShip_type()));
		if($shipping->getThird_party() != null){
		
			$this->xml_w->startElement('THIRD_PARTY_SHIPPER');
			$this->writeAttribute($shipping->getThird_party());
			$this->xml_w->endElement();
		}
		if($shipping->getBest_way() != null){
			$this->xml_w->startElement('BEST_WAY');
			foreach($shipping->getBest_way() as $v){
				$this->xml_w->startElement('CARRIER');
				$this->xml_w->writeCdata($v);
				$this->xml_w->endElement();
			}
			$this->xml_w->endElement();
		}
		$this->xml_w->endElement();
	}
	
	function order_send(){
		return $this->send();
	}
	
	function send(){
		$this->xml_w->endDocument();
		$post_xml = $this->xml_w->outputMemory();
		$this->encoded_xml = $post_xml;
		
		//Transmit XML

		curl_setopt($this->ch, CURLOPT_POSTFIELDS, $this->encoded_xml);
		if (0 != ($this->raw_xml = curl_exec($this->ch))){
			return true;
		}else{
			return false;
		}
	}
	
	function order_status($order){
		$this->version = '1.2';
		$this->new_request();
		$this->xml_w->startElement('OWD_ORDER_STATUS_REQUEST');
		foreach($order as $k=>$v){
			$this->xml_w->writeAttribute($k,$v);
		}
		return $this->send();
	}
/*
	function payment($client,$transaction,$customer=null){
		$this->xml_w->startElement('OWD_PAYMENT_REQUEST');
		foreach($client as $k=>$v){
			$this->xml_w->writeAttribute($k,$v);
		}
		$this->xml_w->startElement('TRANSACTION');
		foreach($transaction as $k=>$v){
			$this->xml_w->writeAttribute($k,$v);
		}
		$this->xml_w->endElement();
		if($customer != null){
		$this->xml_w->startElement('CUSTOMER');
			foreach($customer as $k=>$v){
				$this->xml_w->writeAttribute($k,$v);
			}
		}
		return $this->send();
	}
*/	
	function xml_array(){
		$this->array_xml = array();
		$this->xml_r->XML($this->raw_xml);
		$this->xml_r->read();
		if($this->xml_r->getAttribute('results') == 'SUCCESS'){
			while($this->xml_r->read()){
				$x++;
				if($this->xml_r->hasAttributes && $this->xml_r->nodeType != 15){
					$name = $this->xml_r->name;	
					while($this->xml_r->moveToNextAttribute()){
						$this->array_xml[$x][$name][$this->xml_r->name] = $this->xml_r->value;
					}
				}
			}
			$status = true;
		}else{
			$status = false;
			$message = $this->xml_r->getAttribute('error_response');
			$this->array_xml = array('Error',$message);
		}
		return $status;
	}
	
	public function getTesting() {
		return $this->testing;
	}

	/*
	function report($report){
		$this->version = '1.2';
		$this->new_request();
		$this->xml_w->startElement('OWD_REPORT_REQUEST');
		foreach($report as $k=>$v){
			$this->xml_w->writeAttribute($k,$v);
		}
		$this->send();
	}
	*/
//end class
}
?>