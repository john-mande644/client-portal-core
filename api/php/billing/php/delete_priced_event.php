<?php
include_once("includes/base.php");
include_once("includes/priced_event_record.php");

$clientID = $_GET["clientID"];
$pricedEventID = $_GET["pricedEventID"];

if ($clientID) {
    $result = PricedEventRecord::delete($pricedEventID);

    if (!$result) {
        // TODO: handle failure
    } else {
        header("Location: priced_events.php?clientID=$clientID");
    }
}
?>