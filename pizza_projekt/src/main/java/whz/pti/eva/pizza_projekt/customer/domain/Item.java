package whz.pti.eva.pizza_projekt.customer.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString(exclude = "shoppingCart")
public class Item {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Getter @Setter private int quantity;

    @ManyToOne
    @Getter @Setter Pizza pizza;

    @ManyToOne
    @Setter ShoppingCart shoppingCart;

    public Item(){}

    public Item(int quantity, Pizza pizza, ShoppingCart shoppingCart){

        this.quantity = quantity;
        this.pizza = pizza;
        this.shoppingCart = shoppingCart;
    }

}
