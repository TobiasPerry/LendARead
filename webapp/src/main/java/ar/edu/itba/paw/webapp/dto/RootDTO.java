package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.webapp.form.AssetInstanceGetForm;
import ar.edu.itba.paw.webapp.form.AssetsGetForm;
import ar.edu.itba.paw.webapp.form.LendingGetForm;
import ar.edu.itba.paw.webapp.miscellaneous.EndpointsUrl;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.util.UriComponentsBuilder;

import javax.ws.rs.core.UriInfo;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
@Setter
public class RootDTO {

    private  String api_url;
    private  String assets_url;
    private  String asset_instances_url;
    private  String lendings_url;
    private  String locations_url;
    private  String users_url;
    private  String languages_url;
    private String asset_url;
    private String assets_instances_url;
    private String lending_url;
    private String location_url;
    private String language_url;
    private String asset_instance_review_url;
    private String asset_instance_reviews_url;
    private String users_lender_reviews_url;
    private String users_borrower_reviews_url;
    public RootDTO() {
    }

    public static RootDTO fromRoot(UriInfo uriInfo) {
        RootDTO rootDTO = new RootDTO();
        String lendingParams = Arrays.stream(LendingGetForm.class.getDeclaredFields()).map(Field::getName).collect(Collectors.joining(","));
        String assetParams = Arrays.stream(AssetsGetForm.class.getDeclaredFields()).map(Field::getName).collect(Collectors.joining(","));
        String assetInstanceParams = Arrays.stream(AssetInstanceGetForm.class.getDeclaredFields()).map(Field::getName).collect(Collectors.joining(","));
        String paginationParams = "page,itemsPerPage";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(uriInfo.getBaseUriBuilder().build());
        rootDTO.api_url = uriInfo.getBaseUriBuilder().build().toString();
        rootDTO.asset_url = builder.cloneBuilder().path(EndpointsUrl.Assets_URL).path("{id}").build().toString();
        rootDTO.assets_url = builder.cloneBuilder().path(EndpointsUrl.Assets_URL).path("{?"+assetParams+"}").build().toString();
        rootDTO.asset_instances_url = builder.cloneBuilder().path(EndpointsUrl.AssetInstances_URL).path("{/id}").build().toString();
        rootDTO.assets_instances_url = builder.cloneBuilder().path(EndpointsUrl.AssetInstances_URL).path("{?"+assetInstanceParams+"}").build().toString();
        rootDTO.asset_instance_review_url = builder.cloneBuilder().path(EndpointsUrl.AssetInstances_URL).path("{id}/reviews/{/idReview}").build().toString();
        rootDTO.asset_instance_reviews_url = builder.cloneBuilder().path(EndpointsUrl.AssetInstances_URL).path("{id}/reviews").path("{?"+paginationParams+"}").build().toString();
        rootDTO.lending_url = builder.cloneBuilder().path(EndpointsUrl.Lendings_URL).path("{/id}").build().toString();
        rootDTO.lendings_url = builder.cloneBuilder().path(EndpointsUrl.Lendings_URL).path("{?"+lendingParams+"}").build().toString();
        rootDTO.location_url = builder.cloneBuilder().path(EndpointsUrl.Locations_URL).path("{/id}").build().toString();
        rootDTO.locations_url = builder.cloneBuilder().path(EndpointsUrl.Locations_URL).path("{?"+paginationParams+",userId}").build().toString();
        rootDTO.users_url = builder.cloneBuilder().path(EndpointsUrl.Users_URL).path("{/id}").build().toString();
        rootDTO.users_lender_reviews_url = builder.cloneBuilder().path(EndpointsUrl.Users_URL).path("{id}/lender_reviews").path("{?"+paginationParams+"}").build().toString();
        rootDTO.users_borrower_reviews_url = builder.cloneBuilder().path(EndpointsUrl.Users_URL).path("{id}/borrower_reviews").path("{?"+paginationParams+"}").build().toString();
        rootDTO.language_url = builder.cloneBuilder().path(EndpointsUrl.Languages_URL).path("{/code}").build().toString();
        rootDTO.languages_url = builder.cloneBuilder().path(EndpointsUrl.Languages_URL).path("{?"+paginationParams+",userId}").build().toString();
        return rootDTO;
    }


    @Override
    public int hashCode() {
        int result = api_url != null ? api_url.hashCode() : 0;
        result = 31 * result + (assets_url != null ? assets_url.hashCode() : 0);
        result = 31 * result + (asset_instances_url != null ? asset_instances_url.hashCode() : 0);
        result = 31 * result + (lendings_url != null ? lendings_url.hashCode() : 0);
        result = 31 * result + (locations_url != null ? locations_url.hashCode() : 0);
        result = 31 * result + (users_url != null ? users_url.hashCode() : 0);
        result = 31 * result + (languages_url != null ? languages_url.hashCode() : 0);
        result = 31 * result + (asset_url != null ? asset_url.hashCode() : 0);
        result = 31 * result + (assets_instances_url != null ? assets_instances_url.hashCode() : 0);
        result = 31 * result + (lending_url != null ? lending_url.hashCode() : 0);
        result = 31 * result + (location_url != null ? location_url.hashCode() : 0);
        result = 31 * result + (language_url != null ? language_url.hashCode() : 0);
        result = 31 * result + (asset_instance_review_url != null ? asset_instance_review_url.hashCode() : 0);
        result = 31 * result + (asset_instance_reviews_url != null ? asset_instance_reviews_url.hashCode() : 0);
        result = 31 * result + (users_lender_reviews_url != null ? users_lender_reviews_url.hashCode() : 0);
        result = 31 * result + (users_borrower_reviews_url != null ? users_borrower_reviews_url.hashCode() : 0);
        return result;
    }
}
