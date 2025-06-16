package com.s27691.dungenrous.interfaces;

import com.s27691.dungenrous.entity.Mob;

public interface IMage {

  public  Mob castSpell(Mob mob);
  public  boolean castBarrier();

}
