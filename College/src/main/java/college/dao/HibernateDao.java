package college.dao;

import java.io.Serializable;
import java.util.Set;

public interface HibernateDao<E extends Serializable> {

	Set<E> findAll();

	E findOneById(final String id);

	void saveOrUpdate(E e);

	void deleteById(final String id);

}
