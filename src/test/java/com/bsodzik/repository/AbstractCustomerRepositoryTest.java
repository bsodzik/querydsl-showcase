package com.bsodzik.repository;

import com.bsodzik.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@Slf4j
public abstract class AbstractCustomerRepositoryTest {

	protected CustomerRepository repository;

	@Test
	public void findAllCustomers() {
		List<Customer> customers = repository.findAll(1, 10);
		for (Customer customer : customers) {
			log.info(customer.toString());
		}
		assertThat(customers, hasSize(10));
		assertThat(customers.get(0).getId(), equalTo(11L));
		assertThat(customers.get(9).getId(), equalTo(20L));
	}

	@Test
	public void findBetween25and35() {
		List<Customer> customers = repository.findByBirthDate(yearsFromNow(35), yearsFromNow(25));
		for (Customer customer : customers) {
			log.info("Customer: {}, birth date: {}", customer.getName(), customer.getBirthDate());
		}
		assertThat(customers, hasSize(8));
	}

	@Test
	public void countByCustomerType() {
		Map<String, Long> map = repository.countByCustomerType();
		for (String type : map.keySet()) {
			log.info("Type: {}, count: {}", type, map.get(type));
		}
	}

	@Test
	public void calculateCustomersBalance() {
		Map<String, Double> map = repository.calculateCustomersBalance();
		for (String customerName : map.keySet()) {
			log.info("Customer: {}, balance: {}", customerName, map.get(customerName));
		}
	}

	@Test
	public void findCustomersHavingMultipleAccounts() {
		List<Customer> customers = repository.findCustomersHavingMultipleAccounts();
		for (Customer customer : customers) {
			log.info("Customer: {}, accounts: {}", customer.getName(), customer.getAccounts().size());
		}
	}

	protected Date yearsFromNow(int years) {
		return Date.from(LocalDate.now().minusYears(years).atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
}
