package ar.edu.itba.paw.models.assetExistanceContext.implementations;

import javax.persistence.*;

@Entity
public class AssetInstanceReview {
    @Column(nullable = false)
    private int lendingId;
    @Column(length = 500, nullable = false)
    private String review;
    @Column(nullable = false)
    private int reviewerId;
    @Column(nullable = false)
    private int rating;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "asset_instance_id_seq")
    @SequenceGenerator(sequenceName = "asset_instance_id_seq", name = "asset_instance_id_seq", allocationSize = 1)
    private Long id;

    public AssetInstanceReview(int lendingId, String message, int reviewerId, int rating) {
        this.lendingId = lendingId;
        this.review = message;
        this.reviewerId = reviewerId;
        this.rating = rating;
    }

    AssetInstanceReview() {}
}
