package com.s27691.dungenrous.service;

import com.s27691.dungenrous.entity.Player;
import com.s27691.dungenrous.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

  @Autowired
  private PlayerRepository playerRepository;

  public Player getPlayer(Long id){
    return playerRepository.findById(id).orElse(null);
  }

}
