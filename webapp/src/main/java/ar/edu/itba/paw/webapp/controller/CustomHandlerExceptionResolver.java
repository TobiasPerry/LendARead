package ar.edu.itba.paw.webapp.controller;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex instanceof MaxUploadSizeExceededException) {
            ModelAndView modelAndView = new ModelAndView("redirect:/addAssetView");
            modelAndView.addObject("showSnackbarInvalid", true);
            modelAndView.addObject("snackBarInvalidTextTitle", "File size exceeds limit");
            return modelAndView;
        }

        // Handle other exceptions if necessary

        return null;
    }
}
