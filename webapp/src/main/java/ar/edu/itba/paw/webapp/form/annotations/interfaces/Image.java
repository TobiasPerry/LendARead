package ar.edu.itba.paw.webapp.form.annotations.interfaces;


import ar.edu.itba.paw.webapp.form.annotations.implementations.ImageValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;

@Target({ElementType.FIELD,PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= ImageValidatorImpl.class)
public @interface Image {

    String message() default "{image.validation}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}