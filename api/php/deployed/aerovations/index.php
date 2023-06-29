<?php

require_once 'carts/cubecart.php';

function custom_code($order){
	$shipping = array(
	'UPS 2nd Day Air'=>'UPS.2DA',
	'UPS 2nd Day Air A.M.'=>'UPS.2AM',
	'UPS 3 Day Select'=>'UPS.3DA',
	'UPS Ground'=>'UPS.GND',
	'UPS Next Day Air'=>'UPS.NDA',
	'UPS Next Day Air A.M.'=>'UPS.NAM',
	'UPS Next Day Air Saver'=>'UPS.NDS',
	'UPS Standard Canada'=>'UPS.STDCAMX',
	'UPS Worldwide Expedited'=>'UPS.WEPD',
	'UPS Worldwide Express'=>'UPS.WEXP',
	'UPS Worldwide Express Plus'=>'TANDATA_UPS.UPS.WEXPPLS',
	'UPS Worldwide Express Saver'=>'TANDATA_UPS.UPS.WEXPSVR',
	'USPS Bnd Prt Mtr Single Piece'=>'TANDATA_USPS.USPS.BPM',
	'USPS Express Mail'=>'POS.EXP',
	'USPS Express Mail International'=>'OWD_USPS_I_EXP_DMND',
	'USPS First-Class Mail'=>'OWD.1ST.LETTER',
	'USPS First-Class Mail International'=>'OWD_USPS_I_FIRST',
	'USPS Library Mail Single-Piece'=>'TANDATA_USPS.USPS.LIBRARY',
	'USPS Media Mail Single-Piece'=>'OWD.4TH.BOOK',
	'USPS Parcel Post'=>'OWD.4TH.PARCEL',
	'USPS Priority Mail'=>'OWD.1ST.PRIORITY',
	'Free Shipping'=>'OWD.1ST.PRIORITY',
	'Flat Rate'=>'OWD.1ST.PRIORITY',
	'USPS Global Priority Mail - Flat-rate Envelope (Small)'=>'OWD_USPS_I_PRIORITY'
	);

	$order->shipping->setShip_type($shipping[trim($order->shipping->getShip_type())]);
	
}

$clientID = 404;
$client_auth_code = "P6QkI8Y6E84CKOM2CjPWEwM=";
$cart = new cart();

if($_POST['update']){
	if($client_auth_code===$_POST['passwd']){
		$cart->status_update(intval($_POST['orderNum']));
	}
}else{
	$cart->process_orders($clientID,$client_auth_code);
}

?>