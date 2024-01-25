package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.interfaces.Image;
import lombok.Getter;
import lombok.Setter;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Getter @Setter
public class ImageForm {

    @FormDataParam("image")
    private byte[] image;

    @FormDataParam("image")
    @Image
    private FormDataBodyPart imageBodyPart;
}
