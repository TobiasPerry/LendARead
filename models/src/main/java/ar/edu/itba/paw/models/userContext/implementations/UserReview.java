package ar.edu.itba.paw.models.userContext.implementations;

import javax.persistence.*;

@Entity
public class UserReview {


    public UserReview(final String review, final int rating, final UserImpl reviewer, final UserImpl recipient) {
        this.review = review;
        this.rating = rating;
        this.recipient = recipient;
        this.reviewer = reviewer;
    }

    public UserReview(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_reviews_id_seq")
    @SequenceGenerator(sequenceName = "user_reviews_id_seq", name = "user_reviews_id_seq", allocationSize = 1)
    private Long id;

    @Column(length = 500, nullable = false)
    private String review;

    @Column(length = 1, nullable = false)
    private int rating;

    @ManyToOne
    @JoinColumn(name = "reviewerId", referencedColumnName = "id", nullable = false)
    private UserImpl reviewer;

    @ManyToOne
    @JoinColumn(name = "recipientId", referencedColumnName = "id", nullable = false)
    private UserImpl recipient;

}
