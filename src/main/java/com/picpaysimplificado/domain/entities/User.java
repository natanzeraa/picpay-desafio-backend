package com.picpaysimplificado.domain.entities;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

import com.picpaysimplificado.domain.enums.UserType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
@Entity(name = "users")
@Table(name = "users")
public class User implements Serializable {

	@Serial
	private static final long serialVersionUID = 4487131630354407224L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	@Email(message = "Invalid email")
	private String email;

	@Column(nullable = false, unique = true)
	private String document;

	@Column(nullable = false)
	private BigDecimal balance;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private UserType userType;

}
