package whz.pti.eva.pizza_projekt.customer.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import whz.pti.eva.pizza_projekt.customer.domain.Item;
import whz.pti.eva.pizza_projekt.customer.domain.Pizza;
import whz.pti.eva.pizza_projekt.customer.service.PizzaServiceImpl;

import java.util.List;

@Controller
public class PizzaController {


    @Autowired
    PizzaServiceImpl pizzaService;

    @RequestMapping("/angebot")
    public String showItems( Model model ){
        List<Pizza> pizza = pizzaService.getAllPizza();

        model.addAttribute("pizza", pizza);
        return "angebot";
    }

    @RequestMapping("/add_to_basket")
    public String addTobBasket(@RequestParam List<Integer> quantity, Model model){


        List<Pizza> pizza = pizzaService.getAllPizza();

        int i=0;
        for(Pizza p : pizza){
            if(quantity.get(i) != null){
                Item item = new Item(quantity.get(i));
                p.setItem(item);
            }
            i++;
        }

        model.addAttribute("pizza", pizza);
        model.addAttribute("gesamptpreis", pizzaService.gesamptpreis());

        return "warenkorp";
    }

}
