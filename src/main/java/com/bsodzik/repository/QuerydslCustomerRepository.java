package com.bsodzik.repository;

import com.bsodzik.model.Customer;
import com.bsodzik.model.QAccount;
import com.bsodzik.model.QCustomer;
import com.mysema.query.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class QuerydslCustomerRepository implements CustomerRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Customer> findByBirthDate(Date birthDateFrom, Date birthDateTo) {
		JPAQuery query = new JPAQuery(entityManager);

		return query.from(QCustomer.customer)
				.where(QCustomer.customer.birthDate.between(birthDateFrom, birthDateTo))
				.list(QCustomer.customer);
	}

	@Override
	public List<Customer> findAll(int page, int size) {
		JPAQuery query = new JPAQuery(entityManager);

		return query.from(QCustomer.customer)
				.orderBy(QCustomer.customer.id.asc())
				.limit(size)
				.offset(page * size)
				.list(QCustomer.customer);
	}

	@Override
	public Map<String, Long> countByCustomerType() {
		JPAQuery query = new JPAQuery(entityManager);

		return query.from(QCustomer.customer)
				.groupBy(QCustomer.customer.type)
				.map(QCustomer.customer.type, QCustomer.customer.count());
	}

	@Override
	public Map<String, Double> calculateCustomersBalance() {

		JPAQuery query = new JPAQuery(entityManager);

		return query.from(QCustomer.customer)
				.join(QCustomer.customer.accounts, QAccount.account)
				.groupBy(QCustomer.customer.id)
				.map(QCustomer.customer.name, QAccount.account.balance.sum());
	}

	@Override
	public List<Customer> findCustomersHavingMultipleAccounts() {
		JPAQuery query = new JPAQuery(entityManager);

		return query.from(QCustomer.customer)
				.join(QCustomer.customer.accounts, QAccount.account)
				.groupBy(QCustomer.customer.id)
				.having(QAccount.account.count().gt(1))
				.list(QCustomer.customer);
	}
}
