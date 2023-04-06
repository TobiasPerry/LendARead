package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.presentation.FormService;
import ar.edu.itba.paw.webapp.presentation.FormServiceAddAssetView;
import ar.edu.itba.paw.webapp.presentation.FormValidationService;
import ar.edu.itba.paw.webapp.presentation.SnackbarService;
import interfaces.TestService;
import models.assetExistanceContext.implementations.BookImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AddAssetViewController {
    private final ViewResolver viewResolver;
    private final static String INVALID_INPUT_MESSAGE ="Los siguientes argumentos estan mal:" ;
    FormService formElements = new FormServiceAddAssetView();

    @Autowired
    TestService testService;

    final String viewName = "views/addAssetView";

    @RequestMapping(value = "/addAsset", method = RequestMethod.POST)
    public String addAsset(
            Model model,
            HttpServletRequest request
    ){
        FormValidationService formValidationService = formElements.validateRequest(request);

        SnackbarService.updateSnackbar(model, formValidationService);

        return viewName;
    }
    @RequestMapping(value="/createAsset",method = RequestMethod.POST)
    public String createAsset(String isbn,String title ){
        BookImpl book = new BookImpl(isbn,"",title,"");
        testService.createAsset(book);
        return viewName;
    }
    @RequestMapping( "/addAssetViewTest")
    public ModelAndView addAsetTest(){
        final ModelAndView mav = new ModelAndView("views/testView");
        mav.addObject("path","addAsset");

        return  mav;
    }

    @Autowired
    public AddAssetViewController(@Qualifier("viewResolver")final ViewResolver vr){
        this.viewResolver = vr;
    }

    @RequestMapping( "/addAssetView")
    public ModelAndView lendView(){
        final ModelAndView mav = new ModelAndView(viewName);

        mav.addObject("path","addAsset");

        return  mav;
    }
}
