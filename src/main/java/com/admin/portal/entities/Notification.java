package com.admin.portal.entities;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "messages", nullable = false)
    private String message;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false, referencedColumnName = "user_id")
    private User user;

    @Column(name = "status", nullable = false)
    private String status;
}
