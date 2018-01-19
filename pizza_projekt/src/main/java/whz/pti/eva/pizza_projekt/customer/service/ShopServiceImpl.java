package whz.pti.eva.pizza_projekt.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import whz.pti.eva.pizza_projekt.customer.domain.Customer;
import whz.pti.eva.pizza_projekt.customer.domain.Item;
import whz.pti.eva.pizza_projekt.customer.domain.ShoppingCart;
import whz.pti.eva.pizza_projekt.customer.domain.repo.ShoppingCartRepository;

import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired private ShoppingCartRepository shoppingCartRepository;

    @Override
    public void buy(String timestamp, List<Item> items, Customer customer) {
        ShoppingCart shoppingCart = new ShoppingCart(timestamp, items, customer);

        shoppingCartRepository.save(shoppingCart);


    }
}
