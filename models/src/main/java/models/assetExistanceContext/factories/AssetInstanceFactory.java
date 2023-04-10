package models.assetExistanceContext.factories;

import models.assetExistanceContext.implementations.AssetInstanceImpl;
import models.assetExistanceContext.implementations.PhysicalCondition;
import models.assetExistanceContext.interfaces.AssetInstance;
import models.assetExistanceContext.interfaces.Book;
import models.userContext.interfaces.Location;
import models.userContext.interfaces.User;

public class AssetInstanceFactory {
    public static AssetInstance createAssetInstance(int id,Book book, PhysicalCondition physicalCondition, User user, Location location) {
        return new AssetInstanceImpl(id,book, physicalCondition, user, location);
    }
}
