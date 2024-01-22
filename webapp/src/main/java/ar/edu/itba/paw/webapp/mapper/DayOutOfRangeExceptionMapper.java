package ar.edu.itba.paw.webapp.mapper;

import ar.edu.itba.paw.exceptions.DayOutOfRangeException;
import ar.edu.itba.paw.webapp.dto.ErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Component
@Provider
@Singleton
public class DayOutOfRangeExceptionMapper implements ExceptionMapper<DayOutOfRangeException> {

    private final MessageSource messageSource;
    @Autowired
    public DayOutOfRangeExceptionMapper(MessageSource messageSource){
        this.messageSource = messageSource;
    }
    @Override
    public Response toResponse(DayOutOfRangeException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(ErrorDTO.fromError(messageSource.getMessage("exception.dayOutOfRange", null, LocaleContextHolder.getLocale()),null)).build();
    }
}