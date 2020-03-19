package nl.sogyo.agoldschmidt_food_matcher.dao;

import org.springframework.data.repository.CrudRepository;
import nl.sogyo.agoldschmidt_food_matcher.model.Offer;

public interface OfferDao extends CrudRepository<Offer, Integer> {
    
}