package com.crivano.jflow.model;

import java.util.List;

import com.crivano.jflow.support.Procedimento;

public interface IDao {

	void persist(IProcedimento pi);

	List<Procedimento> loadAllByConditionByAncestor(Class<Procedimento> class1, String string, String evento,
			String namespace);

}
