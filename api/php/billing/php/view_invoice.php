<?php
include_once("includes/base.php");
include_once("includes/client_record.php");
include_once("includes/invoice.php");

define("MONTHLY_MINIMUM_GL_ACCOUNT", "4040T");
define("MONTHLY_MINIMUM_LINE_ITEM_DESCRIPTION", "Monthly Account Minimum");

$clientID = $_GET["clientID"];
$invoiceID = $_GET["invoiceID"];

$dateArray = getdate();
$date = $dateArray["year"] . "-" . $dateArray["mon"] . "-" . $dateArray["mday"];

$invoice = new Invoice($clientID, $invoiceID, $date);

$template = new XiTemplate("templates/view_invoice.html");

$clientRecord = new ClientRecord();
$clientRecord->findByID($clientID);

$template->assign("SECTION_TITLE", $clientRecord->getName());

$template->assign("PAGE_TITLE", "Invoice " . $invoice->getInvoiceNumber());

if ($invoice->getClosed() != 1) {
    $closeInvoiceURL = "close_invoice.php?clientID=$clientID&invoiceID=$invoiceID";
    $template->assign("CLOSE_INVOICE_URL", $closeInvoiceURL);
    $template->parse("main.invoice_open");
} else {
    $template->parse("main.invoice_closed");
}

$invoicePeriod = $invoice->getStartDate() . " - " . $invoice->getEndDate();
$template->assign("INVOICE_PERIOD", $invoicePeriod);

$template->assign("CLIENT_CONTRACTS_URL", "client.php?clientID=$clientID");
$template->assign("CLIENT_INVOICES_URL", "client_invoices.php?clientID=$clientID");
$template->assign("CLIENT_BILLABLE_EVENTS_URL", "client_billable_events.php?clientID=$clientID");
$template->assign("CLIENT_PRICED_EVENTS_URL", "priced_events.php?clientID=$clientID");

$departments = $invoice->getDepartments();
foreach($departments as $department) {
    $template->assign("DEPARTMENT_NAME", $department->getName());
    $template->parse("main.line_items.department_header_line");

    for($j = 0; $j < $department->getNumLineItems(); $j++) {
        $lineItem = $department->getLineItem($j);

        $template->assign("GL_ACCOUNT_CODE", $lineItem->getGLAccountCode());
        $template->assign("DESCRIPTION", $lineItem->getDescription());
        $template->assign("QUANTITY", number_format($lineItem->getQuantity(), 0));
        $template->assign("UNIT_PRICE", number_format($lineItem->getUnitPrice(), 2));
        $template->assign("PRICE", number_format($lineItem->getTotalPrice(), 2));

        $template->parse("main.line_items.line_item");
    }

    if ($department->getMinimumAmount() > 0) {
        $template->assign("GL_ACCOUNT_CODE", MONTHLY_MINIMUM_GL_ACCOUNT);
        $template->assign("DESCRIPTION", MONTHLY_MINIMUM_LINE_ITEM_DESCRIPTION);
        $template->assign("QUANTITY", 1);
        $template->assign("UNIT_PRICE", number_format($department->getMinimumAmount(), 2));
        $template->assign("PRICE", number_format($department->getDifferenceToMeetMinimum(), 2));

        $template->parse("main.line_items.line_item");        
    }

    $template->assign("DEPARTMENT_TOTAL_PRICE", number_format($department->getTotalPrice(), 2));
    $template->parse("main.line_items.department_total_line");

    $template->parse("main.line_items");
}

$template->assign("TOTAL_APPLIED_TO_MINIMUM", number_format($invoice->getTotalAppliedToMinimum(), 2));
$template->assign("DIFFERENCE_TO_MEET_MINIMUM", number_format($invoice->getDifferenceToMeetMinimum(), 2));
$template->assign("INVOICE_SUBTOTAL", number_format($invoice->getSubtotal(), 2));
$template->assign("TOTAL_NOT_APPLIED_TO_MINIMUM", number_format($invoice->getTotalNotAppliedToMinimum(), 2));
$template->assign("INVOICE_SALES_TAX", number_format($invoice->getSalesTax(), 2));
$template->assign("INVOICE_TOTAL", number_format($invoice->getTotal(), 2));

$template->parse("main");

write_header();
$template->out("main");
write_footer();
?>