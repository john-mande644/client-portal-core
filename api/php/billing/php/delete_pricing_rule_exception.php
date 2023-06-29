<?php
include_once("includes/base.php");
include_once("includes/billable_exception_record.php");

$clientID = $_GET["clientID"];
$contractID = $_GET["contractID"];
$pricingRuleExceptionID = $_GET["pricingRuleExceptionID"];

if ($pricingRuleExceptionID) {
    $result = BillableExceptionRecord::delete($pricingRuleExceptionID);

    if (!$result) {
        // TODO: handle failure
    } else {
        header("Location: pricing_rules.php?clientID=$clientID&contractID=$contractID");
    }
}
?>