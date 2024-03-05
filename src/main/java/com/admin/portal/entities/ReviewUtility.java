package com.admin.portal.entities;

import javax.persistence.*;

import com.admin.portal.utilities.StatusDocuments;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "ReviewUtility")
public class ReviewUtility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ENTRYID", nullable = false)
    private Long entryId;

    @ManyToOne
    @JoinColumn (name = "REFNO", referencedColumnName = "REFNO")
    private Reference referenceNumber;

    @Column(name = "UTILITYTYPE")
    private String utilityType;

    @Column(name = "UTILITYLOCATION")
    private String utilityLocation;

    @Column(name = "UTILITYADDRESS")
    private String utilityAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "UTIITYSTATUS")
    private StatusDocuments utilityStatus;

    @ManyToOne
    @JoinColumn(name = "VERIFIEDBY", referencedColumnName = "user_id")
    private User verifiedBy;

    @ManyToOne
    @JoinColumn(name = "APPROVEDBY", referencedColumnName = "user_id")
    private User approvedBy;

    @Column(name = "SHAREPOINTID")
    private String sharepointID;

    @Column(name = "SHAREPOINTGUID")
    private String sharepointGUID;

    @Column(name = "DATEVERIFIED")
    private Date dateVerified;

    @Column(name = "UTILITYCOMMENT")
    private String UtilityComment;

    @Column(name = "ACCOUNTNO")
    private String accountNo;

    @Column(name = "DATEAPPROVED")
    private Date dateApproved;
}
