package ar.edu.itba.paw.webapp.mapper;

import ar.edu.itba.paw.exceptions.ImageNotFoundException;
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

@Component @Singleton @Provider
public class ImageNotFoundExceptionMapper implements ExceptionMapper<ImageNotFoundException> {

    private final MessageSource messageSource;
    @Autowired
    public ImageNotFoundExceptionMapper(MessageSource messageSource){
        this.messageSource = messageSource;
    }
    @Override
    public Response toResponse(ImageNotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).entity(ErrorDTO.fromError(messageSource.getMessage("exception.imageNotFound", null, LocaleContextHolder.getLocale()),null)).build();
    }
}