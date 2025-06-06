package com.s27691.dungenrous.entity;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class Human extends Fraction{
  private static final float PROBABILITY_OF_SUSTAINING_FATAL_DAMAGE = 0.15f;

  public boolean sustainFatalDamage(){
    return Math.random() <= PROBABILITY_OF_SUSTAINING_FATAL_DAMAGE;
  }
}
