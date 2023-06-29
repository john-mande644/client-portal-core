<?php
include_once("database.php");

class DepartmentMinimumRecord {
    private $id;
    private $contractID;
    private $departmentID;
    private $amount;
    private $departmentCode;
    private $departmentName;

    public function getID() { return $this->id; }
    public function setID($value) { $this->id = $value; }

    public function getContractID() { return $this->contractID; }
    public function setContractID($value) { $this->contractID = $value; }

    public function getDepartmentID() { return $this->departmentID; }
    public function setDepartmentID($value) { $this->departmentID = $value; }

    public function getAmount() { return $this->amount; }
    public function setAmount($value) { $this->amount = $value; }

    public function getDepartmentCode() { return $this->departmentCode; }
    public function setDepartmentCode($value) { $this->departmentCode = $value; }

    public function getDepartmentName() { return $this->departmentName; }
    public function setDepartmentName($value) { $this->departmentName = $value; }

    public function insert() {
        $db = DBFactory::create();

        $sql = "INSERT INTO
                    department_minimums
                    (contract_id,
                     department_id,
                     amount)
                VALUES
                    ($this->contractID,
                     $this->departmentID,
                     $this->amount)";

        $result = $db->query($sql);

        $this->id = $db->getInsertID();

        return $result;
    }

    public function update() {
        $db = DBFactory::create();

        $sql = "UPDATE
                    department_minimums
                SET
                    amount = $this->amount
                WHERE
                    contract_id = $this->contractID AND
                    department_id = $this->departmentID";

        $result = $db->query($sql);

        return $result;
    }

    public static function purge($id) {
        $db = DBFactory::create();

        $sql = "DELETE FROM department_minimums WHERE id = $id";
        $result = $db->query($sql);

        return $result;
    }

    public static function findByContractID($contractID) {
        $db = DBFactory::create();

        if ($contractID) {
            $sql = "SELECT
                        departments.id AS department_id,
                        departments.code AS department_code,
                        departments.name AS department_name,
                        department_minimums.id,
                        department_minimums.amount AS amount
                    FROM
                        departments
                    LEFT OUTER JOIN
                        department_minimums
                    ON
                        department_minimums.department_id = departments.id AND
                        department_minimums.contract_id = $contractID";
        } else {
            $sql = "SELECT
                        departments.id AS department_id,
                        departments.code AS department_code,
                        departments.name AS department_name
                    FROM
                        departments";
        }

        $result = $db->query($sql);

        $records = array();

        $numRows = $db->getNumRows($result);

        if ($db->getNumRows($result) > 0) {
            while($row = $db->fetchObject($result)) {
                $record = new DepartmentMinimumRecord();

                $record->setDepartmentID($row->department_id);
                $record->setDepartmentCode($db->unescapeString($row->department_code));
                $record->setDepartmentName($db->unescapeString($row->department_name));
                $record->setID($row->id);
                $record->setAmount($row->amount);

                $records[$row->department_id] = $record;
            }
        }

        return $records;
    }

    public static function findByClientID($clientID) {
        $db = DBFactory::create();

        $sql = "SELECT
                    departments.id AS department_id,
                    departments.code AS department_code,
                    departments.name AS department_name,
                    department_minimums.id,
                    department_minimums.amount AS amount
                FROM
                    departments
                LEFT OUTER JOIN
                    department_minimums
                ON
                    department_minimums.department_id = departments.id AND
                    department_minimums.contract_id = contracts.id
                WHERE
                    (CURDATE() BETWEEN contracts.start_date AND contracts.end_date) AND
                    contracts.client_id = clients.id AND
                    clients.external_id = $clientID";

        $result = $db->query($sql);

        $records = array();

        $numRows = $db->getNumRows($result);

        if ($db->getNumRows($result) > 0) {
            while($row = $db->fetchObject($result)) {
                $record = new DepartmentMinimumRecord();

                $record->setID($row->id);
                $record->setDepartmentID($row->department_id);
                $record->setAmount($row->amount);
                $record->setDepartmentCode($db->unescapeString($row->department_code));
                $record->setDepartmentName($db->unescapeString($row->department_name));

                $records[$row->department_id] = $record;
            }
        }

        return $records;
    }
}
?>