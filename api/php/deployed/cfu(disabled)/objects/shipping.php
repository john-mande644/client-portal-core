<?php

class shipping {

	private $last_name;
    private $first_name;
    private $company_name;
    private $address_one;
    private $address_two;
    private $city;
    private $state;
    private $zip;
    private $country;
    private $ship_type;
    private $ship_cost;
    private $insure_amount;
    private $declared_value;
    private $terms;
    private $third_party;
    private $best_way;

	function __construct() {
	
	}
	
	public function getAddress_one() {
		return $this->address_one;
	}
	
	public function getAddress_two() {
		return $this->address_two;
	}
	
	public function getCity() {
		return $this->city;
	}
	
	public function getCompany_name() {
		return $this->company_name;
	}
	
	public function getCountry() {
		return $this->country;
	}
	
	public function getDeclared_value() {
		return $this->declared_value;
	}
	
	public function getFirst_name() {
		return $this->first_name;
	}
	
	public function getInsure_amount() {
		return $this->insure_amount;
	}
	
	public function getLast_name() {
		return $this->last_name;
	}
	
	public function getShip_cost() {
		return $this->ship_cost;
	}
	
	public function getShip_type() {
		return $this->ship_type;
	}
	
	public function getState() {
		return $this->state;
	}
	
	public function getTerms() {
		return $this->terms;
	}
	
	public function getZip() {
		return $this->zip;
	}
	public function getBest_way() {
		return $this->best_way;
	}
	
	public function getThird_party() {
		return $this->third_party;
	}

	
	public function setAddress_one($address_one) {
		$this->address_one = $address_one;
	}
	
	public function setAddress_two($address_two) {
		$this->address_two = $address_two;
	}
	
	public function setCity($city) {
		$this->city = $city;
	}
	
	public function setCompany_name($company_name) {
		$this->company_name = $company_name;
	}
	
	public function setCountry($country) {
		$this->country = $country;
	}
	
	public function setDeclared_value($declared_value) {
		$this->declared_value = $declared_value;
	}
	
	public function setFirst_name($first_name) {
		$this->first_name = $first_name;
	}
	
	public function setInsure_amount($insure_amount) {
		$this->insure_amount = $insure_amount;
	}
	
	public function setLast_name($last_name) {
		$this->last_name = $last_name;
	}
	
	public function setShip_cost($ship_cost) {
		$this->ship_cost = $ship_cost;
	}
	
	public function setShip_type($ship_type) {
		$this->ship_type = $ship_type;
	}
	
	public function setState($state) {
		$this->state = $state;
	}
	
	public function setTerms($terms) {
		$this->terms = $terms;
	}
	
	public function setZip($zip) {
		$this->zip = $zip;
	}

	public function setBest_way($best_way) {
		$this->best_way = $best_way;
	}
	
	public function setThird_party($third_party) {
		$this->third_party = $third_party;
	}


}

?>
