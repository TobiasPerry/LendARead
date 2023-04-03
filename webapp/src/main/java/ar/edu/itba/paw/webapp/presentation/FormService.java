package ar.edu.itba.paw.webapp.presentation;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface FormService {

    public List<List<FormElement>> getAllFormElementLists();

    default FormValidationService validateRequest(HttpServletRequest request) {
        List<List<FormElement>>  allFormElementLists = getAllFormElementLists();

        FormValidationService response = new FormValidationServiceImpl();

        for (List<FormElement> formElementList : allFormElementLists) {
            for (FormElement formElement : formElementList) {
                String inputName = formElement.getInputName();
                String requestValue = request.getParameter(inputName);
                if (requestValue == null || !formElement.isValidInput(requestValue)) {
                    response.addInvalidFormElement(formElement);
                }
            }
        }

        return response;
    }


}
