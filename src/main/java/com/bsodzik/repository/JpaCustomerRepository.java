package com.bsodzik.repository;

import com.bsodzik.model.Customer;
import com.bsodzik.model.Customer_;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class JpaCustomerRepository implements CustomerRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Customer> findAll(int page, int size) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
		query.from(Customer.class);

		return entityManager.createQuery(query)
				.setFirstResult(page * size)
				.setMaxResults(size)
				.getResultList();
	}

	@Override
	public List<Customer> findByBirthDate(Date birthDateFrom, Date birthDateTo) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
		Root<Customer> customer = query.from(Customer.class);
		query.where(builder.between(customer.get(Customer_.birthDate), birthDateFrom, birthDateTo));

		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public Map<String, Long> countByCustomerType() {
		return Collections.emptyMap();
	}

	@Override
	public Map<String, Double> calculateCustomersBalance() {
		return Collections.emptyMap();
	}

	@Override
	public List<Customer> findCustomersHavingMultipleAccounts() {
		return Collections.emptyList();
	}
}
