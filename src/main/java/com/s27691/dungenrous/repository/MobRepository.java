package com.s27691.dungenrous.repository;

import com.s27691.dungenrous.entity.Mob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MobRepository extends JpaRepository<Mob, Long> {

}
