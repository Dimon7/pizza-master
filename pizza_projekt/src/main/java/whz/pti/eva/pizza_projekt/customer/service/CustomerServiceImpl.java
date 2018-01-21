package whz.pti.eva.pizza_projekt.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import whz.pti.eva.pizza_projekt.customer.domain.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import whz.pti.eva.pizza_projekt.customer.domain.repo.AddressRepository;
import whz.pti.eva.pizza_projekt.customer.domain.repo.CustomerRepository;
import whz.pti.eva.pizza_projekt.customer.domain.repo.PizzaRepository;
import whz.pti.eva.pizza_projekt.customer.service.MPA.SmmpServiceImpl;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    PizzaRepository pizzaRepository;

    @Autowired
    ShopServiceImpl shopService;

    @Autowired
    SmmpServiceImpl smmpService;

    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public void addCustomer(String firstName, String lastName, String loginName, String passwordHash) {
        Customer c = new Customer(firstName, lastName, loginName, passwordHash);
        customerRepository.save(c);

    }

    @Override
    public Optional<Customer> getCustomerById(long id) {
        return Optional.ofNullable(customerRepository.findOne(id) );
    }

    @Override
    public Optional<Customer> getCustomerByLoginName(String loginName) {
        return Optional.ofNullable(customerRepository.findByLoginName(loginName));
    }

    @Override
    public void addCustomerAdress(String loginName, String street, String housenumber, String town, String zipcode) {

        Customer cust = customerRepository.findByLoginName(loginName);
        Address address = new Address(street, housenumber,town,zipcode);
        addressRepository.save(address);
        cust.setAddresses(address);
        customerRepository.save(cust);


    }

    @Override
    public List<Address> getAddressesForCustomer(Customer c) {

        return addressRepository.findAdressesByCustomer(c);
    }

    @Override
    public Customer editCustomer( String firstName, String lastName, String loginName) {

        Customer cust = customerRepository.findByLoginName(loginName);


        cust.setFirstName(firstName);
        cust.setLastName(lastName);

        customerRepository.save(cust);

        return cust;
    }

    @Override
    public Customer create(CustomerCreateForm form) {
            Customer customer = new Customer();

            customer.setLoginName(form.getLoginName());
            customer.setFirstName(form.getFirstName());
            customer.setLastName(form.getLastName());
            customer.setPasswordHash(new BCryptPasswordEncoder().encode(form.getPassword()));
            customer.setRole(Role.USER);

            customerRepository.save(customer);
            shopService.addShoppingCart(customer);

            smmpService.createPayUser(form.getLoginName());

            return customer;


    }

    public Address editAddress(int id, String street, String housenumber, String town, String zipCode){

        Address address = addressRepository.findOne(id);

        address.setHousenumber(housenumber);
        address.setTown(town);
        address.setZipCode(zipCode);
        address.setStreet(street);

        addressRepository.save(address);

        return address;


    }

    public void deleteAdres(int id){
        addressRepository.delete(id);
    }

    public Address getAddressesById(int id){
        return addressRepository.findOne(id);
    }

}
