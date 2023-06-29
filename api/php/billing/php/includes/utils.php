<?php
include_once("base.php");
include_once("client_record.php");
include_once("contract_record.php");
include_once("invoice_record.php");
include_once("recurring_item_record.php");
include_once("pricing_rule_record.php");
include_once("billable_exception_record.php");

function get_current_date() {
    //return getdate(mktime(0, 0, 0, 4, 30, 2009));
    return getdate();
}

function create_invoice($clientID) {
    //1. Get the current date
    $currentDateArray = get_current_date();
    $currentDate = $currentDateArray["mon"] . "/" . $currentDateArray["mday"] . "/" . $currentDateArray["year"];

    //2. Figure out the invoice interval
    $contractRecord = new ContractRecord();
    $contractRecord->findByClientIDAndDate($clientID, format_as_mysql_date($currentDate));

    //3. If no contract has been defined to just exit
    if (!$contractRecord->getID()) { return; }

    $invoiceInterval = $contractRecord->getInvoiceInterval();

    //4. Construct the start date of the billing period (MySQL) format
    if ($invoiceInterval == 1) {
        // Find the first day of the month
        $startDate = $currentDateArray["mon"] . "/1/" . $currentDateArray["year"];
    } else {
        // Find the preceeding Saturday
        $dayOfWeek = $currentDateArray["wday"];
        $daysBack = $dayOfWeek;

        $startDateArray = getdate(mktime(0, 0, 0, $currentDateArray["mon"],
                                ($currentDateArray["mday"] - $daysBack), $currentDateArray["year"]));
        $startDate = $startDateArray["mon"] . "/" . $startDateArray["mday"] . "/" . $startDateArray["year"];
    }

    //5. Construct the end date of the billing period (MySQL) format
    if ($invoiceInterval == 1) {
        // Find the last day of the month
        $endDateArray = getdate(mktime(0, 0, 0, $currentDateArray["mon"] + 1, 0, $currentDateArray["year"]));
        $endDate = $endDateArray["mon"] . "/" . $endDateArray["mday"] . "/" . $endDateArray["year"];
    } else {
        // Find the next Saturday
        $dayOfWeek = $currentDateArray["wday"];
        $daysForward = 6 - $dayOfWeek;

        $endDateArray = getdate(mktime(0, 0, 0, $currentDateArray["mon"],
                                ($currentDateArray["mday"] + $daysForward), $currentDateArray["year"]));
        $endDate = $endDateArray["mon"] . "/" . $endDateArray["mday"] . "/" . $endDateArray["year"];
    }

    //6. Construct the invoice #, which is the end date of the billing period 3/25/09 => 32509
    $invoiceNumber = 10000 * $endDateArray["mon"] + 100 * $endDateArray["mday"] + ($endDateArray["year"] - 2000);

    //7. Insert the record, but if it's already there then it will be rejected
    $invoiceRecord = new InvoiceRecord();
    $invoiceRecord->setInvoiceNumber($invoiceNumber);
    $invoiceRecord->setClientID($clientID);
    $invoiceRecord->setContractID($contractRecord->getID());
    $invoiceRecord->setStartDate(format_as_mysql_date($startDate));
    $invoiceRecord->setEndDate(format_as_mysql_date($endDate));

    $invoiceRecord->insert(&$sql);
}

function is_valid_event_date($clientID, $eventDate) {
    $invoiceRecord = new InvoiceRecord();
    $invoiceRecord->findByClientIDAndDate($clientID, format_as_mysql_date($eventDate));

    $id = $invoiceRecord->getID();
    $closed = $invoiceRecord->getClosed();

    $valid = (!$id || !$closed);

    return $valid;
}

function clone_contract($oldContractID, $newContractID) {
    clone_recurring_items($oldContractID, $newContractID);
    clone_pricing_rules_and_exceptions($oldContractID, $newContractID);
}

function clone_recurring_items($oldContractID, $newContractID) {
    $records = RecurringItemRecord::findByContractID($oldContractID);

    $db = DBFactory::create();

    $valueSets = array();

    foreach($records as $record) {
        $displayName = $db->prepStrVal($record->getDisplayName());
        $moneyValue = $record->getMoneyValue();
        $glAccountID = $record->getGLAccountID();
        $departmentID = $record->getDepartmentID();
        $locationID = $record->getLocationID();

        $valueSets[] = "($displayName,
                         $moneyValue,
                         $newContractID,
                         $glAccountID,
                         $departmentID,
                         $locationID)";
    }

    $valuesClause = implode(",", $valueSets);

    $sql = "INSERT INTO
                recurring_items
                (display_name,
                 money_value,
                 contract_id,
                 gl_account_id,
                 department_id,
                 location_id)
            VALUES
                $valuesClause";

    $result = $db->query($sql);

    return $result;
}

function clone_pricing_rules_and_exceptions($oldContractID, $newContractID) {
    $db = DBFactory::create();

    // 1. Insert copies of pricing rules for new contract
    $valueSets = array();

    $oldPricingRuleRecords = PricingRuleRecord::findByContractID($oldContractID);
    foreach($oldPricingRuleRecords as $pricingRuleRecord) {
        $billableTypeID = $pricingRuleRecord->getBillableTypeID();
        $unitPrice = $pricingRuleRecord->getUnitPrice();

        $valueSets[] = "($newContractID,
                         $billableTypeID,
                         $unitPrice)";
    }

    $valuesClause = implode(",", $valueSets);

    $sql = "INSERT INTO
                pricing_rules
                (contract_id,
                 billable_type_id,
                 unit_price)
            VALUES
                $valuesClause";

    $result = $db->query($sql);

    // 2. Create a mapping between billable type and new pricing rules
    $newPricingRuleRecordsByBillableTypeID = array();

    $newPricingRuleRecords = PricingRuleRecord::findByContractID($newContractID);
    foreach($newPricingRuleRecords as $newPricingRuleRecord) {
        $billableTypeID = $newPricingRuleRecord->getBillableTypeID();
        $newPricingRuleRecordsByBillableTypeID[$billableTypeID] = $newPricingRuleRecord;
    }

    // 3. Insert copies exceptions for new contract
    $valueSets = array();
    $oldExceptionRecords = BillableExceptionRecord::findByContractID($oldContractID);
    foreach($oldExceptionRecords as $oldExceptionRecord) {

        $oldPricingRuleID = $oldExceptionRecord->getPricingRuleID();
        $oldPricingRuleRecord = $oldPricingRuleRecords[$oldPricingRuleID];
        $billableTypeID = $oldPricingRuleRecord->getBillableTypeID();
        $newPricingRuleRecord = $newPricingRuleRecordsByBillableTypeID[$billableTypeID];
        $newPricingRuleID = $newPricingRuleRecord->getID();

        $field1Value = $db->prepStrVal($oldExceptionRecord->getField1Value());
        $field1Comparator = $db->prepStrVal($oldExceptionRecord->getField1Comparator());
        $field2Value = $db->prepStrVal($oldExceptionRecord->getField2Value());
        $field2Comparator = $db->prepStrVal($oldExceptionRecord->getField2Comparator());
        $price = $oldExceptionRecord->getPrice();

        $valueSets[] = "($newPricingRuleID,
                         $field1Comparator,
                         $field1Value,
                         $field2Comparator,
                         $field2Value,
                         $price)";
    }

    $valuesClause = implode(",", $valueSets);

    $sql = "INSERT INTO
                billable_exceptions
                (pricing_rule_id,
                 field1_comparator,
                 field1_value,
                 field2_comparator,
                 field2_value,
                 price)
            VALUES
                $valuesClause";

    $result = $db->query($sql);
}
?>