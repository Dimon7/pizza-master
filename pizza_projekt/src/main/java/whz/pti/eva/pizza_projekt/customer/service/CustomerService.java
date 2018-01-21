package whz.pti.eva.pizza_projekt.customer.service;

import whz.pti.eva.pizza_projekt.customer.domain.Address;
import whz.pti.eva.pizza_projekt.customer.domain.Customer;
import whz.pti.eva.pizza_projekt.customer.domain.CustomerCreateForm;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<Customer> getAllCustomer();

    void addCustomer(String firstName, String lastName, String loginName, String passwordHash);

    Optional<Customer> getCustomerById(long id);

    Optional<Customer> getCustomerByLoginName(String loginName);

    void addCustomerAdress(String loginName, String street, String
            housenumber, String town, String zipcode);

    public List<Address> getAddressesForCustomer(Customer c);

    Customer editCustomer(String firstName, String lastName, String loginName);

    Customer create(CustomerCreateForm form);

}
