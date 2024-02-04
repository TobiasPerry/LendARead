package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.assetLendingContext.Lending;
import ar.edu.itba.paw.webapp.miscellaneous.EndpointsUrl;
import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class LendingDTO {


    private String assetInstance;

    private String borrowerUrl;

    private String lenderUrl;

    private String lendDate;
    private String devolutionDate;
    private List<String> userReviews;
    private String assetInstanceReview;
    private String state;
    private String borrowerReviewUrl;
    private String lenderReviewUrl;
    private String selfUrl;
    private Long id;
    public static LendingDTO fromLending(Lending lending, UriInfo url) {
        final LendingDTO dto = new LendingDTO();
        dto.assetInstance = AssetsInstancesDTO.reference(url, lending.getAssetInstance());
        dto.borrowerUrl = UserDTO.reference(url, lending.getUserReference());
        dto.lenderUrl = UserDTO.reference(url, lending.getAssetInstance().getOwner());
        dto.lendDate = lending.getLendDate().toString();
        dto.devolutionDate = lending.getDevolutionDate().toString();
        dto.state = lending.getActive().toString();
        if (lending.getUserReviews() != null)
          lending.getUserReviews().forEach(userReview -> {
              if (userReview.isBorrowerReview())
                    dto.borrowerReviewUrl = UserReviewsDTO.reference(url, userReview);
                else
                    dto.lenderReviewUrl = UserReviewsDTO.reference(url, userReview);
          });
        else dto.userReviews = null;
        if (lending.getAssetInstanceReview() != null)
            dto.assetInstanceReview = AssetInstanceReviewDTO.reference(url, lending.getAssetInstanceReview());
        else dto.assetInstanceReview = null;
        dto.selfUrl = reference(url, lending);
        dto.id = lending.getId();
        return dto;
    }
    public static List<LendingDTO> fromLendings(List<Lending> lendings, UriInfo url) {
        return lendings.stream().map(lending -> fromLending(lending, url)).collect(Collectors.toList());
    }


    public static String reference(UriInfo url, Lending lending) {
        return url.getBaseUriBuilder().path(EndpointsUrl.Lendings_URL).path(String.valueOf(lending.getId())).build().toString();
    }
}
