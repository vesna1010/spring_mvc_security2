package college.service;

import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import college.dao.HibernateDao;

public abstract class HibernateService<T extends Serializable> {
	
	
    private HibernateDao<T> myDao;

    @Autowired
	public void setMyDao(HibernateDao<T> myDao) {
		this.myDao = myDao;
	}

	public T findOne(String id){
		return (T) myDao.findOne(id);
	}
	
	public T findOne(Long id){
		return (T) myDao.findOne(id);
	}
	
	public List<T> findAll(){
		return (List<T>) myDao.findAll();
	}
	
	public void save(T t){
		myDao.save(t);
	}
	
	public void delete(String id){
		myDao.delete(id);
	}
	
	public void delete(Long id){
		myDao.delete(id);
	}
	
	public void deleteAll(){
		myDao.deleteAll();
	}
	
	
}
