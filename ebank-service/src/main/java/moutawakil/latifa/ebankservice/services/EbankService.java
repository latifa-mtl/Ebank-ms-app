package moutawakil.latifa.ebankservice.services;

import moutawakil.latifa.ebankservice.entities.BankAccount;
import moutawakil.latifa.ebankservice.feign.CustomerRestClient;
import moutawakil.latifa.ebankservice.model.Customer;
import moutawakil.latifa.ebankservice.repositories.BankAccountRepository;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EbankService {
    private BankAccountRepository accountRepository;
    private CustomerRestClient customerRestClient;

    public EbankService(BankAccountRepository accountRepository, CustomerRestClient customerRestClient){
        this.accountRepository=accountRepository;
        this.customerRestClient=customerRestClient;
    }

    @McpTool(description = "Get all bank accounts")
    public List<BankAccount> getAllBankAccounts(){
        return accountRepository.findAll();
    }

    @McpTool(description = "Get a bank account by id")
    public BankAccount getBankAccountById(@McpToolParam(description = "bank account id") String id) {
        BankAccount bankAccount=accountRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Account Not Found"));
        bankAccount.setCustomer(customerRestClient.getCustomerById(bankAccount.getCustomerId()));
        return  bankAccount;
    }

    @McpTool(description = "Save a new bank account")
    public BankAccount save(@McpToolParam(description = "the bank account to save (balance,type,customerId)") BankAccount bankAccount) {
        try {
//            Customer customer=customerRestClient.getCustomerById(bankAccount.getCustomerId());
            bankAccount.setId(UUID.randomUUID().toString());
            bankAccount.setCreatedAt(new Date());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return accountRepository.save(bankAccount);
    }
}
