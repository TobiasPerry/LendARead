package models.assetExistanceContext.implementations;


import models.assetExistanceContext.interfaces.Book;
import models.userContext.implementations.LocationImpl;
import models.userContext.implementations.UserImpl;

public class AssetInstance {
    private Book book;

    private PhysicalCondition physicalCondition;

    private UserImpl userReference;

    private LocationImpl location;
}
