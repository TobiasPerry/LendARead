package models.assetExistanceContext.implementations;

import models.assetExistanceContext.interfaces.Asset;
import models.assetExistanceContext.interfaces.AssetInstance;
import models.assetExistanceContext.interfaces.Book;
import models.userContext.implementations.LocationImpl;
import models.userContext.implementations.UserImpl;
import models.userContext.interfaces.Location;
import models.userContext.interfaces.User;

public class AssetInstanceImpl implements AssetInstance {

    public AssetInstanceImpl(int id,Book book, PhysicalCondition physicalCondition, User userReference, Location location) {
        this.id = id;
        this.book = book;
        this.physicalCondition = physicalCondition;
        this.userReference = userReference;
        this.location = location;
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
    public PhysicalCondition getPhysicalCondition() {
        return physicalCondition;
    }
}
