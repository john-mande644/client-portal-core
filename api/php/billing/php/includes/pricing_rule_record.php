<?php
include_once("database.php");

class PricingRuleRecord {
    private $id;
    private $unitPrice;
    private $contractID;
    private $billableTypeID;
    private $billableTypeName;
    private $departmentID;
    private $departmentName;
    private $billableUnitID;
    private $billableUnitName;
    private $billableUnitValueType;
    private $numExceptions;
    private $exceptionField1Name;
    private $exceptionField2Name;

    public function getID() { return $this->id; }
    public function setID($value) { $this->id = $value; }

    public function getUnitPrice() { return $this->unitPrice; }
    public function setUnitPrice($value) { $this->unitPrice = $value; }

    public function getContractID() { return $this->contractID; }
    public function setContractID($value) { $this->contractID = $value; }


    public function getDepartmentID() { return $this->departmentID; }
    public function setDepartmentID($value) { $this->departmentID = $value; }

    public function getDepartmentName() { return $this->departmentName; }
    public function setDepartmentName($value) { $this->departmentName = $value; }


    public function getBillableTypeID() { return $this->billableTypeID; }
    public function setBillableTypeID($value) { $this->billableTypeID = $value; }

    public function getBillableTypeName() { return $this->billableTypeName; }
    public function setBillableTypeName($value) { $this->billableTypeName = $value; }

    public function getExceptionField1Name() { return $this->exceptionField1Name; }
    public function setExceptionField1Name($value) { $this->exceptionField1Name = $value; }

    public function getExceptionField2Name() { return $this->exceptionField2Name; }
    public function setExceptionField2Name($value) { $this->exceptionField2Name = $value; }


    public function getBillableUnitID() { return $this->billableUnitID; }
    public function setBillableUnitID($value) { $this->billableUnitID = $value; }

    public function getBillableUnitName() { return $this->billableUnitName; }
    public function setBillableUnitName($value) { $this->billableUnitName = $value; }

    public function getBillableUnitValueType() { return $this->billableUnitValueType; }
    public function setBillableUnitValueType($value) { $this->billableUnitValueType = $value; }

    public function getNumExceptions() { return $this->numExceptions; }
    public function setNumExceptions($value) { $this->numExceptions = $value; }

    public function insert(&$sql) {
        $db = DBFactory::create();

        $name = $db->prepStrVal($this->name);

        $sql = "INSERT INTO
                    pricing_rules
                    (contract_id,
                     billable_type_id,
                     unit_price)
                VALUES
                    ($this->contractID,
                     $this->billableTypeID,
                     $this->unitPrice)";

        $result = $db->query($sql);

        $this->id = $db->getInsertID();

        return $result;
    }

    public function update(&$sql) {
        $db = DBFactory::create();

        if ($this->id) {
            $fields = array();

            if (!is_null($this->contractID)) {
                $fields[] = "contract_id = " .
                    $this->contractID;
            }
            if (!is_null($this->billableTypeID)) {
                $fields[] = "billable_type_id = " .
                    $this->billableTypeID;
            }
            if (!is_null($this->unitPrice)) {
                $fields[] = "unit_price = " .
                    $this->unitPrice;
            }

            if (count($fields) > 0) {
                $setClause = implode(',', $fields);

                $sql = "UPDATE pricing_rules
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

        $sql = "DELETE FROM billable_exceptions WHERE pricing_rule_id = $id";
        $result = $db->query($sql);

        $sql = "DELETE FROM pricing_rules WHERE id = $id";
        $result = $db->query($sql);

        return $result;
    }

    public function findByID($id) {
        $db = DBFactory::create();

        $sql = "SELECT
                    pricing_rules.id,
                    pricing_rules.unit_price,
                    pricing_rules.contract_id,
                    pricing_rules.billable_type_id,
                    billable_types.name As billable_type_name,
                    billable_types.exception_field1_name,
                    billable_types.exception_field2_name,
                    departments.id AS department_id,
                    departments.name AS department_name,
                    billable_units.id As billable_unit_id,
                    billable_units.name AS billable_unit_name,
                    billable_units.value_type AS billable_unit_value_type
                FROM
                    pricing_rules
                JOIN  
                    billable_types
                ON
                    billable_types.id = pricing_rules.billable_type_id
                INNER JOIN
                    departments
                ON
                    departments.id = billable_types.department_id
                INNER JOIN
                    billable_units
                ON
                    billable_units.id = billable_types.billable_unit_id
                WHERE
                    pricing_rules.id = $id";

        $result = $db->query($sql);

        if ($db->getNumRows($result) == 1) {
            $row = $db->fetchObject($result);

            $this->setID($row->id);
            $this->setUnitPrice($row->unit_price);
            $this->setContractID($row->contract_id);
            $this->setBillableTypeID($row->billable_type_id);
            $this->setBillableTypeName($db->unescapeString($row->billable_type_name));
            $this->setExceptionField1Name($db->unescapeString($row->exception_field1_name));
            $this->setExceptionField2Name($db->unescapeString($row->exception_field2_name));
            $this->setDepartmentID($row->department_id);
            $this->setDepartmentName($db->unescapeString($row->departmentname));
            $this->setBillableUnitID($row->billable_unit_id);
            $this->setBillableUnitName($db->unescapeString($row->billable_unit_name));
            $this->setBillableUnitValueType($row->billable_unit_value_type);
            $this->setNumExceptions($row->num_exceptions);

            return true;
        } else {
            return false;
        }
    }

    public static function findByContractID($contractID) {
        $sql = "SELECT
                    pricing_rules.id,
                    pricing_rules.unit_price,
                    pricing_rules.contract_id,
                    pricing_rules.billable_type_id,
                    billable_types.name As billable_type_name,
                    billable_types.exception_field1_name,
                    billable_types.exception_field2_name,
                    departments.id AS department_id,
                    departments.name AS department_name,
                    billable_units.id As billable_unit_id,
                    billable_units.name AS billable_unit_name,
                    billable_units.value_type AS billable_unit_value_type
                FROM
                    pricing_rules
                JOIN  
                    billable_types
                ON
                    billable_types.id = pricing_rules.billable_type_id
                INNER JOIN
                    departments
                ON
                    departments.id = billable_types.department_id
                INNER JOIN
                    billable_units
                ON
                    billable_units.id = billable_types.billable_unit_id
                WHERE
                    pricing_rules.contract_id = $contractID
                ORDER BY
                    departments.name,
                    billable_types.name";

        return self::createRecordsFromQuery($sql);
    }

    public static function findAll() {
        $sql = "SELECT
                    pricing_rules.id,
                    pricing_rules.unit_price,
                    pricing_rules.contract_id,
                    pricing_rules.billable_type_id,
                    billable_types.name As billable_type_name,
                    billable_types.field1_name As exception_field1_name,
                    billable_types.field2_name As exception_field2_name,
                    departments.id AS department_id,
                    departments.name AS department_name,
                    billable_units.id As billable_unit_id,
                    billable_units.name AS billable_unit_name,
                    billable_units.value_type AS billable_unit_value_type
                FROM
                    pricing_rules
                JOIN  
                    billable_types
                ON
                    billable_types.id = pricing_rules.billable_type_id
                INNER JOIN
                    departments
                ON
                    departments.id = billable_types.department_id
                INNER JOIN
                    billable_units
                ON
                    billable_units.id = billable_types.billable_unit_id
                WHERE
                    1
                ORDER BY
                    departments.name";

        return self::createRecordsFromQuery($sql);
    }


    private static function createRecordsFromQuery($sql) {
        $db = DBFactory::create();
        $result = $db->query($sql);

        $records = array();

        $numRows = $db->getNumRows($result);

        if ($numRows > 0) {
            while($row = $db->fetchObject($result)) {
                $record = new PricingRuleRecord();

                $record->setID($row->id);
                $record->setUnitPrice($row->unit_price);
                $record->setContractID($row->contract_id);
                $record->setBillableTypeID($row->billable_type_id);
                $record->setBillableTypeName($db->unescapeString($row->billable_type_name));
                $record->setExceptionField1Name($db->unescapeString($row->exception_field1_name));
                $record->setExceptionField2Name($db->unescapeString($row->exception_field2_name));
                $record->setDepartmentID($row->department_id);
                $record->setDepartmentName($db->unescapeString($row->department_name));
                $record->setBillableUnitID($row->billable_unit_id);
                $record->setBillableUnitName($db->unescapeString($row->billable_unit_name));
                $record->setBillableUnitValueType($row->billable_unit_value_type);
                $record->setNumExceptions($row->num_exceptions);

                $records[$row->id] = $record;
            }
        }

        return $records;
    }
}
?>