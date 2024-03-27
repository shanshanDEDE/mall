package com.willy.malltest.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "third_party")
public class ThirdParty {
    @Id
    @Column
    private String ProviderID;
    @Column
    private String ProviderName;
    @Column
    private Long UserID;


    @ManyToOne(fetch = FetchType.LAZY) // 指定多对一关系
    @JoinColumn(name = "user_id") // 指定关联的外键列
    private User user; // 指向 User 类的引用




}
