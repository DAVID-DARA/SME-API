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
@Table(name = "ReviewIds")
public class ReviewId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ENTRYID", nullable = false)
    private Long entryID;

    @ManyToOne
    @JoinColumn (name = "REFNO", referencedColumnName = "REFNO")
    private Reference referenceNumber;

    @Column(name = "IDTYPE")
    private String idType;

    @Column(name = "IDNUMBER")
    private String idNumber;

    @Column(name = "IDSIGNED")
    private String idSigned;

    @Column(name = "IDSIGNLOC")
    private String idSignLoc;

    @Column(name = "IDLOCATION")
    private String idLocation;

    @Enumerated(EnumType.STRING)
    @Column(name = "IDSTATUS")
    private StatusDocuments idStatus;

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

    @Column(name = "IDCOMMENT")
    private String idComment;

    @Column(name = "ACCOUNTNO")
    private String accountNo;

    @Column(name = "DATEAPPROVED")
    private Date dateApproved;
}
