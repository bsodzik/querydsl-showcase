package com.bsodzik.repository;

import com.bsodzik.model.Customer;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CustomerRepository {

	List<Customer> findAll(int page, int size);

	List<Customer> findByBirthDate(Date birthDateFrom, Date birthDateTo);

	Map<String, Long> countByCustomerType();

	Map<String, Double> calculateCustomersBalance();

	List<Customer> findCustomersHavingMultipleAccounts();
}
