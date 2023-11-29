package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.AssetService;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.edu.itba.paw.webapp.dto.AssetDTO;
import ar.edu.itba.paw.webapp.form.AddAssetForm;
import ar.edu.itba.paw.webapp.miscellaneous.Vnd;
import ar.itba.edu.paw.exceptions.BookAlreadyExistException;
import com.sun.istack.internal.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Controller
@Path("assets")
public class AssetController {



    private final AssetService as;
    @Context
    private UriInfo uriInfo;

    @Autowired
    public AssetController(final AssetService as) {
        this.as = as;
    }

    @GET
    @Produces(value = {Vnd.VND_ASSET})
    public Response getAssets(
            @QueryParam(value = "isbn") @Nullable final String isbn,
            @QueryParam(value = "author") @Nullable final String author,
            @QueryParam(value = "title") @Nullable final String title,
            @QueryParam(value = "language") @Nullable final String language
    ){
        List<BookImpl> books = as.getBooks(isbn,author,title,language);
        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<AssetDTO>>(AssetDTO.fromBooks(books,uriInfo)) {});
        return response.build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = {Vnd.VND_ASSET})
    public Response getAsset(@PathParam("id") final Integer id){
        BookImpl book = as.getBookById(id);
        return Response.ok(AssetDTO.fromAsset(uriInfo,book)).build();
    }

    @POST
    @Consumes(value = {Vnd.VND_ASSET})
    public Response createAsset(@Valid @RequestBody final AddAssetForm assetForm) throws BookAlreadyExistException {
         BookImpl book = as.addBook(assetForm.getIsbn(),assetForm.getAuthor(),assetForm.getTitle(),assetForm.getLanguage());
        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(book.getId())).build();
        return Response.created(uri).build();
    }


}
