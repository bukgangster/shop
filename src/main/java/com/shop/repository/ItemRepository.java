package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

// Predicate? '이 조건이 맞다'고 판단하는 근거를 함수로 제공하는 것.  =>  QuerydslPredicateExecutor 인터페이스 상속

public interface ItemRepository extends JpaRepository<Item, Long>
        , QuerydslPredicateExecutor<Item>{



    // find + (엔티티 이름) + By + 변수이름
    List<Item> findByItemNm(String itemNm);
}
