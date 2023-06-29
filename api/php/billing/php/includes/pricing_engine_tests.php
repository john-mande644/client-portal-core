<?php
include_once("pricing_engine.php");

class UnitTester {
    public function runTests() {
        $this->runTest1();
        $this->runTest2();
        $this->runTest3();
        $this->runTest4();
        $this->runTest5();
        $this->runTest6();
        $this->runTest7();
        $this->runTest8();
    }

    public function runTest1() {
        echo("<b>Test 1: Test string type and field 'begins_with' comparator.</b><br>");

        $pricingRule = new PricingRule();
        $pricingRule->id = 1;
        $pricingRule->contractID = 100;
        $pricingRule->billableTypeID = 1000;
        $pricingRule->price = 0.10;
        $pricingRule->exceptionField1Name = "Campaign";
        $pricingRule->exceptionField1Type = "string";
        $pricingRule->exceptionField2Name = null;
        $pricingRule->exceptionField2Type = null;

        $exception = new PricingRuleException();
        $exception->id = 1;
        $exception->field1Comparator = "begins_with";
        $exception->field1Value = "Flowers";
        $exception->field2Comparator = null;
        $exception->field2Value = null;
        $exception->price = 0.15;
        $pricingRule->addException($exception);

        // Create test event
        $flowersEvent = new BillableEvent();
        $flowersEvent->id = 1;
        $flowersEvent->contractID = 100;
        $flowersEvent->billableTypeID = 1000;
        $flowersEvent->units = 5;
        $flowersEvent->exceptionField1Value = "Flowers";
        $flowersEvent->exceptionField2Value = null;
        $price = $pricingRule->calcPrice($flowersEvent, &$exceptionID);

        if ($price == 0.75) {
            echo("<b>---Succeeded!</b><br>");
        } else {
            echo("<b>---Failed. Exception 1 should have been applied to this event.</b><br>");
        }
    }

    public function runTest2() {
        echo("<b>Test 2: Test string field type and 'begins_with' comparator with more than one true exceptions.</b><br>");

        $pricingRule = new PricingRule();
        $pricingRule->id = 1;
        $pricingRule->contractID = 100;
        $pricingRule->billableTypeID = 1000;
        $pricingRule->price = 0.10;
        $pricingRule->exceptionField1Name = "Campaign";
        $pricingRule->exceptionField1Type = "string";
        $pricingRule->exceptionField2Name = null;
        $pricingRule->exceptionField2Type = null;

        $exception = new PricingRuleException();
        $exception->id = 1;
        $exception->field1Comparator = "begins_with";
        $exception->field1Value = "Flowers";
        $exception->field2Comparator = null;
        $exception->field2Value = null;
        $exception->price = 0.15;
        $pricingRule->addException($exception);

        $exception = new PricingRuleException();
        $exception->id = 2;
        $exception->field1Comparator = "begins_with";
        $exception->field1Value = "Flow";
        $exception->field2Comparator = null;
        $exception->field2Value = null;
        $exception->price = 0.25;
        $pricingRule->addException($exception);

        // Create test event
        $flowersEvent = new BillableEvent();
        $flowersEvent->id = 1;
        $flowersEvent->contractID = 100;
        $flowersEvent->billableTypeID = 1000;
        $flowersEvent->units = 5;
        $flowersEvent->exceptionField1Value = "Flowers";
        $flowersEvent->exceptionField2Value = null;
        $price = $pricingRule->calcPrice($flowersEvent, &$exceptionID);

        if ($price == 0.75) {
            echo("<b>---Succeeded!</b><br>");
        } else {
            echo("<b>---Failed. The first exception should have been applied since more than one exception (lacking field ordering) should have evaluated to true.</b><br>");
        }
    }

    public function runTest3() {
        echo("<b>Test 3: Test string field type and 'equals' comparator with only one true exception.</b><br>");

        $pricingRule = new PricingRule();
        $pricingRule->id = 1;
        $pricingRule->contractID = 100;
        $pricingRule->billableTypeID = 1000;
        $pricingRule->price = 0.10;
        $pricingRule->exceptionField1Name = "Campaign";
        $pricingRule->exceptionField1Type = "string";
        $pricingRule->exceptionField2Name = null;
        $pricingRule->exceptionField2Type = null;

        $exception = new PricingRuleException();
        $exception->id = 1;
        $exception->field1Comparator = "begins_with";
        $exception->field1Value = "Flowers";
        $exception->field2Comparator = null;
        $exception->field2Value = null;
        $exception->price = 0.15;
        $pricingRule->addException($exception);

        $exception = new PricingRuleException();
        $exception->id = 2;
        $exception->field1Comparator = "begins_with";
        $exception->field1Value = "Flow";
        $exception->field2Comparator = null;
        $exception->field2Value = null;
        $exception->price = 0.25;
        $pricingRule->addException($exception);

        $exception = new PricingRuleException();
        $exception->id = 3;
        $exception->field1Comparator = "equals";
        $exception->field1Value = "Candy";
        $exception->field2Comparator = null;
        $exception->field2Value = null;
        $exception->price = 0.40;
        $pricingRule->addException($exception);

        $candyEvent = new BillableEvent();
        $candyEvent->id = 1;
        $candyEvent->contractID = 100;
        $candyEvent->billableTypeID = 1000;
        $candyEvent->units = 5;
        $candyEvent->exceptionField1Value = "candy";
        $candyEvent->exceptionField2Value = null;
        $price = $pricingRule->calcPrice($candyEvent, &$exceptionID);

        if ($price == 2.00) {
            echo("<b>---Succeeded!</b><br>");
        } else {
            echo("<b>---Failed.</b><br>");
        }
    }

    public function runTest4() {
        echo("<b>Test 4: Test decimal field type and '>=' comparison with more than one true exception.</b><br>");

        $pricingRule = new PricingRule();
        $pricingRule->id = 1;
        $pricingRule->contractID = 100;
        $pricingRule->billableTypeID = 1000;
        $pricingRule->price = 0.10;
        $pricingRule->exceptionField1Name = "weight";
        $pricingRule->exceptionField1Type = "decimal";
        $pricingRule->exceptionField2Name = null;
        $pricingRule->exceptionField2Type = null;

        $exception = new PricingRuleException();
        $exception->id = 1;
        $exception->field1Comparator = ">=";
        $exception->field1Value = 5;
        $exception->field2Comparator = null;
        $exception->field2Value = null;
        $exception->price = 0.25;
        $pricingRule->addException($exception);

        $exception = new PricingRuleException();
        $exception->id = 2;
        $exception->field1Comparator = ">=";
        $exception->field1Value = 10;
        $exception->field2Comparator = null;
        $exception->field2Value = null;
        $exception->price = 0.40;
        $pricingRule->addException($exception);

        $exception = new PricingRuleException();
        $exception->id = 3;
        $exception->field1Comparator = ">=";
        $exception->field1Value = 2;
        $exception->field2Comparator = null;
        $exception->field2Value = null;
        $exception->price = 0.15;
        $pricingRule->addException($exception);

        $event = new BillableEvent();
        $event->id = 1;
        $event->contractID = 100;
        $event->billableTypeID = 1000;
        $event->units = 2;
        $event->exceptionField1Value = 6;
        $event->exceptionField2Value = null;
        $price = $pricingRule->calcPrice($event, &$exceptionID);

        if ($price == 0.50) {
            echo("<b>---Succeeded!</b><br>");
        } else {
            echo("<b>---Failed.</b><br>");
        }
    }

    public function runTest5() {
        echo("<b>Test 5: Test decimal field type with opposite exception field orderings (Defaults to first true exception).</b><br>");

        $pricingRule = new PricingRule();
        $pricingRule->id = 1;
        $pricingRule->contractID = 100;
        $pricingRule->billableTypeID = 1000;
        $pricingRule->price = 0.10;
        $pricingRule->exceptionField1Name = "weight";
        $pricingRule->exceptionField1Type = "decimal";
        $pricingRule->exceptionField2Name = null;
        $pricingRule->exceptionField2Type = null;

        $exception = new PricingRuleException();
        $exception->id = 1;
        $exception->field1Comparator = ">=";
        $exception->field1Value = 5;
        $exception->field2Comparator = null;
        $exception->field2Value = null;
        $exception->price = 0.25;
        $pricingRule->addException($exception);

        $exception = new PricingRuleException();
        $exception->id = 2;
        $exception->field1Comparator = "<";
        $exception->field1Value = 10;
        $exception->field2Comparator = null;
        $exception->field2Value = null;
        $exception->price = 0.40;
        $pricingRule->addException($exception);

        $event = new BillableEvent();
        $event->id = 1;
        $event->contractID = 100;
        $event->billableTypeID = 1000;
        $event->units = 2;
        $event->exceptionField1Value = 6;
        $event->exceptionField2Value = null;
        $price = $pricingRule->calcPrice($event, &$exceptionID);

        if ($price == 0.50) {
            echo("<b>---Succeeded!</b><br>");
        } else {
            echo("<b>---Failed.</b><br>");
        }
    }

    public function runTest6() {
        echo("<b>Test 6: Test string field with 'contains' comparator.</b><br>");

        $pricingRule = new PricingRule();
        $pricingRule->id = 1;
        $pricingRule->contractID = 100;
        $pricingRule->billableTypeID = 1000;
        $pricingRule->price = 0.10;
        $pricingRule->exceptionField1Name = "reason";
        $pricingRule->exceptionField1Type = "string";
        $pricingRule->exceptionField2Name = null;
        $pricingRule->exceptionField2Type = null;

        $exception = new PricingRuleException();
        $exception->id = 2;
        $exception->field1Comparator = "contains";
        $exception->field1Value = "sold";
        $exception->field2Comparator = null;
        $exception->field2Value = null;
        $exception->price = 0.25;
        $pricingRule->addException($exception);

        $event1 = new BillableEvent();
        $event1->id = 1;
        $event1->contractID = 100;
        $event1->billableTypeID = 1000;
        $event1->units = 2;
        $event1->exceptionField1Value = "upsold";
        $event1->exceptionField2Value = null;

        $price1 = $pricingRule->calcPrice($event1, &$exceptionID);

        $event2 = new BillableEvent();
        $event2->id = 2;
        $event2->contractID = 100;
        $event2->billableTypeID = 1000;
        $event2->units = 1;
        $event2->exceptionField1Value = "returned";
        $event2->exceptionField2Value = null;

        $price2 = $pricingRule->calcPrice($event2, &$exceptionID);

        if ($price1 == 0.50 && $price2 == 0.10) {
            echo("<b>---Succeeded!</b><br>");
        } else {
            echo("<b>---Failed.</b><br>");
        }
    }

    public function runTest7() {
        echo("<b>Test 7: Test two string fields, one 'any' comparator and another with 'equals' comparator.</b><br>");

        $pricingRule = new PricingRule();
        $pricingRule->id = 1;
        $pricingRule->contractID = 100;
        $pricingRule->billableTypeID = 1000;
        $pricingRule->price = 0.10;
        $pricingRule->exceptionField1Name = "Campaign";
        $pricingRule->exceptionField1Type = "string";
        $pricingRule->exceptionField2Name = "Reason";
        $pricingRule->exceptionField2Type = "string";

        $exception = new PricingRuleException();
        $exception->id = 1;
        $exception->field1Comparator = "equals";
        $exception->field1Value = "Flowers";
        $exception->field2Comparator = "any";
        $exception->field2Value = "";
        $exception->price = 0.20;
        $pricingRule->addException($exception);

        $exception = new PricingRuleException();
        $exception->id = 2;
        $exception->field1Comparator = "equals";
        $exception->field1Value = "Candy";
        $exception->field2Comparator = "equals";
        $exception->field2Value = "sold";
        $exception->price = 0.50;
        $pricingRule->addException($exception);

        $event1 = new BillableEvent();
        $event1->id = 1;
        $event1->contractID = 100;
        $event1->billableTypeID = 1000;
        $event1->units = 1;
        $event1->exceptionField1Value = "Flowers";
        $event1->exceptionField2Value = "returned";
        $price1 = $pricingRule->calcPrice($event1, &$exceptionID);

        $event2 = new BillableEvent();
        $event2->id = 2;
        $event2->contractID = 100;
        $event2->billableTypeID = 1000;
        $event2->units = 1;
        $event2->exceptionField1Value = "Candy";
        $event2->exceptionField2Value = "sold";
        $price2 = $pricingRule->calcPrice($event2, &$exceptionID);

        $event3 = new BillableEvent();
        $event3->id = 2;
        $event3->contractID = 100;
        $event3->billableTypeID = 1000;
        $event3->units = 1;
        $event3->exceptionField1Value = "Candy";
        $event3->exceptionField2Value = "returned";
        $price3 = $pricingRule->calcPrice($event3, &$exceptionID);

        if ($price1 == 0.20 && $price2 == 0.50 && $price3 == 0.10) {
            echo("<b>---Succeeded!</b><br>");
        } else {
            echo("<b>---Failed.</b><br>");
        }
    }

    public function runTest8() {
        echo("<b>Test 8: Test string field with 'ends_with' comparator.</b><br>");

        $pricingRule = new PricingRule();
        $pricingRule->id = 1;
        $pricingRule->contractID = 100;
        $pricingRule->billableTypeID = 1000;
        $pricingRule->price = 0.10;
        $pricingRule->exceptionField1Name = "reason";
        $pricingRule->exceptionField1Type = "string";
        $pricingRule->exceptionField2Name = null;
        $pricingRule->exceptionField2Type = null;

        $exception = new PricingRuleException();
        $exception->id = 2;
        $exception->field1Comparator = "ends_with";
        $exception->field1Value = "up";
        $exception->field2Comparator = null;
        $exception->field2Value = null;
        $exception->price = 0.25;
        $pricingRule->addException($exception);

        $event1 = new BillableEvent();
        $event1->id = 1;
        $event1->contractID = 100;
        $event1->billableTypeID = 1000;
        $event1->units = 2;
        $event1->exceptionField1Value = "Hang Up";
        $event1->exceptionField2Value = null;
        $price1 = $pricingRule->calcPrice($event1, &$exceptionID);

        $event2 = new BillableEvent();
        $event2->id = 2;
        $event2->contractID = 100;
        $event2->billableTypeID = 1000;
        $event2->units = 2;
        $event2->exceptionField1Value = "Upsold";
        $event2->exceptionField2Value = null;
        $price2 = $pricingRule->calcPrice($event2, &$exceptionID);

        if ($price1 == 0.50 && $price2 == 0.20) {
            echo("<b>---Succeeded!</b><br>");
        } else {
            echo("<b>---Failed.</b><br>");
        }
    }
}

$unitTester = new UnitTester();
$unitTester->runTests();
?>

