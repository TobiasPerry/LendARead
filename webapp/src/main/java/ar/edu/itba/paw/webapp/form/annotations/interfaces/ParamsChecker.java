package ar.edu.itba.paw.webapp.form.annotations.interfaces;

import ar.edu.itba.paw.webapp.form.annotations.implementations.ParamsCheckerImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE,ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = ParamsCheckerImpl.class)
public @interface ParamsChecker {
    String message() default "{required.params}";

    Class<?>[] groups() default {};

    String field();

    String secondField();

    String thirdField();

    Class<? extends Payload>[] payload() default {};
}
