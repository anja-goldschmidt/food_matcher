package nl.sogyo.agoldschmidt_food_matcher.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

// import org.apache.lucene.util.*;

public class LocationMatcher{

    public Matches matchViaGpsLocation(Matches contentMatches) {
        Matches locationContentMatches = new Matches();
        Demand demand = contentMatches.getDemand();
        locationContentMatches.setDemand(demand);
        double latitudeDemand = demand.getAddress().getLatitude();
        double longitudeDemand = demand.getAddress().getLongitude();
        Offer[] contentOffers = contentMatches.getMatchingOffers();
        ArrayList<Offer> locationContentOfferList = new ArrayList<>();
        for (int i = 0; i < contentOffers.length; i++) {
            double latitudeOffer = contentOffers[i].getAddress().getLatitude();
            double longitudeOffer = contentOffers[i].getAddress().getLongitude();
            double distanceOfferDemand = calculateDistanceInMeters(latitudeDemand, longitudeDemand, latitudeOffer, longitudeOffer);
            if (distanceOfferDemand <= demand.getDistance()) {
                locationContentOfferList.add(contentOffers[i]);
            }
        }
        Offer[] locationContentOfferArray = new Offer[locationContentOfferList.size()];
        for (int i = 0; i < locationContentOfferArray.length; i++) {
            Offer offer = locationContentOfferList.get(i);
            Array.set(locationContentOfferArray, i, offer);
        }
        locationContentMatches.setMatchingOffers(locationContentOfferArray);
        return locationContentMatches;
    }

    private double calculateDistanceInMeters(double lat1, double long1, double lat2, double long2) {
        double distance = org.apache.lucene.util.SloppyMath.haversinMeters(lat1, long1, lat2, long2);
        return distance;
    }

}