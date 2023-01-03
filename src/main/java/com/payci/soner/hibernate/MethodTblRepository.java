package com.payci.soner.hibernate;

import java.util.List;

import javax.transaction.Transactional;

import com.payci.soner.entities.reflection.MethodTbl;
import com.payci.soner.hibernate.base.BaseRepository;

public class MethodTblRepository extends BaseRepository {
	@Transactional
	public List<MethodTbl> getAll() {
		return getAll(MethodTbl.class);
	}

	@Transactional
	public MethodTbl get(long id) {
		MethodTbl method = get(MethodTbl.class, id);
		method.getClass();
		return method;
	}

	public void deleteById(long id) {
		super.deleteById(MethodTbl.class, id);
	}
}
