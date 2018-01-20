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
    public void buy(String timestamp, List<Item> items, int id) {

        ShoppingCart shoppingCart = shoppingCartRepository.getOne(id);
        shoppingCart.setTimestamp(timestamp);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart addShoppingCart( Customer customer){

        ShoppingCart shoppingCart = new ShoppingCart(customer);
        shoppingCartRepository.save(shoppingCart);
        return shoppingCart;

    }
}
