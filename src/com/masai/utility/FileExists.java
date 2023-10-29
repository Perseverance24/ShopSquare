package com.masai.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.masai.entity.Customer;
import com.masai.entity.Product;
import com.masai.entity.Purchase;
import com.masai.entity.Vendor;

public class FileExists {



	public static Map<Integer, Product> productFile() {

		Map<Integer, Product> prod = null;

		File f = new File("Product.ser");
		boolean flag = false;
		try {
			if (!f.exists()) {
				f.createNewFile();
				flag = true;
			}

			if (flag) {
				prod = new LinkedHashMap<>();
				ObjectOutputStream ObjOut = new ObjectOutputStream(new FileOutputStream(f));
				ObjOut.writeObject(prod);
				return prod;

			} else {
				ObjectInputStream obj = new ObjectInputStream(new FileInputStream(f));
				prod = (Map<Integer, Product>) obj.readObject();
				return prod;

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return prod;
	}

	public static Map<String, Customer> customerFile() {
		Map<String, Customer> cust = null;
		File f = new File("Customer.ser");
		boolean flag = false;
		try {
			if (!f.exists()) {
				f.createNewFile();
				flag = true;
			}

			if (flag) {
				
				cust = new LinkedHashMap<>();
				ObjectOutputStream ObjOut = new ObjectOutputStream(new FileOutputStream(f));
				ObjOut.writeObject(cust);
				return cust;

			} else {
				
				ObjectInputStream obj = new ObjectInputStream(new FileInputStream(f));
				cust = (Map<String, Customer>) obj.readObject();

				return cust;

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return cust;

	}

	public static Map<String, Vendor> vendorFile(){
		
		Map<String, Vendor> venFile = null;
		
		File vF = new File("Vendor.ser");
		boolean flag = false;
		
		try {
			if(!vF.exists()) {
				vF.createNewFile();
				flag = true;
			}
			if(flag) {
				venFile = new LinkedHashMap<>();
				ObjectOutputStream ObjOut = new ObjectOutputStream(new FileOutputStream(vF));
				ObjOut.writeObject(venFile);
				return venFile;
				
			}
			else {
				ObjectInputStream ios = new ObjectInputStream(new FileInputStream(vF));
				venFile = (Map<String,Vendor>) ios.readObject();
				return venFile;
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return venFile;
	}
	

	
	public static List<Purchase> purchaseFile(){
		List<Purchase> prod = new ArrayList<>();
		File pF = new File("Purchase.ser");
		boolean flag = false;
		try {
			if(!pF.exists()) {
				pF.createNewFile();
				flag = true;
			}
			if(flag) {
				prod = new ArrayList<>();
				ObjectOutputStream ObjOut = new ObjectOutputStream(new FileOutputStream(pF));
				ObjOut.writeObject(prod);
				return prod;
			}
			else {
				ObjectInputStream obj = new ObjectInputStream(new FileInputStream(pF));
				prod = (List<Purchase>) obj.readObject();
				return prod;
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return prod;
	}
}
