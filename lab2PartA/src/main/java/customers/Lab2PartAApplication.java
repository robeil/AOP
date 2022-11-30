package customers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Lab2PartAApplication implements CommandLineRunner {

	@Autowired
	private CustomerService customerService;

	public static void main(String[] args) {
		SpringApplication.run(Lab2PartAApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Customer customer = new Customer("Frank Brown","fbrown@acme.com ");
		Address address = new Address("1000 N 4th street","Fairfield","52557");
		customerService.addCustomer(customer.getName(), customer.getEmail(), address.getStreet(), address.getCity(), address.getZip());

	}
}
