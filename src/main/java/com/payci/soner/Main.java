package com.payci.soner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.payci.soner.entities.Account;
import com.payci.soner.entities.Address;
import com.payci.soner.entities.Customer;
import com.payci.soner.entities.Phone;
import com.payci.soner.entities.reflection.ClassTbl;
import com.payci.soner.entities.reflection.MethodTbl;
import com.payci.soner.hibernate.ClassTblRepository;
import com.payci.soner.hibernate.CustomerRepository;
import com.soner.payci.helpers.ReflectionHelper;

public class Main {

	public static void main(String[] args) {
		//initalizeEntities();
		
//		ArrayList<Class<?>> classTypes = new ArrayList<Class<?>>();
//		
//		classTypes.add(String.class);
//		classTypes.add(List.class);
//		classTypes.add(ArrayList.class);
//		classTypes.add(HashMap.class);
//		classTypes.add(int.class);
//		
//		String serializedClassTypes = ClassTypeHelper.SerializeTypes(classTypes);
//		
//		System.out.println(serializedClassTypes);
//		
//		
//		classTypes = ClassTypeHelper.DeserializeClassTypes(serializedClassTypes);
//		
//		for(Class<?> classType : classTypes){
//			System.out.println(classType.getSimpleName());
//		}
		
		Method[] declaredMethods = CustomerRepository.class.getDeclaredMethods();
		
		Class<?> superClass = CustomerRepository.class.getSuperclass();
		
		Method[] superClassDeclaredMethods = superClass.getDeclaredMethods();
		
		
		
		for(Method declaredMethod : ReflectionHelper.getAllMethods(CustomerRepository.class)) {
			System.out.println(declaredMethod);
		}
		
		
		Method singleMethod = ReflectionHelper.getMethod(CustomerRepository.class, "getAll");
		
		System.out.println("single method : " + singleMethod);
		
	}
	
//	private static void initializeCommands() {
//		ClassTblRepository classTblRepository = new ClassTblRepository();
//		
//		ClassTbl customerRepositoryTbl = new ClassTbl("CustomerRepository", "com.payci.soner.hibernate");
//		customerRepositoryTbl.addMethod(new MethodTbl("getAll", (String)null));
//		customerRepositoryTbl.addMethod(new MethodTbl("get", "long"));
//		
//		
//		ClassTbl addressTbl = new ClassTbl("Address", "com.payci.soner.entities.reflection");
//		
//		
//		ClassTbl customerTbl = new ClassTbl("Customer", "com.payci.soner.entities.reflection");
//		
//		
//		ClassTbl phoneTbl = new ClassTbl("Phone", "com.payci.soner.entities.reflection");
//	}
	
	
	private static void initializeEntities() {
		CustomerRepository customerRepository = new CustomerRepository();

		Customer customer = new Customer("Soner", "Paycı");
		customerRepository.saveOrUpdate(customer);

		Address address1 = new Address("Manisa", "Salihli", "45300", "bulunamayan adres.");
		Address address2 = new Address("Gebze", "Merkez", "????", "ibtech adres.");
		customer.addAddres(address1);
		customer.addAddres(address2);
		
		Account account = new Account(0.0);
		customer.addAccount(account);
		
		Phone phone = new Phone("+90", "555 222 11 00");
		customer.addPhone(phone);

		customerRepository.saveOrUpdate(customer);
		
		Customer persistentCustomer = customerRepository.get(customer.getId());
		
		System.out.println(persistentCustomer.toString());
		

		List<Address> persistentAddresses = persistentCustomer.getAddresses();
		for (Address addrs : persistentAddresses) {
			System.out.println(addrs.toString());
		}
	}
}
