package com.s27691.dungenrous.controller;

import com.s27691.dungenrous.entity.Loot;
import com.s27691.dungenrous.repository.LootRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loot")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3002"})
public class LootController {

  @Autowired
  private LootRepository lootRepository;

  @GetMapping("/by-dungeon/{dungeonId}")
  public ResponseEntity<List<Loot>> getLootByDungeon(@PathVariable int dungeonId) {
    try {
      List<Loot> loot = lootRepository.findByLootId_DungeonId(dungeonId);
      return ResponseEntity.ok(loot);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }
}