// package nl.sogyo.agoldschmidt_food_matcher.model;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.*;

// public class LocationMatcherTest {
    
//     LocationMatcher locationMatcher;

//     @BeforeEach
//     public void init() {
//         locationMatcher = new LocationMatcher();
//     }

//     @Test
//     public void testLocationMatcher() {
//         assertNotNull(locationMatcher);
//     }

//     @Test
//     public void testCalculateDistance() {
//         Address addressDemand = new Address();
//         addressDemand.setLatitude(52.08836);
//         addressDemand.setLongitude(5.121244);
//         Demand demand = new Demand();
//         demand.setAddress(addressDemand);
//         demand.setDistance(500);
//         Address addressOfferBarber = new Address();
//         addressOfferBarber.setLatitude(52.086079);
//         addressOfferBarber.setLongitude(5.121204);
//         Offer offerBarber = new Offer();
//         offerBarber.setAddress(addressOfferBarber);
//         Address addressOfferSogyo = new Address();
//         addressOfferSogyo.setLatitude(52.101791);
//         addressOfferSogyo.setLongitude(5.171251);
//         Offer offerSogyo = new Offer();
//         offerSogyo.setAddress(addressOfferSogyo);
//         Offer[] matchingOffersContent = {offerBarber, offerSogyo};
//         Matches contentMatches = new Matches();
//         contentMatches.setDemand(demand);
//         contentMatches.setMatchingOffers(matchingOffersContent);
//         Offer[] matchingOffersContentLocation = {offerBarber};
//         Matches contentLocationMatches = new Matches();
//         contentLocationMatches.setDemand(demand);
//         contentLocationMatches.setMatchingOffers(matchingOffersContentLocation);
//         Matches calculatedMatches = locationMatcher.calculateDistance(contentMatches);
//         assertEquals(contentLocationMatches, calculatedMatches);
//     }
// }