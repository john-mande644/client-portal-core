<?php
include_once("includes/base.php");
include_once("includes/client_record.php");
include_once("includes/priced_event_record.php");
include_once("includes/gl_account_record.php");
include_once("includes/department_record.php");
include_once("includes/location_record.php");
include_once("includes/utils.php");

$save = $_POST['save'];
$saveAndAddAnother = $_POST['saveAndAddAnother'];

if ($save || $saveAndAddAnother) {
    // Retrieve form fields
    $clientID = $_POST['clientID'];
    $pricedEventID = $_POST['pricedEventID'];
    $name = trim($_POST['name']);
    $units = trim($_POST['units']);
    $unitPrice = trim($_POST['unitPrice']);
    $eventDate = trim($_POST['eventDate']);
    $glAccountID = $_POST['glAccountID'];
    $departmentID = $_POST['departmentID'];
    $locationID = $_POST['locationID'];

    // Validate fields     
    $isValid = true;

    if ($name == "") {
        $errorMessage = "Oops, you forgot to enter a name.";
        $isValid = false;
    } else if ($units == "" || floatval($units) == 0) {
        $errorMessage = "Oops, you need to enter a non-zero number of units.";
        $isValid = false;
    } else if ($unitPrice == "" || floatval($unitPrice) <= 0.0) {
        $errorMessage = "Oops, you need to enter a non-zero money value for the recurring item.";
        $isValid = false;
    } else if (!is_valid_date($eventDate)) {
        $errorMessage = "Oops, you entered an invalid event date.";
        $isValid = false;
    } else if (!is_valid_event_date($clientID, $eventDate)) {
        $errorMessage = "The invoice has already been closed for the provided event date.";
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
        // Create and populate record
        $pricedEventRecord = new PricedEventRecord();

        $pricedEventRecord->setClientID($clientID);
        $pricedEventRecord->setName($name);
        $pricedEventRecord->setEventDate(format_as_mysql_date($eventDate));
        $pricedEventRecord->setUnits($units);
        $pricedEventRecord->setUnitPrice($unitPrice);
        $pricedEventRecord->setGLAccountID($glAccountID);
        $pricedEventRecord->setDepartmentID($departmentID);
        $pricedEventRecord->setLocationID($locationID);
        $pricedEventRecord->setAdHoc(true);

        if ($pricedEventID) {
            // Update record
            $pricedEventRecord->setID($pricedEventID);
            $pricedEventRecord->update(&$sql);
        } else {
            // Insert record into database
            $pricedEventID = $pricedEventRecord->insert(&$sql);

            // Check to make sure insert succeeded
            if (!$pricedEventID) {
                 // TODO: handle error
            }
        }

        if ($saveAndAddAnother) {
            header("Location: priced_event_form.php?clientID=$clientID");
        } else {
            header("Location: priced_events.php?clientID=$clientID");
        }
    }
} else {
    $clientID = $_GET['clientID'];
    $pricedEventID = $_GET['pricedEventID'];

    $pricedEventRecord = new PricedEventRecord();

    if ($pricedEventID) {
        $pricedEventRecord->findByID($pricedEventID);

        $name = $pricedEventRecord->getName();
        $eventDate = $pricedEventRecord->getEventDate();
        $units = $pricedEventRecord->getUnits();
        $unitPrice = $pricedEventRecord->getUnitPrice();
        $glAccountID = $pricedEventRecord->getGLAccountID();
        $departmentID = $pricedEventRecord->getDepartmentID();
        $locationID = $pricedEventRecord->getLocationID();
    }
}


// Display the form
$template = new XiTemplate("templates/priced_event_form.html");

$clientRecord = new ClientRecord();
$clientRecord->findByID($clientID);

$sectionTitle = $clientRecord->getName();
$template->assign("SECTION_TITLE", $sectionTitle);

$template->assign("CLIENT_CONTRACTS_URL", "client.php?clientID=$clientID");
$template->assign("CLIENT_INVOICES_URL", "client_invoices.php?clientID=$clientID");
$template->assign("CLIENT_BILLABLE_EVENTS_URL", "client_billable_events.php?clientID=$clientID");
$template->assign("PRICED_EVENTS_URL", "priced_events.php?clientID=$clientID");

if ($pricedEventID) {
    $template->assign("PAGE_TITLE", "Edit Priced Event");
} else {
    $template->assign("PAGE_TITLE", "Add Priced Event");
}

// Display error message if necessary
if ($errorMessage) {
    $template->assign("ERROR_MESSAGE", $errorMessage);
    $template->parse("main.error_message");
}

$clientURL = "client.php?clientID=$clientID";

$template->assign("CLIENT_URL", $clientURL);
$template->assign("CLIENT_ID", $clientID);
$template->assign("PRICED_EVENT_ID", $pricedEventID);
$template->assign("EVENT_DATE", $eventDate);
$template->assign("NAME", $name);
$template->assign("UNITS", $units);
$template->assign("UNIT_PRICE", $unitPrice);


// Populate gl account select list
$glAccountRecords = GLAccountRecord::FindAll();
foreach($glAccountRecords as $glAccountRecord) {
    $glAccountSelected = ($glAccountRecord->getID() == $glAccountID ? "selected" : "");

    $template->assign("LIST_GL_ACCOUNT_ID", $glAccountRecord->getID());
    $template->assign("LIST_GL_ACCOUNT_NAME", $glAccountRecord->getCode() . " - " . $glAccountRecord->getName());
    $template->assign("LIST_GL_ACCOUNT_SELECTED", $glAccountSelected);

    $template->parse("main.glAccounts");
}

// Populate department select list
$departmentRecords = DepartmentRecord::FindAll();
foreach($departmentRecords as $departmentRecord) {
    $departmentSelected = ($departmentRecord->getID() == $departmentID ? "selected" : "");

    $template->assign("LIST_DEPARTMENT_ID", $departmentRecord->getID());
    $template->assign("LIST_DEPARTMENT_NAME", $departmentRecord->getName());
    $template->assign("LIST_DEPARTMENT_SELECTED", $departmentSelected);

    $template->parse("main.departments");
}

// Populate location select list
$locationRecords = LocationRecord::FindAll();
foreach($locationRecords as $locationRecord) {
    $locationSelected = ($locationRecord->getID() == $locationID ? "selected" : "");

    $template->assign("LIST_LOCATION_ID", $locationRecord->getID());
    $template->assign("LIST_LOCATION_NAME", $locationRecord->getName());
    $template->assign("LIST_LOCATION_SELECTED", $locationSelected);

    $template->parse("main.locations");
}

$template->assign("CANCEL_URL", "priced_events.php?clientID=$clientID");

$template->parse("main");

write_header();
$template->out("main");
write_footer();
?>