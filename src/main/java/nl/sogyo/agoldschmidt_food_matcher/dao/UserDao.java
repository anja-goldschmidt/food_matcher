package nl.sogyo.agoldschmidt_food_matcher.dao;

import org.springframework.data.repository.CrudRepository;
import nl.sogyo.agoldschmidt_food_matcher.model.User;

public interface UserDao extends CrudRepository<User, Integer> {
    User findByEmail(String email);
    User findByUser_id(Integer user_id);
}
