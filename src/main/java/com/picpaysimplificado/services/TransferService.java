package com.picpaysimplificado.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.picpaysimplificado.domain.entities.Transfer;
import com.picpaysimplificado.domain.entities.User;
import com.picpaysimplificado.records.TransferRecord;
import com.picpaysimplificado.repositories.TransferRepository;
import com.picpaysimplificado.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class TransferService {

	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	TransferRepository transferRepository;

	@Autowired
	AuthorizationService authorizationService;

	@Autowired
	NotificationService notificationService;

	@Transactional
	public Transfer createTransfer(TransferRecord transfer) throws Exception {

		User sender = userRepository.findById(transfer.sender()).orElseThrow(() -> new Exception("Sender not found"));

		User receiver = userRepository.findById(transfer.receiver())
				.orElseThrow(() -> new Exception("Receiver not found"));

		if (transfer.sender() == transfer.receiver())
			throw new Exception("Operation not permitted");

		boolean authorized = authorizationService.authorizeTransfer(sender, transfer.amount());

		if (!authorized)
			throw new Exception("Transfer not authorized, try again later.");

		userService.validateTransfer(sender, transfer.amount());

		Transfer newTransfer = new Transfer();
		newTransfer.setSender(sender);
		newTransfer.setReceiver(receiver);
		newTransfer.setAmount(transfer.amount());
		transferRepository.save(newTransfer);

		sender.setBalance(sender.getBalance().subtract(transfer.amount()));
		receiver.setBalance(receiver.getBalance().add(transfer.amount()));
		userService.saveUser(sender);
		userService.saveUser(receiver);

		notificationService.notifyUser(sender, transfer.amount(),
				"You transfered + " + String.valueOf(transfer.amount()) + "to " + String.valueOf(receiver.getName()));

		notificationService.notifyUser(receiver, transfer.amount(),
				"You received + " + String.valueOf(transfer.amount()) + "from " + String.valueOf(sender.getName()));

		return newTransfer;

	}

}
