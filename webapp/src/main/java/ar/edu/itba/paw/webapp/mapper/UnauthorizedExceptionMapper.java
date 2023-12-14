package ar.edu.itba.paw.webapp.mapper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Singleton
@Component
@Provider
public class UnauthorizedExceptionMapper implements ExceptionMapper<AccessDeniedException> {

    private final MessageSource messageSource;
    @Autowired
    public UnauthorizedExceptionMapper(MessageSource messageSource){
        this.messageSource = messageSource;
    }
    @Override
    public Response toResponse(AccessDeniedException e) {
        return Response.status(Response.Status.UNAUTHORIZED).entity(messageSource.getMessage("exception.accessDenied", null, LocaleContextHolder.getLocale())).build();

    }
}
