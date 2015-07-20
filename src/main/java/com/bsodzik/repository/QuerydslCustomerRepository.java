package com.bsodzik.repository;

import com.bsodzik.model.Customer;
import com.mysema.query.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.bsodzik.model.QAccount.account;
import static com.bsodzik.model.QCustomer.customer;

@Repository
public class QuerydslCustomerRepository implements CustomerRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Customer> findAll(int page, int size) {
		JPAQuery query = new JPAQuery(entityManager);

		return query.from(customer)
				.orderBy(customer.id.asc())
				.offset(page * size)
				.limit(size)
				.list(customer);
	}

	@Override
	public List<Customer> findByBirthDate(Date birthDateFrom, Date birthDateTo) {
		JPAQuery query = new JPAQuery(entityManager);

		return query.from(customer)
				.where(customer.birthDate.between(birthDateFrom, birthDateTo))
				.list(customer);
	}

	@Override
	public Map<String, Long> countByCustomerType() {
		JPAQuery query = new JPAQuery(entityManager);

		return query.from(customer)
				.groupBy(customer.type)
				.map(customer.type, customer.count());
	}

	@Override
	public Map<String, Double> calculateCustomersBalance() {
		JPAQuery query = new JPAQuery(entityManager);

		return query.from(customer)
				.join(customer.accounts, account)
				.groupBy(customer.id)
				.map(customer.name, account.balance.sum());
	}

	@Override
	public List<Customer> findCustomersHavingMultipleAccounts() {
		JPAQuery query = new JPAQuery(entityManager);

		return query.from(customer)
				.join(customer.accounts, account)
				.groupBy(customer.id)
				.having(account.count().gt(1))
				.list(customer);
	}
}
