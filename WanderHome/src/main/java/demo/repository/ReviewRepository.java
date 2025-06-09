package demo.repository;

import demo.entity.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review,Integer> {

    boolean existsByClientIdAndRealEstateId(Integer clientId, Integer realEstateId);

}
