package com.s27691.dungenrous.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public abstract class Dungeon {

  @Id
  //I want it to be from 1 to 30
  private long id;

  @ManyToOne
  private Character visitor;
  private String name;
  private int requiredLevel;

  @OneToMany(mappedBy = "dungeon", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Loot> opponents = new ArrayList<>();

}
