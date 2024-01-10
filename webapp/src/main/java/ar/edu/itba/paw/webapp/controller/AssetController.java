package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.AssetAlreadyExistException;
import ar.edu.itba.paw.exceptions.AssetNotFoundException;
import ar.edu.itba.paw.exceptions.LanguageNotFoundException;
import ar.edu.itba.paw.interfaces.AssetService;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.Asset;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.edu.itba.paw.webapp.dto.AssetDTO;
import ar.edu.itba.paw.webapp.form.AddAssetForm;
import ar.edu.itba.paw.webapp.miscellaneous.EndpointsUrl;
import ar.edu.itba.paw.webapp.miscellaneous.PaginatedData;
import ar.edu.itba.paw.webapp.miscellaneous.StaticCache;
import ar.edu.itba.paw.webapp.miscellaneous.Vnd;
import ar.itba.edu.paw.exceptions.BookAlreadyExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Component
@Path(EndpointsUrl.Assets_URL)
public class AssetController {



    private final AssetService as;
    @Context
    private UriInfo uriInfo;
    private static final Logger LOGGER = LoggerFactory.getLogger(AssetController.class);

    @Autowired
    public AssetController(final AssetService as) {
        this.as = as;
    }

    @GET
    @Produces(value = {Vnd.VND_ASSET})
    public Response getAssets(
            @QueryParam(value = "page")  @Min(1)  @DefaultValue("1") final int page,
            @QueryParam(value = "itemsPerPage")  @Min(1) @DefaultValue("10") final int itemsPerPage,
            @QueryParam(value = "isbn")  final String isbn,
            @QueryParam(value = "author")  final String author,
            @QueryParam(value = "title")  final String title,
            @QueryParam(value = "language")  final String language
    ){
        PagingImpl<Asset> books = as.getBooks(page,itemsPerPage,isbn,author,title,language);
        LOGGER.info("GET asset/ isbn:{} author:{} title:{} language:{}",isbn,author,title,language);
        List<AssetDTO> assetsDTO = AssetDTO.fromBooks( books.getList(),uriInfo);
        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<AssetDTO>>(assetsDTO) {});
        PaginatedData.paginatedData(response, books, uriInfo);
        return response.build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = {Vnd.VND_ASSET})
    public Response getAsset(@PathParam("id") final Long id) throws AssetNotFoundException {
        Asset book = as.getBookById(id);

        LOGGER.info("GET asset/ id:{}",book.getId());
        Response.ResponseBuilder response = Response.ok(AssetDTO.fromAsset(uriInfo,book));
        StaticCache.setUnconditionalCache(response);

        return response.build();
    }

    @POST
    @Consumes(value = {Vnd.VND_ASSET})
    public Response createAsset(@Valid @RequestBody final AddAssetForm assetForm) throws BookAlreadyExistException, LanguageNotFoundException, AssetAlreadyExistException {
         Asset book = as.addBook(assetForm.getIsbn(),assetForm.getAuthor(),assetForm.getTitle(),assetForm.getLanguage());
         LOGGER.info("POST asset/ id:{}",book.getId());
        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(book.getId())).build();
        return Response.created(uri).build();
    }


}
