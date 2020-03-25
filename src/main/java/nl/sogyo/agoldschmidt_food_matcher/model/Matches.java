package nl.sogyo.agoldschmidt_food_matcher.model;

public class Matches {

    private Demand demand;

    private Offer[] matchingOffers;


    public Demand getDemand() {
        return this.demand;
    }

    public void setDemand(Demand demand) {
        this.demand = demand;
    }

    public Offer[] getMatchingOffers() {
        return this.matchingOffers;
    }

    public void setMatchingOffers(Offer[] matchingOffers) {
        this.matchingOffers = matchingOffers;
    }

}