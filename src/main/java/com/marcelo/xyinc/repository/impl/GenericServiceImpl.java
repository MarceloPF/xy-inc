package com.marcelo.xyinc.repository.impl;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marcelo.xyinc.repository.GenericService;

@Repository
public class GenericServiceImpl<E extends Serializable, K extends Serializable> implements GenericService<E, K> {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    @Override
    public E save(final E object) {
	return (E) getSession().save(object);
    }

    @Override
    public E update(final E object) {
	getSession().update(object);
	return object;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E findById(K key, final Class<?> clazz) {
	DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
	criteria.add(Restrictions.eq("id", key));
	return (E) criteria.getExecutableCriteria(getSession()).uniqueResult();
    }

    @Override
    public void delete(E object) {
	getSession().delete(object);
    }

    private Session getSession() {
	return sessionFactory.getCurrentSession();
    }

}
