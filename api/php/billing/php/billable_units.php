<?php
include_once("includes/base.php");
include_once("includes/billable_unit_record.php");

$template = new XiTemplate("templates/billable_units.html");

$addBillableUnitURL = "billable_unit_form.php";
$template->assign("ADD_BILLABLE_UNIT_URL", $addBillableUnitURL);

$billableUnits = BillableUnitRecord::findAll();

if (is_array($billableUnits) && count($billableUnits) > 0) {
    foreach($billableUnits as $billableUnit) {
        $deleteBillableUnitURL = "delete_billable_unit.php?billableUnitID=" . $billableUnit->getID();
        $billableUnitURL = "billable_unit_form.php?billableUnitID=" . $billableUnit->getID();

        $billableUnitValueType = ($billableUnit->getValueType() == 0 ? "Integer" : "Decimal");

        $template->assign("DELETE_BILLABLE_UNIT_URL", $deleteBillableUnitURL);
        $template->assign("BILLABLE_UNIT_URL", $billableUnitURL);
        $template->assign("BILLABLE_UNIT_NAME", $billableUnit->getName());
        $template->assign("BILLABLE_UNIT_VALUE_TYPE", $billableUnitValueType);

        $template->parse("main.billable_units");
    }
} else {
    $template->parse("main.no_billable_units");
}

$template->parse("main");

write_header();
$template->out("main");
write_footer();
?>