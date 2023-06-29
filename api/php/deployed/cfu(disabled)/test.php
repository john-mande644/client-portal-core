<?php


require_once '../../includes/configure.php';
require_once 'carts/zencart.php';


try{
echo "creating cart<BR>";

$cart = new cart();
$query = "SELECT * FROM " . DB_PREFIX ."orders" . " WHERE orders_id=".$_GET['id'];
echo $query."<BR>";
		$rs1 = mysql_query($query);
		while($row=@mysql_fetch_assoc($rs1))
		{
		echo "Order ID:".$_POST['id']."<BR>";
		echo "Order's Bill Country:".$row['billing_country']."<BR>";
		echo "Converted Bill Country With Ending Quote:".convert_country_withquote($row['billing_country'])."<BR>";
		echo "Converted BIll Country Without Ending Quote (original):".convert_country($row['billing_country'])."<BR>";
        echo "[END OF ORDER INFO]<HR>";


		}

		echo "Countries list<BR>";
		$query_country = "SELECT * FROM countries";
		$rs = mysql_query($query_country);
		while($row=@mysql_fetch_assoc($rs))
		{
		echo $row['countries_iso_code_2'].":".$row['countries_name']."<BR>";
		}


}
catch(Exception $e){
	echo $e;
}

function convert_country_withquote($country){
		$query_country = "SELECT countries_iso_code_2 FROM countries WHERE countries_name = '" . $country."'";
		echo "QC".$query_country."<BR>";

		$rs = mysql_query($query_country);
		$result = mysql_fetch_assoc($rs);
		$country = $result['countries_iso_code_2'];
		if('FX'==$country){
			$country='FR';
		}
		return $country;
	}

function convert_country($country){
		$query_country = "SELECT countries_iso_code_2 FROM countries WHERE countries_name = '" . $country;
		echo "QC".$query_country."<BR>";
		
		$rs = mysql_query($query_country);
		$result = mysql_fetch_assoc($rs);
		$country = $result['countries_iso_code_2'];
		if('FX'==$country){
			$country='FR';
		}
		return $country;
	}

?>