package nl.sogyo.agoldschmidt_food_matcher.api;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.sogyo.agoldschmidt_food_matcher.dao.*;
import nl.sogyo.agoldschmidt_food_matcher.model.*;

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

    private LocationMatcher locationMatcher = new LocationMatcher();

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
        updateDatabaseExpiredOfferDemand();
        Offer[] offerArray = getAllOffersByUser(clientOfferData.getUserid());
        Demand[] demandArray = getAllDemandsByUser(clientOfferData.getUserid());
        Matches[] matchesArray = findMatches(demandArray);
        DemandOfferPair[][] selectionPairs = createSelectionPairs(user);
        ClientData clientData = createClientData(user, offerArray, demandArray, matchesArray, selectionPairs);
        return clientData;
    }

    @PostMapping(path="/demandhandler")
    public @ResponseBody ClientData demandHandler(@RequestBody ClientDemandData clientDemandData) {
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
        updateDatabaseExpiredOfferDemand();
        Offer[] offerArray = getAllOffersByUser(clientDemandData.getUserid());
        Demand[] demandArray = getAllDemandsByUser(clientDemandData.getUserid());
        Matches[] matchesArray = findMatches(demandArray);
        DemandOfferPair[][] selectionPairs = createSelectionPairs(user);
        ClientData clientData = createClientData(user, offerArray, demandArray, matchesArray, selectionPairs);
        return clientData;
    }

    @PostMapping(path="/selectionhandler")
    public @ResponseBody ClientData selectionHandler(@RequestBody ClientSelectionData clientSelectionData) {
        Offer offer = getOfferById(clientSelectionData.getOffer_id());
        Demand demand = getDemandById(clientSelectionData.getDemand_id());
        User user = findUser(clientSelectionData.getUserid());
        if (offer.isAvailable()) {
            updateDatabaseSelectedOfferDemand(demand, offer);
        }
        Offer[] offerArray = getAllOffersByUser(user.getUserid());
        Demand[] demandArray = getAllDemandsByUser(user.getUserid());
        Matches[] matchesArray = findMatches(demandArray);
        DemandOfferPair[][] selectionPairs = createSelectionPairs(user);
        ClientData clientData = createClientData(user, offerArray, demandArray, matchesArray, selectionPairs);
        return clientData;
    }

    private void updateDatabaseSelectedOfferDemand(Demand demand, Offer offer) {
        demand.setAvailable(false);
        demand.setOffer(offer);
        demandDao.save(demand);
        offer.setAvailable(false);
        offer.setDemand(demand);
        offerDao.save(offer);
        // if (offer.getContentQuantity() == demand.getContentQuantity()) {
        //     offer.setAvailable(false);
        //     offerDao.save(offer);
        // } else {
        //     Integer offerContentQuantity = offer.getContentQuantity() - demand.getContentQuantity();
        //     offer.setContentQuantity(offerContentQuantity);
        //     offerDao.save(offer);
        // }
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

    private Matches[] findMatches(Demand[] demandArray) {
        if (demandArray.length > 0) {
            Matches[] matchesArray = new Matches[demandArray.length];
            for (int i = 0; i < demandArray.length; i++) {
                Offer[] offerMatches = offerDao.findByAvailableAndContentTypeIgnoreCaseAndContentQuantityGreaterThanEqual(true, demandArray[i].getContentType(), demandArray[i].getContentQuantity());
                Matches matches = new Matches();
                matches.setDemand(demandArray[i]);
                matches.setMatchingOffers(offerMatches);
                Array.set(matchesArray, i, matches);
            }
            for (int i = 0; i < matchesArray.length; i++) {
                Matches locationContentMatches = locationMatcher.matchViaGpsLocation(matchesArray[i]);
                Array.set(matchesArray, i, locationContentMatches);
            }
            return matchesArray;
        } else {
            return null;
        }
    }

    private DemandOfferPair[][] createSelectionPairs(User user) {
        DemandOfferPair[] matchedDemandsAndTheirOffers = createMatchedDemandsAndTheirOffers(user);
        DemandOfferPair[] matchedOffersAndTheirDemands = createMatchedOffersAndTheirDemands(user);
        DemandOfferPair[][] selectionPairs = {matchedDemandsAndTheirOffers, matchedOffersAndTheirDemands};
        return selectionPairs;
    }

    private DemandOfferPair[] createMatchedDemandsAndTheirOffers(User user) {
        Demand[] unavailableDemandsByUser = demandDao.findByAvailableAndUserUserid(false, user.getUserid());
        ArrayList<Demand> matchedDemandsByUser = new ArrayList<>();
        for (int i = 0; i < unavailableDemandsByUser.length; i++) {
            if (unavailableDemandsByUser[i].getOffer() != null) {
                matchedDemandsByUser.add(unavailableDemandsByUser[i]);
            }
        }
        DemandOfferPair[] matchedDemandsAndTheirOffers = new DemandOfferPair[matchedDemandsByUser.size()];
        for (int i = 0; i < matchedDemandsByUser.size(); i++) {
            DemandOfferPair demandOfferPair = new DemandOfferPair();
            Offer offer = getOfferById(matchedDemandsByUser.get(i).getOffer().getOffer_id());
            offer.setDemand(null);
            Demand demand = matchedDemandsByUser.get(i);
            demand.setOffer(null);
            demandOfferPair.setDemand(demand);
            demandOfferPair.setOffer(offer);
            Array.set(matchedDemandsAndTheirOffers, i, demandOfferPair);
        }
        return matchedDemandsAndTheirOffers;
    }

    private DemandOfferPair[] createMatchedOffersAndTheirDemands(User user) {
        Offer[] unavailableOffersByUser = offerDao.findByAvailableAndUserUserid(false, user.getUserid());
        ArrayList<Offer> matchedOffersByUser = new ArrayList<>();
        for (int i = 0; i < unavailableOffersByUser.length; i++) {
            if (unavailableOffersByUser[i].getDemand() != null) {
                matchedOffersByUser.add(unavailableOffersByUser[i]);
            }
        }
        DemandOfferPair[] matchedOffersAndTheirDemands = new DemandOfferPair[matchedOffersByUser.size()];
        for (int i = 0; i < matchedOffersByUser.size(); i++) {
            DemandOfferPair demandOfferPair = new DemandOfferPair();
            Demand demand = getDemandById(matchedOffersByUser.get(i).getDemand().getDemand_id());
            demand.setOffer(null);
            Offer offer = matchedOffersByUser.get(i);
            offer.setDemand(null);
            demandOfferPair.setOffer(offer);
            demandOfferPair.setDemand(demand);
            Array.set(matchedOffersAndTheirDemands, i, demandOfferPair);
        }
        return matchedOffersAndTheirDemands;
    }

    private ClientData createClientData(User user, Offer[] offerArray, Demand[] demandArray, Matches[] matchesArray, DemandOfferPair[][] selectionPairs) {
        ClientData clientData = new ClientData();
        clientData.setUser(user);
        clientData.setOfferArray(offerArray);
        clientData.setDemandArray(demandArray);
        clientData.setMatchesArray(matchesArray);
        clientData.setSelectionPairs(selectionPairs);
        return clientData;
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
        Offer[] offerArray = offerDao.findByAvailableAndUserUserid(true, userid);
        return offerArray;
    }

    private Offer getOfferById(Integer id) {
        ArrayList<Offer> offerList = new ArrayList<>();
        offerDao.findById(id).ifPresent(offerList::add);
        return offerList.get(0);
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
        Demand[] demandArray = demandDao.findByAvailableAndUserUserid(true, userid);
        return demandArray;
    }

    private Demand getDemandById(Integer id) {
        ArrayList<Demand> demandList = new ArrayList<>();
        demandDao.findById(id).ifPresent(demandList::add);
        return demandList.get(0);
    }

}
