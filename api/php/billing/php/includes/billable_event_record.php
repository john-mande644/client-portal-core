<?php
include_once("database.php");

class BillableEventRecord {
    private $id;
    private $clientID;
    private $clientName;
    private $eventDate;
    private $units;
    private $adHoc;
    private $billableTypeID;
    private $billableTypeName;
    private $exceptionField1Value;
    private $exceptionField2Value;
    private $unitPrice;
    private $contractID;
    private $pricingRuleID;
    private $exceptionID;
    private $glAccountCode;
    private $applyToMinimum;
    private $departmentID;
    private $departmentName;

    public function getID() { return $this->id; }
    public function setID($value) { $this->id = $value; }

    public function getClientID() { return $this->clientID; }
    public function setClientID($value) { $this->clientID = $value; }

    public function getClientName() { return $this->clientName; }
    public function setClientName($value) { $this->clientName = $value; }

    public function getEventDate() { return $this->eventDate; }
    public function setEventDate($value) { $this->eventDate = $value; }

    public function getUnits() { return $this->units; }
    public function setUnits($value) { $this->units = $value; }

    public function getAdHoc() { return $this->adHoc; }
    public function setAdHoc($value) { $this->adHoc = $value; }

    public function getBillableTypeID() { return $this->billableTypeID; }
    public function setBillableTypeID($value) { $this->billableTypeID = $value; }

    public function getExceptionField1Value() { return $this->exceptionField1Value; }
    public function setExceptionField1Value($value) { $this->exceptionField1Value = $value; }

    public function getExceptionField2Value() { return $this->exceptionField2Value; }
    public function setExceptionField2Value($value) { $this->exceptionField2Value = $value; }

    public function getBillableTypeName() { return $this->billableTypeName; }
    public function setBillableTypeName($value) { $this->billableTypeName = $value; }

    public function getUnitPrice() { return $this->unitPrice; }
    public function setUnitPrice($value) { $this->unitPrice = $value; }

    public function getContractID() { return $this->contractID; }
    public function setContractID($value) { $this->contractID = $value; }

    public function getPricingRuleID() { return $this->pricingRuleID; }
    public function setPricingRuleID($value) { $this->pricingRuleID = $value; }

    public function getExceptionID() { return $this->exceptionID; }
    public function setExceptionID($value) { $this->exceptionID = $value; }

    public function getGLAccountCode() { return $this->glAccountCode; }
    public function setGLAccountCode($value) { $this->glAccountCode = $value; }

    public function getApplyToMinimum() { return $this->applyToMinimum; }
    public function setApplyToMinimum($value) { $this->applyToMinimum = $value; }

    public function getDepartmentID() { return $this->departmentID; }
    public function setDepartmentID($value) { $this->departmentID = $value; }

    public function getDepartmentName() { return $this->departmentName; }
    public function setDepartmentName($value) { $this->departmentName = $value; }

    public function insert(&$sql) {
        $db = DBFactory::create();

        $eventDate = $db->prepDate($this->eventDate);
        $exceptionField1Value = $db->prepStrVal($this->exceptionField1Value);
        $exceptionField2Value = $db->prepStrVal($this->exceptionField2Value);
        $adHoc = $db->prepBoolVal($this->adHoc);

        $sql = "INSERT INTO
                    billable_events
                    (client_id,
                     event_date,
                     units,
                     billable_type_id,
                     exception_field1_value,
                     exception_field2_value,
                     ad_hoc)
                VALUES
                    ($this->clientID,
                     $eventDate,
                     $this->units,
                     $this->billableTypeID,
                     $exceptionField1Value,
                     $exceptionField2Value,
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
            if (!is_null($this->billable_type_id)) {
                $fields[] = "billable_type_id = " . $this->billableTypeID;
            }
            if (!is_null($this->exceptionField1Value)) {
                $fields[] = "exception_field1_value = " .
                    $db->prepStrVal($this->exceptionField1Value);
            }
            if (!is_null($this->exceptionField2Value)) {
                $fields[] = "exception_field2_value = " .
                    $db->prepStrVal($this->exceptionField2Value);
            }
            if (!is_null($this->adHoc)) {
                $fields[] = "ad_hoc = " .
                    $db->prepBoolVal($this->adHoc);
            }

            if (count($fields) > 0) {
                $setClause = implode(',', $fields);

                $sql = "UPDATE billable_events
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

        $sql = "DELETE FROM billable_events WHERE id = $id";
        $result = $db->query($sql);

        return $result;
    }

    public function findByID($id) {
        $db = DBFactory::create();

        $sql = "SELECT
                    id,
                    client_id,
                    billable_type_id,
                    units,
                    event_date,
                    ad_hoc,
                    exception_field1_value,
                    exception_field2_value
                FROM
                    billable_events
                WHERE
                    id = $id";

        $result = $db->query($sql);

        if ($db->getNumRows($result) == 1) {
            $row = $db->fetchObject($result);

            $this->setID($row->id);
            $this->setClientID($row->client_id);
            $this->setBillableTypeID($row->billable_type_id);
            $this->setEventDate($db->formatDate($db->unescapeString($row->event_date)));
            $this->setUnits($row->units);
            $this->setAdHoc($row->ad_hoc);
            $this->setExceptionField1Value($db->unescapeString($row->exception_field1_value));
            $this->setExceptionField2Value($db->unescapeString($row->exception_field2_value));

            return true;
        } else {
            return false;
        }
    }

    public static function findByInvoiceID($invoiceID) {
        $db = DBFactory::create();

        $sql = "SELECT
                    billable_events.id,
                    billable_events.billable_type_id,
                    billable_events.units,
                    billable_events.event_date,
                    billable_events.exception_field1_value,
                    billable_events.exception_field2_value,
                    billable_events.ad_hoc,
                    billable_events.unit_price,
                    billable_events.pricing_rule_id,
                    billable_events.exception_id,
                    billable_types.name AS billable_type_name,
                    gl_accounts.code AS gl_account_code,
                    gl_accounts.apply_to_minimum,
                    departments.id AS department_id,
                    departments.name AS department_name,
                    clients.id AS client_id,
                    clients.name AS client_name,
                    contracts.id AS contract_id
                FROM
                    billable_events,
                    billable_types,
                    contracts,
                    clients,
                    invoices,
                    gl_accounts,
                    departments
                WHERE
                    (billable_events.event_date >= contracts.start_date AND 
                     (billable_events.event_date <= contracts.end_date OR 
                      contracts.end_date IS NULL)) AND
                    billable_events.client_id = clients.id AND
                    gl_accounts.id = billable_types.gl_account_id AND
                    departments.id = billable_types.department_id AND
                    billable_types.id = billable_events.billable_type_id AND
                    contracts.id = invoices.contract_id AND
                    clients.id = invoices.client_id AND
                    invoices.id = $invoiceID
                ORDER BY
                    department_name,
                    billable_type_id,
                    exception_id";

        $result = $db->query($sql);

        $records = array();

        if ($db->getNumRows($result) > 0) {
            while($row = $db->fetchObject($result)) {
                $record = new BillableEventRecord();

                $record->setID($row->id);
                $record->setClientID($row->client_id);
                $record->setBillableTypeID($row->billable_type_id);
                $record->setEventDate($db->formatDate($db->unescapeString($row->event_date)));
                $record->setUnits($row->units);
                $record->setAdHoc($row->ad_hoc);
                $record->setExceptionField1Value($db->unescapeString($row->exception_field1_value));
                $record->setExceptionField2Value($db->unescapeString($row->exception_field2_value));
                $record->setClientName($row->client_name);
                $record->setBillableTypeName($row->billable_type_name);
                $record->setUnitPrice($row->unit_price);
                $record->setContractID($row->contract_id);
                $record->setPricingRuleID($row->pricing_rule_id);
                $record->setExceptionID($row->exception_id);
                $record->setGLAccountCode($row->gl_account_code);
                $record->setApplyToMinimum($row->apply_to_minimum);
                $record->setDepartmentID($row->department_id);
                $record->setDepartmentName($row->department_name);

                $records[$row->id] = $record;
            }
        }

        return $records;
    }

    public static function findAdHocByClientID($clientID) {
        $db = DBFactory::create();

        $sql = "SELECT
                    billable_events.id,
                    billable_events.billable_type_id,
                    billable_events.units,
                    billable_events.event_date,
                    billable_events.ad_hoc,
                    billable_events.exception_field1_value,
                    billable_events.exception_field2_value,
                    billable_events.unit_price,
                    billable_events.pricing_rule_id,
                    billable_events.exception_id,
                    billable_types.name AS billable_type_name,
                    gl_accounts.code AS gl_account_code,
                    gl_accounts.apply_to_minimum,
                    departments.id AS department_id,
                    departments.name AS department_name,
                    clients.id AS client_id,
                    clients.name AS client_name,
                    contracts.id AS contract_id
                FROM
                    billable_events,
                    billable_types,
                    contracts,
                    clients,
                    gl_accounts,
                    departments
                WHERE
                    (billable_events.event_date >= contracts.start_date AND 
                     (billable_events.event_date <= contracts.end_date OR 
                      contracts.end_date IS NULL)) AND
                    billable_events.client_id = clients.id AND
                    billable_events.ad_hoc = 1 AND
                    contracts.client_id = clients.id AND
                    gl_accounts.id = billable_types.gl_account_id AND
                    departments.id = billable_types.department_id AND
                    billable_types.id = billable_events.billable_type_id AND
                    clients.id = $clientID
                ORDER BY
                    department_name,
                    billable_type_id,
                    exception_id";

        $result = $db->query($sql);

        $records = array();

        if ($db->getNumRows($result) > 0) {
            while($row = $db->fetchObject($result)) {
                $record = new BillableEventRecord();

                $record->setID($row->id);
                $record->setClientID($row->client_id);
                $record->setClientName($row->client_name);
                $record->setEventDate($db->formatDate($row->event_date));
                $record->setUnits($row->units);
                $record->setAdHoc($row->ad_hoc);
                $record->setBillableTypeID($row->billable_type_id);
                $record->setBillableTypeName($row->billable_type_name);
                $record->setExceptionField1Value($db->unescapeString($row->exception_field1_value));
                $record->setExceptionField2Value($db->unescapeString($row->exception_field2_value));
                $record->setUnitPrice($row->unit_price);
                $record->setContractID($row->contract_id);
                $record->setPricingRuleID($row->pricing_rule_id);
                $record->setExceptionID($row->exception_id);
                $record->setGLAccountCode($row->gl_account_code);
                $record->setApplyToMinimum($row->apply_to_minimum);
                $record->setDepartmentID($row->department_id);
                $record->setDepartmentName($row->department_name);

                $records[$row->id] = $record;
            }
        }

        return $records;
    }
}
?>