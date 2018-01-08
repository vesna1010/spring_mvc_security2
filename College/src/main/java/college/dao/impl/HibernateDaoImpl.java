package college.dao.impl;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import college.dao.HibernateDao;

@Transactional
public abstract class HibernateDaoImpl<E extends Serializable> implements HibernateDao<E> {

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
	public E findOneById(final String id) {
		return (E) getSession().get(entityClass, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<E> findAll() {
		Criteria criteria = getSession().createCriteria(entityClass);

		return new HashSet<E>(criteria.list());
	}

	@Override
	public void saveOrUpdate(E e) {
		getSession().merge(e);
	}

	@Override
	public void deleteById(final String id) {
		E e = findOneById(id);
		getSession().delete(e);
	}

}
