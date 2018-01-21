package whz.pti.eva.pizza_projekt.customer.boundary;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import whz.pti.eva.pizza_projekt.customer.domain.Customer;
import whz.pti.eva.pizza_projekt.customer.domain.CustomerCreateForm;
import whz.pti.eva.pizza_projekt.customer.domain.DTO.PayActionResponseDTO;
import whz.pti.eva.pizza_projekt.customer.domain.Item;
import whz.pti.eva.pizza_projekt.customer.domain.validator.CustomerCreateFormValidator;
import whz.pti.eva.pizza_projekt.customer.service.CustomerServiceImpl;
import whz.pti.eva.pizza_projekt.customer.service.ItemServiceImpl;
import whz.pti.eva.pizza_projekt.customer.service.MPA.SmmpService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired private CustomerServiceImpl customerService;

    @Autowired private CustomerCreateFormValidator createFormValidator;

    @Autowired  private SmmpService smmpService;

    @Autowired private ItemServiceImpl itemService;

    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(createFormValidator);
    }


    @RequestMapping("/user")
    public String getCustomerPage(Principal principal, Model model){

        model.addAttribute("currentCustomer", customerService.getCustomerByLoginName(principal.getName())
                .orElseThrow(() -> new NoSuchElementException(String.format("User=%s not found", principal.getName()))));

        PayActionResponseDTO payActionResponse = smmpService.doPayAction(principal.getName(), "", "get");
        Customer customer = customerService.getCustomerByLoginName( principal.getName() ).get();
        List<Item> items = itemService.getAllItemsByCustomer(customer.getId());

        model.addAttribute("account", payActionResponse.getDescription());
        model.addAttribute("count",  items.size());

        return "user_details";
    }

    @PreAuthorize("@currentCustomerServiceImpl.canAccessCustomer(principal, #id)")
    @RequestMapping("/user/{id}")
    public ModelAndView getCustomerPage(@PathVariable int id){

        return new ModelAndView("user_details", "currentCustomer", customerService.getCustomerById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("User=%s not found", id))));
    }



    @RequestMapping(value = "/user/create", method = RequestMethod.GET)
    public ModelAndView getUserCreatePage() {
        return new ModelAndView("user_create", "form", new CustomerCreateForm());
    }



    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public String handleUserCreateForm(@Valid @ModelAttribute("form")  CustomerCreateForm form, BindingResult bindingResult, Model model){
       LOGGER.info("Processing customer create form" + form + " bindingResult= " + bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult.getGlobalError().getDefaultMessage());
            return "user_create";
        }
        try {
            customerService.create(form);
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("error", "LoginName already exists");
            return "user_create";
        }

        return "redirect:/users";
    }


    @RequestMapping("/start_edit_customer_data")
    public String startEditCustomerData(@RequestParam String loginName, Model model){
        Customer customer = customerService.getCustomerByLoginName(loginName).get();
        model.addAttribute("currentCustomer", customer);
        return "edit_customer_data";

    }
    @RequestMapping("finish_edit_customer_data")
    public String  finishEditCustomerData(@RequestParam String firstName,
                                          @RequestParam String lastName,
                                          @RequestParam String loginName ){

        customerService.editCustomer( firstName,  lastName,  loginName);

        return "redirect:user";
    }
}
