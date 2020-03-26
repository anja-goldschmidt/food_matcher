package nl.sogyo.agoldschmidt_food_matcher.model;

public class ClientSelectionData {
    
    private Integer offer_id;

    private Integer demand_id;

    private Integer userid;


    public Integer getOffer_id() {
        return this.offer_id;
    }

    public void setOffer_id(Integer offer_id) {
        this.offer_id = offer_id;
    }

    public Integer getDemand_id() {
        return this.demand_id;
    }

    public void setDemand_id(Integer demand_id) {
        this.demand_id = demand_id;
    }

    public Integer getUserid() {
        return this.userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

}