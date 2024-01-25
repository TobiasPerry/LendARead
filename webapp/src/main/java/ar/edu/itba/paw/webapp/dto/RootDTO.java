package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.webapp.miscellaneous.EndpointsUrl;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.util.UriComponentsBuilder;

import javax.ws.rs.core.UriInfo;

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
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(uriInfo.getBaseUriBuilder().build());
        rootDTO.api_url = uriInfo.getBaseUriBuilder().build().toString();
        rootDTO.asset_url = builder.cloneBuilder().path(EndpointsUrl.Assets_URL).path("{id}").build().toString();
        rootDTO.assets_url = builder.cloneBuilder().path(EndpointsUrl.Assets_URL).path("{?page,itemsPerPage,isbn,author,title,language}").build().toString();
        rootDTO.asset_instances_url = builder.cloneBuilder().path(EndpointsUrl.AssetInstances_URL).path("{/id}").build().toString();
        rootDTO.assets_instances_url = builder.cloneBuilder().path(EndpointsUrl.AssetInstances_URL).path("{?page,itemsPerPage,search,physicalCondition,status,languages,sort,sortDirection,minRating,maxRating,userId}").build().toString();
        rootDTO.asset_instance_review_url = builder.cloneBuilder().path(EndpointsUrl.AssetInstances_URL).path("{id}/reviews/{/idReview}").build().toString();
        rootDTO.asset_instance_reviews_url = builder.cloneBuilder().path(EndpointsUrl.AssetInstances_URL).path("{id}/reviews").path("{?page,itemsPerPage}").build().toString();
        rootDTO.lending_url = builder.cloneBuilder().path(EndpointsUrl.Lendings_URL).path("{/id}").build().toString();
        rootDTO.lendings_url = builder.cloneBuilder().path(EndpointsUrl.Lendings_URL).path("{?page,itemsPerPage,sort,sortDirection,lenderId,borrowerId,assetInstanceId,state}").build().toString();
        rootDTO.location_url = builder.cloneBuilder().path(EndpointsUrl.Locations_URL).path("{/id}").build().toString();
        rootDTO.locations_url = builder.cloneBuilder().path(EndpointsUrl.Locations_URL).path("{?page,itemsPerPage,userId}").build().toString();
        rootDTO.users_url = builder.cloneBuilder().path(EndpointsUrl.Users_URL).path("{/id}").build().toString();
        rootDTO.users_lender_reviews_url = builder.cloneBuilder().path(EndpointsUrl.Users_URL).path("{id}/lender_reviews").path("{?page,itemsPerPage}").build().toString();
        rootDTO.users_borrower_reviews_url = builder.cloneBuilder().path(EndpointsUrl.Users_URL).path("{id}/borrower_reviews").path("{?page,itemsPerPage}").build().toString();
        rootDTO.language_url = builder.cloneBuilder().path(EndpointsUrl.Languages_URL).path("{/code}").build().toString();
        rootDTO.languages_url = builder.cloneBuilder().path(EndpointsUrl.Languages_URL).path("{?page,itemsPerPage,isUsed}").build().toString();
        return rootDTO;
    }
}
