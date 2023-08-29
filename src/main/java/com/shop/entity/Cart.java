package com.shop.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="cart")
@Getter
@Setter
@ToString
public class Cart {

    @Id
    @Column(name="cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // @OneToOne(fetch = FetchType.EAGER) // OneToOne 어노테이션을 이용해 회원 엔티티와 일대일 매핑을 합니다.
    // 엔티티를 조회할 때 해당 엔티티와 매핑된 엔티티도 한 번에 조회하는 것을 '즉시 로딩'이라고 합니다. 따로 옵션을 주지 않으면 FetchType.EAGER(즉시로딩)로 설정하는 것과 동일합니다.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memeber_id") // @JoinColumn 어노테이션을 이용해 매핑할 외래키를 지정합니다. name 속성에는 매핑할 외래키의 이름을 설정합니다.
    // @JoinColumn의 name을 명시하지 않으면 JPA가 알아서 ID를 찾지만 컬럼명이 원하는 대로 생성되지 않을 수 있기 때문에 직접 지정하겠습니다.
    private Member member;
}
