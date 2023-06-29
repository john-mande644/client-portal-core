<?php
include_once("database.php");

class ClientRecord {
    private $id;
    private $name;
    private $numContracts;

    public function getID() { return $this->id; }
    public function setID($value) { $this->id = $value; }

    public function getName() { return $this->name; }
    public function setName($value) { $this->name = $value; }

    public function getNumContracts() { return $this->numContracts; }
    public function setNumContracts($value) { $this->numContracts = $value; }

    public function insert(&$sql) {
        $db = DBFactory::create();

        $name = $db->prepStrVal($this->name);

        $sql = "INSERT INTO
                    clients
                    (id,
                     name)
                VALUES
                    ($this->id,
                     $name)";

        $result = $db->query($sql);

        $this->id = $db->getInsertID();

        return $result;
    }

    public function update(&$sql) {
        $db = DBFactory::create();

        if ($this->id) {

            $fields = array();

            if (!is_null($this->id)) {
                $fields[] = "id = " . $this->id;
            }
            if (!is_null($this->name)) {
                $fields[] = "name = " .
                    $db->prepStrVal($this->name);
            }

            if (count($fields) > 0) {
                $setClause = implode(',', $fields);

                $sql = "UPDATE clients
                        SET $setClause
                        WHERE id = $this->id";

                $result = $db->query($sql);
            }
        } else {
            $result = false;
        }

        return $result;
    }

    public function delete() {
        $db = DBFactory::create();

        $sql = "DELETE FROM clients WHERE id = $this->id";
        $result = $db->query($sql);
    }

    public function findByID($id) {
        $db = DBFactory::create();

        $sql = "SELECT
                    id,
                    name
                FROM
                    clients
                WHERE
                    id = $id";

        $result = $db->query($sql);


        if ($db->getNumRows($result) == 1) {
            $row = $db->fetchObject($result);

            $this->id = $row->id;
            $this->name = $db->unescapeString($row->name);

            return true;
        } else {
            return false;
        }
    }

    public function findAll() {
        $db = DBFactory::create();

        $sql = "SELECT
                    clients.id,
                    clients.name,
                    COUNT(contracts.id) AS num_contracts
                FROM
                    clients
                LEFT JOIN
                    contracts
                ON
                    contracts.client_id = clients.id
                GROUP BY
                    clients.id,
                    clients.name
                ORDER BY
                    clients.name ASC";

        $result = $db->query($sql);

        $records = array();

        $numRows = $db->getNumRows($result);

        if ($db->getNumRows($result) > 0) {
            while($row = $db->fetchObject($result)) {
                $record = new ClientRecord();

                $record->setID($row->id);
                $record->setName($db->unescapeString($row->name));
                $record->setNumContracts($db->unescapeString($row->num_contracts));

                $records[$row->id] = $record;
            }
        }

        return $records;
    }
}
?>