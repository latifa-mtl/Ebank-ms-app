package moutawakil.latifa.ebankservice.services;

import moutawakil.latifa.ebankservice.entities.BankAccount;
import moutawakil.latifa.ebankservice.feign.CustomerRestClient;
import moutawakil.latifa.ebankservice.model.Customer;
import moutawakil.latifa.ebankservice.repositories.BankAccountRepository;
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

    public List<BankAccount> getAllBankAccounts(){
        return accountRepository.findAll();
    }

    public BankAccount getBankAccountById(String id){
        BankAccount bankAccount=accountRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Account Not Found"));
        bankAccount.setCustomer(customerRestClient.getCustomerById(bankAccount.getCustomerId()));
        return  bankAccount;
    }

    public BankAccount save(BankAccount bankAccount){
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
