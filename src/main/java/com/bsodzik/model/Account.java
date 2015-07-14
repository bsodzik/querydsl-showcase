package com.bsodzik.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@ToString(exclude = "customer")
@Entity
public class Account {

	@Id
	private String iban;

	@ManyToOne
	private Customer customer;

	private Double balance;

	private String type;
}
