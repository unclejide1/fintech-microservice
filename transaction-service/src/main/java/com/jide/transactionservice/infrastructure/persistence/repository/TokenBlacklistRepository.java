package com.jide.transactionservice.infrastructure.persistence.repository;

import com.jide.transactionservice.domain.entities.TokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Integer> {
  public boolean existsByToken(String token);
}