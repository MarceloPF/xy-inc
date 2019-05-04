package com.marcelo.xyinc.repository.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marcelo.xyinc.model.GenericModel;
import com.marcelo.xyinc.repository.GenericRepository;

@Repository
public class GenericRepositoryImpl<K extends Serializable, E extends Serializable> implements GenericRepository<K, E> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public E save(final E object) {
	getSession().save(object);
	return (E) object;
    }

    @Override
    public E update(final E object) {
	final E existObject = load(object);
	E result = null;
	if (existObject != null) {
	    getSession().update(object);
	    result = object;
	}
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E findById(K key, final Class<?> clazz) {
	final DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
	criteria.add(Restrictions.eq("id", key));
	return (E) criteria.getExecutableCriteria(getSession()).uniqueResult();
    }

    @Override
    public void delete(E object) {
	final E existObject = load(object);
	if (existObject != null) {
	    getSession().delete(object);
	}
    }

    @SuppressWarnings({ "unchecked", "hiding" })
    private <E> E load(final E object) {
	final DetachedCriteria criteria = DetachedCriteria.forClass(((Object) object).getClass());
	criteria.add(Restrictions.eq("id", ((GenericModel) object).getId()));
	E result = (E) criteria.getExecutableCriteria(getSession()).uniqueResult();
	if (result != null) {
	    getSession().evict(result);
	}
	return result;
    }

    public Session getSession() {
	return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<E> findAll(Class<?> clazz) {
	final DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
	return criteria.getExecutableCriteria(getSession()).list();
    }

}
