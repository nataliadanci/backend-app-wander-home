package demo.repository;

import demo.entity.Client;
import demo.entity.Rental;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RentalRepository extends CrudRepository<Rental,Integer> {
    Set<Rental> findByRentalRealEstateId(Integer realEstateId);
}
