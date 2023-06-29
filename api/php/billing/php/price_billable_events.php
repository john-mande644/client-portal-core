<?php
include_once("includes/pricing_engine.php");

$startTime = microtime(true);

$pricingEngine = new PricingEngine();
$pricingEngine->runBatch();

$endTime = microtime(true);
$timeElapsed = $endTime - $startTime;

echo("The operation took $timeElapsed seconds<br>");
?>