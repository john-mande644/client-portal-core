<?php

class owd_api{

	private $ch;
	private $client_id;
	private $client_auth_code;
	private $testing;
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

	}

	function conv($dirty){
		return iconv("ISO-8859-1","UTF-8", $dirty);
	}

	function writeAttribute($arr){
		foreach($arr as $k=>$v){
			$this->xml_w->writeAttribute($k,$this->conv($v));
		}
	}

	function inventory_count($inv){
		$this->version = '1.9';
		$this->new_request();

		//Write XML

		$this->xml_w->startElement('OWD_INVENTORY_COUNT_REQUEST');
		foreach($inv as $k=>$v){
			$this->xml_w->writeAttribute($k,$v);
		}
		return $this->send();
	}

	function asn_create($asn){
		$this->version = '1.9';
		$this->new_request();
		$this->xml_w->startElement('OWD_ASN_CREATE_REQUEST');
        $this->xml_w->writeElement('PO_REFERENCE',$asn->po_reference);
        $this->xml_w->writeElement('REFERENCE',$asn->asn_reference);
        $this->xml_w->writeElement('SHIPPER',$asn->shipper);
        $this->xml_w->writeElement('EXPECTED_DATE',$asn->expected_date);
        $this->xml_w->writeElement('CARRIER',$asn->carrier);
        $this->xml_w->writeElement('AUTORELEASE',$asn->autorelease);
        $this->xml_w->writeElement('CARTONS',$asn->cartons);
        $this->xml_w->writeElement('PALLETS',$asn->pallets);
        $this->xml_w->writeElement('NOTES',$asn->notes);
        $this->xml_w->startElement('ASNITEMS');
        foreach($asn->asn_items as $item){
        			$this->xml_w->startElement('ASNITEM');
                    $this->xml_w->writeElement('ASNITEM_SKU',$item->sku);
                    $this->xml_w->writeElement('ASNITEM_EXPECTED',$item->quantity);
                    $this->xml_w->writeElement('ASNITEM_DESCRIPTION',$item->description);
		            $this->xml_w->endElement();
        		}
        $this->xml_w->endElement();

		return $this->send();
	}




	function send(){
		$this->xml_w->endDocument();
		$post_xml = $this->xml_w->outputMemory();
		$this->encoded_xml = $post_xml;

		echo $post_xml;
		//Transmit XML

		curl_setopt($this->ch, CURLOPT_POSTFIELDS, $this->encoded_xml);
		if (0 != ($this->raw_xml = curl_exec($this->ch))){
			return true;
		}else{
			return false;
		}
	}


	function xml_array(){
		$this->array_xml = array();
		 echo $this->raw_xml."<BR>";

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


//end class
}
?>