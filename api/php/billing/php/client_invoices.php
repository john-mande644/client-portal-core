<?php
include_once("includes/base.php");
include_once("includes/client_record.php");
include_once("includes/contract_record.php");
include_once("includes/invoice_record.php");

$clientID = $_GET['clientID'];

$template = new XiTemplate("templates/client_invoices.html");

if ($clientID) {
    $clientRecord = new ClientRecord();
    $clientRecord->findByID($clientID);

    $template->assign("SECTION_TITLE", $clientRecord->getName());

    $invoiceRecords = InvoiceRecord::findByClientID($clientID);
}

$template->assign("PAGE_TITLE", "Invoices");

$template->assign("CLIENT_CONTRACTS_URL", "client.php?clientID=$clientID");
$template->assign("CLIENT_INVOICES_URL", "client_invoices.php?clientID=$clientID");
$template->assign("CLIENT_BILLABLE_EVENTS_URL", "client_billable_events.php?clientID=$clientID");
$template->assign("CLIENT_PRICED_EVENTS_URL", "priced_events.php?clientID=$clientID");

if (is_array($invoiceRecords) && count($invoiceRecords) > 0) {
    foreach($invoiceRecords as $invoiceRecord) {
        $invoiceURL = "view_invoice.php?clientID=" . $invoiceRecord->getClientID() ."&invoiceID=" . $invoiceRecord->getID();
        $deleteInvoiceURL = "delete_invoice.php?clientID=" . $invoiceRecord->getClientID() ."&invoiceID=" . $invoiceRecord->getID();
        $contractURL = "contract_form.php?clientID=" . $invoiceRecord->getClientID() ."&contractID=" . $invoiceRecord->getContractID();

        if ($invoiceRecord->getClosed() == 1) {
            $openOrClosed = '<span style="color: red">Closed</span>';
        } else {
            $openOrClosed = '<span style="color: green">Open</span>';
        }

        $template->assign("DELETE_INVOICE_URL", $deleteInvoiceURL);
        $template->assign("INVOICE_URL", $invoiceURL);
        $template->assign("INVOICE_NUMBER", $invoiceRecord->getInvoiceNumber());
        $template->assign("CONTRACT_URL", $contractURL);
        $template->assign("CONTRACT_NAME", $invoiceRecord->getContractName());
        $template->assign("START_DATE", $invoiceRecord->getStartDate());
        $template->assign("END_DATE", $invoiceRecord->getEndDate());
        $template->assign("OPEN_OR_CLOSED", $openOrClosed);

        $template->parse("main.invoices");
    }
} else {
    $template->parse("main.no_invoices");
}

$template->parse("main");

write_header();
$template->out("main");
write_footer();
?>