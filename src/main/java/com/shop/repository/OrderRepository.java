package com.shop.repository;

import com.shop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

// 영속성 전이? 엔티티의 상태를 변경할 때 해당 엔티티와 연관된 엔티티의 상태 변화를 전파시키는 옵션입니다.
// PERSIST : 부모 엔티티가 영속화될 때 자식 엔티티도 영속화
// MERGE : 부모 엔티티가 병합될 때 자식 엔티티도 병합
// REMOVE : 부모 엔티티가 삭제될 때 연관된 자식 엔티티도 삭제
// REFRESH : 부모 엔티티가 refresh되면 연관된 자식 엔티티도 refresh
// DETACH : 부모 엔티티가 detach 되면 연관된 자식 엔티티도 detach 상태로 변경
// ALL : 부모 엔티티의 영속성 상태 변화를 자식 엔티티에 모두 전이
public interface OrderRepository extends JpaRepository<Order, Long> {
}
