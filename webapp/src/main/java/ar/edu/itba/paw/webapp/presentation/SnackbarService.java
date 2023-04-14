package ar.edu.itba.paw.webapp.presentation;

import org.springframework.ui.Model;

 public class SnackbarService {

     private SnackbarService() {}

    static public void displaySuccess(Model model) {
        model.addAttribute("showSnackbarSucess", true);
    }

}
