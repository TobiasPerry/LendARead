package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.LocationNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.LocationsService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.utils.HttpStatusCodes;
import ar.itba.edu.paw.persistenceinterfaces.LocationDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public void addLocation(LocationImpl lc) {
         locationsDao.addLocation(lc);
    }

    @Override
    @Transactional
    public LocationImpl addLocation(String name, String locality, String province, String country, String zipcode) throws UserNotFoundException {
       UserImpl user = userService.getUser(userService.getCurrentUser());
       LocationImpl newLocation = new LocationImpl(name, zipcode, locality, province, country, user);
       addLocation(newLocation);
       LOGGER.info("Location {} added for user {}", newLocation.getId(), user.getId());
       return newLocation;

    }

    @Override
    @Transactional(readOnly = true)
    public LocationImpl getLocation(int locationId) throws LocationNotFoundException {
        LocationImpl location= locationsDao.getLocation(locationId);
        if (location == null) {
            throw new LocationNotFoundException(HttpStatusCodes.NOT_FOUND);
        }
        return location;
    }


    @Override
    @Transactional(readOnly = true)
    public List<LocationImpl> getLocations(UserImpl user) {
        return locationsDao.getLocations(user);
    }

    @Override
    @Transactional
    public void editLocation(LocationImpl lc) {
         locationsDao.editLocation(lc);
    }

    @Override
    @Transactional
    public void deleteLocation(LocationImpl lc) {
            locationsDao.deleteLocation(lc);
            LOGGER.info("Location {} deleted for user {}", lc.getId(), lc.getUser().getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LocationImpl> getLocationsById(int userId) throws UserNotFoundException {
        return locationsDao.getLocations(userService.getUserById(userId));
    }

    @Override
    @Transactional
    public LocationImpl editLocationById(int locationId, Optional<String> name, Optional<String> locality, Optional<String> province, Optional<String> country, Optional<String> zipcode) throws LocationNotFoundException {
        LocationImpl location = locationsDao.getLocation(locationId);
        name.ifPresent(location::setName);
        locality.ifPresent(location::setLocality);
        province.ifPresent(location::setProvince);
        country.ifPresent(location::setCountry);
        zipcode.ifPresent(location::setZipcode);
        editLocation(location);
        return location;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LocationImpl> getLocations() {
        //return locationsDao.getAllLocations();
        return null;
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
