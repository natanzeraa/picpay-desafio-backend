package com.picpaysimplificado.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.picpaysimplificado.domain.entities.User;
import com.picpaysimplificado.records.NotificationRecord;

@Service
public class NotificationService {

	@Autowired
	RestTemplate restTemplate;

	@Value("${api.url.notify}")
	private String notifyURL;

	/*
	 * In the future this method can notify the receiver with the Sender + Amount of
	 * money transfered.
	 */
	public void notifyUser(User user, BigDecimal amount, String message) {
		NotificationRecord notificationRecord = new NotificationRecord(user.getEmail(), amount, message);
		ResponseEntity<String> response = restTemplate.postForEntity(notifyURL, notificationRecord, String.class);

		if (response.getStatusCode() != HttpStatus.NO_CONTENT)
			throw new RuntimeException("Service unavailable, try again later");

		System.out.println("Notification sent to " + user.getName() + "!");

	}

}
