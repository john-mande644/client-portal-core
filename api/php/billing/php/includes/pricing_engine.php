<?php
include_once("database.php");

define("FIELD_ORDERING_NONE", 0);
define("FIELD_ORDERING_LOW_TO_HIGH", 1);
define("FIELD_ORDERING_HIGH_TO_LOW", 2);

class BillableEvent {
    public $id;
    //public $clientID;
    //public $eventDate;
    public $contractID;
    public $billableTypeID;
    public $units;
    public $exceptionField1Value;
    public $exceptionField2Value;
    public $pricingRuleID;
    public $exceptionID;
    public $unitPrice;
}

class PricingRuleException {
    public $id;
    public $field1Comparator;
    public $field1Value;
    public $field2Comparator;
    public $field2Value;
    public $price;
}

class PricingRule {
    private $exceptions = array();

    public $id;
    public $contractID;
    public $billableTypeID;
    public $price;
    public $exceptionField1Name;
    public $exceptionField1Type;
    public $exceptionField2Name;
    public $exceptionField2Type;

    public function addException($exception) {
        $this->exceptions[$exception->id] = $exception;
    }

    public function calcPrice($event, &$exceptionID) {
        $exceptionID = 0;
        $bestException = null;

        if (count($this->exceptions) > 0) {
            $consistentFieldOrdering = $this->checkFieldOrderingConsistency();

            foreach($this->exceptions as $exception) {
                $testResult = $this->testException($event, $exception);

                if ($testResult) {
                    $field1Ordering = $this->determineFieldOrdering($exception->field1Comparator);
                    $field2Ordering = $this->determineFieldOrdering($exception->field2Comparator);

                    if (!is_null($bestException)) {
                        switch ($field1Ordering) {
                            case FIELD_ORDERING_LOW_TO_HIGH:
                                $field1Better = (floatval($exception->field1Value) <= floatval($bestException->field1Value));
                                break;
                            case FIELD_ORDERING_HIGH_TO_LOW:
                                $field1Better = (floatval($exception->field1Value) >= floatval($bestException->field1Value));
                                break;
                            default:
                                $field1Better = true;
                                break;
                        }

                        switch ($field2Ordering) {
                            case FIELD_ORDERING_LOW_TO_HIGH:
                                $field2Better = (floatval($exception->field2Value) <= floatval($bestException->field2Value));
                                break;
                            case FIELD_ORDERING_HIGH_TO_LOW:
                                $field2Better = (floatval($exception->field2Value) >= floatval($bestException->field2Value));
                                break;
                            default:
                                $field2Better = true;
                                break;
                        }

                        if ($field1Better == true && $field2Better == true) {
                            $bestException = $exception;
                        }
                    } else {
                        $bestException = $exception;

                        if (!$consistentFieldOrdering) {
                            // When there is field ordering (high to low or low to high, but the ordering is inconsistent
                            // then we need to just go with the first condition that evaluated to true.
                            break;
                        }

                        if ($field1Ordering == FIELD_ORDERING_NONE  && $field2Ordering == FIELD_ORDERING_NONE) {
                            // When there is no ordering for either field, then it's impossible to determine which
                            // exception is better if there turns out to be more than one. Since we already found
                            // one exception we can go ahead and break out of the loop. (This was Stewart's suggestion
                            // for how to handle this situation.)
                            break;
                        }
                    }
                }
            }
        }

        if ($bestException != null) {
            $unitPrice = $bestException->price;
            $exceptionID = $bestException->id;
        } else {
            $unitPrice = $this->price;
            $exceptionID = 0;
        }

        return $unitPrice;
    }

    private function determineFieldOrdering($fieldComparator) {
        switch ($fieldComparator) {
            case "<":
            case "<=":
                return FIELD_ORDERING_LOW_TO_HIGH;
            case ">":
            case ">=":
                return FIELD_ORDERING_HIGH_TO_LOW;
            default:
                return FIELD_ORDERING_NONE;
        }
    }


    private function checkFieldOrderingConsistency() {
        if (count($this->exceptions) > 1) {
            $field1IsNumeric = ($this->exceptionField1Type == "decimal" || $this->exceptionField1Type == "integer");
            if ($field1IsNumeric) {
                foreach($this->exceptions as $exception) {
                    $field1Ordering = $this->determineFieldOrdering($exception->field1Comparator);

                    if (!is_null($prevField1Ordering) && $field1Ordering != $prevField1Ordering) {
                        return false;
                    }
                    $prevField1Ordering = $field1Ordering;
                }
            }

            $field2IsNumeric = ($this->exceptionField2Type == "decimal" || $this->exceptionField2Type == "integer");
            if ($field2IsNumeric) {
                foreach($this->exceptions as $exception) {
                    $field2Ordering = $this->determineFieldOrdering($exception->field2Comparator);

                    if (!is_null($prevField2Ordering) && $field2Ordering != $prevField2Ordering) {
                        return false;
                    }
                    $prevField2Ordering = $field2Ordering;
                }
            }
        }

        return true;
    }

    private function testException($event, $exception) {
        // Evaluate field 1
        $field1Result = false;
        switch ($this->exceptionField1Type) {
            case "decimal":
                $field1Result = $this->compareDecimals($event->exceptionField1Value,
                                            $exception->field1Comparator, $exception->field1Value);
                break;
            case "integer":
                $field1Result = $this->compareIntegers($event->exceptionField1Value,
                                            $exception->field1Comparator, $exception->field1Value);
                break;
            case "string":
                $field1Result = $this->compareStrings($event->exceptionField1Value,
                                            $exception->field1Comparator, $exception->field1Value);
                break;
            default:
                $field1Result = true;
                break;
        }

        // Evaluate field 2
        $field2Result = false;
        switch ($this->exceptionField2Type) {
            case "decimal":
                $field2Result = $this->compareDecimals($event->exceptionField2Value,
                                            $exception->field2Comparator, $exception->field2Value);
                break;
            case "integer":
                $field2Result = $this->compareIntegers($event->exceptionField2Value,
                                            $exception->field2Comparator, $exception->field2Value);
                break;
            case "string":
                $field2Result = $this->compareStrings($event->exceptionField2Value,
                                            $exception->field2Comparator, $exception->field2Value);
                break;
            default:
                $field2Result = true;
                break;
        }

        $result = $field1Result && $field2Result;

        return $result;
    }


    private function compareIntegers($eventValue, $comparator, $exceptionValue) {
        switch ($comparator) {
            case "=": return intval($eventValue) == intval($exceptionValue);
            case "!=": return intval($eventValue) != intval($exceptionValue);
            case "<": return intval($eventValue) < intval($exceptionValue);
            case "<=": return intval($eventValue) <= intval($exceptionValue);
            case ">": return intval($eventValue) > intval($exceptionValue);
            case ">=": return intval($eventValue) >= intval($exceptionValue);
            default: return false;
        }
    }

    private function compareDecimals($eventValue, $comparator, $exceptionValue) {
        switch ($comparator) {
            case "=": return floatval($eventValue) == floatval($exceptionValue);
            case "!=": return floatval($eventValue) != floatval($exceptionValue);
            case "<": return floatval($eventValue) < floatval($exceptionValue);
            case "<=": return floatval($eventValue) <= floatval($exceptionValue);
            case ">": return floatval($eventValue) > floatval($exceptionValue);
            case ">=": return floatval($eventValue) >= floatval($exceptionValue);
            default: return false;
        }
    }

    private function compareStrings($eventValue, $comparator, $exceptionValue) {
        $result = false;

        switch ($comparator) {
            case "any":
                $result = true;
                break;
            case "equals":
                $result = (strcasecmp($eventValue, $exceptionValue) == 0);
                break;
            case "contains":
                $result = (stripos($eventValue, $exceptionValue) !== false);
                break;
            case "begins_with":
                $n = strlen($exceptionValue);
                $result = (strncasecmp($eventValue, $exceptionValue, $n) == 0);
                break;
            case "ends_with":
                $startIndex = strlen($eventValue) - strlen($exceptionValue);

                if ($startIndex >= 0) {
                    $endStr = substr($eventValue, $startIndex);
                    $result = (strcasecmp($endStr, $exceptionValue) == 0);
                }
                break;
        }

        return $result;
    }
}

class PricingEngine {
    public function runBatch() {
        $events = $this->fetchBillableEvents();
        $pricingRules = $this->fetchPricingRulesWithExceptions();

        if (is_array($events) && count($events) > 0) {
            foreach($events as $event) {
                $key = $event->contractID . "." . $event->billableTypeID;

                if (key_exists($key, $pricingRules)) {
                    $pricingRule = $pricingRules[$key];
                    $event->unitPrice = $pricingRule->calcPrice($event, &$exceptionID);
                    $event->pricingRuleID = $pricingRule->id;
                    $event->exceptionID = $exceptionID;
                } else {
                    $event->unitPrice = 0.0;
                    $event->pricingRuleID = 0;
                    $event->exceptionID = 0;
                }
            }

            $this->updateBillableEvents($events);
        }
    }

    public function updateBillableEvents($events) {
        if (is_array($events) && count($events) > 0) {
            $db = DBFactory::create();

            $sql = "CREATE TABLE
                        #tmp_billable_events_update
                        (billable_event_id INT NOT NULL,
                         unit_price FLOAT NOT NULL,
                         pricing_rule_id INT NOT NULL,
                         exception_id INT NOT NULL)";
            $result = $db->query($sql);


            $inserts = array();
            foreach($events as $event) {
                $inserts[] = "INSERT INTO
                                    #tmp_billable_events_update
                                    (billable_event_id,
                                     unit_price,
                                     pricing_rule_id,
                                     exception_id)
                                  VALUES
                                    ($event->id,
                                     $event->unitPrice,
                                     $event->pricingRuleID,
                                     $event->exceptionID);";
            }
            $sql = implode(" ", $inserts);

            $result = $db->query($sql);

            $sql = "UPDATE
                        billable_events
                    SET
                        billable_events.unit_price = #tmp_billable_events_update.unit_price,
                        billable_events.pricing_rule_id = #tmp_billable_events_update.pricing_rule_id,
                        billable_events.exception_id = #tmp_billable_events_update.exception_id
                    FROM
                        #tmp_billable_events_update
                    WHERE
                        billable_events.id = #tmp_billable_events_update.billable_event_id";
            $result = $db->query($sql);

            $sql = "DROP TABLE #tmp_billable_events_update";
            $result = $db->query($sql);
        }
    }

    public function fetchBillableEvents() {
        $db = DBFactory::create();

        $sql = "SELECT
                    billable_events.id AS billable_event_id,
                    billable_events.billable_type_id,
                    billable_events.event_date,
                    billable_events.units,
                    billable_types.name AS billable_type_name,
                    billable_events.exception_field1_value,
                    billable_events.exception_field2_value,
                    clients.id AS client_id,
                    contracts.id AS contract_id
                FROM
                    billable_events,
                    billable_types,
                    contracts,
                    clients
                WHERE
                    (billable_events.event_date >= contracts.start_date AND
                     (billable_events.event_date <= contracts.end_date OR contracts.end_date is NULL)) AND
                    contracts.client_id = clients.id AND
                    billable_types.id = billable_events.billable_type_id AND
                    clients.id = billable_events.client_id";

        $result = $db->query($sql);

        $billableEvents = array();

        if ($db->getNumRows($result) > 0) {
            while($row = $db->fetchObject($result)) {
                $billableEvent = new BillableEvent();

                $billableEvent->id = $row->billable_event_id;
                //$billableEvent->clientID = $row->client_id;
                $billableEvent->contractID = $row->contract_id;
                $billableEvent->billableTypeID = $row->billable_type_id;
                //$billableEvent->eventDate = $row->event_date;
                $billableEvent->units = floatval($row->units);
                $billableEvent->exceptionField1Value = $row->exception_field1_value;
                $billableEvent->exceptionField2Value = $row->exception_field2_value;

                $billableEvents[$billableEvent->id] = $billableEvent;
            }
        }

        return $billableEvents;
    }

    public function fetchPricingRulesWithExceptions() {
        $db = DBFactory::create();

        $sql = "SELECT
                    pricing_rules.id AS pricing_rule_id,
                    pricing_rules.contract_id,
                    pricing_rules.unit_price,
                    billable_types.id AS billable_type_id,
                    billable_types.exception_field1_name AS field1_name,
                    billable_types.exception_field1_type AS field1_type,
                    billable_types.exception_field2_name AS field2_name,
                    billable_types.exception_field2_type AS field2_type,
                    billable_exceptions.id AS exception_id,
                    billable_exceptions.field1_comparator,
                    billable_exceptions.field1_value,
                    billable_exceptions.field2_comparator,
                    billable_exceptions.field2_value,
                    billable_exceptions.price AS exception_price
                FROM
                    pricing_rules
                LEFT OUTER JOIN
                    billable_exceptions
                ON
                    billable_exceptions.pricing_rule_id = pricing_rules.id
                INNER JOIN
                    billable_types
                ON
                    billable_types.id = pricing_rules.billable_type_id";

        $result = $db->query($sql);

        $pricingRules = array();

        if ($db->getNumRows($result) > 0) {
            while($row = $db->fetchObject($result)) {
                if ($row->pricing_rule_id != $pricingRule->id) {
                    $pricingRule = new PricingRule();

                    $pricingRule->id = $row->pricing_rule_id;
                    $pricingRule->contractID = $row->contract_id;
                    $pricingRule->billableTypeID = $row->billable_type_id;
                    $pricingRule->price = $row->unit_price;

                    $key = $pricingRule->contractID . "." . $pricingRule->billableTypeID;

                    $pricingRules[$key] = $pricingRule;
                }

                if ($row->exception_id) {
                    if ($pricingRule->numExceptions == 0) {
                        $pricingRule->exceptionField1Name = $row->field1_name;
                        $pricingRule->exceptionField1Type = $row->field1_type;
                        $pricingRule->exceptionField2Name = $row->field2_name;
                        $pricingRule->exceptionField2Type = $row->field2_type;
                    }

                    $exception = new PricingRuleException();

                    $exception->id = $row->exception_id;
                    $exception->field1Comparator = $row->field1_comparator;
                    $exception->field1Value = $row->field1_value;
                    $exception->field2Comparator = $row->field2_comparator;
                    $exception->field2Value = $row->field2_value;
                    $exception->price = $row->exception_price;

                    $pricingRule->addException($exception);
                }
            }
        }

        return $pricingRules;
    }
}
?>

