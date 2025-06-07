package com.s27691.dungenrous.data;

import com.s27691.dungenrous.entity.ArcaneCrusader;
import com.s27691.dungenrous.entity.Character;
import com.s27691.dungenrous.entity.Elf;
import com.s27691.dungenrous.entity.Fraction;
import com.s27691.dungenrous.entity.Human;
import com.s27691.dungenrous.entity.Item;
import com.s27691.dungenrous.entity.Mage;
import com.s27691.dungenrous.entity.Paladin;
import com.s27691.dungenrous.entity.Player;
import com.s27691.dungenrous.enums.RequiredClass;
import com.s27691.dungenrous.repository.CharacterRepository;
import com.s27691.dungenrous.repository.FractionRepository;
import com.s27691.dungenrous.repository.ItemRepository;
import com.s27691.dungenrous.repository.PlayerRepository;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CharacterDataInitializer {

  @Autowired
  private CharacterRepository characterRepository;
  @Autowired
  private PlayerRepository playerRepository;
  @Autowired
  private ItemRepository itemRepository;
  @Autowired
  private FractionRepository fractionRepository;

  private Human humanFraction;
  private Elf elfFraction;

  @PostConstruct
  public void initializeCharacters() {
    characterRepository.deleteAll();
    createFractions();
    createSamplePlayers();
    createPaladinHeroes();
    createMageHeroes();
    createArcaneCrusaderHeroes();
    equipStartingGear();
  }

  private void createFractions() {
    List<Fraction> existingFractions = fractionRepository.findAll();

    humanFraction = existingFractions.stream()
        .filter(Human.class::isInstance)
        .map(f -> (Human) f)
        .findFirst()
        .orElse(null);

    elfFraction = existingFractions.stream()
        .filter(Elf.class::isInstance)
        .map(f -> (Elf) f)
        .findFirst()
        .orElse(null);

    if (humanFraction == null) {
      humanFraction = new Human();
      humanFraction = fractionRepository.save(humanFraction);
    }

    if (elfFraction == null) {
      elfFraction = new Elf();
      elfFraction = fractionRepository.save(elfFraction);
    }
  }

  private void createSamplePlayers() {
    createPlayer("DragonSlayer");
    createPlayer("MagicMaster");
    createPlayer("NoobAdventurer");
  }

  private void createPaladinHeroes() {
    Player player1 = playerRepository.findByNickname("DragonSlayer");

    Paladin paladin1 = new Paladin();
    setupCharacter(paladin1, 15, 850, humanFraction, player1);
    paladin1.setStamina(120);
    characterRepository.save(paladin1);
  }

  private void createMageHeroes() {
    Player player2 = playerRepository.findByNickname("MagicMaster");

    Mage mage1 = new Mage();
    setupCharacter(mage1, 12, 600, humanFraction, player2);
    mage1.setMana(150);
    characterRepository.save(mage1);

    Mage mage2 = new Mage();
    setupCharacter(mage2, 3, 85, elfFraction, player2);
    mage2.setMana(60);
    characterRepository.save(mage2);
  }

  private void createArcaneCrusaderHeroes() {
    Player player3 = playerRepository.findByNickname("NoobAdventurer");

    ArcaneCrusader crusader = new ArcaneCrusader();
    setupCharacter(crusader, 20, 1500, humanFraction, player3);
    crusader.setStamina(100);
    crusader.setMana(120);
    characterRepository.save(crusader);
  }

  private void setupCharacter(Character character, int level, int experience,
      Fraction fraction, Player player) {
    character.setLevel(level);
    character.setExperience(experience);
    character.setStrength(20 + level * 2);
    character.setIntellect(15 + level * 2);
    character.setDefense(18 + level * 2);
    character.setMaxHealthPoints(100 + level * 10);
    character.setCurrentHealthPoints(character.getMaxHealthPoints());
    character.setFreeDevelopmentPoints(level);
    character.setNumberOfDungeonsPassed(Math.max(0, level - 5));
    character.setPotionQuantity(3);
    character.setFraction(fraction);
    character.setPlayer(player);
  }

  private void equipStartingGear() {
    List<Character> characters = characterRepository.findAll();
    List<Item> items = itemRepository.findAll();

    for (Character character : characters) {
      equipCharacterWithAppropriateGear(character, items);
    }
  }

  private void equipCharacterWithAppropriateGear(Character character, List<Item> items) {
    RequiredClass characterClass = getCharacterClass(character);

    List<Item> suitableItems = items.stream()
        .filter(item -> canCharacterUseItem(item, characterClass, character.getLevel()))
        .sorted((item1, item2) -> Integer.compare(item2.getPower(), item1.getPower()))
        .toList();

    suitableItems.stream()
        .limit(8)
        .forEach(character::collectItem);

    equipBestItemsPerCategory(character, suitableItems);

    characterRepository.save(character);
  }

  private RequiredClass getCharacterClass(Character character) {
    if (character instanceof Paladin) return RequiredClass.PALADIN;
    if (character instanceof Mage) return RequiredClass.MAGE;
    if (character instanceof ArcaneCrusader) return RequiredClass.ARCANE_CRUSADER;
    return RequiredClass.ANY;
  }

  private boolean canCharacterUseItem(Item item, RequiredClass characterClass, int characterLevel) {
    if (item.getRequiredLevel() > characterLevel) return false;

    return item.getRequiredClass() == RequiredClass.ANY ||
        item.getRequiredClass() == characterClass;
  }

  private void equipBestItemsPerCategory(Character character, List<Item> suitableItems) {
    suitableItems.stream()
        .collect(Collectors.groupingBy(Item::getCategory))
        .forEach((category, categoryItems) -> {
          Item bestItem = categoryItems.stream()
              .max(java.util.Comparator.comparing(Item::getPower))
              .orElse(null);

          if (bestItem != null && character.getItemsOwned().contains(bestItem)) {
            character.equipItem(bestItem);
          }
        });
  }

  private Player createPlayer(String nickname) {
    Player player = new Player();
    player.setNickname(nickname);
    return playerRepository.save(player);
  }
}