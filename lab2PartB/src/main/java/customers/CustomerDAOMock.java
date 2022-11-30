package customers;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("customerDAOMock")
public class CustomerDAOMock {

}
