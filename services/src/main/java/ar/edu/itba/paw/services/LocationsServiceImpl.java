package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.LocationNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.LocationsService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.userContext.implementations.Location;
import ar.edu.itba.paw.models.userContext.implementations.User;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.edu.itba.paw.utils.HttpStatusCodes;
import ar.itba.edu.paw.persistenceinterfaces.LocationDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LocationsServiceImpl implements LocationsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserReviewsServiceImpl.class);

    private final LocationDao locationsDao;
    private final UserService userService;

   @Autowired
    public LocationsServiceImpl(LocationDao locationsDao, UserService userService) {
        this.locationsDao = locationsDao;
        this.userService = userService;
    }

    @Override
    @Transactional
    public void addLocation(Location lc) {
         locationsDao.addLocation(lc);
    }

    @Override
    @Transactional
    public Location addLocation(String name, String locality, String province, String country, String zipcode) throws UserNotFoundException {
       User user = userService.getCurrentUser();
       Location newLocation = new Location(name, zipcode, locality, province, country, user);
       addLocation(newLocation);
       LOGGER.info("Location {} added for user {}", newLocation.getId(), user.getId());
       return newLocation;

    }

    @Override
    @Transactional(readOnly = true)
    public Location getLocation(int locationId) throws LocationNotFoundException {
        Location location= locationsDao.getLocation(locationId);
        if (location == null) {
            throw new LocationNotFoundException(HttpStatusCodes.NOT_FOUND);
        }
        return location;
    }



    @Override
    @Transactional
    public void editLocation(Location lc) {
         locationsDao.editLocation(lc);
    }

    @Override
    @Transactional
    public void deleteLocation(Location lc) throws LocationNotFoundException {
       Location location = getLocation(lc.getId());
       if (!location.isActive()) {
           throw new LocationNotFoundException(HttpStatusCodes.NOT_FOUND);
       }
       location.setActive(false);
       LOGGER.info("Location {} deleted for user {}", lc.getId(), lc.getUser().getId());
    }



    @Override
    @Transactional
    public Location editLocationById(int locationId, Optional<String> name, Optional<String> locality, Optional<String> province, Optional<String> country, Optional<String> zipcode) throws LocationNotFoundException {
        Location location = locationsDao.getLocation(locationId);
        name.ifPresent(location::setName);
        locality.ifPresent(location::setLocality);
        province.ifPresent(location::setProvince);
        country.ifPresent(location::setCountry);
        zipcode.ifPresent(location::setZipcode);
        editLocation(location);
        return location;
    }

    @Transactional(readOnly = true)
    @Override
    public PagingImpl<Location> getLocations(final Integer userId, final int page, final int itemsPerPage) {
        return locationsDao.getLocations(userId, page, itemsPerPage);
    }


    @Override
    @Transactional
    public void deleteLocationById(int locationId) throws  LocationNotFoundException {
        if(locationId != -1) {
            deleteLocation(getLocation(locationId));
            LOGGER.info("Location {} deleted", locationId);
        }
    }
}
