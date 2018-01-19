package whz.pti.eva.pizza_projekt.customer.service;

import whz.pti.eva.pizza_projekt.customer.domain.Customer;
import whz.pti.eva.pizza_projekt.customer.domain.Item;

import java.util.List;

public interface ShopService {

    void buy(String timestamp, List<Item> items, Customer customer);

}
