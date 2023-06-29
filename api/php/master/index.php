<?php
require_once 'carts/<cart.php>';
/*
replace <cart.php> above with a reference to the actual cart-specific
file deployed with this installation
*/

function custom_code($order){

}

/*

The following two values need to be provided by OWD

*/
$clientID = 55;
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