package com.willy.malltest.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "third_party")
public class ThirdParty {
    @Id
    @Column(name = "provider_id")
    private String providerId;
    @Column(name = "provider_name")
    private String providerName;

    @ManyToOne(fetch = FetchType.LAZY) // 指定多对一关系
    @JoinColumn(name = "user_id") // 指定关联的外键列
    private User user; // 指向 User 类的引用


}
