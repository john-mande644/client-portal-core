<?php
include_once("includes/base.php");
include_once("includes/client_record.php");
include_once("includes/billable_event_record.php");

$clientID = $_GET['clientID'];

$template = new XiTemplate("templates/client_billable_events.html");

$clientRecord = new ClientRecord();
$clientRecord->findByID($clientID);

$sectionTitle = $clientRecord->getName();
$template->assign("SECTION_TITLE", $sectionTitle);

$template->assign("CLIENT_CONTRACTS_URL", "client.php?clientID=$clientID");
$template->assign("CLIENT_INVOICES_URL", "client_invoices.php?clientID=$clientID");
$template->assign("CLIENT_BILLABLE_EVENTS_URL", "client_billable_events.php?clientID=$clientID");
$template->assign("PRICED_EVENTS_URL", "priced_events.php?clientID=$clientID");

$template->assign("PAGE_TITLE", "Billable Events");

// Populate billable events list
$billableEventRecords = BillableEventRecord::findAdHocByClientID($clientID);
if (is_array($billableEventRecords) && count($billableEventRecords) > 0) {
    foreach($billableEventRecords as $billableEventRecord) {

        $billableEventURL = "billable_event_form.php?clientID=$clientID&billableEventID=" . $billableEventRecord->getID();
        $deleteBillableEventURL = "delete_billable_event.php?clientID=$clientID&billableEventID=" . $billableEventRecord->getID();

        if ($billableEventRecord->getExceptionField1Value()) {
            $exceptionField1Value = $billableEventRecord->getExceptionField1Value();
        } else {
            $exceptionField1Value = "&nbsp;";
        }

        if ($billableEventRecord->getExceptionField2Value()) {
            $exceptionField2Value = $billableEventRecord->getExceptionField2Value();
        } else {
            $exceptionField2Value = "&nbsp;";
        }

        $template->assign("ID", $billableEventRecord->getID());
        $template->assign("BILLABLE_EVENT_URL", $billableEventURL);
        $template->assign("DELETE_BILLABLE_EVENT_URL", $deleteBillableEventURL);
        $template->assign("BILLABLE_TYPE_NAME", $billableEventRecord->getBillableTypeName());
        $template->assign("EVENT_DATE", $billableEventRecord->getEventDate());
        $template->assign("UNITS", $billableEventRecord->getUnits());
        $template->assign("EXCEPTION_FIELD1_VALUE", $exceptionField1Value);
        $template->assign("EXCEPTION_FIELD2_VALUE", $exceptionField2Value);

        $template->parse("main.billableEvents");
    }
} else {
    $template->parse("main.noBillableEvents");
}

$template->assign("ADD_BILLABLE_EVENT_URL", "billable_event_form.php?clientID=$clientID");

$template->parse("main");

write_header();
$template->out("main");
write_footer();
?>