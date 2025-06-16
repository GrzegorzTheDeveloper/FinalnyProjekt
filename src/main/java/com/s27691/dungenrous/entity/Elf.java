package com.s27691.dungenrous.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("Elf")
@NoArgsConstructor
public class Elf extends Fraction{
  private static final float PROBABILITY_OF_INTIMIDATING_OPPONENT  = 0.15f;

  public boolean intimidateOpponent(){
    return Math.random() <= PROBABILITY_OF_INTIMIDATING_OPPONENT;
  }
}