package com.s27691.dungenrous.dto;

import com.s27691.dungenrous.entity.Character;
import com.s27691.dungenrous.entity.Mob;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CombatResult {

  private Character character;
  private Mob mob;
  private List<String> combatLog;

  private boolean combatEnded;
  private boolean victory;

  public CombatResult(){
    this.combatLog = new ArrayList<>();
    this.combatEnded = false;
    this.victory = false;
  }

  public CombatResult(Character character, Mob mob, String message, boolean combatEnded){
    this.character = character;
    this.mob = mob;
    this.combatLog = new ArrayList<>();
    this.combatLog.add(message);
    this.combatEnded = combatEnded;
    this.victory = combatEnded && (mob != null && mob.getHealthPoints() <= 0);
  }

  public CombatResult(Character character, Mob mob, List<String> combatLog, boolean combatEnded) {
    this.character = character;
    this.mob = mob;
    this.combatLog = new ArrayList<>(combatLog);
    this.combatEnded = combatEnded;
    this.victory = combatEnded && (mob != null && mob.getHealthPoints() <= 0);
  }

  public void setCombatLog(List<String> combatLog) {
    this.combatLog = combatLog != null ? new ArrayList<>(combatLog) : new ArrayList<>();
  }

  public void addLogMessage(String message) {
    if (this.combatLog == null) {
      this.combatLog = new ArrayList<>();
    }
    this.combatLog.add(message);
  }

  public void addLogMessages(List<String> messages) {
    if (this.combatLog == null) {
      this.combatLog = new ArrayList<>();
    }
    this.combatLog.addAll(messages);
  }

  public String getLastLogMessage() {
    if (combatLog == null || combatLog.isEmpty()) {
      return "";
    }
    return combatLog.get(combatLog.size() - 1);
  }

  public boolean isPlayerDefeated() {
    return combatEnded && character != null && character.getCurrentHealthPoints() <= 0;
  }

  public boolean isMobDefeated() {
    return combatEnded && mob != null && mob.getHealthPoints() <= 0;
  }

  @Override
  public String toString() {
    return "CombatResult{" +
        "character=" + (character != null ? character.getClass().getSimpleName() : "null") +
        ", mob=" + (mob != null ? mob.getName() : "null") +
        ", combatEnded=" + combatEnded +
        ", victory=" + victory +
        ", logEntries=" + (combatLog != null ? combatLog.size() : 0) +
        '}';
  }

}


