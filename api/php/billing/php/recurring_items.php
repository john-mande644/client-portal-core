<?php
include_once("includes/base.php");
include_once("includes/contract_record.php");
include_once("includes/recurring_item_record.php");
include_once("includes/gl_account_record.php");
include_once("includes/department_record.php");
include_once("includes/location_record.php");

$clientID = $_GET['clientID'];
$contractID = $_GET['contractID'];

$template = new XiTemplate("templates/recurring_items.html");

$contractRecord = new ContractRecord();
$contractRecord->findByID($contractID);

// Assign section title
$template->assign("SECTION_TITLE", $contractRecord->getClientName() . " - " . $contractRecord->getName());

// Assign section tab links
$template->assign("CLIENT_CONTRACTS_URL", "client.php?clientID=$clientID");
$template->assign("CLIENT_INVOICES_URL", "client_invoices.php?clientID=$clientID");
$template->assign("CLIENT_BILLABLE_EVENTS_URL", "client_billable_events.php?clientID=$clientID");
$template->assign("CLIENT_PRICED_EVENTS_URL", "priced_events.php?clientID=$clientID");

// Assign sub section tab links
$template->assign("CONTRACT_GENERAL_URL", "contract_form.php?clientID=$clientID&contractID=$contractID");
$template->assign("CONTRACT_RECURRING_ITEMS_URL", "recurring_items.php?clientID=$clientID&contractID=$contractID");
$template->assign("CONTRACT_PRICING_RULES_URL", "pricing_rules.php?clientID=$clientID&contractID=$contractID");

// Assign page title
$template->assign("PAGE_TITLE", "Recurring Items");

$template->assign("ADD_RECURRING_ITEM_URL", "recurring_item_form.php?clientID=$clientID&contractID=$contractID");

// Populate recurring items list
$recurringItemRecords = RecurringItemRecord::findByContractID($contractID);
if (is_array($recurringItemRecords) && count($recurringItemRecords) > 0) {
    foreach($recurringItemRecords as $recurringItemRecord) {

        $recurringItemURL = "recurring_item_form.php?clientID=$clientID&contractID=$contractID&recurringItemID=" . $recurringItemRecord->getID();
        $deleteRecurringItemURL = "delete_recurring_item.php?clientID=$clientID&contractID=$contractID&recurringItemID=" . $recurringItemRecord->getID();

        $template->assign("ID", $recurringItemRecord->getID());
        $template->assign("RECURRING_ITEM_URL", $recurringItemURL);
        $template->assign("DELETE_RECURRING_ITEM_URL", $deleteRecurringItemURL);
        $template->assign("DISPLAY_NAME", $recurringItemRecord->getDisplayName());
        $template->assign("MONEY_VALUE", number_format($recurringItemRecord->getMoneyValue(), 2));
        $template->assign("GL_ACCOUNT_ID", $recurringItemRecord->getGLAccountID());
        $template->assign("GL_ACCOUNT_CODE", $recurringItemRecord->getGLAccountCode());
        $template->assign("DEPARTMENT_ID", $recurringItemRecord->getDepartmentID());
        $template->assign("DEPARTMENT_CODE", $recurringItemRecord->getDepartmentCode());
        $template->assign("LOCATION_ID", $recurringItemRecord->getLocationID());
        $template->assign("LOCATION_CODE", $recurringItemRecord->getLocationCode());

        $template->parse("main.recurringItems");
    }
} else {
    $template->parse("main.noRecurringItems");
}

$template->parse("main");

write_header();
$template->out("main");
write_footer();
?>