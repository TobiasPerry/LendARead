package ar.edu.itba.paw.webapp.form.annotations.implementations;

import ar.edu.itba.paw.webapp.form.annotations.interfaces.ParamsChecker;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ParamsCheckerImpl implements ConstraintValidator<ParamsChecker, Object> {

    private String field;
    private String secondField;

    private String thirdField;

    @Override
    public void initialize(ParamsChecker constraint) {
        this.field = constraint.field();
        this.secondField = constraint.secondField();
        this.thirdField = constraint.thirdField();
    }


    @Override
    public boolean isValid(final Object value, ConstraintValidatorContext constraintValidatorContext) {
        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
        Object secondFieldValue = new BeanWrapperImpl(value).getPropertyValue(secondField);
        Object thirdFieldValue = new BeanWrapperImpl(value).getPropertyValue(thirdField);
        return fieldValue != null || secondFieldValue != null || thirdFieldValue != null;
    }
}
