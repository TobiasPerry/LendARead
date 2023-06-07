package ar.edu.itba.paw.models.userContext.implementations;

import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingImpl;

import javax.persistence.*;

@Entity
public class UserReview {


    public UserReview(final String review, final int rating, final UserImpl reviewer, final UserImpl recipient,final LendingImpl lending) {
        this.review = review;
        this.rating = rating;
        this.recipient = recipient;
        this.reviewer = reviewer;
        this.lending = lending;
    }

    public UserReview(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userreview_id_seq")
    @SequenceGenerator(sequenceName = "userreview_id_seq", name = "userreview_id_seq", allocationSize = 1)
    private Long id;

    @Column(length = 500, nullable = false)
    private String review;

    @Column(length = 1, nullable = false)
    private int rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer", referencedColumnName = "id", nullable = false)
    private UserImpl reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient", referencedColumnName = "id", nullable = false)
    private UserImpl recipient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lendId", referencedColumnName = "id", nullable = false)
    private LendingImpl lending;
    public Long getId() {
        return id;
    }

    public String getReview() {
        return review;
    }

    public int getRating() {
        return rating;
    }

    public UserImpl getReviewer() {
        return reviewer;
    }

    public UserImpl getRecipient() {
        return recipient;
    }
}
