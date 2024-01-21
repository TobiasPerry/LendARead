package ar.edu.itba.paw.webapp.form.annotations.implementations;

import ar.edu.itba.paw.webapp.form.BorrowAssetForm;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.DayCheckOverlap;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DaysOverlapImpl implements ConstraintValidator<DayCheckOverlap, BorrowAssetForm> {

    @Override
    public boolean isValid(BorrowAssetForm s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null){
            return false;
        }
        LocalDate start = s.getBorrowDate();
        LocalDate end = s.getDevolutionDate();
        if (start == null || end == null){
            return false;
        }
        return start.isBefore(end);
    }
}