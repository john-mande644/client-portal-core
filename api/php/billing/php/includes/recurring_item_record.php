<?php
include_once("database.php");

class RecurringItemRecord {
    private $id;
    private $displayName;
    private $moneyValue;
    private $contractID;
    private $glAccountID;
    private $glAccountCode;
    private $glAccountName;
    private $applyToMinimum;
    private $departmentID;
    private $departmentCode;
    private $departmentName;
    private $locationID;
    private $locationCode;
    private $locationName;

    public function getID() { return $this->id; }
    public function setID($value) { $this->id = $value; }

    public function getDisplayName() { return $this->displayName; }
    public function setDisplayName($value) { $this->displayName = $value; }

    public function getMoneyValue() { return $this->moneyValue; }
    public function setMoneyValue($value) { $this->moneyValue = $value; }

    public function getContractID() { return $this->contractID; }
    public function setContractID($value) { $this->contractID = $value; }


    public function getGLAccountID() { return $this->glAccountID; }
    public function setGLAccountID($value) { $this->glAccountID = $value; }

    public function getGLAccountCode() { return $this->glAccountCode; }
    public function setGLAccountCode($value) { $this->glAccountCode = $value; }

    public function getGLAccountName() { return $this->glAccountName; }
    public function setGLAccountName($value) { $this->glAccountName = $value; }

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

    public function getLocationName() { return $this->locationName; }
    public function setLocationName($value) { $this->locationName = $value; }


    public function insert(&$sql) {
        $db = DBFactory::create();

        $displayName = $db->prepStrVal($this->displayName);

        $sql = "INSERT INTO
                    recurring_items
                    (display_name,
                     money_value,
                     contract_id,
                     gl_account_id,
                     department_id,
                     location_id)
                VALUES
                    ($displayName,
                     $this->moneyValue,
                     $this->contractID,
                     $this->glAccountID,
                     $this->departmentID,
                     $this->locationID)";

        $result = $db->query($sql);

        $this->id = $db->getInsertID();

        return $result;
    }

    public function update(&$sql) {
        $db = DBFactory::create();

        if ($this->id) {
            $fields = array();

            if (!is_null($this->displayName)) {
                $fields[] = "display_name = " .
                    $db->prepStrVal($this->displayName);
            }
            if (!is_null($this->moneyValue)) {
                $fields[] = "money_value = " .
                    $this->moneyValue;
            }
            if (!is_null($this->contractID)) {
                $fields[] = "contract_id = " .
                    $this->contractID;
            }
            if (!is_null($this->glAccountID)) {
                $fields[] = "gl_account_id = " .
                    $this->glAccountID;
            }
            if (!is_null($this->departmentID)) {
                $fields[] = "department_id = " .
                    $this->departmentID;
            }
            if (!is_null($this->locationID)) {
                $fields[] = "location_id = " .
                    $this->locationID;
            }

            if (count($fields) > 0) {
                $setClause = implode(',', $fields);

                $sql = "UPDATE recurring_items
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

        $sql = "UPDATE recurring_items
                SET deleted = 1
                WHERE id = $id";
        $result = $db->query($sql);

        return $result;
    }

    public static function purge($id) {
        $db = DBFactory::create();

        $sql = "DELETE FROM recurring_items WHERE id = $id";
        $result = $db->query($sql);

        return $result;
    }

    public function findByID($id) {
        $db = DBFactory::create();

        $sql = "SELECT
                    recurring_items.id,
                    recurring_items.display_name,
                    recurring_items.money_value,
                    recurring_items.contract_id,
                    recurring_items.gl_account_id,
                    recurring_items.department_id,
                    recurring_items.location_id,
                    gl_accounts.code As gl_account_code,
                    gl_accounts.name AS gl_account_name,
                    gl_accounts.apply_to_minimum,
                    departments.code AS department_code,
                    departments.name AS department_name,
                    locations.code AS location_code,
                    locations.name AS location_name
                FROM
                    recurring_items                                                        
                INNER JOIN  
                    gl_accounts
                ON
                    gl_accounts.id = recurring_items.gl_account_id
                INNER JOIN
                    departments
                ON
                    departments.id = recurring_items.department_id
                INNER JOIN
                    locations
                ON
                    locations.id = recurring_items.location_id
                WHERE
                    recurring_items.id = $id";

        $result = $db->query($sql);

        if ($db->getNumRows($result) == 1) {
            $row = $db->fetchObject($result);

            $this->setID($row->id);
            $this->setDisplayName($db->unescapeString($row->display_name));
            $this->setMoneyValue($row->money_value);
            $this->setContractID($row->contract_id);

            $this->setGLAccountID($row->gl_account_id);
            $this->setGLAccountCode($db->unescapeString($row->gl_account_code));
            $this->setGLAccountName($db->unescapeString($row->gl_account_name));
            $this->setApplyToMinimum($row->apply_to_minimum);

            $this->setDepartmentID($row->department_id);
            $this->setDepartmentCode($db->unescapeString($row->department_code));
            $this->setDepartmentName($db->unescapeString($row->department_name));

            $this->setLocationID($row->location_id);
            $this->setLocationCode($db->unescapeString($row->location_code));
            $this->setLocationName($db->unescapeString($row->location_name));

            return true;
        } else {
            return false;
        }
    }

    public static function findByClientID($clientID, $date) {
        $db = DBFactory::create();

        $date = $db->prepDate($date);

        $sql = "SELECT
                    recurring_items.id,
                    recurring_items.display_name,
                    recurring_items.money_value,
                    recurring_items.contract_id,
                    recurring_items.gl_account_id,
                    recurring_items.department_id,
                    recurring_items.location_id,
                    gl_accounts.code As gl_account_code,
                    gl_accounts.name AS gl_account_name,
                    gl_accounts.apply_to_minimum,
                    departments.code AS department_code,
                    departments.name AS department_name,
                    locations.code AS location_code,
                    locations.name AS location_name
                FROM
                    recurring_items,                                                        
                    gl_accounts,
                    departments,
                    locations,
                    contracts,
                    clients
                WHERE
                    gl_accounts.id = recurring_items.gl_account_id AND
                    departments.id = recurring_items.department_id AND
                    locations.id = recurring_items.location_id AND
                    recurring_items.contract_id = contracts.id AND
                    ($date BETWEEN contracts.start_date AND contracts.end_date) AND
                    contracts.client_id = clients.id AND
                    clients.id = $clientID";

        $result = $db->query($sql);

        $records = array();

        $numRows = $db->getNumRows($result);

        if ($db->getNumRows($result) > 0) {
            while($row = $db->fetchObject($result)) {
                $record = new RecurringItemRecord();

                $record->setID($row->id);
                $record->setDisplayName($db->unescapeString($row->display_name));
                $record->setMoneyValue($row->money_value);
                $record->setContractID($row->contract_id);

                $record->setGLAccountID($row->gl_account_id);
                $record->setGLAccountCode($db->unescapeString($row->gl_account_code));
                $record->setGLAccountName($db->unescapeString($row->gl_account_name));
                $record->setApplyToMinimum($row->apply_to_minimum);

                $record->setDepartmentID($row->department_id);
                $record->setDepartmentCode($db->unescapeString($row->department_code));
                $record->setDepartmentName($db->unescapeString($row->department_name));

                $record->setLocationID($row->location_id);
                $record->setLocationCode($db->unescapeString($row->location_code));
                $record->setLocationName($db->unescapeString($row->location_name));

                $records[$row->id] = $record;
            }
        }

        return $records;
    }

    public static function findByContractID($contractID) {
        $db = DBFactory::create();

        $sql = "SELECT
                    recurring_items.id,
                    recurring_items.display_name,
                    recurring_items.money_value,
                    recurring_items.contract_id,
                    recurring_items.gl_account_id,
                    recurring_items.department_id,
                    recurring_items.location_id,
                    gl_accounts.code As gl_account_code,
                    gl_accounts.name AS gl_account_name,
                    gl_accounts.apply_to_minimum,
                    departments.code AS department_code,
                    departments.name AS department_name,
                    locations.code AS location_code,
                    locations.name AS location_name
                FROM
                    recurring_items                                                        
                INNER JOIN  
                    gl_accounts
                ON
                    gl_accounts.id = recurring_items.gl_account_id
                INNER JOIN
                    departments
                ON
                    departments.id = recurring_items.department_id
                INNER JOIN
                    locations
                ON
                    locations.id = recurring_items.location_id
                WHERE
                    recurring_items.contract_id = $contractID";

        $result = $db->query($sql);

        $records = array();

        $numRows = $db->getNumRows($result);

        if ($db->getNumRows($result) > 0) {
            while($row = $db->fetchObject($result)) {
                $record = new RecurringItemRecord();

                $record->setID($row->id);
                $record->setDisplayName($db->unescapeString($row->display_name));
                $record->setMoneyValue($row->money_value);
                $record->setContractID($row->contract_id);

                $record->setGLAccountID($row->gl_account_id);
                $record->setGLAccountCode($db->unescapeString($row->gl_account_code));
                $record->setGLAccountName($db->unescapeString($row->gl_account_name));
                $record->setApplyToMinimum($row->apply_to_minimum);

                $record->setDepartmentID($row->department_id);
                $record->setDepartmentCode($db->unescapeString($row->department_code));
                $record->setDepartmentName($db->unescapeString($row->department_name));

                $record->setLocationID($row->location_id);
                $record->setLocationCode($db->unescapeString($row->location_code));
                $record->setLocationName($db->unescapeString($row->location_name));

                $records[$row->id] = $record;
            }
        }

        return $records;
    }

    public static function findAll() {
        $db = DBFactory::create();

        $sql = "SELECT
                    recurring_items.id,
                    recurring_items.display_name,
                    recurring_items.money_value,
                    recurring_items.contract_id,
                    recurring_items.gl_account_id,
                    recurring_items.department_id,
                    recurring_items.location_id,
                    gl_accounts.code As gl_account_code,
                    gl_accounts.name AS gl_account_name,
                    departments.code AS department_code,
                    departments.name AS department_name,
                    locations.code AS location_code,
                    locations.name AS location_name
                FROM
                    recurring_items                                                        
                INNER JOIN  
                    gl_accounts
                ON
                    gl_accounts.id = recurring_items.gl_account_id
                INNER JOIN
                    departments
                ON
                    departments.id = recurring_items.department_id
                INNER JOIN
                    locations
                ON
                    locations.id = recurring_items.location_id
                WHERE
                    1";

        $result = $db->query($sql);

        $records = array();

        $numRows = $db->getNumRows($result);

        if ($db->getNumRows($result) > 0) {
            while($row = $db->fetchObject($result)) {
                $record = new RecurringItemRecord();

                $record->setID($row->id);
                $record->setDisplayName($db->unescapeString($row->display_name));
                $record->setMoneyValue($row->money_value);
                $record->setContractID($row->contract_id);

                $record->setGLAccountID($row->gl_account_id);
                $record->setGLAccountCode($db->unescapeString($row->gl_account_code));
                $record->setGLAccountName($db->unescapeString($row->gl_account_name));

                $record->setDepartmentID($row->department_id);
                $record->setDepartmentCode($db->unescapeString($row->department_code));
                $record->setDepartmentName($db->unescapeString($row->department_name));

                $record->setLocationID($row->location_id);
                $record->setLocationCode($db->unescapeString($row->location_code));
                $record->setLocationName($db->unescapeString($row->location_name));

                $records[$row->id] = $record;
            }
        }

        return $records;
    }
}
?>