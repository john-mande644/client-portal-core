<?php
include_once("includes/base.php");
include_once("includes/invoice_record.php");

$clientID = $_GET["clientID"];
$invoiceID = $_GET["invoiceID"];

if ($invoiceID ) {
    $result = InvoiceRecord::delete($invoiceID);

    if (!$result) {
        // TODO: handle failure
    } else {
        header("Location: client_invoices.php?clientID=$clientID");
    }
}
?>