package com.s27691.dungenrous.service;

import com.s27691.dungenrous.entity.ArcaneCrusader;
import com.s27691.dungenrous.entity.Character;
import com.s27691.dungenrous.dto.CombatResult;
import com.s27691.dungenrous.entity.Mage;
import com.s27691.dungenrous.entity.Mob;
import com.s27691.dungenrous.entity.Paladin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CombatService {

  @Autowired
  private CharacterService characterService;

  @Autowired
  private MobService mobService;

  private Random random = new Random();

  private Map<String, Mob> activeCombats = new HashMap<>();

  public CombatResult performBasicAttack(Long playerId, Long mobId){
    Character character = characterService.getCharacterByPlayerId(playerId);
    Mob combatMob = getOrCreateCombatMob(playerId, mobId);

    if(character == null || combatMob == null){
      return new CombatResult(character, combatMob, "Invalid character or mob", true);
    }

    List<String> combatLog = new ArrayList<>();

    int damage = character.getTotalStrength() + random.nextInt(10);
    combatMob.setHealthPoints(Math.max(0, combatMob.getHealthPoints() - damage));
    combatLog.add("You perform a basic attack for " + damage + " damage");

    if(mobService.isDefeated(combatMob)){
      combatLog.add(combatMob.getName() + " has been defeated!");
      clearCombat(playerId, mobId);
      return new CombatResult(character, combatMob, combatLog, true);
    }

    return processMobAttack(character, combatMob, combatLog, playerId, mobId);
  }

  public CombatResult performPowerStrike(Long playerId, Long mobId){
    Character character = characterService.getCharacterByPlayerId(playerId);
    Mob combatMob = getOrCreateCombatMob(playerId, mobId);

    if (character == null || combatMob == null) {
      return new CombatResult(character, combatMob, "Invalid character or mob", false);
    }

    List<String> combatLog = new ArrayList<>();

    if (character instanceof Paladin paladin) {
      if (!paladin.hasStamina(20)) {
        combatLog.add("Not enough stamina for power strike");
        return new CombatResult(character, combatMob, combatLog, false);
      }
      combatMob = paladin.powerStrike(combatMob);
      combatLog.add("You perform a powerful paladin strike!");
      character = characterService.saveCharacter(character);

    } else if (character instanceof ArcaneCrusader crusader) {
      if (!crusader.hasStamina(20)) {
        combatLog.add("Not enough stamina for power strike");
        return new CombatResult(character, combatMob, combatLog, false);
      }
      combatMob = crusader.powerStrike(combatMob);
      combatLog.add("You perform a powerful crusader strike!");
      character = characterService.saveCharacter(character);

    } else {
      combatLog.add("This character cannot perform power strikes");
      return new CombatResult(character, combatMob, combatLog, false);
    }

    updateCombatMob(playerId, mobId, combatMob);

    if (mobService.isDefeated(combatMob)) {
      combatLog.add(combatMob.getName() + " has been defeated!");
      clearCombat(playerId, mobId);
      return new CombatResult(character, combatMob, combatLog, true);
    }

    return processMobAttack(character, combatMob, combatLog, playerId, mobId);
  }

  public CombatResult performDefenseStance(Long playerId) {
    Character character = characterService.getCharacterByPlayerId(playerId);

    if (character == null) {
      return new CombatResult(null, null, "Invalid character", false);
    }

    List<String> combatLog = new ArrayList<>();
    boolean success = false;

    if (character instanceof Paladin paladin) {
      if (!paladin.hasStamina(15)) {
        combatLog.add("Not enough stamina for shield stance");
        return new CombatResult(character, null, combatLog, false);
      }
      success = paladin.shieldStance();
      if (success) {
        combatLog.add("You take a defensive paladin stance (+5 defense)");
        character = characterService.saveCharacter(character);
      }
    } else if (character instanceof ArcaneCrusader crusader) {
      if (!crusader.hasStamina(15)) {
        combatLog.add("Not enough stamina for shield stance");
        return new CombatResult(character, null, combatLog, false);
      }
      success = crusader.shieldStance();
      if (success) {
        combatLog.add("You take a defensive crusader stance (+5 defense)");
        character = characterService.saveCharacter(character);
      }
    } else {
      combatLog.add("This character cannot use shield stance");
    }

    return new CombatResult(character, null, combatLog, false);
  }

  public CombatResult castSpell(Long playerId, Long mobId) {
    Character character = characterService.getCharacterByPlayerId(playerId);
    Mob combatMob = getOrCreateCombatMob(playerId, mobId);

    if (character == null || combatMob == null) {
      return new CombatResult(character, combatMob, "Invalid character or mob", false);
    }

    List<String> combatLog = new ArrayList<>();

    if (character instanceof Mage mage) {
      if (!mage.hasMana(25)) {
        combatLog.add("Not enough mana to cast spell");
        return new CombatResult(character, combatMob, combatLog, false);
      }
      combatMob = mage.castSpell(combatMob);
      combatLog.add("You cast a powerful mage spell!");
      character = characterService.saveCharacter(character);

    } else if (character instanceof ArcaneCrusader crusader) {
      if (!crusader.hasMana(25)) {
        combatLog.add("Not enough mana to cast spell");
        return new CombatResult(character, combatMob, combatLog, false);
      }
      combatMob = crusader.castSpell(combatMob);
      combatLog.add("You cast a powerful arcane spell!");
      character = characterService.saveCharacter(character);

    } else {
      combatLog.add("This character cannot cast spells");
      return new CombatResult(character, combatMob, combatLog, false);
    }

    updateCombatMob(playerId, mobId, combatMob);

    if (mobService.isDefeated(combatMob)) {
      combatLog.add(combatMob.getName() + " has been defeated!");
      clearCombat(playerId, mobId);
      return new CombatResult(character, combatMob, combatLog, true);
    }

    return processMobAttack(character, combatMob, combatLog, playerId, mobId);
  }

  public CombatResult castBarrier(Long playerId) {
    Character character = characterService.getCharacterByPlayerId(playerId);

    if (character == null) {
      return new CombatResult(character, null, "Invalid character", false);
    }

    List<String> combatLog = new ArrayList<>();
    boolean success = false;

    if (character instanceof Mage mage) {
      if (!mage.hasMana(20)) {
        combatLog.add("Not enough mana to cast barrier");
        return new CombatResult(character, null, combatLog, false);
      }
      success = mage.castBarrier();
      if (success) {
        combatLog.add("You create a magical mage barrier (+8 defense)");
        character = characterService.saveCharacter(character);
      }
    } else if (character instanceof ArcaneCrusader crusader) {
      if (!crusader.hasMana(20)) {
        combatLog.add("Not enough mana to cast barrier");
        return new CombatResult(character, null, combatLog, false);
      }
      success = crusader.castBarrier();
      if (success) {
        combatLog.add("You create a magical arcane barrier (+8 defense)");
        character = characterService.saveCharacter(character);
      }
    } else {
      combatLog.add("This character cannot cast barrier");
    }

    return new CombatResult(character, null, combatLog, false);
  }

  public CombatResult usePotion(Long playerId) {
    Character character = characterService.getCharacterByPlayerId(playerId);

    if (character == null) {
      return new CombatResult(character, null, "Invalid character", false);
    }

    if (character.getCurrentHealthPoints() >= character.getMaxHealthPoints()) {
      return new CombatResult(character, null, "Health is already full", false);
    }

    if (character.getPotionQuantity() <= 0) {
      return new CombatResult(character, null, "No potions available", false);
    }

    character = characterService.usePotion(playerId);

    List<String> combatLog = new ArrayList<>();
    combatLog.add("You use a healing potion");

    return new CombatResult(character, null, combatLog, false);
  }

  public CombatResult recharge(Long playerId) {
    Character character = characterService.getCharacterByPlayerId(playerId);

    if (character == null) {
      return new CombatResult(null, null, "Invalid character", false);
    }

    List<String> combatLog = new ArrayList<>();

    if (character instanceof Paladin paladin) {
      paladin.setCurrentStamina(Math.min(paladin.getMaxStamina(), paladin.getCurrentStamina() + 30));
      combatLog.add("You recharge your energy (+30 Stamina)");
    } else if (character instanceof Mage mage) {
      mage.setCurrentMana(Math.min(mage.getMaxMana(), mage.getCurrentMana() + 25));
      combatLog.add("You recharge your energy (+25 Mana)");
    } else if (character instanceof ArcaneCrusader crusader) {
      crusader.setCurrentStamina(Math.min(crusader.getMaxStamina(), crusader.getCurrentStamina() + 30));
      crusader.setCurrentMana(Math.min(crusader.getMaxMana(), crusader.getCurrentMana() + 25));
      combatLog.add("You recharge your energy (+30 Stamina, +25 Mana)");
    }

    character = characterService.saveCharacter(character);

    return new CombatResult(character, null, combatLog, false);
  }

  private CombatResult processMobAttack(Character character, Mob combatMob, List<String> combatLog, Long playerId, Long mobId){

    int damage;
    if(combatMob.getEnergy() >=80){
      damage = (int) (combatMob.getPower() * 1.5) + random.nextInt(10);
      combatMob.setEnergy(0);
      combatLog.add(combatMob.getName() + " performs a FURY STRIKE for " + damage + " damage!");
    } else {
      damage = combatMob.getPower() + random.nextInt(5);
      combatMob.setEnergy(Math.min(100, combatMob.getEnergy() + 20));
      combatLog.add(combatMob.getName() + " attacks for " + damage + " damage (Fury: " + combatMob.getEnergy() + "/100)");
    }

    updateCombatMob(playerId, mobId, combatMob);

    int actualDamage = Math.max(1, damage - character.getTotalDefense());
    character.takeDamage(actualDamage);

    combatLog.add("You take " + actualDamage + " damage after defense");

    if(character.getCurrentHealthPoints() <= 0){
      combatLog.add("You have been defeated! Retreating to town...");
      clearCombat(playerId, mobId);
      return new CombatResult(character, combatMob, combatLog, true);
    }

    character.resetTemporaryDefense();
    character = characterService.saveCharacter(character);

    return new CombatResult(character, combatMob, combatLog, false);
  }

  public CombatResult startCombat(Long playerId, Long mobId) {
    Character character = characterService.getCharacterByPlayerId(playerId);
    Mob combatMob = getOrCreateCombatMob(playerId, mobId);

    if (character == null || combatMob == null) {
      return new CombatResult(character, combatMob, "Cannot start combat", true);
    }

    combatMob.setEnergy(0);
    updateCombatMob(playerId, mobId, combatMob);

    List<String> combatLog = new ArrayList<>();
    combatLog.add("Combat begins against " + combatMob.getName() + "!");

    return new CombatResult(character, combatMob, combatLog, false);
  }

  private Mob getOrCreateCombatMob(Long playerId, Long mobId) {
    String combatKey = playerId + "_" + mobId;

    if (activeCombats.containsKey(combatKey)) {
      return activeCombats.get(combatKey);
    }

    Mob originalMob = mobService.createCombatInstance(mobId);
    if (originalMob != null) {
      activeCombats.put(combatKey, originalMob);
    }
    return originalMob;
  }

  private void updateCombatMob(Long playerId, Long mobId, Mob combatMob) {
    String combatKey = playerId + "_" + mobId;
    activeCombats.put(combatKey, combatMob);
  }

  private void clearCombat(Long playerId, Long mobId) {
    String combatKey = playerId + "_" + mobId;
    activeCombats.remove(combatKey);
  }

  public boolean isCombatOver(Character character, Mob combatMob) {
    return character.getCurrentHealthPoints() <= 0 || mobService.isDefeated(combatMob);
  }
}