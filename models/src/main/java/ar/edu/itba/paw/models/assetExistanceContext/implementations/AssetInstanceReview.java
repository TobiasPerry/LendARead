package ar.edu.itba.paw.models.assetExistanceContext.implementations;

import ar.edu.itba.paw.models.userContext.implementations.UserImpl;

import javax.persistence.*;

@Entity
public class AssetInstanceReview {
    //@Column(nullable = false)
    //private int assetInstanceId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="assetInstanceId", referencedColumnName = "id", nullable = false)
    private AssetInstanceImpl assetInstance;

    @Column(length = 500, nullable = false)
    private String review;
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
    private UserImpl reviewer;
    @Column(nullable = false)
    private int rating;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assetinstancereview_id_seq")
    @SequenceGenerator(sequenceName = "assetinstancereview_id_seq", name = "assetinstancereview_id_seq", allocationSize = 1)
    private Long id;

    public AssetInstanceReview(final AssetInstanceImpl assetInstance, String message, final UserImpl reviewer, int rating) {
        this.assetInstance = assetInstance;
        this.review = message;
        this.reviewer = reviewer;
        this.rating = rating;
    }

    public AssetInstanceReview() {}
}
