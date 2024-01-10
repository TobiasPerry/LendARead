package ar.edu.itba.paw.webapp.form.annotations.interfaces;

import ar.edu.itba.paw.webapp.form.annotations.implementations.IsbnImpl;
import ar.edu.itba.paw.webapp.form.annotations.implementations.LanguageValidationImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= LanguageValidationImpl.class)
public @interface Language {
    String message() default "{language.validation}";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
