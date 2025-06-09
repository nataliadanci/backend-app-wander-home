package demo.repository;

import demo.entity.RealEstate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealEstateRepository extends CrudRepository<RealEstate,Integer> {
}
