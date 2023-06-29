<?php
include_once("includes/base.php");
include_once("includes/contract_record.php");
include_once("includes/pricing_rule_record.php");
include_once("includes/billable_exception_record.php");

$clientID = $_GET['clientID'];
$contractID = $_GET['contractID'];

$template = new XiTemplate("templates/pricing_rules.html");

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
$template->assign("PAGE_TITLE", "Pricing Rules");

$template->assign("ADD_PRICING_RULE_URL", "pricing_rule_form.php?clientID=$clientID&contractID=$contractID");

// Populate list
$pricingRuleRecords = PricingRuleRecord::findByContractID($contractID);
$billableExceptionRecords = BillableExceptionRecord::findByContractID($contractID);

if (is_array($pricingRuleRecords) && count($pricingRuleRecords) > 0) {

    $departmentName = "";
    foreach($pricingRuleRecords as $pricingRuleRecord) {

        if ($pricingRuleRecord->getDepartmentName() != $departmentName) {
            $departmentName = $pricingRuleRecord->getDepartmentName();

            $template->assign("DEPARTMENT_NAME", $departmentName);
            $template->parse("main.pricingRules.departmentName");
        }

        $pricingRuleURL = "pricing_rule_form.php?clientID=$clientID&contractID=$contractID&pricingRuleID=" . $pricingRuleRecord->getID();
        $deletePricingRuleURL = "delete_pricing_rule.php?contractID=$contractID&pricingRuleID=" . $pricingRuleRecord->getID();

        $template->assign("PRICING_RULE_ID", $pricingRuleRecord->getID());
        $template->assign("PRICING_RULE_URL", $pricingRuleURL);
        $template->assign("DELETE_PRICING_RULE_URL", $deletePricingRuleURL);
        $template->assign("BILLABLE_TYPE_NAME", $pricingRuleRecord->getBillableTypeName());
        $template->assign("UNIT_PRICE", number_format($pricingRuleRecord->getUnitPrice(), 2));
        $template->assign("BILLABLE_UNIT_NAME", $pricingRuleRecord->getBillableUnitName());

        if ($pricingRuleRecord->getExceptionField1Name() != "") {
            $addPricingRuleExceptionURL = "pricing_rule_exception_form.php?clientID=$clientID&contractID=$contractID&pricingRuleID=" . $pricingRuleRecord->getID();

            $template->assign("ADD_PRICING_RULE_EXCEPTION_URL", $addPricingRuleExceptionURL);
            $template->parse("main.pricingRules.addException");
        }

        if (is_array($billableExceptionRecords) && count($billableExceptionRecords) > 0) {
            foreach($billableExceptionRecords as $billableExceptionRecord) {

                if ($billableExceptionRecord->getPricingRuleID() == $pricingRuleRecord->getID()) {

                    $pricingRuleID = $pricingRuleRecord->getID();

                    $query = "clientID=$clientID&contractID=$contractID&pricingRuleID=$pricingRuleID&pricingRuleExceptionID=" . $billableExceptionRecord->getID();

                    $deletePricingRuleExceptionURL = "delete_pricing_rule_exception.php?$query";
                    $pricingRuleExceptionURL = "pricing_rule_exception_form.php?$query";

                    $field1Name = $billableExceptionRecord->getField1Name();
                    $field1Type = $billableExceptionRecord->getField1Type();
                    $field1Comparator = $billableExceptionRecord->getField1Comparator();
                    $field1Value = $billableExceptionRecord->getField1Value();

                    $field2Name = $billableExceptionRecord->getField2Name();
                    $field2Type = $billableExceptionRecord->getField2Type();
                    $field2Comparator = $billableExceptionRecord->getField2Comparator();
                    $field2Value = $billableExceptionRecord->getField2Value();

                    $exceptionCondition = "";
                    if ($field1Name && $field1Comparator != "any") {
                        $exceptionCondition = $field1Name . " " . $field1Comparator . " ";
                        $exceptionCondition .= ($field1Type == "string" ? "\"$field1Value\"" : $field1Value);
                    }

                    if ($field2Name && $field2Comparator != "any") {
                        if ($exceptionCondition) {
                            $exceptionCondition .= " and ";
                        }
                        $exceptionCondition .= $field2Name . " " . $field2Comparator . " ";
                        $exceptionCondition .= ($field2Type == "string" ? "\"$field2Value\"" : $field2Value);
                    }

                    $exceptionPrice = number_format($billableExceptionRecord->getPrice(), 2);

                    $template->assign("DELETE_PRICING_RULE_EXCEPTION_URL", $deletePricingRuleExceptionURL);
                    $template->assign("PRICING_RULE_EXCEPTION_URL", $pricingRuleExceptionURL);
                    $template->assign("EXCEPTION_CONDITION", $exceptionCondition);
                    $template->assign("EXCEPTION_PRICE", $exceptionPrice);

                    $template->parse("main.pricingRules.exceptions");
                }
            }
        }

        $template->parse("main.pricingRules");
    }
} else {
    $template->parse("main.noPricingRules");
}

$template->parse("main");

write_header();
$template->out("main");
write_footer();

?>