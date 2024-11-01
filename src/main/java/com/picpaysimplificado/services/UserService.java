package com.picpaysimplificado.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.picpaysimplificado.domain.entities.User;
import com.picpaysimplificado.domain.enums.UserType;
import com.picpaysimplificado.records.UserRecord;
import com.picpaysimplificado.repositories.UserRepository;

@Service
public class UserService {

	UserRepository userRepository;

	UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void validateTransfer(User sender, BigDecimal ammount) {

		if (sender.getUserType().toString() == "MERCHANT") {
			throw new RuntimeException("Merchants cannot send transfers");
		}

		if (sender.getBalance().compareTo(ammount) <= 0) {
			throw new RuntimeException("Not enought balance");
		}

	}

	public User createUser(UserRecord user) {
		if (userRepository.existsByEmail(user.email()) || userRepository.existsByDocument(user.document())) {
			// Handle it as exceptions in a future
//			return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
			throw new RuntimeException("User email or document already exists");
		}

		User newUser = new User();

		newUser.setName(user.name());
		newUser.setEmail(user.email());
		newUser.setDocument(user.document());
		newUser.setBalance(user.balance());
		newUser.setPassword(user.password());
		newUser.setUserType(UserType.valueOf(user.userType().toUpperCase()));

		return userRepository.save(newUser);
	}

	public void saveUser(User user) {
		userRepository.save(user);
	}

	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	public User findUserById(Integer id) {
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
	}

}
