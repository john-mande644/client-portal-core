<?php
include_once("includes/base.php");
include_once("includes/contract_record.php");
include_once("includes/recurring_item_record.php");
include_once("includes/gl_account_record.php");
include_once("includes/department_record.php");
include_once("includes/location_record.php");

$save = $_POST['save'];
$saveAndAddAnother = $_POST['saveAndAddAnother'];

if ($save || $saveAndAddAnother) {
    // Retrieve form fields
    $recurringItemID = $_POST['recurringItemID'];
    $clientID = $_POST['clientID'];
    $contractID = $_POST['contractID'];
    $displayName = trim($_POST['displayName']);
    $moneyValue = trim($_POST['moneyValue']);
    $glAccountID = $_POST['glAccountID'];
    $departmentID = $_POST['departmentID'];
    $locationID = $_POST['locationID'];

    // Validate fields     
    $isValid = true;

    if ($displayName == "") {
        $errorMessage = "Oops, you need to enter a display name for the recurring item.";
        $isValid = false;
    } else if ($moneyValue == "" || floatval($moneyValue) <= 0.0) {
        $errorMessage = "Oops, you need to enter a non-zero money value for the recurring item.";
        $isValid = false;
    } else if ($glAccountID == 0) {
        $errorMessage = "Oops, you forgot to select a gl account.";
        $isValid = false;
    } else if ($departmentID == 0) {
        $errorMessage = "Oops, you forgot to select a department.";
        $isValid = false;
    } else if ($locationID == 0) {
        $errorMessage = "Oops, you forgot to select a location.";
        $isValid = false;
    }

    if ($isValid) {
        // Create and populate contract record
        $recurringItemRecord = new RecurringItemRecord();

        $recurringItemRecord->setContractID($contractID);
        $recurringItemRecord->setDisplayName($displayName);
        $recurringItemRecord->setMoneyValue($moneyValue);
        $recurringItemRecord->setGLAccountID($glAccountID);
        $recurringItemRecord->setDepartmentID($departmentID);
        $recurringItemRecord->setLocationID($locationID);

        if ($recurringItemID) {
            // Update record
            $recurringItemRecord->setID($recurringItemID);
            $recurringItemRecord->update(&$sql);
        } else {
            // Insert record into database
            $recurringItemID = $recurringItemRecord->insert(&$sql);

            // Check to make sure insert succeeded
            if (!$recurringItemID) {
                 // TODO: handle error
            }
        }

        if ($saveAndAddAnother) {
            header("Location: recurring_item_form.php?clientID=$clientID&contractID=$contractID");
        } else {
            header("Location: recurring_items.php?clientID=$clientID&contractID=$contractID");
        }
    }
} else {
    $clientID = $_GET['clientID'];
    $contractID = $_GET['contractID'];
    $recurringItemID = $_GET['recurringItemID'];

    $recurringItemRecord = new RecurringItemRecord();

    if ($recurringItemID) {
        $recurringItemRecord->FindByID($recurringItemID);

        $displayName = $recurringItemRecord->getDisplayName();
        $moneyValue = $recurringItemRecord->getMoneyValue();
        $glAccountID = $recurringItemRecord->getGLAccountID();
        $departmentID = $recurringItemRecord->getDepartmentID();
        $locationID = $recurringItemRecord->getLocationID();
    }
}

// Display the form
$template = new XiTemplate("templates/recurring_item_form.html");

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
if ($recurringItemID) {
    $template->assign("PAGE_TITLE", "Edit Recurring Item");
} else {
    $template->assign("PAGE_TITLE", "Add Recurring Item");
}

// Display error message if necessary
if ($errorMessage) {
    $template->assign("ERROR_MESSAGE", $errorMessage);
    $template->parse("main.error_message");
}

// Populate gl account select list
$glAccountRecords = GLAccountRecord::FindAll();
foreach($glAccountRecords as $glAccountRecord) {
    $glAccountSelected = ($glAccountRecord->getID() == $glAccountID ? "selected" : "");

    $template->assign("LIST_GL_ACCOUNT_ID", $glAccountRecord->getID());
    $template->assign("LIST_GL_ACCOUNT_CODE", $glAccountRecord->getCode());
    $template->assign("LIST_GL_ACCOUNT_SELECTED", $glAccountSelected);

    $template->parse("main.glAccounts");
}

// Populate department select list
$departmentRecords = DepartmentRecord::FindAll();
foreach($departmentRecords as $departmentRecord) {
    $departmentSelected = ($departmentRecord->getID() == $departmentID ? "selected" : "");

    $template->assign("LIST_DEPARTMENT_ID", $departmentRecord->getID());
    $template->assign("LIST_DEPARTMENT_CODE", $departmentRecord->getCode());
    $template->assign("LIST_DEPARTMENT_SELECTED", $departmentSelected);

    $template->parse("main.departments");
}

// Populate location select list
$locationRecords = LocationRecord::FindAll();
foreach($locationRecords as $locationRecord) {
    $locationSelected = ($locationRecord->getID() == $locationID ? "selected" : "");

    $template->assign("LIST_LOCATION_ID", $locationRecord->getID());
    $template->assign("LIST_LOCATION_CODE", $locationRecord->getCode());
    $template->assign("LIST_LOCATION_SELECTED", $locationSelected);

    $template->parse("main.locations");
}

$template->assign("RECURRING_ITEM_ID", $recurringItemID);
$template->assign("CLIENT_ID", $clientID);
$template->assign("CONTRACT_ID", $contractID);
$template->assign("DISPLAY_NAME", $displayName);
$template->assign("MONEY_VALUE", number_format($moneyValue, 2));
$template->assign("GL_ACCOUNT_ID", $glAccountID);
$template->assign("DEPARTMENT_ID", $departmentID);
$template->assign("LOCATION_ID", $locationID);

if (!$recurringItemID) {
    $template->parse("main.addAnotherButton");
}

$template->parse("main");

write_header();
$template->out("main");
write_footer();
?>