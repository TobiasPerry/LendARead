package ar.edu.itba.paw.webapp.form.annotations.interfaces;

import ar.edu.itba.paw.webapp.form.annotations.implementations.NotEmptyImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyImpl.class)
public @interface NotEmpty {
    String message() default "{not.empty.validation}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
