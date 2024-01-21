package ar.edu.itba.paw.webapp.mapper;

import ar.edu.itba.paw.webapp.dto.ErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Singleton
@Component
@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {
    private final MessageSource messageSource;
    @Autowired
    public BadRequestExceptionMapper(MessageSource messageSource){
        this.messageSource = messageSource;
    }
    @Override
    public Response toResponse(BadRequestException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(ErrorDTO.fromError(messageSource.getMessage("exception.badRequest", null, LocaleContextHolder.getLocale()),null)).build();
    }
}