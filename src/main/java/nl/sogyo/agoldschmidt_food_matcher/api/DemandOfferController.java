package nl.sogyo.agoldschmidt_food_matcher.api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public @ResponseBody ResponseEntity<String> addNewOffer(@RequestParam String contentType, @RequestParam Integer contentQuantity, @RequestParam Integer user_id, @RequestParam String streetName, @RequestParam String houseNumber, @RequestParam String postCode, @RequestParam String city, @RequestParam String country, @RequestParam Double longitude, @RequestParam Double latitude, @RequestParam String expiryDateString) {
        // if (addressDao.findByLongitudeAndLatitude(offer.getAddress().getLongitude(), offer.getAddress().getLatitude()) == null) {
        //     addressDao.save(offer.getAddress());
        // } else {
        //     offer.setAddress(addressDao.findByLongitudeAndLatitude(offer.getAddress().getLongitude(), offer.getAddress().getLatitude()));
        // }
        // offerDao.save(offer);
        Offer offer = new Offer();
        offer.setContentType(contentType);
        offer.setContentQuantity(contentQuantity);
        offer.setAvailable(true);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        LocalDate expiryDate = LocalDate.parse(expiryDateString, formatter);
        offer.setExpiryDate(expiryDate);
        ArrayList<User> userList = new ArrayList<>();
        userDao.findById(user_id).ifPresent(userList::add);
        User user = userList.get(0);
        offer.setUser(user);
        if (addressDao.findByLongitudeAndLatitude(longitude, latitude) == null) {
            Address address = new Address();
            address.setStreetName(streetName);
            address.setHouseNumber(houseNumber);
            address.setPostCode(postCode);
            address.setCity(city);
            address.setCountry(country);
            address.setLongitude(longitude);
            address.setLatitude(latitude);
            addressDao.save(address);
            offer.setAddress(address);
        } else {
            Address address = addressDao.findByLongitudeAndLatitude(longitude, latitude);
            offer.setAddress(address);
        }
        offerDao.save(offer);
        return new ResponseEntity<>("Your offer is posted.", HttpStatus.OK);
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
