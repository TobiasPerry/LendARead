package ar.edu.itba.paw.webapp.presentation;

import java.util.regex.Pattern;

public class FormElementISBN extends FormElementImpl{
    public FormElementISBN(String label, String inputName) {
        super(label, "string", inputName, "");
    }

    @Override
    public boolean isValidInput(String input) {
        return ISBNValidator.isValidISBN(input);
    }

    @Override
    public String getInvalidInputFormatMessage() {
        return "no es un isbn valido";
    }

    static class ISBNValidator {
        public static boolean isValidISBN(String isbn) {
            if (isbn.length() == 0)
                return false;


            String sanitizedISBN = isbn.replaceAll("[-\\s]", ""); // Remove dashes and whitespaces
            int length = sanitizedISBN.length();

            if (length == 10)
                return isValidISBN10(sanitizedISBN);
             else if (length == 13)
                return isValidISBN13(sanitizedISBN);

            return false;
        }

        private static boolean isValidISBN10(String isbn10) {
            Pattern pattern = Pattern.compile("^(\\d{9}[0-9X])$");

            if (!pattern.matcher(isbn10).matches()) {
                return false;
            }

            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += (i + 1) * Character.getNumericValue(isbn10.charAt(i));
            }

            char lastChar = isbn10.charAt(9);
            int checkDigit = (lastChar == 'X' || lastChar == 'x') ? 10 : Character.getNumericValue(lastChar);
            sum += 10 * checkDigit;

            return sum % 11 == 0;
        }

        private static boolean isValidISBN13(String isbn13) {
            Pattern pattern = Pattern.compile("^\\d{13}$");
            if (!pattern.matcher(isbn13).matches())
                return false;


            int sum = 0;
            for (int i = 0; i < 13; i++) {
                int digit = Character.getNumericValue(isbn13.charAt(i));
                int weight = (i % 2 == 0) ? 1 : 3;
                sum += digit * weight;
            }

            return sum % 10 == 0;
        }
    }
}
