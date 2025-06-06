package com.s27691.dungenrous.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class PassThrough extends Dungeon{

  private int length;
  private int currentPosition;
  private float probabilityOfMonsterOccurring;
  @Transient
  private List<Loot> collectedLoot = new ArrayList<>();

  public void moveForward(){

  }

//  private boolean checkForMonster(){
//
//  }

  public void retreat(){

  }

}
