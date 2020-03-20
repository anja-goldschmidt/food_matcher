package nl.sogyo.agoldschmidt_food_matcher.model;

import org.hibernate.annotations.Table;
import org.hibernate.annotations.Type;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Table(appliesTo = "offer")
public class Offer{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer offer_id;

    @NonNull
    private String contentType;

    @NonNull
    private Integer contentQuantity;

    @NonNull
    private LocalDate expiryDate;

    @Type(type="true_false")
    private Boolean available;

    @OneToOne
    @JoinColumn(name = "demand_id")
    private Demand demand;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Integer getOffer_id() {
        return this.offer_id;
    }

    public void setOffer_id(Integer offer_id) {
        this.offer_id = offer_id;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getContentQuantity() {
        return this.contentQuantity;
    }

    public void setContentQuantity(Integer contentQuantity) {
        this.contentQuantity = contentQuantity;
    }

    public LocalDate getExpiryDate() {
        return this.expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Demand getDemand() {
        return this.demand;
    }

    public void setDemand(Demand demand) {
        if (!isAvailable()) {
            this.demand = demand;
        } else {
            this.demand = null;
        }
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}