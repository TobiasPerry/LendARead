package ar.edu.itba.paw.webapp.presentation;

import org.springframework.ui.Model;

 public class SnackbarService {

     private SnackbarService() {}

    static public void updateSnackbar(Model model, FormValidationService formValidationService) {
        if(formValidationService.isValid())
            model.addAttribute("showSnackbarSucess", true);
        else {
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
}
