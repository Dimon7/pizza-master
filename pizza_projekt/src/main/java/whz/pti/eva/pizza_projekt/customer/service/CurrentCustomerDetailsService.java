package whz.pti.eva.pizza_projekt.customer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import whz.pti.eva.pizza_projekt.customer.domain.CurrentCustomer;
import whz.pti.eva.pizza_projekt.customer.domain.Customer;


@Service
public class CurrentCustomerDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrentCustomerDetailsService.class);
    @Autowired private  CustomerService customerService;

    @Override
    public CurrentCustomer loadUserByUsername(String loginName) throws UsernameNotFoundException {
        LOGGER.debug("Authenticating user with loginName={}", loginName);

        Customer customer = customerService.getCustomerByLoginName(loginName)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email=%s was not found", loginName)));
        return new CurrentCustomer(customer);
    }

}