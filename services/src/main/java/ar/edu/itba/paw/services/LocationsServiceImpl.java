package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.LocationsService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.itba.edu.paw.persistenceinterfaces.LocationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LocationsServiceImpl implements LocationsService {

    private final LocationDao locationsDao;
    private final UserService userService;

   @Autowired
    public LocationsServiceImpl(LocationDao locationsDao, UserService userService) {
        this.locationsDao = locationsDao;
        this.userService = userService;
    }

    @Override
    @Transactional
    public LocationImpl addLocation(LocationImpl lc) {
        return locationsDao.addLocation(lc);
    }

    @Override
    @Transactional
    public LocationImpl getLocation(int locationId) {
        return locationsDao.getLocation(locationId);
    }

    @Override
    @Transactional
    public List<LocationImpl> getLocations(UserImpl user) {
        return locationsDao.getLocations(user);
    }

    @Override
    @Transactional
    public LocationImpl editLocation(LocationImpl lc) {
        return locationsDao.editLocation(lc);
    }

    @Override
    @Transactional
    public void deleteLocation(LocationImpl lc) {
        locationsDao.deleteLocation(lc);
    }

    @Override
    @Transactional
    public List<LocationImpl> getLocationsById(int userId) throws UserNotFoundException {
        return locationsDao.getLocations(userService.getUserById(userId));
    }

    @Override
    @Transactional
    public LocationImpl editLocationById(int locationId) {
        return locationsDao.editLocation(locationsDao.getLocation(locationId));
    }

    @Override
    @Transactional
    public void deleteLocationById(int locationId) throws UserNotFoundException {
        deleteLocation(getLocation(locationId));
    }
}
