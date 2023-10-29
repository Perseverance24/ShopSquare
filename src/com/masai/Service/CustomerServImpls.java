package com.masai.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.masai.entity.Customer;
import com.masai.entity.Product;
import com.masai.entity.Vendor;
import com.masai.entity.Purchase;
import com.masai.exceptions.DuplicateDataException;
import com.masai.exceptions.InvalidDetailsException;
import com.masai.exceptions.ProductException;

public class CustomerServImpls implements CustomerService{

	@Override
	public boolean login(String email, String password, Map<String, Customer> customers)
			throws InvalidDetailsException {
		
		if(customers.containsKey(email)) {
			if(customers.get(email).getPassword().equals(password)) {
				return true;
			}
			else {
				throw new InvalidDetailsException("Invalid Credentials");
			}
		}
		 else {
				throw new InvalidDetailsException("Please Sign-Up");
			}
	}

	@Override
	public void signup(Customer cus, Map<String, Customer> customers) throws DuplicateDataException {
		if (customers.containsKey(cus.getEmail())) {
			throw new DuplicateDataException("User already exists, please login");
		} else {

			customers.put(cus.getEmail(), cus);

		}
		
	}

	@Override
	public boolean buyProduct(int id, int qty, String email, Map<Integer, Product> products,
			Map<String, Customer> customers, List<Purchase> purchases, Map<String, Vendor> vendors)
			throws InvalidDetailsException, ProductException {
		
		if (products.size() == 0)
			throw new ProductException("Product list is empty");

		if (products.containsKey(id)) {

			Product prod = products.get(id);

			if (prod.getQty() >= qty) {

				Customer cus = customers.get(email);

				double buyingPrice = qty * prod.getPrice();

				if (cus.getWalletBalance() >= buyingPrice) {
					cus.setWalletBalance(cus.getWalletBalance() - buyingPrice);

					prod.setQty(prod.getQty() - qty);

					products.put(id, prod);

					Purchase tr = new Purchase(cus.getUsername(), email, id,prod.getName(), qty, prod.getPrice(),
							prod.getPrice() * qty, LocalDate.now());

					purchases.add(tr);

				} else {
					throw new InvalidDetailsException("Wallet balance is not sufficient");
				}

			} else {
				throw new InvalidDetailsException("Product quantity is not suffiecient");
			}

		} else {
			throw new InvalidDetailsException("Product not available with id: " + id);
		}

		return false;
		
		
	}

	@Override
	public boolean addMoneyToWallet(double amount, String email, Map<String, Customer> customers) {
		
		Customer cus = customers.get(email);

		cus.setWalletBalance(cus.getWalletBalance() + amount);

		customers.put(email, cus);

		return true;
	}

	@Override
	public double viewWalletBalance(String email, Map<String, Customer> customers) {
		Customer cus = customers.get(email);

		return cus.getWalletBalance();
	}

	@Override
	public Customer viewCustomerDetails(String email, Map<String, Customer> customers) {
		if (customers.containsKey(email)) {
			return customers.get(email);
		}
		return null;
	}

	@Override
	public List<Customer> viewAllCustomers(Map<String, Customer> customers) throws ProductException {
		List<Customer> list = null;
		if (customers != null && customers.size() > 0) {
			Collection<Customer> coll = customers.values();
			list = new ArrayList<>(coll);
		} else {
			throw new ProductException("Customer list is empty");
		}
		return list;
	}

	@Override
	public String updateDetails(String email, Customer cus, Map<String, Customer> customers)
			throws InvalidDetailsException {
			if (customers.containsKey(email)) {
				customers.put(email, cus);
				return "Details successfully updated";
			} else {
				throw new InvalidDetailsException("Customer not found");
			}
		} 

	@Override
	public void deleteAccount(String email, Map<String, Customer> customers) throws InvalidDetailsException {
			if (customers.containsKey(email)) {
				customers.remove(email);
				System.out.println("Product deleted successfully");

			} else {
				throw new InvalidDetailsException("Product not found");
			}

		}
}
