<?php
include_once("includes/base.php");
include_once("includes/billable_unit_record.php");

$billableUnitID = $_GET["billableUnitID"];

if ($billableUnitID) {
    $result = BillableUnitRecord::delete($billableUnitID);

    if (!$result) {
        // TODO: handle failure
    } else {
        header("Location: billable_units.php");
    }
}
?>