<?php
include_once("database.php");

class BillableExceptionTypeRecord {
    private $id;
    private $billableTypeID;
    private $field1Name;
    private $field1ValueType;
    private $field2Name;
    private $field2ValueType;

    public function getID() { return $this->id; }
    public function setID($value) { $this->id = $value; }

    public function getBillableTypeID() { return $this->billableTypeID; }
    public function setBillableTypeID($value) { $this->billableTypeID = $value; }

    public function getField1Name() { return $this->field1Name; }
    public function setField1Name($value) { $this->field1Name = $value; }

    public function getField1ValueType() { return $this->field1ValueType; }
    public function setField1ValueType($value) { $this->field1ValueType = $value; }

    public function getField2Name() { return $this->field2Name; }
    public function setField2Name($value) { $this->field2Name = $value; }

    public function getField2ValueType() { return $this->field2ValueType; }
    public function setField2ValueType($value) { $this->field2ValueType = $value; }

    public function insert() {
        $db = DBFactory::create();

        $field1Name = $db->prepStrVal($this->field1Name);

        if (!is_null($this->field2Name)) {
            $field2Name = $db->prepStrVal($this->field2Name);
            $field2ValueType = $this->field2ValueType;
        } else {
            $field2Name = "NULL1";
            $field2ValueType = "NULL";
        }

        $sql = "INSERT INTO
                    billable_exception_types
                    (billable_type_id,
                     field1_name,
                     field1_value_type
                     field2_name,
                     field2_value_type)
                VALUES
                    ($this->billableTypeID,
                     $field1Name,
                     $this->field1ValueType
                     $field2Name,
                     $field2ValueType)";

        $result = $db->query($sql);

        $this->id = $db->getInsertID();

        return $result;
    }

    public function update() {
        $db = DBFactory::create();

        if ($this->id) {
            $fields = array();


            if (!is_null($this->billableTypeID)) {
                $fields[] = "billable_type_id = " .
                    $this->billableTypeID;
            }
            if (!is_null($this->field1Name)) {
                $fields[] = "field1_name = " .
                    $db->prepStrVal($this->field1Name);
            }
            if (!is_null($this->field1ValueType)) {
                $fields[] = "field1_value_type = " .
                    $this->field1ValueType;
            }
            if (!is_null($this->field2Name)) {
                $fields[] = "field2_name = " .
                    $db->prepStrVal($this->field2Name);
            }
            if (!is_null($this->field2ValueType)) {
                $fields[] = "field2_value_type = " .
                    $this->field2ValueType;
            }

            if (count($fields) > 0) {
                $setClause = implode(',', $fields);

                $sql = "UPDATE billable_exception_types
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

        $sql = "DELETE FROM billable_exception_types WHERE id = $id";
        $result = $db->query($sql);

        return $result;
    }

    public function findByID($id) {
        $db = DBFactory::create();

        $sql = "SELECT
                    id,
                    billable_type_id,
                    field1_name,
                    field1_value_type,
                    field2_name,
                    field2_value_type
                FROM
                    billable_exception_types
                WHERE
                    id = $id";

        $result = $db->query($sql);

        if ($db->getNumRows($result) == 1) {
            $row = $db->fetchObject($result);

            $this->setID($row->id);
            $this->setBillableTypeID($row->billable_type_id);
            $this->setField1Name($db->unescapeString($row->field1_name));
            $this->setField1ValueType($row->field1_value_type);
            $this->setField2Name($db->unescapeString($row->field2_name));
            $this->setField2ValueType($row->field2_value_type);

            return true;
        } else {
            return false;
        }
    }

    public function findByBillableTypeID($billableTypeID) {
        $db = DBFactory::create();

        $sql = "SELECT
                    id,
                    billable_type_id,
                    field1_name,
                    field1_value_type,
                    field2_name,
                    field2_value_type
                FROM
                    billable_exception_types
                WHERE
                    billable_type_id = $billableTypeID";

        $result = $db->query($sql);

        return createRecordsFromQueryResult($result);
    }

    public static function findAll() {
        $db = DBFactory::create();

        $sql = "SELECT
                    id,
                    billable_type_id
                    field1_name,
                    field1_value_type,
                    field2_name,
                    field2_value_type
                FROM
                    billable_exception_types
                WHERE
                    1";

        $result = $db->query($sql);

        return createRecordsFromQueryResult($result);
    }

    private static function createRecordsFromQueryResult($result) {
        $records = array();

        $numRows = $db->getNumRows($result);

        if ($numRows > 0) {
            while($row = $db->fetchObject($result)) {
                $record = new BillableExceptionTypeRecord();

                $record->setID($row->id);
                $record->setBillableTypeID($row->billable_type_id);
                $record->setField1Name($db->unescapeString($row->field1_name));
                $record->setField1ValueType($row->field1_value_type);
                $record->setField2Name($db->unescapeString($row->field2_name));
                $record->setField2ValueType($row->field2_value_type);

                $records[$row->id] = $record;
            }
        }

        return $records;
    }
}
?>