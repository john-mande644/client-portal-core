<?php
include_once("config.php");
include_once("base.php");

interface IDatabase {
    public function query($sql);
    public function getInsertID();
    public function getNumRows($result);
    public function fetchObject($result);

    public function prepNumVal($val);
    public function prepBoolVal($val);

    public function prepStrVal($str);
    public function unescapeString($str);

    public function prepDate($val);
    public function formatDate($val);
}

class DBFactory {
    public function create() {
        $dbType = SELECTED_DATABASE_TYPE;

        switch ($dbType) {
            case "MSSQL":
                return new MSSQLDatabase();
            case "MySQL":
                return new MySQLDatabase();
            default:
                return false;
        }
    }
}

class MSSQLDatabase implements IDatabase {
    private $link;

    public function __construct() {
        $this->link = mssql_connect(DATABASE_HOST_SERVER, DATABASE_USERNAME, DATABASE_PASSWORD);

        if ($this->link === false) {
            throw new DatabaseException("MSSQL connect failed.");
        }

        mssql_select_db(DATABASE_NAME, $this->link);
        mssql_select_db(DATABASE_NAME, $this->link);
    }

    public function query($sql) {
        if (LOG_QUERIES) { echof("$sql\n"); }

        //echo("<br>$sql<br>");

        try {
            $result = mssql_query($sql);
        } catch(Exception $e) {
            echo("FAILED SQL:<br>$sql<br>");
        }

        return $result;
    }

    public function getInsertID() {
        return mssql_insert_id($this->link);
    }

    public function getNumRows($result) {
        return ($result ? mssql_num_rows($result) : 0);
    }

    public function fetchObject($result) {
        return mssql_fetch_object($result);
    }

    public function prepStrVal($str) {
        if ($str == NULL) {
            return "''";
        } else {
            $result = "'" . str_replace("'", "''", $str) . "'";
        }

        return $result;
    }

    public function unescapeString($str) {
        return str_replace("''", "'", $str);
    }

    public function prepNumVal($val) {
        return ($val != NULL ? $val : 0);
    }

    public function prepBoolVal($val) {
        if (strtolower($val) == "true") return 1;
        if (strtolower($val) == "false") return 0;

        return ($val ? 1 : 0);
    }

    public function prepDate($val) {
        // Convert display format 'M/D/YYYY' to SQL Server format 'YYYYMMDD 00:00:00'

        if ($val) {
            $parts = split('/', $val);

            $month = $parts[0];
            $day = $parts[1];
            $year = $parts[2];

            if (strlen($month) == 1) { $month = "0$month"; }
            if (strlen($day) == 1) { $day = "0$day"; }

            $output = "'$year$month$day 00:00:00'";

        } else {
            $output = "NULL";
        }

        return $output;
    }

    public function formatDate($val) {
        // Convert MSSQL format 'Jan 1 2008 12:00:00:000AM'

        if ($val && strlen($val) >= 0) {
            $parts = preg_split('/\s+/', $val);

            $shortMonthName = trim($parts[0]);
            $day = trim($parts[1]);
            $year = trim($parts[2]);

            switch ($shortMonthName) {
                case 'Jan': $month = 1; break;
                case 'Feb': $month = 2; break;
                case 'Mar': $month = 3; break;
                case 'Apr': $month = 4; break;
                case 'May': $month = 5; break;
                case 'Jun': $month = 6; break;
                case 'Jul': $month = 7; break;
                case 'Aug': $month = 8; break;
                case 'Sep': $month = 9; break;
                case 'Oct': $month = 10; break;
                case 'Nov': $month = 11; break;
                case 'Dec': $month = 12; break;
                default: $month = 0; break;
            }

            $output = "$month/$day/$year";
        } else {
            return "";
        }

        return $output;  
    }
}

function mssql_insert_id($link) {
    //$query = "SET NOCOUNT ON " . $query . " SELECT @@IDENTITY AS NewID SET NOCOUNT OFF";

    $result = @mssql_query("SELECT @@identity", $link);

    if (!$result) {
        return -1;
    }

    return mssql_result($result, 0, 0);
}

////////////////////////////////////////////////////////////////////////////////////////////////

class MySQLDatabase implements IDatabase {
    private $link;

    public function __construct() {
        $this->link = mysql_connect(DATABASE_HOST_SERVER, DATABASE_USERNAME, DATABASE_PASSWORD);

        if ($this->link === false) {
            throw new DatabaseException("MySQL pconnect failed.");
        }

        mysql_select_db(DATABASE_NAME, $this->link);
    }

    public function query($sql) {
        if (LOG_QUERIES) { echof("$sql\n"); }

        try {
            $result = mysql_query($sql);
        } catch(Exception $e) {
            echo("FAILED SQL:<br>$sql<br>");
        }

        return $result;
    }

    public function getInsertID() {
        return mysql_insert_id();
    }

    public function getNumRows($result) {
        return ($result ? mysql_num_rows($result) : 0);
    }

    public function fetchObject($result) {
        return mysql_fetch_object($result);
    }

    public function prepStrVal($str) {
        if ($str == NULL) {
            return "''";
        } else {
            $result = "'" . mysql_real_escape_string($str, $this->link) . "'";
        }

        return $result;
    }

    public function unescapeString($str) {
        return stripslashes($str);
    }

    public function prepNumVal($val) {
        return ($val != NULL ? $val : 0);
    }

    public function prepBoolVal($val) {
        if (strtolower($val) == "true") return 1;
        if (strtolower($val) == "false") return 0;

        return ($val ? 1 : 0);
    }

    public function prepDate($val) {
        // Convert display format 'M/D/YYYY' to mySQl format 'YYYY-MM-DD'

        if ($val) {
            $parts = split('/', $val);

            $month = $parts[0];
            $day = $parts[1];
            $year = $parts[2];

            if (strlen($month) == 1) { $month = "0$month"; }
            if (strlen($day) == 1) { $day = "0$day"; }

            $output = "'$year-$month-$day'";
        } else {
            $output = "NULL";
        }

        return $output;
    }

    public function formatDate($val) {
        // Convert MySQL format 'YYYY-MM-DD to 'M/D/YYYY'

        if ($input == "0000-00-00") { return ""; }

        if ($input && strlen($input) >= 8) {
            $parts = split('-', $input);

            $year = $parts[0];
            $month = $parts[1];
            $day = $parts[2];

            if (substr($month, 0, 1) == "0") {
                $month = substr($month, 1, 1);
            }
            if (substr($day, 0, 1) == "0") {
                $day = substr($day, 1, 1);
            }

            $output = "$month/$day/$year";
        } else {
            return "";
        }

        return $output;
    }
}

class DatabaseException extends Exception {
    private $description;

    public function __construct($description) {
        $this->description = $description;
    }

    public function getDescription() { return $this->description; }
    public function setDescription($value) { $this->description = $value; }
}
?>