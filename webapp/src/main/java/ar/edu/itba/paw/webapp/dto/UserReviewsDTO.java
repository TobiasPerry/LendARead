package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.userContext.UserReview;
import ar.edu.itba.paw.webapp.miscellaneous.EndpointsUrl;
import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.core.UriInfo;
import java.util.List;

@Setter
@Getter
public class UserReviewsDTO {

      private String review ;
      private int rating;
      private String reviewer;
      private String lending ;
      private String selfUrl;
      private Long id;

      public static UserReviewsDTO fromUserReview(UserReview userReview, UriInfo url) {
            UserReviewsDTO dto = new UserReviewsDTO();
            dto.review = userReview.getReview();
            dto.rating = userReview.getRating();
            dto.reviewer = UserDTO.reference(url, userReview.getReviewer());
            dto.lending = LendingDTO.reference(url, userReview.getLending());
            dto.selfUrl = reference(url, userReview);
            dto.id = userReview.getId();
            return dto;
      }
      public static List<UserReviewsDTO> fromUserReviewsList(List<UserReview> userReviews, UriInfo uriInfo) {
          return userReviews.stream().map(userReview -> fromUserReview(userReview, uriInfo)).collect(java.util.stream.Collectors.toList());
      }
      public static String reference(UriInfo url, UserReview userReview) {
            return url.getBaseUriBuilder().path(EndpointsUrl.Users_URL).path(String.valueOf(userReview.getRecipient().getId())).path(userReview.isBorrowerReview()?"borrower_reviews":"lender_reviews").path(String.valueOf(userReview.getId())).build().toString();
      }

}
