package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingImpl;
import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class LendingDTO {

    private Long id;

    private String assetInstance;

    private String userReference;

    private String lendDate;

    private String devolutionDate;

    private List<String> userReviews;

    private String assetInstanceReview;

    public static LendingDTO fromLending(LendingImpl lending, UriInfo url) {
        final LendingDTO dto = new LendingDTO();
        dto.id = lending.getId();
        dto.assetInstance = AssetsInstancesDTO.reference(url, lending.getAssetInstance());
        dto.userReference = UserDTO.reference(url, lending.getUserReference());
        dto.lendDate = lending.getLendDate().toString();
        dto.devolutionDate = lending.getDevolutionDate().toString();
        dto.userReviews = lending.getUserReviews().stream().map(userReview -> UserReviewsDTO.reference(url, userReview)).collect(Collectors.toList());
        dto.assetInstanceReview = AssetInstanceReviewDTO.reference(url, lending.getAssetInstanceReview());
        return dto;
    }
    public static List<LendingDTO> fromLendings(List<LendingImpl> lendings, UriInfo url) {
        return lendings.stream().map(lending -> fromLending(lending, url)).collect(Collectors.toList());
    }


    public static String reference(UriInfo url, LendingImpl lending) {
        return url.getBaseUriBuilder().path("lending").path(String.valueOf(lending.getId())).build().toString();
    }
}
