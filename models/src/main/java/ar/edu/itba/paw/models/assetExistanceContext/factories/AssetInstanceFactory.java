package ar.edu.itba.paw.models.assetExistanceContext.factories;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.edu.itba.paw.models.userContext.interfaces.Location;
import ar.edu.itba.paw.models.userContext.interfaces.User;

public class AssetInstanceFactory {
    public static AssetInstance createAssetInstance(int id, Book book, PhysicalCondition physicalCondition, User user, Location location,int photoId) {
        return new AssetInstanceImpl(id,book, physicalCondition, user, location,photoId);
    }
}
