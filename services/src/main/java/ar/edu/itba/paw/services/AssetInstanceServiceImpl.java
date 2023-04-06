package ar.edu.itba.paw.services;

import interfaces.AssetInstanceService;
import org.springframework.stereotype.Service;

@Service
public class AssetInstanceServiceImpl implements AssetInstanceService {

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
