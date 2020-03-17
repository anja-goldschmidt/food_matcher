package nl.sogyo.agoldschmidt_food_matcher.api;

import nl.sogyo.agoldschmidt_food_matcher.dao.AddressDao;
import nl.sogyo.agoldschmidt_food_matcher.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="")
public class AddressController {
    @Autowired
    private AddressDao addressDao;

    @PostMapping(path="/add")
    public @ResponseBody Address addNewAddress (@RequestParam String streetName, @RequestParam String houseNumber, @RequestParam String postCode, @RequestParam String city, @RequestParam String country, @RequestParam Double longitude, @RequestParam Double latitude) {
        Address address;
        if (addressDao.findByLongitudeAndLatitude(longitude, latitude) == null) {
            address = new Address();
            address.setStreetName(streetName);
            address.setHouseNumber(houseNumber);
            address.setPostCode(postCode);
            address.setCity(city);
            address.setCountry(country);
            address.setLongitude(longitude);
            address.setLatitude(latitude);
            addressDao.save(address);
        } else {
            address = addressDao.findByLongitudeAndLatitude(longitude, latitude);
        }
        return address;
    }

    @GetMapping(path="/adminAddress")
    public @ResponseBody Iterable<Address> getAllAddresses() {
        return addressDao.findAll();
    }

}
