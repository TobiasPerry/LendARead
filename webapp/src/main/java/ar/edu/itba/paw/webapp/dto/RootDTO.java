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


    public RootDTO(UriInfo uriInfo) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(uriInfo.getBaseUriBuilder().build());
        api_url = uriInfo.getBaseUriBuilder().build().toString();
        assets_url = builder.cloneBuilder().path(EndpointsUrl.Assets_URL).path("{/id}").build().toString();
        asset_instances_url = builder.cloneBuilder().path(EndpointsUrl.AssetInstances_URL).path("{/id}").build().toString();
        lendings_url = builder.cloneBuilder().path(EndpointsUrl.Lendings_URL).path("{/id}").build().toString();
        locations_url = builder.cloneBuilder().path(EndpointsUrl.Locations_URL).path("{/id}").build().toString();
        users_url = builder.cloneBuilder().path(EndpointsUrl.Users_URL).path("{/id}").build().toString();
        languages_url = builder.cloneBuilder().path(EndpointsUrl.Languages_URL).path("{/code}").build().toString();
    }
}
