package whz.pti.eva.pizza_projekt.customer.service;

import org.springframework.stereotype.Service;
import whz.pti.eva.pizza_projekt.customer.domain.CurrentCustomer;
import whz.pti.eva.pizza_projekt.customer.domain.Role;

@Service
public class CurrentCustomerServiceImpl implements CurrentCustomerService {


    @Override
    public boolean canAccessCustomer(CurrentCustomer currentCustomer, Long customerId) {
        return currentCustomer != null
                && (currentCustomer.getRole() == Role.ADMIN || currentCustomer.getId().equals(customerId));
    }
}
