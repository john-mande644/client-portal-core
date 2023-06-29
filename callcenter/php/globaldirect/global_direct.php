<?php
    require_once('../includes/sms.php');
    if($_POST['sent']){
      $sms = new sms('gdmmtl@gmail.com');
      $result = $sms->send();
    }
?>

<!DOCTYPE html
     PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
  <head>
    <title>Global Direct Form Entry Page</title>
  </head>
  <body>
  <div id="announcement">
    <?php
      if($_POST['sent']){
        if($result){
          echo("Message was sent");
        }else{
          echo("Message was not sent properly, do not resend the message write down the details and contact IT after your call");
        }
      }
    ?>
  </div>
  <div id="form_area">
    <form action="global_direct.php/global_direct.php" method="post">
      <div id="form_select">
        Action: &nbsp;
        <select name="action">
          <option value="address change">Address Change Request</option>
          <option value="subscription change">Subscription Change Request</option>
          <option value="cancel">Cancellation Request</option>
        </select>
      </div>
      <div id="label_textarea">Details</div>
      <div id="form_textarea">
        <textarea rows="10" cols="25" name="message"/>
      </div>
      <div id="form_submit">
        <input type="hidden" name="sent" value = "1"/>
        <input type="submit" value="Send Message">
      </div>
    </form>
  </div>
  </body>
</html>