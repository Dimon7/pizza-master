package whz.pti.eva.pizza_projekt.customer.boundary;


import com.sun.org.apache.xpath.internal.SourceTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import whz.pti.eva.pizza_projekt.customer.domain.Customer;
import whz.pti.eva.pizza_projekt.customer.domain.CustomerCreateForm;
import whz.pti.eva.pizza_projekt.customer.domain.validator.CustomerCreateFormValidator;
import whz.pti.eva.pizza_projekt.customer.service.CustomerServiceImpl;

import javax.validation.Valid;
import java.security.Principal;
import java.util.NoSuchElementException;

@Controller
public class CustomerController {


    @Autowired private CustomerServiceImpl customerService;

    @Autowired private CustomerCreateFormValidator createFormValidator;

    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(createFormValidator);
    }


    @RequestMapping("/user")
    public ModelAndView getCustomerPage(Principal principal, Model model){
        return new ModelAndView("user_details", "currentCustomer", customerService.getCustomerByLoginName(principal.getName())
                .orElseThrow(() -> new NoSuchElementException(String.format("User=%s not found", principal.getName()))));
    }

    @PreAuthorize("@currentCustomerServiceImpl.canAccessCustomer(principal, #id)")
    @RequestMapping("/user/{id}")
    public ModelAndView getCustomerPage(@PathVariable int id){

        return new ModelAndView("user_details", "currentCustomer", customerService.getCustomerById(id)
            .orElseThrow(() -> new NoSuchElementException(String.format("User=%s not found", id))));
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/user/create", method = RequestMethod.GET)
    public ModelAndView getUserCreatePage() {
        return new ModelAndView("user_create", "form", new CustomerCreateForm());
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public String handleUserCreateForm(@Valid @ModelAttribute("form")  CustomerCreateForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user_create";
        }
        try {
            customerService.create(form);
        } catch (DataIntegrityViolationException e) {
            bindingResult.reject("loginName.exists", "LoginName already exists");
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
                                          @RequestParam String loginName,
                                          @RequestParam String passwordHash){

        customerService.editCustomer( firstName,  lastName,  loginName,  passwordHash);

        return "redirect:user";
    }
}
