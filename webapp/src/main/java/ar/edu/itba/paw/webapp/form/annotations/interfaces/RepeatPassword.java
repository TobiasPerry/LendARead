package ar.edu.itba.paw.webapp.form.annotations.interfaces;

import ar.edu.itba.paw.webapp.form.annotations.implementations.ChangePasswordRepeatPasswordImpl;
import ar.edu.itba.paw.webapp.form.annotations.implementations.RegisterRepeatPasswordImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {RegisterRepeatPasswordImpl.class, ChangePasswordRepeatPasswordImpl.class})
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RepeatPassword {

    String message() default "{repeat.password.validation}";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}