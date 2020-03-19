package nl.sogyo.agoldschmidt_food_matcher.api;

import nl.sogyo.agoldschmidt_food_matcher.dao.UserDao;
import nl.sogyo.agoldschmidt_food_matcher.model.User;

import java.util.ArrayList;

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
        if (userDao.findByEmail(user.getEmail()) == null) {
            userDao.save(user);
            return user;
        } else if (userDao.findByEmail(user.getEmail()) != null && userDao.findByEmail(user.getEmail()).getPassword().equals(user.getPassword())) {
            return userDao.findByEmail(user.getEmail());
        } else {
            return null;
        }
    }

    @GetMapping(path="/adminUser")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userDao.findAll();
    }

    @GetMapping(path="/adminUser1")
    public @ResponseBody User getUserById(@RequestParam Integer id) {
        ArrayList<User> userList = new ArrayList<>();
        userDao.findById(id).ifPresent(userList::add);
        return userList.get(0);
    }

}
