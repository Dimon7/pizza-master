package whz.pti.eva.pizza_projekt.customer.domain;


import org.springframework.security.core.authority.AuthorityUtils;

public class CurrentCustomer extends org.springframework.security.core.userdetails.User {

    private Customer customer;

    public CurrentCustomer(Customer customer) {
        super(customer.getLoginName(), customer.getPasswordHash(), AuthorityUtils.createAuthorityList(customer.getRole().toString()));
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Long getId() {
        return customer.getId();
    }

    public Role getRole() {
        return customer.getRole();
    }

    @Override
    public String toString() {
        return "CurrentUser{" +
                "user=" + customer +
                "} " + super.toString();
    }
}
