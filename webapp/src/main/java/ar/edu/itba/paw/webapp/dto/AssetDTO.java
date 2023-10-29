package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.core.UriInfo;

@Getter
@Setter
public class AssetDTO {
    private int id;
    private String isbn;
    private String author;
    private String title;
    private String language;



    public static AssetDTO fromAsset(UriInfo url, BookImpl asset) {
        AssetDTO assetDTO = new AssetDTO();
        assetDTO.id = asset.getId();
        assetDTO.isbn = asset.getIsbn();
        assetDTO.author = asset.getAuthor();
        assetDTO.title = asset.getName();
        assetDTO.language = asset.getLanguage();
        return assetDTO;
    }
    public static String reference(UriInfo url, BookImpl asset) {
        return url.getBaseUriBuilder().path("assets").path(String.valueOf(asset.getId())).build().toString();
    }

}

