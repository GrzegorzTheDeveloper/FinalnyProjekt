package com.s27691.dungenrous.repository;

import com.s27691.dungenrous.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.s27691.dungenrous.entity.Character;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {

}
