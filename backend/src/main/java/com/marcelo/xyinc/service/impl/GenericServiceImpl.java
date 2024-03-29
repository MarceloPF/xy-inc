package com.marcelo.xyinc.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcelo.xyinc.repository.GenericRepository;
import com.marcelo.xyinc.service.GenericService;

@Service
@Transactional
public class GenericServiceImpl<K extends Serializable, E extends Serializable> implements GenericService<K, E> {

    private GenericRepository<K, E> genericRepository;

    @Override
    @Autowired
    @Qualifier("genericRepositoryImpl")
    public void setDAO(final GenericRepository<K, E> genericRepository) {
	this.genericRepository = genericRepository;
    }

    @Override
    public GenericRepository<K, E> getDAO() {
	return genericRepository;
    }

    @Override
    public E save(E object) {
	final E result;
	if (validPreSave(object)) {
	    result = genericRepository.save(object);
	} else {
	    result = null;
	}
	return result;
    }

    @Override
    public E update(E object) {
	final E result;
	if (validPreSave(object)) {
	    result = genericRepository.update(object);
	} else {
	    result = null;
	}
	return result;
    }

    @Override
    public E findById(K key, Class<?> clazz) {
	return genericRepository.findById(key, clazz);
    }

    @Override
    public List<E> findAll(Class<?> clazz) {
	return genericRepository.findAll(clazz);
    }

    @Override
    public void delete(E object) {
	genericRepository.delete(object);
    }

    @Override
    public boolean validPreSave(E object) {
	return true;
    }


}
