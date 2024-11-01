package com.picpaysimplificado.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.picpaysimplificado.domain.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	public boolean existsByEmail(String email);

	public boolean existsByDocument(String document);
}
