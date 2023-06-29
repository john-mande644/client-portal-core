<?php
include_once("includes/base.php");
include_once("includes/client_record.php");
include_once("includes/contract_record.php");
include_once("includes/recurring_item_record.php");
include_once("includes/pricing_rule_record.php");
include_once("includes/department_minimum_record.php");
include_once("includes/clone_contract.php");

////////////////////////////////////////////////////////////////////////////////
// Utillity functions

function is_valid_date_range($startDate, $endDate) {
    // Returns false if the start date is greater than or equal to the end date
    if (!$endDate) { return true; }

    $startDateArr = split('/', $startDate);
    $startDateInt = mktime(0, 0, 0, $startDateArr[0], $startDateArr[1], $startDateArr[2]);

    $endDateArr = split('/', $endDate);
    $endDateInt = mktime(0, 0, 0, $endDateArr[0], $endDateArr[1], $endDateArr[2]);

    return ($startDateInt < $endDateInt);
}

function is_valid_start_date($clientID, $contractID, $invoiceInterval, $startDate, &$reason) {
    $startDateArr = split('/', $startDate);
    $startDateMonth = $startDateArr[0];
    $startDateDay = $startDateArr[1];
    $startDateYear = $startDateArr[2];

    $startDateInt = mktime(0, 0, 0, $startDateMonth, $startDateDay, $startDateYear);

    $greatestEndDateInt = 0;

    $contractRecords = ContractRecord::findByClientID($clientID);
    foreach($contractRecords as $contractRecord) {
        $id = $contractRecord->getID();

        if ($id != $contractID) {
            $name = $contractRecord->getName();
            $endDate = $contractRecord->getEndDate();

            if ($endDate) {
                $endDateArr = split('-', $endDate);
                $endDateYear = $endDateArr[0];
                $endDateMonth = $endDateArr[1];
                $endDateDay = $endDateArr[2];

                $endDateInt = mktime(0, 0, 0, $endDateMonth, $endDateDay, $endDateYear);

                if ($endDateInt > $greatestEndDateInt) {
                    $greatestEndDateInt = $endDateInt;
                    $greatestEndDate = $endDate;
                    $greatestEndDateContractName = $name;
                }

                if ($endDateInt > $startDateInt) {
                    $reason = "$name has a specified end date of " . format_as_display_date($endDate) . ",";
                    $reason .= " which is more recent than the start date your entered.";

                    return false;
                }
            } else {
                $reason = "$name has no specified end date.";
                return false;
            }
        }
    }

    // Check that there are no uncovered dates
    if ($greatestEndDateInt > 0) {
        $dateDiffArray = date_diff($greatestEndDateInt, $startDateInt);

        $dayDiff = $dateDiffArray['days'];

        if ($dayDiff > 1) {
            $reason = "There must be no uncovered dates between contracts.";
            $reason .= " $greatestEndDateContractName has a specified end date of ";
            $reason .= format_as_display_date($greatestEndDate) . ",";
            $reason .=" which is $dayDiff day(s) before the start date your entered.";
            return false;
        }
    }

    return true;
}

function is_valid_end_date($invoiceInterval, $endDate, &$reason) {
    if (!$endDate) { return true; }

    $endDateParts = split('/', $endDate);
    $endDateMonth = $endDateParts[0];
    $endDateDay = $endDateParts[1];
    $endDateYear = $endDateParts[2];

    $endDateArray = getdate(mktime(0, 0, 0, $endDateMonth, $endDateDay, $endDateYear));

    switch ($invoiceInterval) {
        case WEEKLY_INVOICE_INTERVAL:
            if ($endDateArray["wday"] != 6) { // Saturday
                switch ($endDateArray["wday"]) {
                    case 0: $endDateDayOfWeek = "Sunday"; break;
                    case 1: $endDateDayOfWeek = "Monday"; break;
                    case 2: $endDateDayOfWeek = "Tuesday"; break;
                    case 3: $endDateDayOfWeek = "Wednesday"; break;
                    case 4: $endDateDayOfWeek = "Thursday"; break;
                    case 5: $endDateDayOfWeek = "Friday"; break;
                    case 6: $endDateDayOfWeek = "Saturday"; break;
                    default: $endDateDayOfWeek = "";
                }

                $reason = "The end date for a weekly invoice interval must fall on a Saturday.";
                $reason .= " The end date you entered falls on a $endDateDayOfWeek.";
                return false;
            }
            break;

        case MONTHLY_INVOICE_INTERVAL:
            // Find the last day of the month
            $dateArray = getdate(mktime(0, 0, 0, $endDateMonth + 1, 0, $endDateYear));
            $lastDayOfMonth = $dateArray["mon"] . "/" . $dateArray["mday"] . "/" . $dateArray["year"];

            if ($lastDayOfMonth["mday"] != $endDateDay) {
                $reason = "The end date for a monthly invoice interval must fall on the last day of the month you specified,";
                $reason .= " which is $lastDayOfMonth.";
                return false;
            }
            break;

        default:
            $reason = "Unknown invoice interval.";
            return false;
            break;
    }

    return true;
}

////////////////////////////////////////////////////////////////////////////////

$submit = $_POST['submit'];

// Returns true if the str starts with substr
function begins_with($str, $substr) {
    $n = strlen($substr);
    return (strncasecmp($str, $substr, $n) == 0);
}

if ($submit) {
    // Retrieve form fields
    $contractID = $_POST['contractID'];
    $clientID = $_POST['clientID'];
    $name = trim($_POST['name']);
    $startDate = trim($_POST['startDate']);
    $endDate = trim($_POST['endDate']);
    $active = ($_POST['active'] == "on" ? true : false);
    $invoiceInterval = $_POST['invoiceInterval'];
    $clonedContractID = $_POST['clonedContractID'];

    // Extract department minimums and ids
    $departmentMinimums = array();
    $prefix = "departmentID_";
    foreach($_POST as $key => $value) {
        if (begins_with($key, $prefix)) {
            $startPos = strlen($prefix);
            $departmentIDAndDepartmentMinimumID = substr($key, $startPos);

            $ids = explode("-", $departmentIDAndDepartmentMinimumID);
            $departmentID = intval($ids[0]);
            $departmentMinimumID = intval($ids[1]);

            $departmentMinimums[] = array('departmentID'=>$departmentID,
                                          'departmentMinimumID'=>$departmentMinimumID,
                                          'amount'=>$value);
        }
    }

    // Validate fields     
    $isValid = true;

    if ($name == "") {
        $errorMessage = "Oops, you need to enter a name for the contract.";
        $isValid = false;
    } else if (!is_valid_date($startDate)) {
        $errorMessage = "Oops, you entered an invalid start date.";
        $isValid = false;
    } else if (!is_valid_date_range($startDate, $endDate)) {
        $errorMessage = "Oops, the start date is greater than the end date.";
        $isValid = false;
    } else if (!is_valid_start_date($clientID, $contractID, $invoiceInterval, $startDate, &$reason)) {
        $errorMessage = "Oops, you entered an invalid start date. " . $reason;
        $isValid = false;        
    } else if (!is_valid_end_date($invoiceInterval, $endDate, &$reason)) {
        $errorMessage = "Oops, you entered an invalid end date. " . $reason;
        $isValid = false;        
    } else if ($endDate != "" && !is_valid_date($endDate)) {
        $errorMessage = "Oops, you entered an invalid end date.";
        $isValid = false;
    }

    if ($isValid) {
        // Create and populate contract record
        $contractRecord = new ContractRecord();

        $contractRecord->setClientID($clientID);
        $contractRecord->setName($name);
        $contractRecord->setStartDate($startDate);
        $contractRecord->setEndDate($endDate);
        $contractRecord->setActive($active);
        $contractRecord->setInvoiceInterval($invoiceInterval);

        if ($contractID) {
            // Update contract record
            $contractRecord->setID($contractID);
            $contractRecord->update();

            // Insert department minimum records
            foreach($departmentMinimums as $departmentMinimum) {
                $departmentMinimumRecord = new DepartmentMinimumRecord();

                $departmentID = $departmentMinimum['departmentID'];
                $departmentMinimumID = $departmentMinimum['departmentMinimumID'];
                $amount = $departmentMinimum['amount'];

                $departmentMinimumRecord->setContractID($contractID);
                $departmentMinimumRecord->setDepartmentID($departmentID);
                $departmentMinimumRecord->setAmount($amount);

                if ($departmentMinimumID > 0) {
                    $departmentMinimumRecord->setID($departmentMinimumID);
                    $result = $departmentMinimumRecord->update();
                } else {
                    $result = $departmentMinimumRecord->insert();
                }
            }
        } else {
            // Insert contract record
            $contractID = $contractRecord->insert();

            // Clone the contract's recurring items, pricing rules and exceptions
            if ($clonedContractID) {
                clone_contract($clonedContractID, $contractID);
            }

            // Insert department minimum records
            foreach($departmentMinimums as $departmentMinimum) {
                $departmentID = $departmentMinimum['departmentID'];
                $amount = $departmentMinimum['amount'];

                $departmentMinimumRecord = new DepartmentMinimumRecord();

                $departmentMinimumRecord->setContractID($contractID);
                $departmentMinimumRecord->setDepartmentID($departmentID);
                $departmentMinimumRecord->setAmount($amount);
                $departmentMinimumRecord->insert();
            }
        }


        header("Location: client.php?clientID=$clientID");
    }
} else {
    $clientID = $_GET['clientID'];
    $contractID = $_GET['contractID'];

    $contractRecord = new ContractRecord();
    $contractRecord->findByID($contractID);

    $name = $contractRecord->getName();
    $clientName = $contractRecord->getClientName();
    $startDate = $contractRecord->getStartDate();
    $endDate = $contractRecord->getEndDate();
    $active = $contractRecord->getActive();
    $invoiceInterval = $contractRecord->getInvoiceInterval();
}

// Display the form
$template = new XiTemplate("templates/contract_form.html");

if (!$clientName) {
    $clientRecord = new ClientRecord();
    $clientRecord->findByID($clientID);
    $clientName = $clientRecord->getName();
}


$template->assign("SECTION_TITLE", $clientName);

// Assign section tab links
$template->assign("CLIENT_CONTRACTS_URL", "client.php?clientID=$clientID");
$template->assign("CLIENT_INVOICES_URL", "client_invoices.php?clientID=$clientID");
$template->assign("CLIENT_BILLABLE_EVENTS_URL", "client_billable_events.php?clientID=$clientID");
$template->assign("CLIENT_PRICED_EVENTS_URL", "priced_events.php?clientID=$clientID");


if ($contractID) {
    // Assign sub section tab links
    $template->assign("CONTRACT_RECURRING_ITEMS_URL", "recurring_items.php?clientID=$clientID&contractID=$contractID");
    $template->assign("CONTRACT_PRICING_RULES_URL", "pricing_rules.php?clientID=$clientID&contractID=$contractID");

    $template->parse("main.section_sub_tabs");
}


$template->assign("CANCEL_URL", "client.php?clientID=$clientID");

if ($contractID) {
    $template->assign("PAGE_TITLE", "Edit Contract");
} else {
    $template->assign("PAGE_TITLE", "Add Contract");
}

// Display error message if necessary
if ($errorMessage) {
    $template->assign("ERROR_MESSAGE", $errorMessage);
    $template->parse("main.error_message");
}

$template->assign("CONTRACT_ID", $contractID);
$template->assign("CLIENT_ID", $clientID);
$template->assign("NAME", $name);
$template->assign("START_DATE", $startDate);
$template->assign("END_DATE", $endDate);

// Populate department minimums
$departmentMinimumRecords = DepartmentMinimumRecord::findByContractID($contractID);
foreach($departmentMinimumRecords as $departmentMinimumRecord) {

    if ($departmentMinimumRecord->getID()) {
        $departmentMinimumID = $departmentMinimumRecord->getID();
    } else {
        $departmentMinimumID = 0;
    }

    $departmentIDAndDepartmentMinimumID = $departmentMinimumRecord->getDepartmentID() . "-" .
                                          $departmentMinimumID;

    $template->assign("DEPARTMENT_ID_AND_DEPARTMENT_MINIMUM_ID", $departmentIDAndDepartmentMinimumID);
    $template->assign("DEPARTMENT_NAME", $departmentMinimumRecord->getDepartmentName());

    $minumumAmount = $departmentMinimumRecord->getAmount() ? $departmentMinimumRecord->getAmount() : 0.00;


    $template->assign("DEPARTMENT_MINIMUM_AMOUNT", number_format($minumumAmount, 2));

    $template->parse("main.department_minimums");
}


$template->assign("ACTIVE", ($active ? "checked" : ""));

switch ($invoiceInterval) {
    case 0:
        $template->assign("WEEKLY_INVOICE_INTERVAL", "checked");
        break;
    case 1:
        $template->assign("MONTHLY_INVOICE_INTERVAL", "checked");
        break;
}

if (!$contractID) {
    $contractRecords = ContractRecord::findByClientID($clientID);

    if (is_array($contractRecords) && count($contractRecords) > 0) {
        foreach($contractRecords as $contractRecord) {
            $template->assign("LIST_EXISTING_CONTRACT_ID", $contractRecord->getID());
            $template->assign("LIST_EXISTING_CONTRACT_NAME", $contractRecord->getName());

            $template->parse("main.clone_contract.existing_contracts");
        }

        $template->parse("main.clone_contract");
    }
}


$template->parse("main");

write_header();
$template->out("main");
write_footer();

?>