<?php
include_once("config.php");
include_once("xi_template.php");

ini_set('magic_quotes_gpc', 0);
ini_set('magic_quotes_runtime', 0);

if (get_magic_quotes_gpc()) {
    $_POST = array_map('stripslashes_deep', $_POST);
    $_GET = array_map('stripslashes_deep', $_GET);
    $_COOKIE = array_map('stripslashes_deep', $_COOKIE);
}


function post_data($url, $postData) {
    $ch = curl_init();

    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_VERBOSE, 1);
    curl_setopt($ch, CURLOPT_HEADER, 1);
    curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
    curl_setopt($ch, CURLOPT_POSTFIELDS, $postData);

    $result = curl_exec($ch);

    curl_close($ch);

    return $result;
}

function destroy_session() {
    unset($_SESSION['user_id']);

    session_destroy();
}

function stripslashes_deep($value) {
    $value = is_array($value) ? array_map('stripslashes_deep', $value) : stripslashes($value);

    return $value;
}

function log_error($module, $output) {
    $localtime = localtime(time(), true);

    $day = $localtime['tm_mday'];
    $month = $localtime['tm_mon'];
    $year = $localtime['tm_year'];

    $pathname = ERROR_DIR . "/$month-$day-$year.txt";

    if ($clean) {
        $fp = fopen($pathname, 'w');
    } else {
        $fp = fopen($pathname, 'a');
    }

    fwrite($fp, $module . ": " . $output . "\n");
    fclose($fp);
}

function echof($output, $filename = "debug.txt", $clean = false) {
    $pathname = LOGS_DIR . "/$filename";

    if ($clean) {
        $fp = fopen($pathname, 'w');
    } else {
        $fp = fopen($pathname, 'a');
    }

    fwrite($fp, $output);
    fclose($fp);
}

function is_valid_date($date) {
    if (is_null($date) || $date == "") { return false; }

    $parts = split('/', $date);

    $month = intval($parts[0]);
    $day = intval($parts[1]);
    $year = intval($parts[2]);

    $result = checkdate($month, $day, $year);

    return $result;
}

function format_mssql_as_display_date($input) {
    // Convert MSSQL format 'Jan 1 2008 12:00:00:000AM'

    if ($input && strlen($input) >= 0) {
        $parts = preg_split('/\s+/', $input);

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

function format_as_display_date($input) {
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

function format_as_mysql_date($input) {
    // Convert display format 'M/D/YYYY' to mySQl format 'YYYY-MM-DD'

    if ($input == "") { return ""; }

    $parts = split('/', $input);

    $month = $parts[0];
    $day = $parts[1];
    $year = $parts[2];

    if (strlen($month) == 1) { $month = "0$month"; }
    if (strlen($day) == 1) { $day = "0$day"; }

    $output = "$year-$month-$day";

    return $output;
}

function date_to_int($date) {
    $dateArr = split('/', $date);
    return mktime(0, 0, 0, $dateArr[0], $dateArr[1], $dateArr[2]);
}

// Returns an array of numeric values representing days, hours, minutes & seconds respectively
function date_diff($earlierTimestamp, $laterTimestamp) {
    $diffArray = array('days'=>0, 'hours'=>0, 'minutes'=>0, 'seconds'=>0);

    $totalSeconds = $laterTimestamp - $earlierTimestamp;

    if ($totalSeconds >= 86400) {
        $diffArray['days'] = floor($totalSeconds / 86400);
        $totalsec = $totalSeconds % 86400;
    }

    if ($totalSeconds >= 3600) {
        $diffArray['hours'] = floor($totalSeconds / 3600);
        $totalsec = $totalSeconds % 3600;
    }

    if ($totalSeconds >= 60) {
        $diffArray['minutes'] = floor($totalSeconds / 60);
    }

    $diffArray['seconds'] = $totalSeconds % 60;

    return $diffArray;
}

function write_header() {
    $template = new XiTemplate("templates/header.html");

    $template->parse("main");
    $template->out("main");
}

function write_footer() {
    $template = new XiTemplate("templates/footer.html");

    $template->parse("main");
    $template->out("main");
}
?>