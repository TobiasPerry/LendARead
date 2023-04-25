package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.AssetAvailabilityService;
import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.BorrowedAssetInstance;
import ar.edu.itba.paw.models.userContext.implementations.UserAssetsImpl;
import ar.edu.itba.paw.models.userContext.interfaces.UserAssets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAssetInstanceServiceImpl implements UserAssetInstanceService {

    private final AssetInstanceService assetInstanceService;

    private final AssetAvailabilityService assetAvailabilityService;

    @Autowired
    public UserAssetInstanceServiceImpl(AssetInstanceService assetInstanceService, AssetAvailabilityService assetAvailabilityService) {
        this.assetInstanceService = assetInstanceService;
        this.assetAvailabilityService = assetAvailabilityService;
    }

    private List<AssetInstance> getMyBookInventory(String email) {
        return getAllMyBooks(email).stream().filter(book -> book.getAssetState().isPublic() || book.getAssetState().isPrivate()).collect(Collectors.toList());
    }

    private List<AssetInstance> getAllMyBooks(String email) {
        return assetInstanceService.getAllAssetsInstances().stream().filter(book -> book.getOwner().getEmail().equals(email)).collect(Collectors.toList());
    }

    private List<BorrowedAssetInstance> getMyLendedBooks(String email) {
       return assetAvailabilityService.getAllBorrowedAssetsInstances().stream().filter(borrowedAssetInstance -> borrowedAssetInstance.getBorrower().equals(email)).collect(Collectors.toList());
    }

    private List<BorrowedAssetInstance> getMyBorrowedBooks(String email) {
        return assetAvailabilityService.getAllBorrowedAssetsInstances().stream().filter(borrowedAssetInstance -> !borrowedAssetInstance.getBorrower().equals(email)).collect(Collectors.toList());
    }
    @Override
    public UserAssets getUserAssets(String email) {
        return new UserAssetsImpl(getMyLendedBooks(email), getMyBorrowedBooks(email), getMyBookInventory(email));
    }
}
