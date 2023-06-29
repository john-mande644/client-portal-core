<?php
include_once("includes/base.php");
include_once("includes/client_record.php");
include_once("includes/priced_event_record.php");

$clientID = $_GET['clientID'];

$template = new XiTemplate("templates/priced_events.html");

$clientRecord = new ClientRecord();
$clientRecord->findByID($clientID);

$sectionTitle = $clientRecord->getName();
$template->assign("SECTION_TITLE", $sectionTitle);

$template->assign("CLIENT_CONTRACTS_URL", "client.php?clientID=$clientID");
$template->assign("CLIENT_INVOICES_URL", "client_invoices.php?clientID=$clientID");
$template->assign("CLIENT_BILLABLE_EVENTS_URL", "client_billable_events.php?clientID=$clientID");
$template->assign("PRICED_EVENTS_URL", "priced_events.php?clientID=$clientID");

$template->assign("PAGE_TITLE", "Priced Events");

$template->assign("ADD_PRICED_EVENT_URL", "priced_event_form.php?clientID=$clientID");

// Populate priced events list
$pricedEventRecords = PricedEventRecord::findByClientIDAndAdHoc($clientID, true);

if (is_array($pricedEventRecords) && count($pricedEventRecords) > 0) {
    foreach($pricedEventRecords as $pricedEventRecord) {
        $pricedEventURL = "priced_event_form.php?clientID=$clientID&pricedEventID=" . $pricedEventRecord->getID();
        $deletePricedEventURL = "delete_priced_event.php?clientID=$clientID&pricedEventID=" . $pricedEventRecord->getID();

        $template->assign("PRICED_EVENT_URL", $pricedEventURL);
        $template->assign("DELETE_PRICED_EVENT_URL", $deletePricedEventURL);
        $template->assign("NAME", $pricedEventRecord->getName());
        $template->assign("EVENT_DATE", $pricedEventRecord->getEventDate());
        $template->assign("UNITS", $pricedEventRecord->getUnits());
        $template->assign("UNIT_PRICE", $pricedEventRecord->getUnitPrice());
        $template->assign("GL_ACCOUNT_CODE", $pricedEventRecord->getGLAccountCode());
        $template->assign("DEPARTMENT_CODE", $pricedEventRecord->getDepartmentCode());
        $template->assign("LOCATION_CODE", $pricedEventRecord->getLocationCode());

        $template->parse("main.pricedEvents");
    }
} else {
    $template->parse("main.noPricedEvents");
}

$template->parse("main");

write_header();
$template->out("main");
write_footer();
?>