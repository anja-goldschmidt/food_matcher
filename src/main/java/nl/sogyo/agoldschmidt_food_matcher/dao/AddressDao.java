package nl.sogyo.agoldschmidt_food_matcher.dao;

import org.springframework.data.repository.CrudRepository;
import nl.sogyo.agoldschmidt_food_matcher.model.Address;

public interface AddressDao extends CrudRepository<Address, Integer> {
    Address findByLongitudeAndLatitude(Double longitude, Double latitude);
}
