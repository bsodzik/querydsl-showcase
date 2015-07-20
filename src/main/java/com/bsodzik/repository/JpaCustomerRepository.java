package com.bsodzik.repository;

import com.bsodzik.model.Account;
import com.bsodzik.model.Account_;
import com.bsodzik.model.Customer;
import com.bsodzik.model.Customer_;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class JpaCustomerRepository implements CustomerRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Customer> findAll(int page, int size) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
		Root<Customer> customer = query.from(Customer.class);
		query.orderBy(builder.asc(customer.get(Customer_.id)));

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
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
		Root<Customer> customer = query.from(Customer.class);

		Path<String> type = customer.get(Customer_.type);
		Expression<Long> count = builder.count(customer.get(Customer_.id));
		CriteriaQuery<Object[]> select = query.multiselect(type, count).groupBy(type);

		List<Object[]> result = entityManager.createQuery(select).getResultList();

		return result.stream().collect(Collectors.toMap(arr -> (String) arr[0], arr -> (Long) arr[1]));
	}

	@Override
	public Map<String, Double> calculateCustomersBalance() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
		Root<Customer> customer = query.from(Customer.class);
		ListJoin<Customer, Account> account = customer.join(Customer_.accounts);

		Path<String> name = customer.get(Customer_.name);
		Expression<Double> sum = builder.sum(account.get(Account_.balance));
		CriteriaQuery<Object[]> select = query.multiselect(name, sum).groupBy(name);

		List<Object[]> result = entityManager.createQuery(select).getResultList();

		return result.stream().collect(Collectors.toMap(arr -> (String) arr[0], arr -> (Double) arr[1]));
	}

	@Override
	public List<Customer> findCustomersHavingMultipleAccounts() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
		Root<Customer> customer = query.from(Customer.class);
		ListJoin<Customer, Account> account = customer.join(Customer_.accounts);
		query.groupBy(customer.get(Customer_.id))
				.having(builder.gt(builder.count(account.get(Account_.iban)), 1));

		return entityManager.createQuery(query).getResultList();
	}
}
