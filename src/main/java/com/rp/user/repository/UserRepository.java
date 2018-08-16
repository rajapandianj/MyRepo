package com.rp.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rp.user.modal.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

}
