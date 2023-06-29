<?php
include_once("includes/base.php");
include_once("includes/client_record.php");
include_once("includes/contract_record.php");
include_once("includes/utils.php");

$clientID = $_GET['clientID'];

// This ensure that the invoice for the most recent date has been created
create_invoice($clientID);

$template = new XiTemplate("templates/client.html");

if ($clientID) {
    $clientRecord = new ClientRecord();
    $clientRecord->findByID($clientID);

    $template->assign("SECTION_TITLE", $clientRecord->getName());

    $contractRecords = ContractRecord::findByClientID($clientID);
}

$template->assign("PAGE_TITLE", "Contracts");

$template->assign("CLIENT_INVOICES_URL", "client_invoices.php?clientID=$clientID");
$template->assign("CLIENT_BILLABLE_EVENTS_URL", "client_billable_events.php?clientID=$clientID");
$template->assign("CLIENT_PRICED_EVENTS_URL", "priced_events.php?clientID=$clientID");

if (is_array($contractRecords) && count($contractRecords) > 0) {
    foreach($contractRecords as $contractRecord) {

        $deleteContractURL = "delete_contract.php?clientID=" . $contractRecord->getClientID() ."&contractID=" . $contractRecord->getID();
        $contractURL = "contract_form.php?clientID=" . $contractRecord->getClientID() ."&contractID=" . $contractRecord->getID();
        $clientURL = "client_form.php?clientID=" . $contractRecord->getClientID();

        $endDate = $contractRecord->getEndDate() ? $contractRecord->getEndDate() : "-";

        $active = ($contractRecord->getActive() == 1 ? "Yes" : "No");
        $InvoiceInterval = ($contractRecord->getInvoiceInterval() == 0 ? "Weekly" : "Monthly");

        $template->assign("CLIENT_NAME", $contractRecord->getClientName());
        $template->assign("CLIENT_ID", $contractRecord->getClientID());
        $template->assign("ID", $contractRecord->getID());
        $template->assign("DELETE_CONTRACT_URL", $deleteContractURL);
        $template->assign("CONTRACT_URL", $contractURL);
        $template->assign("CLIENT_URL", $clientURL);
        $template->assign("CONTRACT_NAME", $contractRecord->getName());
        $template->assign("START_DATE", $contractRecord->getStartDate());
        $template->assign("END_DATE", $endDate);
        $template->assign("ACTIVE", $active);
        $template->assign("INVOICE_INTERVAL", $InvoiceInterval);

        $template->parse("main.contracts");
    }
} else {
    $template->parse("main.no_contracts");
}

$template->assign("ADD_CONTRACT_URL", "contract_form.php?clientID=$clientID");

$template->parse("main");

write_header();
$template->out("main");
write_footer();
?>