package ar.edu.itba.paw.models.userContext;

import ar.edu.itba.paw.models.assetLendingContext.Lending;

import javax.persistence.*;

@Entity
public class UserReview {


    public UserReview(final String review, final int rating, final User reviewer, final User recipient, final Lending lending) {
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
    private User reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient", referencedColumnName = "id", nullable = false)
    private User recipient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lendId", referencedColumnName = "id", nullable = false)
    private Lending lending;
    public Long getId() {
        return id;
    }

    public String getReview() {
        return review;
    }

    public int getRating() {
        return rating;
    }

    public User getReviewer() {
        return reviewer;
    }

    public User getRecipient() {
        return recipient;
    }

    public Lending getLending() {
        return lending;
    }
    public boolean isLenderReview(){
        return recipient.getEmail().equals(lending.getAssetInstance().getOwner().getEmail());
    }
    public boolean isBorrowerReview(){
        return recipient.getEmail().equals(lending.getUserReference().getEmail());
    }
}
