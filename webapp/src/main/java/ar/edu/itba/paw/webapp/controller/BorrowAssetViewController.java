package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.presentation.FormService;
import ar.edu.itba.paw.webapp.presentation.FormServiceBorrowAssetView;
import ar.edu.itba.paw.webapp.presentation.FormValidationService;
import ar.edu.itba.paw.webapp.presentation.SnackbarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Controller
public class BorrowAssetViewController {
    private final ViewResolver viewResolver;
    final String viewName = "views/borrowAssetView";

    private final FormServiceBorrowAssetView formElements = new FormServiceBorrowAssetView();

    @RequestMapping(value = "/borrowAsset", method = RequestMethod.POST)
    public String borrowAsset(
            Model model, HttpServletRequest request
    ){

        FormValidationService formValidationService = formElements.validateRequest(request);

        SnackbarService.updateSnackbar(model, formValidationService);


        return viewName;
    }

    @Autowired
    public BorrowAssetViewController(@Qualifier("viewResolver")final ViewResolver vr){
        this.viewResolver = vr;
    }

    @RequestMapping( "/borrowAssetView")
    public ModelAndView lendView(){
        return new ModelAndView(viewName);
    }
}
