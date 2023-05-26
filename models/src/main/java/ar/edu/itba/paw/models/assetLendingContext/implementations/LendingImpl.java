package ar.edu.itba.paw.models.assetLendingContext.implementations;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;

import javax.persistence.*;
import java.time.LocalDate;

//this model only relates to the asset availability dao, in reality business
//logic deals with borrowed asset instances.
@Entity
@Table(name = "lendings")
public class LendingImpl {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lendings_id_seq")
    @SequenceGenerator(sequenceName = "lendings_id_seq", name = "lendings_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "assetInstanceId")
    private AssetInstanceImpl assetInstance;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "borrowerId")
    private UserImpl userReference;

    @Column(name = "lendDate")
    private LocalDate lendDate;

    @Column(name = "devolutionDate")
    private LocalDate devolutionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "active")
    private LendingState active;

    public LendingImpl() {}

    public LendingImpl(AssetInstanceImpl assetInstance, UserImpl userReference, LocalDate lendDate, LocalDate devolutionDate, LendingState active) {
        this.assetInstance = assetInstance;
        this.userReference = userReference;
        this.lendDate = lendDate;
        this.devolutionDate = devolutionDate;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AssetInstanceImpl getAssetInstance() {
        return assetInstance;
    }

    public void setAssetInstance(AssetInstanceImpl assetInstance) {
        this.assetInstance = assetInstance;
    }

    public UserImpl getBorrower() {
        return userReference;
    }

    public void setBorrower(UserImpl borrower) {
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
}

