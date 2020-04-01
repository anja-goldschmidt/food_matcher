package nl.sogyo.agoldschmidt_food_matcher.api;

import nl.sogyo.agoldschmidt_food_matcher.dao.DemandDao;
import nl.sogyo.agoldschmidt_food_matcher.dao.OfferDao;
import nl.sogyo.agoldschmidt_food_matcher.dao.UserDao;
import nl.sogyo.agoldschmidt_food_matcher.model.*;

import java.lang.reflect.Array;
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

    private LocationMatcher locationMatcher = new LocationMatcher();

    @PostMapping(path="login")
    public @ResponseBody ClientData userHandler (@RequestBody User user) {
        updateDatabaseExpiredOfferDemand();
        user = addNewUser(user);
        Offer[] offerArray = getAllOffersByUser(user.getUserid());
        Demand[] demandArray = getAllDemandsByUser(user.getUserid());
        Matches[] matchesArray = findMatches(demandArray);
        DemandOfferPair[][] selectionPairs = createSelectionPairs(user);
        ClientData clientData = createClientData(user, offerArray, demandArray, matchesArray, selectionPairs);
        return clientData;
    }

    void updateDatabaseExpiredOfferDemand() {
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

    private Offer getOfferById(Integer id) {
        ArrayList<Offer> offerList = new ArrayList<>();
        offerDao.findById(id).ifPresent(offerList::add);
        return offerList.get(0);
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

    // @GetMapping(path="/adminUser")
    // public @ResponseBody Iterable<User> getAllUsers() {
    //     return userDao.findAll();
    // }

    // @GetMapping(path="/adminUser1")
    // public @ResponseBody User getUserById(@RequestParam Integer id) {
    //     ArrayList<User> userList = new ArrayList<>();
    //     userDao.findById(id).ifPresent(userList::add);
    //     return userList.get(0);
    // }

}
