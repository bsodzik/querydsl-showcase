package com.bsodzik.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Customer {

	@Id
	private Long id;

	private String name;

	@Temporal(TemporalType.DATE)
	private Date birthDate;

	private String type;

	@OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
	private List<Account> accounts;
}
