package ar.edu.itba.paw.models.userContext.implementations;

import ar.edu.itba.paw.models.userContext.interfaces.User;

import javax.persistence.*;

@Entity
@Table(name = "user_reviews")
public class UserReview {
    public UserReview(final String review, final int rating, final int reviewerId, final int recipientId) {
        this.review = review;
        this.rating = rating;
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
    private int reviewerId;

//    @ManyToOne()
//    private User reviewer;

    @Column(nullable = false)
    private int recipientId;

    UserReview() {

    }
}
