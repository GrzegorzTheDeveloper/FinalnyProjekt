package com.s27691.dungenrous.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class Dungeon {

  @Id
  private int id;

  @ManyToOne
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private Character visitor;

  private String name;
  private int requiredLevel;

  @OneToMany(mappedBy = "dungeon", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<Loot> opponents = new ArrayList<>();
}