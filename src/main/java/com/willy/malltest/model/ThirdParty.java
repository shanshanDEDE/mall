package com.willy.malltest.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "thirdparty")
public class ThirdParty {
    @Id
    @Column(name = "ProviderID")
    private String ProviderID;
    @Column(name = "ProviderName")
    private String ProviderName;
    @Column(name = "UserId",insertable=false, updatable=false)
    private Long UserId;


    @ManyToOne(fetch = FetchType.LAZY) // 指定多对一关系
    @JoinColumn(name = "UserID") // 指定关联的外键列
    private User user; // 指向 User 类的引用




}
