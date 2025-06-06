package com.s27691.dungenrous.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("Human")
@NoArgsConstructor
public class Human extends Fraction{
  private static final float PROBABILITY_OF_SUSTAINING_FATAL_DAMAGE = 0.15f;

  public boolean sustainFatalDamage(){
    return Math.random() <= PROBABILITY_OF_SUSTAINING_FATAL_DAMAGE;
  }
}
