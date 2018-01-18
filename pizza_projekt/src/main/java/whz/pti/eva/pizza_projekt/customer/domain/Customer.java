package whz.pti.eva.pizza_projekt.customer.domain;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@ToString(exclude="id")
@EqualsAndHashCode(exclude="id")
@NoArgsConstructor
public class Customer implements Serializable {

    @Id @GeneratedValue @Getter long id;

    @ManyToMany
    @Getter List<Address> addresses;

    @OneToMany
    List<ShoppingCart> shoppingCart;

    @Column(name="firstName", nullable = false)
    @Getter @Setter private String firstName;

    @Column(name="lastName", nullable = false)
    @Getter @Setter  private String lastName;

    @Column(name="loginName", nullable = false)
    @Getter @Setter private String loginName;

    @Column(name="password_hash", nullable = false)
    @Getter @Setter private String passwordHash;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    @Getter @Setter private Role role;

    public Customer(String firstName, String lastName, String loginName, String passwordHash) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.loginName = loginName;
        this.passwordHash = passwordHash;
    }



    public void setAddresses(Address addresses) {
        this.addresses.add(addresses);
    }


   public void add(Address address){
        this.addresses.add(address);
   }

   public void remove(Address address){
        this.addresses.remove(address);
   }

}
