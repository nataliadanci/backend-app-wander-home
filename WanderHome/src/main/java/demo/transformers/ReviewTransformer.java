package demo.transformers;

import demo.dto.ReviewDTO;
import demo.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewTransformer implements Transformer<Review, ReviewDTO>{

    @Override
    public ReviewDTO fromEntity(Review review){

        ReviewDTO reviewDTO = new ReviewDTO();

        reviewDTO.setId(review.getId());
        reviewDTO.setRating(review.getRating());
        reviewDTO.setComment(review.getComment());
        reviewDTO.setDateAdded(review.getDateAdded());
        reviewDTO.setClientId(review.getClient().getId());
        reviewDTO.setClientName(review.getClient().getFirstName() + " " + review.getClient().getLastName());
        reviewDTO.setRealEstateId(review.getRealEstate().getId());

        return reviewDTO;
    }

    @Override
    public Review fromDTO(ReviewDTO reviewDTO){

        Review reviewEntity = new Review();

        reviewEntity.setId(reviewDTO.getId());
        reviewEntity.setRating(reviewDTO.getRating());
        reviewEntity.setComment(reviewDTO.getComment());
        reviewEntity.setDateAdded(reviewDTO.getDateAdded());
        //when we use this transformer we need to perform an additional operation after we use it
        //it needs to retrieve the client and real estate objects by ids and set them in the review object

        return reviewEntity;
    }
}
