package com.marcelo.xyinc.repository;

import java.io.Serializable;
import java.util.List;

public interface GenericRepository<K extends Serializable, E extends Serializable> {

    public E save(final E object);

    public E update(final E object);

    public E findById(K key, final Class<?> clazz);

    public List<E> findAll(final Class<?> clazz);
    
    public void delete(final E object);

}
