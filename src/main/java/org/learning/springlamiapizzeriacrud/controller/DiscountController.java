package org.learning.springlamiapizzeriacrud.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.learning.springlamiapizzeriacrud.model.Discount;
import org.learning.springlamiapizzeriacrud.model.Pizza;
import org.learning.springlamiapizzeriacrud.repository.DiscountRepository;
import org.learning.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/discounts")
public class DiscountController {
    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private DiscountRepository discountRepository;

    //metodo per mostrare pagina creazione di un discounbt
    @GetMapping("/create")
    public String create(@RequestParam(name = "pizzaId", required = true)Integer pizzaId, Model model){
        //verifico se pizza esiste su database
        Optional<Pizza> result = pizzaRepository.findById(pizzaId);
        if (result.isPresent()){
        //estraggo pizza dall'optional
            Pizza pizzaToDiscount = result.get();
            //passo al model la Pizza come attributo
            model.addAttribute("pizza", pizzaToDiscount);
            //preparo il discount per precaricare il form
            Discount newDiscount = new Discount();
            //precarico i campi pizza, startdate e expireDate
            newDiscount.setPizza(pizzaToDiscount);
            newDiscount.setStartDate(LocalDate.now());
            newDiscount.setExpireDate(LocalDate.now().plusDays(30));
            model.addAttribute("discount", newDiscount);
            //restituisco il template
            return "discounts/create";
        } else {
            //se l'optional è vuoto sollevo eccezione http 404
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "pizza with id " + pizzaId + " not found");
        }
    }
    @PostMapping("/create")
    public String store(Discount formDiscount){
        //valido l'oggeetto

        //se ci sono errori ritorno al template del form

        //se non ci sono errori lo salvo sul db
       Discount storedDiscount = discountRepository.save(formDiscount);
        //faccio una redirect alla pagina di dettaglio della pizza
        return "redirect:/pizzas/show/" + storedDiscount.getPizza().getId();
    }
}
