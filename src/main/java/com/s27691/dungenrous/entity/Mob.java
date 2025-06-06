package com.s27691.dungenrous.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Mob {
  @Id
  @GeneratedValue
  private long id;
  private String name;
  private int power;
  private int energy;
  private int healthPoints;
}
