package nl.sogyo.agoldschmidt_food_matcher.api;

import nl.sogyo.agoldschmidt_food_matcher.dao.DemandDao;
import nl.sogyo.agoldschmidt_food_matcher.dao.OfferDao;
import nl.sogyo.agoldschmidt_food_matcher.model.Demand;
import nl.sogyo.agoldschmidt_food_matcher.model.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="profilepage")
public class DemandOfferController {
    @Autowired
    private DemandDao demandDao;

    @Autowired
    private OfferDao offerDao;

    @PostMapping(path="/demand")
    public @ResponseBody Demand addNewDemand (@RequestBody Demand demand) {
        return null;
    }

    @PostMapping(path="/offer")
    public @ResponseBody Offer addNewOffer (@RequestBody Offer offer) {
        return null;
    }

    @GetMapping(path="/adminDemand")
    public @ResponseBody Iterable<Demand> getAllDemands() {
        return demandDao.findAll();
    }

    @GetMapping(path="/adminOffer")
    public @ResponseBody Iterable<Offer> getAllOffers() {
        return offerDao.findAll();
    }

}