package whz.pti.eva.pizza_projekt.customer.service;

import whz.pti.eva.pizza_projekt.customer.domain.Item;
import whz.pti.eva.pizza_projekt.customer.domain.Pizza;

import java.util.List;

public interface ItemService {

    void addItem(int quantity, Pizza pizza);

    List<Item> getAllItems();

}
