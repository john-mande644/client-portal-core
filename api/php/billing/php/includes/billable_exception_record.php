<?php
include_once("database.php");

class BillableExceptionRecord {
    private $id;
    private $pricingRuleID;
    private $field1Value;
    private $field1Comparator;
    private $field2Value;
    private $field2Comparator;
    private $price;
    private $field1Name;
    private $field1Type;
    private $field2Name;
    private $field2Type;

    public function getID() { return $this->id; }
    public function setID($value) { $this->id = $value; }

    public function getPricingRuleID() { return $this->pricingRuleID; }
    public function setPricingRuleID($value) { $this->pricingRuleID = $value; }

    public function getField1Value() { return $this->field1Value; }
    public function setField1Value($value) { $this->field1Value = $value; }

    public function getField1Comparator() { return $this->field1Comparator; }
    public function setField1Comparator($value) { $this->field1Comparator = $value; }

    public function getField2Value() { return $this->field2Value; }
    public function setField2Value($value) { $this->field2Value = $value; }

    public function getField2Comparator() { return $this->field2Comparator; }
    public function setField2Comparator($value) { $this->field2Comparator = $value; }

    public function getPrice() { return $this->price; }
    public function setPrice($value) { $this->price = $value; }

    public function getField1Name() { return $this->field1Name; }
    public function setField1Name($value) { $this->field1Name = $value; }

    public function getField1Type() { return $this->field1Type; }
    public function setField1Type($value) { $this->field1Type = $value; }

    public function getField2Name() { return $this->field2Name; }
    public function setField2Name($value) { $this->field2Name = $value; }

    public function getField2Type() { return $this->field2Type; }
    public function setField2Type($value) { $this->field2Type = $value; }

    public function insert() {
        $db = DBFactory::create();

        $field1Value = $db->prepStrVal($this->field1Value);
        $field1Comparator = $db->prepStrVal($this->field1Comparator);
        $field2Value = $db->prepStrVal($this->field2Value);
        $field2Comparator = $db->prepStrVal($this->field2Comparator);

        $sql = "INSERT INTO
                    billable_exceptions
                    (pricing_rule_id,
                     field1_comparator,
                     field1_value,
                     field2_comparator,
                     field2_value,
                     price)
                VALUES
                    ($this->pricingRuleID,
                     $field1Comparator,
                     $field1Value,
                     $field2Comparator,
                     $field2Value,
                     $this->price)";

        $result = $db->query($sql);

        $this->id = $db->getInsertID();

        return $result;
    }

    public function update() {
        $db = DBFactory::create();

        if ($this->id) {
            $fields = array();

            if (!is_null($this->pricingRuleID)) {
                $fields[] = "pricing_rule_id = " .
                    $this->pricingRuleID;
            }
            if (!is_null($this->field1Value)) {
                $fields[] = "field1_value = " .
                    $db->prepStrVal($this->field1Value);
            }
            if (!is_null($this->field1Comparator)) {
                $fields[] = "field1_comparator = " .
                    $db->prepStrVal($this->field1Comparator);
            }
            if (!is_null($this->field2Value)) {
                $fields[] = "field2_value = " .
                    $db->prepStrVal($this->field2Value);
            }
            if (!is_null($this->field2Comparator)) {
                $fields[] = "field2_comparator = " .
                    $db->prepStrVal($this->field2Comparator);
            }
            if (!is_null($this->price)) {
                $fields[] = "price = " .
                    $this->price;
            }

            if (count($fields) > 0) {
                $setClause = implode(',', $fields);

                $sql = "UPDATE billable_exceptions
                        SET $setClause
                        WHERE id = $this->id";

                $result = $db->query($sql);
            }
        } else {
            $result = false;
        }

        return $result;
    }

    public static function delete($id) {
        $db = DBFactory::create();

        $sql = "DELETE FROM billable_exceptions WHERE id = $id";
        $result = $db->query($sql);

        return $result;
    }

    public function findByID($id) {
        $db = DBFactory::create();

        $sql = "SELECT
                    billable_exceptions.id,
                    billable_exceptions.pricing_rule_id,
                    billable_exceptions.field1_value,
                    billable_exceptions.field1_comparator,
                    billable_exceptions.field2_value,
                    billable_exceptions.field2_comparator,
                    billable_exceptions.price,
                    billable_types.exception_field1_name AS field1_name,
                    billable_types.exception_field1_type AS field1_type,
                    billable_types.exception_field2_name AS field2_name,
                    billable_types.exception_field2_type AS field2_type
                FROM
                    billable_exceptions
                INNER JOIN
                    pricing_rules
                ON
                    pricing_rules.id = billable_exceptions.pricing_rule_id  
                INNER JOIN
                    billable_types
                ON
                    billable_types.id = pricing_rules.billable_type_id
                WHERE
                    billable_exceptions.id = $id";

        $result = $db->query($sql);

        if ($db->getNumRows($result) == 1) {
            $row = $db->fetchObject($result);

            $this->setID($row->id);
            $this->setPricingRuleID($row->pricing_rule_id);
            $this->setField1Value($db->unescapeString($row->field1_value));
            $this->setField1Comparator($db->unescapeString($row->field1_comparator));
            $this->setField2Value($db->unescapeString($row->field2_value));
            $this->setField2Comparator($db->unescapeString($row->field2_comparator));
            $this->setPrice($row->price);
            $this->setField1Name($db->unescapeString($row->field1_name));
            $this->setField1Type($db->unescapeString($row->field1_type));
            $this->setField2Name($db->unescapeString($row->field2_name));
            $this->setField2Type($db->unescapeString($row->field2_type));

            return true;
        } else {
            return false;
        }
    }

    public static function findByContractID($contractID) {
        $db = DBFactory::create();

        $sql = "SELECT
                    billable_exceptions.id,
                    billable_exceptions.pricing_rule_id,
                    billable_exceptions.field1_value,
                    billable_exceptions.field1_comparator,
                    billable_exceptions.field2_value,
                    billable_exceptions.field2_comparator,
                    billable_exceptions.price,
                    billable_types.exception_field1_name AS field1_name,
                    billable_types.exception_field1_type AS field1_type,
                    billable_types.exception_field2_name AS field2_name,
                    billable_types.exception_field2_type AS field2_type
                FROM
                    pricing_rules,
                    billable_types,
                    billable_exceptions
                WHERE
                    billable_exceptions.pricing_rule_id = pricing_rules.id AND
                    billable_types.id = pricing_rules.billable_type_id AND
                    pricing_rules.contract_id = $contractID";

        return self::createRecordsFromQuery($db, $sql);
    }

    public static function findByClientID($clientID) {
        $db = DBFactory::create();

        $date = $db->prepDate($date);

        $sql = "SELECT
                    billable_exceptions.id,
                    billable_exceptions.pricing_rule_id,
                    billable_exceptions.field1_value,
                    billable_exceptions.field1_comparator,
                    billable_exceptions.field2_value,
                    billable_exceptions.field2_comparator,
                    billable_exceptions.price,
                    billable_types.exception_field1_name AS field1_name,
                    billable_types.exception_field1_type AS field1_type,
                    billable_types.exception_field2_name AS field2_name,
                    billable_types.exception_field2_type AS field2_type
                FROM
                    pricing_rules,
                    billable_types,
                    billable_exceptions,
                    contracts
                WHERE
                    billable_exceptions.pricing_rule_id = pricing_rules.id AND
                    billable_types.id = pricing_rules.billable_type_id AND
                    pricing_rules.contract_id = contracts.id AND
                    ($date BETWEEN contracts.start_date AND contracts.end_date) AND
                    contracts.client_id = $clientID";

        return self::createRecordsFromQuery($db, $sql);
    }

    public static function findByPricingRuleID($pricingRuleID) {
        $db = DBFactory::create();

        $sql = "SELECT
                    billable_exceptions.id,
                    billable_exceptions.pricing_rule_id,
                    billable_exceptions.field1_value,
                    billable_exceptions.field1_comparator,
                    billable_exceptions.field2_value,
                    billable_exceptions.field2_comparator,
                    billable_exceptions.price,
                    billable_types.exception_field1_name AS field1_name,
                    billable_types.exception_field1_type AS field1_type,
                    billable_types.exception_field2_name AS field2_name,
                    billable_types.exception_field2_type AS field2_type
                FROM
                    billable_exceptions
                INNER JOIN
                    pricing_rules
                ON
                    pricing_rules.id = billable_exceptions.pricing_rule_id  
                INNER JOIN
                    billable_types
                ON
                    billable_types.id = pricing_rules.billable_type_id
                WHERE
                    billable_exceptions.pricing_rule_id = $pricingRuleID";

        return self::createRecordsFromQuery($db, $sql);
    }

    public static function findAll() {
        $db = DBFactory::create();

        $sql = "SELECT
                    billable_exceptions.id,
                    billable_exceptions.pricing_rule_id,
                    billable_exceptions.field1_value,
                    billable_exceptions.field1_comparator,
                    billable_exceptions.field2_value,
                    billable_exceptions.field2_comparator,
                    billable_exceptions.price,
                    billable_types.exception_field1_name AS field1_name,
                    billable_types.exception_field1_type AS field1_type,
                    billable_types.exception_field2_name AS field2_name,
                    billable_types.exception_field2_type AS field2_type
                FROM
                    billable_exceptions
                INNER JOIN
                    pricing_rules
                ON
                    pricing_rules.id = billable_exceptions.pricing_rule_id  
                INNER JOIN
                    billable_types
                ON
                    billable_types.id = pricing_rules.billable_type_id
                WHERE
                    1
                ORDER BY
                    name ASC";

        return self::createRecordsFromQuery($db, $sql);
    }

    private static function createRecordsFromQuery($db, $sql) {
        $result = $db->query($sql);

        $records = array();

        $numRows = $db->getNumRows($result);

        if ($numRows > 0) {
            while($row = $db->fetchObject($result)) {
                $record = new BillableExceptionRecord();

                $record->setID($row->id);
                $record->setPricingRuleID($row->pricing_rule_id);
                $record->setField1Value($db->unescapeString($row->field1_value));
                $record->setField1Comparator($db->unescapeString($row->field1_comparator));
                $record->setField2Value($db->unescapeString($row->field2_value));
                $record->setField2Comparator($db->unescapeString($row->field2_comparator));
                $record->setPrice($row->price);
                $record->setField1Name($db->unescapeString($row->field1_name));
                $record->setField1Type($db->unescapeString($row->field1_type));
                $record->setField2Name($db->unescapeString($row->field2_name));
                $record->setField2Type($db->unescapeString($row->field2_type));

                $records[$row->id] = $record;
            }
        }

        return $records;
    }
}
?>