package com.mfano.meo.hois;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository1 extends JpaRepository<User1, Integer> {

}
