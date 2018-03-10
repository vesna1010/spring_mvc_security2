package college.dao;

import java.io.Serializable;
import java.util.Set;

public interface HibernateDao<ID extends Serializable, E extends Serializable> {

	Set<E> findAll();

	E findById(ID id);

	void saveOrUpdate(E entity);

	void deleteAll();

	void delete(E entity);
	
	void deleteById(ID id);

}

