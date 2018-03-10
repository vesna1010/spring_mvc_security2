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
public abstract class HibernateDaoImpl<ID extends Serializable, E extends Serializable> implements HibernateDao<ID, E> {

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
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Set<E> findAll() {
		Criteria criteria = this.getSession().createCriteria(entityClass);

		return new HashSet<E>(criteria.list());
	}

	@Override
	public void saveOrUpdate(E entity) {
		this.getSession().saveOrUpdate(entity);
	}

	@Override
	public void deleteAll() {
		for (E entity : this.findAll()) {
			this.delete(entity);
		}
	}

	@Override
	public void deleteById(ID id) {
		E entity = this.findById(id);

		this.delete(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public E findById(ID id) {
		return (E) this.getSession().get(entityClass, id);
	}

	@Override
	public void delete(E entity) {
		this.getSession().delete(entity);
	}

}

