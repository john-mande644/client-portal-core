<?php

class lineItem{

    private $part_reference;
    private $description;
    private $requested;
    private $cost;
    private $declared_value;
    private $customs_desc;
    private $backordered;
    private $line_number;

    public function __construct($part_reference,$description,$requested,$cost,$declared_value,$customs_desc,$backordered,$line_number){
		$this->part_reference = $part_reference;
		$this->description = $description;
		$this->requested = $requested;
		$this->cost = $cost;
		$this->declared_value = $declared_value;
		$this->customs_desc = $customs_desc;
		$this->backordered = $backordered;
		$this->line_number = $line_number;
    }
	
	public function getBackordered() {
		return $this->backordered;
	}
	
	public function getCost() {
		return $this->cost;
	}
	
	public function getCustoms_desc() {
		return $this->customs_desc;
	}
	
	public function getDeclared_value() {
		return $this->declared_value;
	}
	
	public function getDescription() {
		return $this->description;
	}
	
	public function getLine_number() {
		return $this->line_number;
	}
	
	public function getPart_reference() {
		return $this->part_reference;
	}
	
	public function getRequested() {
		return $this->requested;
	}
	
	public function setBackordered($backordered) {
		$this->backordered = $backordered;
	}
	
	public function setCost($cost) {
		$this->cost = $cost;
	}
	
	public function setCustoms_desc($customs_desc) {
		$this->customs_desc = $customs_desc;
	}
	
	public function setDeclared_value($declared_value) {
		$this->declared_value = $declared_value;
	}
	
	public function setDescription($description) {
		$this->description = $description;
	}
	
	public function setLine_number($line_number) {
		$this->line_number = $line_number;
	}
	
	public function setPart_reference($part_reference) {
		$this->part_reference = $part_reference;
	}
	
	public function setRequested($requested) {
		$this->requested = $requested;
	}

}
?>