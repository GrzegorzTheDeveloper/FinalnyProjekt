package com.s27691.dungenrous.entity;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class Elf extends Fraction{
  private static final float PROBABILITY_OF_INTIMIDATING_OPPONENT  = 0.15f;

  public boolean intimidateOpponent(){
    return Math.random() <= PROBABILITY_OF_INTIMIDATING_OPPONENT;
  }

}
