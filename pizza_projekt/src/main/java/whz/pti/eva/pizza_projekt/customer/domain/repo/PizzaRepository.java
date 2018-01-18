package whz.pti.eva.pizza_projekt.customer.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import whz.pti.eva.pizza_projekt.customer.domain.Pizza;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
}
