package org.learning.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.learning.springlamiapizzeriacrud.model.Pizza;
import org.learning.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
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
    //Metodo che mostra i dettagli di una singola pizza
    @GetMapping("show/{id}") //pizzas/show/{id}
    public String show(@PathVariable Integer id, Model model) {
        //nel corpo del metodo ho l'id della pizza da cercare
        Optional<Pizza> result = pizzaRepository.findById(id);
        //verifico se la pizza è stata trovata
        if (result.isPresent()) {
            //estraggo la pizza dall'optional
            Pizza pizza = result.get();
            //aggiungo al model l'attributo con la pizza
            model.addAttribute("pizza", pizza);
            //restituisco il template
            return "pizzas/show";
        } else {
            //gestisco il caso in cui la pizza nel database non c'è
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

    //metodo che riceve il submit del form di creazione e salva su dB la pizza
    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult) {
        //valido i dati del book cioè verifico se bindingResult ha errori
        if (bindingResult.hasErrors()) {
            return "pizzas/create";
        }
        //verifico se nome della pizza già è in DB

        //se esiste gia torna errore
        Optional<Pizza> pizzawithName = pizzaRepository.findByDescription(formPizza.getDescription());
        if (pizzawithName.isPresent()) {
            //se esiste gia torna errore
            bindingResult.addError(new FieldError("pizza", "description", formPizza.getDescription(), false, null, null,
                    "description must be unique"));
            return "pizzas/create";
        } else {
            //se sono validi li salvo su DB
            Pizza savedPizza = pizzaRepository.save(formPizza);

            //faccio redirect a pagina di dettaglio della pizza appena creata
            return "redirect:/pizzas/show/" + savedPizza.getId();

            //se non sono validi ricarico la pagina col form e i messaggi di errore

        }
    }

    //metodo che restituisce pagina modifica della pizza
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        //recupero pizza dal database
        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isPresent()) {
            //la passo come attributo del model
            model.addAttribute("pizza", result.get());
            //ritorno il template
            return "pizzas/edit";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pizza with id " + id + " not found");
        }
    }

    //metodo che riceve il submit del form
    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @Valid Pizza formPizza, BindingResult bindingResult) {
        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isPresent()) {
            Pizza pizzaToEdit = result.get();
            //validare dati della pizza
            if (bindingResult.hasErrors()) {
                //se ci sono errori di validazione
                return "pizzas/edit";
            }

                //se sono validi salvo la pizza sul db
                //prima di salvare i dati su db recupero il valore del campo createdAt
                formPizza.setCreatedAt(pizzaToEdit.getCreatedAt());
                Pizza savedPizza = pizzaRepository.save(formPizza);
                //faccio la redirect alla pagina dettaglio della pizza
                return "redirect:/pizzas/show/" + id;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pizza with id " + id + " not found");
            }
        }
        //metodo che cancella una pizza presa per id
    @PostMapping("/delete/{id}") //se metti pizzas/ prima di delete è come se fosse pizzas/pizzas/delete etc
    public String delete(@PathVariable Integer id) {
    pizzaRepository.deleteById(id);
    return "redirect:/pizzas";
    }
}

