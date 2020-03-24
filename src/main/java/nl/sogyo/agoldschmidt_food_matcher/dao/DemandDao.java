package nl.sogyo.agoldschmidt_food_matcher.dao;

import org.springframework.data.repository.CrudRepository;
import nl.sogyo.agoldschmidt_food_matcher.model.Demand;

public interface DemandDao extends CrudRepository<Demand, Integer> {
    Demand[] findByUserUserid(Integer userid);
}