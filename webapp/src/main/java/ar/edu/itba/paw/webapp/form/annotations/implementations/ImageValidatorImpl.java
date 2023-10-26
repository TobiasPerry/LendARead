package ar.edu.itba.paw.webapp.form.annotations.implementations;

import ar.edu.itba.paw.webapp.form.annotations.interfaces.Image;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageValidatorImpl implements ConstraintValidator<Image, FormDataBodyPart> {
    private static final long MAX_SIZE = 3000 * 3000;
    private static final String ACCEPTED_MIME_TYPES = "image/";


    @Override
    public void initialize(Image image) {

    }

    @Override
    public boolean isValid(FormDataBodyPart multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        if (multipartFile == null ) {
            return true;
        }
        return   multipartFile.getMediaType().toString().contains(ACCEPTED_MIME_TYPES);
    }
}