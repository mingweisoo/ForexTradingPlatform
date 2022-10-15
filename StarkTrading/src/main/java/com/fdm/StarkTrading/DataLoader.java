package com.fdm.StarkTrading;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fdm.StarkTrading.Repo.AccountRepo;
import com.fdm.StarkTrading.Repo.CustomerRepo;
import com.fdm.StarkTrading.Repo.OrderRepo;

import com.fdm.StarkTrading.Repo.WalletRepo;
import com.fdm.StarkTrading.model.Account;

import com.fdm.StarkTrading.model.Customer;
import com.fdm.StarkTrading.model.Order;
import com.fdm.StarkTrading.model.OrderType;

import com.fdm.StarkTrading.model.StatusType;

import com.fdm.StarkTrading.model.Wallet;

@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private AccountRepo accountRepo;
	
	@Autowired
	private WalletRepo walletRepo;
	

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		Account account1 = new Account();
		
		Wallet wallet1 = new Wallet();
		wallet1.setCurrency("USD");
		wallet1.setAmount(5000.00);
		wallet1.setAccount(account1);
		
		Wallet wallet2 = new Wallet();
		wallet2.setCurrency("SGD");
		wallet2.setAmount(10.00);
		wallet2.setAccount(account1);
		
		Account account2 = new Account();

		Wallet wallet3 = new Wallet();
		wallet3.setCurrency("HKD");
		wallet3.setAmount(15000.00);
		wallet3.setAccount(account2);
		
		Wallet wallet4 = new Wallet();
		wallet4.setCurrency("SGD");
		wallet4.setAmount(5.40);
		wallet4.setAccount(account2);
		
		Account account3 = new Account();
		
		Wallet wallet5 = new Wallet();
		wallet5.setCurrency("SGD");
		wallet5.setAmount(5000.00);
		wallet5.setAccount(account3);
		
		Wallet wallet6 = new Wallet();
		wallet6.setCurrency("HKD");
		wallet6.setAmount(100.80);
		wallet6.setAccount(account3);
		
		Account account4 = new Account();
		
		Wallet wallet7 = new Wallet();
		wallet7.setCurrency("IDR");
		wallet7.setAmount(22200000.00);
		wallet7.setAccount(account4);
		
		Wallet wallet8 = new Wallet();
		wallet8.setCurrency("SGD");
		wallet8.setAmount(50.80);
		wallet8.setAccount(account4);
		
		Account account5 = new Account();
		
		Wallet wallet9 = new Wallet();
		wallet9.setCurrency("USD");
		wallet9.setAmount(4000.00);
		wallet9.setAccount(account5);
		
		Wallet wallet10 = new Wallet();
		wallet10.setCurrency("HKD");
		wallet10.setAmount(300.80);
		wallet10.setAccount(account5);
		
		Customer customer1 = new Customer();
		customer1.setCustomerUsername("JohnDoe123");
		customer1.setCustomerFirstName("John");
		customer1.setCustomerLastName("Doe");
		customer1.setCustomerPassword("JohnDoe123");
		customer1.setAccount(account1);
		customer1.setCustomerConfirmPassword("JohnDoe123");
			
		Customer customer2 = new Customer();
		customer2.setCustomerUsername("SyafiqSulaimun456");
		customer2.setCustomerFirstName("Sulaimun");
		customer2.setCustomerLastName("Sulaimun");
		customer2.setCustomerPassword("SyafiqHandsome");
		customer2.setAccount(account2);
		customer2.setCustomerConfirmPassword("SyafiqHandsome");
		
		Customer customer3 = new Customer();
		customer3.setCustomerUsername("MeiChuanHao789");
		customer3.setCustomerFirstName("ChuanHao");
		customer3.setCustomerLastName("Mei");
		customer3.setCustomerPassword("ChuanHaoIsOurBoss");
		customer3.setAccount(account3);
		customer3.setCustomerConfirmPassword("ChuanHaoIsOurBoss");
		
		Customer customer4 = new Customer();
		customer4.setCustomerUsername("AlfredNg123");
		customer4.setCustomerFirstName("Alfred");
		customer4.setCustomerLastName("Ng");
		customer4.setCustomerPassword("AlfredHandsome");
		customer4.setAccount(account4);
		customer4.setCustomerConfirmPassword("AlfredHandsome");
		
		Customer customer5 = new Customer();
		customer5.setCustomerUsername("KennyLam456");
		customer5.setCustomerFirstName("Kenny");
		customer5.setCustomerLastName("Lam");
		customer5.setCustomerPassword("KennyHandsome");
		customer5.setAccount(account5);
		customer5.setCustomerConfirmPassword("KennyHandsome");
		
		Order order1 = new Order();
		order1.setAccount(account1);
		order1.setOrderType(OrderType.spotBuy);
		order1.setStatus(StatusType.pending);
		order1.setVolume(1500);
		order1.setOrderRate(1.40993f);
		order1.setExpiryDate(LocalDateTime.of(2022, 10, 1, 11, 34));
		order1.setFromCurrency("USD");
		order1.setToCurrency("SGD");
		
		Order order2 = new Order();
		order2.setAccount(account2);
		order2.setOrderType(OrderType.spotSell);
		order2.setStatus(StatusType.pending);
		order2.setVolume(500);
		order2.setOrderRate(1/5.569399f);
		order2.setExpiryDate(LocalDateTime.of(2022, 9, 28, 4, 46));
		order2.setFromCurrency("HKD");
		order2.setToCurrency("SGD");
		
		Order order3 = new Order();
		order3.setAccount(account3);
		order3.setOrderType(OrderType.limitSell);
		order3.setStatus(StatusType.pending);
		order3.setVolume(600);
		order3.setOrderRate(0.71f);
		order3.setExpiryDate(LocalDateTime.of(2022, 9, 29, 12, 45));
		order3.setFromCurrency("SGD");
		order3.setToCurrency("USD");
		
		Order order4 = new Order();
		order4.setAccount(account3);
		order4.setOrderType(OrderType.limitBuy);
		order4.setStatus(StatusType.pending);
		order4.setVolume(200);
		order4.setOrderRate(5.6f);
		order4.setExpiryDate(LocalDateTime.of(2022, 9, 25, 17, 25));
		order4.setFromCurrency("SGD");
		order4.setToCurrency("HKD");
		
		Order order5 = new Order();
		order5.setAccount(account4);
		order5.setOrderType(OrderType.spotBuy);
		order5.setStatus(StatusType.success);
		order5.setVolume(700);
		order5.setOrderRate(0.000067f);
		order5.setExpiryDate(LocalDateTime.of(2022, 9, 27, 15, 48));
		order5.setFromCurrency("IDR");
		order5.setToCurrency("USD");
		
		Order order6 = new Order();
		order6.setAccount(account5);
		order6.setOrderType(OrderType.spotSell);
		order6.setStatus(StatusType.success);
		order6.setVolume(1200);
		order6.setOrderRate(1/0.709228f);
		order6.setExpiryDate(LocalDateTime.of(2022, 9, 28, 22, 36));
		order6.setFromCurrency("SGD");
		order6.setToCurrency("USD");
		
		Order order7 = new Order();
		order7.setAccount(account2);
		order7.setOrderType(OrderType.limitSell);
		order7.setStatus(StatusType.success);
		order7.setVolume(500);
		order7.setOrderRate(1/5.71f);
		order7.setExpiryDate(LocalDateTime.of(2022, 9, 26, 14, 54));
		order7.setFromCurrency("SGD");
		order7.setToCurrency("HKD");
		
		Order order8 = new Order();
		order8.setAccount(account5);
		order8.setOrderType(OrderType.limitBuy);
		order8.setStatus(StatusType.expired);
		order8.setVolume(200);
		order8.setOrderRate(0.128f);
		order8.setExpiryDate(LocalDateTime.of(2022, 9, 20, 14, 54));
		order8.setFromCurrency("HKD");
		order8.setToCurrency("USD");
		
		Order order9 = new Order();
		order9.setAccount(account5);
		order9.setOrderType(OrderType.limitBuy);
		order9.setStatus(StatusType.expired);
		order9.setVolume(50);
		order9.setOrderRate(0.128f);
		order9.setExpiryDate(LocalDateTime.of(2022, 9, 22, 20, 47));
		order9.setFromCurrency("HKD");
		order9.setToCurrency("USD");
		
		Order order10 = new Order();
		order10.setAccount(account5);
		order10.setOrderType(OrderType.limitBuy);
		order10.setStatus(StatusType.pending);
		order10.setVolume(50);
		order10.setOrderRate(0.128f);
		order10.setExpiryDate(LocalDateTime.of(2022, 9, 23, 14, 20));
		order10.setFromCurrency("HKD");
		order10.setToCurrency("USD");
		
		Order order11 = new Order();
		order11.setAccount(account5);
		order11.setOrderType(OrderType.limitBuy);
		order11.setStatus(StatusType.pending);
		order11.setVolume(250);
		order11.setOrderRate(0.128f);
		order11.setExpiryDate(LocalDateTime.of(2022, 10, 23, 15, 45));
		order11.setFromCurrency("HKD");
		order11.setToCurrency("USD");
		
		customerRepo.save(customer1);
		customerRepo.save(customer2);
		customerRepo.save(customer3);
		customerRepo.save(customer4);
		customerRepo.save(customer5);
		
		accountRepo.save(account1);
		accountRepo.save(account2);
		accountRepo.save(account3);
		accountRepo.save(account4);
		accountRepo.save(account5);
		
		orderRepo.save(order1);
		orderRepo.save(order2);
		orderRepo.save(order3);
		orderRepo.save(order4);
		orderRepo.save(order5);
		orderRepo.save(order6);
		orderRepo.save(order7);
		orderRepo.save(order8);
		orderRepo.save(order9);
		orderRepo.save(order10);
		orderRepo.save(order11);
		
		walletRepo.save(wallet1);
		walletRepo.save(wallet2);
		walletRepo.save(wallet3);
		walletRepo.save(wallet4);
		walletRepo.save(wallet5);
		walletRepo.save(wallet6);
		walletRepo.save(wallet7);
		walletRepo.save(wallet8);
		walletRepo.save(wallet9);
		walletRepo.save(wallet10);

	}

}
