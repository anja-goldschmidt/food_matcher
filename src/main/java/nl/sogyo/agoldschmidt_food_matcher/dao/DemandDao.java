package nl.sogyo.agoldschmidt_food_matcher.dao;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;
import nl.sogyo.agoldschmidt_food_matcher.model.Demand;

public interface DemandDao extends CrudRepository<Demand, Integer> {
    Demand[] findByUserUserid(Integer userid);
    Demand[] findByAvailableAndUserUserid(Boolean available, Integer userid);
    Demand[] findByAvailableAndExpiryDateLessThan(Boolean available, LocalDate expirydate);
}