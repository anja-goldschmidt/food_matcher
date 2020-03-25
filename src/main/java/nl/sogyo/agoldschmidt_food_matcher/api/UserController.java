package nl.sogyo.agoldschmidt_food_matcher.api;

import nl.sogyo.agoldschmidt_food_matcher.dao.DemandDao;
import nl.sogyo.agoldschmidt_food_matcher.dao.OfferDao;
import nl.sogyo.agoldschmidt_food_matcher.dao.UserDao;
import nl.sogyo.agoldschmidt_food_matcher.model.Demand;
import nl.sogyo.agoldschmidt_food_matcher.model.Offer;
import nl.sogyo.agoldschmidt_food_matcher.model.User;
import nl.sogyo.agoldschmidt_food_matcher.model.ClientData;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="")
public class UserController {
    @Autowired
    private UserDao userDao;

    @Autowired 
    private OfferDao offerDao;

    @Autowired
    private DemandDao demandDao;

    @PostMapping(path="login")
    public @ResponseBody ClientData userHandler (@RequestBody User user) {
        updateDatabaseExpiredOfferDemand();
        user = addNewUser(user);
        Offer[] offerArray = getAllOffersByUser(user.getUserid());
        Demand[] demandArray = getAllDemandsByUser(user.getUserid());
        ClientData clientData = new ClientData();
        clientData.setUser(user);
        clientData.setOfferArray(offerArray);
        clientData.setDemandArray(demandArray);
        return clientData;
    }

    private void updateDatabaseExpiredOfferDemand() {
        LocalDate today = LocalDate.now();
        Offer[] expiredOffers = offerDao.findByAvailableAndExpiryDateLessThan(true, today);
        for (int i = 0; i < expiredOffers.length; i++) {
            expiredOffers[i].setAvailable(false);
            offerDao.save(expiredOffers[i]);
        }
        Demand[] expiredDemands = demandDao.findByAvailableAndExpiryDateLessThan(true, today);
        for (int i = 0; i < expiredDemands.length; i++) {
            expiredDemands[i].setAvailable(false);
            demandDao.save(expiredDemands[i]);
        }
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
        Offer[] offerArray = offerDao.findByAvailableAndUserUserid(true, userid);
        return offerArray;
    }

    private Demand[] getAllDemandsByUser(Integer userid) {
        Demand[] demandArray = demandDao.findByAvailableAndUserUserid(true, userid);
        return demandArray;
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
