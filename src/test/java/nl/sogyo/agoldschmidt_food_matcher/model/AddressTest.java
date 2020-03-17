package nl.sogyo.agoldschmidt_food_matcher.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class AddressTest {

    Address address;

    @BeforeEach
    public void init() {
        address = new Address();
    }

    @Test
    public void testAddress() {
        assertNotNull(address);
    }

    @Test
    public void testAddress_id() {
        address.setAddress_id(1);
        assertNotNull(address.getAddress_id());
        assertTrue(address.getAddress_id() instanceof Integer);
    }

    @Test
    public void testStreetName() {
        address.setStreetName("Utrechtseweg");
        assertEquals("Utrechtseweg", address.getStreetName());
    }

    @Test
    public void testHouseNumber() {
        address.setHouseNumber("301");
        assertEquals("301", address.getHouseNumber());
    }

    @Test
    public void testPostCode() {
        address.setPostCode("3731 GA");
        assertEquals("3731 GA", address.getPostCode());
    }

    @Test
    public void testCity() {
        address.setCity("De Bilt");
        assertEquals("De Bilt", address.getCity());
    }

    @Test
    public void testCountry() {
        address.setCountry("the Netherlands");
        assertEquals("the Netherlands", address.getCountry());
    }

    @Test
    public void testGpsPoints() {
        Map<String, Double> gpsPoints = new HashMap <String, Double>();
        address.setGpsPoints(gpsPoints);
        assertEquals(gpsPoints, address.getGpsPoints());
    }

}