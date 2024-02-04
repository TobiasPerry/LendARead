package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.interfaces.ImageService;
import ar.edu.itba.paw.models.miscellaneous.Image;
import ar.edu.itba.paw.webapp.miscellaneous.EndpointsUrl;
import ar.edu.itba.paw.webapp.miscellaneous.ImagesSizes;
import ar.edu.itba.paw.webapp.miscellaneous.StaticCache;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Component
@Path(EndpointsUrl.IMAGE_URL)
public class ImageController {


   private final ImageService imageService;
    @Context
    private UriInfo uriInfo;
    @Autowired
    public ImageController(final ImageService imageService){
         this.imageService = imageService;
    }


    @GET
    @Path("/{id}")
    @Produces({ "image/png", "image/jpeg", "image/gif", MediaType.APPLICATION_JSON })
    public Response getImage(@PathParam("id") final int id,
                             @QueryParam("size")  @DefaultValue("FULL") @Pattern(regexp = ("FULL|CUADRADA|PORTADA"),message = "{Image.size.pattern}") final String size) throws ImageNotFoundException {
        Image image = imageService.getImage(id).orElseThrow(ImageNotFoundException::new);
        byte [] imageBytes = image.getPhoto();
        Response.ResponseBuilder responseBuilder = Response.ok(ImagesSizes.valueOf(size).resizeImage(imageBytes));
        StaticCache.setUnconditionalCache(responseBuilder);
        return responseBuilder.build();
    }


    @POST
    @Produces({ "image/png", "image/jpeg", "image/gif", MediaType.APPLICATION_JSON })
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response addImage(@ar.edu.itba.paw.webapp.form.annotations.interfaces.Image @FormDataParam("image") FormDataBodyPart imageBodyPart, @FormDataParam("image") byte[] image) {
        Image newImage = imageService.addImage( image);
        URI url = uriInfo.getAbsolutePathBuilder().path(String.valueOf(newImage.getId())).build();
        return Response.created(url).entity(image).build();
    }
}
