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
      // Boss dungeons: 1 boss mob only
      createBossLoot(dungeon, mobs, items);
    } else {
      // Regular dungeons: 5-10 mobs
      createRegularDungeonLoot(dungeon, mobs, items);
    }
  }

  private void createBossLoot(Dungeon dungeon, List<Mob> mobs, List<Item> items) {
    // Find a boss mob appropriate for this dungeon level
    Mob bossMob = getBossMobForLevel(mobs, dungeon.getRequiredLevel());

    // Create single loot entry for the boss
    Loot loot = new Loot();

    LootId lootId = new LootId();
    lootId.setDungeonId(dungeon.getId());
    lootId.setMobId(bossMob.getId());
    lootId.setInstance(1); // Only one boss
    loot.setLootId(lootId);

    loot.setDungeon(dungeon);
    loot.setMob(bossMob);
    loot.setExperience(bossMob.getPower() * 2); // Bosses give more XP

    // Bosses always drop good loot
    Item bossItem = getBossItemForLevel(items, dungeon.getRequiredLevel());
    loot.setItem(bossItem);

    lootRepository.save(loot);
  }

  private void createRegularDungeonLoot(Dungeon dungeon, List<Mob> mobs, List<Item> items) {
    // Regular dungeons: 5-10 mobs
    int mobCount = 5 + random.nextInt(6); // 5-10 mobs

    for (int instance = 1; instance <= mobCount; instance++) {
      // Pick mob appropriate for dungeon level (weaker mobs for lower levels)
      Mob mob = getRegularMobForLevel(mobs, dungeon.getRequiredLevel());

      Loot loot = new Loot();

      LootId lootId = new LootId();
      lootId.setDungeonId(dungeon.getId());
      lootId.setMobId(mob.getId());
      lootId.setInstance(instance); // Multiple instances of same mob type
      loot.setLootId(lootId);

      loot.setDungeon(dungeon);
      loot.setMob(mob);
      loot.setExperience(mob.getPower() + random.nextInt(10));

      // 60% chance to drop an item, BUT only NON-BOSS items
      if (random.nextFloat() < 0.6f) {
        Item item = getRegularItemForLevel(items, dungeon.getRequiredLevel());
        loot.setItem(item);
      }

      lootRepository.save(loot);
    }
  }

  private Mob getBossMobForLevel(List<Mob> mobs, int dungeonLevel) {
    // Get any boss mob - bosses are meant to be challenging regardless of level!
    List<Mob> bossMobs = mobs.stream()
        .filter(Mob::isBoss)
        .toList();

    // Safety check - if no boss mobs exist at all, use any mob
    if (bossMobs.isEmpty()) {
      if (mobs.isEmpty()) {
        throw new IllegalStateException("No mobs available for loot generation");
      }
      return mobs.get(0); // Fallback to first available mob
    }

    return bossMobs.get(random.nextInt(bossMobs.size()));
  }

  private Mob getRegularMobForLevel(List<Mob> mobs, int dungeonLevel) {
    // Get only regular (non-boss) mobs appropriate for this level
    List<Mob> regularMobs = mobs.stream()
        .filter(mob -> !mob.isBoss()) // ONLY regular mobs
        .filter(mob -> {
          if (dungeonLevel <= 5) return mob.getPower() <= 25;
          else if (dungeonLevel <= 10) return mob.getPower() > 15 && mob.getPower() <= 45;
          else if (dungeonLevel <= 20) return mob.getPower() > 35 && mob.getPower() <= 80;
          else return mob.getPower() > 70;
        })
        .toList();

    // Safety check - if no suitable regular mobs, get any non-boss mob
    if (regularMobs.isEmpty()) {
      List<Mob> allRegularMobs = mobs.stream()
          .filter(mob -> !mob.isBoss())
          .toList();

      if (allRegularMobs.isEmpty()) {
        if (mobs.isEmpty()) {
          throw new IllegalStateException("No mobs available for loot generation");
        }
        return mobs.get(0); // Last resort fallback
      }
      return allRegularMobs.get(0);
    }

    return regularMobs.get(random.nextInt(regularMobs.size()));
  }

  private Item getBossItemForLevel(List<Item> items, int dungeonLevel) {
    List<Item> bossItems = items.stream()
        .filter(item -> item.getRequiredLevel() <= dungeonLevel + 3)
        .toList();

    // Prefer boss loot but allow regular items too
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
        .filter(item -> !item.isBossLoot()) // ONLY non-boss items
        .filter(item -> item.getRequiredLevel() <= dungeonLevel + 2 &&
            item.getRequiredLevel() >= Math.max(1, dungeonLevel - 3))
        .toList();

    return regularItems.isEmpty() ?
        items.stream().filter(item -> !item.isBossLoot()).findFirst().orElse(items.get(0)) :
        regularItems.get(random.nextInt(regularItems.size()));
  }
}