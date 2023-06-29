<?php

class sms{
    public function __construct($address){
        $this->address = $address;
        $this->action = $_POST['action'];
        $this->message = $_POST['message'];
    }

    public function send(){
        $message = $this->action . ":  \n" . $this->message;
        return mail($this->address,$this->action,$message);
    }

}
?>