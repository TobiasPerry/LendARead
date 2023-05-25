package ar.edu.itba.paw.models.assetLendingContext.implementations;

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


    @Column(name = "assetInstanceId")
    private Integer assetInstanceId;

    @Column(name = "borrowerId")
    private Integer borrowerId;

    @Column(name = "lendDate")
    private LocalDate lendDate;

    @Column(name = "devolutionDate")
    private LocalDate devolutionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "active")
    private LendingState active;

    LendingImpl() {}

    public LendingImpl(Integer assetInstanceId, Integer borrowerId, LocalDate lendDate, LocalDate devolutionDate, LendingState active) {
        this.assetInstanceId = assetInstanceId;
        this.borrowerId = borrowerId;
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

    public Integer getAssetInstanceId() {
        return assetInstanceId;
    }

    public void setAssetInstanceId(Integer assetInstanceId) {
        this.assetInstanceId = assetInstanceId;
    }

    public Integer getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Integer borrowerId) {
        this.borrowerId = borrowerId;
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

