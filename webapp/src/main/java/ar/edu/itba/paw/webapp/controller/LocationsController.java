package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.LocationsService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.webapp.form.AddAssetForm;
import ar.edu.itba.paw.webapp.form.LocationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class LocationsController {

    private final LocationsService locationsService;
    private final UserService userService;
    private final static String VIEW_NAME = "views/locations";

    @Autowired
    public LocationsController(LocationsService locationsService, UserService userService) {
        this.locationsService = locationsService;
        this.userService = userService;
    }

    @RequestMapping(value = "/userLocations", method = RequestMethod.GET)
    public ModelAndView manageLocations(@ModelAttribute("locationForm") final LocationForm locationForm) throws UserNotFoundException {
        List<LocationImpl> locations = locationsService.getLocations(userService.getUser(userService.getCurrentUser()));
        return new ModelAndView(VIEW_NAME).addObject("locations", locations);
    }

    @RequestMapping(value = "/editLocation", method = RequestMethod.POST)
    public ModelAndView editLocation(@Valid @ModelAttribute final LocationForm locationForm, final BindingResult errors) throws UserNotFoundException {

        if(errors.hasErrors())
            return manageLocations(locationForm).addObject("locationIdError", locationForm.getId()).addObject("hasErrors", true);

        locationsService.handleNewLocation(locationForm.getId(), locationForm.getName(), locationForm.getLocality(), locationForm.getProvince(), locationForm.getCountry(), locationForm.getZipcode(), userService.getUser(userService.getCurrentUser()));
        return new ModelAndView("redirect:/userLocations/");
    }

    @RequestMapping(value = "/deleteLocation", method = RequestMethod.POST)
    public ModelAndView deleteLocation(@RequestParam("id") int id) throws UserNotFoundException {
        locationsService.deleteLocationById(id);
        return new ModelAndView("redirect:/userLocations/");
    }

}
