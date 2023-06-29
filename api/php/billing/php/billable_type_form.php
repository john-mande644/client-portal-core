<?php
include_once("includes/base.php");
include_once("includes/billable_type_record.php");
include_once("includes/billable_unit_record.php");
include_once("includes/gl_account_record.php");
include_once("includes/department_record.php");
include_once("includes/location_record.php");

$submit = $_POST['submit'];

if ($submit) {
    // Retrieve form fields
    $billableTypeID = $_POST['billableTypeID'];
    $name = trim($_POST['name']);
    $description = trim($_POST['description']);
    $billableUnitID = $_POST['billableUnitID'];
    $glAccountID = $_POST['glAccountID'];
    $departmentID = $_POST['departmentID'];
    $locationID = $_POST['locationID'];

    $exceptionField1Name = trim($_POST['exception_field1_name']);
    $exceptionField1Type = $_POST['exception_field1_type'];
    $exceptionField2Name = trim($_POST['exception_field2_name']);
    $exceptionField2Type = $_POST['exception_field2_type'];

    $useExceptionField1 = $exceptionField1Name != "" ||  $exceptionField1Type != "";
    $useExceptionField2 = $exceptionField2Name != "" ||  $exceptionField2Type != "";

    // Validate fields     
    $isValid = true;

    if ($name == "") {
        $errorMessage = "Oops, you need to enter a name.";
        $isValid = false;
    } else if ($description == "") {
        $errorMessage = "Oops, you need to enter a description.";
        $isValid = false;
    } else if ($billableUnitID == 0) {
        $errorMessage = "Oops, you forgot to select a billable unit.";
        $isValid = false;
    }  else if ($glAccountID == 0) {
        $errorMessage = "Oops, you forgot to select a gl account.";
        $isValid = false;
    } else if ($departmentID == 0) {
        $errorMessage = "Oops, you forgot to select a department.";
        $isValid = false;
    } else if ($locationID == 0) {
        $errorMessage = "Oops, you forgot to select a location.";
        $isValid = false;
    } else if ($useExceptionField1 && $exceptionField1Name == "") {
        $errorMessage = "Oops, you forgot to enter a name for exception field 1.";
        $isValid = false;
    } else if ($useExceptionField1 && $exceptionField1Type == "") {
        $errorMessage = "Oops, you forgot to select a type for exception field 1.";
        $isValid = false;
    } else if ($useExceptionField2 && $exceptionField2Name == "") {
        $errorMessage = "Oops, you forgot to enter a name for exception field 2.";
        $isValid = false;
    } else if ($useExceptionField2 && $exceptionField2Type == "") {
        $errorMessage = "Oops, you forgot to select a type for exception field 2.";
        $isValid = false;
    }

    if ($isValid) {
        // Create and populate billable type record
        $billableTypeRecord = new BillableTypeRecord();

        $billableTypeRecord->setName($name);
        $billableTypeRecord->setDescription($description);
        $billableTypeRecord->setBillableUnitID($billableUnitID);
        $billableTypeRecord->setGLAccountID($glAccountID);
        $billableTypeRecord->setDepartmentID($departmentID);
        $billableTypeRecord->setLocationID($locationID);
        $billableTypeRecord->setExceptionField1Name($exceptionField1Name);
        $billableTypeRecord->setExceptionField1Type($exceptionField1Type);
        $billableTypeRecord->setExceptionField2Name($exceptionField2Name);
        $billableTypeRecord->setExceptionField2Type($exceptionField2Type);

        if ($billableTypeID) {
            // Update record
            $billableTypeRecord->setID($billableTypeID);
            $billableTypeRecord->update(&$sql);
        } else {
            // Insert record into database
            $billableTypeID = $billableTypeRecord->insert(&$sql);

            // Check to make sure insert succeeded
            if (!$billableTypeID) {
                 // TODO: handle error
            }
        }

        header("Location: billable_types.php?billableTypeID=$billableTypeID");
    }
} else {
    $billableTypeID = $_GET['billableTypeID'];

    $billableTypeRecord = new BillableTypeRecord();

    if ($billableTypeID) {
        $billableTypeRecord->FindByID($billableTypeID);

        $name = $billableTypeRecord->getName();
        $description = $billableTypeRecord->getDescription();
        $billableUnitID = $billableTypeRecord->getBillableUnitID();
        $glAccountID = $billableTypeRecord->getGLAccountID();
        $departmentID = $billableTypeRecord->getDepartmentID();
        $locationID = $billableTypeRecord->getLocationID();

        $exceptionField1Name = $billableTypeRecord->getExceptionField1Name();
        $exceptionField1Type = $billableTypeRecord->getExceptionField1Type();
        $useExceptionField1 = ($exceptionField1Name != "" ? true : false);

        $exceptionField2Name = $billableTypeRecord->getExceptionField2Name();
        $exceptionField2Type = $billableTypeRecord->getExceptionField2Type();
        $useExceptionField2 = ($exceptionField2Name != "" ? true : false);
    }
}

// Display the form
$template = new XiTemplate("templates/billable_type_form.html");

// Display error message if necessary
if ($errorMessage) {
    $template->assign("ERROR_MESSAGE", $errorMessage);
    $template->parse("main.error_message");
}

// Populate billable type select list
$billableUnitRecords = BillableUnitRecord::FindAll();
foreach($billableUnitRecords as $billableUnitRecord) {
    $billableUnitSelected = ($billableUnitRecord->getID() == $billableUnitID ? "selected" : "");

    $template->assign("LIST_BILLABLE_UNIT_ID", $billableUnitRecord->getID());
    $template->assign("LIST_BILLABLE_UNIT_NAME", $billableUnitRecord->getName());
    $template->assign("LIST_BILLABLE_UNIT_SELECTED", $billableUnitSelected);

    $template->parse("main.billableUnits");
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

$template->assign("BILLABLE_TYPE_ID", $billableTypeID);
$template->assign("NAME", $name);
$template->assign("DESCRIPTION", $description);
$template->assign("BILLABLE_UNIT_ID", $billableUnitID);
$template->assign("GL_ACCOUNT_ID", $glAccountID);
$template->assign("DEPARTMENT_ID", $departmentID);
$template->assign("LOCATION_ID", $locationID);

$template->assign("EXCEPTION_FIELD1_NAME", $exceptionField1Name);

$exceptionField1TypeIntegerSelected = $exceptionField1Type == "integer" ? " selected" : "";
$exceptionField1TypeDecimalSelected = $exceptionField1Type == "decimal" ? " selected" : "";
$exceptionField1TypeStringSelected = $exceptionField1Type == "string" ? " selected" : "";

$template->assign("EXCEPTION_FIELD1_TYPE_INTEGER_SELECTED", $exceptionField1TypeIntegerSelected);
$template->assign("EXCEPTION_FIELD1_TYPE_DECIMAL_SELECTED", $exceptionField1TypeDecimalSelected);
$template->assign("EXCEPTION_FIELD1_TYPE_STRING_SELECTED", $exceptionField1TypeStringSelected);

$template->assign("EXCEPTION_FIELD2_NAME", $exceptionField2Name);

$exceptionField2TypeIntegerSelected = $exceptionField2Type == "integer" ? " selected" : "";
$exceptionField2TypeDecimalSelected = $exceptionField2Type == "decimal" ? " selected" : "";
$exceptionField2TypeStringSelected = $exceptionField2Type == "string" ? " selected" : "";

$template->assign("EXCEPTION_FIELD2_TYPE_INTEGER_SELECTED", $exceptionField2TypeIntegerSelected);
$template->assign("EXCEPTION_FIELD2_TYPE_DECIMAL_SELECTED", $exceptionField2TypeDecimalSelected);
$template->assign("EXCEPTION_FIELD2_TYPE_STRING_SELECTED", $exceptionField2TypeStringSelected);

$template->parse("main");

write_header();
$template->out("main");
write_footer();
?>