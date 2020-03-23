package nl.sogyo.agoldschmidt_food_matcher.api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

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
import nl.sogyo.agoldschmidt_food_matcher.model.ClientOfferData;
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

    @PostMapping(path="/offerhandler")
    public @ResponseBody Offer offerHandler(@RequestBody ClientOfferData clientOfferData) {
        Address address = createAddress(clientOfferData.getStreetName(), 
                                        clientOfferData.getHouseNumber(), 
                                        clientOfferData.getPostCode(),
                                        clientOfferData.getCity(), 
                                        clientOfferData.getCountry(), 
                                        clientOfferData.getLatitude(), 
                                        clientOfferData.getLongitude());
        User user = findUser(clientOfferData.getUserid());
        Offer offer = createOffer(clientOfferData.getContentType(), 
                    clientOfferData.getContentQuantity(), 
                    clientOfferData.getExpiryDate(), 
                    address, 
                    user);
        Offer[] offerArray = getAllOffersByUser(clientOfferData.getUserid());
        System.out.println("The offer array as it will be send to the client (from inside the offerHandler()): ");
        for (int i = 0; i < offerArray.length; i++) {
            System.out.print(offerArray[i] + " ");
        }
        return offer;
    }

    private Address createAddress(String streetName, String houseNumber, String postCode, String city, String country, Double latitude, Double longitude) {
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
            addressDao.save(address);
        } else {
            address = addressDao.findByLatitudeAndLongitude(latitude, longitude);
        }
        return address;
    }

    private User findUser(Integer userid) {
        ArrayList<User> userList = new ArrayList<>();
        userDao.findById(userid).ifPresent(userList::add);
        User user = userList.get(0);
        return user;
    }

    private Offer createOffer(String contentType, Integer contentQuantity, LocalDate expiryDate, Address address, User user) {
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

    private Offer[] getAllOffersByUser(Integer userid) {
        ArrayList<Offer> offerList = (ArrayList<Offer>) offerDao.findByUserUserid(userid);
        System.out.println("The offer list from the database (via private function getAlOffersByUser()): ");
        System.out.println(offerList);
        Offer[] offerArray = (Offer[]) offerList.toArray();
        return offerArray;
    }

    @PostMapping(path="/demandhandler")
    public @ResponseBody Demand addNewDemand(@RequestBody Demand demand) {
        return null;
    }

}
