package ar.edu.itba.paw.models.assetLendingContext.implementations;

import ar.edu.itba.paw.models.assetLendingContext.interfaces.Lending;
import jdk.nashorn.internal.objects.annotations.Getter;

import javax.persistence.*;
import java.time.LocalDate;

//this model only relates to the asset availability dao, in reality business
//logic deals with borrowed asset instances.
@Entity
@Table(name = "lendings")
public class LendingImpl implements Lending {

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

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Integer getAssetInstanceId() {
        return assetInstanceId;
    }

    @Override
    public void setAssetInstanceId(Integer assetInstanceId) {
        this.assetInstanceId = assetInstanceId;
    }

    @Override
    public Integer getBorrowerId() {
        return borrowerId;
    }

    @Override
    public void setBorrowerId(Integer borrowerId) {
        this.borrowerId = borrowerId;
    }

    @Override
    public LocalDate getLendDate() {
        return lendDate;
    }

    @Override
    public void setLendDate(LocalDate lendDate) {
        this.lendDate = lendDate;
    }

    @Override
    public LocalDate getDevolutionDate() {
        return devolutionDate;
    }

    @Override
    public void setDevolutionDate(LocalDate devolutionDate) {
        this.devolutionDate = devolutionDate;
    }

    @Override
    public LendingState getActive() {
        return active;
    }

    @Override
    public void setActive(LendingState active) {
        this.active = active;
    }
}

