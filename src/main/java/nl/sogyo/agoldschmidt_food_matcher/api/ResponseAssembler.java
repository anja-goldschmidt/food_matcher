package nl.sogyo.agoldschmidt_food_matcher.api;

import java.lang.reflect.Array;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

import nl.sogyo.agoldschmidt_food_matcher.dao.DemandDao;
import nl.sogyo.agoldschmidt_food_matcher.dao.OfferDao;
import nl.sogyo.agoldschmidt_food_matcher.model.ClientData;
import nl.sogyo.agoldschmidt_food_matcher.model.Demand;
import nl.sogyo.agoldschmidt_food_matcher.model.Matches;
import nl.sogyo.agoldschmidt_food_matcher.model.Offer;
import nl.sogyo.agoldschmidt_food_matcher.model.User;

class ResponseAssembler {
    @Autowired
    private DemandDao demandDao;

    @Autowired
    private OfferDao offerDao;

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

    Offer[] getAllOffersByUser(Integer userid) {
        Offer[] offerArray = offerDao.findByAvailableAndUserUserid(true, userid);
        return offerArray;
    }

    Demand[] getAllDemandsByUser(Integer userid) {
        Demand[] demandArray = demandDao.findByAvailableAndUserUserid(true, userid);
        return demandArray;
    }

    Matches[] findMatches(Demand[] demandArray) {
        Matches[] matchesArray = new Matches[demandArray.length];
        for (int i = 0; i < demandArray.length; i++) {
            Offer[] offerMatches = offerDao.findByAvailableAndContentTypeAndContentQuantityGreaterThanEqual(true, demandArray[i].getContentType(), demandArray[i].getContentQuantity());
            Matches matches = new Matches();
            matches.setDemand(demandArray[i]);
            matches.setMatchingOffers(offerMatches);
            Array.set(matchesArray, i, matches);
        }
        return matchesArray;
    }

    ClientData createClientData(User user, Offer[] offerArray, Demand[] demandArray, Matches[] matchesArray) {
        ClientData clientData = new ClientData();
        clientData.setUser(user);
        clientData.setOfferArray(offerArray);
        clientData.setDemandArray(demandArray);
        clientData.setMatchesArray(matchesArray);
        return clientData;
    }

}