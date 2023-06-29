<?php
include_once("database.php");

class BillableTypeRecord {
    private static $tableName = "billable_types";
    private $id;
    private $name;                                
    private $description;
    private $billableUnitID;
    private $billableUnitName;
    private $billableUnitValueType;
    private $glAccountID;
    private $glAccountCode;
    private $glAccountName;
    private $departmentID;
    private $departmentCode;
    private $departmentName;
    private $locationID;
    private $locationCode;
    private $locationName;
    private $exceptionField1Name;
    private $exceptionField1Type;
    private $exceptionField2Name;
    private $exceptionField2Type;

    public function getID() { return $this->id; }
    public function setID($value) { $this->id = $value; }

    public function getName() { return $this->name; }
    public function setName($value) { $this->name = $value; }

    public function getDescription() { return $this->description; }
    public function setDescription($value) { $this->description = $value; }

    // Billable Unit
    public function getBillableUnitID() { return $this->billableUnitID; }
    public function setBillableUnitID($value) { $this->billableUnitID = $value; }

    public function getBillableUnitName() { return $this->billableUnitName; }
    public function setBillableUnitName($value) { $this->billableUnitName = $value; }

    public function getBillableUnitValueType() { return $this->billableUnitValueType; }
    public function setBillableUnitValueType($value) { $this->billableUnitValueType = $value; }

    // GL Account
    public function getGLAccountID() { return $this->glAccountID; }
    public function setGLAccountID($value) { $this->glAccountID = $value; }

    public function getGLAccountCode() { return $this->glAccountCode; }
    public function setGLAccountCode($value) { $this->glAccountCode = $value; }

    public function getGLAccountName() { return $this->glAccountName; }
    public function setGLAccountName($value) { $this->glAccountName = $value; }

    // Department
    public function getDepartmentID() { return $this->departmentID; }
    public function setDepartmentID($value) { $this->departmentID = $value; }

    public function getDepartmentCode() { return $this->departmentCode; }
    public function setDepartmentCode($value) { $this->departmentCode = $value; }

    public function getDepartmentName() { return $this->departmentName; }
    public function setDepartmentName($value) { $this->departmentName = $value; }

    // Location
    public function getLocationID() { return $this->locationID; }
    public function setLocationID($value) { $this->locationID = $value; }

    public function getLocationCode() { return $this->locationCode; }
    public function setLocationCode($value) { $this->locationCode = $value; }

    public function getLocationName() { return $this->locationName; }
    public function setLocationName($value) { $this->locationName = $value; }

    // Exception
    public function getExceptionField1Name() { return $this->exceptionField1Name; }
    public function setExceptionField1Name($value) { $this->exceptionField1Name = $value; }

    public function getExceptionField1Type() { return $this->exceptionField1Type; }
    public function setExceptionField1Type($value) { $this->exceptionField1Type = $value; }

    public function getExceptionField2Name() { return $this->exceptionField2Name; }
    public function setExceptionField2Name($value) { $this->exceptionField2Name = $value; }

    public function getExceptionField2Type() { return $this->exceptionField2Type; }
    public function setExceptionField2Type($value) { $this->exceptionField2Type = $value; }


    public function insert(&$sql) {
        $db = DBFactory::create();

        $name = $db->prepStrVal($this->name);
        $description = $db->prepStrVal($this->description);
        $exceptionField1Name = $db->prepStrVal($this->exceptionField1Name);
        $exceptionField1Type = $db->prepStrVal($this->exceptionField1Type);
        $exceptionField2Name = $db->prepStrVal($this->exceptionField2Name);
        $exceptionField2Type = $db->prepStrVal($this->exceptionField2Type);

        $tableName = self::$tableName;

        $sql = "INSERT INTO $tableName
                    (name,
                     description,
                     billable_unit_id,
                     gl_account_id,
                     department_id,
                     location_id,
                     exception_field1_name,
                     exception_field1_type,
                     exception_field2_name,
                     exception_field2_type)
                VALUES
                    ($name,
                     $description,
                     $this->billableUnitID,
                     $this->glAccountID,
                     $this->departmentID,
                     $this->locationID,
                     $exceptionField1Name,
                     $exceptionField1Type,
                     $exceptionField2Name,
                     $exceptionField2Type)";

        $result = $db->query($sql);

        $this->id = $db->getInsertID();

        return $result;
    }

    public function update(&$sql) {
        $db = DBFactory::create();

        if ($this->id) {

            $fields = array();

            if (!is_null($this->name)) {
                $fields[] = "name = " .
                    $db->prepStrVal($this->name);
            }
            if (!is_null($this->description)) {
                $fields[] = "description = " .
                    $db->prepStrVal($this->description);
            }
            if (!is_null($this->billableUnitID)) {
                $fields[] = "billable_unit_id = " . $this->billableUnitID;
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
            if (!is_null($this->exceptionField1Name)) {
                $fields[] = "exception_field1_name = " .
                    $db->prepStrVal($this->exceptionField1Name);
            }
            if (!is_null($this->exceptionField1Type)) {
                $fields[] = "exception_field1_type = " .
                    $db->prepStrVal($this->exceptionField1Type);
            }
            if (!is_null($this->exceptionField2Name)) {
                $fields[] = "exception_field2_name = " .
                    $db->prepStrVal($this->exceptionField2Name);
            }
            if (!is_null($this->exceptionField2Type)) {
                $fields[] = "exception_field2_type = " .
                    $db->prepStrVal($this->exceptionField2Type);
            }

            if (count($fields) > 0) {
                $setClause = implode(',', $fields);

                $tableName = self::$tableName;

                $sql = "UPDATE $tableName
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

        $tableName = self::$tableName;

        $sql = "DELETE FROM $tableName WHERE id = $id";

        $result = $db->query($sql);

        return $result;
    }

    public function findByID($id) {
        $db = DBFactory::create();

        $tableName = self::$tableName;

        $sql = "SELECT
                    $tableName.id,
                    $tableName.name,
                    $tableName.description,
                    $tableName.billable_unit_id,
                    $tableName.gl_account_id,
                    $tableName.department_id,
                    $tableName.location_id,
                    $tableName.exception_field1_name,
                    $tableName.exception_field1_type,
                    $tableName.exception_field2_name,
                    $tableName.exception_field2_type,
                    billable_units.name AS billable_unit_name,
                    billable_units.value_type AS billable_unit_value_type,
                    gl_accounts.code As gl_account_code,
                    gl_accounts.name AS gl_account_name,
                    departments.code AS department_code,
                    departments.name AS department_name,
                    locations.code AS location_code,
                    locations.name AS location_name
                FROM
                    $tableName
                INNER JOIN
                    billable_units
                ON
                    billable_units.id = $tableName.billable_unit_id
                INNER JOIN
                    gl_accounts
                ON
                    gl_accounts.id = $tableName.gl_account_id
                INNER JOIN
                    departments
                ON
                    departments.id = $tableName.department_id
                INNER JOIN
                    locations
                ON
                    locations.id = $tableName.location_id
                WHERE
                    $tableName.id = $id
                ORDER BY
                    $tableName.name";

        $result = $db->query($sql);

        if ($db->getNumRows($result) == 1) {
            $row = $db->fetchObject($result);

            $this->setID($row->id);
            $this->setName($db->unescapeString($row->name));
            $this->setDescription($db->unescapeString($row->description));

            $this->setBillableUnitID($row->billable_unit_id);
            $this->setBillableUnitName($db->unescapeString($row->billable_unit_name));
            $this->setBillableUnitValueType($row->billable_unit_value_type);

            $this->setGLAccountID($row->gl_account_id);
            $this->setGLAccountCode($db->unescapeString($row->gl_account_code));
            $this->setGLAccountName($db->unescapeString($row->gl_account_name));

            $this->setDepartmentID($row->department_id);
            $this->setDepartmentCode($db->unescapeString($row->department_code));
            $this->setDepartmentName($db->unescapeString($row->department_name));

            $this->setLocationID($row->location_id);
            $this->setLocationCode($db->unescapeString($row->location_code));
            $this->setLocationName($db->unescapeString($row->location_name));

            $this->setExceptionField1Name($db->unescapeString($row->exception_field1_name));
            $this->setExceptionField1Type($row->exception_field1_type);
            $this->setExceptionField2Name($db->unescapeString($row->exception_field2_name));
            $this->setExceptionField2Type($row->exception_field2_type);

            return true;
        } else {
            return false;
        }
    }

    public function findAll() {
        $db = DBFactory::create();

        $tableName = self::$tableName;  

        $sql = "SELECT
                    $tableName.id,
                    $tableName.name,
                    $tableName.description,
                    $tableName.billable_unit_id,
                    $tableName.gl_account_id,
                    $tableName.department_id,
                    $tableName.location_id,
                    $tableName.exception_field1_name,
                    $tableName.exception_field1_type,
                    $tableName.exception_field2_name,
                    $tableName.exception_field2_type,
                    billable_units.name AS billable_unit_name,
                    billable_units.value_type AS billable_unit_value_type,
                    gl_accounts.code As gl_account_code,
                    gl_accounts.name AS gl_account_name,
                    departments.code AS department_code,
                    departments.name AS department_name,
                    locations.code AS location_code,
                    locations.name AS location_name
                FROM
                    $tableName
                INNER JOIN
                    billable_units
                ON
                    billable_units.id = $tableName.billable_unit_id
                INNER JOIN
                    gl_accounts
                ON
                    gl_accounts.id = $tableName.gl_account_id
                INNER JOIN
                    departments
                ON
                    departments.id = $tableName.department_id
                INNER JOIN
                    locations
                ON
                    locations.id = $tableName.location_id
                ORDER BY
                    $tableName.name";

        $result = $db->query($sql);

        $records = array();

        $numRows = $db->getNumRows($result);

        if ($db->getNumRows($result) > 0) {
            while($row = $db->fetchObject($result)) {
                $record = new BillableTypeRecord();

                $record->setID($row->id);
                $record->setName($db->unescapeString($row->name));
                $record->setDescription($db->unescapeString($row->description));

                $record->setBillableUnitID($row->billable_unit_id);
                $record->setBillableUnitName($db->unescapeString($row->billable_unit_name));
                $record->setBillableUnitValueType($row->billable_unit_value_type);

                $record->setGLAccountID($row->gl_account_id);
                $record->setGLAccountCode($db->unescapeString($row->gl_account_code));
                $record->setGLAccountName($db->unescapeString($row->gl_account_name));

                $record->setDepartmentID($row->department_id);
                $record->setDepartmentCode($db->unescapeString($row->department_code));
                $record->setDepartmentName($db->unescapeString($row->department_name));

                $record->setLocationID($row->location_id);
                $record->setLocationCode($db->unescapeString($row->location_code));
                $record->setLocationName($db->unescapeString($row->location_name));

                $record->setExceptionField1Name($db->unescapeString($row->exception_field1_name));
                $record->setExceptionField1Type($row->exception_field1_type);
                $record->setExceptionField2Name($db->unescapeString($row->exception_field2_name));
                $record->setExceptionField2Type($row->exception_field2_type);

                $records[$row->id] = $record;
            }
        }

        return $records;
    }
}
?>