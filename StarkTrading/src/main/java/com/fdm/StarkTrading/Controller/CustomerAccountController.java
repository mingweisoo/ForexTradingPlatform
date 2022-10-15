package com.fdm.StarkTrading.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdm.StarkTrading.Service.AccountService;
import com.fdm.StarkTrading.Service.CurrencyService;
import com.fdm.StarkTrading.Service.CustomerService;
import com.fdm.StarkTrading.model.Account;
import com.fdm.StarkTrading.model.Customer;
import com.fdm.StarkTrading.model.Wallet;

@Controller
public class CustomerAccountController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CurrencyService currencyService;
	
	@Autowired
	private AccountService accountService;

	@GetMapping("/dashboard")
	public String homePage(Model model, HttpSession session) {

		String username = (String) session.getAttribute("username");

		Customer customer = customerService.retrieveCustomer(username);
		Account account = customer.getAccount();
		session.setAttribute("account", account.getAccountNumber());

		model.addAttribute("customer", customer);
		model.addAttribute("account", customer.getAccount());

		List<Wallet> wallets = (List<Wallet>) accountService.getAccountWallets(customer.getAccount());
		model.addAttribute("listOfWallets", wallets);

		return "dashboard";
	}
	
	@GetMapping("/rates")
	public String ratesPage(Model model, HttpSession session) {

		String username = (String) session.getAttribute("username");

		Customer customer = customerService.retrieveCustomer(username);

		model.addAttribute("customer", customer);
		model.addAttribute("account", customer.getAccount());

		List<Wallet> wallets = (List<Wallet>) accountService.getAccountWallets(customer.getAccount());
		model.addAttribute("listOfWallets", wallets);

		return "rates";
	}
	
	@GetMapping("/viewPreferredCurrencyWallet")
	public String viewPreferredCurrencyWallet(@RequestParam String baseCurrency, Model model, HttpSession session) throws IOException, InterruptedException {
		
		String username = (String) session.getAttribute("username");

		Customer customer = customerService.retrieveCustomer(username);

		model.addAttribute("customer", customer);
		model.addAttribute("account", customer.getAccount());

		List<Wallet> wallets = (List<Wallet>) accountService.getAccountWallets(customer.getAccount());
		model.addAttribute("listOfWallets", wallets);
		
		model.addAttribute("baseCurrency", baseCurrency);
		
		Float totalValueOfPortfolio = accountService.getTotalValueOfPortfolio(baseCurrency, wallets);
		
		model.addAttribute("totalValueOfPortfolio", totalValueOfPortfolio);
		
		return "viewPreferredCurrencyWallet";
	}
	
	@GetMapping("/wallet")
    public String walletList(Model model, HttpSession session){
        
        String username = (String) session.getAttribute("username");
        Customer customer = customerService.retrieveCustomer(username);
        Account account = customer.getAccount();
        List<Wallet> wallets = (List<Wallet>) accountService.getAccountWallets(account);
        model.addAttribute("wallets", wallets);
        
        return "wallet";        
        
    }
    
    @GetMapping("/withdraw")
    public String withdrawpage(Model model,HttpSession session){
        String username = (String) session.getAttribute("username");
        Customer customer = customerService.retrieveCustomer(username);
        Account account = customer.getAccount();
        List<Wallet> wallets = (List<Wallet>) accountService.getAccountWallets(account);
        model.addAttribute("wallets", wallets);
        return "withdraw";
        
        
    }
    
    @PostMapping("/withdraw")
    public String withdraw(@RequestParam String currency,@RequestParam double amount, HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        Customer customer = customerService.retrieveCustomer(username);
        long accountNumber = customer.getAccount().getAccountNumber();
        Account account = customer.getAccount();
        Wallet wallet = accountService.retrieveWallet(account, currency);
        if(wallet.getAmount() < amount) {
            String error = "error";
            model.addAttribute("error", error);
            
            List<Wallet> wallets = (List<Wallet>) accountService.getAccountWallets(account);
            model.addAttribute("wallets", wallets);
            
            return "withdraw";    
        }else {
        accountService.withdraw(account, currency, amount);
        return "redirect:/wallet";
        }
    }
    
    @GetMapping("/deposit")
    public String depositpage(Model model,HttpSession session) throws IOException, InterruptedException{
        Map<String,Float> rates = currencyService.getAllRates("USD");
        ArrayList<String> currencies = new ArrayList<>();
        for (var entry : rates.entrySet()) {
            currencies.add(entry.getKey());
        }
        
        model.addAttribute("currencies", currencies);
        return "deposit";
        
        
    }
    
    @PostMapping("/deposit")
    public String deposit(@RequestParam String currency,@RequestParam double amount, HttpSession session) {
        boolean contains = false;
        String username = (String) session.getAttribute("username");
        Customer customer = customerService.retrieveCustomer(username);
        long accountNumber = customer.getAccount().getAccountNumber();
        Account account = customer.getAccount();
        accountService.deposit(account, currency, amount);
        
        return "redirect:/wallet";
    }
	
	@GetMapping("/marketRates")
	public String goToMarketRates(@RequestParam String baseCurrency, Model model, HttpSession session) throws IOException, InterruptedException {
		
		Map<String, Float> allRates = currencyService.getAllRates(baseCurrency);
		session.getAttribute(baseCurrency);
		model.addAttribute("baseCurrency", baseCurrency);
		model.addAttribute("allRates", allRates);
		
		return "marketRates";
	}
	
}
