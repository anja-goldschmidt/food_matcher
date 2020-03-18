package nl.sogyo.agoldschmidt_food_matcher.model;

import org.hibernate.annotations.Table;
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
@Table(appliesTo = "demand")
public class Demand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer demand_id;

    @NonNull
    private LocalDate dateOfPlacing;

    @NonNull
    private String contentType;

    @NonNull
    private Integer contentQuantity;

    @NonNull
    private boolean available;

    @OneToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @NonNull
    private LocalDate expiryDate;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    public Integer getDemand_id() {
        return this.demand_id;
    }

    public void setDemand_id(Integer demand_id) {
        this.demand_id = demand_id;
    }

    public LocalDate getDateOfPlacing() {
        return this.dateOfPlacing;
    }

    public void setDateOfPlacing(LocalDate dateOfPlacing) {
        this.dateOfPlacing = dateOfPlacing;
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

    public boolean isAvailable() {
        return this.available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Offer getOffer() {
        return this.offer;
    }

    public void setOffer(Offer offer) {
        if (!isAvailable()) {
            this.offer = offer;
        } else {
            this.offer = null;
        }
    }

    public LocalDate getExpiryDate() {
        return this.expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}