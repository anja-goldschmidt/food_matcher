package nl.sogyo.agoldschmidt_food_matcher.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DemandOfferPairTest{

    DemandOfferPair demandOfferPair;

    @BeforeEach
    public void init() {
        demandOfferPair = new DemandOfferPair();
    }

    @Test
    public void testDemandOfferPair() {
        assertNotNull(demandOfferPair);
    }

    @Test
    public void testDemand() {
        Demand demand = new Demand();
        demandOfferPair.setDemand(demand);
        assertEquals(demand, demandOfferPair.getDemand());
    }

    @Test
    public void testOffer() {
        Offer offer = new Offer();
        demandOfferPair.setOffer(offer);
        assertEquals(offer, demandOfferPair.getOffer());
    }

}