package whz.pti.eva.pizza_projekt.customer.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@ToString
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

    public ShoppingCart(Customer customer){
        this.customer = customer;
    }


}
