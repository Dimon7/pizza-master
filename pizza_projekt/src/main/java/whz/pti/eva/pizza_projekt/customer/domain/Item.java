package whz.pti.eva.pizza_projekt.customer.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Item {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Getter @Setter private int quantity;

    @ManyToOne
    Pizza pizza;

    @ManyToOne
    ShoppingCart shoppingCart;

    public Item(){}

    public Item(int quantity){
        this.quantity = quantity;
    }

}
