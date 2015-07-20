package com.bsodzik.repository;

import com.bsodzik.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@Slf4j
public abstract class AbstractCustomerRepositoryTest {

	protected CustomerRepository repository;

	@Test
	public void findSecondPageOfCustomers() {
		List<Customer> customers = repository.findAll(1, 10);
		for (Customer customer : customers) {
			log.info("{}", customer);
		}
		assertThat(customers, hasSize(10));
		assertThat(customers.get(0).getId(), equalTo(11L));
		assertThat(customers.get(9).getId(), equalTo(20L));
	}

	@Test
	public void findBornIn80ties() {
		List<Customer> customers = repository.findByBirthDate(toDate("01-01-1980"), toDate("31-12-1989"));
		for (Customer customer : customers) {
			log.info("Customer: {}, birth date: {}", customer.getName(), customer.getBirthDate());
		}
		assertThat(customers, hasSize(9));
	}

	@Test
	public void countByCustomerType() {
		Map<String, Long> map = repository.countByCustomerType();
		for (String type : map.keySet()) {
			log.info("Type: {}, count: {}", type, map.get(type));
		}
		assertThat(map.size(), equalTo(2));
		assertThat(map, hasEntry("private", 25L));
		assertThat(map, hasEntry("corporate", 5L));
	}

	@Test
	public void calculateCustomersBalance() {
		Map<String, Double> map = repository.calculateCustomersBalance();
		for (String customerName : map.keySet()) {
			log.info("Customer: {}, balance: {}", customerName, map.get(customerName));
		}
		assertThat(map, hasEntry("Buzzster", 12560.05));
		assertThat(map, hasEntry("Roy Romero", 23892.29));
		assertThat(map, hasEntry("Joe Johnson", 14457.02));
		assertThat(map, hasEntry("Craig Walker", 10357.26));
	}

	@Test
	public void findCustomersHavingMultipleAccounts() {
		List<Customer> customers = repository.findCustomersHavingMultipleAccounts();
		for (Customer customer : customers) {
			log.info("Customer: {}, accounts: {}", customer.getName(), customer.getAccounts().size());
		}
		assertThat(customers, hasSize(7));
	}

	protected Date toDate(String str) {
		try {
			return new SimpleDateFormat("dd-MM-yyyy").parse(str);
		} catch (ParseException ex) {
			throw new RuntimeException(ex);
		}
	}
}
