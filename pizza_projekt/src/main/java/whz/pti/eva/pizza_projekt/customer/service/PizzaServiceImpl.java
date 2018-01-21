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

    @Autowired
    ItemServiceImpl itemService;




    @Override
    public List<Pizza> getAllPizza() {
       return  pizzaRepository.findAll();
    }

    @Override
    public double gesamptpreis(List<Item> items) {

        double preis = 0;


        for(Item item : items){
            preis += item.getPizza().getPrice() * item.getQuantity() ;

        }


        return preis;
    }
}
