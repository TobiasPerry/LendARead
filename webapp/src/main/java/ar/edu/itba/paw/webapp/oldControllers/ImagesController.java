package ar.edu.itba.paw.webapp.oldControllers;

import ar.edu.itba.paw.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.interfaces.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ImagesController {
    private final ImageService imageService;

    @Autowired
    public ImagesController(ImageService imageService) {
        this.imageService = imageService;
    }

    @RequestMapping( "/getImage/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String id, HttpServletRequest request){
        byte[] array;

        try {
           array = imageService.getPhoto(Integer.parseInt(id));
        }catch (ImageNotFoundException e){
            array = new byte[0];
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", MediaType.IMAGE_JPEG_VALUE);
        headers.set("Content-Disposition","inline; filename=\"whaterver.jpg\"");

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(array);
    }

}
