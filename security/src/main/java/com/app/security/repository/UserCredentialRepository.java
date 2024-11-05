package com.app.security.repository;

import com.app.security.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCredentialRepository extends JpaRepository<UserCredential,Integer> {

    UserCredential findByUsername(String username);
}
