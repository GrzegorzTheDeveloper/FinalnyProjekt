package com.s27691.dungenrous.entity;

import com.s27691.dungenrous.interfaces.IMage;
import com.s27691.dungenrous.interfaces.IPaladin;
import jakarta.persistence.Entity;
import java.util.Random;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ArcaneCrusader extends Character implements IMage, IPaladin {

  private int maxStamina = 100;
  private int currentStamina = 100;
  private int maxMana = 100;
  private int currentMana = 100;

  Random random = new Random();

  public boolean hasStamina(int amount) {
    return currentStamina >= amount;
  }

  public boolean hasMana(int amount) {
    return currentMana >= amount;
  }

  @Override
  public boolean shieldStance() {
    if (this.currentStamina >= 15) {
      this.currentStamina -= 15;
      this.addTemporaryDefense(5);
      return true;
    }
    return false;
  }

  @Override
  public Mob powerStrike(Mob mob) {
    if (this.currentStamina >= 20) {
      this.currentStamina -= 20;

      int damage = (int)(getTotalStrength() * 1.5) + random.nextInt(15);
      mob.setHealthPoints(Math.max(0, mob.getHealthPoints() - damage));
    }
    return mob;
  }

  @Override
  public boolean castBarrier() {
    if (this.currentMana >= 20) {
      this.currentMana -= 20;
      this.addTemporaryDefense(8);
      return true;
    }
    return false;
  }

  @Override
  public Mob castSpell(Mob mob) {
    if (this.currentMana >= 25) {
      this.currentMana -= 25;

      int damage = getTotalIntellect() * 2 + random.nextInt(20);
      mob.setHealthPoints(Math.max(0, mob.getHealthPoints() - damage));
    }
    return mob;
  }

  public void recharge() {
    this.currentStamina = Math.min(maxStamina, this.currentStamina + 30);
    this.currentMana = Math.min(maxMana, this.currentMana + 25);
  }
}
