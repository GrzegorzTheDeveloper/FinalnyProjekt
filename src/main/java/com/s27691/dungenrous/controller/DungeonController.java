package com.s27691.dungenrous.controller;

import com.s27691.dungenrous.entity.Dungeon;
import com.s27691.dungenrous.repository.DungeonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/dungeons")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3002"})
public class DungeonController {

  @Autowired
  private DungeonRepository dungeonRepository;


  @GetMapping
  public ResponseEntity<List<Dungeon>> getAllDungeons() {
    List<Dungeon> dungeons = dungeonRepository.findAll();
    return ResponseEntity.ok(dungeons);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Dungeon> getDungeon(@PathVariable int id) {
    return dungeonRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/by-level/{maxLevel}")
  public ResponseEntity<List<Dungeon>> getDungeonsByLevel(@PathVariable int maxLevel) {
    List<Dungeon> dungeons = dungeonRepository.findAll().stream()
        .filter(dungeon -> dungeon.getRequiredLevel() <= maxLevel)
        .sorted((d1, d2) -> Integer.compare(d1.getRequiredLevel(), d2.getRequiredLevel()))
        .toList();
    return ResponseEntity.ok(dungeons);
  }

  @GetMapping("/boss-fights")
  public ResponseEntity<List<Dungeon>> getBossFights() {
    List<Dungeon> bossFights = dungeonRepository.findAll().stream()
        .filter(dungeon -> dungeon instanceof com.s27691.dungenrous.entity.BossFight)
        .toList();
    return ResponseEntity.ok(bossFights);
  }

  @GetMapping("/pass-through")
  public ResponseEntity<List<Dungeon>> getPassThroughDungeons() {
    List<Dungeon> passThroughDungeons = dungeonRepository.findAll().stream()
        .filter(dungeon -> dungeon instanceof com.s27691.dungenrous.entity.PassThrough)
        .toList();
    return ResponseEntity.ok(passThroughDungeons);
  }
}