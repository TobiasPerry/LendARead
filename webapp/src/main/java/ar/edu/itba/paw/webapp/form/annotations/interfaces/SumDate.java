package ar.edu.itba.paw.webapp.form.annotations.interfaces;

import ar.edu.itba.paw.webapp.form.annotations.implementations.NotEmptyImpl;
import ar.edu.itba.paw.webapp.form.annotations.implementations.SumDateImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= SumDateImpl.class)
public @interface SumDate {
    String message() default "The sum cannot be less or equals 0";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
