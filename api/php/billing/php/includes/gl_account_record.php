<?php
include_once("database.php");

class GLAccountRecord {
    private $id;
    private $name;
    private $code;
    private $taxable;
    private $applyToMinimum;

    public function getID() { return $this->id; }
    public function setID($value) { $this->id = $value; }

    public function getName() { return $this->name; }
    public function setName($value) { $this->name = $value; }

    public function getCode() { return $this->code; }
    public function setCode($value) { $this->code = $value; }

    public function getTaxable() { return $this->taxable; }
    public function setTaxable($value) { $this->taxable = $value; }

    public function getApplyToMinimum() { return $this->applyToMinimum; }
    public function setApplyToMinimum($value) { $this->applyToMinimum = $value; }

    public function insert() {
        $db = DBFactory::create();

        $name = $db->prepStrVal($this->name);
        $code = $db->prepStrVal($this->code);

        $sql = "INSERT INTO
                    gl_accounts
                    (name,
                     code,
                     taxable,
                     apply_to_minimum)
                VALUES
                    ($name,
                     $code,
                     $this->taxable,
                     $this->apply_to_minimum)";

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
            if (!is_null($this->taxable)) {
                $fields[] = "taxable = " .
                    $this->taxable;
            }
            if (!is_null($this->applyToMinimum)) {
                $fields[] = "apply_to_minimum = " .
                    $this->applyToMinimum;
            }

            if (count($fields) > 0) {
                $setClause = implode(',', $fields);

                $sql = "UPDATE gl_accounts
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

        $sql = "UPDATE gl_accounts
                SET deleted = 1
                WHERE id = $id";
        $result = $db->query($sql);

        return $result;
    }

    public static function purge($id) {
        $db = DBFactory::create();

        $sql = "DELETE FROM gl_accounts WHERE id = $id";
        $result = $db->query($sql);

        return $result;
    }

    public function findByID($id) {
        $db = DBFactory::create();

        $sql = "SELECT
                    id,
                    name,
                    code,
                    taxable,
                    apply_to_minimum
                FROM
                    gl_accounts
                WHERE
                    id = $id";

        $result = $db->query($sql);

        if ($db->getNumRows($result) == 1) {
            $row = $db->fetchObject($result);

            $this->setID($row->id);
            $this->setName($db->unescapeString($row->name));
            $this->setCode($db->unescapeString($row->code));
            $this->setTaxable($row->taxable);
            $this->setApplyToMinimum($row->apply_to_minimum);

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
                    code,
                    taxable,
                    apply_to_minimum
                FROM
                    gl_accounts";

        $result = $db->query($sql);

        $records = array();

        $numRows = $db->getNumRows($result);

        if ($db->getNumRows($result) > 0) {
            while($row = $db->fetchObject($result)) {
                $record = new GLAccountRecord();

                $record->setID($row->id);
                $record->setName($db->unescapeString($row->name));
                $record->setCode($db->unescapeString($row->code));
                $record->setTaxable($row->taxable);
                $record->setApplyToMinimum($row->apply_to_minimum);

                $records[$row->id] = $record;
            }
        }

        return $records;
    }
}
?>