package com.fdm.StarkTrading.Controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fdm.StarkTrading.Service.CustomerService;
import com.fdm.StarkTrading.model.Account;
import com.fdm.StarkTrading.model.Customer;

@Controller
public class CustomerInterfaceController {

	@Autowired
	private CustomerService customerService;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@GetMapping("/")
	public String GotoIndexPage() {

		return "index";
	}
	
	@GetMapping("/login")
	public String goToLoginPage(@ModelAttribute Customer customer) {
		return "login";
	}
	

	@PostMapping("/login")
	public String attemptLogIn(@RequestParam String username, @RequestParam("password") String pw, HttpSession session,
			Model model) {

		boolean isVerified = customerService.verifyUser(username, pw);

		if (isVerified) {
			session.setAttribute("username", username);
			return "redirect:/dashboard";
		} else {
			String error = "error";
			model.addAttribute("error", error);
			return "/login";
		}
	}


	@GetMapping("/register")
	public String goToRegisterPage(@ModelAttribute Customer customer, Model model) {

		model.addAttribute("customer", customer);
		System.out.println("register page");
		return "register";
	}

	@PostMapping("/register")
	public String registerCustomer(@Valid Customer customer, BindingResult bindingResult, RedirectAttributes ra) {
		//check if customer exists 
		if (customerService.customerExists(customer.getCustomerUsername())) {
			bindingResult.addError(new FieldError("customer", "customerUsername", "Username already taken"));
			System.out.println("customer exists");

		}
		
		//check if passwords match
		if(customer.getCustomerPassword() !=null && customer.getCustomerConfirmPassword() != null) {
			if(!customer.getCustomerPassword().equals(customer.getCustomerConfirmPassword())) {
				bindingResult.addError(new FieldError("customer","customerConfirmPassword","Passwords entered do not match!"));
			}
		}
		
		//check if username field is empty
		if(customer.getCustomerUsername() == null) {
			bindingResult.addError(new FieldError("customer","customerUsername","Username cannot be empty"));
		}

		//check if password field is empty
		if(customer.getCustomerPassword() == null) {
			bindingResult.addError(new FieldError("customer","customerPassword","Password cannot be empty"));
		}
		
		//check if confirm password field is empty
		if(customer.getCustomerConfirmPassword() == null) {
			bindingResult.addError(new FieldError("customer","customerConfirmPassword","Confirm password cannot be empty"));
		}
		
		//check if first name is empty
		if(customer.getCustomerFirstName() == null) {
			bindingResult.addError(new FieldError("customer","customerFirstName","First Name cannot be empty"));
		}
		
		//check if last name is empty
		if(customer.getCustomerLastName() == null) {
			bindingResult.addError(new FieldError("customer","customerLastName","Last Name cannot be empty"));
		}
		
		if (bindingResult.hasErrors()) {
			System.out.println("form has errors!!!");
			return "register";
		}
		ra.addFlashAttribute("message","You have successfully created an account!");
		Account defaultAccount = new Account();
		customer.setAccount(defaultAccount);
		customerService.registerCustomer(customer);
		System.out.println(customer.toString());
		System.out.println("customer added successfully");
		return "redirect:/login";
	}
	
	@GetMapping("/logout")
	public String logOut(HttpSession session) {
		
		session.invalidate();
		
		return "redirect:/";
	}

}
	
