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
    public @ResponseBody User addNewUser (@RequestBody User user) {
        if (userDao.findByEmail(user.getEmail()).size() == 0) {
            User newUser = new User();
            newUser.setName(user.getName());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());
            userDao.save(newUser);
            return newUser;
        } else if (userDao.findByEmail(user.getEmail()).size() > 0 && userDao.findByEmail(user.getEmail()).get(0).getPassword().equals(user.getPassword())) {
            return userDao.findByEmail(user.getEmail()).get(0);
        } else {
            return null;
        }
    }

    @GetMapping(path="/admin")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userDao.findAll();
    }

}
