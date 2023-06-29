<?php



class TransFirst_Elink_Block_Form_Cc extends Mage_Payment_Block_Form_Cc

{

	protected function _construct()

    {

        parent::_construct();

        $this->setTemplate('elink/form/cc.phtml');

    }

} 



?>