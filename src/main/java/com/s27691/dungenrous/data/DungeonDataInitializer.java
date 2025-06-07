package com.s27691.dungenrous.data;

import com.s27691.dungenrous.entity.BossFight;
import com.s27691.dungenrous.entity.PassThrough;
import com.s27691.dungenrous.repository.DungeonRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DungeonDataInitializer {

  @Autowired
  private DungeonRepository dungeonRepository;

  @PostConstruct
  public void initializeDungeons(){
    if(dungeonRepository.count()>0)
      return;
    createEarlyDungeons();
    createMidTierDungeons();
    createHighTierDungeons();
  }

  private void createEarlyDungeons() {
    createPassThrough(1, "Goblin Cave", 1, 50, 0.3f);
    createPassThrough(2, "Dark Forest Path", 2, 75, 0.4f);
    createPassThrough(3, "Abandoned Mine", 3, 60, 0.35f);

    createBossFight(4, "Skeleton Lord's Lair", 4);
    createBossFight(5, "Spider Queen's Den", 5);
  }

  private void createMidTierDungeons() {
    createPassThrough(6, "Haunted Catacombs", 8, 100, 0.5f);
    createPassThrough(7, "Crystal Caverns", 10, 80, 0.45f);

    createBossFight(8, "Dragon's Chamber", 12);
    createBossFight(9, "Lich King's Throne", 15);
  }

  private void createHighTierDungeons() {
    createPassThrough(10, "Void Passages", 20, 150, 0.6f);

    createBossFight(11, "Demon Lord's Domain", 25);
    createBossFight(12, "The Final Abyss", 30);
  }

  private PassThrough createPassThrough(long id, String name, int requiredLevel,
      int length, float probabilityOfMonster) {
    PassThrough dungeon = new PassThrough();
    dungeon.setId(id);
    dungeon.setName(name);
    dungeon.setRequiredLevel(requiredLevel);
    dungeon.setLength(length);
    dungeon.setProbabilityOfMonsterOccurring(probabilityOfMonster);
    dungeon.setCurrentPosition(0);
    return dungeonRepository.save(dungeon);
  }

  private BossFight createBossFight(long id, String name, int requiredLevel) {
    BossFight dungeon = new BossFight();
    dungeon.setId(id);
    dungeon.setName(name);
    dungeon.setRequiredLevel(requiredLevel);
    return dungeonRepository.save(dungeon);
  }

}
