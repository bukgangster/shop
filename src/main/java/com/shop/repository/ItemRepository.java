package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>{

    // find + (엔티티 이름) + By + 변수이름
    List<Item> findByItemNm(String itemNm);
}
