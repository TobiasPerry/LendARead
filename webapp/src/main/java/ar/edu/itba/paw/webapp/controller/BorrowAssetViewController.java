package ar.edu.itba.paw.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;

@Controller
public class BorrowAssetViewController {
    private final ViewResolver viewResolver;
    final String viewName = "views/borrowAssetView";

    @RequestMapping(value = "/borrowAsset", method = RequestMethod.POST)
    public String borrowAsset(
            @RequestParam(value = "email", required = true) final String email,
            @RequestParam(value = "guidelines", required = true) final String guidelines,
            @RequestParam(value = "location", required = true) final String location,
            Model model
    ){
        model.addAttribute("showSnackbar", true);

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
