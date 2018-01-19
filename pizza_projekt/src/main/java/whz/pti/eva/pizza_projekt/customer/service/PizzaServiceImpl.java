package whz.pti.eva.pizza_projekt.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import whz.pti.eva.pizza_projekt.customer.domain.Item;
import whz.pti.eva.pizza_projekt.customer.domain.Pizza;
import whz.pti.eva.pizza_projekt.customer.domain.repo.PizzaRepository;

import java.util.List;
@Service
public class PizzaServiceImpl implements PizzaService {


    @Autowired
    PizzaRepository pizzaRepository;




    @Override
    public List<Pizza> getAllPizza() {
       return  pizzaRepository.findAll();
    }

    @Override
    public double gesamptpreis() {

        double preis = 0.0;
        List<Pizza> pizzas = pizzaRepository.findAll();

        for(Pizza p : pizzas){

            if(!p.getItems().isEmpty()) {
                preis += p.getItems().get(0).getQuantity() * p.getPrice();
            }

        }


        return preis;
    }
}
