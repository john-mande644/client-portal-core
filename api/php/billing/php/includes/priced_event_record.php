<?php
include_once("database.php");

class PricedEventRecord {
    private $id;
    private $clientID;
    private $name;
    private $eventDate;
    private $units;
    private $unitPrice;
    private $glAccountID;
    private $glAccountCode;
    private $applyToMinimum;
    private $departmentID;
    private $departmentCode;
    private $departmentName;
    private $locationID;
    private $locationCode;
    private $adHoc;

    public function getID() { return $this->id; }
    public function setID($value) { $this->id = $value; }

    public function getClientID() { return $this->clientID; }
    public function setClientID($value) { $this->clientID = $value; }

    public function getName() { return $this->name; }
    public function setName($value) { $this->name = $value; }

    public function getEventDate() { return $this->eventDate; }
    public function setEventDate($value) { $this->eventDate = $value; }

    public function getUnits() { return $this->units; }
    public function setUnits($value) { $this->units = $value; }

    public function getUnitPrice() { return $this->unitPrice; }
    public function setUnitPrice($value) { $this->unitPrice = $value; }

    public function getGLAccountID() { return $this->glAccountID; }
    public function setGLAccountID($value) { $this->glAccountID = $value; }

    public function getGLAccountCode() { return $this->glAccountCode; }
    public function setGLAccountCode($value) { $this->glAccountCode = $value; }

    public function getApplyToMinimum() { return $this->applyToMinimum; }
    public function setApplyToMinimum($value) { $this->applyToMinimum = $value; }

    public function getDepartmentID() { return $this->departmentID; }
    public function setDepartmentID($value) { $this->departmentID = $value; }

    public function getDepartmentCode() { return $this->departmentCode; }
    public function setDepartmentCode($value) { $this->departmentCode = $value; }

    public function getDepartmentName() { return $this->departmentName; }
    public function setDepartmentName($value) { $this->departmentName = $value; }

    public function getLocationID() { return $this->locationID; }
    public function setLocationID($value) { $this->locationID = $value; }

    public function getLocationCode() { return $this->locationCode; }
    public function setLocationCode($value) { $this->locationCode = $value; }

    public function getAdHoc() { return $this->adHoc; }
    public function setAdHoc($value) { $this->adHoc = $value; }

    public function insert(&$sql) {
        $db = DBFactory::create();

        $name = $db->prepStrVal($this->name);
        $eventDate = $db->prepDate($this->eventDate);
        $adHoc = $db->prepBoolVal($this->adHoc);

        $sql = "INSERT INTO
                    priced_events
                    (client_id,
                     name,
                     event_date,
                     units,
                     unit_price,
                     gl_account_id,
                     department_id,
                     location_id,
                     ad_hoc)
                VALUES
                    ($this->clientID,
                     $name,
                     $eventDate,
                     $this->units,
                     $this->unitPrice,
                     $this->glAccountID,
                     $this->departmentID,
                     $this->locationID,
                     $adHoc)";

        $result = $db->query($sql);

        $this->id = $db->getInsertID();

        return $result;
    }

    public function update(&$sql) {
        $db = DBFactory::create();

        if ($this->id) {
            $fields = array();

            if (!is_null($this->clientID)) {
                $fields[] = "client_id = " . $this->clientID;
            }
            if (!is_null($this->eventDate)) {
                $fields[] = "event_date = " .
                    $db->prepDate($this->eventDate);
            }
            if (!is_null($this->units)) {
                $fields[] = "units = " . $this->units;
            }
            if (!is_null($this->unitPrice)) {
                $fields[] = "unit_price = " . $this->unitPrice;
            }
            if (!is_null($this->glAccountID)) {
                $fields[] = "gl_account_id = " . $this->glAccountID;
            }
            if (!is_null($this->departmentID)) {
                $fields[] = "department_id = " . $this->departmentID;
            }
            if (!is_null($this->locationID)) {
                $fields[] = "location_id = " . $this->locationID;
            }
            if (!is_null($this->adHoc)) {
                $fields[] = "ad_hoc = " .
                    $db->prepBoolVal($this->adHoc);
            }

            if (count($fields) > 0) {
                $setClause = implode(',', $fields);

                $sql = "UPDATE priced_events
                        SET $setClause
                        WHERE id = $this->id";

                $result = $db->query($sql);
            }
        } else {
            $result = false;
        }

        return $result;
    }

    public function delete($id) {
        $db = DBFactory::create();

        $sql = "DELETE FROM priced_events WHERE id = $id";
        $result = $db->query($sql);

        return $result;
    }

    public function findByID($id) {
        $db = DBFactory::create();

        $sql = "SELECT
                    priced_events.id,
                    priced_events.name,
                    priced_events.units,
                    priced_events.unit_price,
                    priced_events.event_date,
                    priced_events.ad_hoc,
                    priced_events.gl_account_id,
                    priced_events.department_id,
                    priced_events.location_id,
                    gl_accounts.code AS gl_account_code,
                    gl_accounts.apply_to_minimum,
                    departments.code AS department_code,
                    departments.name As department_name,
                    locations.code AS location_code
                FROM
                    priced_events,
                    gl_accounts,
                    departments,
                    locations
                WHERE
                    gl_accounts.id = priced_events.gl_account_id AND
                    departments.id = priced_events.department_id AND
                    locations.id = priced_events.location_id AND
                    priced_events.id = $id";

        $result = $db->query($sql);

        if ($db->getNumRows($result) > 0) {
            while($row = $db->fetchObject($result)) {

                $this->setID($row->id);
                $this->setClientID($row->client_id);
                $this->setName($row->name);
                $this->setEventDate($db->formatDate($row->event_date));
                $this->setUnits($row->units);
                $this->setUnitPrice($row->unit_price);
                $this->setAdHoc($row->ad_hoc);
                $this->setGLAccountID($row->gl_account_id);
                $this->setGLAccountCode($row->gl_account_code);
                $this->setApplyToMinimum($row->apply_to_minimum);
                $this->setDepartmentID($row->department_id);
                $this->setDepartmentCode($row->department_code);
                $this->setDepartmentName($row->department_name);
                $this->setLocationID($row->location_id);
                $this->setLocationCode($row->location_code);
            }
        }
    }

    public static function findByClientIDAndAdHoc($clientID, $adHoc) {
        $db = DBFactory::create();

        $adHoc = $db->prepBoolVal($adHoc);

        $sql = "SELECT
                    priced_events.id,
                    priced_events.name,
                    priced_events.units,
                    priced_events.unit_price,
                    priced_events.event_date,
                    priced_events.ad_hoc,
                    priced_events.gl_account_id,
                    priced_events.department_id,
                    priced_events.location_id,
                    gl_accounts.code AS gl_account_code,
                    gl_accounts.apply_to_minimum,
                    departments.code AS department_code,
                    departments.name As department_name,
                    locations.code AS location_code
                FROM
                    priced_events,
                    gl_accounts,
                    departments,
                    locations
                WHERE
                    gl_accounts.id = priced_events.gl_account_id AND
                    departments.id = priced_events.department_id AND
                    locations.id = priced_events.location_id AND
                    priced_events.ad_hoc = $adHoc AND
                    priced_events.client_id = $clientID";

        $result = $db->query($sql);

        $records = array();

        if ($db->getNumRows($result) > 0) {
            while($row = $db->fetchObject($result)) {
                $record = new PricedEventRecord();

                $record->setID($row->id);
                $record->setClientID($row->client_id);
                $record->setName($row->name);
                $record->setEventDate($db->formatDate($row->event_date));
                $record->setUnits($row->units);
                $record->setUnitPrice($row->unit_price);
                $record->setAdHoc($row->ad_hoc);
                $record->setGLAccountID($row->gl_account_id);
                $record->setGLAccountCode($row->gl_account_code);
                $record->setApplyToMinimum($row->apply_to_minimum);
                $record->setDepartmentID($row->department_id);
                $record->setDepartmentCode($row->department_code);
                $record->setDepartmentName($row->department_name);
                $record->setLocationID($row->location_id);
                $record->setLocationCode($row->location_code);

                $records[$row->id] = $record;
            }
        }

        return $records;
    }

    public static function findByInvoiceID($invoiceID) {
        $db = DBFactory::create();

        $sql = "SELECT
                    priced_events.id,
                    priced_events.name,
                    priced_events.units,
                    priced_events.unit_price,
                    priced_events.event_date,
                    priced_events.ad_hoc,
                    priced_events.gl_account_id,
                    priced_events.department_id,
                    priced_events.location_id,
                    gl_accounts.code AS gl_account_code,
                    gl_accounts.apply_to_minimum,
                    departments.code AS department_code,
                    departments.name As department_name,
                    locations.code AS location_code
                FROM
                    priced_events,
                    invoices,
                    gl_accounts,
                    departments,
                    locations
                WHERE
                    (priced_events.event_date BETWEEN invoices.start_date AND invoices.end_date) AND
                    gl_accounts.id = priced_events.gl_account_id AND
                    departments.id = priced_events.department_id AND
                    locations.id = priced_events.location_id AND
                    invoices.id = $invoiceID";

        $result = $db->query($sql);

        $records = array();

        if ($db->getNumRows($result) > 0) {
            while($row = $db->fetchObject($result)) {
                $record = new PricedEventRecord();

                $record->setID($row->id);
                $record->setClientID($row->client_id);
                $record->setName($row->name);
                $record->setEventDate($db->formatDate($row->event_date));
                $record->setUnits($row->units);
                $record->setUnitPrice($row->unit_price);
                $record->setAdHoc($row->ad_hoc);
                $record->setGLAccountID($row->gl_account_id);
                $record->setGLAccountCode($row->gl_account_code);
                $record->setApplyToMinimum($row->apply_to_minimum);
                $record->setDepartmentID($row->department_id);
                $record->setDepartmentCode($row->department_code);
                $record->setDepartmentName($row->department_name);
                $record->setLocationID($row->location_id);
                $record->setLocationCode($row->location_code);

                $records[$row->id] = $record;
            }
        }

        return $records;
    }
}
?>