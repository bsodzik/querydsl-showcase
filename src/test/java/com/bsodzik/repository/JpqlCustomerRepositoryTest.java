package com.bsodzik.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.bsodzik.Application;

import javax.annotation.PostConstruct;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class JpqlCustomerRepositoryTest extends AbstractCustomerRepositoryTest {

	@Autowired
	private JpqlCustomerRepository jpqlRepository;

	@PostConstruct
	public void init() {
		this.repository = jpqlRepository;
	}
}