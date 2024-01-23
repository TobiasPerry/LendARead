package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.webapp.miscellaneous.EndpointsUrl;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RootDTO {

    private  String API_URL = EndpointsUrl.ROOT_URL;
    private  String ASSETS_URL = EndpointsUrl.Assets_URL + "{/id}";
    private  String ASSET_INSTANCES_URL = EndpointsUrl.AssetInstances_URL + "{/id}";
    private  String LENDINGS_URL = EndpointsUrl.Lendings_URL + "{/id}";
    private  String LOCATIONS_URL = EndpointsUrl.Locations_URL + "{/id}";
    private  String USERS_URL = EndpointsUrl.Users_URL + "{/id}";
    private  String LANGUAGES_URL = EndpointsUrl.Languages_URL;

    public RootDTO() {
    }
}
