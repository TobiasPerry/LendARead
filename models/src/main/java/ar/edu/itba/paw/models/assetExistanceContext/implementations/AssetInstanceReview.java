package ar.edu.itba.paw.models.assetExistanceContext.implementations;

import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;

import javax.persistence.*;

@Entity
public class AssetInstanceReview {


    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="lendId", referencedColumnName = "id", nullable = false)
    private LendingImpl lending;

    @Column(length = 500, nullable = false)
    private String review;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
    private UserImpl reviewer;
    @Column(nullable = false)
    private int rating;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assetinstancereview_id_seq")
    @SequenceGenerator(sequenceName = "assetinstancereview_id_seq", name = "assetinstancereview_id_seq", allocationSize = 1)
    private Long id;

    public AssetInstanceReview(final LendingImpl lending, String message, final UserImpl reviewer, int rating) {
        this.lending = lending;
        this.review = message;
        this.reviewer = reviewer;
        this.rating = rating;
    }

    public LendingImpl getLending() {
        return lending;
    }

    public String getReview() {
        return review;
    }

    public UserImpl getReviewer() {
        return reviewer;
    }

    public int getRating() {
        return rating;
    }

    public Long getId() {
        return id;
    }

    public void setLending(LendingImpl lending) {
        this.lending = lending;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setReviewer(UserImpl reviewer) {
        this.reviewer = reviewer;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AssetInstanceReview() {}
}
