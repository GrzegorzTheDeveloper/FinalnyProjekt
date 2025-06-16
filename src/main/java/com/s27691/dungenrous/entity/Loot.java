package com.s27691.dungenrous.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Loot {

  @EmbeddedId
  private LootId lootId;

  @ManyToOne
  @MapsId("dungeonId")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "opponents", "visitor"})
  private Dungeon dungeon;

  @ManyToOne
  @MapsId("mobId")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private Mob mob;

  private int experience;

  @ManyToOne
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private Item item;
}