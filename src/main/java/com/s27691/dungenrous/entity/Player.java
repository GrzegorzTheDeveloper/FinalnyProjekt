package com.s27691.dungenrous.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Player {

  @Id
  @GeneratedValue
  private long id;

  private String nickname;
  @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Character> characters = new ArrayList<>();

  private long currentCharacterId;

  public boolean selectCharacter(long id){
    for(Character character : characters){
      if(character.getId() == id){
        currentCharacterId = id;
        return true;
      }
    }
    return false;
  }

//  public void createCharacter(Character character){
//    Character tmp = Character.createCharacter(character);
//  }

//  public List<Dungeons> exploreDungeons(int storeyId){
//
//  }

//  public boolean enterDungeon(long dungeonId, long characterId){
//
//  }

  public void modifyCharacter(long characterId){

  }

//  public boolean removeCharacter(){
//
//  }



}
