package com.ipstresser.app.repositories;

import com.ipstresser.app.domain.entities.Cryptocurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptocurrencyRepository extends JpaRepository<Cryptocurrency, String> {
}
