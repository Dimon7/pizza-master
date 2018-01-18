package whz.pti.eva.pizza_projekt.customer.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class ShoppingCart {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private Date creationDate;

    @OneToMany
    List<Item> items;

    @ManyToOne
    Customer customer;

    public ShoppingCart(){}

    ShoppingCart(Date creationDate){
        this.items = new ArrayList<>();
        this.creationDate = creationDate;
    }
}
