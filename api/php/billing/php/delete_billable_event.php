<?php
include_once("includes/base.php");
include_once("includes/billable_event_record.php");

$clientID = $_GET["clientID"];
$billableEventID = $_GET["billableEventID"];

if ($billableEventID ) {
    $result = BillableEventRecord::delete($billableEventID);

    if (!$result) {
        // TODO: handle failure
    } else {
        header("Location: client_billable_events.php?clientID=$clientID");
    }
}
?>