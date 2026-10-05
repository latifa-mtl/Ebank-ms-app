package moutawakil.latifa.customerservice.repositories;

import moutawakil.latifa.customerservice.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
