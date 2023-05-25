package ar.edu.itba.paw.models.assetExistanceContext.implementations;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.miscellaneous.Image;
import ar.edu.itba.paw.models.miscellaneous.ImageImpl;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.interfaces.Location;
import ar.edu.itba.paw.models.userContext.interfaces.User;

import javax.persistence.*;

@Entity
@Table(name = "AssetInstance")
public class AssetInstanceImpl {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "asset_instance_id_seq")
    @SequenceGenerator(sequenceName = "asset_instance_id_seq", name = "asset_instance_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "assetId", referencedColumnName = "uid", nullable = false)
    private  BookImpl book;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner", referencedColumnName = "id", nullable = false)
    private  UserImpl userReference;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "locationId", referencedColumnName = "id")
    private  LocationImpl location;

    @Enumerated(EnumType.STRING)
    @Column(name = "physicalCondition", length = 100)
    private  PhysicalCondition physicalCondition;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 100)
    private  AssetState assetState;

    @Column(name = "maxLendingDays", nullable = false)
    private  int maxLendingDays;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photoId", referencedColumnName = "id", nullable = false)
    private ImageImpl imageId;

    public AssetInstanceImpl(int id, BookImpl book, PhysicalCondition physicalCondition, UserImpl userReference, LocationImpl location, ImageImpl imageId, AssetState as, int maxDaysLending) {
        this.id = (long) id;
        this.book = book;
        this.physicalCondition = physicalCondition;
        this.userReference = userReference;
        this.location = location;
        this.imageId = imageId;
        this.assetState = as;
        this.maxLendingDays = maxDaysLending;
    }

     AssetInstanceImpl() {

    }
    @Override
    public String toString() {
        return "AssetInstanceImpl{" +
                "book=" + book +
                ", physicalCondition=" + physicalCondition +
                ", userReference=" + userReference +
                ", location=" + location +
                '}';
    }


    public BookImpl getBook() {
        return book;
    }


    public UserImpl getOwner() {
        return userReference;
    }


    public LocationImpl getLocation() {
        return location;
    }

    public AssetState getAssetState() {
        return assetState;
    }


    public int getMaxDays() {
        return this.maxLendingDays;
    }


    public ImageImpl getImageId() {
        return imageId;
    }


    public boolean getIsBorrowedInstance() {
        return false;
    }


    public int getId() {
        return Math.toIntExact(id);
    }


    public PhysicalCondition getPhysicalCondition() {
        return physicalCondition;
    }
}
