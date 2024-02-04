package ar.edu.itba.paw.models.assetExistanceContext;

import ar.edu.itba.paw.models.assetLendingContext.Lending;
import ar.edu.itba.paw.models.userContext.User;

import javax.persistence.*;

@Entity
public class AssetInstanceReview {


    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="lendId", referencedColumnName = "id", nullable = false)
    private Lending lending;

    @Column(length = 500, nullable = false)
    private String review;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
    private User reviewer;
    @Column(nullable = false)
    private int rating;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assetinstancereview_id_seq")
    @SequenceGenerator(sequenceName = "assetinstancereview_id_seq", name = "assetinstancereview_id_seq", allocationSize = 1)
    private Long id;

    public AssetInstanceReview(final Lending lending, String message, final User reviewer, int rating) {
        this.lending = lending;
        this.review = message;
        this.reviewer = reviewer;
        this.rating = rating;
    }

    public Lending getLending() {
        return lending;
    }

    public String getReview() {
        return review;
    }

    public User getReviewer() {
        return reviewer;
    }

    public int getRating() {
        return rating;
    }

    public Long getId() {
        return id;
    }

    public void setLending(Lending lending) {
        this.lending = lending;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setReviewer(User reviewer) {
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
