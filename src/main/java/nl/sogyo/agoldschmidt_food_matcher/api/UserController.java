package nl.sogyo.agoldschmidt_food_matcher.api;

import nl.sogyo.agoldschmidt_food_matcher.dao.UserDao;
import nl.sogyo.agoldschmidt_food_matcher.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="")
public class UserController {
    @Autowired
    private UserDao userDao;

    @PostMapping(path="login")
    public @ResponseBody User addNewUser (@RequestBody String name, @RequestBody String email, @RequestBody String password) {
        if (userDao.findByEmail(email).size() == 0) {
            User newUser = new User();
            newUser.setName(name);
            newUser.setEmail(email);
            newUser.setPassword(password);
            userDao.save(newUser);
            return newUser;
        } else if (userDao.findByEmail(email).size() > 0 && userDao.findByEmail(email).get(0).getPassword().equals(password)) {
            return userDao.findByEmail(email).get(0);
        } else {
            return null;
        }
    }

    @GetMapping(path="/admin")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userDao.findAll();
    }

}
