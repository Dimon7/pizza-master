package whz.pti.eva.pizza_projekt.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import whz.pti.eva.pizza_projekt.customer.domain.Item;
import whz.pti.eva.pizza_projekt.customer.domain.Pizza;
import whz.pti.eva.pizza_projekt.customer.domain.repo.ItemRepository;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;


    @Override
    public void addItem(int quantity, Pizza pizza) {

        Item item = new Item(quantity, pizza);
        this.itemRepository.save(item);


    }

    @Override
    public List<Item> getAllItems() {
        return this.itemRepository.findAll();
    }
}
