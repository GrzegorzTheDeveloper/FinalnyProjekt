package com.s27691.dungenrous.entity;

import com.s27691.dungenrous.interfaces.IMage;
import com.s27691.dungenrous.interfaces.IPaladin;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ArcaneCrusader extends Character implements IMage, IPaladin {

  private int stamina;
  private int mana;

  // @Override
//  public int getPower(){
//
//  }
//  public void powerStrike(Mob mob){
//
//  }

//  @Override
//  public void castSpell(Mob mob){
//
//  }

  @Override
  public void castBarrier() {

  }

  @Override
  public void shieldStance() {

  }
}
