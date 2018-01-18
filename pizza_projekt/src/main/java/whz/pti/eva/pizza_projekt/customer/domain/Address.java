package whz.pti.eva.pizza_projekt.customer.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@EqualsAndHashCode(exclude="id")
public class Address implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter private int id;

    @ManyToMany(mappedBy = "addresses")
    List<Customer> customer;

    @Getter @Setter private String street;
    @Getter @Setter private String housenumber;
    @Getter @Setter private String town;
    @Getter @Setter private String zipCode;

    public Address(){

    }
    public Address(String street, String housenumber, String town, String zipCode) {
        this.customer = new ArrayList<>();
        this.street = street;
        this.housenumber = housenumber;
        this.town = town;
        this.zipCode = zipCode;
    }


    public List<Customer> getCustomer() {
        return customer;
    }

    public void setCustomer(List<Customer> customer) {
        this.customer = customer;
    }


    public void add(Customer customer){
        this.customer.add(customer);
    }

    public void remove(Customer customer){
        this.customer.remove(customer);
    }


}
