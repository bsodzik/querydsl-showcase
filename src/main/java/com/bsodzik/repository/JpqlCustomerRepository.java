package com.bsodzik.repository;

import org.springframework.stereotype.Repository;
import com.bsodzik.model.Customer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class JpqlCustomerRepository implements CustomerRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Customer> findAll(int page, int size) {
		return entityManager.createQuery("select c from Customer c", Customer.class)
				.setFirstResult(page * size)
				.setMaxResults(size)
				.getResultList();
	}

	@Override
	public List<Customer> findByBirthDate(Date birthDateFrom, Date birthDateTo) {
		return entityManager.createQuery("select c from Customer c where c.birthDate between :from and :to", Customer.class)
				.setParameter("from", birthDateFrom, TemporalType.DATE)
				.setParameter("to", birthDateTo, TemporalType.DATE)
				.getResultList();
	}

	@Override
	public Map<String, Long> countByCustomerType() {
		List<Object[]> result = entityManager.createQuery("select c.type, count(c.id) from Customer c group by c.type", Object[].class)
				.getResultList();

		return result.stream().collect(Collectors.toMap(arr -> (String) arr[0], arr -> (Long) arr[1]));
	}

	@Override
	public Map<String, Double> calculateCustomersBalance() {
		List<Object[]> result = entityManager.createQuery("select c.name, sum(a.balance) from Customer c join c.accounts a group by c.id",
				Object[].class).getResultList();

		return result.stream().collect(Collectors.toMap(arr -> (String) arr[0], arr -> (Double) arr[1]));
	}

	@Override
	public List<Customer> findCustomersHavingMultipleAccounts() {
		return entityManager.createQuery("select c from Customer c join c.accounts a group by c.id having count(a.iban) > 1",
				Customer.class).getResultList();
	}
}
