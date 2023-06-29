<?php
include_once("includes/base.php");
include_once("includes/recurring_item_record.php");
include_once("includes/pricing_rule_record.php");
include_once("includes/billable_exception_record.php");

function clone_contract($oldContractID, $newContractID) {
    clone_recurring_items($oldContractID, $newContractID);
    clone_pricing_rules_and_exceptions($oldContractID, $newContractID)
}

function clone_recurring_items($oldContractID, $newContractID) {
    $records = RecurringItemRecord::findByContractID($oldContractID);

    $db = new Database();

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
    $db = new Database();

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

clone_pricing_contract(28, 36);
?>