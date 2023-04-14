package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.AssetAvailabilityService;
import org.springframework.stereotype.Service;

@Service
public class AssetAvailabilityAvailabilityServiceImpl implements AssetAvailabilityService {
    @Override
    public boolean borrowAsset(/*User user, AvailableAsset availableAsset*/) {

//        if(availableAsset.canBorrow(user)) {
//            //do logic with persistence to borrow asset
//
//           return true;
//        }

        return true;
    }
}
