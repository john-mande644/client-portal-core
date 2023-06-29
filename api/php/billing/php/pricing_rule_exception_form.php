<?php
include_once("includes/base.php");
include_once("includes/contract_record.php");
include_once("includes/pricing_rule_record.php");
include_once("includes/billable_type_record.php");
include_once("includes/billable_exception_record.php");

$pricingRuleID = $_REQUEST['pricingRuleID'];

$pricingRuleRecord = new PricingRuleRecord();
$pricingRuleRecord->findByID($pricingRuleID);

$billableTypeRecord = new BillableTypeRecord();
$billableTypeRecord->findByID($pricingRuleRecord->getBillableTypeID());

$field1Name = $billableTypeRecord->getExceptionField1Name();
$field1Type = $billableTypeRecord->getExceptionField1Type();
$field2Name = $billableTypeRecord->getExceptionField2Name();
$field2Type = $billableTypeRecord->getExceptionField2Type();
$billableUnitName = $billableTypeRecord->getBillableUnitName();


function compare_exception_values($type, $value1, $value2) {
    switch ($type) {
        case "decimal":
            return (floatval($value1) == floatval($value2));
        case "integer":
            return (intval($value1) == intval($value2));
        case "string":
            return (strcasecmp($value1, $value2) == 0);
        default:
            return false;
    }
}

function is_unique_exception($pricingRuleID, $field1Comparator, $field1Value,
                      $field2Comparator, $field2Value) {
    $records = BillableExceptionRecord::findByPricingRuleID($pricingRuleID);

    foreach($records as $record) {
        if ($record->getField1Comparator() != $field1Comparator ||
            !compare_exception_values($record->getField1Type(), $record->getField1Value(), $field1Value)) {

            continue;
        }
        if ($record->getField2Name()) {
            if ($record->getField2Comparator() != $field2Comparator ||
                !compare_exception_values($record->getField2Type(), $record->getField2Value(), $field2Value)) {

                continue;
            }
        }

        return false;
    }

    return true;
}


$save = $_POST['save'];
$saveAndAddAnother = $_POST['saveAndAddAnother'];

if ($save || $saveAndAddAnother) {
    // Retrieve form fields
    $clientID = $_POST['clientID'];
    $contractID = $_POST['contractID'];
    $pricingRuleExceptionID = $_POST['pricingRuleExceptionID'];
    $field1Comparator = trim($_POST['field1Comparator']);
    $field1Value = trim($_POST['field1Value']);
    $field2Comparator = trim($_POST['field2Comparator']);
    $field2Value = trim($_POST['field2Value']);
    $price = floatval(trim($_POST['price']));

    // Validate fields
    $isValid = true;


    if ($field1Comparator == "") {
        $errorMessage = "Oops, you need to select a comparison type for  $field1Name.";
        $isValid = false;
    } else if ($field1Comparator != "any" && $field1Value == "") {
        $errorMessage = "Oops, you forgot to enter a value for $field1Name.";
        $isValid = false;
    } else if ($field2Name && $field2Comparator == "") {
        $errorMessage = "Oops, you need to select a comparison type for $field2Name.";
        $isValid = false;
    } else if ($field2Name && $field2Comparator != "any" && $field2Value == "") {
        $errorMessage = "Oops, you forgot to enter a value for $field2Name.";
        $isValid = false;
    }  else if ($price < 0.0) {
        $errorMessage = "Oops, you need to enter a non-negative price.";
        $isValid = false;
    }  else if (!is_unique_exception($pricingRuleID, $field1Comparator, $field1Value,
                            $field2Comparator, $field2Value)) {
        $errorMessage = "Oops, the exception you attempted to create aready exists.";
        $isValid = false;
    }


    if ($isValid) {
        // Create and populate record
        $billableExceptionRecord = new BillableExceptionRecord();

        $billableExceptionRecord->setPricingRuleID($pricingRuleID);
        $billableExceptionRecord->setField1Comparator($field1Comparator);
        $billableExceptionRecord->setField2Comparator($field2Comparator);
        $billableExceptionRecord->setPrice($price);

        if ($pricingRuleExceptionID) {
            // Update record
            $billableExceptionRecord->setField1Value($field1Value);
            $billableExceptionRecord->setField2Value($field2Value);
            $billableExceptionRecord->setID($pricingRuleExceptionID);
            $billableExceptionRecord->update(&$sql);
        } else {

            $separator1Pos = stripos($field1Value, "|");

            if ($separator1Pos !== false) {
                // Since we have a least one '|' in the string, that means we have
                // multiple values that we need to split out from the field value
                // string and insert into the database as individual exception records
                $field1Values = explode("|", $field1Value);
            } else {
                $billableExceptionRecord->setField1Value($field1Value);
            }


            $billableExceptionRecord->setField2Value($field2Value);



            // Insert record into database
            $pricingRuleExceptionID = $billableExceptionRecord->insert(&$sql);

            // Check to make sure insert succeeded
            if (!$$pricingRuleExceptionID) {
                 // TODO: handle error
            }
        }

        if ($saveAndAddAnother) {
            header("Location: pricing_rule_exception_form.php?clientID=$clientID&contractID=$contractID&pricingRuleID=$pricingRuleID");
        } else {
            header("Location: pricing_rules.php?clientID=$clientID&contractID=$contractID");
        }
    }
} else {
    $clientID = $_GET['clientID'];
    $contractID = $_GET['contractID'];
    $pricingRuleID = $_GET['pricingRuleID'];
    $pricingRuleExceptionID = $_GET['pricingRuleExceptionID'];

    $billableExceptionRecord = new BillableExceptionRecord();

    if ($pricingRuleExceptionID) {
        $billableExceptionRecord->findByID($pricingRuleExceptionID);

        $field1Comparator = $billableExceptionRecord->getField1Comparator();
        $field1Value = $billableExceptionRecord->getField1Value();
        $field2Comparator = $billableExceptionRecord->getField2Comparator();
        $field2Value = $billableExceptionRecord->getField2Value();
        $price = $billableExceptionRecord->getPrice();
    }
}

// Display the form
$template = new XiTemplate("templates/pricing_rule_exception_form.html");

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
if ($pricingRuleExceptionID) {
    $template->assign("PAGE_TITLE", "Pricing Rule: " . $pricingRuleRecord->getBillableTypeName() . " - Edit Exception");
} else {
    $template->assign("PAGE_TITLE", "Pricing Rule: " . $pricingRuleRecord->getBillableTypeName() . " - Add Exception");
}


// Display error message if necessary
if ($errorMessage) {
    $template->assign("ERROR_MESSAGE", $errorMessage);
    $template->parse("main.error_message");
}

$template->assign("CLIENT_ID", $clientID);
$template->assign("CONTRACT_ID", $contractID);
$template->assign("PRICING_RULE_ID", $pricingRuleID);
$template->assign("PRICING_RULE_EXCEPTION_ID", $pricingRuleExceptionID);
$template->assign("FIELD1_NAME", $field1Name);

// Populate field1 comparator select list
if ($field1Type == "string") {
    $comparators = array("any", "equals", "begins with", "ends with", "contains");
} else {
    $comparators = array("=", "<", "<=", ">", ">=");
}

foreach($comparators as $comparator) {
    $comparatorSelected = ($field1Comparator == $comparator ? "selected" : "");

    $template->assign("LIST_FIELD1_COMPARISON_TYPE", $comparator);
    $template->assign("LIST_FIELD1_COMPARISON_TYPE_SELECTED", $comparatorSelected);

    $template->parse("main.field1ComparisonTypes");
}

$template->assign("FIELD1_VALUE", $field1Value);

if ($field2Name != "") {
    $template->assign("FIELD2_NAME", $field2Name);

    // Populate field2 comparator select list
    if ($field2Type == "string") {
        $comparators = array("any", "equals", "begins with", "ends with", "contains");
    } else {
        $comparators = array("=", "<", "<=", ">", ">=");
    }

    foreach($comparators as $comparator) {
        $comparatorSelected = ($field2Comparator == $comparator ? "selected" : "");

        $template->assign("LIST_FIELD2_COMPARISON_TYPE", $comparator);
        $template->assign("LIST_FIELD2_COMPARISON_TYPE_SELECTED", $comparatorSelected);

        $template->parse("main.field2.field2ComparisonTypes");
    }

    $template->assign("FIELD2_VALUE", $field2Value);

    $template->parse("main.field2");
}

$template->assign("PRICE", number_format($price, 2));
$template->assign("BILLABLE_UNIT_NAME", $billableUnitName);

if (!$pricingRuleExceptionID) {
    $template->parse("main.addAnotherButton");
}

$template->parse("main");

write_header();
$template->out("main");
write_footer();
?>