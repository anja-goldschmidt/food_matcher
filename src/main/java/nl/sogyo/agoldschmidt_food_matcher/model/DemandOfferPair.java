package nl.sogyo.agoldschmidt_food_matcher.model;

public class DemandOfferPair{

    private Offer offer;

    private Demand demand;


    public Offer getOffer() {
        return this.offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Demand getDemand() {
        return this.demand;
    }

    public void setDemand(Demand demand) {
        this.demand = demand;
    }
}