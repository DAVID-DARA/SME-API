package com.admin.portal.entities;

import com.admin.portal.utilities.StatusRequests;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEMNO", nullable = false)
    private Long itemNumber;

    @OneToOne
    @JoinColumn (name = "REFNO", nullable = false, referencedColumnName = "REFNO")
    private Reference referenceNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private StatusRequests status;

    @Column(name = "ITEMQ")
    private Integer itemQ;

    @Column(name = "ITEMCOMMENT")
    private String itemComment;

    @Column(name = "ACCOUNTNAME")
    private String accountName;

    @Column(name = "ITEMDATE")
    private Date itemDate;

    @ManyToOne
    @JoinColumn(name = "REVIEWERID", referencedColumnName = "user_id")
    private User reviewer;

    @ManyToOne
    @JoinColumn (name = "APPROVER", referencedColumnName = "user_id")
    private User approver;

    @Column(name = "DATEASSIGNED")
    private Date dateAssigned;

    @Column(name = "DATEREVIEWED")
    private Date dateReviewed;

    @Column(name = "PROFILEID")
    private String profileID;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "LASTNAME")
    private String lastName;

    @Column(name = "PHONENUMBER")
    private Integer phoneNumber;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "STATE")
    private String state;

    @Column(name = "CITY")
    private String city;

    @Column(name = "NATIONALITY")
    private String nationality;

    @Column(name = "CUSTOMERTYPE")
    private String customerType;

    @Column(name = "ACCOUNTNO", nullable = false)
    private String accountNo;

    @Column(name = "RCBN")
    private String rcbn;

    @Column(name = "TIN")
    private String tin;

    @Column(name = "SCUMLNO")
    private String SCUMLNO;

    @Column(name = "SCUMLDOCNO")
    private String SCUMLDOCNO;

    @Column(name = "DATEOFBIRTH")
    private Date dateOfBirth;

    @Column(name = "BUSINESSNAME")
    private String businessName;

    @Column(name = "BVNNO")
    private String BVNNO;

    @Column(name = "TYPEOFID")
    private String typeOfID;

    @Column(name = "IDCARDNO")
    private Integer IdCardNo;

    @Column(name = "CUSTOMERSELFIEPIC")
    private String customerSelfiePic;

    @Column(name = "ADDRESSVERIFDSTATUS")
    private String addressVerifyStatus;

    @Column(name = "ELEVATESTATUS")
    private String elevateStatus;
}
