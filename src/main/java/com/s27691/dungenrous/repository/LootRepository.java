package com.s27691.dungenrous.repository;

import com.s27691.dungenrous.entity.Dungeon;
import com.s27691.dungenrous.entity.Loot;
import com.s27691.dungenrous.entity.LootId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LootRepository extends JpaRepository<Loot, LootId> {
  @Query("SELECT l FROM Loot l " +
      "JOIN FETCH l.mob " +
      "LEFT JOIN FETCH l.item " +
      "WHERE l.lootId.dungeonId = :dungeonId")
  List<Loot> findByLootId_DungeonId(@Param("dungeonId") int dungeonId);
}
