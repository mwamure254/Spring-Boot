package com.mfano.meo.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Otp2Repository extends JpaRepository<Otp2, Long>{

}
