package com.picpaysimplificado.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.picpaysimplificado.domain.entities.User;
import com.picpaysimplificado.domain.enums.UserType;
import com.picpaysimplificado.records.UserRecord;
import com.picpaysimplificado.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	public User findUserById(Integer id) throws Exception {
		return userRepository.findById(id).orElseThrow(() -> new Exception("User not found"));
	}

	public User createUser(UserRecord user) {
		
		User newUser = new User();

		newUser.setName(user.name());
		newUser.setEmail(user.email());
		newUser.setDocument(user.document());
		newUser.setBalance(user.balance());
		newUser.setPassword(user.password());
		newUser.setUserType(UserType.valueOf(user.userType().toUpperCase()));

		return userRepository.save(newUser);
	}

	public void validateTransfer(User sender, BigDecimal ammount) throws Exception {

		if (sender.getUserType().toString() == "MERCHANT")
			throw new Exception("Merchants cannot send transfers");

		if (sender.getBalance().compareTo(ammount) <= 0)
			throw new Exception("Not enought balance");
	}

	public void saveUser(User user) {
		userRepository.save(user);
	}

}
