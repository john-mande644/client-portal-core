<?php
require './carts/xcart.php';

$clientID = 407;
$client_auth_code = "UCsOfxiIrtd01T7mQLNBbwM=";
$cart = new cart();

if($_POST['update']){
	if($client_auth_code===$_POST['passwd']){
		$cart->status_update(intval($_POST['orderNum']));
	}
}else{
	$cart->process_orders($clientID,$client_auth_code);
}

?>