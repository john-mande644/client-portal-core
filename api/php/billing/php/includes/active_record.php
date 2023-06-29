<?php
include_once("database.php");

abstract class FieldType {
    public const Number = 0;
    public const String = 1;
    public const Boolean = 2;
    public const Date = 3;
    public const Timestamp = 4;
}

abstract class Field {
    protected $name;
    protected $type;
    protected $external; // If true, then field is joined from another table
    protected $value;
}

class NumberField : Field {
    protected $autoIncrement;
}

class StringField : Field {
    public function setValue($value, $dbFormat = false) {
        if ($dbFormat) {
            $this->value = stripslashes($value);
        } else {
            $this->value = $value;
        }
    }

    public function getValue($dbFormat = false) {
        if ($this->value == null) {
            return = "''";
        } else {
            return = "'" . mysql_real_escape_string($this->value) . "'";
        }
    }
}

class TimestampField {
    protected $auto;
}

class DateField {
    protected $auto;
}

class ActiveRecord {
    private $tableName;
    private $id;
    private $fields;

    public function __construct($tableName, $fields) {
        $this->tableName = $tableName;

        foreach ($fields as $key => $value) {
            $this->fields[$key] = $value;
        }
    }

    public function __call($method, $args) {
        if (strncasecmp($method, 'set', 3) == 0) {
            $fieldName = self::toCamelCase(substr($method, 3));

            if (array_key_exists($fieldName, $this->fields)) {
                $this->fields[$fieldName] = $args[0];
                return true;
            }
        } else if (strncasecmp($method, 'get', 3) == 0) {
            $fieldName = self::toCamelCase(substr($method, 3));

            if (array_key_exists($fieldName, $this->fields)) {
                return $this->fields[$fieldName];
            }
        }

        return false;
    }

    private static function toCamelCase($str) {
        return strtolower(substr($str, 0, 1)) . substr($str, 1);
    }
}

$fields = array();
$fields["name"] = null;
$fields["age"] = null;
$fields["phone"] = null;
$fields["email"] = null;

$r = new ActiveRecord("Person", $fields);

$r->setName("Jason");
$r->setAge(38);
$r->setPhone("(626) 676-4594");
$r->setEmail("j.roberts@yahoo.com");

$name = $r->getName();
$age = $r->getAge();

echo("Name: " . $r->getName() . "<br>");
echo("Age: " . $r->getAge() . "<br>");
?>