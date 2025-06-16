package com.s27691.dungenrous.data;

import com.s27691.dungenrous.entity.*;
import com.s27691.dungenrous.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Random;

@Component
@Transactional
@DependsOn({"itemDataInitializer", "mobDataInitializer", "dungeonDataInitializer"})
public class LootDataInitializer {

  @Autowired
  private LootRepository lootRepository;
  @Autowired
  private DungeonRepository dungeonRepository;
  @Autowired
  private MobRepository mobRepository;
  @Autowired
  private ItemRepository itemRepository;

  private Random random = new Random();

  @PostConstruct
  public void initializeLoot() {
    if (lootRepository.count() > 0) {
      return;
    }

    createDungeonLoot();
  }

  private void createDungeonLoot() {
    List<Dungeon> dungeons = dungeonRepository.findAll();
    List<Mob> mobs = mobRepository.findAll();
    List<Item> items = itemRepository.findAll();

    for (Dungeon dungeon : dungeons) {
      createLootForDungeon(dungeon, mobs, items);
    }
  }

  private void createLootForDungeon(Dungeon dungeon, List<Mob> mobs, List<Item> items) {
    if (dungeon instanceof BossFight) {
      createBossLoot(dungeon, mobs, items);
    } else {
      createRegularDungeonLoot(dungeon, mobs, items);
    }
  }

  private void createBossLoot(Dungeon dungeon, List<Mob> mobs, List<Item> items) {
    Mob bossMob = getBossMobForLevel(mobs, dungeon.getRequiredLevel());

    Loot loot = new Loot();

    LootId lootId = new LootId();
    lootId.setDungeonId(dungeon.getId());
    lootId.setMobId(bossMob.getId());
    lootId.setInstance(1);
    loot.setLootId(lootId);

    loot.setDungeon(dungeon);
    loot.setMob(bossMob);
    loot.setExperience(bossMob.getPower() * 2);

    Item bossItem = getBossItemForLevel(items, dungeon.getRequiredLevel());
    loot.setItem(bossItem);

    lootRepository.save(loot);
  }

  private void createRegularDungeonLoot(Dungeon dungeon, List<Mob> mobs, List<Item> items) {
    int mobCount = 5 + random.nextInt(6); // 5-10 mobs

    for (int instance = 1; instance <= mobCount; instance++) {
      Mob mob = getRegularMobForLevel(mobs, dungeon.getRequiredLevel());

      Loot loot = new Loot();

      LootId lootId = new LootId();
      lootId.setDungeonId(dungeon.getId());
      lootId.setMobId(mob.getId());
      lootId.setInstance(instance);
      loot.setLootId(lootId);

      loot.setDungeon(dungeon);
      loot.setMob(mob);
      loot.setExperience(mob.getPower() + random.nextInt(10));

      if (random.nextFloat() < 0.6f) {
        Item item = getRegularItemForLevel(items, dungeon.getRequiredLevel());
        loot.setItem(item);
      }

      lootRepository.save(loot);
    }
  }

  private Mob getBossMobForLevel(List<Mob> mobs, int dungeonLevel) {
    List<Mob> bossMobs = mobs.stream()
        .filter(Mob::isBoss)
        .toList();

    if (bossMobs.isEmpty()) {
      if (mobs.isEmpty()) {
        throw new IllegalStateException("No mobs available for loot generation");
      }
      return mobs.get(0);
    }

    return bossMobs.get(random.nextInt(bossMobs.size()));
  }

  private Mob getRegularMobForLevel(List<Mob> mobs, int dungeonLevel) {
    List<Mob> regularMobs = mobs.stream()
        .filter(mob -> !mob.isBoss())
        .filter(mob -> {
          if (dungeonLevel <= 5) return mob.getPower() <= 25;
          else if (dungeonLevel <= 10) return mob.getPower() > 15 && mob.getPower() <= 45;
          else if (dungeonLevel <= 20) return mob.getPower() > 35 && mob.getPower() <= 80;
          else return mob.getPower() > 70;
        })
        .toList();

    if (regularMobs.isEmpty()) {
      List<Mob> allRegularMobs = mobs.stream()
          .filter(mob -> !mob.isBoss())
          .toList();

      if (allRegularMobs.isEmpty()) {
        if (mobs.isEmpty()) {
          throw new IllegalStateException("No mobs available for loot generation");
        }
        return mobs.get(0);
      }
      return allRegularMobs.get(0);
    }

    return regularMobs.get(random.nextInt(regularMobs.size()));
  }

  private Item getBossItemForLevel(List<Item> items, int dungeonLevel) {
    List<Item> bossItems = items.stream()
        .filter(item -> item.getRequiredLevel() <= dungeonLevel + 3)
        .toList();

    List<Item> preferredItems = bossItems.stream()
        .filter(Item::isBossLoot)
        .toList();

    if (!preferredItems.isEmpty() && random.nextFloat() < 0.7f) {
      return preferredItems.get(random.nextInt(preferredItems.size()));
    }

    return bossItems.isEmpty() ? items.get(0) :
        bossItems.get(random.nextInt(bossItems.size()));
  }

  private Item getRegularItemForLevel(List<Item> items, int dungeonLevel) {
    List<Item> regularItems = items.stream()
        .filter(item -> !item.isBossLoot())
        .filter(item -> item.getRequiredLevel() <= dungeonLevel + 2 &&
            item.getRequiredLevel() >= Math.max(1, dungeonLevel - 3))
        .toList();

    return regularItems.isEmpty() ?
        items.stream().filter(item -> !item.isBossLoot()).findFirst().orElse(items.get(0)) :
        regularItems.get(random.nextInt(regularItems.size()));
  }
}