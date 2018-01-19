package whz.pti.eva.pizza_projekt.customer.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import whz.pti.eva.pizza_projekt.customer.domain.Item;
import whz.pti.eva.pizza_projekt.customer.domain.Pizza;
import whz.pti.eva.pizza_projekt.customer.service.CustomerServiceImpl;
import whz.pti.eva.pizza_projekt.customer.service.ItemServiceImpl;
import whz.pti.eva.pizza_projekt.customer.service.PizzaServiceImpl;
import whz.pti.eva.pizza_projekt.customer.service.ShopServiceImpl;

import java.security.Principal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;

import static java.time.format.DateTimeFormatter.ofLocalizedTime;

@Controller
public class PizzaController {

    @Autowired
    PizzaServiceImpl pizzaService;

    @Autowired private ShopServiceImpl shopService;
    @Autowired private CustomerServiceImpl customerService;
    @Autowired private ItemServiceImpl itemService;

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
                itemService.addItem(quantity.get(i), p);
            }
            i++;
        }
        List<Item> items = itemService.getAllItems();
        model.addAttribute("pizza", pizza);
        model.addAttribute("items", items);
        model.addAttribute("gesamptpreis", pizzaService.gesamptpreis());

        return "warenkorp";
    }

    @RequestMapping(value = "/bestellen", method = RequestMethod.POST)
    public String showItems(Principal principal ){

        List<Item> items = itemService.getAllItems();

        DateTimeFormatter germanFormatter =
                ofLocalizedTime(FormatStyle.MEDIUM)
                        .withLocale(Locale.GERMAN);
        String s = LocalTime.now().minusMinutes(10).format(germanFormatter);

        shopService.buy(s, items, customerService.getCustomerByLoginName(principal.getName()).get());

        return "redirect:/user";
    }

}
