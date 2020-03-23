package com.example.process.repository;

import com.example.process.model.BPMUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BPMUserRepository extends JpaRepository<BPMUser, Long> {

    public BPMUser findByUserName(String userName);

}
