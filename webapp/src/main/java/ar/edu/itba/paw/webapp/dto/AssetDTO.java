package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.Asset;
import ar.edu.itba.paw.webapp.miscellaneous.EndpointsUrl;
import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.core.UriInfo;
import java.util.List;

@Getter
@Setter
public class AssetDTO {
    private String isbn;
    private String author;
    private String title;
    private String language;
    private String selfUrl;
    private Long id;

    public static AssetDTO fromAsset(UriInfo url, Asset asset) {
        AssetDTO assetDTO = new AssetDTO();
        assetDTO.isbn = asset.getIsbn();
        assetDTO.author = asset.getAuthor();
        assetDTO.title = asset.getName();
        assetDTO.language = LanguagesDTO.reference(url,asset.getLanguage());
        assetDTO.selfUrl = reference(url, asset);
        assetDTO.id = asset.getId();
        return assetDTO;
    }
    public static String reference(UriInfo url, Asset asset) {
        return url.getBaseUriBuilder().path(EndpointsUrl.Assets_URL).path(String.valueOf(asset.getId())).build().toString();
    }
    public static List<AssetDTO> fromBooks(final List<Asset> books, final UriInfo uriInfo) {
        return books.stream().map(book -> fromAsset(uriInfo, book)).collect(java.util.stream.Collectors.toList());
    }

}

