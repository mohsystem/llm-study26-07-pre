package com.um.springbootprojstructure.repository;

import com.um.springbootprojstructure.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findByRefreshJti(String refreshJti);
}
