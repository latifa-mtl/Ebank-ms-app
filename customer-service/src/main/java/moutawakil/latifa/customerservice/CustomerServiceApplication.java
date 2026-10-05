package moutawakil.latifa.customerservice;

import moutawakil.latifa.customerservice.entities.Customer;
import moutawakil.latifa.customerservice.services.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner(CustomerService customerService) {
        return args -> {
            List<String> names = List.of("Mohamed", "Imane", "yassine");
            names.forEach(name -> {
                customerService.saveCustomer(Customer.builder()
                        .name(name)
                        .email(name + "@gmail.com")
                        .build());
            });
        };
    }
}
