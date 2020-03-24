package nl.sogyo.agoldschmidt_food_matcher.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserDataTest{

    UserData userData;

    @BeforeEach
    public void init() {
        userData = new UserData();
    }

    @Test
    public void testUser() {
        User user = new User();
        userData.setUser(user);
        assertEquals(user, userData.getUser());
    }

    @Test
    public void testOffer() {
        Offer offer = new Offer();
        Offer[] offerArray = {offer};
        userData.setOfferArray(offerArray);
        assertEquals(offerArray, userData.getOfferArray());
    }

    // @Test
    // public void testDemand() {
    //     Demand demand = new Demand();
    //     Demand[] demandArray = {demand};
    //     userData.setDemandArray(demandArray);
    //     assertEquals(demandArray, userData.getDemandArray());
    // }

}