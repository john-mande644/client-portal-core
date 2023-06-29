<?php
include_once("includes/base.php");
include_once("includes/client_record.php");
include_once("includes/contract_record.php");
include_once("includes/pricing_rule_record.php");
include_once("includes/billable_event_record.php");
include_once("includes/utils.php");

$save = $_POST['save'];
$saveAndAddAnother = $_POST['saveAndAddAnother'];

if ($save || $saveAndAddAnother) {
    // Retrieve form fields
    $clientID = $_POST['clientID'];
    $billableEventID = $_POST['billableEventID'];
    $billableTypeID = $_POST['billableTypeID'];
    $exceptionField1Value = trim($_POST['exceptionField1Value']);
    $exceptionField2Value = trim($_POST['exceptionField2Value']);
    $units = trim($_POST['units']);
    $eventDate = trim($_POST['eventDate']);

    // Validate fields     
    $isValid = true;

    if ($billableTypeID == 0) {
        $errorMessage = "Oops, you forgot to select a billable type.";
        $isValid = false;
    } else if ($units == "" || floatval($units) == 0) {
        $errorMessage = "Oops, you need to enter a non-zero number of units.";
        $isValid = false;
    } else if (!is_valid_date($eventDate)) {
        $errorMessage = "Oops, you entered an invalid event date.";
        $isValid = false;
    } else if (!is_valid_event_date($clientID, $eventDate)) {
        $errorMessage = "The invoice has already been closed for the provided event date.";
        $isValid = false;        
    }

    if ($isValid) {
        // Create and populate record
        $billableEventRecord = new BillableEventRecord();

        $billableEventRecord->setClientID($clientID);
        $billableEventRecord->setBillableTypeID($billableTypeID);
        $billableEventRecord->setEventDate($eventDate);
        $billableEventRecord->setUnits($units);
        $billableEventRecord->setExceptionField1Value($exceptionField1Value);
        $billableEventRecord->setExceptionField2Value($exceptionField2Value);
        $billableEventRecord->setAdHoc(true);

        if ($billableEventID) {
            // Update record
            $billableEventRecord->setID($billableEventID);
            $billableEventRecord->update(&$sql);
        } else {
            // Insert record into database
            $billableEventID = $billableEventRecord->insert(&$sql);

            // Check to make sure insert succeeded
            if (!$billableEventID) {
                 // TODO: handle error
            }
        }

        if ($saveAndAddAnother) {
            header("Location: billable_event_form.php?clientID=$clientID");
        } else {
            header("Location: client_billable_events.php?clientID=$clientID");
        }
    }
} else {
    $clientID = $_GET['clientID'];
    $billableEventID = $_GET['billableEventID'];

    $billableEventRecord = new BillableEventRecord();

    if ($billableEventID) {
        $billableEventRecord->findByID($billableEventID);

        $billableTypeID = $billableEventRecord->getBillableTypeID();
        $exceptionField1Value = $billableEventRecord->getExceptionField1Value();
        $exceptionField2Value = $billableEventRecord->getExceptionField2Value();
        $units = $billableEventRecord->getUnits();
        $eventDate = $billableEventRecord->getEventDate();
    }
}


// Display the form
$template = new XiTemplate("templates/billable_event_form.html");

$clientRecord = new ClientRecord();
$clientRecord->findByID($clientID);

$template->assign("SECTION_TITLE", $clientRecord->getName());

$template->assign("CLIENT_CONTRACTS_URL", "client.php?clientID=$clientID");
$template->assign("CLIENT_INVOICES_URL", "client_invoices.php?clientID=$clientID");
$template->assign("CLIENT_BILLABLE_EVENTS_URL", "client_billable_events.php?clientID=$clientID");
$template->assign("CLIENT_PRICED_EVENTS_URL", "priced_events.php?clientID=$clientID");

if ($billableEventID) {
    $template->assign("PAGE_TITLE", "Edit Billable Event");
} else {
    $template->assign("PAGE_TITLE", "Add Billable Event");
}

// Display error message if necessary
if ($errorMessage) {
    $template->assign("ERROR_MESSAGE", $errorMessage);
    $template->parse("main.error_message");
}

// TODO: Find contractID by client ID
$contractID = 1;

$pricingRuleRecords = PricingRuleRecord::findByContractID($contractID );

// Populate billable type javascript array
foreach($pricingRuleRecords as $pricingRuleRecord) {
    $template->assign("JS_BILLABLE_TYPE_ID", $pricingRuleRecord->getBillableTypeID());
    $template->assign("JS_BILLABLE_TYPE_NAME", $pricingRuleRecord->getBillableTypeName());
    $template->assign("JS_BILLABLE_TYPE_UNIT_NAME", $pricingRuleRecord->getBillableUnitName());
    $template->assign("JS_EXCEPTION_FIELD1_NAME", $pricingRuleRecord->getExceptionField1Name());
    $template->assign("JS_EXCEPTION_FIELD2_NAME", $pricingRuleRecord->getExceptionField2Name());

    $template->parse("main.jsBillableTypes");
}

// Populate billable type select list
foreach($pricingRuleRecords as $pricingRuleRecord) {
    $billableTypeSelected = ($pricingRuleRecord->getBillableTypeID() == $billableTypeID ? "selected" : "");

    $template->assign("LIST_BILLABLE_TYPE_ID", $pricingRuleRecord->getBillableTypeID());
    $template->assign("LIST_BILLABLE_TYPE_NAME", $pricingRuleRecord->getBillableTypeName());
    $template->assign("LIST_BILLABLE_TYPE_SELECTED", $billableTypeSelected);

    $template->parse("main.billableTypes");
}

$template->assign("CLIENT_ID", $clientID);
$template->assign("BILLABLE_EVENT_ID", $billableEventID);
$template->assign("EVENT_DATE", $eventDate);
$template->assign("BILLABLE_TYPE_ID", $billableTypeID);
$template->assign("UNITS", $units);
$template->assign("EXCEPTION_FIELD1_VALUE", $exceptionField1Value);
$template->assign("EXCEPTION_FIELD2_VALUE", $exceptionField2Value);

$template->assign("CANCEL_URL", "client_billable_events.php?clientID=$clientID");

$template->parse("main");

write_header();
$template->out("main");
write_footer();
?>