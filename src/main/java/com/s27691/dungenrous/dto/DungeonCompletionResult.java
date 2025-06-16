package com.s27691.dungenrous.dto;

import com.s27691.dungenrous.entity.Item;
import com.s27691.dungenrous.entity.Character;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DungeonCompletionResult {

  private Character character;
  private List<Item> lootItems;

  public DungeonCompletionResult(Character character, List<Item> lootItems) {
    this.character = character;
    this.lootItems = lootItems;
  }


}
