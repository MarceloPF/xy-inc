package com.marcelo.xyinc.repository;

import java.io.Serializable;

public interface GenericService<E extends Serializable, K extends Serializable> {

    public E save(final E object);

    public E update(final E object);

    public E findBiId(final K key);
    
    public int delete(final E object);

}
