package org.fanidiyassine.ebankservice.services;

import org.fanidiyassine.ebankservice.entities.BankAccount;
import org.fanidiyassine.ebankservice.repositories.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EbankService {
    private BankAccountRepository bankAccountRepository;

    public EbankService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public List<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.findAll();
    }
    public BankAccount getBankAccountById(String id) {
        return bankAccountRepository.findById(id).orElseThrow(()->new RuntimeException("Bank account not found"));
    }
    public BankAccount saveBankAccount(BankAccount bankAccount) {
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setCreatedAt(new Date());
        return bankAccountRepository.save(bankAccount);
    }
}
