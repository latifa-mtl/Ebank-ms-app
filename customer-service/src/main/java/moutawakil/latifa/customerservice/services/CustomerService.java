package moutawakil.latifa.customerservice.services;

import moutawakil.latifa.customerservice.entities.Customer;
import moutawakil.latifa.customerservice.repositories.CustomerRepository;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository=customerRepository;
    }

    @McpTool(description = "Get all customers")
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    @McpTool(description = "Find customer by ID")
    public Customer findCustomerById(@McpToolParam(description = "The ID of the customer to find") Long id){
        return customerRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Customer Not Found"));

    }

    @McpTool(description = "Save a new customer")
    public Customer saveCustomer(@McpToolParam(description = "The customer to save") Customer customer){
        return customerRepository.save(customer);
    }
}
