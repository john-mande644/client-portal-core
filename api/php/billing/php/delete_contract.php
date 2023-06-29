<?php
include_once("includes/base.php");
include_once("includes/contract_record.php");

$clientID = $_GET["clientID"];
$contractID = $_GET["contractID"];

if ($contractID) {
    $result = ContractRecord::delete($contractID);

    if (!$result) {
        // TODO: handle failure
    } else {
        header("Location: client.php?clientID=$clientID");
    }
}
?>

