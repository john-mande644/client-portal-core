<?php
include_once("database.php");

class DepartmentRecord {
    private $id;
    private $name;
    private $code;

    public function getID() { return $this->id; }
    public function setID($value) { $this->id = $value; }

    public function getName() { return $this->name; }
    public function setName($value) { $this->name = $value; }

    public function getCode() { return $this->code; }
    public function setCode($value) { $this->code = $value; }

    public function insert() {
        $db = DBFactory::create();

        $name = $db->prepStrVal($this->name);
        $code = $db->prepStrVal($this->code);

        $sql = "INSERT INTO
                    departments
                    (name,
                     code)
                VALUES
                    ($name,
                     $code)";

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
            if (!is_null($this->code)) {
                $fields[] = "code = " .
                    $db->prepStrVal($this->code);
            }

            if (count($fields) > 0) {
                $setClause = implode(',', $fields);

                $sql = "UPDATE departments
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

        $sql = "UPDATE departments
                SET deleted = 1
                WHERE id = $id";
        $result = $db->query($sql);

        return $result;
    }

    public static function purge($id) {
        $db = DBFactory::create();

        $sql = "DELETE FROM departments WHERE id = $id";
        $result = $db->query($sql);

        return $result;
    }

    public function findByID($id) {
        $db = DBFactory::create();

        $sql = "SELECT
                    id,
                    name,
                    code
                FROM
                    departments
                WHERE
                    departments.id = $id";

        $result = $db->query($sql);

        if ($db->getNumRows($result) == 1) {
            $row = $db->fetchObject($result);

            $this->id = $row->id;
            $this->name = $db->unescapeString($row->name);
            $this->code = $db->unescapeString($row->code);

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
                    code
                FROM
                    departments";

        $result = $db->query($sql);

        $records = array();

        $numRows = $db->getNumRows($result);

        if ($db->getNumRows($result) > 0) {
            while($row = $db->fetchObject($result)) {
                $record = new DepartmentRecord();

                $record->setID($row->id);
                $record->setName($db->unescapeString($row->name));
                $record->setCode($db->unescapeString($row->code));

                $records[$row->id] = $record;
            }
        }

        return $records;
    }

    private function clear() {
        $this->id = NULL;
        $this->name = NULL;
        $this->code = NULL;
        $this->taxable = NULL;
    }
}
?>