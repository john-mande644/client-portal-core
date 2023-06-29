<?php
include_once("includes/base.php");
include_once("includes/pricing_rule_record.php");

$clientID = $_GET["clientID"];
$contractID = $_GET["contractID"];
$pricingRuleID = $_GET["pricingRuleID"];

if ($pricingRuleID) {
    $result = PricingRuleRecord::delete($pricingRuleID);

    if (!$result) {
        // TODO: handle failure
    } else {
        header("Location: pricing_rules.php?clientID=$client&contractID=$contractID");
    }
}
?>