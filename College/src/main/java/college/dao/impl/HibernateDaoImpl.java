package college.dao.impl;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import college.dao.HibernateDao;

public abstract class HibernateDaoImpl<T extends Serializable, ID extends Serializable> implements HibernateDao<T, ID> {

	@Autowired
	private SessionFactory sessionFactory;
	private Class<T> entityClass;

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		Criteria criteria = getSession().createCriteria(entityClass);

		return criteria.list();
	}

	@Override
	public void saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
	}

	@Override
	public void deleteById(ID id) {
		getSession().delete(findById(id));
	}

	@Override
	public T findById(ID id) {
		return (T) getSession().get(entityClass, id);
	}

}
