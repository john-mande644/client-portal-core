<?php
require_once 'carts/ycag.php';


function custom_code($order){
	$order->setOrder_source('YCAG Web Store');
	$order->setBackorder_rule('BACKORDER');
	
//NOTE: Ship method will be reset during import at OWD based on predicted cost. Default will be Parcel Post
	$order->shipping->setShip_type("OWD.4TH.PARCEL");
//removed to go live SB 9/12/08
//	$order->setHold_for_release('YES');
}

$clientID = 397;
$client_auth_code = "LADAlvt+MSo+6uSV6evO9wM=";
$cart = new cart();

if($_POST['update']){
	if($client_auth_code===$_POST['passwd']){
		$cart->status_update(intval($_POST['orderNum']));
	}
}else{
	$cart->process_orders($clientID,$client_auth_code);
}

?>