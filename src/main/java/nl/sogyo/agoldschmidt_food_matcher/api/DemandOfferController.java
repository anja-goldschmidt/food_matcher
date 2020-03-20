package nl.sogyo.agoldschmidt_food_matcher.api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.sogyo.agoldschmidt_food_matcher.dao.AddressDao;
import nl.sogyo.agoldschmidt_food_matcher.dao.DemandDao;
import nl.sogyo.agoldschmidt_food_matcher.dao.OfferDao;
import nl.sogyo.agoldschmidt_food_matcher.dao.UserDao;
import nl.sogyo.agoldschmidt_food_matcher.model.Address;
import nl.sogyo.agoldschmidt_food_matcher.model.Demand;
import nl.sogyo.agoldschmidt_food_matcher.model.Offer;
import nl.sogyo.agoldschmidt_food_matcher.model.User;

@Controller
@RequestMapping(path="profilepage")
public class DemandOfferController {
    @Autowired
    private DemandDao demandDao;

    @Autowired
    private OfferDao offerDao;

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private UserDao userDao;

    @PostMapping(path="/offer")
    public @ResponseBody Offer addNewOffer(@RequestBody Offer offer) {
        offer.setAvailable(true);
        if (addressDao.findByLatitudeAndLongitude(offer.getAddress().getLatitude(), offer.getAddress().getLongitude()) == null) {
            addressDao.save(offer.getAddress());
        } else {
            offer.setAddress(addressDao.findByLatitudeAndLongitude(offer.getAddress().getLatitude(), offer.getAddress().getLongitude()));
        }
        ArrayList<User> userList = new ArrayList<>();
        userDao.findById(offer.getUser().getUser_id()).ifPresent(userList::add);
        User user = userList.get(0);
        offer.setUser(user);
        offerDao.save(offer);
        return offer;
        // Offer offer = new Offer();
        // offer.setContentType(contentType);
        // offer.setContentQuantity(Integer.valueOf(contentQuantity));
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        // LocalDate expiryDate = LocalDate.parse(expiryDateString, formatter);
        // offer.setExpiryDate(expiryDate);
        // offer.setAvailable(true);
        // if (addressDao.findByLatitudeAndLongitude(Double.valueOf(latitude), Double.valueOf(longitude)) == null) {
        //     Address address = new Address();
        //     address.setStreetName(streetName);
        //     address.setHouseNumber(houseNumber);
        //     address.setPostCode(postCode);
        //     address.setCity(city);
        //     address.setCountry(country);
        //     address.setLongitude(Double.valueOf(longitude));
        //     address.setLatitude(Double.valueOf(latitude));
        //     addressDao.save(address);
        //     offer.setAddress(address);
        // } else {
        //     Address address = addressDao.findByLatitudeAndLongitude(Double.valueOf(latitude), Double.valueOf(longitude));
        //     offer.setAddress(address);
        // }
        // ArrayList<User> userList = new ArrayList<>();
        // userDao.findById(user_id).ifPresent(userList::add);
        // User user = userList.get(0);
        // offer.setUser(user);
        // offerDao.save(offer);
        // return offer;
    }

    @PostMapping(path="/demand")
    public @ResponseBody Demand addNewDemand(@RequestBody Demand demand) {
        return null;
    }

    @GetMapping(path="/adminDemand")
    public @ResponseBody Iterable<Demand> getAllDemands() {
        return demandDao.findAll();
    }

    @GetMapping(path="/adminOffer")
    public @ResponseBody Iterable<Offer> getAllOffers() {
        return offerDao.findAll();
    }

}
