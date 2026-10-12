package moutawakil.latifa.ebankservice.services;

import moutawakil.latifa.ebankservice.entities.BankAccount;
import moutawakil.latifa.ebankservice.repositories.BankAccountRepository;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EbankService {
    private BankAccountRepository accountRepository;

    public EbankService(BankAccountRepository accountRepository){
        this.accountRepository=accountRepository;
    }

    public List<BankAccount> getAllBankAccounts(){
        return accountRepository.findAll();
    }

    public BankAccount getBankAccountById(String id){
        return accountRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Account Not Found"));
    }

    public BankAccount save(BankAccount bankAccount){
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setCreatedAt(new Date());
        return accountRepository.save(bankAccount);
    }
}
