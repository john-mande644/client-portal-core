<?php
require './carts/xcart.php';

$clientID = 428; //replace with provided client ID
$client_auth_code = "okuhYjjuqH9wkuALB48I5AM="; //replace with provided authorization code
echo "create";
$cart = new cart();



if($_POST['update']){
	if($client_auth_code===$_POST['passwd']){
		$cart->status_update(intval($_POST['orderNum']));
	}
}else{
	//$cart->tester();
	$cart->process_orders($clientID,$client_auth_code);
}

?>
