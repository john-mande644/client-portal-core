<?php

$location="172.16.2.250"; //Hostname of MSSQL server
$username="billingadmin"; //Username for MSSQL
$password="bill7172"; //password for MSSQL user
$database="BILLING"; //Database name

$conn=mssql_connect("$location","$username","$password"); //connect to sql server

mssql_select_db($database,$conn);//select database
$res=mssql_query("select * from test"); //select table
while($row=mssql_fetch_array($res)) { //fetch each row
echo $row["id"]." ".$row["avalue"];//print field id and value
}
include_once("includes/config.php");
?>
