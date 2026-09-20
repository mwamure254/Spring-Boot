package com.mfano.mpos.repositories.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfano.mpos.models.security.Profile;
import com.mfano.mpos.models.security.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password);

    User findByBranch_Id(Long storeId);

}
