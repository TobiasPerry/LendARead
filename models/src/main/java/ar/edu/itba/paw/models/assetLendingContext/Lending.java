package ar.edu.itba.paw.models.assetLendingContext;

import ar.edu.itba.paw.models.assetExistanceContext.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.AssetInstanceReview;
import ar.edu.itba.paw.models.userContext.User;
import ar.edu.itba.paw.models.userContext.UserReview;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "lendings")
public class Lending {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lendings_id_seq")
    @SequenceGenerator(sequenceName = "lendings_id_seq", name = "lendings_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assetInstanceId", referencedColumnName = "id")
    private AssetInstance assetInstance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrowerId", referencedColumnName = "id")
    private User userReference;

    @Column(name = "lendDate")
    private LocalDate lendDate;

    @Column(name = "devolutionDate")
    private LocalDate devolutionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "active")
    private LendingState active;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lending")
    private List<UserReview> userReviews;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "lending")
    private AssetInstanceReview assetInstanceReview;

    public Lending() {
    }

    public Lending(AssetInstance assetInstance, User userReference, LocalDate lendDate, LocalDate devolutionDate, LendingState active) {
        this.assetInstance = assetInstance;
        this.userReference = userReference;
        this.lendDate = lendDate;
        this.devolutionDate = devolutionDate;
        this.active = active;
    }
    public Lending(Long id,AssetInstance assetInstance, User userReference, LocalDate lendDate, LocalDate devolutionDate, LendingState active) {
        this.assetInstance = assetInstance;
        this.userReference = userReference;
        this.lendDate = lendDate;
        this.devolutionDate = devolutionDate;
        this.active = active;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AssetInstance getAssetInstance() {
        return assetInstance;
    }

    public void setAssetInstance(AssetInstance assetInstance) {
        this.assetInstance = assetInstance;
    }

    public User getUserReference() {
        return userReference;
    }

    public void setUserReference(User borrower) {
        this.userReference = borrower;
    }

    public LocalDate getLendDate() {
        return lendDate;
    }

    public void setLendDate(LocalDate lendDate) {
        this.lendDate = lendDate;
    }

    public LocalDate getDevolutionDate() {
        return devolutionDate;
    }

    public void setDevolutionDate(LocalDate devolutionDate) {
        this.devolutionDate = devolutionDate;
    }

    public LendingState getActive() {
        return active;
    }

    public void setActive(LendingState active) {
        this.active = active;
    }

    public List<UserReview> getUserReviews() {
        return userReviews;
    }

    public void setUserReviews(List<UserReview> userReview) {
        this.userReviews = userReview;
    }

    public AssetInstanceReview getAssetInstanceReview() {
        return assetInstanceReview;
    }

    public void setAssetInstanceReview(AssetInstanceReview assetInstanceReview) {
        this.assetInstanceReview = assetInstanceReview;
    }

    @Override
    public String toString() {
        return "LendingImpl{" +
                "id=" + id +
                ", assetInstance=" + assetInstance +
                ", userReference=" + userReference +
                ", lendDate=" + lendDate +
                ", devolutionDate=" + devolutionDate +
                ", active=" + active +
                ", userReviews=" + userReviews +
                ", assetInstanceReview=" + assetInstanceReview +
                '}';
    }
}

