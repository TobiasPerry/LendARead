package ar.edu.itba.paw.webapp.mapper;

import ar.edu.itba.paw.exceptions.LanguageNotFoundException;
import ar.edu.itba.paw.webapp.dto.ErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Component
@Provider
@Singleton
public class LanguageNotFoundExceptionMapper implements ExceptionMapper<LanguageNotFoundException> {

    private final MessageSource messageSource;
    @Autowired
    public LanguageNotFoundExceptionMapper(MessageSource messageSource){
        this.messageSource = messageSource;
    }
    @Override
    public Response toResponse(LanguageNotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).entity(ErrorDTO.fromError(messageSource.getMessage("exception.LanguageNotFound", null, LocaleContextHolder.getLocale()),null)).build();
    }
}