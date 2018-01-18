package whz.pti.eva.pizza_projekt.customer.service;

import whz.pti.eva.pizza_projekt.customer.domain.CurrentCustomer;

public interface CurrentCustomerService {

    boolean canAccessCustomer(CurrentCustomer currentCustomer, Long customerId);
}
