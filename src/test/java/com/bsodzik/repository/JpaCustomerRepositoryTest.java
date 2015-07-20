package com.bsodzik.repository;

import com.bsodzik.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class JpaCustomerRepositoryTest extends AbstractCustomerRepositoryTest {

	@Autowired
	private JpaCustomerRepository jpaRepository;

	@PostConstruct
	public void init() {
		this.repository = jpaRepository;
	}
}
