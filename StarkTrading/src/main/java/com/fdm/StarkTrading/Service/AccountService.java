package com.fdm.StarkTrading.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdm.StarkTrading.Repo.AccountRepo;
import com.fdm.StarkTrading.Repo.WalletRepo;
import com.fdm.StarkTrading.model.Account;
import com.fdm.StarkTrading.model.Order;
import com.fdm.StarkTrading.model.Wallet;

@Service
public class AccountService {

	@Autowired
	private WalletRepo walletRepo;
	
	@Autowired
	private AccountRepo accountRepo;
	
	@Autowired
	private CurrencyService currencyService;
	
	public List<Wallet> getAccountWallets(Account account) {
		return walletRepo.findWalletByAccount(account);
	}
	
	public Account getAccount(long accountID) {
		return accountRepo.findById(accountID).get();
	}
	
	public Wallet getTraderWallet(Order order) {
		
		List<Wallet> balances = getAccountWallets(order.getAccount());
		Wallet balance = new Wallet();
		
		for (int i=0; i < balances.size(); i++) {
			balance = balances.get(i);
			if (balance.getCurrency().equals(order.getFromCurrency())) {
				break;
			}
		}
		
		return balance;
	}
	
	public Wallet getUserWallet(Account userAccount, Order trade) {
		
		List<Wallet> balances = getAccountWallets(userAccount);
		Wallet balance = new Wallet();
		
		for (int i=0; i < balances.size(); i++) {
			balance = balances.get(i);
			if (balance.getCurrency().equals(trade.getToCurrency())) {
				break;
			}
		}
		
		return balance;
	}

   public void deposit(Account account, String currency, double amount) {
		List<Wallet> allWallets = getAccountWallets(account);
        boolean contains = false;
        for (Wallet wallet : allWallets) {
            if (wallet.getCurrency().equals(currency)) {
                contains = true;
            }
        }
        if (contains) {
            Wallet wallet = walletRepo.getWallet(account,currency);
            wallet.setAmount(amount + wallet.getAmount());
            walletRepo.saveAndFlush(wallet);
        } else if (amount > 0){
            Wallet wallet = new Wallet();
            wallet.setAmount(amount);
            wallet.setCurrency(currency);
            wallet.setAccount(account);
            walletRepo.save(wallet);
            
        }
    }

   public void withdraw(Account account, String currency, double amount) {
        
	    Wallet wallet = walletRepo.getWallet(account, currency); 
        double residuals = wallet.getAmount() - amount;
        if (residuals > 0) {
        	wallet.setAmount(residuals);
            walletRepo.saveAndFlush(wallet);
        }
        else {
        	walletRepo.delete(wallet);
        }
        
    }

   public Wallet retrieveWallet(Account account, String currency) {
        return walletRepo.getWallet(account, currency);
    }

	public Float getTotalValueOfPortfolio(String baseCurrency, List<Wallet> wallets) throws IOException, InterruptedException {
		Float totalValue = 0f;
		for (Wallet wallet : wallets) {
			String FromCurrency = wallet.getCurrency();
			Float newAmount = (float) (wallet.getAmount() * currencyService.getRate(FromCurrency, baseCurrency));
			totalValue += newAmount;
		}
				
		return totalValue;
	}

}

