package whz.pti.eva.pizza_projekt.customer.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import whz.pti.eva.pizza_projekt.customer.domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByLoginName(String loginName);
}
