package com.shop.entity;

import com.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter
@Setter
public class Order extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    private LocalDateTime orderDate; // 주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문상태

    // 주문 상품 엔티티와 일대다 매핑을 합니다. 외래키(order_id)가 order_item 테이블에 있으므로 연관 관계의 주인은 OrderItem 엔티티입니다.
    // Order 엔티티가 주인이 아니므로 "mappedBy" 속성으로 연관 관계의 주인을 설정합니다.
    // 속성의 값으로 "order"를 적어준 이유는 OrderItem에 있는 Order에 의해 관리된다는 의미로 해석하시면 됩니다. 즉, 연관 관계의 주인의 필드인 order를 mappedBy의 값으로 세팅하면 됩니다.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            orphanRemoval = true) // 고아객체제거기능. 부모 엔티티와 연관 관계가 끊어진 자식 엔티티를 고아 객체라고 합니다.
                                  // 영속성 전이 기능과 같이 사용하면 부모 엔티티를 통해서 자식의 생명 주기를 관리 할 수 있습니다. 고아객체제거기능은 참조하는 곳이 하나일 때만 사용해야 합니다.
    private List<OrderItem> orderItems = new ArrayList<>(); // 하나의 주문이 여러 개의 주문 상품을 갖으므로 List 자료형을 사용해서 매핑을 합니다.

}
