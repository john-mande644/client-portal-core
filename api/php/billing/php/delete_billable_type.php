<?php
include_once("includes/base.php");
include_once("includes/billable_type_record.php");

$billableTypeID = $_GET["billableTypeID"];

if ($billableTypeID) {
    $result = BillableTypeRecord::delete($billableTypeID);

    if (!$result) {
        // TODO: handle failure
    } else {
        header("Location: billable_types.php");
    }
}
?>