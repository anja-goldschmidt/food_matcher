package nl.sogyo.agoldschmidt_food_matcher.api;

import nl.sogyo.agoldschmidt_food_matcher.dao.UserDao;
import nl.sogyo.agoldschmidt_food_matcher.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/")
public class UserController {
    @Autowired
    private UserDao userDao;

    @PostMapping(path="/login")
    public @ResponseBody String addNewUser (@RequestParam String name, @RequestParam String email, @RequestParam String password) {
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);
        userDao.save(newUser);
        return "User created in Database";
    }

    @GetMapping(path="/admin")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userDao.findAll();
    }

}
