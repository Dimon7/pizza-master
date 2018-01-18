package whz.pti.eva.pizza_projekt.customer.service;

import whz.pti.eva.pizza_projekt.customer.domain.Pizza;

import java.util.List;

public interface PizzaService {

    List<Pizza> getAllPizza();

    double gesamptpreis();


}
