package com.willy.malltest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "track")
public class Track {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "TrackID")
//    private int  trackID;
//
//    @Column(name = "SpecID")
//    private String specID;


//    @ManyToOne
//    @JoinColumn(name = "UserID", referencedColumnName = "UserID")
//    private Users users;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "track_id")
    private Integer trackID;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "UserID", referencedColumnName = "UserID")
//    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spec_id", referencedColumnName = "spec_id")
    private ProductSpec productSpec;

    //test
    @ManyToOne
    @JoinColumn(name = "user_Id")
    private User user;
}
