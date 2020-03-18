package nl.sogyo.agoldschmidt_food_matcher.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;

public class DemandTest {
    
    Demand demand;

    @BeforeEach
    public void init() {
        demand = new Demand();
    }

    @Test
    public void testDemand() {
        assertNotNull(demand);
    }

    @Test
    public void testdemand_id() {
        demand.setDemand_id(1);
        assertNotNull(demand.getDemand_id());
        assertTrue(demand.getDemand_id() instanceof Integer);
    }

    @Test
    public void testDateOfPlacing() {
        demand.setDateOfPlacing(LocalDate.now());
        assertNotNull(demand.getDateOfPlacing());
        assertTrue(demand.getDateOfPlacing() instanceof LocalDate);
    }

    @Test
    public void testContentType() {
        demand.setContentType("beetroot");
        assertEquals("beetroot", demand.getContentType());
    }

    @Test
    public void testContentQuantity() {
        demand.setContentQuantity(3);
        assertEquals(3, demand.getContentQuantity());
    }

    @Test
    public void testAvailable() {
        demand.setAvailable(true);
        assertEquals(true, demand.isAvailable());
    }

    @Test
    public void testOfferNotNull() {
        Offer offer = new Offer();
        demand.setAvailable(false);
        demand.setOffer(offer);
        assertEquals(offer, demand.getOffer());
    }

    @Test
    public void testOfferNull() {
        demand.setAvailable(true);
        Offer offer = new Offer();
        offer.setOffer_id(1);
        demand.setOffer(offer);
        assertNull(demand.getOffer());
    }

    @Test
    public void testExpiryDate() {
        demand.setExpiryDate(LocalDate.of(2020, Month.DECEMBER, 24));
        assertNotNull(demand.getExpiryDate());
        assertTrue(demand.getExpiryDate() instanceof LocalDate);
    }

    @Test
    public void testUser() {
        User user = new User();
        demand.setUser(user);
        assertEquals(user, demand.getUser());
    }

    @Test
    public void testAddress() {
        Address address = new Address();
        demand.setAddress(address);
        assertEquals(address, demand.getAddress());
    }
}