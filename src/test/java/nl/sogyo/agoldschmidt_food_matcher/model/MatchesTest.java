package nl.sogyo.agoldschmidt_food_matcher.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MatchesTest {
    
    Matches matches;

    @BeforeEach
    public void init() {
        matches = new Matches();
    }

    @Test
    public void testDemand() {
        Demand demand = new Demand();
        matches.setDemand(demand);
        assertEquals(demand, matches.getDemand());
    }

    @Test
    public void testMatchingOffers() {
        Offer offer = new Offer();
        Offer[] matchingOffers = {offer};
        matches.setMatchingOffers(matchingOffers);
        assertEquals(matchingOffers, matches.getMatchingOffers());
    }
}