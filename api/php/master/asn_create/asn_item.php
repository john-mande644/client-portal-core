<?php

class asn_item{

    public $sku='';
    public $quantity='';
    public $description='';

    public function __construct($sku,$quantity,$description){
        $this->sku=$sku;
        $this->quantity=$quantity;
        $this->description=$description;
    }

}
?>