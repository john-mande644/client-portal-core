<?php
include_once("database.php");

class BillableUnitRecord {
    private $id;
    private $name;
    private $valueType;

    public function getID() { return $this->id; }
    public function setID($value) { $this->id = $value; }

    public function getName() { return $this->name; }
    public function setName($value) { $this->name = $value; }

    public function getValueType() { return $this->valueType; }
    public function setValueType($value) { $this->valueType = $value; }

    public function insert() {
        $db = DBFactory::create();

        $name = $db->prepStrVal($this->name);

        $sql = "INSERT INTO
                    billable_units
                    (name,
                     value_type)
                VALUES
                    ($name,
                     $this->valueType)";

        $result = $db->query($sql);

        $this->id = $db->getInsertID();

        return $result;
    }

    public function update() {
        $db = DBFactory::create();

        if ($this->id) {
            $fields = array();

            if (!is_null($this->name)) {
                $fields[] = "name = " .
                    $db->prepStrVal($this->name);
            }
            if (!is_null($this->valueType)) {
                $fields[] = "value_type = " .
                    $this->valueType;
            }

            if (count($fields) > 0) {
                $setClause = implode(',', $fields);

                $sql = "UPDATE billable_units
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

        $sql = "DELETE FROM billable_units WHERE id = $id";
        $result = $db->query($sql);

        return $result;
    }

    public function findByID($id) {
        $db = DBFactory::create();

        $sql = "SELECT
                    id,
                    name,
                    value_type
                FROM
                    billable_units
                WHERE
                    id = $id";

        $result = $db->query($sql);

        if ($db->getNumRows($result) == 1) {
            $row = $db->fetchObject($result);

            $this->setID($row->id);
            $this->setName($db->unescapeString($row->name));
            $this->setValueType($row->value_type);

            return true;
        } else {
            return false;
        }
    }

    public static function findAll() {
        $db = DBFactory::create();

        $sql = "SELECT
                    id,
                    name,
                    value_type
                FROM
                    billable_units
                ORDER BY
                    name ASC";

        $result = $db->query($sql);

        $records = array();

        $numRows = $db->getNumRows($result);

        if ($numRows > 0) {
            while($row = $db->fetchObject($result)) {
                $record = new BillableUnitRecord();

                $record->setID($row->id);
                $record->setName($db->unescapeString($row->name));
                $record->setValueType($row->value_type);

                $records[$row->id] = $record;
            }
        }

        return $records;
    }
}
?>