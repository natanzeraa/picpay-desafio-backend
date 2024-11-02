package com.picpaysimplificado.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.picpaysimplificado.domain.entities.Transfer;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Integer> {
}
