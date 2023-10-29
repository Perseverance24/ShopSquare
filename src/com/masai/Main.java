package com.masai;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.masai.Service.CustomerServImpls;
import com.masai.Service.CustomerService;
import com.masai.Service.ProductServImpls;
import com.masai.Service.ProductService;
import com.masai.Service.PurchaseServImpls;
import com.masai.Service.PurchaseService;
import com.masai.Service.VendorServImpls;
import com.masai.Service.VendorService;
import com.masai.entity.Customer;
import com.masai.entity.Product;
import com.masai.entity.Purchase;
import com.masai.entity.User;
import com.masai.entity.Vendor;
import com.masai.exceptions.DuplicateDataException;
import com.masai.exceptions.InvalidDetailsException;
import com.masai.exceptions.ProductException;
import com.masai.exceptions.TransactionException;
import com.masai.utility.Admin;
import com.masai.utility.FileExists;
import com.masai.utility.IdGeneration;

public class Main {

		private static void adminFunctionality(Scanner sc, Map<Integer, Product> products, Map<String, Customer> customers,
				List<Purchase> purchases,Map<String,Vendor> vendors) throws InvalidDetailsException, ProductException, TransactionException {

			adminLogin(sc);

			
			CustomerService custService = new CustomerServImpls();
			ProductService prodService = new ProductServImpls();
			PurchaseService purchaseService = new PurchaseServImpls();
			VendorService vendorService = new VendorServImpls();
			int choice = 0;
			try {
				do {
					System.out.println("Press 1 to Add Product");
					System.out.println("Press 2 to View All Products");
					System.out.println("Press 3 to Delete Product");
					System.out.println("Press 4 to Update Product");
					System.out.println("Press 5 to View All Customers");
					System.out.println("Press 6 to View All Purchases");
					System.out.println("Press 7 to Log Out");
					choice = sc.nextInt();

					switch (choice) {
					case 1:
						String added = adminAddProduct(sc, products, prodService);
						System.out.println(added);
						break;
					case 2:

						adminViewAllProducts(products, prodService);
						break;
					case 3:

						adminDeleteProduct(sc, products, prodService);
						break;
					case 4:

						String upt = adminUpdateProduct(sc, products, prodService);
						System.out.println(upt);
						break;
					case 5:
						adminViewAllCustomers(customers, custService);

						break;
					case 6:
						adminViewAllPurchases(purchases, purchaseService);
						break;
						
					case 7:
						System.out.println("admin has successfully logout");
						break;
					default:
						throw new IllegalArgumentException("Unexpected value: " + choice);
					}

				} while (choice <= 6);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	
		public static void adminLogin(Scanner sc) throws InvalidDetailsException {

			System.out.println("Enter Username");
			String userName = sc.next();
			System.out.println("Enter Password");
			String password = sc.next();
			if (userName.equals(Admin.username) && password.equals(Admin.password)) {

				System.out.println("Successfully logged-In!");
			} else {
				throw new InvalidDetailsException("Inva7lid Admin Credentials");
			}
		}
	
		public static String adminAddProduct(Scanner sc, Map<Integer, Product> products, ProductService prodService) {

			String str = null;
			System.out.println("Enter Product Details");
			System.out.println("Enter Product Name");
			String name = sc.next();
			System.out.println("Enter Quantity");
			int qty = sc.nextInt();
			System.out.println("Enter Product Price");
			double price = sc.nextDouble();
			System.out.println("Enter Product Category");
			String cate = sc.next();
			
			System.out.println("Enter Vendor Name");
			String vendorName = sc.next();

			Product prod = new Product(IdGeneration.generateId(), name, qty, price, cate, vendorName);
			str = prodService.addProduct(prod, products);

			return str;

		}
	
		public static void adminViewAllProducts(Map<Integer, Product> products, ProductService prodService)
				throws ProductException {
			prodService.viewAllProducts(products);
		}
		
		public static void adminDeleteProduct(Scanner sc, Map<Integer, Product> products, ProductService prodService)
				throws ProductException {
			System.out.println("Enter Id to delete product");
			int id = sc.nextInt();
			prodService.deleteProduct(id, products);
		}
		
		public static String adminUpdateProduct(Scanner sc, Map<Integer, Product> products, ProductService prodService)
				throws ProductException {
			String result = null;
			System.out.println("Enter Id to update product");
			int id = sc.nextInt();
			System.out.println("Enter Details");

			System.out.println("Enter Product Name");
			String name = sc.next();

			System.out.println("Enter Product Quantity");
			int qty = sc.nextInt();

			System.out.println("Enter Product Price");
			double price = sc.nextDouble();

			System.out.println("Enter Product Category");
			String cate = sc.next();

			System.out.println("Enter Vendor Name");
			String vendorName = sc.next();
			
			System.out.println("Successfully Updated");
			
			Product update = new Product(id, name, qty, price, cate,vendorName);

			result = prodService.updateProduct(id, update, products);
			return result;
		}

		public static void adminViewAllCustomers(Map<String, Customer> customers, CustomerService custService)
				throws ProductException {
			List<Customer> list = custService.viewAllCustomers(customers);

			for (Customer c : list) {
				System.out.println(c);
			}
		}

		public static void adminViewAllPurchases(List<Purchase> purchases, PurchaseService purchaseService) 
				throws TransactionException {
			List<Purchase> allPurchases = purchaseService.viewAllPurchase(purchases);
			
			for(Purchase pr : allPurchases) {
				System.out.println(pr);
			}
		}
	
		// Functionality
		public static void customerFunctionality(Scanner sc, Map<String, Customer> customers,
				Map<Integer, Product> products, List<Purchase> purchases,Map<String,Vendor> vendors)
				throws InvalidDetailsException, TransactionException {

			CustomerService custService = new CustomerServImpls();
			ProductService prodService = new ProductServImpls();
			PurchaseService purchaseService = new PurchaseServImpls();
			VendorService venService = new VendorServImpls();

			//login
			System.out.println("Enter Login Details Below:");
			System.out.println("Enter Email Below:");
			String email = sc.next();
			System.out.println("Enter Password Below:");
			String pass = sc.next();
			customerLogin(email, pass, customers, custService);

			try {
				int choice = 0;
				do {
					System.out.println("Select from option below:");
					System.out.println("Press 1 to View All Products");
					System.out.println("Press 2 to Buy a Product");
					System.out.println("Press 3 to Add Money to a Wallet");
					System.out.println("Press 4 to View Wallet Balance");
					System.out.println("Press 5 to View My Details");
					System.out.println("Press 6 to View My Purchases");
					System.out.println("Press 7 to Update Details");
					System.out.println("Press 8 to Delete Account");
					System.out.println("Press 9 to Logout");
					choice = sc.nextInt();

					switch (choice) {
					case 1:
						customerViewAllProducts(products, prodService);
						break;
					case 2:
						String result = customerBuyProduct(sc, email, products, customers, purchases, custService,vendors);
						System.out.println(result);
						break;
					case 3:
						String moneyAdded = customerAddMoneyToWallet(sc, email, customers, custService);
						System.out.println(moneyAdded);
						break;
					case 4:
						double walletBalance = customerViewWalletBalance(email, customers, custService);
						System.out.println("Wallet balance is: " + walletBalance);
						break;
					case 5:
						customerViewMyDetails(email, customers, custService);
						break;
					case 6:
						customerViewCustomerPurchases(email, purchases, purchaseService);
						break;
					case 7:
						customerUpdateDetails(sc,customers,custService);
						break;
					case 8:
						customerDeleteAccount(sc,customers,custService);
						break;
					case 9:
						System.out.println("Successsfully Logged Out.");
						break;
					default:
						System.out.println("Invalid Entry");
						break;
					}

				} while (choice <= 6);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		private static void customerDeleteAccount(Scanner sc, Map<String, Customer> customers,
				CustomerService custService) throws InvalidDetailsException {
	
			System.out.println("Enter e-mail to delete account.");
			String email = sc.next();
			custService.deleteAccount(email, customers);
		}

		private static String customerUpdateDetails(Scanner sc, Map<String, Customer> customers,
				CustomerService custService) throws InvalidDetailsException {

			String result = null;
			System.out.println("Enter email to update product.");
			String email = sc.next();
			System.out.println("Enter details to update:");
			

			System.out.println("Enter Name");
			String name = sc.next();

			System.out.println("Enter Mobile Number");
			String address = sc.next();

			System.out.println("Enter New Password");
			String pass = sc.next();


			Customer update = new Customer(email, name, address,pass);

			result = custService.updateDetails(email, update, customers);
			return result;
		}

		public static void customerSignup(Scanner sc, Map<String, Customer> customers) throws DuplicateDataException {
			System.out.println("Enter details to Signup:");
			System.out.println("Enter Username");
			String name = sc.next();
			System.out.println("Enter Password");
			String pass = sc.next();
			System.out.println("Enter Address");
			String address = sc.next();
			System.out.println("Enter Email");
			String email = sc.next();
			System.out.println("Enter balance to add in the wallet");
			double balance = sc.nextDouble();
			Customer cus = new Customer(balance, name, pass, address, email);

			CustomerService custService = new CustomerServImpls();
			custService.signup(cus, customers);
			System.out.println("Signed Up Successfully!");

		}

		public static void customerLogin(String email,String pass, Map<String, Customer> customers, CustomerService custService)
				throws InvalidDetailsException {
			custService.login(email, pass,customers);
			System.out.println("Customer Logged-In");

		}

		public static void customerViewAllProducts(Map<Integer, Product> products, ProductService prodService)
				throws ProductException {
			prodService.viewAllProducts(products);
		}

		public static String customerBuyProduct(Scanner sc, String email, Map<Integer, Product> products,
				Map<String, Customer> customers, List<Purchase> purchases, CustomerService custService,
				Map<String,Vendor> vendor)
				throws InvalidDetailsException, ProductException {
			System.out.println("Enter Product-Id");
			int id = sc.nextInt();
			System.out.println("Enter quantity to buy:");
			int qty = sc.nextInt();
			custService.buyProduct(id, qty, email, products, customers, purchases, vendor);

			return "Product purchased successfully!";

		}

		public static String customerAddMoneyToWallet(Scanner sc, String email, Map<String, Customer> customers,
				CustomerService custService) {
			System.out.println("Please enter the amount");
			double money = sc.nextDouble();
			boolean added = custService.addMoneyToWallet(money, email, customers);

			return "Amount: " + money + " successfully added to the wallet";
		}

		public static double customerViewWalletBalance(String email, Map<String, Customer> customers,
				CustomerService custService) {
			double walletBalance = custService.viewWalletBalance(email, customers);
			return walletBalance;
		}

		public static void customerViewMyDetails(String email, Map<String, Customer> customers,
				CustomerService custService) {
			Customer cus = custService.viewCustomerDetails(email, customers);
			System.out.println("Name : " + cus.getUsername());
			System.out.println("Address : " + cus.getAddress());
			System.out.println("E-mail : " + cus.getEmail());
			System.out.println("Wallet Balance : " + cus.getWalletBalance());
		}

		public static void customerViewCustomerPurchases(String email, List<Purchase> purchases,
				PurchaseService purchaseService) throws TransactionException {
			
			List<Purchase> myPurchases = purchaseService.viewCustomerPurchase(email, purchases);

			for (Purchase tr : myPurchases) {
				System.out.println(tr);
			}
		}
		
// Vendor
		public static void vendorFunctionality(Scanner sc, Map<String, Customer> customers,
				Map<Integer, Product> products, List<Purchase> purchases,Map<String,Vendor> vendors)
				throws InvalidDetailsException, TransactionException {

			CustomerService custService = new CustomerServImpls();
			ProductService prodService = new ProductServImpls();
			PurchaseService purchaseService = new PurchaseServImpls();
			VendorService venService = new VendorServImpls();

			// Vendor
			System.out.println("Enter the following details to login");
			System.out.println("Enter E-mail");
			String email = sc.next();
			System.out.println("Enter Password");
			String pass = sc.next();
			vendorLogin(email,pass, vendors, venService);

			try {
				int choice = 0;
				do {
					System.out.println("Select the option of your choice");
					System.out.println("Press 1 to View My Products");
					System.out.println("Press 2 to View My Details");
					System.out.println("Press 3 to View Purchase History");
					System.out.println("Press 4 to Add Product");
					System.out.println("Press 5 to Update Product");
					System.out.println("Press 6 to Delete Product");
					System.out.println("Press 7 to Logout");
					choice = sc.nextInt();

					switch (choice) {
					case 1:
						vendorViewAllProducts(sc,products, prodService);
						break;

					case 2:
						vendorViewMyDetails(email, vendors, venService);
						break;
					case 3:
						vendorViewPurchasesHistory(email, purchases, purchaseService);
						break;
					case 4:
						vendorAddProduct(sc,products,prodService);
						break;
					case 5:
						vendorUpdateProduct(sc,products,prodService);
						break;
					case 6: 
						deleteProduct(sc,products,prodService);
						break;
					case 7:
						System.out.println("Successsfully Logged Out");
						break;
					default:
						System.out.println("Invalid Entry.");
						break;
					}

				} while (choice <= 4);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	
	private static void deleteProduct(Scanner sc, Map<Integer, Product> products, ProductService prodService) throws ProductException {

		System.out.println("Enter Product-Id to delete product");
		int id = sc.nextInt();
		prodService.deleteProduct(id, products);
}

	private static String vendorUpdateProduct(Scanner sc, Map<Integer, Product> products, ProductService prodService) throws ProductException {

		String result = null;
		System.out.println("Enter Product-Id to Update Product Details");
		int id = sc.nextInt();
		System.out.println("Enter the details below:");

		System.out.println("Enter Product Name");
		String name = sc.next();

		System.out.println("Enter Product Quantity");
		int qty = sc.nextInt();

		System.out.println("Enter Product Price");
		double price = sc.nextDouble();

		System.out.println("Enter Product Category");
		String cate = sc.next();
		
		System.out.println("Enter Vendor Name");
		String vendor = sc.next();

		Product update = new Product(id, name, qty, price, cate,vendor);

		result = prodService.updateProduct(id, update, products);
		return result;
}

	private static String vendorAddProduct(Scanner sc, Map<Integer, Product> products, ProductService prodService) {

		String str = null;
		System.out.println("Enter Product Details");
		System.out.println("Enter Product Name");
		String name = sc.next();
		System.out.println("Enter Product Quantity");
		int qty = sc.nextInt();
		System.out.println("Enter Product Price");
		double price = sc.nextDouble();
		System.out.println("Enter Product Category");
		String cate = sc.next();
		System.out.println("Enter Vendor Name");
		String vendorName = sc.next();
		System.out.println("Product Added Successfully");

		Product prod = new Product(IdGeneration.generateId(), name, qty, price, cate,vendorName);

		str = prodService.addProduct(prod, products);

		return str;
}

	private static void vendorViewPurchasesHistory(String email, List<Purchase> purchases,
		PurchaseService purchaseService) throws TransactionException {

	
		List<Purchase> myPurchases = purchaseService.viewCustomerPurchase(email, purchases);

		for (Purchase tr : myPurchases) {
			System.out.println(tr);
		}
}

	private static void vendorViewMyDetails(String email, Map<String, Vendor> vendors, VendorService venService) {

		Vendor ven = venService.viewVendorDetails(email, vendors);
		System.out.println("Name : " + ven.getUsername());
		System.out.println("Address : " + ven.getAddress());
		System.out.println("Email : " + ven.getEmail());
		System.out.println("Mobile No. : " + ven.getMoblieNum());
		}

	private static void vendorViewAllProducts(Scanner sc, Map<Integer, Product> products,
				ProductService prodService) throws ProductException {
		prodService.viewAllProducts(products);
		}

	private static void vendorLogin(String email, String pass, Map<String, Vendor> vendors,
				VendorService venService) throws InvalidDetailsException {
		venService.login(email, pass,vendors);
		System.out.println("Vendor has successfully logged in!");
		}
	
	private static void vendorSignup(Scanner sc, Map<String, Vendor> vendors) throws DuplicateDataException {
		System.out.println("Enter following details to Signup:");
		System.out.println("Enter Username");
		String name = sc.next();
		System.out.println("Enter Password");
		String pass = sc.next();
		System.out.println("Enter Address");
		String address = sc.next();
		System.out.println("Enter Email-Id");
		String email = sc.next();
		System.out.println("Enter Mobile Number");
		String mobNum = sc.next();
		
		Vendor ven = new Vendor(name, pass, address, email,mobNum);

		VendorService venService = new VendorServImpls();
		venService.signup(ven, vendors);
		System.out.println("Vendor has succefully signed-up!");

	}

	public static void main(String[] args) {
		
		Map<Integer, Product> products = FileExists.productFile();
		Map<String, Customer> customers = FileExists.customerFile();
		Map<String, Vendor> vendors =FileExists.vendorFile();
		List<Purchase> purchases = FileExists.purchaseFile();
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Welcome to ShopSquare!!!");
		
		try {
			
			int preference = 0;
			
			do {
				System.out.println("Please enter your preference...");
				System.out.println("Press 1 for Admin Login");
				System.out.println("Press 2 for Customer Login");
				System.out.println("Press 3 for Customer Sign-Up");
				System.out.println("Press 4 for Vendor Login");
				System.out.println("Press 5 for Vendor Sign-Up");
				System.out.println("Press 0 for Exit");
				
				preference = sc.nextInt();
				switch (preference) {
				case 1:
					adminFunctionality(sc, products, customers, purchases,vendors);
					break;
				case 2:
					customerFunctionality(sc, customers, products, purchases,vendors);
					break;

				case 3:
					customerSignup(sc, customers);
					break;
				
				case 4:
					vendorFunctionality(sc,customers,products,purchases,vendors);
					break;
				
				case 5:
					vendorSignup(sc,vendors);
					break;

				case 0:
					System.out.println("Successfully Exited System!");

					break;

				default:
					throw new IllegalArgumentException("Invalid Entry");
				}

			}
			while(preference != 0);	
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			try {
				ObjectOutputStream prods = new ObjectOutputStream(new FileOutputStream("Product.ser"));
				prods.writeObject(products);
				
				ObjectOutputStream custo = new ObjectOutputStream(new FileOutputStream("Customer.ser"));
				custo.writeObject(customers);

				ObjectOutputStream purch = new ObjectOutputStream(new FileOutputStream("purchases.ser"));
				purch.writeObject(purchases);
				
				ObjectOutputStream vend = new ObjectOutputStream(new FileOutputStream("Vendor.ser"));
				vend.writeObject(vendors);
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
	}
}
