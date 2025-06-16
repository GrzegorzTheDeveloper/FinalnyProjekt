package com.s27691.dungenrous.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Player {

  @Id
  @GeneratedValue
  private long id;

  private String nickname;

  @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
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

  public void modifyCharacter(long characterId){

  }
}