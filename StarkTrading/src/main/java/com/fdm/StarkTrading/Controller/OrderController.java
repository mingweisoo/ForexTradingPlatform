package com.fdm.StarkTrading.Controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fdm.StarkTrading.Service.AccountService;
import com.fdm.StarkTrading.Service.CurrencyService;
import com.fdm.StarkTrading.Service.OrderService;
import com.fdm.StarkTrading.model.Account;
import com.fdm.StarkTrading.model.Order;
import com.fdm.StarkTrading.model.StatusType;
import com.fdm.StarkTrading.model.Wallet;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private AccountService accountService;
	

	@GetMapping("/orders")
	public String getOrdersPage(Model model) {

		List<Order> orders = orderService.getOrderList();
		for (int i=0; i < orders.size(); i++) {
			Order order = orders.get(i);
			if (order.getExpiryDate().compareTo(LocalDateTime.now()) <= 0) {
				order.setStatus(StatusType.expired);
			}
		}
		
		List<Order> sortedOrders = orders.stream()
				.sorted(
						(order1,order2) -> {
						return (order1.getStatus().getRank() - order2.getStatus().getRank());
						}).toList();
		
		model.addAttribute("orders", sortedOrders);

		return "orders";
	}
	
	@GetMapping("/orders/createorder")
	public String createOrderPage(@ModelAttribute Order order) {		
		return "createorder";
	}
	
	@GetMapping("/orders/createorder/spot")
	public String createOrderPageForSpot(Model model, HttpSession session) {		
		
		Order order = new Order();
		Long accountID = (long) session.getAttribute("account");
		Account account = accountService.getAccount(accountID);
		
		model.addAttribute("order",order);
		model.addAttribute("wallets",accountService.getAccountWallets(account));
				
		return "createSpotOrder";
	}
	
	@GetMapping("/orders/createorder/limit")
	public String createOrderPageForLimit(Model model, HttpSession session) {		
		
		Order order = new Order();
		Long accountID = (long) session.getAttribute("account");
		Account account = accountService.getAccount(accountID);
		
		model.addAttribute("order",order);
		model.addAttribute("wallets",accountService.getAccountWallets(account));
				
		return "createLimitOrder";
	}
	
	@PostMapping("/limitOrders")
	public String AddLimitOrder(Order order, Model model, HttpSession session) throws IOException, InterruptedException {
		
		Long accountID = (long) session.getAttribute("account");
		Account account = accountService.getAccount(accountID);
		order.setAccount(account);
		
		CurrencyService service = new CurrencyService();
		Wallet balance = accountService.getTraderWallet(order);
		model.addAttribute("wallets",accountService.getAccountWallets(account));
		
		Boolean isWithinBalance = true;
		if (order.getOrderRate() == 0) {
			model.addAttribute("zeroRate", "Exchange rate must be greater than zero");
			return "createLimitOrder";
		}
		else {
			isWithinBalance = order.getVolume()/order.getOrderRate() <= balance.getAmount();
		}
		
		Boolean PositiveVolume = order.getVolume() > 0;
		
		if (!isWithinBalance) {
			model.addAttribute("wallets",accountService.getAccountWallets(account));
			model.addAttribute("lowBalance", "There is no enough amount in balance for this transaction");
			
			return "createLimitOrder";
		}
		else if (!PositiveVolume) {
			model.addAttribute("wallets",accountService.getAccountWallets(account));
			model.addAttribute("PositiveVolume", "Please enter a positive volume for this transaction");
			
			return "createLimitOrder";
		}
		else {
			service.setOrderRate(order);
			order.setExpiryDate(LocalDateTime.now().plusDays(15));
			order.setStatus(StatusType.pending);
			
			orderService.saveOrder(order);
			
			return "redirect:/orders";
		}
	}
	
	@PostMapping("/spotOrders")
	public String AddSpotOrder(Order order, Model model, HttpSession session) throws IOException, InterruptedException {
		
		Long accountID = (long) session.getAttribute("account");
		Account account = accountService.getAccount(accountID);
		order.setAccount(account);
		
		CurrencyService service = new CurrencyService();
		Wallet balance = accountService.getTraderWallet(order);
		String wallet = order.getFromCurrency();
		String target = order.getToCurrency();
		float rate = service.getRate(wallet, target);
		Boolean isWithinBalance = order.getVolume()/rate <= balance.getAmount();
		
		Boolean PositiveVolume = order.getVolume() > 0;
		
		if (!isWithinBalance) {			
			model.addAttribute("wallets",accountService.getAccountWallets(account));
			model.addAttribute("lowBalance", "There is no enough amount in balance for this transaction");
			
			return "createSpotOrder";
		}
		else if (!PositiveVolume) {
			model.addAttribute("wallets",accountService.getAccountWallets(account));
			model.addAttribute("PositiveVolume", "Please enter a positive volume for this transaction");
			
			return "createSpotOrder";
		}
		else {
			service.setOrderRate(order);
			order.setExpiryDate(LocalDateTime.now().plusDays(15));
			order.setStatus(StatusType.pending);
			
			orderService.saveOrder(order);
			
			return "redirect:/orders";
		}
		
	}
	
	@GetMapping("/orders/{id}")
	public String getTradePage(@PathVariable("id") long OrderId, Model model, HttpSession session, RedirectAttributes ra) {

		Order order = orderService.getOrder(OrderId);
		session.setAttribute("orderid", OrderId);
		
		Long accountID = (long) session.getAttribute("account");
		Account account = accountService.getAccount(accountID);
		Wallet balance = accountService.getUserWallet(account, order);
		String side = order.getOrderType().getSide().toLowerCase();
		Boolean hasSufficientFund = true;
		
		if (side.equals("sell")) {
			hasSufficientFund = balance.getAmount() >= order.getVolume() / order.getOrderRate();
		}
		else {
			hasSufficientFund = balance.getAmount() >= order.getVolume();
		}
		
		
		if(hasSufficientFund && order.getStatus() == StatusType.pending) {
			model.addAttribute("order", order);
			session.setAttribute("order", OrderId);
			return "trade";
		}
		else if (order.getStatus() == StatusType.success) {
			ra.addFlashAttribute("orderSuccess","Cannot execute an order that is already successfully executed!");
			return "redirect:/orders";
		}
		else if (order.getStatus() == StatusType.expired) {
			ra.addFlashAttribute("orderExpired","Cannot execute an order that is already expired!");
			return "redirect:/orders";
		}
		else {
			ra.addFlashAttribute("insufficientFunds","You are not able to execute this order!");
			return "redirect:/orders";
		}	
	}

	@PostMapping("/dashboard")
	public String executeTrade(HttpSession session) {
		
		Long orderID = (long) session.getAttribute("orderid");
		Order order = orderService.getOrder(orderID);
		Long accountID = (long) session.getAttribute("account");
		
		Account userAccount = accountService.getAccount(accountID);
		Account traderAccount = order.getAccount();
		String userWantedCurrency = order.getFromCurrency();
		String traderWantedCurrency = order.getToCurrency();
		int volume = order.getVolume();
		String side = order.getOrderType().getSide().toLowerCase();
		
		if (side.equals("buy")) {
			accountService.deposit(userAccount, userWantedCurrency, volume);
			accountService.withdraw(userAccount, traderWantedCurrency, volume);
			accountService.deposit(traderAccount, traderWantedCurrency, volume);
			accountService.withdraw(traderAccount, userWantedCurrency, volume);
		}
		else {
			accountService.deposit(userAccount, userWantedCurrency, volume);
			accountService.withdraw(userAccount, traderWantedCurrency, volume);
			accountService.deposit(traderAccount, traderWantedCurrency, volume);
			accountService.withdraw(traderAccount, userWantedCurrency, volume);
		}
		
		order.setStatus(StatusType.success);
		orderService.updateOrder(order);
		
		return "redirect:/dashboard";
	}
	
}
