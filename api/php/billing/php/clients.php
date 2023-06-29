<?php
include_once("includes/base.php");
include_once("includes/client_record.php");

$template = new XiTemplate("templates/clients.html");

$clientRecords = ClientRecord::findAll();

$numClients = count($clientRecords);

if ($numClients > 0) {
    $clientRecordsArray = array();
    foreach($clientRecords as $clientRecord) {
        $clientRecordsArray[] = $clientRecord;
    }

    $pivot = ceil($numClients / 3);

    for($i = 0; $i < $pivot; $i++) {
        $leftIndex = $i;
        $leftRecord = $clientRecordsArray[$leftIndex];

        if ($leftRecord->getNumContracts() > 0) {
            $leftName = "<b>" . $leftRecord->getName() . "</b>";
        } else {
            $leftName = $leftRecord->getName();
        }

        $template->assign("LEFT_COLUMN_URL", "client.php?clientID=" . $leftRecord->getID());
        $template->assign("LEFT_COLUMN_NAME", $leftName);

        $middleIndex = $pivot + $i;

        if ($middleIndex <= (2 * $pivot)) {
            $middleRecord = $clientRecordsArray[$middleIndex];

            if ($middleRecord->getNumContracts() > 0) {
                $middleName = "<b>" . $middleRecord->getName() . "</b>";
            } else {
                $middleName = $middleRecord->getName();
            }

            $template->assign("MIDDLE_COLUMN_URL", "client.php?clientID=" . $middleRecord->getID());
            $template->assign("MIDDLE_COLUMN_NAME", $middleName);
        } else {
            $template->assign("MIDDLE_COLUMN_CONTRACTS_URL", "");
            $template->assign("MIDDLE_COLUMN_NAME", "&nbsp;");
        }

        $rightIndex = 2 * $pivot + $i;

        if ($rightIndex < $numClients) {
            $rightRecord = $clientRecordsArray[$rightIndex];

            if ($rightRecord->getNumContracts() > 0) {
                $rightName = "<b>" . $rightRecord->getName() . "</b>";
            } else {
                $rightName = $rightRecord->getName();
            }

            $template->assign("RIGHT_COLUMN_URL", "client.php?clientID=" . $rightRecord->getID());
            $template->assign("RIGHT_COLUMN_NAME", $rightName);
        } else {
            $template->assign("RIGHT_COLUMN_CONTRACTS_URL", "");
            $template->assign("RIGHT_COLUMN_NAME", "&nbsp;");
        }

        $template->parse("main.clients");
    }
} else {
    $template->parse("main.no_clients");
}

$template->parse("main");

write_header();
$template->out("main");
write_footer();
?>

