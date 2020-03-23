package nl.sogyo.agoldschmidt_food_matcher.api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
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

    @PostMapping(path="/handler")
    public @ResponseBody Offer offerHandler(
        @RequestBody String contentType,
        @RequestBody Integer contentQuantity,
        @RequestBody String expiryDate,
        @RequestBody String streetName,
        @RequestBody String houseNumber,
        @RequestBody String postCode,
        @RequestBody String city,
        @RequestBody String country,
        @RequestBody Double latitude,
        @RequestBody Double longitude,
        @RequestBody Integer user_id
    ) {
        Address address = createAddress(streetName, houseNumber, postCode, city, country, latitude, longitude);
        User user = findUser(user_id);
        Offer offer = createOffer(contentType, contentQuantity, expiryDate, address, user);
        return offer;
    }

    public Address createAddress(String streetName, String houseNumber, String postCode, String city, String country, Double latitude, Double longitude) {
        Address address;
        if (addressDao.findByLatitudeAndLongitude(latitude, longitude) == null) {
            address = new Address();
            address.setStreetName(streetName);
            address.setHouseNumber(houseNumber);
            address.setPostCode(postCode);
            address.setCity(city);
            address.setCountry(country);
            address.setLatitude(latitude);
            address.setLongitude(longitude);
        } else {
            address = addressDao.findByLatitudeAndLongitude(latitude, longitude);
        }
        return address;
    }

    public User findUser(Integer user_id) {
        ArrayList<User> userList = new ArrayList<>();
        userDao.findById(user_id).ifPresent(userList::add);
        User user = userList.get(0);
        return user;
    }

    public Offer createOffer(String contentType, Integer contentQuantity, String expiryDate, Address address, User user) {
        Offer offer = new Offer();
        offer.setContentType(contentType);
        offer.setContentQuantity(contentQuantity);
        offer.setExpiryDate(expiryDate);
        offer.setAvailable(true);
        offer.setAddress(address);
        offer.setUser(user);
        offerDao.save(offer);
        return offer;
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
