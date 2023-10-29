package com.masai.Service;

import java.util.List;
import java.util.Map;

import com.masai.entity.Product;
import com.masai.entity.Purchase;
import com.masai.entity.Vendor;
import com.masai.exceptions.DuplicateDataException;
import com.masai.exceptions.InvalidDetailsException;

public class VendorServImpls implements VendorService{

	

	@Override
	public void signup(Vendor ven, Map<String, Vendor> vendors) throws DuplicateDataException {
		if (vendors.containsKey(ven.getEmail())) {
			throw new DuplicateDataException("Customer already exists , please login");
		} else {

			vendors.put(ven.getEmail(), ven);

		}
	}

	@Override
	public boolean login(String email, String password, Map<String, Vendor> vendors) throws InvalidDetailsException {
		if(vendors.containsKey(email)) {
			if(vendors.get(email).getPassword().equals(password)) {
				return true;
			}
			else {
				throw new InvalidDetailsException("Invalid Credentials");
			}
		}
		 else {
				throw new InvalidDetailsException("you have not sign up yet, please signup");
			}
	}


	@Override
	public String addProduct(Map<Integer, Product> products, Product prod) throws InvalidDetailsException {
		products.put(prod.getId(), prod);
		return "Product added successfully";
	}


	@Override
	public Vendor viewVendorDetails(String email, Map<String, Vendor> vendors) {
		if (vendors.containsKey(email)) {
			return vendors.get(email);
		}
		return null;
	}
}
