package nl.sogyo.agoldschmidt_food_matcher.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClientDataTest{

    ClientData clientData;

    @BeforeEach
    public void init() {
        clientData = new ClientData();
    }

    @Test
    public void testUser() {
        User user = new User();
        clientData.setUser(user);
        assertEquals(user, clientData.getUser());
    }

    @Test
    public void testOffer() {
        Offer offer = new Offer();
        Offer[] offerArray = {offer};
        clientData.setOfferArray(offerArray);
        assertEquals(offerArray, clientData.getOfferArray());
    }

    @Test
    public void testDemand() {
        Demand demand = new Demand();
        Demand[] demandArray = {demand};
        clientData.setDemandArray(demandArray);
        assertEquals(demandArray, clientData.getDemandArray());
    }

    @Test
    public void testMatches() {
        Matches matches = new Matches();
        Matches[] matchesArray = {matches};
        clientData.setMatchesArray(matchesArray);
        assertEquals(matchesArray, clientData.getMatchesArray());
    }

    @Test
    public void testDemandOfferPair() {
        Demand demand = new Demand();
        Offer offer = new Offer();
        DemandOfferPair demandOfferPair = new DemandOfferPair();
        demandOfferPair.setDemand(demand);
        demandOfferPair.setOffer(offer);
        DemandOfferPair[] demandOfferPairArray = {demandOfferPair};
        DemandOfferPair[][] demandOfferPairMultiArray = {demandOfferPairArray, demandOfferPairArray};
        clientData.setSelectionPairs(demandOfferPairMultiArray);
        assertEquals(demandOfferPairMultiArray, clientData.getSelectionPairs());
    }

}