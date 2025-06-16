package com.s27691.dungenrous.controller;

import com.s27691.dungenrous.dto.CombatResult;
import com.s27691.dungenrous.service.CombatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/combat")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3002"})
public class CombatController {

  @Autowired
  private CombatService combatService;


  @PostMapping("/start")
  public ResponseEntity<CombatResult> startCombat(
      @RequestParam Long playerId,
      @RequestParam Long mobId) {
    CombatResult result = combatService.startCombat(playerId, mobId);
    return ResponseEntity.ok(result);
  }


  @PostMapping("/basic-attack")
  public ResponseEntity<CombatResult> performBasicAttack(
      @RequestParam Long playerId,
      @RequestParam Long mobId) {
    CombatResult result = combatService.performBasicAttack(playerId, mobId);
    return ResponseEntity.ok(result);
  }

  @PostMapping("/use-potion")
  public ResponseEntity<CombatResult> usePotion(@RequestParam Long playerId) {
    CombatResult result = combatService.usePotion(playerId);
    return ResponseEntity.ok(result);
  }


  @PostMapping("/power-strike")
  public ResponseEntity<CombatResult> performPowerStrike(
      @RequestParam Long playerId,
      @RequestParam Long mobId) {
    CombatResult result = combatService.performPowerStrike(playerId, mobId);
    return ResponseEntity.ok(result);
  }

  @PostMapping("/defense-stance")
  public ResponseEntity<CombatResult> performDefenseStance(@RequestParam Long playerId) {
    CombatResult result = combatService.performDefenseStance(playerId);
    return ResponseEntity.ok(result);
  }


  @PostMapping("/cast-spell")
  public ResponseEntity<CombatResult> castSpell(
      @RequestParam Long playerId,
      @RequestParam Long mobId) {
    CombatResult result = combatService.castSpell(playerId, mobId);
    return ResponseEntity.ok(result);
  }

  @PostMapping("/cast-barrier")
  public ResponseEntity<CombatResult> castBarrier(@RequestParam Long playerId) {
    CombatResult result = combatService.castBarrier(playerId);
    return ResponseEntity.ok(result);
  }


  @PostMapping("/recharge")
  public ResponseEntity<CombatResult> recharge(@RequestParam Long playerId) {
    CombatResult result = combatService.recharge(playerId);
    return ResponseEntity.ok(result);
  }

}