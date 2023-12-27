package ar.edu.itba.paw.webapp.mapper;


import ar.edu.itba.paw.exceptions.CustomException;
import ar.edu.itba.paw.webapp.dto.ErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Singleton
@Component
@Provider
public class CustomExceptionMapper implements ExceptionMapper<CustomException> {

    private final MessageSource messageSource;
    @Autowired
    public CustomExceptionMapper(MessageSource messageSource){
        this.messageSource = messageSource;
    }

    @Override
    public Response toResponse(CustomException e) {
        return Response.status(e.getStatusCode()).entity(ErrorDTO.fromError(messageSource.getMessage(e.getMessage(), null,LocaleContextHolder.getLocale()))).build();
    }
}
