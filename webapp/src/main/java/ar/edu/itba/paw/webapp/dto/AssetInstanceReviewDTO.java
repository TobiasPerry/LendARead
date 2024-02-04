package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.assetExistanceContext.AssetInstanceReview;
import ar.edu.itba.paw.webapp.miscellaneous.EndpointsUrl;
import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.core.UriInfo;
import java.util.List;

@Getter
@Setter
public class AssetInstanceReviewDTO {

        private int rating;
        private String review;
        private String lendingReference;
        private String reviewer;
        private String selfUrl;
        private Long id;
        public static AssetInstanceReviewDTO fromAssetInstanceReview(final AssetInstanceReview review, final UriInfo uriInfo) {
            final AssetInstanceReviewDTO dto = new AssetInstanceReviewDTO();
            dto.setRating(review.getRating());
            dto.setReview(review.getReview());
            dto.setLendingReference(LendingDTO.reference(uriInfo,review.getLending()));
            dto.setReviewer(UserDTO.reference(uriInfo,review.getReviewer()));
            dto.setSelfUrl(reference(uriInfo,review));
            dto.setId(review.getId());
            return dto;
        }
        public static List<AssetInstanceReviewDTO> fromAssetInstanceReviews(final List<AssetInstanceReview> reviews, final UriInfo uriInfo) {
          return reviews.stream().map(review -> fromAssetInstanceReview(review, uriInfo)).collect(java.util.stream.Collectors.toList());
        }
        public static String reference(final UriInfo uriInfo, final AssetInstanceReview review) {
            return uriInfo.getBaseUriBuilder().path(EndpointsUrl.AssetInstances_URL).path(String.valueOf(review.getLending().getAssetInstance().getId())).path("reviews").path(String.valueOf(review.getId())).build().toString();
        }


}
