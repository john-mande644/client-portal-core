<?php
include_once("includes/invoice_record.php");
include_once("includes/contract_record.php");
include_once("includes/department_minimum_record.php");
include_once("includes/billable_event_record.php");
include_once("includes/recurring_item_record.php");
include_once("includes/billable_exception_record.php");
include_once("includes/priced_event_record.php");

define("TAX_RATE", 0.06);

class Invoice {
    private $clientID;
    private $contractID;
    private $invoiceNumber;
    private $startDate;
    private $endDate;
    private $closed;

    // Contract
    private $contractStartDate;
    private $contractEndDate;
    private $invoiceInterval;

    // Record arrays from database
    private $departmentMinimums;
    private $billableEvents;
    private $pricedEvents;
    private $recurringItems;
    private $exceptions;

    // Arrays
    private $departments;
    private $lineItems;

    // Totals
    private $totalAppliedToMinimum;
    private $differenceToMeetMinimum;
    private $subtotal;
    private $totalNotAppliedToMinimum;
    private $salesTax;
    private $total;

    public function Invoice($clientID, $invoiceID, $date) {
        // Fetch invoice data
        $invoiceRecord = new InvoiceRecord();
        $invoiceRecord->findByID($invoiceID);

        $this->invoiceNumber = $invoiceRecord->getInvoiceNumber();
        $this->clientID = $invoiceRecord->getClientID();
        $this->contractID = $invoiceRecord->getContractID();
        $this->startDate = $invoiceRecord->getStartDate();
        $this->endDate = $invoiceRecord->getEndDate();
        $this->invoiceInterval = $invoiceRecord->getInvoiceInterval();
        $this->closed = $invoiceRecord->getClosed();

        // Fetch contract data
        $contractRecord = new ContractRecord();
        $contractRecord->findByID($this->contractID);

        $this->contractStartDate = $contractRecord->getStartDate();
        $this->contractEndDate = $contractRecord->getEndDate();

        // Fetch arrays
        $this->departmentMinimums = DepartmentMinimumRecord::findByContractID($this->contractID);
        $this->billableEvents = BillableEventRecord::findByInvoiceID($invoiceID);
        $this->pricedEvents = PricedEventRecord::findByInvoiceID($invoiceID);
        $this->recurringItems = RecurringItemRecord::findByClientID($clientID, $date);
        $this->exceptions = BillableExceptionRecord::findByClientID($clientID);

        $this->departments = array();
        $this->lineItems = array();

        // Now do the calculations
        $this->aggregateRecurringItems();
        $this->aggregateBillableEvents();
        $this->aggregatePricedEvents();
        $this->calculateTotals();
    }

    public function getInvoiceNumber() { return $this->invoiceNumber; }
    public function setInvoiceNumber($value) { $this->invoiceNumber = $value; }

    public function getClientID() { return $this->clientID; }
    public function setClientID($value) { $this->clientID = $value; }

    public function getContractID() { return $this->contractID; }
    public function setContractID($value) { $this->contractID = $value; }

    public function getStartDate() { return $this->startDate; }
    public function setStartDate($value) { $this->startDate = $value; }

    public function getEndDate() { return $this->endDate; }
    public function setEndDate($value) { $this->endDate = $value; }

    public function getClosed() { return $this->closed; }
    public function setClosed($value) { $this->closed = $value; }

    public function getDepartments() { return $this->departments; }
    public function getTotalAppliedToMinimum() { return $this->totalAppliedToMinimum; }
    public function getDifferenceToMeetMinimum() { return $this->differenceToMeetMinimum; }
    public function getSubtotal() { return $this->subtotal; }
    public function getTotalNotAppliedToMinimum() { return $this->totalNotAppliedToMinimum; }
    public function getSalesTax() { return $this->salesTax; }
    public function getTotal() { return $this->total; }

    private function calcProratedMinimumAmount($minimumAmount) {
        // Prorate department minimum for weekly invoice intervals -
        // Minimum amounts are defined in terms of month, so we just need to divide by 4
        if ($this->invoiceInterval == WEEKLY_INVOICE_INTERVAL) {
            $minimumAmount /= 4;
        }

        // Prorate if the contract start date is later than the invoice start date
        $contractStartDateInt = date_to_int($this->contractStartDate);
        $invoiceStartDateInt = date_to_int($this->startDate);

        if ($contractStartDateInt > $invoiceStartDateInt) {
            $dateDiff = date_diff($invoiceStartDateInt, $contractStartDateInt);
            $days = $dateDiff['days'];

            switch ($this->invoiceInterval) {
                case WEEKLY_INVOICE_INTERVAL:
                    $scalar = (7 - $days) / 7;
                    break;
                case MONTHLY_INVOICE_INTERVAL:
                    $scalar = (30 - $days) / 30;
                    break;
            }
        }

        $proratedMinimumAmount = $scalar * $minimumAmount;

        return $proratedMinimumAmount;
    }

    private function aggregateRecurringItems() {
        if (is_array($this->recurringItems) && count($this->recurringItems) > 0) {
            foreach($this->recurringItems as $recurringItem) {
                if ($department == null || $recurringItem->getDepartmentName() != $department->getName()) {

                    $departmentID = $recurringItem->getDepartmentID();

                    if (key_exists($departmentID, $this->departmentMinimums)) {
                        $departmentMinimum = $this->departmentMinimums[$recurringItem->getDepartmentID()];
                        $minimumAmount = $departmentMinimum->getAmount();
                    } else {
                        $minimumAmount = 0;
                    }

                    $minimumAmount = $this->calcProratedMinimumAmount($minimumAmount);

                    $department = new Department($recurringItem->getDepartmentID(),
                                            $recurringItem->getDepartmentName(), $minimumAmount);
                    $this->departments[$recurringItem->getDepartmentName()] = $department;
                }

                $lineItem = new LineItem();
                $lineItem->setGLAccountCode(substr($recurringItem->getGLAccountCode(), 0, 5));
                $lineItem->setApplyToMinimum($recurringItem->getApplyToMinimum());
                $lineItem->setQuantity(1);
                $lineItem->setUnitPrice($recurringItem->getMoneyValue());
                $lineItem->setDescription($recurringItem->getDisplayName());

                $this->lineItems[] = $lineItem;

                $department->addLineItem($lineItem);
            }
        }
    }

    private function aggregateBillableEvents() {
        if (is_array($this->billableEvents) && count($this->billableEvents) > 0) {
            foreach($this->billableEvents as $billableEvent) {

                if ($department == null || $billableEvent->getDepartmentName() != $department->getName()) {

                    if (key_exists($billableEvent->getDepartmentName(), $this->departments)) {
                        $department = $this->departments[$billableEvent->getDepartmentName()];
                    } else {
                        $departmentID = $billableEvent->getDepartmentID();

                        if (key_exists($departmentID, $this->departmentMinimums)) {
                            $departmentMinimum = $this->departmentMinimums[$billableEvent->getDepartmentID()];
                            $minimumAmount = $departmentMinimum->getAmount();
                        } else {
                            $minimumAmount = 0;
                        }

                        $department = new Department($billableEvent->getDepartmentID(),
                                                $billableEvent->getDepartmentName(), $minimumAmount);
                        $this->departments[$billableEvent->getDepartmentName()] = $department;
                    }
                }

                if ($lineItem == null ||
                    $billableEvent->getPricingRuleID() != $lineItem->getPricingRuleID() ||
                    $billableEvent->getExceptionID() != $lineItem->getExceptionID()) {

                    if ($lineItem != null) {
                        $department->addLineItem($lineItem);
                    }

                    $lineItem = new LineItem();
                    $lineItem->setPricingRuleID($billableEvent->getPricingRuleID());
                    $lineItem->setExceptionID($billableEvent->getExceptionID());
                    $lineItem->setGLAccountCode(substr($billableEvent->getGLAccountCode(), 0, 5));
                    $lineItem->setApplyToMinimum($billableEvent->getApplyToMinimum());
                    $lineItem->setQuantity($billableEvent->getUnits());
                    $lineItem->setUnitPrice($billableEvent->getUnitPrice());

                    $this->lineItems[] = $lineItem;

                    $description = $billableEvent->getBillableTypeName();
                    if ($billableEvent->getExceptionID()) {

                        if (key_exists($billableEvent->getExceptionID(), $this->exceptions)) {
                            $exception = $this->exceptions[$billableEvent->getExceptionID()];

                            if ($exception->getField1Name()) {
                                $exceptionDescription = $exception->getField1Name() . " " .
                                                        $exception->getField1Comparator() . " " .
                                                        $exception->getField1Value();
                            }

                            $description .= ": $exceptionDescription";
                        }
                    }

                    $lineItem->setDescription($description);
                }

                $lineItem->setQuantity($lineItem->getQuantity() + $billableEvent->getUnits());
            }
        }
    }

    private function aggregatePricedEvents() {
        if (is_array($this->pricedEvents) && count($this->pricedEvents) > 0) {
            foreach($this->pricedEvents as $pricedEvent) {

                if ($department == null || $pricedEvent->getDepartmentName() != $department->getName()) {
                    if (key_exists($pricedEvent->getDepartmentName(), $this->departments)) {
                        $department = $this->departments[$pricedEvent->getDepartmentName()];
                    } else {
                        $departmentID = $pricedEvent->getDepartmentID();

                        if (key_exists($departmentID, $this->departmentMinimums)) {
                            $departmentMinimum = $this->departmentMinimums[$pricedEvent->getDepartmentID()];
                            $minimumAmount = $departmentMinimum->getAmount();
                        } else {
                            $minimumAmount = 0;
                        }

                        $department = new Department($pricedEvent->getDepartmentID(),
                                                $pricedEvent->getDepartmentName(), $minimumAmount);
                        $this->departments[$pricedEvent->getDepartmentName()] = $department;
                    }
                }

                if ($lineItem == null ||
                    $pricedEvent->getPricingRuleID() != $lineItem->getPricingRuleID() ||
                    $pricedEvent->getExceptionID() != $lineItem->getExceptionID()) {

                    if ($lineItem != null) {
                        $department->addLineItem($lineItem);
                    }

                    $lineItem = new LineItem();
                    $lineItem->setPricingRuleID($pricedEvent->getPricingRuleID());
                    $lineItem->setExceptionID($pricedEvent->getExceptionID());
                    $lineItem->setGLAccountCode(substr($pricedEvent->getGLAccountCode(), 0, 5));
                    $lineItem->setApplyToMinimum($pricedEvent->getApplyToMinimum());
                    $lineItem->setQuantity($pricedEvent->getUnits());
                    $lineItem->setUnitPrice($pricedEvent->getUnitPrice());

                    $this->lineItems[] = $lineItem;

                    $lineItem->setDescription($pricedEvent->getName());
                }

                $lineItem->setQuantity($lineItem->getQuantity() + $pricedEvent->getUnits());
            }
        }
    }

    private function calculateTotals() {
        $this->totalAppliedToMinimum = 0;
        $this->differenceToMeetMinimum = 0;
        $this->subtotal = 0;
        $this->totalNotAppliedToMinimum = 0;
        $this->salesTax = 0;
        $this->total = 0;

        foreach($this->departments as $department) {
            $this->totalAppliedToMinimum += $department->getTotalAppliedToMinimum();
            $this->differenceToMeetMinimum += $department->getDifferenceToMeetMinimum();
        }

        foreach($this->lineItems as $lineItem) {
            if (!$lineItem->getApplyToMinimum()) {
                $this->totalNotAppliedToMinimum += $lineItem->getTotalPrice();
            }

            $this->salesTax += $lineItem->getSalesTax();
        }


        $this->subtotal = $this->totalAppliedToMinimum + $this->totalNotAppliedToMinimum;

        $this->total = $this->subtotal + $this->salesTax;
    }
}

class Department {
    private $id;
    private $name;
    private $minimumAmount;
    private $totalPrice;
    private $totalAppliedToMinimum;
    private $totalTaxes;
    private $lineItems;

    public function Department($id, $name, $minimumAmount) {
        $this->id = $id;
        $this->name = $name;
        $this->minimumAmount = $minimumAmount;
        $this->totalBasePrice = 0;      // Does not include differenceToMeetMinimum
        $this->totalAppliedToMinimum = 0;
        $this->totalTaxes = 0;
        $this->lineItems = array();
    }

    public function getID() { return $this->id; }
    public function getName() { return $this->name; }
    public function getMinimumAmount() { return $this->minimumAmount; }

    public function getTotalBasePrice() { return $this->totalBasePrice; }
    public function getTotalAppliedToMinimum() { return $this->totalAppliedToMinimum; }

    public function getDifferenceToMeetMinimum() {
        if ($this->minimumAmount > $this->totalAppliedToMinimum) {
            return $this->minimumAmount - $this->totalAppliedToMinimum;
        } else {
            return 0;
        }
    }

    public function getTotalPrice() {
        return $this->totalBasePrice + $this->getDifferenceToMeetMinimum();
    }

    public function getTotalTaxes() { return $this->totalTaxes; }
    public function getNumLineItems() { return count($this->lineItems); }
    public function getLineItem($index) { return $this->lineItems[$index]; }

    public function addLineItem($lineItem) {
        $this->lineItems[] = $lineItem;
        $this->totalBasePrice += $lineItem->getTotalPrice();

        if ($lineItem->getApplyToMinimum()) {
            $this->totalAppliedToMinimum += $lineItem->getTotalPrice();
        }
    }
}

class LineItem {
    private $pricingRuleID;
    private $exceptionID;
    private $glAccountCode;
    private $applyToMinimum;
    private $description;
    private $quantity;
    private $unitPrice;

    public function getPricingRuleID() { return $this->pricingRuleID; }
    public function setPricingRuleID($val) { $this->pricingRuleID = $val; }

    public function getExceptionID() { return $this->exceptionID; }
    public function setExceptionID($val) { $this->exceptionID = $val; }

    public function getGLAccountCode() { return $this->glAccountCode; }
    public function setGLAccountCode($val) { $this->glAccountCode = $val; }

    public function getApplyToMinimum() { return $this->applyToMinimum; }
    public function setApplyToMinimum($val) { $this->applyToMinimum = $val; }

    public function getDescription() { return $this->description; }
    public function setDescription($val) { $this->description = $val; }

    public function getQuantity() { return $this->quantity; }
    public function setQuantity($val) { $this->quantity = $val; }

    public function getUnitPrice() { return $this->unitPrice; }
    public function setUnitPrice($val) { $this->unitPrice = $val; }

    public function getTotalPrice() { return $this->quantity * $this->unitPrice; }

    public function getSalesTax() {
        // If the last character of the gl account is a 'T' then it's taxable
        if ($this->glAccountCode) {
            $taxable = (substr($this->glAccountCode, -1) == "T");
        }

        if ($taxable) {
            $salesTax = TAX_RATE * $this->getTotalPrice();
        } else {
            $salesTax = 0.0;
        }

        //echo("Sales Tax: $this->glAccountCode => $salesTax<br>");

        return $salesTax;
    }
}
?>


