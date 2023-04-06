package ar.edu.itba.paw.services;

import interfaces.AssetExistanceService;
import org.springframework.stereotype.Service;

@Service
public class AssetExistanceServiceImpl implements AssetExistanceService {

    @Override
    public boolean addAssetInstance() {
        System.out.println("send to database a new book");

        return false;
    }

    @Override
    public boolean removeAssetInstance() {
        return false;
    }
}
