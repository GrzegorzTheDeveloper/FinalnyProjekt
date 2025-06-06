package com.s27691.dungenrous.entity;

import com.s27691.dungenrous.interfaces.IMage;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Mage extends Character implements IMage {

  private int mana;

// @Override
//  public int getPower(){
//
//  }

//  @Override
//  public void castSpell(Mob mob){
//
//  }
  @Override
  public void castBarrier() {

  }
}
