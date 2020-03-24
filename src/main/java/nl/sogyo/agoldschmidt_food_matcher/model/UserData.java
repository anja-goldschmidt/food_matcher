package nl.sogyo.agoldschmidt_food_matcher.model;

public class UserData {

    private User user;

    private Offer[] offerArray;

    // private Demand[] demandArray;

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Offer[] getOfferArray() {
        return this.offerArray;
    }

    public void setOfferArray(Offer[] offerArray) {
        this.offerArray = offerArray;
    }

    // public Demand[] getDemandArray() {
    //     return this.demandArray;
    // }

    // public void setDemandArray(Demand[] demandArray) {
    //     this.demandArray = demandArray;
    // }

}