package nl.sogyo.agoldschmidt_food_matcher.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;

public class ClientDemandDataTest {
    
    ClientDemandData clientDemandData;

    @BeforeEach
    public void init() {
        clientDemandData = new ClientDemandData();
    }

    @Test
    public void testClientDemandData() {
        assertNotNull(clientDemandData);
    }

    @Test
    public void testContentType() {
        clientDemandData.setContentType("beetroot");
        assertEquals("beetroot", clientDemandData.getContentType());
    }

    @Test
    public void testContentQuantity() {
        clientDemandData.setContentQuantity(3);
        assertEquals(3, clientDemandData.getContentQuantity());
    }

    @Test
    public void testExpiryDate() {
        clientDemandData.setExpiryDate(LocalDate.of(2020, Month.DECEMBER, 24));
        assertNotNull(clientDemandData.getExpiryDate());
        assertTrue(clientDemandData.getExpiryDate() instanceof LocalDate);
    }

    @Test
    public void testStreetName() {
        clientDemandData.setStreetName("Utrechtseweg");
        assertEquals("Utrechtseweg", clientDemandData.getStreetName());
    }

    @Test
    public void testHouseNumber() {
        clientDemandData.setHouseNumber("301");
        assertEquals("301", clientDemandData.getHouseNumber());
    }

    @Test
    public void testPostCode() {
        clientDemandData.setPostCode("3731 GA");
        assertEquals("3731 GA", clientDemandData.getPostCode());
    }

    @Test
    public void testCity() {
        clientDemandData.setCity("De Bilt");
        assertEquals("De Bilt", clientDemandData.getCity());
    }

    @Test
    public void testCountry() {
        clientDemandData.setCountry("the Netherlands");
        assertEquals("the Netherlands", clientDemandData.getCountry());
    }
    
    @Test
    public void testLatitude() {
        clientDemandData.setLatitude(52.101791);
        assertEquals(52.101791, clientDemandData.getLatitude());
    }

    @Test
    public void testLongitude() {
        clientDemandData.setLongitude(5.171251);
        assertEquals(5.171251, clientDemandData.getLongitude());
    }

    @Test
    public void testUserid() {
        clientDemandData.setUserid(1);
        assertNotNull(clientDemandData.getUserid());
        assertTrue(clientDemandData.getUserid() instanceof Integer);
    }

    @Test
    public void testRange() {
        clientDemandData.setDistance(300);
        assertEquals(300, clientDemandData.getDistance());
    }
}