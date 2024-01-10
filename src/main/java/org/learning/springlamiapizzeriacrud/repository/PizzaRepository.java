package org.learning.springlamiapizzeriacrud.repository;

import org.learning.springlamiapizzeriacrud.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// interfaccia che eredita da JpaRepository tutti i metodi che permettono di fare le CRUD
// i generics chiedono: che tipo di dato è l'entità (Pizza) e che tipo di dato è la chiave primaria (Integer)
public interface PizzaRepository extends JpaRepository<Pizza, Integer>{
    //metodo che ricerca le pizze per nome
    Optional<Pizza> findByDescription (String description);
}


