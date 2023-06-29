<?php

require_once './asn.php';
require_once './asn_item.php';
require_once './owd_api.php';

$tester = new test_asn();
$tester->test_create_asn();

class test_asn {

	private $api;

	function __construct() {

	}

	public function test_create_asn() {
		echo "[START]<br>\n";

			$this->api = new owd_api ( '517', '6IjD55HrHSv+zqDdY83/UgM=', 'TRUE' );

			$asn_items = array ();


			$asn = new asn();

			$asn->po_reference = "MY PO Ref 123";
			$asn->asn_reference = "My internal ASN reference" ;
			$asn->expected_date = "20130701";
			$asn->shipper = "Bioscience";
			$asn->notes = "Some notes or instructions";

			$asn_items [] = new asn_item ( 'Test', '1', 'item description');
			$asn_items [] = new asn_item ( 'Test2', '1', 'item description 2');

			$asn->asn_items = $asn_items;


			$this->api->asn_create ( $asn );

			if ($this->api->xml_array ()) {
				echo "[ SUCCESS ]" . "<br>" ;
			} else {

					echo "[ ERROR ] " . $this->api->array_xml [1] . "<br>";

			}


	}



}


?>