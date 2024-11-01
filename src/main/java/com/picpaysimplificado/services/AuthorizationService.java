package com.picpaysimplificado.services;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.picpaysimplificado.domain.entities.User;

@Service
public class AuthorizationService {

	@Autowired
	RestTemplate restTemplate;

	@Value("${api.url.authorization}")
	private String authorizationURL;

	public boolean authorizeTransfer(User sender, BigDecimal ammount) {

		ResponseEntity<Map> response = restTemplate.getForEntity(authorizationURL, Map.class);

//		if (response.getStatusCode() == HttpStatus.OK) {
//			Boolean status = (Boolean) response.getBody().get("data.authorization");
//			System.out.println("\n\n\n" + response.toString() + "\n\n\n");
//			return Boolean.TRUE.equals(status);
//		} else
//			return false;
		
		
		if(response.getStatusCode() != HttpStatus.OK)
			return false;
		
		return true;
	}
}
