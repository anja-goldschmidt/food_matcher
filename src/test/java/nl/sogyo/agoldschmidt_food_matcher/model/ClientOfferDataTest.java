package nl.sogyo.agoldschmidt_food_matcher.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;

public class ClientOfferDataTest {
    
    ClientOfferData clientOfferData;

    @BeforeEach
    public void init() {
        clientOfferData = new ClientOfferData();
    }

    @Test
    public void testClientOfferData() {
        assertNotNull(clientOfferData);
    }

    @Test
    public void testContentType() {
        clientOfferData.setContentType("beetroot");
        assertEquals("beetroot", clientOfferData.getContentType());
    }

    @Test
    public void testContentQuantity() {
        clientOfferData.setContentQuantity(3);
        assertEquals(3, clientOfferData.getContentQuantity());
    }

    @Test
    public void testExpiryDate() {
        clientOfferData.setExpiryDate(LocalDate.of(2020, Month.DECEMBER, 24));
        assertNotNull(clientOfferData.getExpiryDate());
        assertTrue(clientOfferData.getExpiryDate() instanceof LocalDate);
    }

    @Test
    public void testStreetName() {
        clientOfferData.setStreetName("Utrechtseweg");
        assertEquals("Utrechtseweg", clientOfferData.getStreetName());
    }

    @Test
    public void testHouseNumber() {
        clientOfferData.setHouseNumber("301");
        assertEquals("301", clientOfferData.getHouseNumber());
    }

    @Test
    public void testPostCode() {
        clientOfferData.setPostCode("3731 GA");
        assertEquals("3731 GA", clientOfferData.getPostCode());
    }

    @Test
    public void testCity() {
        clientOfferData.setCity("De Bilt");
        assertEquals("De Bilt", clientOfferData.getCity());
    }

    @Test
    public void testCountry() {
        clientOfferData.setCountry("the Netherlands");
        assertEquals("the Netherlands", clientOfferData.getCountry());
    }
    
    @Test
    public void testLatitude() {
        clientOfferData.setLatitude(52.101791);
        assertEquals(52.101791, clientOfferData.getLatitude());
    }

    @Test
    public void testLongitude() {
        clientOfferData.setLongitude(5.171251);
        assertEquals(5.171251, clientOfferData.getLongitude());
    }

    @Test
    public void testUserid() {
        clientOfferData.setUserid(1);
        assertNotNull(clientOfferData.getUserid());
        assertTrue(clientOfferData.getUserid() instanceof Integer);
    }
}