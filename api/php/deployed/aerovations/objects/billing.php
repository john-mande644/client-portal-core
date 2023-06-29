<?php

class billing{
	
	
	private $last_name;
    private $first_name;
    private $company_name;
    private $address_one;
    private $address_two;
    private $city;
    private $state;
    private $zip;
    private $country;
    private $phone;
    private $email;
    private $tax;
    private $paid;
    private $paid_date;
    private $payment_type;

    function __construct(){

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
	
	public function getEmail() {
		return $this->email;
	}
	
	public function getFirst_name() {
		return $this->first_name;
	}
	
	public function getLast_name() {
		return $this->last_name;
	}
	
	public function getPaid() {
		return $this->paid;
	}
	
	public function getPaid_date() {
		return $this->paid_date;
	}
	
	public function getPayment_type() {
		return $this->payment_type;
	}
	
	public function getPhone() {
		return $this->phone;
	}
	
	public function getState() {
		return $this->state;
	}
	
	public function getTax() {
		return $this->tax;
	}
	
	public function getZip() {
		return $this->zip;
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
	
	public function setEmail($email) {
		$this->email = $email;
	}
	
	public function setFirst_name($first_name) {
		$this->first_name = $first_name;
	}
	
	public function setLast_name($last_name) {
		$this->last_name = $last_name;
	}
	
	public function setPaid($paid) {
		$this->paid = $paid;
	}
	
	public function setPaid_date($paid_date) {
		$this->paid_date = $paid_date;
	}
	
	public function setPayment_type($payment_type) {
		$this->payment_type = $payment_type;
	}
	
	public function setPhone($phone) {
		$this->phone = $phone;
	}
	
	public function setState($state) {
		$this->state = $state;
	}
	
	public function setTax($tax) {
		$this->tax_pct = $tax;
	}
	
	public function setZip($zip) {
		$this->zip = $zip;
	}
	
    public function validate(){
    	
    }
}

?>