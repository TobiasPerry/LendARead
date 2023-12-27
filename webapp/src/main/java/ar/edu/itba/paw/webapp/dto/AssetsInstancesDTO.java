package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstance;
import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.core.UriInfo;
import java.util.List;

@Getter
@Setter
public class AssetsInstancesDTO {
    private int id;

    private String assetReference;

    private  String status;

    private  String userReference;

    private  String locationReference;

    private  String description;

    private  String physicalCondition;

    private String reviewsReference;

    private  int maxLendingDays;

    private  boolean isReservable;

    private String imageReference;

    private Integer rating;

    private String selfUrl;


    public static AssetsInstancesDTO fromAssetInstance(UriInfo url, AssetInstance assetInstance) {
        AssetsInstancesDTO dto = new AssetsInstancesDTO();
        dto.id = assetInstance.getId();
        dto.assetReference =  AssetDTO.reference(url, assetInstance.getBook());
        dto.status = assetInstance.getAssetState().toString();
        dto.userReference = UserDTO.reference(url, assetInstance.getOwner());
        dto.locationReference = LocationDTO.reference(url, assetInstance.getLocation());
        dto.description = assetInstance.getDescription();
        dto.physicalCondition = assetInstance.getPhysicalCondition().toString();
        dto.rating = assetInstance.getRating();
        dto.maxLendingDays = assetInstance.getMaxDays();
        dto.isReservable = assetInstance.getIsReservable();
        dto.selfUrl = reference(url, assetInstance);
        dto.reviewsReference = url.getBaseUriBuilder().path("/api/assetInstances").path(String.valueOf(assetInstance.getId())).path("reviews").build().toString();
        dto.imageReference = url.getBaseUriBuilder().path("/api/assetInstances").path(String.valueOf(assetInstance.getId())).path("image").build().toString();
        return dto;
    }
    public static List<AssetsInstancesDTO> fromAssetInstanceList(UriInfo url, List<AssetInstance> assetInstances) {
       return assetInstances.stream().map(assetInstance -> fromAssetInstance(url, assetInstance)).collect(java.util.stream.Collectors.toList());
    }
    public static String reference(UriInfo url, AssetInstance assetInstance) {
        return url.getBaseUriBuilder().path("/api/assetInstances").path(String.valueOf(assetInstance.getId())).build().toString();
    }
}
