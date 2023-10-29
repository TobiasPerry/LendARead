package ar.edu.itba.paw.webapp.exceptionMapper;

import ar.edu.itba.paw.webapp.dto.ErrorDTO;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Singleton
@Component
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {


    @Override
    public Response toResponse(ConstraintViolationException e) {
        List<ErrorDTO> errors = new ArrayList<>();

        e.getConstraintViolations().forEach(violation -> errors.add(ErrorDTO.fromValidationError(getViolationPropertyName(violation), violation.getMessage())));


        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new GenericEntity<Collection<ErrorDTO>>(errors) {
                })
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    private String getViolationPropertyName(ConstraintViolation<?> violation) {
        final String propertyPath = violation.getPropertyPath().toString();
        return propertyPath.substring(propertyPath.lastIndexOf(".") + 1);
    }
}