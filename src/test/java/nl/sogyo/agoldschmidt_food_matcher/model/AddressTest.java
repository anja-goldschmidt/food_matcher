package nl.sogyo.agoldschmidt_food_matcher.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
        address.setPostCode("3731GA");
        assertEquals("3731GA", address.getPostCode());
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
    public void testLatitude() {
        address.setLatitude(52.101791);
        assertEquals(52.101791, address.getLatitude());
    }

    @Test
    public void testLongitude() {
        address.setLongitude(5.171251);
        assertEquals(5.171251, address.getLongitude());
    }

}