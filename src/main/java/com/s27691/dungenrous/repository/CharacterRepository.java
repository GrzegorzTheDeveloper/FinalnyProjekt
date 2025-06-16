package com.s27691.dungenrous.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.s27691.dungenrous.entity.Character;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {
  @Query("SELECT DISTINCT c FROM Character c " +
      "LEFT JOIN FETCH c.fraction " +
      "LEFT JOIN FETCH c.player " +
      "LEFT JOIN FETCH c.itemsOwned " +
      "WHERE c.player.id = :playerId " +
      "ORDER BY c.id")
  Optional<Character> findByPlayerIdWithDetails(@Param("playerId") Long playerId);

  @Query("SELECT DISTINCT c FROM Character c " +
      "LEFT JOIN FETCH c.currentEquipment " +
      "WHERE c.id = :characterId")
  Optional<Character> findByIdWithEquipment(@Param("characterId") Long characterId);
}
