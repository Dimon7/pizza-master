package whz.pti.eva.pizza_projekt.customer.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Pizza {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter private int id;

    @Getter @Setter private String name;
    @Getter @Setter private String description;

    @Getter @Setter private double price;

    @OneToMany
    private List<Item> items;

    public Pizza(){}

    public Pizza(String name, String description, double price){
        this.items = new ArrayList<>();
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItem(Item item) {
        this.items.add(item);
    }

}
