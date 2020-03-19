package nl.sogyo.agoldschmidt_food_matcher.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;

public class OfferTest {
    
    Offer offer;

    @BeforeEach
    public void init() {
        offer = new Offer();
    }

    @Test
    public void testOffer() {
        assertNotNull(offer);
    }

    @Test
    public void testOffer_id() {
        offer.setOffer_id(1);
        assertNotNull(offer.getOffer_id());
        assertTrue(offer.getOffer_id() instanceof Integer);
    }

    @Test
    public void testContentType() {
        offer.setContentType("beetroot");
        assertEquals("beetroot", offer.getContentType());
    }

    @Test
    public void testContentQuantity() {
        offer.setContentQuantity(3);
        assertEquals(3, offer.getContentQuantity());
    }

    @Test
    public void testAvailable() {
        offer.setAvailable(true);
        assertEquals(true, offer.isAvailable());
    }

    @Test
    public void testDemandNotNull() {
        Demand demand = new Demand();
        offer.setAvailable(false);
        offer.setDemand(demand);
        assertEquals(demand, offer.getDemand());
    }

    @Test
    public void testDemandNull() {
        offer.setAvailable(true);
        Demand demand = new Demand();
        demand.setDemand_id(1);
        offer.setDemand(demand);
        assertNull(offer.getDemand());
    }

    @Test
    public void testExpiryDate() {
        offer.setExpiryDate(LocalDate.of(2020, Month.DECEMBER, 24));
        assertNotNull(offer.getExpiryDate());
        assertTrue(offer.getExpiryDate() instanceof LocalDate);
    }

    @Test
    public void testUser() {
        User user = new User();
        offer.setUser(user);
        assertEquals(user, offer.getUser());
    }

    @Test
    public void testAddress() {
        Address address = new Address();
        offer.setAddress(address);
        assertEquals(address, offer.getAddress());
    }
}