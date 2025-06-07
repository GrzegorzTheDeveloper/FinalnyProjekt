package com.s27691.dungenrous.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public abstract class Character {

  @Id
  @GeneratedValue
  private long id;

  private int level;
  private int experience;
  private int strength;
  private int intellect;
  private int defense;
  private int currentHealthPoints;
  private int maxHealthPoints;
  private int freeDevelopmentPoints;

  private int numberOfDungeonsPassed;
  private int potionQuantity;
  @ManyToOne
  private Player player;

  @ManyToMany
  private List<Item> itemsOwned = new ArrayList<>();

  @ManyToMany
  private List<Item> currentEquipment = new ArrayList<>();

  @ManyToOne
  Fraction fraction;

  //items

  public boolean collectItem(Item item){
    if(itemsOwned.size()<30){
      itemsOwned.add(item);
      itemsOwned.sort(Comparator.comparing(Item::getPower));
      return true;
    }
    return false;
  }

  public void removeItem(Item item){
    itemsOwned.remove(item);
    currentEquipment.remove(item);
  }

  public boolean equipItem(Item item){
    if(!itemsOwned.contains(item) || item.getRequiredLevel()>level)
      return false;
    currentEquipment.removeIf(equipped -> equipped.getCategory() == item.getCategory());

    currentEquipment.add(item);
    return true;
  }

  public void removeItemFromEquipment(Item item){
    currentEquipment.remove(item);
  }


  //level up / attributes

  public void initializeStartingStats() {
    this.level = 1;
    this.experience = 0;
    this.strength = 10;
    this.intellect = 10;
    this.defense = 10;
    this.currentHealthPoints = 100;
    this.maxHealthPoints = 100;
    this.freeDevelopmentPoints = 5;
    this.numberOfDungeonsPassed = 0;
    this.potionQuantity = 3;
  }

//  private boolean assignDevelopmentPoint(){
//
//  }

//  private boolean evolveToArcaneCrusader(){
//
//  }

  //functionality

//  public int getPower(){
//
//  }


  public boolean takeDamage(int damage){
    currentHealthPoints = Math.max(0, currentHealthPoints-damage);
    return currentHealthPoints>0;
  }

  public boolean usePotion() {
    if (potionQuantity > 0 && currentHealthPoints < maxHealthPoints) {
      potionQuantity--;
      int healAmount = (int)(maxHealthPoints * 0.15);
      currentHealthPoints = Math.min(maxHealthPoints, currentHealthPoints + healAmount);
      return true;
    }
    return false;
  }

  public void enterDungeon(int dungeonId) {
    if (dungeonId > numberOfDungeonsPassed + 1) {
      throw new IllegalArgumentException("Must complete dungeons in order");
    }
  }
  public void completeDungeon(int dungeonId) {
    if (dungeonId == numberOfDungeonsPassed + 1) {
      numberOfDungeonsPassed = dungeonId;
      potionQuantity += 3;
    }
  }

//  public static Character createCharacter(Character character){
//    return character;
//  }

}
