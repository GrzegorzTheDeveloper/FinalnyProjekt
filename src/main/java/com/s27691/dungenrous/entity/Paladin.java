package com.s27691.dungenrous.entity;

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
public class Paladin extends Character implements IPaladin {

  private int maxStamina = 100;
  private int currentStamina = 100;

  Random random = new Random();

  public boolean hasStamina(int amount) {
      return currentStamina >= amount;
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
}
