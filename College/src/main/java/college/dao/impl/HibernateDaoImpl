package college.dao.impl;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import college.dao.HibernateDao;

@Transactional
public abstract class HibernateDaoImpl<I extends Serializable, E extends Serializable> implements HibernateDao<I, E> {

	@Autowired
	private SessionFactory sessionFactory;

	private Class<E> entityClass;

	public void setEntityClass(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public E findOneById(final I i) {
		return (E) getSession().get(entityClass, i);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<E> findAll() {
		Criteria criteria = getSession().createCriteria(entityClass);

		return new TreeSet<>(criteria.list());
	}

	@Override
	public void saveOrUpdate(E e) {
		getSession().saveOrUpdate(e);
	}

	@Override
	public void deleteById(final I i) {
		E e = findOneById(i);
		getSession().delete(e);
	}

}
