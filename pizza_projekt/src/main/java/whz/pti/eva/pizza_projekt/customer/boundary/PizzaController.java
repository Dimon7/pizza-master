package whz.pti.eva.pizza_projekt.customer.boundary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import whz.pti.eva.pizza_projekt.customer.domain.Item;
import whz.pti.eva.pizza_projekt.customer.domain.Pizza;
import whz.pti.eva.pizza_projekt.customer.domain.ShoppingCart;
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


    private static final Logger LOGGER = LoggerFactory.getLogger(PizzaController.class);

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
    public String addTobBasket(@RequestParam List<Integer> quantity, Model model, Principal principal){
        List<Pizza> pizza = pizzaService.getAllPizza();
        ShoppingCart shoppingCart = shopService.addShoppingCart( customerService.getCustomerByLoginName(principal.getName()).get() );
        LOGGER.debug( shoppingCart.toString() );
        int i=0;
        for(Pizza p : pizza){
            if(quantity.get(i) != null){
                itemService.addItem(quantity.get(i), p, shoppingCart);
            }
            i++;
        }

        long customerId = customerService.getCustomerByLoginName(principal.getName()).get().getId();
        List<Item> items = itemService.getAllItemsByCustomer(customerId);
        model.addAttribute("pizza", pizza);
        model.addAttribute("items", items);
        model.addAttribute("gesamptpreis", pizzaService.gesamptpreis());

        return "warenkorp";
    }

    @RequestMapping(value = "/bestellen", method = RequestMethod.POST)
    public String showItems(Principal principal ){
        return "redirect:/user";
    }

}
