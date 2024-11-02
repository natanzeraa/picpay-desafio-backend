package com.picpaysimplificado.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.picpaysimplificado.domain.entities.Transfer;
import com.picpaysimplificado.records.TransferRecord;
import com.picpaysimplificado.services.TransferService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transfers")
public class TransferController {
	@Autowired
	TransferService transferService;

	@PostMapping
	public ResponseEntity<Transfer> createNewTransfer(@RequestBody @Valid TransferRecord transfer) throws Exception {
		return ResponseEntity.status(HttpStatus.CREATED).body(transferService.createTransfer(transfer));
	}
}
