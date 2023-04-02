package ar.edu.itba.paw.webapp.presentation;

import javax.servlet.http.HttpServletRequest;

public interface FormElements {

    FormValidation validateRequest(HttpServletRequest request);
}
