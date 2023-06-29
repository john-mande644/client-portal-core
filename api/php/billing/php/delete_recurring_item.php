<?php
include_once("includes/base.php");
include_once("includes/recurring_item_record.php");

$clientID = $_GET["clientID"];
$contractID = $_GET["contractID"];
$recurringItemID = $_GET["recurringItemID"];

if ($recurringItemID) {
    $result = RecurringItemRecord::purge($recurringItemID);

    if (!$result) {
        // TODO: handle failure
    } else {
        header("Location: recurring_items.php?clientID=$clientID&contractID=$contractID");
    }
}
?>