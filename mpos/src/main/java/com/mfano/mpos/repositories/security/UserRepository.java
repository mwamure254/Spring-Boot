package com.mfano.mpos.repositories.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfano.mpos.models.security.Profile;
import com.mfano.mpos.models.security.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByUsername(String username);
    User findByEmailAndUsername(String email, String username);

    User findByEmailOrUsername(String email, String username);

    User findByEmailAndPassword(String email, String password);

    User findByUsernameAndPassword(String username, String password);

    User findByEmailOrUsernameAndPassword(String email, String username, String password);
    
    User findByEmailAndUsernameAndPassword(String email, String username, String password);

    User findByBranch_Id(Long storeId);

}
