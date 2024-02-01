package ar.edu.itba.paw.webapp.mapper;

import ar.edu.itba.paw.webapp.dto.ErrorsDTO;
import ar.edu.itba.paw.webapp.miscellaneous.Vnd;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Singleton
@Component
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {


    @Override
    public Response toResponse(ConstraintViolationException e) {

        ErrorsDTO errors = ErrorsDTO.fromValidationError(e);
        return Response.status(Response.Status.BAD_REQUEST).entity(new GenericEntity<ErrorsDTO>(errors) {}).type(Vnd.VND_VALIDATION_ERROR).build();
    }


}