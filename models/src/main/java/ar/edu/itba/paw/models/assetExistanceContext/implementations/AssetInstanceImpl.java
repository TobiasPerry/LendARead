package ar.edu.itba.paw.models.assetExistanceContext.implementations;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.edu.itba.paw.models.userContext.interfaces.Location;
import ar.edu.itba.paw.models.userContext.interfaces.User;

public class AssetInstanceImpl implements AssetInstance {

    public AssetInstanceImpl(int id,Book book, PhysicalCondition physicalCondition, User userReference, Location location,int imageId) {
        this.id = id;
        this.book = book;
        this.physicalCondition = physicalCondition;
        this.userReference = userReference;
        this.location = location;
        this.imageId = imageId;
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

    private Book book;

    private int id;

    private PhysicalCondition physicalCondition;

    private User userReference;

    private Location location;

    private int imageId;
    @Override
    public Book getBook() {
        return book;
    }

    @Override
    public User getOwner() {
        return userReference;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public int getImageId() {
        return imageId;
    }

    @Override
    public PhysicalCondition getPhysicalCondition() {
        return physicalCondition;
    }
}
