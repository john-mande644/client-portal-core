<?php

class order{
	
    private $order_reference;
    private $order_source;
    private $backorder_rule;
	private $message;
	private $hold_for_release;
	private $comments;
	private $warehouse_notes;
	public $billing;
	public $shipping;
	public $line_items;
	
    public function __construct(){
    }
	public function getBackorder_rule() {
		return $this->backorder_rule;
	}
	
	public function getBilling() {
		return $this->billing;
	}
	
	public function getHold_for_release() {
		return $this->hold_for_release;
	}
	
	public function getLine_items() {
		return $this->line_items;
	}
	
	public function getMessage() {
		return $this->message;
	}
	
	public function getOrder_reference() {
		return $this->order_reference;
	}
	
	public function getOrder_source() {
		return $this->order_source;
	}
	
	public function getShipping() {
		return $this->shipping;
	}
	public function getComments() {
		return $this->comments;
	}
	public function getWarehouse_notes() {
		return $this->warehouse_notes;
	}
	
	public function setWarehouse_notes($warehouse_notes) {
		$this->warehouse_notes = $warehouse_notes;
	}

	
	public function setComments($comments) {
		$this->comments = $comments;
	}
	public function setBackorder_rule($backorder_rule) {
		$this->backorder_rule = $backorder_rule;
	}
	
	public function setBilling($billing) {
		$this->billing = $billing;
	}
	
	public function setHold_for_release($hold_for_release) {
		$this->hold_for_release = $hold_for_release;
	}
	
	public function setLine_items($line_items) {
		$this->line_items = $line_items;
	}
	
	public function setMessage($message) {
		$this->message = $message;
	}
	
	public function setOrder_reference($order_reference) {
		$this->order_reference = $order_reference;
	}
	
	public function setOrder_source($order_source) {
		$this->order_source = $order_source;
	}
	
	public function setShipping($shipping) {
		$this->shipping = $shipping;
	}


}
?>