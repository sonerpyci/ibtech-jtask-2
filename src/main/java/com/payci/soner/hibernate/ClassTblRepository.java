package com.payci.soner.hibernate;

import java.util.List;

import javax.transaction.Transactional;

import com.payci.soner.entities.reflection.ClassTbl;
import com.payci.soner.hibernate.base.BaseRepository;

public class ClassTblRepository extends BaseRepository {
	@Transactional
	public List<ClassTbl> getAll() {
		return getAll(ClassTbl.class);
	}

	@Transactional
	public ClassTbl get(long id) {
		ClassTbl clazz = get(ClassTbl.class, id);
		clazz.getMethods();
		return clazz;
	}

	public void deleteById(long id) {
		super.deleteById(ClassTbl.class, id);
	}
}
