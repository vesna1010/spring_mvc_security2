package college.dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class HibernateDao<T extends Serializable> {

	private Class<T> myClass;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session session(){
		return sessionFactory.getCurrentSession();
	}

	public void setMyClass(Class<T> myClass) {
		this.myClass = myClass;
	}

	public T findOne(String id){
		return (T)session().get(myClass, id);
	}
	
	public T findOne(Long id){
		return (T)session().get(myClass, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAll(){
		return (List<T>)session().createCriteria(myClass).list();
	}
	
	public void save(T t){
		session().saveOrUpdate(t);
	}
	
	public void delete(String id){
		T t=findOne(id);
		if(t!=null)
		session().delete(t);
	}
	
	public void delete(Long id){
		T t=findOne(id);
		if(t!=null)
		session().delete(t);
	}
	
	public void deleteAll(){
		List<T> list=findAll();
		for(int i=0; i<list.size();i++)
			session().delete(list.get(i));
	}
	
}
