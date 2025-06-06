package com.s27691.dungenrous.repository;

import com.s27691.dungenrous.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

  Player findByNickname(String nickname);
}
