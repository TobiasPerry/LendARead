package ar.edu.itba.paw.webapp.presentation;

import org.springframework.ui.Model;

 public class SnackbarService {

     private SnackbarService() {}

    static public void displaySuccess(Model model) {
        model.addAttribute("showSnackbarSucess", true);
    }

    static public void displayValidation(Model model, FormValidationService formValidationService) {
            StringBuilder message = new StringBuilder();
            int num = 0;
            for (String invalidElementInfo : formValidationService) {
                message.append('\n');
                message.append(num++);
                message.append(". ");
                message.append(invalidElementInfo);
            }
            model.addAttribute("snackBarInvalidTextTitle", "Los valores son incorrectos: ");
            model.addAttribute("snackBarInvalidText", message.toString());
            model.addAttribute("showSnackbarInvalid", true);
        }
}
