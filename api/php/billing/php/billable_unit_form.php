<?php
include_once("includes/base.php");
include_once("includes/billable_unit_record.php");

$submit = $_POST['submit'];

if ($submit) {
    // Retrieve form fields
    $billableUnitID = $_POST['billableUnitID'];
    $name = trim($_POST['name']);
    $valueType = $_POST['valueType'];

    // Validate fields     
    $isValid = true;

    if ($name == "") {
        $errorMessage = "Oops, you need to enter a name for the billable unit.";
        $isValid = false;
    }

    if ($isValid) {
        // Create and populate record
        $billableUnitRecord = new BillableUnitRecord();

        $billableUnitRecord->setName($name);
        $billableUnitRecord->setValueType($valueType);

        if ($billableUnitID) {
            // Update record
            $billableUnitRecord->setID($billableUnitID);
            $billableUnitRecord->update();
        } else {
            // Insert record into database
            $billableUnitID = $billableUnitRecord->insert();

            // Check to make sure insert succeeded
            if (!$billableUnitID) {
                 // TODO: handle error
            }
        }

        header("Location: billable_units.php");
    }
} else {
    $billableUnitID = $_GET['billableUnitID'];

    $billableUnitRecord = new BillableUnitRecord();

    if ($billableUnitID) {
        $billableUnitRecord->findByID($billableUnitID);
    }

    $name = $billableUnitRecord->getName();
    $valueType = $billableUnitRecord->getValueType();
}

// Display the form
$template = new XiTemplate("templates/billable_unit_form.html");

// Display error message if necessary
if ($errorMessage) {
    $template->assign("ERROR_MESSAGE", $errorMessage);
    $template->parse("main.error_message");
}

$template->assign("BILLABLE_UNIT_ID", $billableUnitID);
$template->assign("NAME", $name);

switch ($valueType) {
    case 0:
        $template->assign("INTEGER_VALUE_TYPE", "checked");
        break;
    case 1:
        $template->assign("DECIMAL_VALUE_TYPE", "checked");
        break;
}

$template->parse("main");

write_header();
$template->out("main");
write_footer();

?>