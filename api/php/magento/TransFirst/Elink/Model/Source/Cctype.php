<?php

class TransFirst_Elink_Model_Source_Cctype extends Mage_Payment_Model_Source_Cctype
{
    public function getAllowedTypes()
    {  
        return array('VI', 'DN', 'AE','MC');
    }
}
?>
