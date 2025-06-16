package com.s27691.dungenrous.service;

import com.s27691.dungenrous.entity.Mob;
import com.s27691.dungenrous.repository.MobRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MobService {

  @Autowired
  private MobRepository mobRepository;

  public Mob createCombatInstance(Long mobId){
    Mob originalMob = mobRepository.findById(mobId).orElse(null);
    if(originalMob == null) return null;

    Mob combatMob = new Mob();
    combatMob.setId(originalMob.getId());
    combatMob.setName(originalMob.getName());
    combatMob.setPower(originalMob.getPower());
    combatMob.setEnergy(originalMob.getEnergy());
    combatMob.setHealthPoints(originalMob.getHealthPoints());
    combatMob.setBoss(originalMob.isBoss());

    return combatMob;
  }

  public Mob takeDamage(Mob combatMob, int damage) {
    combatMob.setHealthPoints(Math.max(0, combatMob.getHealthPoints() - damage));
    return combatMob;
  }

  public boolean isDefeated(Mob combatMob){
    return combatMob.getHealthPoints() <= 0;
  }

}
