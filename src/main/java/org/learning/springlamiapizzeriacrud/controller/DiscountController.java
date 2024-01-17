package org.learning.springlamiapizzeriacrud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/discounts")
public class DiscountController {
    //metodo per mostrare pagina creazione di un discounbt
    @GetMapping("/create")
    public String create(){
        return "discounts/create";
    }
}
