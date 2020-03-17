package nl.sogyo.agoldschmidt_food_matcher.model;

import org.hibernate.annotations.Table;
import org.springframework.lang.NonNull;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@Table(appliesTo = "address")
public class Address{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer address_id;

    @NonNull
    private String streetName;

    @NonNull
    private String houseNumber;

    @NonNull
    private String postCode;

    @NonNull
    private String city;

    @NonNull
    private String country;

    private Map<String, Double> gpsPoints;

    public Map<String, Double> getGpsPoints() {
        return this.gpsPoints;
    }

    public void setAddress_id(Integer address_id) {
        this.address_id = address_id;
    }

    public String getStreetName() {
        return this.streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getHouseNumber() {
        return this.houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostCode() {
        return this.postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    };

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setGpsPoints(Map<String, Double> gpsPoints) {
        this.gpsPoints = gpsPoints;
    }

    public Integer getAddress_id() {
        return this.address_id;
    }

    private String fakeMethod() {
        return null;
    }

}