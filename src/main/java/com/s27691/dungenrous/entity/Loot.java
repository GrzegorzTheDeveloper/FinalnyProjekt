package com.s27691.dungenrous.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Loot {

  @EmbeddedId
  private LootId lootId;

  @ManyToOne
  @MapsId("dungeonId")
  private Dungeon dungeon;

  @ManyToOne
  @MapsId("mobId")
  private Mob mob;

  private int experience;

  @ManyToOne
  private Item item;
}
