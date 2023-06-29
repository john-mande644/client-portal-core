<?php
include_once("database.php");

class InvoiceRecord {
    private $id;
    private $invoiceNumber;
    private $clientID;
    private $contractID;
    private $startDate;
    private $endDate;
    private $closed;
    private $contractName;
    private $invoiceInterval;

    public function getID() { return $this->id; }
    public function setID($value) { $this->id = $value; }

    public function getInvoiceNumber() { return $this->invoiceNumber; }
    public function setInvoiceNumber($value) { $this->invoiceNumber = $value; }

    public function getClientID() { return $this->clientID; }
    public function setClientID($value) { $this->clientID = $value; }

    public function getContractID() { return $this->contractID; }
    public function setContractID($value) { $this->contractID = $value; }

    public function getStartDate() { return $this->startDate; }
    public function setStartDate($value) { $this->startDate = $value; }

    public function getEndDate() { return $this->endDate; }
    public function setEndDate($value) { $this->endDate = $value; }

    public function getClosed() { return $this->closed; }
    public function setClosed($value) { $this->closed = $value; }

    public function getContractName() { return $this->contractName; }
    public function setContractName($value) { $this->contractName = $value; }

    public function getInvoiceInterval() { return $this->invoiceInterval; }
    public function setInvoiceInterval($value) { $this->invoiceInterval = $value; }

    public function insert(&$sql) {
        $db = DBFactory::create();

        $startDate = $db->prepStrVal($this->startDate);
        $endDate = $db->prepStrVal($this->endDate);
        $closed = $db->prepBoolVal($this->closed);

        $sql = "IF NOT EXISTS
                    (SELECT *
                     FROM
                          invoices
                     WHERE
                          invoice_number = $this->invoiceNumber AND
                          client_id = $this->clientID)
                BEGIN
                    INSERT INTO
                        invoices
                        (invoice_number,
                         client_id,
                         contract_id,
                         start_date,
                         end_date,
                         closed)
                    VALUES
                        ($this->invoiceNumber,
                         $this->clientID,
                         $this->contractID,
                         $startDate,
                         $endDate,
                         $closed)
                END";

        $result = $db->query($sql);

        $this->id = $db->getInsertID();

        return $result;
    }

    public function update() {
        $db = DBFactory::create();

        if ($this->id) {
            $fields = array();

            if (!is_null($this->invoice_number)) {
                $fields[] = "invoice_number = " . $this->invoiceNumber;
            }
            if (!is_null($this->clientID)) {
                $fields[] = "client_id = " . $this->clientID;
            }
            if (!is_null($this->contractID)) {
                $fields[] = "contract_id = " . $this->contractID;
            }
            if (!is_null($this->startDate)) {
                $fields[] = "start_date = " .
                    $db->prepDate($this->startDate);
            }
            if (!is_null($this->endDate)) {
                $fields[] = "end_date = " .
                    $db->prepDate($this->endDate);
            }
            if (!is_null($this->closed)) {
                $fields[] = "closed = " .
                    $db->prepBoolVal($this->closed);
            }

            if (count($fields) > 0) {
                $setClause = implode(',', $fields);

                $sql = "UPDATE invoices
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

        $sql = "DELETE FROM invoices WHERE id = $id";
        $result = $db->query($sql);

        return $result;
    }

    public function findByID($id) {
        $db = DBFactory::create();

        $sql = "SELECT
                    invoices.id,
                    invoices.invoice_number,
                    invoices.client_id,
                    invoices.contract_id,
                    invoices.start_date,
                    invoices.end_date,
                    invoices.closed,
                    contracts.name AS contract_name,
                    contracts.invoice_interval
                FROM
                    invoices
                JOIN
                    contracts
                ON
                    contracts.id = invoices.contract_id
                WHERE
                    invoices.id = $id";

        $result = $db->query($sql);

        if ($db->getNumRows($result) == 1) {
            $row = $db->fetchObject($result);

            $this->setID($row->id);
            $this->setInvoiceNumber($row->invoice_number);
            $this->setClientID($row->client_id);
            $this->setContractID($row->contract_id);
            $this->setStartDate($db->formatDate($row->start_date));
            $this->setEndDate($db->formatDate($row->end_date));
            $this->setClosed($row->closed);
            $this->setContractName($db->unescapeString($row->contract_name));
            $this->setInvoiceInterval($row->invoice_Interval);

            return true;
        } else {
            return false;
        }
    }

    public function findByClientIDAndInvoiceNumber($clientID, $invoiceNumber) {
        $db = DBFactory::create();

        $sql = "SELECT
                    invoices.id,
                    invoices.invoice_number,
                    invoices.client_id,
                    invoices.contract_id,
                    invoices.start_date,
                    invoices.end_date,
                    invoices.closed,
                    contracts.name AS contract_name,
                    contracts.invoice_interval
                FROM
                    invoices
                JOIN
                    contracts
                ON
                    contracts.id = invoices.contract_id
                WHERE
                    invoices.client_id = $clientID AND
                    invoices.invoice_number = $invoiceNumber";

        $result = $db->query($sql);

        if ($db->getNumRows($result) == 1) {
            $row = $db->fetchObject($result);

            $this->setID($row->id);
            $this->setInvoiceNumber($row->invoice_number);
            $this->setClientID($row->client_id);
            $this->setContractID($row->contract_id);
            $this->setStartDate($db->formatDate($row->start_date));
            $this->setEndDate($db->formatDate($row->end_date));
            $this->setClosed($row->closed);
            $this->setContractName($db->unescapeString($row->contract_name));
            $this->setInvoiceInterval($row->invoice_Interval);

            return true;
        } else {
            return false;
        }
    }

    public function findByClientIDAndDate($clientID, $date) {
        $db = DBFactory::create();

        $date = $db->prepStrVal($date);

        $sql = "SELECT
                    invoices.id,
                    invoices.invoice_number,
                    invoices.client_id,
                    invoices.contract_id,
                    invoices.start_date,
                    invoices.end_date,
                    invoices.closed,
                    contracts.name AS contract_name,
                    contracts.invoice_interval
                FROM
                    invoices
                JOIN
                    contracts
                ON
                    contracts.id = invoices.contract_id
                WHERE
                    invoices.client_id = $clientID AND
                    ($date BETWEEN invoices.start_date AND invoices.end_date)";

        $result = $db->query($sql);

        if ($db->getNumRows($result) == 1) {
            $row = $db->fetchObject($result);

            $this->setID($row->id);
            $this->setInvoiceNumber($row->invoice_number);
            $this->setClientID($row->client_id);
            $this->setContractID($row->contract_id);
            $this->setStartDate($db->formatDate($row->start_date));
            $this->setEndDate($db->formatDate($row->end_date));
            $this->setClosed($row->closed);
            $this->setContractName($db->unescapeString($row->contract_name));
            $this->setInvoiceInterval($row->invoice_Interval);

            return true;
        } else {
            return false;
        }
    }

    public function findByClientID($clientID) {
        $db = DBFactory::create();

        $sql = "SELECT
                    invoices.id,
                    invoices.invoice_number,
                    invoices.client_id,
                    invoices.contract_id,
                    invoices.start_date,
                    invoices.end_date,
                    invoices.closed,
                    contracts.name AS contract_name,
                    contracts.invoice_interval
                FROM
                    invoices
                JOIN
                    contracts
                ON
                    contracts.id = invoices.contract_id
                WHERE
                    invoices.client_id = $clientID
                ORDER BY
                    invoices.start_date DESC";

        $result = $db->query($sql);

        $records = array();

        if ($db->getNumRows($result) > 0) {
            while($row = $db->fetchObject($result)) {
                $record = new InvoiceRecord();

                $record->setID($row->id);
                $record->setInvoiceNumber($row->invoice_number);
                $record->setClientID($row->client_id);
                $record->setContractID($row->contract_id);
                $record->setStartDate($db->formatDate($row->start_date));
                $record->setEndDate($db->formatDate($row->end_date));
                $record->setClosed($row->closed);
                $record->setContractName($db->unescapeString($row->contract_name));
                $record->setInvoiceInterval($row->invoice_Interval);

                $records[$row->id] = $record;
            }
        }

        return $records;
    }
}
?>