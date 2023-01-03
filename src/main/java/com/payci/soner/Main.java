package com.payci.soner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.payci.soner.entities.Account;
import com.payci.soner.entities.Address;
import com.payci.soner.entities.Customer;
import com.payci.soner.entities.Phone;
import com.payci.soner.entities.reflection.CommandTbl;
import com.payci.soner.entities.reflection.MethodTbl;
import com.payci.soner.helpers.ReflectionHelper;
import com.payci.soner.hibernate.ClassTblRepository;
import com.payci.soner.hibernate.CustomerRepository;
import com.payci.soner.hibernate.MethodTblRepository;

public class Main {

	static boolean run = true;
	public static void main(String[] args) {
		initializeEntities();
		initializeRepositoryCommands("com.payci.soner.operations", "Customer");
		//testReflection();
		
		StringBuilder sb = new StringBuilder();
		while(run) {
			try (Scanner scanner = new Scanner(System.in)){
				System.out.print("Enter CommandName: ");  
				String commandName = scanner.nextLine();
				
				MethodTblRepository methodTblRepository = new MethodTblRepository();
				MethodTbl methodTbl = methodTblRepository.getByCommandName(commandName);
				CommandTbl commandTbl = methodTbl.getCommandTbl();
				String fullClassPath = sb.append(commandTbl.getPackageName())
						.append('.')
						.append(commandTbl.getName())
						.append("Operations").toString();
				
				Class<?> cls = ReflectionHelper.ExtractClassType(fullClassPath);
				Object clsObj = cls.getDeclaredConstructor().newInstance();
				
				
				//Method method = cls.getDeclaredMethod("method name", parameterTypes);
				Method method = ReflectionHelper.getMethod(cls, methodTbl.getName());
				
				List<Class<?>> methodParams = methodTbl.getParameters();
				
				Object rv = method.invoke(clsObj);
				
				System.out.println(cls);
			} catch (Exception e) {
				System.out.println("\nException occured. Try Again."); 
			}
		}
	}
	
	private static void initializeRepositoryCommands(String packagePath, String className) {
		StringBuilder sb = new StringBuilder();
		String fullClassPath = sb.append(packagePath)
				.append('.')
				.append(className)
				.append("Operations")
				.toString();
		sb.setLength(0);
		
		ClassTblRepository classTblRepository = new ClassTblRepository();
		
		if (classTblRepository.getByName(className) != null)
			return;
		
		CommandTbl repositoryTbl = new CommandTbl(className, packagePath);
		Class<?> repositoryClass = ReflectionHelper.ExtractClassType(fullClassPath);
		
		List<Method> methods = ReflectionHelper.getAllMethods(repositoryClass);
		
		for(Method method : methods) {
			ArrayList<Class<?>> parameterTypesOfMethod = ReflectionHelper.getParameterTypes(method);
			String commandName = sb.append(className)
					.append('_')
					.append(method.getName())
					.toString();
			sb.setLength(0);
			MethodTbl methodTbl = new MethodTbl(method.getName(), commandName, parameterTypesOfMethod);
			repositoryTbl.addMethod(methodTbl);
		}
		classTblRepository.save(repositoryTbl);
	}
	
	
	private static void testReflection() {
		for(Method declaredMethod : ReflectionHelper.getAllMethods(CustomerRepository.class)) {
			System.out.println(declaredMethod);
		}

		Method singleMethod = ReflectionHelper.getMethod(CustomerRepository.class, "getAll");
		
		System.out.println("single method : " + singleMethod);
	}
	
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
