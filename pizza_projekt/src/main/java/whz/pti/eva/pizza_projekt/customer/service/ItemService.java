package whz.pti.eva.pizza_projekt.customer.service;

import whz.pti.eva.pizza_projekt.customer.domain.Item;
import whz.pti.eva.pizza_projekt.customer.domain.Pizza;
import whz.pti.eva.pizza_projekt.customer.domain.ShoppingCart;

import java.util.List;

public interface ItemService {

    void addItem(int quantity, Pizza pizza, ShoppingCart shoppingCart);

    List<Item> getAllItems();
    List<Item> getAllItemsByCustomer(long customerId);

}
