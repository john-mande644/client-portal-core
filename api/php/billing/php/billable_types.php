<?php
include_once("includes/base.php");
include_once("includes/billable_type_record.php");

$template = new XiTemplate("templates/billable_types.html");

$billableTypes = BillableTypeRecord::findAll();

$template->assign("ADD_BILLABLE_TYPE_URL", "billable_type_form.php");

if (is_array($billableTypes) && count($billableTypes) > 0) {
    foreach($billableTypes as $billableType) {
        $deleteBillableTypeURL = "delete_billable_type.php?billableTypeID=" . $billableType->getID();
        $billableTypeFormURL = "billable_type_form.php?billableTypeID=" . $billableType->getID();

        $exceptionFields = "&nbsp;";
        if ($billableType->getExceptionField1Name() != "") {
            $exceptionFields = $billableType->getExceptionField1Name();
        }

        if (trim($billableType->getExceptionField2Name()) != "") {
            $exceptionFields .= ", " . $billableType->getExceptionField2Name();
        }

        $template->assign("DELETE_BILLABLE_TYPE_URL", $deleteBillableTypeURL);
        $template->assign("BILLABLE_TYPE_FORM_URL", $billableTypeFormURL);
        $template->assign("ID", $billableType->getID());
        $template->assign("NAME", $billableType->getName());
        $template->assign("BILLABLE_UNIT_NAME", $billableType->getBillableUnitName());
        $template->assign("EXCEPTION_FIELDS", $exceptionFields);
        $template->assign("GL_ACCOUNT_ID", $billableType->getGLAccountID());
        $template->assign("GL_ACCOUNT_CODE", $billableType->getGLAccountCode());
        $template->assign("LOCATION_ID", $billableType->getLocationID());
        $template->assign("LOCATION_CODE", $billableType->getLocationCode());
        $template->assign("DEPARTMENT_ID", $billableType->getDepartmentID());
        $template->assign("DEPARTMENT_CODE", $billableType->getDepartmentCode());

        $template->parse("main.billable_types");
    }
} else {
    $template->parse("main.no_billable_types");
}

$template->parse("main");

write_header();
$template->out("main");
write_footer();
?>