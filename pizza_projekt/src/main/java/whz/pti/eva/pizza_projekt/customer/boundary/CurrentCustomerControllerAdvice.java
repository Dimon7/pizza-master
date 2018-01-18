package whz.pti.eva.pizza_projekt.customer.boundary;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import whz.pti.eva.pizza_projekt.customer.domain.CurrentCustomer;

@ControllerAdvice
public class CurrentCustomerControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrentCustomerControllerAdvice.class);

    @ModelAttribute("currentCustomer")
    public CurrentCustomer getCurrentUser(Authentication authentication) {
        return (authentication == null) ? null : (CurrentCustomer) authentication.getPrincipal();
    }
}
