package nl.sogyo.agoldschmidt_food_matcher.dao;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;
import nl.sogyo.agoldschmidt_food_matcher.model.Offer;

public interface OfferDao extends CrudRepository<Offer, Integer> {
    Offer[] findByUserUserid(Integer userid);
    Offer[] findByAvailableAndUserUserid(Boolean available, Integer userid);
    Offer[] findByAvailableAndContentTypeIgnoreCaseAndContentQuantityGreaterThanEqual(Boolean available, String contenttype, Integer contentquantity);
    Offer[] findByAvailableAndExpiryDateLessThan(Boolean available, LocalDate expirydate);
}