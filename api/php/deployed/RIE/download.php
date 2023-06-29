<?php
if($_GET["auth"]==='hZksiPG65J1049Jh')
{
require_once('../wp-load.php');

 $start = date('Y-m-d 00:00:00', strtotime($_GET["start"]));
    $end = date('Y-m-d 00:00:00', strtotime($_GET["end"] . ' + 1 day'));

    $orders = Cart66Common::getTableName('orders');
    $items = Cart66Common::getTableName('order_items');
    $products = Cart66Common::getTableName('products');

    $orderHeaders = array(
      'id' => 'Order ID',
      'trans_id' => 'Order Number',
      'ordered_on' => 'Date',
      'bill_first_name' => 'Billing First Name',
      'bill_last_name' => 'Billing Last Name',
      'bill_address' => 'Billing Address',
      'bill_address2' => 'Billing Address 2',
      'bill_city' => 'Billing City',
      'bill_state' => 'Billing State',
      'bill_country' => 'Billing Country',
      'bill_zip' => 'Billing Zip Code',
      'ship_first_name' => 'Shipping First Name',
      'ship_last_name' => 'Shipping Last Name',
      'ship_address' => 'Shipping Address',
      'ship_address2' => 'Shipping Address 2',
      'ship_city' => 'Shipping City',
      'ship_state' => 'Shipping State',
      'ship_country' => 'Shipping Country',
      'ship_zip' => 'Shipping Zip Code',
      'phone' => 'Phone',
      'email' => 'Email',
      'coupon' => 'Coupon',
      'discount_amount' => 'Discount Amount',
      'shipping' => 'Shipping Cost',
      'subtotal' => 'Subtotal',
      'tax' => 'Tax',
      'total' => 'Total',
      'ip' => 'IP Address',
      'shipping_method' => 'Delivery Method'
    );

    $orderColHeaders = implode(',', $orderHeaders);
    $orderColSql = implode(',', array_keys($orderHeaders));
    $out  = $orderColHeaders . ",Item Number,Description,Quantity,Product Price,Shippable\n";

    $sql = "SELECT $orderColSql from $orders where ordered_on >= '$start' AND ordered_on < '$end' order by id";
    $selectedOrders = $wpdb->get_results($sql, ARRAY_A);

    foreach($selectedOrders as $o) {
      $orderId = $o['id'];
      $sql = "SELECT $items.item_number, $items.description, $items.quantity, $items.product_price,$products.shipped FROM $items join $products on $products.id=$items.product_id where order_id = $orderId and $products.shipped=1 order by item_number";
      $selectedItems = $wpdb->get_results($sql, ARRAY_A);
      foreach($selectedItems as $i) {
        $out .= '"' . implode('","', $o) . '"';
        $out .= ',"' . implode('","', $i) . '"';
        $out .= "\n";
      }
    }
   }else
   {
   		$out='Page Not Found';
   }

?><?php echo $out ?>