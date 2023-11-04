package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceReview;
import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AssetInstanceReviewDTO {

        private int rating;
        private String review;
        private String lendingReference;
        private String reviewer;

        public static AssetInstanceReviewDTO fromAssetInstanceReview(final AssetInstanceReview review, final UriInfo uriInfo) {
            final AssetInstanceReviewDTO dto = new AssetInstanceReviewDTO();
            dto.setRating(review.getRating());
            dto.setReview(review.getReview());
            dto.setLendingReference(LendingDTO.reference(uriInfo,review.getLending()));
            dto.setReviewer(UserDTO.reference(uriInfo,review.getReviewer()));
            return dto;
        }
        public static List<AssetInstanceReviewDTO> fromAssetInstanceReviews(final List<AssetInstanceReview> reviews, final UriInfo uriInfo) {
            List<AssetInstanceReviewDTO> toReturn = new ArrayList<AssetInstanceReviewDTO>();
            for (AssetInstanceReview review : reviews) {
                toReturn.add(fromAssetInstanceReview(review,uriInfo));
            }
            return toReturn;
        }



}
