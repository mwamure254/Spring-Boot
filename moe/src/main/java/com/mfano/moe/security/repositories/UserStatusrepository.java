package com.mfano.moe.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfano.moe.security.models.UserStatus;

public interface UserStatusrepository extends JpaRepository<UserStatus, Integer>{

}
