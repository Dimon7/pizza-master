package whz.pti.eva.pizza_projekt.customer.domain.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import whz.pti.eva.pizza_projekt.customer.domain.Address;
import whz.pti.eva.pizza_projekt.customer.domain.Customer;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {


    List<Address> findAdressesByCustomer(Customer c);

}
