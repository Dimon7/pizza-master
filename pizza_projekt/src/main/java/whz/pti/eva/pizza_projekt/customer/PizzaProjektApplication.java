package whz.pti.eva.pizza_projekt.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import whz.pti.eva.pizza_projekt.customer.service.CustomerServiceImpl;

@SpringBootApplication
public class PizzaProjektApplication {

	public static void main(String[] args) {
		SpringApplication.run(PizzaProjektApplication.class, args);
	}

	@Bean
	CommandLineRunner init() {
		return (evt) -> {


		};
	}
}
