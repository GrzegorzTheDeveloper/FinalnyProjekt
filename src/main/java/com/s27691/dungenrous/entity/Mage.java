package com.s27691.dungenrous.entity;

import com.s27691.dungenrous.interfaces.IMage;
import jakarta.persistence.Entity;
import java.util.Random;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Mage extends Character implements IMage {

  private int maxMana = 100;
  private int currentMana = 100;

  Random random = new Random();

  public boolean hasMana(int amount) {
    return currentMana >= amount;
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
}
