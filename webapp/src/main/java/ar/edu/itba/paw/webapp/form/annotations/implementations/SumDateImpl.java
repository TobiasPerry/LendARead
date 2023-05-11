package ar.edu.itba.paw.webapp.form.annotations.implementations;


import ar.edu.itba.paw.webapp.form.AddAssetForm;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.SumDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SumDateImpl implements ConstraintValidator<SumDate, AddAssetForm> {

    @Override
    public void initialize(SumDate sumDate) {

    }

    @Override
    public boolean isValid(AddAssetForm value, ConstraintValidatorContext constraintValidatorContext) {
        return (value.getMaxDays() + value.getMaxWeeks()) > 0;
    }
}