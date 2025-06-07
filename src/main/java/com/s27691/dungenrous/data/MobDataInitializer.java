package com.s27691.dungenrous.data;

import com.s27691.dungenrous.entity.Mob;
import com.s27691.dungenrous.repository.MobRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class MobDataInitializer {

  @Autowired
  MobRepository mobRepository;

  @PostConstruct
  public void initializeMobs() {
      mobRepository.deleteAll();
      createEarlyMobs();
      createMidTierMobs();
      createHighTierMobs();
      createBossMobs();

  }

  private void createEarlyMobs() {
    createMob("Skeleton Warrior", 15, 40, 50, false);
    createMob("Goblin Scout", 12, 35, 45, false);
    createMob("Cave Rat", 8, 25, 30, false);
    createMob("Zombie Shambler", 18, 50, 60, false);
    createMob("Giant Spider", 14, 45, 55, false);
    createMob("Orc Grunt", 20, 50, 70, false);
    createMob("Shadow Wolf", 16, 55, 65, false);
  }

  private void createMidTierMobs() {
    createMob("Armored Skeleton", 35, 80, 120, false);
    createMob("Hobgoblin Warrior", 40, 90, 130, false);
    createMob("Dire Wolf", 38, 100, 110, false);
    createMob("Stone Golem", 50, 60, 180, false);
    createMob("Dark Mage", 30, 120, 90, false);
    createMob("Troll Berserker", 55, 70, 200, false);
    createMob("Wraith", 25, 110, 80, false);
  }

  private void createHighTierMobs() {
    createMob("Death Knight", 80, 150, 300, false);
    createMob("Ancient Dragon", 120, 200, 500, false);
    createMob("Demon Lord", 100, 180, 400, false);
    createMob("Lich King", 70, 220, 250, false);
    createMob("Titan Golem", 130, 100, 600, false);
    createMob("Void Reaper", 90, 190, 350, false);
  }

  private void createBossMobs() {
    createMob("The Bone Emperor", 200, 300, 1000, true);
    createMob("Shadowflame Dragon", 250, 400, 1500, true);
    createMob("Archdemon Malphas", 220, 350, 1200, true);
    createMob("The Void Sovereign", 300, 500, 2000, true);
  }

  private Mob createMob(String name, int power, int healthPoints, int energy, boolean boss) {
    Mob mob = new Mob();
    mob.setName(name);
    mob.setPower(power);
    mob.setHealthPoints(healthPoints);
    mob.setEnergy(energy);
    mob.setBoss(boss);
    return mobRepository.save(mob);
  }

}
