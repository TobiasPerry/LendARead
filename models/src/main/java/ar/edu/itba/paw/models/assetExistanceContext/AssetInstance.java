package ar.edu.itba.paw.models.assetExistanceContext;

import ar.edu.itba.paw.models.assetLendingContext.AssetState;
import ar.edu.itba.paw.models.miscellaneous.Image;
import ar.edu.itba.paw.models.userContext.Location;
import ar.edu.itba.paw.models.userContext.User;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AssetInstance")
public class AssetInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assetinstance_id_seq")
    @SequenceGenerator(sequenceName = "assetinstance_id_seq", name = "assetinstance_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "assetId", referencedColumnName = "uid", nullable = false)
    private Asset book;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner", referencedColumnName = "id", nullable = false)
    private User userReference;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "locationId", referencedColumnName = "id")
    private Location location;

    @Column(name = "description", length = 300)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "physicalCondition", length = 100)
    private  PhysicalCondition physicalCondition;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 100)
    private  AssetState assetState;

    @Column(name = "maxLendingDays", nullable = false)
    private  int maxLendingDays;

    @Column(name = "isReservable", nullable = false)
    private  boolean isReservable;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photoId", referencedColumnName = "id", nullable = false)
    private Image image;

    @Formula("(SELECT COALESCE(AVG(COALESCE(asr.rating, 0)),0) FROM assetInstanceReview AS asr left outer join lendings as l on asr.lendid = l.id  WHERE l.assetinstanceid = id)")
    private float rating;


    public AssetInstance(final int id, final Asset book, final PhysicalCondition physicalCondition, final User userReference, final Location location, final Image imageId, final AssetState as, final int maxDaysLending, final String description) {
        this.id = (long) id;
        this.book = book;
        this.physicalCondition = physicalCondition;
        this.userReference = userReference;
        this.location = location;
        this.image = imageId;
        this.assetState = as;
        this.maxLendingDays = maxDaysLending;
        this.description = description;
    }
    public AssetInstance(Asset book, PhysicalCondition physicalCondition, User userReference, Location location, Image imageId, AssetState as, int maxDaysLending, String description, boolean isReservable) {
        this.book = book;
        this.physicalCondition = physicalCondition;
        this.userReference = userReference;
        this.location = location;
        this.image = imageId;
        this.assetState = as;
        this.maxLendingDays = maxDaysLending;
        this.description = description;
        this.isReservable = isReservable;
    }


    public AssetInstance() {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssetInstance that = (AssetInstance) o;

        if (maxLendingDays != that.maxLendingDays) return false;
        if (isReservable != that.isReservable) return false;
        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(book, that.book)) return false;
        if (!Objects.equals(userReference, that.userReference))
            return false;
        if (!Objects.equals(location, that.location)) return false;
        if (!Objects.equals(description, that.description)) return false;
        if (physicalCondition != that.physicalCondition) return false;
        if (assetState != that.assetState) return false;
        return Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (book != null ? book.hashCode() : 0);
        result = 31 * result + (userReference != null ? userReference.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (physicalCondition != null ? physicalCondition.hashCode() : 0);
        result = 31 * result + (assetState != null ? assetState.hashCode() : 0);
        result = 31 * result + maxLendingDays;
        result = 31 * result + (isReservable ? 1 : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }

    public boolean getIsReservable() {
        return isReservable;
    }

    public void setReservable(boolean reservable) {
        isReservable = reservable;
    }

    public Asset getBook() {
        return book;
    }

    public User getOwner() {
        return userReference;
    }

    public Location getLocation() {
        return location;
    }

    public AssetState getAssetState() {
        return assetState;
    }

    public int getMaxDays() {
        return this.maxLendingDays;
    }

    public Image getImage() {
        return image;
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

    public void setBook(Asset book) {
        this.book = book;
    }

    public void setUserReference(User userReference) {
        this.userReference = userReference;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setPhysicalCondition(PhysicalCondition physicalCondition) {
        this.physicalCondition = physicalCondition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAssetState(AssetState assetState) {
        this.assetState = assetState;
    }

    public void setMaxLendingDays(int maxLendingDays) {
        this.maxLendingDays = maxLendingDays;
    }

    public void setImage(Image image) {
        this.image = image;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public float getRating() {
        return rating;
    }
    public void setIsReservable(boolean isReservable) {
        this.isReservable = isReservable;
    }

}
