package demo.repository;

import demo.entity.Rental;
import demo.entity.Review;
import demo.entity.SwitchBooking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SwitchBookingRepository extends CrudRepository<SwitchBooking,Integer> {

}
