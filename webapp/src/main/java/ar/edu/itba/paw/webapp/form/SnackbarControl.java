package ar.edu.itba.paw.webapp.form;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

public class SnackbarControl {

     private SnackbarControl() {}

    static public void displaySuccess(Model model, String msg) {
        model.addAttribute("showSnackbarSucess", true);
        model.addAttribute("snackbarSuccessMessage", msg);
    }
    static public void displaySuccess(ModelAndView model, String msg) {
        model.addObject("showSnackbarSucess", true);
        model.addObject("snackbarSuccessMessage", msg);
    }

}
