<?php
include_once("includes/base.php");
include_once("includes/invoice_record.php");

$clientID = $_GET["clientID"];
$invoiceID = $_GET["invoiceID"];

if ($clientID && $invoiceID) {
    $invoiceRecord = new InvoiceRecord();
    $invoiceRecord->setID($invoiceID);
    $invoiceRecord->setClosed(true);
    $result = $invoiceRecord->update();

    if (!$result) {
        // TODO: handle failure
    } else {
        header("Location: view_invoice.php?clientID=$clientID&invoiceID=$invoiceID");
    }
}
?>