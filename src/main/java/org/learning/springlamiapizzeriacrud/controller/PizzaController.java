package org.learning.springlamiapizzeriacrud.controller;

import org.learning.springlamiapizzeriacrud.model.Pizza;
import org.learning.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {
    @Autowired
   private PizzaRepository pizzaRepository;

    @GetMapping
    public String index(Model model) {
        List<Pizza> pizzaList = pizzaRepository.findAll();
        model.addAttribute("pizzaList", pizzaList);
        return "pizzas/list";
    }
    @GetMapping("show/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isPresent()) {
            Pizza pizza = result.get();
            model.addAttribute("pizza", pizza);
            return "pizzas/show";
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with id " + id + " not found");
        }
    }
    //metodo che mostra pagina creazione di una pizza
    @GetMapping("/create")
    public String create(Model model) {
        //passo tramite model attributo di tipo pizza vuoto
        model.addAttribute("pizza", new Pizza());
        return "pizzas/create";
    }
    //metodo che riceve il submit del form di creazione e sakva su dB la pizza
    @PostMapping("/create")
    public String store(Pizza formPizza) {
        //valido i dati del book

        //se sono validi li salvo su DB

        Pizza savedPizza = pizzaRepository.save(formPizza);
        //faccio redirect a pagina di dettaglio della pizza appena creata
        return "redirect:/pizzas/show/" + savedPizza.getId();
        //se non sono validi ricarico la pagina col form e i messaggi di errore

    }
}
