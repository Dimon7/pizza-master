package whz.pti.eva.pizza_projekt.customer.boundary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import whz.pti.eva.pizza_projekt.customer.domain.Customer;
import whz.pti.eva.pizza_projekt.customer.domain.DTO.PayActionResponseDTO;
import whz.pti.eva.pizza_projekt.customer.domain.Item;
import whz.pti.eva.pizza_projekt.customer.domain.Pizza;
import whz.pti.eva.pizza_projekt.customer.domain.ShoppingCart;
import whz.pti.eva.pizza_projekt.customer.service.CustomerServiceImpl;
import whz.pti.eva.pizza_projekt.customer.service.ItemServiceImpl;
import whz.pti.eva.pizza_projekt.customer.service.MPA.SmmpService;
import whz.pti.eva.pizza_projekt.customer.service.PizzaServiceImpl;
import whz.pti.eva.pizza_projekt.customer.service.ShopServiceImpl;

import java.security.Principal;
import java.util.List;

@Controller
public class PizzaController {

    private static final String TO = "WHZ-Pizza_Forever";
    @Autowired
    PizzaServiceImpl pizzaService;

    @Autowired private ShopServiceImpl shopService;
    @Autowired private CustomerServiceImpl customerService;
    @Autowired private ItemServiceImpl itemService;
    @Autowired  private SmmpService smmpService;

    @RequestMapping("/angebot")
    public String showItems( Model model, Principal principal ){
        List<Pizza> pizza = pizzaService.getAllPizza();
        Customer customer = customerService.getCustomerByLoginName(principal.getName()).get();

        model.addAttribute("pizza", pizza);
        model.addAttribute("currentCustomer", customer);
        return "angebot";
    }

    @RequestMapping("/add_to_basket")
    public String addTobBasket(@RequestParam List<Integer> quantity, Model model, Principal principal){

        List<Pizza> pizza = pizzaService.getAllPizza();
        long customerId = customerService.getCustomerByLoginName(principal.getName()).get().getId();

        ShoppingCart shoppingCart = shopService.getShoppingCartByCustomerId(customerId);


        int i=0;
        for(Pizza p : pizza){
            if(quantity.get(i) != null){
                itemService.addItem(quantity.get(i), p, shoppingCart);
            }
            i++;
        }

        List<Item> items = itemService.getAllItemsByCustomer(customerId);

        model.addAttribute("items", items);
        model.addAttribute("gesamptpreis", pizzaService.gesamptpreis(items));

        return "warenkorp";
    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping("/warenkorp")
    public String addTobBasket( Model model, Principal principal){

        long customerId = customerService.getCustomerByLoginName(principal.getName()).get().getId();
        List<Item> items = itemService.getAllItemsByCustomer(customerId);

        model.addAttribute("items", items);

        model.addAttribute("gesamptpreis", pizzaService.gesamptpreis(items));
        if(items.isEmpty()){
            return "redirect:/user";
        }
        return "warenkorp";
    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(value = "/bestellen", method = RequestMethod.POST)
    public String showItems(Principal principal, @RequestParam double gesamptpreis , Model model){

        PayActionResponseDTO payActionResponse = smmpService.doPayAction(principal.getName(), "", "transfer "+ TO + " " +gesamptpreis);

        model.addAttribute("error", payActionResponse.getDescription());

        if( payActionResponse.isPayment() ){
            return deleteBasket(principal);
        }
        return addTobBasket(model, principal);
    }

    @RequestMapping(value = "/leeren", method = RequestMethod.POST)
    public String deleteBasket(Principal principal){

        long customerId = customerService.getCustomerByLoginName(principal.getName()).get().getId();
        itemService.basketDelete(customerId);
        return "redirect:/user";
    }

}
