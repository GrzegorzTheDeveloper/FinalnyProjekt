package com.s27691.dungenrous.controller;
import com.s27691.dungenrous.dto.DungeonCompletionResult;
import com.s27691.dungenrous.entity.Character;
import com.s27691.dungenrous.service.CharacterService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/characters")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3002"})
public class CharacterController {

  @Autowired
  private CharacterService characterService;


  @PostMapping("/create")
  public ResponseEntity<Character> createCharacter(
      @RequestParam String playerNickname,
      @RequestParam String fractionType,
      @RequestParam String characterClass) {
    try {
      Character character = characterService.createCharacter(playerNickname, fractionType, characterClass);
      return ResponseEntity.ok(character);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Character> getCharacter(@PathVariable Long id) {
    Character character = characterService.getCharacter(id);
    return character != null ? ResponseEntity.ok(character) : ResponseEntity.notFound().build();
  }

  @GetMapping("/player/{playerId}")
  public ResponseEntity<Character> getCharacterByPlayerId(@PathVariable Long playerId) {
    Character character = characterService.getCharacterByPlayerId(playerId);
    return character != null ? ResponseEntity.ok(character) : ResponseEntity.notFound().build();
  }


  @PostMapping("/{playerId}/gain-experience")
  public ResponseEntity<Character> gainExperience(
      @PathVariable Long playerId,
      @RequestParam int experienceGained) {
    Character character = characterService.gainExperience(playerId, experienceGained);
    return character != null ? ResponseEntity.ok(character) : ResponseEntity.badRequest().build();
  }

  @PostMapping("/{playerId}/increase-attribute")
  public ResponseEntity<Character> increaseAttribute(
      @PathVariable Long playerId,
      @RequestParam String attribute) {
    Character character = characterService.increaseAttribute(playerId, attribute);
    return character != null ? ResponseEntity.ok(character) : ResponseEntity.badRequest().build();
  }

  @PostMapping("/{playerId}/evolve")
  public ResponseEntity<Character> evolveToArcaneCrusader(@PathVariable Long playerId) {
    Character character = characterService.evolveToArcaneCrusader(playerId);
    return character != null ? ResponseEntity.ok(character) : ResponseEntity.badRequest().build();
  }


  @PostMapping("/{playerId}/equip-item/{itemId}")
  public ResponseEntity<Character> equipItem(
      @PathVariable Long playerId,
      @PathVariable Long itemId) {
    Character character = characterService.equipItem(playerId, itemId);
    return character != null ? ResponseEntity.ok(character) : ResponseEntity.badRequest().build();
  }

  @PostMapping("/{playerId}/unequip-item/{itemId}")
  public ResponseEntity<Character> unequipItem(
      @PathVariable Long playerId,
      @PathVariable Long itemId) {
    Character character = characterService.unequipItem(playerId, itemId);
    return character != null ? ResponseEntity.ok(character) : ResponseEntity.badRequest().build();
  }

  @PostMapping("/{playerId}/add-item/{itemId}")
  public ResponseEntity<Character> addItemToInventory(
      @PathVariable Long playerId,
      @PathVariable Long itemId) {
    Character character = characterService.addItemToInventory(playerId, itemId);
    return character != null ? ResponseEntity.ok(character) : ResponseEntity.badRequest().build();
  }

  @PostMapping("/{playerId}/remove-item/{itemId}")
  public ResponseEntity<Character> removeItemFromInventory(
      @PathVariable Long playerId,
      @PathVariable Long itemId) {
    Character character = characterService.removeItemFromInventory(playerId, itemId);
    return character != null ? ResponseEntity.ok(character) : ResponseEntity.badRequest().build();
  }

  @PostMapping("/{playerId}/take-damage")
  public ResponseEntity<Character> takeDamage(
      @PathVariable Long playerId,
      @RequestParam int damage) {
    Character character = characterService.takeDamage(playerId, damage);
    return character != null ? ResponseEntity.ok(character) : ResponseEntity.badRequest().build();
  }

  @PostMapping("/{playerId}/use-potion")
  public ResponseEntity<Character> usePotion(@PathVariable Long playerId) {
    Character character = characterService.usePotion(playerId);
    return character != null ? ResponseEntity.ok(character) : ResponseEntity.badRequest().build();
  }


  @PostMapping("/{playerId}/enter-dungeon/{dungeonId}")
  public ResponseEntity<Character> enterDungeon(
      @PathVariable Long playerId,
      @PathVariable Long dungeonId) {
    Character character = characterService.enterDungeon(playerId);
    return character != null ? ResponseEntity.ok(character) : ResponseEntity.badRequest().build();
  }

  @PostMapping("/{playerId}/complete-dungeon/{dungeonId}")
  public ResponseEntity<DungeonCompletionResult> completeDungeon(
      @PathVariable Long playerId,
      @PathVariable int dungeonId) {
    DungeonCompletionResult result = characterService.completeDungeon(playerId, dungeonId);
    return result != null ? ResponseEntity.ok(result) : ResponseEntity.badRequest().build();
  }

  @PostMapping("/{playerId}/collect-loot/{itemId}")
  public ResponseEntity<Character> collectLootItem(
      @PathVariable Long playerId,
      @PathVariable Long itemId) {
    Character character = characterService.collectLootItem(playerId, itemId);
    return character != null ? ResponseEntity.ok(character) : ResponseEntity.badRequest().build();
  }

  @PostMapping("/{playerId}/reject-loot/{itemId}")
  public ResponseEntity<Character> rejectLootItem(
      @PathVariable Long playerId,
      @PathVariable Long itemId) {
    Character character = characterService.rejectLootItem(playerId, itemId);
    return character != null ? ResponseEntity.ok(character) : ResponseEntity.notFound().build();
  }


  @PostMapping("/{playerId}/restore")
  public ResponseEntity<Character> restoreCharacterToFullStrength(@PathVariable Long playerId) {
    Character character = characterService.restoreCharacterToFullStrength(playerId);
    return character != null ? ResponseEntity.ok(character) : ResponseEntity.notFound().build();
  }


  @PostMapping("/{id}/save")
  public ResponseEntity<Character> saveCharacter(@PathVariable Long id, @RequestBody Character character) {
    character.setId(id);
    Character savedCharacter = characterService.saveCharacter(character);
    return ResponseEntity.ok(savedCharacter);
  }
}
