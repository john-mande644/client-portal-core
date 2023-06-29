<?php
include_once("database.php");

define("WEEKLY_INVOICE_INTERVAL", 0);
define("MONTHLY_INVOICE_INTERVAL", 1);

class ContractRecord {
    private $id;
    private $clientID;
    private $clientName;
    private $name;
    private $startDate;
    private $endDate;
    private $active;
    private $invoiceInterval;

    public function getID() { return $this->id; }
    public function setID($value) { $this->id = $value; }

    public function getClientID() { return $this->clientID; }
    public function setClientID($value) { $this->clientID = $value; }

    public function getClientName() { return $this->clientName; }
    public function setClientName($value) { $this->clientName = $value; }

    public function getName() { return $this->name; }
    public function setName($value) { $this->name = $value; }

    public function getStartDate() { return $this->startDate; }
    public function setStartDate($value) { $this->startDate = $value; }

    public function getEndDate() { return $this->endDate; }
    public function setEndDate($value) { $this->endDate = $value; }

    public function getActive() { return $this->active; }
    public function setActive($value) { $this->active = $value; }

    public function getInvoiceInterval() { return $this->invoiceInterval; }
    public function setInvoiceInterval($value) { $this->invoiceInterval = $value; }

    public function insert() {
        $db = DBFactory::create();

        $name = $db->prepStrVal($this->name);
        $startDate = $db->prepDate($this->startDate);
        $endDate = $db->prepDate($this->endDate);
        $active = $db->prepBoolVal($this->active);
        $invoiceInterval = $this->invoiceInterval;

        $sql = "INSERT INTO
                    contracts
                    (client_id,
                     name,
                     start_date,
                     end_date,
                     active,
                     invoice_interval)
                VALUES
                    ($this->clientID,
                     $name,
                     $startDate,
                     $endDate,
                     $active,
                     $invoiceInterval)";

        $result = $db->query($sql);

        if ($result) {
            $this->id = $db->getInsertID();
            return $this->id;
        } else {
            return false;
        }
    }

    public function update() {
        $db = DBFactory::create();

        if ($this->id) {

            $fields = array();

            if (!is_null($this->clientID)) {
                $fields[] = "client_id = " . $this->clientID;
            }
            if (!is_null($this->name)) {
                $fields[] = "name = " .
                    $db->prepStrVal($this->name);
            }
            if (!is_null($this->startDate)) {
                $fields[] = "start_date = " .
                    $db->prepDate($this->startDate);
            }
            if (!is_null($this->endDate)) {
                $fields[] = "end_date = " .
                    $db->prepDate($this->endDate);
            }
            if (!is_null($this->active)) {
                $fields[] = "active = " .
                    $db->prepBoolVal($this->active);
            }
            if (!is_null($this->invoiceInterval)) {
                $fields[] = "invoice_interval = " . $this->invoiceInterval;
            }

            if (count($fields) > 0) {
                $setClause = implode(',', $fields);

                $sql = "UPDATE contracts
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

        // Delete recurring items
        $sql = "DELETE
                FROM
                    recurring_items
                WHERE
                    contract_id = $id";
        $result = $db->query($sql);

        // Delete billable exceptions
        $sql = "DELETE
                FROM 
                    billable_exceptions 
                WHERE 
                    pricing_rule_id 
                IN 
                    (SELECT 
                        id AS pricing_rule_id
                    FROM
                        pricing_rules 
                    WHERE 
                        contract_id = $id)";
        $result = $db->query($sql);

        // Delete pricing rules
        $sql = "DELETE
                FROM
                    pricing_rules
                WHERE
                    contract_id = $id";
        $result = $db->query($sql);

        // Delete contract
        $sql = "DELETE FROM contracts WHERE id = $id";
        $result = $db->query($sql);

        return $result;
    }

    public function findByID($id) {
        $db = DBFactory::create();

        $sql = "SELECT
                    contracts.id,
                    contracts.client_id,
                    contracts.name,
                    contracts.start_date,
                    contracts.end_date,
                    contracts.active,
                    contracts.invoice_interval,
                    clients.name AS client_name
                FROM
                    contracts
                INNER JOIN
                    clients
                ON
                    clients.id = contracts.client_id
                WHERE
                    contracts.id = $id";

        $result = $db->query($sql);

        if ($db->getNumRows($result) == 1) {
            $row = $db->fetchObject($result);

            $this->setID($row->id);
            $this->setClientID($row->client_id);
            $this->setClientName($db->unescapeString($row->client_name));
            $this->setName($db->unescapeString($row->name));
            $this->setStartDate($db->formatDate($row->start_date));
            $this->setEndDate($db->formatDate($row->end_date));
            $this->setActive($row->active);
            $this->setInvoiceInterval($row->invoice_interval);

            return true;
        } else {
            return false;
        }
    }

    public function findByClientIDAndDate($clientID, $date) {
        $db = DBFactory::create();

        $date = $db->prepStrVal($date);

        $sql = "SELECT
                    contracts.id,
                    contracts.client_id,
                    contracts.name,
                    contracts.start_date,
                    contracts.end_date,
                    contracts.active,
                    contracts.invoice_interval,
                    clients.name AS client_name
                FROM
                    contracts,
                    clients
                WHERE
                    ($date >= contracts.start_date AND
                     ($date <= contracts.end_date OR contracts.end_date is NULL)) AND
                    contracts.client_id = clients.id AND
                    clients.id = $clientID";

        $result = $db->query($sql);

        if ($db->getNumRows($result) == 1) {
            $row = $db->fetchObject($result);

            $this->setID($row->id);
            $this->setClientID($row->client_id);
            $this->setClientName($db->unescapeString($row->client_name));
            $this->setName($db->unescapeString($row->name));
            $this->setStartDate($db->formatDate($row->start_date));
            $this->setEndDate($db->formatDate($row->end_date));
            $this->setActive($row->active);
            $this->setInvoiceInterval($row->invoice_interval);

            return true;
        } else {
            return false;
        }
    }

    public static function findByClientID($clientID) {
        $db = DBFactory::create();

        $sql = "SELECT
                    contracts.id,
                    contracts.client_id,
                    contracts.name,
                    contracts.start_date,
                    contracts.end_date,
                    contracts.active,
                    contracts.invoice_interval,
                    clients.name AS client_name
                FROM
                    contracts
                INNER JOIN
                    clients
                ON
                    clients.id = contracts.client_id
                WHERE
                    contracts.client_id = $clientID
                ORDER BY
                    contracts.start_date DESC";

        $result = $db->query($sql);

        $records = array();

        if ($db->getNumRows($result) > 0) {
            while($row = $db->fetchObject($result)) {
                $record = new ContractRecord();

                $record->setID($row->id);
                $record->setClientID($row->client_id);
                $record->setClientName($db->unescapeString($row->client_name));
                $record->setName($db->unescapeString($row->name));
                $record->setStartDate($db->formatDate($row->start_date));
                $record->setEndDate($db->formatDate($row->end_date));
                $record->setActive($row->active);
                $record->setInvoiceInterval($row->invoice_interval);

                $records[$row->id] = $record;
            }
        }

        return $records;
    }

    public static function findAll() {
        $db = DBFactory::create();

        $sql = "SELECT
                    contracts.id,
                    contracts.client_id,
                    contracts.name,
                    contracts.start_date,
                    contracts.end_date,
                    contracts.active,
                    contracts.invoice_interval,
                    clients.name AS client_name
                FROM
                    contracts,
                    clients
                WHERE
                    clients.id = contracts.client_id
                ORDER BY
                    clients.name DESC,
                    contracts.start_date DESC";

        $result = $db->query($sql);

        $records = array();

        $numRows = $db->getNumRows($result);

        if ($db->getNumRows($result) > 0) {
            while($row = $db->fetchObject($result)) {
                $record = new ContractRecord();

                $record->setID($row->id);
                $record->setClientID($row->client_id);
                $record->setClientName($db->unescapeString($row->client_name));
                $record->setName($db->unescapeString($row->name));
                $record->setStartDate($db->formatDate($row->start_date));
                $record->setEndDate($db->formatDate($row->end_date));
                $record->setActive($row->active);
                $record->setInvoiceInterval($row->invoice_interval);

                $records[$row->id] = $record;
            }
        }

        return $records;
    }
}
?>