package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.core.UriInfo;
import java.util.List;

@Getter
@Setter
public class AssetDTO {
    private int id;
    private String isbn;
    private String author;
    private String title;
    private String language;

    private String selfUrl;


    public static AssetDTO fromAsset(UriInfo url, BookImpl asset) {
        AssetDTO assetDTO = new AssetDTO();
        assetDTO.id = asset.getId();
        assetDTO.isbn = asset.getIsbn();
        assetDTO.author = asset.getAuthor();
        assetDTO.title = asset.getName();
        assetDTO.language = asset.getLanguage();
        assetDTO.selfUrl = reference(url, asset);
        return assetDTO;
    }
    public static String reference(UriInfo url, BookImpl asset) {
        return url.getBaseUriBuilder().path("assets").path(String.valueOf(asset.getId())).build().toString();
    }
    public static List<AssetDTO> fromBooks(final List<BookImpl> books, final UriInfo uriInfo) {
        return books.stream().map(book -> fromAsset(uriInfo, book)).collect(java.util.stream.Collectors.toList());
    }

}

