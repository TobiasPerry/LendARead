package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.userContext.implementations.UserReview;
import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class UserReviewsDTO {

      private String review ;
      private int rating;
      private String reviewer;
      private String lending ;

      public static UserReviewsDTO fromUserReview(UserReview userReview, UriInfo url) {
            UserReviewsDTO dto = new UserReviewsDTO();
            dto.review = userReview.getReview();
            dto.rating = userReview.getRating();
            dto.reviewer = UserDTO.reference(url, userReview.getReviewer());
            dto.lending = LendingDTO.reference(url, userReview.getLending());
            return dto;
      }
      public static List<UserReviewsDTO> fromUserReviewsList(List<UserReview> userReviews, UriInfo uriInfo) {
          List<UserReviewsDTO> toReturn = new ArrayList<>();
            for (UserReview userReview : userReviews) {
                toReturn.add(fromUserReview(userReview, uriInfo));
            }
            return toReturn;
      }

}
