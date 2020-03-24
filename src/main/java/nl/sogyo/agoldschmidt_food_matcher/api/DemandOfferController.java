package nl.sogyo.agoldschmidt_food_matcher.api;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.sogyo.agoldschmidt_food_matcher.dao.AddressDao;
import nl.sogyo.agoldschmidt_food_matcher.dao.DemandDao;
import nl.sogyo.agoldschmidt_food_matcher.dao.OfferDao;
import nl.sogyo.agoldschmidt_food_matcher.dao.UserDao;
import nl.sogyo.agoldschmidt_food_matcher.model.Address;
import nl.sogyo.agoldschmidt_food_matcher.model.ClientData;
import nl.sogyo.agoldschmidt_food_matcher.model.ClientDemandData;
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
    public @ResponseBody ClientData offerHandler(@RequestBody ClientOfferData clientOfferData) {
        Address address = createAddress(clientOfferData.getStreetName(), 
                                        clientOfferData.getHouseNumber(), 
                                        clientOfferData.getPostCode(),
                                        clientOfferData.getCity(), 
                                        clientOfferData.getCountry(), 
                                        clientOfferData.getLatitude(), 
                                        clientOfferData.getLongitude());
        User user = findUser(clientOfferData.getUserid());
        createOffer(clientOfferData.getContentType(), 
                    clientOfferData.getContentQuantity(), 
                    clientOfferData.getExpiryDate(), 
                    address, 
                    user);
        Offer[] offerArray = getAllOffersByUser(clientOfferData.getUserid());
        Demand[] demandArray = getAllDemandsByUser(clientOfferData.getUserid());
        ClientData clientData = new ClientData();
        clientData.setUser(user);
        clientData.setOfferArray(offerArray);
        clientData.setDemandArray(demandArray);
        return clientData;
    }

    @PostMapping(path="/demandhandler")
    public @ResponseBody ClientData addNewDemand(@RequestBody ClientDemandData clientDemandData) {
        Address address = createAddress(clientDemandData.getStreetName(),
                        clientDemandData.getHouseNumber(),
                        clientDemandData.getPostCode(),
                        clientDemandData.getCity(),
                        clientDemandData.getCountry(),
                        clientDemandData.getLatitude(),
                        clientDemandData.getLongitude());
        User user = findUser(clientDemandData.getUserid());
        createDemand(clientDemandData.getContentType(),
                    clientDemandData.getContentQuantity(),
                    clientDemandData.getExpiryDate(),
                    address,
                    clientDemandData.getDistance(),
                    user);
        Offer[] offerArray = getAllOffersByUser(clientDemandData.getUserid());
        Demand[] demandArray = getAllDemandsByUser(clientDemandData.getUserid());
        ClientData clientData = new ClientData();
        clientData.setUser(user);
        clientData.setOfferArray(offerArray);
        clientData.setDemandArray(demandArray);
        return clientData;
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

    private void createOffer(String contentType, Integer contentQuantity, LocalDate expiryDate, Address address, User user) {
        Offer offer = new Offer();
        offer.setContentType(contentType);
        offer.setContentQuantity(contentQuantity);
        offer.setExpiryDate(expiryDate);
        offer.setAvailable(true);
        offer.setAddress(address);
        offer.setUser(user);
        offerDao.save(offer);
    }

    private Offer[] getAllOffersByUser(Integer userid) {
        Offer[] offerArray = offerDao.findByUserUserid(userid);
        return offerArray;
    }

    private void createDemand(String contentType, Integer contentQuantity, LocalDate expiryDate, Address address, Integer distance, User user) {
        Demand demand = new Demand();
        demand.setContentType(contentType);
        demand.setContentQuantity(contentQuantity);
        demand.setExpiryDate(expiryDate);
        demand.setAvailable(true);
        demand.setAddress(address);
        demand.setDistance(distance);
        demand.setUser(user);
        demandDao.save(demand);
    }

    private Demand[] getAllDemandsByUser(Integer userid) {
        Demand[] demandArray = demandDao.findByUserUserid(userid);
        return demandArray;
    }

}
