package com.willy.malltest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "Track")
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
    @Column(name = "TrackID")
    private Integer  trackID;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "UserID", referencedColumnName = "UserID")
//    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SpecID", referencedColumnName = "SpecID")
    private ProductSpec productSpec;

    //test
    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;
}
