package org.learning.springlamiapizzeriacrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository { extends JpaRepository<Pizza, Integer> {}
}
