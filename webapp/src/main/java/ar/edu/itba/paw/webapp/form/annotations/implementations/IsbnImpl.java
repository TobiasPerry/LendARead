package ar.edu.itba.paw.webapp.form.annotations.implementations;

import ar.edu.itba.paw.webapp.form.annotations.interfaces.Isbn;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsbnImpl implements ConstraintValidator<Isbn, String> {
    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext constraintValidatorContext) {
                // Check if the input string is null or empty
                if (isbn == null ) {
                    return true;
                }
                if (isbn.isEmpty()) {
                    return false;
                }
                // Remove any hyphens or spaces from the input string
                isbn = isbn.replace("-", "").replace(" ", "");

                // Check if the input string has exactly 10 or 13 characters
                if (isbn.length() != 10 && isbn.length() != 13) {
                    return false;
                }

                // Check if the input string contains only digits (0-9)
                if (!isbn.matches("\\d+")) {
                    return false;
                }

                // Calculate the ISBN checksum based on the length
                int length = isbn.length();
                int sum = 0;
                for (int i = 0; i < length; i++) {
                    int digit = Character.getNumericValue(isbn.charAt(i));
                    if (length == 10) {
                        sum += digit * (10 - i);
                    } else {
                        sum += i % 2 == 0 ? digit : digit * 3;
                    }
                }

                // Check if the ISBN checksum is valid
                if (length == 10) {
                    return sum % 11 == 0;
                } else {
                    return sum % 10 == 0;
                }
    }
}
