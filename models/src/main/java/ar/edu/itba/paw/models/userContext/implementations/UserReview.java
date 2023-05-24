package ar.edu.itba.paw.models.userContext.implementations;

import javax.persistence.*;

@Entity
@Table(name = "user_reviews")
public class UserReview {
    public UserReview(final String review, final int rating, final int lendingId, final int reviewerId, final int recipientId) {
        this.review = review;
        this.rating = rating;
        this.lendingId = lendingId;
        this.reviewerId = reviewerId;
        this.recipientId = recipientId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_reviews_id_seq")
    @SequenceGenerator(sequenceName = "user_reviews_id_seq", name = "user_reviews_id_seq", allocationSize = 1)
    private Long id;

    @Column(length = 500, nullable = false)
    private String review;

    @Column(length = 1, nullable = false)
    private int rating;
    @Column(nullable = false)
    private int lendingId;

    @Column(nullable = false)
    private int reviewerId;

    @Column(nullable = false)
    private int recipientId;

    UserReview() {

    }
}
