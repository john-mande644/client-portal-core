<?php
include_once("includes/base.php");
include_once("includes/contract_record.php");
include_once("includes/pricing_rule_record.php");
include_once("includes/billable_type_record.php");
include_once("includes/billable_exception_record.php");

$save = $_POST['save'];
$saveAndAddAnother = $_POST['saveAndAddAnother'];

if ($save || $saveAndAddAnother) {
    // Retrieve form fields
    $clientID = $_POST['clientID'];
    $contractID = $_POST['contractID'];
    $pricingRuleID = $_POST['pricingRuleID'];
    $billableTypeID = $_POST['billableTypeID'];
    $unitPrice = trim($_POST['unitPrice']);

    // Validate fields     
    $isValid = true;

    if ($billableTypeID == 0) {
        $errorMessage = "Oops, you forgot to select a billable type.";
        $isValid = false;
    } else if ($unitPrice == "" || floatval($unitPrice) < 0.0) {
        $errorMessage = "Oops, you need to enter a non-zero unit price.";
        $isValid = false;
    }

    if ($isValid) {
        // Create and populate record
        $pricingRuleRecord = new PricingRuleRecord();

        $pricingRuleRecord->setContractID($contractID);
        $pricingRuleRecord->setBillableTypeID($billableTypeID);
        $pricingRuleRecord->setUnitPrice($unitPrice);

        if ($pricingRuleID) {
            // Update record
            $pricingRuleRecord->setID($pricingRuleID);
            $pricingRuleRecord->update(&$sql);
        } else {
            // Insert record into database
            $pricingRuleID = $pricingRuleRecord->insert(&$sql);

            // Check to make sure insert succeeded
            if (!$pricingRuleID) {
                 // TODO: handle error
            }
        }

        if ($saveAndAddAnother) {
            header("Location: pricing_rule_form.php?clientID&$clientID&contractID=$contractID");
        } else {
            header("Location: pricing_rules.php?clientID&$clientID&contractID=$contractID");
        }
    }
} else {
    $clientID = $_GET['clientID'];
    $contractID = $_GET['contractID'];
    $pricingRuleID = $_GET['pricingRuleID'];

    $pricingRuleRecord = new PricingRuleRecord();

    if ($pricingRuleID) {
        $pricingRuleRecord->FindByID($pricingRuleID);

        $unitPrice = $pricingRuleRecord->getUnitPrice();
        $billableTypeID = $pricingRuleRecord->getBillableTypeID();
    }
}

// Display the form
$template = new XiTemplate("templates/pricing_rule_form.html");

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
if ($pricingRuleID) {
    $template->assign("PAGE_TITLE", "Edit Pricing Rule");
} else {
    $template->assign("PAGE_TITLE", "Add Pricing Rule");
}

// Display error message if necessary
if ($errorMessage) {
    $template->assign("ERROR_MESSAGE", $errorMessage);
    $template->parse("main.error_message");
}


$billableTypeRecords = BillableTypeRecord::FindAll();

// Populate billable type javascript array
foreach($billableTypeRecords as $billableTypeRecord) {
    $template->assign("JS_BILLABLE_TYPE_ID", $billableTypeRecord->getID());
    $template->assign("JS_BILLABLE_TYPE_NAME", $billableTypeRecord->getName());
    $template->assign("JS_BILLABLE_TYPE_UNIT_NAME", $billableTypeRecord->getBillableUnitName());

    $template->parse("main.jsBillableTypes");
}

// Populate billable type select list
foreach($billableTypeRecords as $billableTypeRecord) {
    $billableTypeSelected = ($billableTypeRecord->getID() == $billableTypeID ? "selected" : "");

    $template->assign("LIST_BILLABLE_TYPE_ID", $billableTypeRecord->getID());
    $template->assign("LIST_BILLABLE_TYPE_NAME", $billableTypeRecord->getName());
    $template->assign("LIST_BILLABLE_TYPE_SELECTED", $billableTypeSelected);

    $template->parse("main.billableTypes");
}

$template->assign("PRICING_RULE_ID", $pricingRuleID);
$template->assign("CLIENT_ID", $clientID);
$template->assign("CONTRACT_ID", $contractID);
$template->assign("UNIT_PRICE", number_format($unitPrice, 2));
$template->assign("BILLABLE_TYPE_ID", $billableTypeID);


if (!$pricingRuleID) {
    $template->parse("main.addAnotherButton");
}

$template->parse("main");

write_header();
$template->out("main");
write_footer();
?>