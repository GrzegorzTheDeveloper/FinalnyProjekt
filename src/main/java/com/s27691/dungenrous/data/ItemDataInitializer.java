package com.s27691.dungenrous.data;

import com.s27691.dungenrous.entity.Item;
import com.s27691.dungenrous.enums.Category;
import com.s27691.dungenrous.enums.RequiredClass;
import com.s27691.dungenrous.repository.ItemRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemDataInitializer {

  @Autowired
  private ItemRepository itemRepository;

  @PostConstruct
  public void initializeItems(){

    itemRepository.deleteAll();
    createWeapons();
    createArmor();
    createHandsGear();
    createFeetGear();
    createShields();
    createAccessories();
    createBossLoot();

  }

  private void createWeapons() {
    // PALADIN weapons - Holy/Righteous theme
    createItem("Dawnbreaker Sword", Category.WEAPON, RequiredClass.PALADIN, 1, 25, false);
    createItem("Oathkeeper Blade", Category.WEAPON, RequiredClass.PALADIN, 5, 45, false);
    createItem("Lightbringer Greatsword", Category.WEAPON, RequiredClass.PALADIN, 10, 65, false);

    // MAGE weapons - Mystical/Arcane theme
    createItem("Emberwood Staff", Category.WEAPON, RequiredClass.MAGE, 1, 20, false);
    createItem("Stormcaller Rod", Category.WEAPON, RequiredClass.MAGE, 5, 40, false);
    createItem("Voidwhisper Scepter", Category.WEAPON, RequiredClass.MAGE, 10, 60, false);

    // ARCANE_CRUSADER weapons - Hybrid magical/physical
    createItem("Spellblade Katana", Category.WEAPON, RequiredClass.ARCANE_CRUSADER, 8, 55, false);
    createItem("Runescribed Falchion", Category.WEAPON, RequiredClass.ARCANE_CRUSADER, 12, 70, false);

    // ANY class weapons - Basic/Universal
    createItem("Rusty Dagger", Category.WEAPON, RequiredClass.ANY, 1, 15, false);
    createItem("Traveler's Blade", Category.WEAPON, RequiredClass.ANY, 3, 30, false);
  }

  private void createArmor() {
    // HEAD pieces
    createItem("Crusader's Helm", Category.HEAD, RequiredClass.PALADIN, 2, 20, false);
    createItem("Crown of Valor", Category.HEAD, RequiredClass.PALADIN, 8, 40, false);

    createItem("Sage's Circlet", Category.HEAD, RequiredClass.MAGE, 2, 15, false);
    createItem("Archmage's Diadem", Category.HEAD, RequiredClass.MAGE, 8, 35, false);

    createItem("Battlemage Hood", Category.HEAD, RequiredClass.ARCANE_CRUSADER, 10, 45, false);
    createItem("Wanderer's Cap", Category.HEAD, RequiredClass.ANY, 1, 10, false);

    // BODY pieces
    createItem("Sanctified Plate", Category.BODY, RequiredClass.PALADIN, 3, 35, false);
    createItem("Aegis of the Divine", Category.BODY, RequiredClass.PALADIN, 9, 55, false);

    createItem("Robes of Starlight", Category.BODY, RequiredClass.MAGE, 3, 25, false);
    createItem("Mantle of the Void", Category.BODY, RequiredClass.MAGE, 9, 45, false);

    createItem("Spellforged Chainmail", Category.BODY, RequiredClass.ARCANE_CRUSADER, 11, 60, false);
    createItem("Leather Jerkin", Category.BODY, RequiredClass.ANY, 1, 15, false);

    // LEGS pieces
    createItem("Greaves of Justice", Category.LEGS, RequiredClass.PALADIN, 4, 25, false);
    createItem("Mystweave Leggings", Category.LEGS, RequiredClass.MAGE, 4, 20, false);
    createItem("Warlock's Trousers", Category.LEGS, RequiredClass.ARCANE_CRUSADER, 12, 40, false);
    createItem("Simple Pants", Category.LEGS, RequiredClass.ANY, 1, 8, false);
  }

  private void createHandsGear() {
    // PALADIN hands
    createItem("Gauntlets of Righteousness", Category.HANDS, RequiredClass.PALADIN, 3, 18, false);
    createItem("Blessed Wargloves", Category.HANDS, RequiredClass.PALADIN, 7, 32, false);

    // MAGE hands
    createItem("Gloves of Channeling", Category.HANDS, RequiredClass.MAGE, 3, 15, false);
    createItem("Spellweaver's Grip", Category.HANDS, RequiredClass.MAGE, 7, 28, false);

    // ARCANE_CRUSADER hands
    createItem("Runic Handwraps", Category.HANDS, RequiredClass.ARCANE_CRUSADER, 9, 38, false);

    // ANY hands
    createItem("Worn Gloves", Category.HANDS, RequiredClass.ANY, 1, 5, false);
    createItem("Sturdy Mitts", Category.HANDS, RequiredClass.ANY, 4, 12, false);
  }

  private void createFeetGear() {
    // PALADIN feet
    createItem("Boots of the Faithful", Category.FEET, RequiredClass.PALADIN, 2, 16, false);
    createItem("Ironshod Warboots", Category.FEET, RequiredClass.PALADIN, 6, 30, false);

    // MAGE feet
    createItem("Slippers of Swiftness", Category.FEET, RequiredClass.MAGE, 2, 12, false);
    createItem("Ethereal Sandals", Category.FEET, RequiredClass.MAGE, 6, 26, false);

    // ARCANE_CRUSADER feet
    createItem("Battlecaster's Greaves", Category.FEET, RequiredClass.ARCANE_CRUSADER, 8, 35, false);

    // ANY feet
    createItem("Leather Boots", Category.FEET, RequiredClass.ANY, 1, 8, false);
    createItem("Traveler's Footwear", Category.FEET, RequiredClass.ANY, 3, 14, false);
  }

  private void createShields() {
    // PALADIN shields
    createItem("Shield of Dawn", Category.SHIELD, RequiredClass.PALADIN, 2, 22, false);
    createItem("Bulwark of the Divine", Category.SHIELD, RequiredClass.PALADIN, 6, 42, false);
    createItem("Aegis of Eternal Light", Category.SHIELD, RequiredClass.PALADIN, 10, 58, false);

    // ARCANE_CRUSADER shields
    createItem("Spellguard Buckler", Category.SHIELD, RequiredClass.ARCANE_CRUSADER, 7, 48, false);

    // ANY shields
    createItem("Wooden Shield", Category.SHIELD, RequiredClass.ANY, 1, 12, false);
    createItem("Iron Kite Shield", Category.SHIELD, RequiredClass.ANY, 4, 28, false);
  }

  private void createAccessories() {
    // PALADIN accessories
    createItem("Ring of Valor", Category.ACCESSORY, RequiredClass.PALADIN, 3, 14, false);
    createItem("Amulet of Sacred Might", Category.ACCESSORY, RequiredClass.PALADIN, 8, 36, false);

    // MAGE accessories
    createItem("Ring of Arcane Focus", Category.ACCESSORY, RequiredClass.MAGE, 3, 18, false);
    createItem("Pendant of Infinite Wisdom", Category.ACCESSORY, RequiredClass.MAGE, 8, 42, false);

    // ARCANE_CRUSADER accessories
    createItem("Sigil of Dual Mastery", Category.ACCESSORY, RequiredClass.ARCANE_CRUSADER, 10, 50, false);

    // ANY accessories
    createItem("Simple Ring", Category.ACCESSORY, RequiredClass.ANY, 1, 6, false);
    createItem("Lucky Charm", Category.ACCESSORY, RequiredClass.ANY, 5, 20, false);
    createItem("Adventurer's Medallion", Category.ACCESSORY, RequiredClass.ANY, 8, 32, false);
  }

  private void createBossLoot() {
    // Legendary boss weapons
    createItem("Excalibur Reborn", Category.WEAPON, RequiredClass.PALADIN, 15, 95, true);
    createItem("Staff of Cosmic Dominion", Category.WEAPON, RequiredClass.MAGE, 15, 88, true);
    createItem("Shadowmourne the Eternal", Category.WEAPON, RequiredClass.ARCANE_CRUSADER, 18, 110, true);

    // Legendary boss armor
    createItem("Crown of the Dungeon King", Category.HEAD, RequiredClass.ANY, 20, 75, true);
    createItem("Dragonscale Hauberk", Category.BODY, RequiredClass.ANY, 22, 95, true);
    createItem("Leggings of the Abyss", Category.LEGS, RequiredClass.ANY, 20, 65, true);

    // Legendary boss accessories
    createItem("Ring of Infinite Power", Category.ACCESSORY, RequiredClass.ANY, 25, 85, true);
    createItem("Amulet of the Void Lord", Category.ACCESSORY, RequiredClass.ANY, 28, 100, true);

    // Ultimate boss shield
    createItem("Aegis of Reality", Category.SHIELD, RequiredClass.PALADIN, 30, 120, true);
  }

  private Item createItem(String name, Category category, RequiredClass requiredClass,
      int requiredLevel, int power, boolean bossLoot) {
    Item item = new Item();
    item.setName(name);
    item.setCategory(category);
    item.setRequiredClass(requiredClass);
    item.setRequiredLevel(requiredLevel);
    item.setPower(power);
    item.setBossLoot(bossLoot);
    return itemRepository.save(item);
  }
}
