package ar.edu.itba.paw.webapp.form;

import org.springframework.ui.Model;

 public class SnackbarService {

     private SnackbarService() {}

    static public void displaySuccess(Model model, String msg) {
        model.addAttribute("showSnackbarSucess", true);
        model.addAttribute("snackbarSuccessMessage", msg);
    }

}
