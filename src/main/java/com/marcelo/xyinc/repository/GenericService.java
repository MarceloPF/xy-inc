package com.marcelo.xyinc.repository;

import java.io.Serializable;

public interface GenericService<E extends Serializable, K extends Serializable> {

    public E save(final E object);

    public E update(final E object);

    public E findById(K key, final Class<?> clazz);
    
    public void delete(final E object);

}
