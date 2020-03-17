package nl.sogyo.agoldschmidt_food_matcher.dao;

import org.springframework.data.repository.CrudRepository;
import nl.sogyo.agoldschmidt_food_matcher.model.User;

import java.util.List;

public interface UserDao extends CrudRepository<User, Integer> {
    User findByEmail(String email);
}
