package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.AssetAlreadyExistException;
import ar.edu.itba.paw.exceptions.AssetNotFoundException;
import ar.edu.itba.paw.exceptions.LanguageNotFoundException;
import ar.edu.itba.paw.exceptions.UnableToCreateAssetException;
import ar.edu.itba.paw.interfaces.AssetService;
import ar.edu.itba.paw.models.assetExistanceContext.Asset;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.edu.itba.paw.webapp.dto.AssetDTO;
import ar.edu.itba.paw.webapp.form.AddAssetForm;
import ar.edu.itba.paw.webapp.form.AssetsGetForm;
import ar.edu.itba.paw.webapp.form.PatchAssetForm;
import ar.edu.itba.paw.webapp.miscellaneous.EndpointsUrl;
import ar.edu.itba.paw.webapp.miscellaneous.PaginatedData;
import ar.edu.itba.paw.webapp.miscellaneous.StaticCache;
import ar.edu.itba.paw.webapp.miscellaneous.Vnd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
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
            @Valid @BeanParam final AssetsGetForm assetsGetForm) {
        PagingImpl<Asset> books = as.getAssets(assetsGetForm.getPage(),assetsGetForm.getItemsPerPage(), assetsGetForm.getIsbn(), assetsGetForm.getAuthor(), assetsGetForm.getTitle(), assetsGetForm.getLanguage());
        if (books.getTotalPages() == 0 || books.getList().isEmpty()) {
            return Response.noContent().build();
        }
        LOGGER.info("GET asset/ isbn:{} author:{} title:{} language:{}",assetsGetForm.getIsbn(), assetsGetForm.getAuthor(), assetsGetForm.getTitle(), assetsGetForm.getLanguage());
        List<AssetDTO> assetsDTO = AssetDTO.fromBooks( books.getList(),uriInfo);
        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<AssetDTO>>(assetsDTO) {});
        PaginatedData.paginatedData(response, books, uriInfo);
        return response.build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = {Vnd.VND_ASSET})
    public Response getAsset(@Context javax.ws.rs.core.Request request,@PathParam("id") final Long id) throws AssetNotFoundException {
        Asset book = as.getAssetById(id).orElseThrow(AssetNotFoundException::new);
        AssetDTO assetDTO = AssetDTO.fromAsset(uriInfo,book);
        EntityTag eTag = new EntityTag(String.valueOf(assetDTO.hashCode()));
        Response.ResponseBuilder response = StaticCache.getConditionalCacheResponse(request, eTag);
        if (response == null) {
            LOGGER.info("GET asset/ id:{}",id);
            return Response.ok(assetDTO).tag(eTag).build();
        }
        LOGGER.info("GET asset/ id:{} 304",id);
        return response.build();
    }

    @POST
    @Consumes(value = {Vnd.VND_ASSET})
    @Produces(value = {Vnd.VND_ASSET})
    public Response createAsset(@Valid @RequestBody final AddAssetForm assetForm) throws  AssetAlreadyExistException, UnableToCreateAssetException {
         Asset book = as.addAsset(assetForm.getIsbn(),assetForm.getAuthor(),assetForm.getTitle(),assetForm.getLanguage());
         LOGGER.info("POST asset/ id:{}",book.getId());
        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(book.getId())).build();
        return Response.created(uri).entity(AssetDTO.fromAsset(uriInfo,book)).build();
    }
    @PATCH
    @Path("/{id}")
    @Consumes(value = {Vnd.VND_ASSET})
    public Response updateAsset(@PathParam("id") final Long id, @Valid final PatchAssetForm assetForm) throws AssetNotFoundException, LanguageNotFoundException, AssetAlreadyExistException {
         as.updateAsset(id,assetForm.getIsbn(),assetForm.getAuthor(),assetForm.getTitle(),assetForm.getLanguage());
        LOGGER.info("PATCH asset/ id:{}",id);
        return Response.noContent().build();
    }


}
