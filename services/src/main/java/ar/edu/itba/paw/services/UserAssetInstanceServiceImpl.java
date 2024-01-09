package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.LendingNotFoundException;
import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import ar.edu.itba.paw.models.assetLendingContext.implementations.Lending;
import ar.edu.itba.paw.utils.HttpStatusCodes;
import ar.itba.edu.paw.persistenceinterfaces.UserAssetsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserAssetInstanceServiceImpl implements UserAssetInstanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAssetInstanceService.class);
    private final UserAssetsDao userAssetsDao;


    @Autowired
    public UserAssetInstanceServiceImpl(final UserAssetsDao userAssetsDao) {
        this.userAssetsDao = userAssetsDao;
    }


    @Transactional(readOnly = true)
    @Override
    public Lending getBorrowedAssetInstance(final int lendingId) throws LendingNotFoundException {
      Optional<Lending> lending = userAssetsDao.getBorrowedAsset(lendingId);
      if (!lending.isPresent()) {
          LOGGER.error("Not found borrowed asset instance with the lending lendingId: {}", lendingId);
          throw new LendingNotFoundException(HttpStatusCodes.NOT_FOUND);
      }
      return lending.get();
    }

}
