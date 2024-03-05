package com.admin.portal.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@Entity
public class Reference {

    @Id
    @Column(name = "REFNO", nullable = false)
    private Long referenceNumber;

    @Column(name = "REFNAME")
    private String name;

    @Column(name = "DATEUPLOADED")
    private Date dateUploaded;
}
