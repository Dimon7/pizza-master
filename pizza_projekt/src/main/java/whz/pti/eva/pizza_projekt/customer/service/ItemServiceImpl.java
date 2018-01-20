package whz.pti.eva.pizza_projekt.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import whz.pti.eva.pizza_projekt.customer.domain.Item;
import whz.pti.eva.pizza_projekt.customer.domain.Pizza;
import whz.pti.eva.pizza_projekt.customer.domain.ShoppingCart;
import whz.pti.eva.pizza_projekt.customer.domain.repo.ItemRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {


    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ItemRepository itemRepository;


    @Override
    public void addItem(int quantity, Pizza pizza, ShoppingCart shoppingCart) {

        Item item = new Item(quantity, pizza, shoppingCart);
        this.itemRepository.save(item);


    }

    @Override
    public List<Item> getAllItems() {
        return this.itemRepository.findAll();
    }

    @Override
    public List<Item> getAllItemsByCustomer(long customerId) {
        String qlQuery = "select t from Item t where (t.SHOPPING_CART_ID = :CUSTOMER_ID)";

        TypedQuery query= em.createQuery(qlQuery, Item.class );
        query.setParameter("CUSTOMER_ID", customerId);

        return query.getResultList();
    }
}
