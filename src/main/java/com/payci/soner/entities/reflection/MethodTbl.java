package com.payci.soner.entities.reflection;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.payci.soner.entities.base.BaseEntity;
import com.soner.payci.helpers.ReflectionHelper;

@Entity
@Table(name = "MethodTbl")
public class MethodTbl extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public MethodTbl() {}
	
	public MethodTbl(String name, ArrayList<Class<?>> parameters) {
		this.name = name;
		this.parameters = parameters;
	}
	
	public MethodTbl(long id, String name, String parameters) {
		this(name, ReflectionHelper.DeserializeClassTypes(parameters));
		this.id = id;
	}
	
	
	private String name;
	
	private ArrayList<Class<?>> parameters;
	
	@ManyToOne
    @JoinColumn(name="class_id")
    private ClassTbl classTbl;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Class<?>> getParameters() {
		return parameters;
	}

	public void setParameters(ArrayList<Class<?>> parameters) {
		this.parameters = parameters;
	}

	public ClassTbl getClassTbl() {
		return classTbl;
	}

	public void setClassTbl(ClassTbl classTbl) {
		this.classTbl = classTbl;
	}
}
