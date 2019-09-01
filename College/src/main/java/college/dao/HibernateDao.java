package college.dao;

import java.io.Serializable;
import java.util.List;

public interface HibernateDao<T extends Serializable, ID extends Serializable> {

	List<T> findAll();

	T findById(ID id);

	void saveOrUpdate(T entity);

	void deleteById(ID id);

}

