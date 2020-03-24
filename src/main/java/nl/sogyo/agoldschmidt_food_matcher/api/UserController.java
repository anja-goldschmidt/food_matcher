package nl.sogyo.agoldschmidt_food_matcher.api;

import nl.sogyo.agoldschmidt_food_matcher.dao.OfferDao;
import nl.sogyo.agoldschmidt_food_matcher.dao.UserDao;
import nl.sogyo.agoldschmidt_food_matcher.model.Demand;
import nl.sogyo.agoldschmidt_food_matcher.model.Offer;
import nl.sogyo.agoldschmidt_food_matcher.model.User;
import nl.sogyo.agoldschmidt_food_matcher.model.UserData;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="")
public class UserController {
    @Autowired
    private UserDao userDao;

    @Autowired OfferDao offerDao;

    // private DemandOfferController demandOfferController;

    @PostMapping(path="login")
    public @ResponseBody UserData userHandler (@RequestBody User user) {
        user = addNewUser(user);
        Offer[] offerArray = getAllOffersByUser(user.getUserid());
        UserData userData = new UserData();
        userData.setUser(user);
        userData.setOfferArray(offerArray);
        // userData.setDemandArray(demandArray);
        return userData;
    }

    private User addNewUser(User user) {
        if (userDao.findByEmailIgnoreCase(user.getEmail()) == null) {
            userDao.save(user);
            return user;
        } else if (userDao.findByEmailIgnoreCase(user.getEmail()) != null && userDao.findByEmailIgnoreCase(user.getEmail()).getPassword().equals(user.getPassword())) {
            return userDao.findByEmailIgnoreCase(user.getEmail());
        } else {
            return null;
        }
    }

    private Offer[] getAllOffersByUser(Integer userid) {
        Offer[] offerArray = offerDao.findByUserUserid(userid);
        return offerArray;
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
