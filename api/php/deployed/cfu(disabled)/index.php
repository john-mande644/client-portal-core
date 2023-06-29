<?php

require_once 'carts/zencart.php';

function custom_code($order){
	$line_items = $order->getLine_items();
	$total = 0;
	foreach($line_items as $num=>$line){
		$total += $line->getCost();
		if('One Month Combo' === $line->getPart_reference()){
			$line_items[$num] = new lineItem('The Kitchen Sink','The Kitchen Sink',$line->getRequested(),0,0,'The Kitchen Sink',0,$num + 1);
			$line_items[] = new lineItem('CC120','TrueBlue Coral Calcium',$line->getRequested(),0,0,'TrueBlue Coral Calcium',0,count($line_items)+2);
			$quantity += 2*$line->getRequested;
		}elseif('Six Month Combo' === $line->getPart_reference()){
			$line_items[$num] = new lineItem('The Kitchen Sink','The Kitchen Sink',6*$line->getRequested(),0,0,'The Kitchen Sink',0,$num + 1);
			$line_items[] = new lineItem('CC120','TrueBlue Coral Calcium',6*$line->getRequested(),0,0,'TrueBlue Coral Calcium',0,count($line_items)+2);
			$quantity += 12*$line->getRequested;
		}elseif('cc147g'=== strtolower($line->getPart_reference())){
			$line_items[] = new lineItem('scoop','One Gram Scoop',$line->getRequested(),0.00,0.00,'One Gram Scoop',0,count($line_items)+2);
			$quantity += $line->getRequested();
		}else{
			$quantity += $line->getRequested();
		}
	}
	$line_items[] = new lineItem('Brochure - TKS','Brochure',1,0,0,'Brochure',0,count($line_items)+1);
	$line_items[] = new lineItem('BROCHURE-TB','Brochure',1,0,0,'Brochure',0,count($line_items)+1);
	$order->setLine_items($line_items);
	if('US' == strtoupper($order->shipping->getCountry())||'USA' == strtoupper($order->shipping->getCountry())){
		if($quantity < 2){
			$order->shipping->setShip_type("OWD.1ST.LETTER");
		}elseif($quantity < 6){
			$order->shipping->setShip_type("OWD.1ST.PRIORITY");
		}else{
			$order->shipping->setShip_type("UPS.GND");
		}
	}else{
		if($quantity < 8){
			$order->shipping->setShip_type("OWD_USPS_I_FIRST");
		}else{
			$order->shipping->setShip_type("OWD_USPS_I_PRIORITY");
		}
		$order->setHold_for_release('YES');
	}
	if($total>500){
		$order->setHold_for_release('YES');
	}
	if('ak' === strtolower($order->shipping->getState())||'hi' === strtolower($order->shipping->getState())){
		$order->shipping->setShip_type("OWD.1ST.PRIORITY");
	}
		
}

$clientID = 149;
$client_auth_code = "ZOrWtvsgZWuZfqR02uDKMQM=";
$cart = new cart();

try{
	if($_POST['update']){
		if($client_auth_code===$_POST['passwd']){
			$cart->status_update(intval($_POST['orderNum']));
		}
	}else{
		$cart->process_orders($clientID,$client_auth_code);
	}
}
catch(Exception $e){
	mail("ejackman@owd.com","Cures For You PHP Exception",$e->getMessage());
}

?>