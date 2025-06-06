package com.s27691.dungenrous.repository;

import com.s27691.dungenrous.entity.Item;
import com.s27691.dungenrous.enums.RequiredClass;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

  List<Item> findByRequiredClassAndRequiredLevelLessThanEqual(RequiredClass valueOf, int level);
}
