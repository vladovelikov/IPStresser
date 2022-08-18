package com.ipstresser.app.repositories;

import com.ipstresser.app.domain.entities.Attack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AttackRepository extends JpaRepository<Attack, String> {

    List<Attack> findAllByAttacker_Username(String username);

    @Modifying
    @Transactional
    @Query("DELETE FROM Attack a where a.attacker.id=:id")
    void deleteAllAttacksForUser(@Param("id") String id);
}
