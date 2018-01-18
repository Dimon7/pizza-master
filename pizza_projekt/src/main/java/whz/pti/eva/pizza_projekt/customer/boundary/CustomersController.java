package whz.pti.eva.pizza_projekt.customer.boundary;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import whz.pti.eva.pizza_projekt.customer.service.CustomerServiceImpl;

@Controller
public class CustomersController {

    @Autowired
    private CustomerServiceImpl customerService;

    @RequestMapping("/users")
    public ModelAndView getUsersPage() {
        return new ModelAndView("users", "users", customerService.getAllCustomer() );
    }
}
