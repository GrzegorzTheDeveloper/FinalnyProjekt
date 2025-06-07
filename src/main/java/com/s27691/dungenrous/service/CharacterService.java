package com.s27691.dungenrous.service;

import com.s27691.dungenrous.entity.ArcaneCrusader;
import com.s27691.dungenrous.entity.Character;
import com.s27691.dungenrous.entity.Fraction;
import com.s27691.dungenrous.entity.Human;
import com.s27691.dungenrous.entity.Item;
import com.s27691.dungenrous.entity.Mage;
import com.s27691.dungenrous.entity.Paladin;
import com.s27691.dungenrous.entity.Player;
import com.s27691.dungenrous.repository.CharacterRepository;
import com.s27691.dungenrous.repository.FractionRepository;
import com.s27691.dungenrous.repository.ItemRepository;
import com.s27691.dungenrous.repository.PlayerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CharacterService {

  @Autowired
  private CharacterRepository characterRepository;

  @Autowired
  private PlayerRepository playerRepository;

  @Autowired
  private FractionRepository fractionRepository;

  @Autowired
  private ItemRepository itemRepository;

  public Character createCharacter(String playerNickname, String fractionType, String characterClass) {
    Player player = new Player();
    player.setNickname(playerNickname);
    player = playerRepository.save(player);

    List<Fraction> fractions = fractionRepository.findAll();

    Fraction fraction = fractions.stream()
        .filter(f -> f.getClass().getSimpleName().toUpperCase().equals(fractionType))
        .findFirst().orElse(null);

    Character character;
    if ("PALADIN".equalsIgnoreCase(characterClass))
      character = new Paladin();
    else if ("MAGE".equalsIgnoreCase(characterClass))
      character = new Mage();
    else
      throw new IllegalArgumentException("Invalid character class");

    character.setFraction(fraction);
    character.setPlayer(player);
    character.initializeStartingStats();
    giveStartingEquipment(character);

    return characterRepository.save(character);
  }

  private Character giveStartingEquipment(Character character){

    List<Item> startingItems = itemRepository.findAll().stream()
        .filter(item -> item.getRequiredLevel() == 1)
        .filter(item -> item.getRequiredClass().toString().equals("ANY") ||
            item.getRequiredClass().toString().equals(character.getClass().getSimpleName().toUpperCase())).toList();
    for(Item item : startingItems){
      character.collectItem(item);
      character.equipItem(item);
    }

    return characterRepository.save(character);


  }

  public Character gainExperience(Long playerId, int experienceGained){
    Character character = getCharacterByPlayerId(playerId);
    if(character==null) return null;

    while(character.getExperience() >= getExperienceToNextLevel(character.getLevel())){
      character.setExperience(character.getExperience() - getExperienceToNextLevel(character.getLevel()));
      character.setLevel(character.getLevel()+1);
      character.setFreeDevelopmentPoints(character.getFreeDevelopmentPoints()+3);
    }

//    if(character.getLevel() >= 10 && !(character instanceof ArcaneCrusader))
//      to be implemented
    return characterRepository.save(character);
  }

  public Character increaseAttribute(Long playerId, String attribute) {
    Character character = getCharacterByPlayerId(playerId);
    if (character == null || character.getFreeDevelopmentPoints() <= 0)
      return null;

    switch (attribute.toLowerCase()) {
      case "strength" -> character.setStrength(character.getStrength() + 1);
      case "intellect" -> character.setIntellect(character.getIntellect() + 1);
      case "defense" -> character.setDefense(character.getDefense() + 1);
      default -> {
        return null;
      }
    }

    character.setFreeDevelopmentPoints(character.getFreeDevelopmentPoints() -1);
    return characterRepository.save(character);
  }

  private Character evolveToArcaneCrusader(Long playerId){
    Character character = getCharacterByPlayerId(playerId);
    if(character == null || character.getLevel() < 10 || character instanceof ArcaneCrusader || character.getFreeDevelopmentPoints()< 10)
      return null;

    ArcaneCrusader arcaneCrusader = new ArcaneCrusader();
    copyCharacterStats(character, arcaneCrusader);
    characterRepository.delete(character);
    arcaneCrusader.setFreeDevelopmentPoints(character.getFreeDevelopmentPoints() - 10);
    return characterRepository.save(arcaneCrusader);
  }

  private void copyCharacterStats(Character from, Character to) {
    to.setPlayer(from.getPlayer());
    to.setFraction(from.getFraction());
    to.setLevel(from.getLevel());
    to.setExperience(from.getExperience());
    to.setStrength(from.getStrength());
    to.setIntellect(from.getIntellect());
    to.setDefense(from.getDefense());
    to.setCurrentHealthPoints(from.getCurrentHealthPoints());
    to.setMaxHealthPoints(from.getMaxHealthPoints());
    to.setFreeDevelopmentPoints(from.getFreeDevelopmentPoints());
    to.setNumberOfDungeonsPassed(from.getNumberOfDungeonsPassed());
    to.setPotionQuantity(from.getPotionQuantity());
    to.setItemsOwned(from.getItemsOwned());
    to.setCurrentEquipment(from.getCurrentEquipment());
  }


  public Character equipItem(Long playerId, long itemId){
    Character character = getCharacterByPlayerId(playerId);
    Item item = itemRepository.findById(itemId).orElse(null);

    if(character == null || item == null) return null;

    boolean equipped = character.equipItem(item);

    if(equipped)
      return characterRepository.save(character);

    return character;
  }

  public Character unequipItem(Long playerId, Long itemId){
    Character character = getCharacterByPlayerId(playerId);
    Item item = itemRepository.findById(itemId).orElse(null);

    if(character == null || item == null) return null;

    character.removeItemFromEquipment(item);
    return characterRepository.save(character);
  }

  public Character addItemToInventory(Long playerId, Long itemId){
    Character character = getCharacterByPlayerId(playerId);
    Item item = itemRepository.findById(itemId).orElse(null);
    if (character == null || item == null) return null;

    boolean collected = character.collectItem(item);

    if(collected){
      return characterRepository.save(character);
    }
    return character;
  }

  public Character removeItemFromInventory(Long playerId, Long itemId){
    Character character = getCharacterByPlayerId(playerId);
    Item item = itemRepository.findById(itemId).orElse(null);
    if (character == null || item == null) return null;

    character.removeItem(item);
    return characterRepository.save(character);
  }



  public Character takeDamage(Long playerId, int damage){
    Character character = getCharacterByPlayerId(playerId);
    if(character == null) return null;

    boolean isAlive = character.takeDamage(damage);

    if( !isAlive && character.getFraction().sustainFatalDamage()) {
      character.setCurrentHealthPoints(1);
    }

    return characterRepository.save(character);

  }

  public Character usePotion(Long playerId){
    Character character = getCharacterByPlayerId(playerId);
    if (character == null) return null;

    boolean used = character.usePotion();
    if(used)
      return characterRepository.save(character);

    return character;
  }

  public Character restoreStaminaAndMana(Long playerId, int stamina, int mana) {
    Character character = getCharacterByPlayerId(playerId);
    if (character == null)
      return null;

    if (character instanceof Paladin paladin) {
      paladin.setStamina(Math.min(paladin.getStamina() + stamina, 100));
    } else if (character instanceof ArcaneCrusader crusader) {
      crusader.setStamina(Math.min(crusader.getStamina() + stamina, 100));
    }

    if (character instanceof Mage mage) {
      mage.setMana(Math.min(mage.getMana() + mana, 100));
    } else if (character instanceof ArcaneCrusader crusader) {
      crusader.setMana(Math.min(crusader.getMana() + mana, 100));
    }

    return characterRepository.save(character);
  }

  private int getExperienceToNextLevel(int level) {
    return 100 + (level * 50);
  }

  private Character getCharacterByPlayerId(Long playerId){
    Player player = playerRepository.findById(playerId).orElse(null);
    if(player == null || player.getCharacters().isEmpty())
      return null;
    return player.getCharacters().get(0);
  }


}
