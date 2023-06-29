<?php
include_once("includes/base.php");
include_once("includes/billable_event_record.php");

$template = new XiTemplate("templates/billable_events.html");

$billableEvents = BillableEventRecord::findAll();

if (is_array($billableEvents) && count($billableEvents) > 0) {
    foreach($billableEvents as $billableEvent) {
        $deleteBillableEventURL = "delete_billable_event.php?billableEventID=" . $billableEvent->getID();

        $template->assign("DELETE_BILLABLE_EVENT_URL", $deleteBillableEventURL);
        $template->assign("BILLABLE_EVENT_ID", $billableEvent->getID());
        $template->assign("CLIENT_NAME", $billableEvent->getClientName());
        $template->assign("BILLABLE_TYPE_NAME", $billableEvent->getBillableTypeName());
        $template->assign("EVENT_DATE", $billableEvent->getEventDate());
        $template->assign("UNITS", $billableEvent->getUnits());
        $template->assign("UNIT_PRICE", number_format($billableEvent->getUnitPrice(), 2));
        $template->assign("TOTAL_PRICE", number_format($billableEvent->getTotalPrice(), 2));

        $template->parse("main.billable_events");
    }
} else {
    $template->parse("main.no_billable_events");
}

$template->parse("main");

write_header();
$template->out("main");
write_footer();
?>