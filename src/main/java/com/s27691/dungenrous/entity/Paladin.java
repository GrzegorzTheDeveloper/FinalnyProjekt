package com.s27691.dungenrous.entity;

import com.s27691.dungenrous.interfaces.IPaladin;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Paladin extends Character implements IPaladin {

  private int stamina;

// @Override
//  public int getPower(){
//
//  }
//  public void powerStrike(Mob mob){
//
//  }
  public void shieldStance(){

  }
}
