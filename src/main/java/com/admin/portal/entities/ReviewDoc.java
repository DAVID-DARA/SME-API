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
@Table(name = "ReviewDocs")
public class ReviewDoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ENTRYID", nullable = false)
    private Long entryID;

    @ManyToOne
    @JoinColumn(name = "REFNO", referencedColumnName = "REFNO")
    private Reference referenceNumber;

    @Column(name = "DOCTYPE")
    private String docType;

    @Column(name = "DOCLOCATION")
    private String docLocation;

    @Enumerated(EnumType.STRING)
    @Column(name = "DOCSTATUS")
    private StatusDocuments docStatus;

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

    @Column(name = "DOCCOMMENT")
    private String docComment;

    @Column(name = "ACCOUNTNO")
    private String accountNo;

    @Column(name = "DATEAPPROVED")
    private Date dateApproved;
}
