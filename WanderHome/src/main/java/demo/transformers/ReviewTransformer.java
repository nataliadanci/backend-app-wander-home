package demo.transformers;

import demo.dto.ReviewDTO;
import demo.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewTransformer implements Transformer<Review, ReviewDTO>{

    @Override
    public ReviewDTO fromEntity(Review review){

        ReviewDTO reviewDTO = new ReviewDTO();

        reviewDTO.setReviewId(review.getReviewId());
        reviewDTO.setRating(review.getRating());
        reviewDTO.setComment(review.getComment());

        return reviewDTO;
    }

    @Override
    public Review fromDTO(ReviewDTO reviewDTO){

        Review reviewEntity = new Review();

        reviewEntity.setReviewId(reviewDTO.getReviewId());
        reviewEntity.setRating(reviewDTO.getRating());
        reviewEntity.setComment(reviewDTO.getComment());

        return reviewEntity;
    }
}
