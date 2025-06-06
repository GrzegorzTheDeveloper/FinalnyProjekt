package com.s27691.dungenrous.entity;

import com.s27691.dungenrous.enums.Category;
import com.s27691.dungenrous.enums.RequiredClass;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Item {

  @Id
  @GeneratedValue
  private long id;
  private String name;
  @Enumerated(EnumType.STRING)
  private Category category;

  @Enumerated(EnumType.STRING)
  private RequiredClass requiredClass;
  private int requiredLevel;
  private int power;
  private boolean bossLoot;
}
