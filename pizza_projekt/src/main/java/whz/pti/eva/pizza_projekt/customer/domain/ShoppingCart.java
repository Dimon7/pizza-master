package whz.pti.eva.pizza_projekt.customer.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class ShoppingCart {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Getter @Setter private String timestamp;

    @OneToMany(mappedBy = "shoppingCart")
    List<Item> items;

    @ManyToOne
    Customer customer;

    public ShoppingCart(){}

    public ShoppingCart(String timestamp, List<Item> items, Customer customer){
        this.items = items;
        this.timestamp = timestamp;
        this.customer = customer;
    }


}
