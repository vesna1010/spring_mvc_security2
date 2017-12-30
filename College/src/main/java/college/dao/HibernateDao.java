package college.dao;

import java.io.Serializable;
import java.util.Set;

public interface HibernateDao<I extends Serializable, E extends Serializable> {

	Set<E> findAll();

	E findOneById(final I i);

	void saveOrUpdate(E e);

	void deleteById(final I i);

}
