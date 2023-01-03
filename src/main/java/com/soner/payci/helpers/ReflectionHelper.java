package com.soner.payci.helpers;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.payci.soner.hibernate.CustomerRepository;

public class ReflectionHelper {

	public static Class<?> ExtractClassType(String className) {
		try {
			Class<?> cls = Class.forName(className);
			return cls;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return String.class;
		}
	}
	
	public static String SerializeTypes(ArrayList<Class<?>> classTypes) {
		StringBuilder sb = new StringBuilder();
		
		Iterator<Class<?>> iterator = classTypes.iterator();
		
		//[ a , b , c]
		while (iterator.hasNext()) {
			sb.append(iterator.next().getSimpleName());
			sb.append(",");
		}
		return sb.toString();
	}
	
	public static ArrayList<Class<?>> DeserializeClassTypes(String classTypesStr) {
		String[] classTypesArr = classTypesStr.split(",");
		ArrayList<Class<?>> classTypes = new ArrayList<Class<?>>();
		
		for(String classTypeStr : classTypesArr) {
			Class<?> classType;
			try {
				classType = Class.forName(classTypeStr);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				classType = String.class;
			}
			classTypes.add(classType);
		}
		return classTypes;
	}
	
	
	public static List<Method> getAllMethods(Class<?> cls) {
		List<Method> suitableMethods = new ArrayList<Method>();
		do {
			System.out.println(cls.getSimpleName());
			suitableMethods.addAll(Arrays.asList(cls.getSuperclass().getMethods()));
			cls = cls.getSuperclass();
		} while (cls.getSuperclass() != null && cls.getSuperclass().getSimpleName() != "Object");
		
		return suitableMethods;
	}
	
	public static Method getMethod(Class<?> cls, String methodName) {
		Method suitableMethod = null;
		do {
			System.out.println(cls.getSimpleName());
			// check if list contains desired method.
			// if method not exists in existing method list, look for super classes until
			// there is no super class or method found.
			for(Method method : cls.getSuperclass().getMethods()) {
				if (method.getName().equals(methodName)) {
					suitableMethod = method;
					return suitableMethod;
				}
			}
			
			cls = cls.getSuperclass();
		} while (suitableMethod == null || cls.getSuperclass().getSimpleName() != "Object");
		
		return suitableMethod;
	}
}
