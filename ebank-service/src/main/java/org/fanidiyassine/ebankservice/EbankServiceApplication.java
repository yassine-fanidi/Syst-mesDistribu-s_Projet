package org.fanidiyassine.ebankservice;

import org.fanidiyassine.ebankservice.entities.BankAccount;
import org.fanidiyassine.ebankservice.repositories.BankAccountRepository;
import org.fanidiyassine.ebankservice.services.EbankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class EbankServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(EbankService ebankService) {
		return args -> {
			for(int i = 1; i <= 3; i++) {
				for(int j = 0; j < 5; j++) {
					ebankService.saveBankAccount(BankAccount.builder()
									.balance(1000+Math.random()*60000)
									.type(Math.random()>0.5?"CURRENT-ACCOUNT":"SAVING-ACCOUNT")
									.customerId(i)
							.build());
				}
			}
		};
	}
}
